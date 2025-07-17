#include "net/Server.hpp"

#include "Logger.hpp"

Server::Server(int port, const ConnectionListener& connectionListener, SettingsManager& settings, const INPUT_MANAGER& inputmanager)
	: settings(settings), 
	inputmanager(inputmanager),
	port(port),
	connectionListener(connectionListener),
	acceptor(tcp::acceptor(context, tcp::endpoint(tcp::v4(), port))),
	udpsocket(context, udp::endpoint(asio::ip::udp::v4(), port))
{
	byteBuffer = new unsigned char[10];
	streamingDevice = 0;
	settings.ADBOn();
	StartReceive();
}

Server::HostInfo Server::GetHostInfo()
{
	std::string name = asio::ip::host_name();

	// Get the active network adapter by 
	// creating a dummy UDP connection and let the OS 
	// determine what network adapter will be used
	udp::resolver resolver(context);
	udp::socket socket(context);

	socket.connect(udp::endpoint(asio::ip::address::from_string("8.8.8.8"), 80));

	// The IPv4 address needed is the address of the local endpoint assigned to the socket
	// (the network adapter actual traffic is routed trough)
	asio::ip::address local_addr = socket.local_endpoint().address();

	socket.close();

	return { name, local_addr.to_string(), std::to_string(port)};
}

void Server::Start()
{
	try
	{
		TCPDoAccept();
		thread = std::thread([this]() {
			context.run();
		});
		LOG("[SERVER] Started");
	}
	catch (std::exception e)
	{
		LOG("[SERVER] Start failed");
	}
}

void Server::Close()
{
	LOG("[SERVER] Closing...");

	settings.ADBOff();

	acceptor.close();
	udpsocket.close();
	
	for (std::shared_ptr<Connection> conn : connections)
	{
		conn->Close(true);
	}

	context.stop();

	if (thread.joinable())
	{
		thread.join();
	}

	LOG("[SERVER] Closed.\n");
}

std::vector<DeviceInfo> Server::GetConnectedDevicesInfo()
{
	std::vector<DeviceInfo> connectionInfo;
	for (auto& connection : connections)
	{
		connectionInfo.push_back(connection->GetDeviceInfo());
	}
	return connectionInfo;
}

void Server::TCPDoAccept()
{
	acceptor.async_accept([&, this](asio::error_code ec, tcp::socket socket) {
		if (!ec)
		{
			LOG("[SERVER] Device connected.", socket.remote_endpoint());

			// Read the device details sent by the client (name, model, manufacturer)
			std::array<char, 128> buf;
			size_t bytes = socket.read_some(asio::buffer(buf, 128));
			std::string deviceDetails(buf.data(), bytes);

			auto conn = std::make_shared<Connection>(this->inputmanager, std::move(socket), deviceDetails, std::bind(&Server::OnConnectionDisconnected, this, std::placeholders::_1));
			connections.push_back(std::move(conn));

			connectionListener.OnDeviceConnected(connections.back()->GetDeviceInfo().Name);
		}

		TCPDoAccept();
	});
}

void Server::StartReceive()
{
	udpsocket.async_receive_from(asio::buffer(byteBuffer, 10), remote_endpoint, [&](const std::error_code& ec, size_t bytesReceived) {
		if (!ec)
		{
			// inputmanager.execute(byteBuffer...)

			// Mechanism to synchronize the server with the client
			// otherwise client might send the next segment of the 
			// frame before the server finished processing the current 
			// one result in loss of data
			// The client waits to read some bytes after between sending segments
			// udpsocket.send_to(asio::buffer("done"), remote_endpoint);

			StartReceive();
		}
	});
}

void Server::OnConnectionDisconnected(std::shared_ptr<Connection> connection)
{
	connections.erase(std::remove(connections.begin(), connections.end(), connection), connections.end());
	connectionListener.OnDeviceDisconnected(connection->GetDeviceInfo().Name);
}

#include "net/Connection.hpp"

#include "Logger.hpp"

Connection::Connection(const INPUT_MANAGER& inputmanager, tcp::socket socket, std::string& deviceDetails, OnDisconnectedListener onDisconnectedListener)
	: inputmanager(inputmanager),
	socket(std::move(socket)),
	onDisconnectedListener(onDisconnectedListener)
{
	// Parse the device details string to get the device info
	std::stringstream ss(deviceDetails);
	std::string token;
	std::vector<std::string> tokens;
	while(std::getline(ss, token, '/'))
		tokens.push_back(token);

	// Build the device info from the parsed tokens
	deviceInfo.Manufacturer = tokens[0];
	deviceInfo.Name = tokens[1];
	deviceInfo.Model = tokens[2];
	deviceInfo.ctype = std::atoi(tokens[3].c_str());
	deviceInfo.Address = this->socket.remote_endpoint().address().to_string();

	byteBuffer = new unsigned char[10];
	active = false;
	Read();
}

DeviceInfo Connection::GetDeviceInfo()
{
    return deviceInfo;
}

void Connection::Read()
{
	socket.async_read_some(asio::buffer(byteBuffer, 10), [this](asio::error_code ec, size_t bytes) {
		if (!ec)
		{
			// inputmanager.execute(byteBuffer...)
			Read();
		}
		else
		{
			// When the connection is closed by the server this function may still be called
			// and then the socket will be invalid so wrap it in a try/catch
			// 
			// Also the close() method is called inside the try to avoid closing
			// the socket again when the server is stopped
			try {
				LOG("[CONNECTION@", this->socket.remote_endpoint(), "] Closed with error code: ", ec);
				Close();
			} catch(std::exception e) { }
		}
	});
}

void Connection::Send(std::string message)
{
	socket.send(asio::buffer(message));
}

void Connection::Send(unsigned char* bytes, size_t size)
{
	socket.send(asio::buffer(bytes, size));
}

void Connection::Close(bool stoppedByServer)
{
	if (stoppedByServer)
	{
		// Sometimes socket.cancel() makes the app to not respond
		// but sometimes is required to successfully remove the adb
		// port forwarding
		// TODO...
		//socket.cancel();
		socket.close();
	}
	else
	{
		socket.close();
		onDisconnectedListener(this->shared_from_this());
	}
}
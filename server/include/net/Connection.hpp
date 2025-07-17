#ifndef CONNECTION_HPP
#define CONNECTION_HPP

#include <memory>
#include <functional>
#include <asio.hpp>

#include "input/input.h"
#include "net/Device.hpp"

/// <summary>
/// Manages a TCP connection
/// </summary>
class Connection : public std::enable_shared_from_this<Connection>
{
	friend class Server;

public:
	using tcp = asio::ip::tcp;
	using udp = asio::ip::udp;
	using OnDisconnectedListener = std::function<void(std::shared_ptr<Connection>)>;

	Connection(const INPUT_MANAGER& inputmanager, tcp::socket socket, std::string& deviceDetails, OnDisconnectedListener onDisconnectedListener);

	DeviceInfo GetDeviceInfo();
private:

	OnDisconnectedListener onDisconnectedListener;

	tcp::socket socket;
	const INPUT_MANAGER& inputmanager;
	unsigned char* byteBuffer;

	bool active;
	DeviceInfo deviceInfo;

	void Read();

	void Send(std::string message);
	void Send(unsigned char* bytes, size_t size);
	void Close(bool stoppedByServer = false);
};

#endif

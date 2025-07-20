#ifndef ADB_HPP
#define ADB_HPP

#ifdef _WIN32

#include <windows.h>
#include <string>

#include "Logger.hpp"

namespace adb
{
	/*
	* Get the local directory of the program
	*/
	std::string dir()
	{
		char buffer[MAX_PATH];
		GetModuleFileNameA(NULL, buffer, MAX_PATH);

		std::string path(buffer);
		return path.substr(0, path.find_last_of("\\/"));
	}

	
	/*
	* Reverse the given tcp port.
	* adb reverse tcp:<port> tcp:<port>
	*/
	bool start(int port)
	{
		std::string path = dir();
		std::string command = path + "\\adb.exe " + "reverse tcp:" + std::to_string(port) + " tcp:" + std::to_string(port);

		if (system(command.c_str()) == 0)
		{
			LOG("[ADB:", port, "] Started");
			return true;
		}
		else
		{
			LOG("[ADB:", port, "] Failed to start");
			return false;
		}
	}

	/*
	* Removes the reversed given tcp port.
	* adb reverse --remove tcp:<port>
	*/
	bool kill(int port)
	{
		std::string path = dir();

		// Sometimes without kill-server adb port remains used and the app
		// won't start next time
		// Sometimes with kill-server it takes too long for the app to stop
		// becoming not responding
		// std::string command = path + "\\adb\\adb.exe " + "reverse --remove tcp:" + std::to_string(port);
		std::string command = path + "\\adb.exe " + "reverse --remove tcp:" + std::to_string(port) + " & " + path + "\\adb\\adb.exe kill-server";

		if (system(command.c_str()) == 0)
		{
			LOG("[ADB:", port, "] Stopped");
			return true;
		}
		else
		{
			LOG("[ADB:", port, "] Failed to stop");
			return false;
		}
	}
}

#endif // _WIN32
#endif // ADB_HPP
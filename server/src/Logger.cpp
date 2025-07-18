#include "Logger.hpp"

int Logger::rows = 0;
wxListView* Logger::monitor;
std::stringstream Logger::output;
std::ofstream Logger::fileoutstream("app.log");

void Logger::displayOutput()
{
    time_t now = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
    std::string time = std::string(ctime(&now) + 11, 8);

    monitor->InsertItem(rows, time);
    monitor->SetItem(rows++, 1, output.str());

    fileoutstream << time << " " << output.str() << std::endl;

    output.str("");
}

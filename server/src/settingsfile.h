#pragma once

#include <filesystem>
#include <fstream>
#include <map>
#include <sstream>
#include <string>
#include <system_error>

#ifdef _WIN32
#include <windows.h>
#endif

// Keep file handling independent of the GUI so startup defaults and persistence
// can be tested without wxWidgets or an input device.
namespace SettingsFile
{
    using Values = std::map<std::string, std::string>;

    inline std::filesystem::path BesideExecutable(const std::filesystem::path& executable)
    {
        return executable.parent_path() / "config.ini";
    }

    #ifdef _WIN32
    inline std::filesystem::path ExecutableConfigPath()
    {
        // SettingsManager is constructed before wxApp::OnInit. Resolve the
        // executable directly, without depending on initialized GUI state.
        std::wstring executable(256, L'\0');
        for (;;)
        {
            const DWORD length = GetModuleFileNameW(nullptr, executable.data(),
                static_cast<DWORD>(executable.size()));
            if (length == 0)
                throw std::system_error(GetLastError(), std::system_category(),
                    "Cannot locate Mousedroid executable");
            if (length < executable.size())
            {
                executable.resize(length);
                return BesideExecutable(std::filesystem::path(executable));
            }
            executable.resize(executable.size() * 2);
        }
    }
    #endif

    inline std::string Sensitivity(const std::string& text, int fallback)
    {
        std::istringstream input(text);
        int value = 0;
        if (!(input >> value) || value < 1 || value > 20)
            return std::to_string(fallback);
        input >> std::ws;
        return input.eof() ? std::to_string(value) : std::to_string(fallback);
    }

    inline void Load(const std::filesystem::path& path, Values& values)
    {
        values.emplace("RUN_STARTUP", "0");
        values.emplace("MINIMIZE_TASKBAR", "0");
        values.emplace("MOVE_SENSITIVITY", "10");
        values.emplace("SCROLL_SENSITIVITY", "3");

        std::ifstream input(path);
        std::string line;
        while (std::getline(input, line))
        {
            const auto separator = line.find('=');
            if (separator == std::string::npos || separator == 0)
                continue;
            values[line.substr(0, separator)] = line.substr(separator + 1);
        }

        values["MOVE_SENSITIVITY"] = Sensitivity(values["MOVE_SENSITIVITY"], 10);
        values["SCROLL_SENSITIVITY"] = Sensitivity(values["SCROLL_SENSITIVITY"], 3);
    }

    inline bool Save(const std::filesystem::path& path, const Values& values)
    {
        std::ofstream output(path);
        for (const auto& pair : values)
            output << pair.first << '=' << pair.second << '\n';
        output.close();
        return !output.fail();
    }
}

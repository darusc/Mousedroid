#include "../src/settingsfile.h"

#include <chrono>
#include <iostream>
#include <stdexcept>

namespace fs = std::filesystem;

void require(bool condition, const char* message)
{
    if (!condition)
        throw std::runtime_error(message);
}

struct Fixture
{
    fs::path previous = fs::current_path();
    fs::path root = fs::temp_directory_path() / ("mousedroid-settings-" +
        std::to_string(std::chrono::steady_clock::now().time_since_epoch().count()));
    fs::path app = root / fs::u8path(u8"app with spaces \u7528\u6237");
    fs::path other = root / "different-launch-directory";
    fs::path config = SettingsFile::BesideExecutable(app / "Mousedroid.exe");

    Fixture()
    {
        fs::create_directories(app);
        fs::create_directories(other);
    }

    ~Fixture()
    {
        std::error_code error;
        fs::current_path(previous, error);
        fs::remove(config, error);
        fs::remove(app, error);
        fs::remove(other, error);
        fs::remove(root, error);
    }
};

int main()
{
    try
    {
        Fixture fixture;
        SettingsFile::Values values;
        SettingsFile::Load(fixture.config, values);
        require(values.at("MOVE_SENSITIVITY") == "10", "Missing file must not disable movement");
        require(values.at("SCROLL_SENSITIVITY") == "3", "Missing file must not disable scrolling");

        values["MOVE_SENSITIVITY"] = "17";
        values["SCROLL_SENSITIVITY"] = "8";
        #ifdef _WIN32
        const auto nativeConfigPath = SettingsFile::ExecutableConfigPath();
        #endif
        fs::current_path(fixture.other);
        #ifdef _WIN32
        require(SettingsFile::ExecutableConfigPath() == nativeConfigPath,
            "Windows executable lookup depends on the launch directory");
        #endif
        require(SettingsFile::Save(fixture.config, values), "Save beside executable failed");
        require(!fs::exists(fixture.other / "config.ini"), "Save used the launch directory");
        SettingsFile::Values restored;
        SettingsFile::Load(fixture.config, restored);
        require(restored.at("MOVE_SENSITIVITY") == "17", "Launch directory changed saved movement");
        require(restored.at("SCROLL_SENSITIVITY") == "8", "Launch directory changed saved scrolling");

        {
            std::ofstream partial(fixture.config);
            partial << "malformed line\n=ignored\nMOVE_SENSITIVITY=7\n";
        }
        SettingsFile::Values partial;
        partial["RUN_STARTUP"] = "1";
        SettingsFile::Load(fixture.config, partial);
        require(partial.at("MOVE_SENSITIVITY") == "7", "Valid partial setting was lost");
        require(partial.at("SCROLL_SENSITIVITY") == "3", "Partial file disabled scrolling");
        require(partial.at("RUN_STARTUP") == "1", "Default overwrote detected startup state");
        require(partial.count("malformed line") == 0, "Malformed line was treated as a setting");

        for (const auto* invalid : {"", "0", "-1", "21", "abc", "10garbage", "999999999999999"})
        {
            require(SettingsFile::Sensitivity(invalid, 10) == "10", "Invalid sensitivity accepted");
            require(SettingsFile::Sensitivity(invalid, 3) == "3", "Invalid scrolling fallback");
        }
        require(SettingsFile::Sensitivity("3 \r", 10) == "3", "Existing trailing whitespace rejected");
        require(SettingsFile::Sensitivity("1", 10) == "1", "Minimum sensitivity rejected");
        require(SettingsFile::Sensitivity("20", 10) == "20", "Maximum sensitivity rejected");
        require(!SettingsFile::Save(fixture.root / "missing" / "config.ini", values),
            "Failed writes must be reported");
        std::cout << "PASS: defaults, partial/malformed settings, sensitivity bounds, "
                     "launch-directory independence, Unicode paths, persistence, write failures\n";
        return 0;
    }
    catch (const std::exception& error)
    {
        std::cerr << "FAIL: " << error.what() << '\n';
        return 1;
    }
}

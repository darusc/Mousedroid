# Build it

## Prerequisites

### Windows

- wxWidgets https://www.wxwidgets.org/downloads/. Build it yourself or use the prebuild binaries and add it to path as `WXWIN`. 
  https://wiki.wxwidgets.org/Compiling_wxWidgets_with_MinGW <br>
  https://wiki.wxwidgets.org/Compiling_and_getting_started

- ASIO https://think-async.com/Asio/. Download the library and add it to path as `ASIO`. Project is configured to use the standalone version, if you use the BOOST version you may need to change include paths.

- Android SDK Platform tools https://developer.android.com/tools/releases/platform-tools. For a wired connection adb is required. ADB binaries are also provided.


### Linux

- wxWidgets https://www.wxwidgets.org/downloads/. Build it yourself or install the required packages:
```
libgtk-3-dev
libwxgtk3.0-dev
```
- ASIO https://think-async.com/Asio/. Download the library and config the CMakeLists or install the `libasio-dev` package.
- Android SDK Platform tools https://developer.android.com/tools/releases/platform-tools - install the ```android-platform-tools``` package.

## Build

### Windows

- First build the the `resource.rc`:
```
  windres resource.rc resource.o -I$(WXWIN)\include
```
- Build the `CMakeLists.txt`
```
  cmake -G"MinGW Makefiles" -B./cmake . 
```
- Run `make` in the `cmake` directory
- Run the `install.bat` script
- The built binaries are located in `build/bin`

### Linux

- Build the `CMakeLists.txt`
```
  cmake -B./cmake .
```
- Run `make` in the `cmake` directory
- Run the `install.sh` script
- The binary executable is located in `cmake/bin` and it's also installed in the system.



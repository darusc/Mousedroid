@echo off

copy %WXWIN%\lib\gcc_dll\wxbase32u_gcc_custom.dll cmake\bin
copy %WXWIN%\lib\gcc_dll\wxmsw32u_core_gcc_custom.dll cmake\bin

copy adb\adb.exe cmake\bin
copy adb\AdbWinApi.dll cmake\bin
copy adb\AdbWinUsbApi.dll cmake\bin

copy dll\libgcc_s_seh-1.dll cmake\bin
copy dll\libstdc++-6.dll cmake\bin
copy dll\libwinpthread-1.dll cmake\bin

copy /y app.ico cmake\bin

(
    echo MINIMIZE_TASKBAR=0
    echo MOVE_SENSITIVITY=10
    echo RUN_STARTUP=0
    echo SCROLL_SENSITIVITY=3 
)>cmake\bin\config.ini
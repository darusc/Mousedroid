@echo off
setlocal 

if not exist "mousedroid_win64" mkdir "mousedroid_win64"

xcopy "out\build\x64-Release\bin\*.*" "dist\" /E /I /Y /H

xcopy "adb" "dist\adb\" /E /I /Y

copy /y app.ico dist

(
    echo MINIMIZE_TASKBAR=0
    echo MOVE_SENSITIVITY=10
    echo RUN_STARTUP=0
    echo SCROLL_SENSITIVITY=3 
)>distconfig.ini

echo Deployment to "dist" complete!
pause
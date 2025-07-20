<h1  align="center">
  <sub>
    <img  src="imgs/logo.png"  width=200></img>
  </sub>
</h1>

<p align="center">Use your android phone as a mouse & keyboard.</p>

[<img src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png" width=170>](https://apt.izzysoft.de/fdroid/index/apk/com.darusc.mousedroid)

![GitHub License](https://img.shields.io/github/license/darusc/mousedroid?style=for-the-badge)
![GitHub Release](https://img.shields.io/github/v/release/darusc/mousedroid?style=for-the-badge)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/darusc/mousedroid/total?style=for-the-badge)
[<img src="https://shields.rbtlog.dev/simple/com.darusc.mousedroid" alt="RB shield">](https://shields.rbtlog.dev/com.darusc.mousedroid)

## Description

Cross platform application that transforms your android phone in an input peripheral. Use it as a mouse (touchpad), keyboard or numpad. Wired or wirelessly, control your pc with your phone.

### Supported platforms:
- Windows
- Linux
- Android 8 (Oreo) and newer

## How to use
1. [Download](https://github.com/darusc/Mousedroid/releases) the available binaries or build and install the server on your pc ([How to build](https://github.com/hypertensiune/Mousedroid/tree/master/server)).
2. [Download](https://github.com/darusc/Mousedroid/releases) and install the APK on your phone.
3. If you want to use the application in ***WIFI*** mode, make sure your phone is connected to the same network as the pc. <br>
   If you want to use it ***wired***, you must enable `USB Debugging` under `Developer options` on your phone. ([See](https://developer.android.com/tools/adb) for more details)
4. Start the server. (If you start the server after connecting your phone via USB you need to restart ADB)
5. For ***WIFI*** mode add the IP Address of your server and connect to it.

## Gestures
| Action         | Gesture     |
| -------------- | ----------- |
| Click          | Tap the screen once          |
| Double click   | Double tap the screen        |
| Right click    | Tap the screen once with two fingers
| Scroll         | Place two fingers on the screen and slide horizontally or vertically
| Drag & drop    | Long press and drag |
| Zoom           | Pinch & zoom |


## Notes

- For Android versions greater than 31 (Android 12L and newer) the app requires bluetooth permission to display the bluetooth name set by the user.
- If you start the server after connecting your phone via USB you need to restart ADB.
- On Wayland some features, like the taskbar icon may not be visible, switching to Xorg might solve the issue.
- For linux the app, development and tests were done on a **Ubuntu 22.04 virtual machine**. There might be some differences when run on a non-virtual machine intallation

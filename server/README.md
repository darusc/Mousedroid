# Building Mousedroid Server

Mousedroid uses a cross-platform CMake build system. Follow the instructions below based on your operating system.

---

## Prerequisites

### **Windows (Visual Studio + vcpkg)**
* **IDE:** [Visual Studio 2022/2026](https://visualstudio.microsoft.com/vs/) with the **"Desktop development with C++"** workload.
* **Package Manager:** [vcpkg](https://github.com/microsoft/vcpkg) is required for dependency management.

### **Linux (Ubuntu/Debian)**
* **Compiler:** GCC 11+ or Clang.
* **Build Tools:** CMake and Make.
* **Dependencies:** `asio` and `wxWidgets 3.3`.

---

## Build Instructions

### **Windows**

1.  **Install Dependencies via vcpkg** Open your terminal (PowerShell or CMD) and run:
    ```powershell
    vcpkg install asio:x64-windows wxwidgets:x64-windows
    vcpkg integrate install
    ```

2.  **Open & Build in Visual Studio** * Open **Visual Studio**.
    * Select **"Open a local folder"** and choose the Mousedroid project root.
    * Visual Studio will detect `CMakeLists.txt`. 
    * Set the configuration dropdown to **x64-Release**.
    * Go to **Build > Build All**.

3.  **Deploy** Run the deployment script to collect the executable and its dependencies into the `mousedroid_win64/` folder:
    ```powershell
    ./release.bat
    ```



---

### **Linux**

1.  **Install Dependencies** Install the required development packages via `apt`:
    ```bash
    sudo apt update
    sudo apt install build-essential cmake libwxgtk3.3-dev libasio-dev adb
    ```

2.  **Configure & Compile** Use the standard CMake out-of-source build workflow:
    ```bash
    cmake -B"cmake" -DCMAKE_BUILD_TYPE=Release
    cd cmake
    make
    ```

3.  **System Installation** Run the installation script to set up `uinput` permissions (allowing mouse control without root) and create the desktop launcher:
    ```bash
    cd ..
    chmod +x release.sh
    ./release.sh

    chmod +x install.sh
    ./install.sh
    ```



---

## 🔍 Troubleshooting

| Issue | Platform | Solution |
| :--- | :--- | :--- |
| **Missing DLLs** | Windows | Ensure `install.bat` was run to copy vcpkg DLLs to the executable folder. |
| **Mouse not moving** | Linux | Ensure `install.sh` was run and you have restarted your session to apply udev rules. |
| **No Tray Icon** | Linux | If using Wayland, tray icons may be hidden. Try switching to an X11 session. |
| **vcpkg Triplets** | Windows | Ensure you are targeting `x64-windows`. Use `vcpkg install ...:x86-windows` if you need 32-bit. |

---
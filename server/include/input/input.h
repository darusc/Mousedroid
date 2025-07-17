#ifndef INPUT_H
#define INPUT_H

#ifdef _WIN32
    #include "input/WindowsInputManager.hpp"
    typedef InputManager::Windows INPUT_MANAGER;
#endif
#ifdef __unix__
    #include "input/LinuxInputManager.hpp"
    typedef InputManager::Linux INPUT_MANAGER;
#endif

#endif
#ifndef INPUT_H
#define INPUT_H

#ifdef _WIN32
    #include "input/win32/windowsinputmanager.h"
    typedef InputManager::Windows INPUT_MANAGER;
#endif
#ifdef __unix__
    #include "input/linux/linuxinputmanager.h"
    typedef InputManager::Linux INPUT_MANAGER;
#endif

#endif
#pragma once

#include <string>
#include <iostream>
#include <vector>
#include <sstream>

#ifdef __unix__
    #include <climits>
#endif

#include "Logger.hpp"

namespace InputManager
{
    struct Command 
    {
        char type;
        std::vector<double> args;
    };   

    const char LCLICK = 0x01;
    const char RCLICK = 0x02;
    const char DOWN = 0x03;
    const char UP = 0x04;
    const char MOVE = 0x05;
    const char SCROLL = 0x06;
    const char KEYPRESS = 0x07;
    const char SCROLL_H = 0x08;
    const char ZOOM = 0x09;

    extern int MOVE_SENSITIVITY;
    extern int SCROLL_SENSITIVITY;
    
    struct KEY_PAIR
    {
        char c;
        int keycode;
        bool shouldShift;
    };

    /**
     * @brief Base class to handle all input events.
     * Specialized os classes are dervived from this.
     */
    class Base
    {
        public:
            void execute(const char* bytes, size_t size) const
            {
                switch(bytes[0])
                {
                    case InputManager::MOVE:
                        move(-(int)bytes[1], -(int)bytes[2]);
                        break;
                    
                    case InputManager::LCLICK:
                        click();
                        break;

                    case InputManager::RCLICK:
                        right_click();
                        break;

                    case InputManager::SCROLL:
                        scroll(bytes[1]);
                        break;

                    case InputManager::SCROLL_H:
                        scroll_h(bytes[1]);
                        break;

                    case InputManager::DOWN:
                        down();
                        break;

                    case InputManager::UP:
                        up();
                        break;

                    case InputManager::KEYPRESS:
                        for(int i = 1; i < size; i++)
                            send_key(bytes[i]);
                        break;

                    case InputManager::ZOOM:
                        zoom(bytes[1]);
                        break;
                }
            }

            virtual std::pair<int, bool> getFromKeyMap(char c) const = 0;

        private:
            virtual int parse_char(char c, bool &shiftPressed) const = 0;

            virtual void click() const = 0;
            virtual void right_click() const = 0;
            virtual void move(int dx, int dy) const = 0;
            virtual void scroll(int dy) const = 0;
            virtual void scroll_h(int dx) const = 0;
            virtual void down() const = 0;
            virtual void up() const  = 0;
            virtual void send_key(char c) const = 0;
            virtual void zoom(int scale) const = 0;
    };
}
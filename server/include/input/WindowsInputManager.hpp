#pragma once

#ifdef _WIN32

#include "InputManager.hpp"
#include <windows.h>
#include <map>

namespace InputManager
{
    /**
     * @brief Class for handling input events on windows.
     */
   class Windows : public Base
    {
        public:
            Windows();
            ~Windows();
            
        private:
            virtual void click() const override;
            virtual void right_click() const override;
            virtual void move(int dx, int dy) const override;
            virtual void scroll(int dy) const override;
            virtual void scroll_h(int dx) const override;
            virtual void down() const override;
            virtual void up() const override;
            virtual void send_key(uint8_t keycode, uint8_t modifier) const override;
            virtual void zoom(int scale) const override;
    };
}

#endif
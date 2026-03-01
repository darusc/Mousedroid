#ifndef LINUX_INPUT_MANAGER_H
#define LINUX_INPUT_MANAGER_H

#ifdef __unix__

#include "input/inputmanager.hpp"

#include <linux/uinput.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

namespace InputManager
{
    class Linux : public Base
    {
        public:
            Linux();

        private:
            int fd;
            uinput_setup usetup;

            void emit(int fd, int type, int code, int val) const;

            virtual void click() const override;
            virtual void right_click() const override;
            virtual void move(int dx, int dy) const override;
            virtual void scroll(int dy) const override;
            virtual void scroll_h(int dx) const override;
            virtual void down() const override;
            virtual void up() const override;
            virtual void send_key(uint8_t keycode, uint8_t modifier) const override;
            virtual void zoom(int scale) const override;
            virtual void media(uint8_t action) const override;
    };
}

#endif
#endif
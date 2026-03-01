#ifdef __unix__

#include "input/linux/linuxinputmanager.h"
#include "input/linux/keymap.h"

namespace InputManager
{
    int MOVE_SENSITIVITY = 1;
    int SCROLL_SENSITIVITY = 1;

    Linux::Linux()
    {
        fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
        if (fd < 0) {
            std::cerr << "[ERROR] Could not open /dev/uinput. Are you root? " 
                    << strerror(errno) << std::endl;
            return; 
        }

        ioctl(fd, UI_SET_EVBIT, EV_KEY);
        
        ioctl(fd, UI_SET_KEYBIT, BTN_LEFT);
        ioctl(fd, UI_SET_KEYBIT, BTN_RIGHT);
        ioctl(fd, UI_SET_KEYBIT, BTN_MIDDLE);

        for(const auto& [hid, code] : KEYMAP) {
            ioctl(fd, UI_SET_KEYBIT, code);
        }

        for(const auto& [bit, code] : MEDIAMAP) {
            ioctl(fd, UI_SET_KEYBIT, code);
        }

        for(const auto& [bit, code] : MODMAP) {
            ioctl(fd, UI_SET_KEYBIT, code);
        }

        ioctl(fd, UI_SET_EVBIT, EV_REL);
        ioctl(fd, UI_SET_RELBIT, REL_X);
        ioctl(fd, UI_SET_RELBIT, REL_Y);
        ioctl(fd, UI_SET_RELBIT, REL_WHEEL);
        ioctl(fd, UI_SET_RELBIT, REL_HWHEEL);

        ioctl(fd, UI_SET_EVBIT, EV_SYN);

        memset(&usetup, 0, sizeof(usetup));
        usetup.id.bustype = BUS_USB;
        usetup.id.vendor  = 0x1234; 
        usetup.id.product = 0x5678;
        strncpy(usetup.name, "Mousedroid", UINPUT_MAX_NAME_SIZE);

        if (ioctl(fd, UI_DEV_SETUP, &usetup) < 0) {
            std::cerr << "[ERROR] UI_DEV_SETUP failed." << std::endl;
        }
        if (ioctl(fd, UI_DEV_CREATE) < 0) {
            std::cerr << "[ERROR] UI_DEV_CREATE failed." << std::endl;
        }

        // Give the OS a moment to register the new HID device in the input stack
        usleep(100000);
    }

    void Linux::emit(int fd, int type, int code, int val) const
    {
        input_event ie;

        ie.type = type;
        ie.code = code;
        ie.value = val;
        ie.time.tv_sec = 0;
        ie.time.tv_usec = 0;

        write(fd, &ie, sizeof(ie));
    }

    void Linux::click() const
    {
        emit(fd, EV_KEY, BTN_LEFT, 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);
        emit(fd, EV_KEY, BTN_LEFT, 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::right_click() const
    {
        emit(fd, EV_KEY, BTN_RIGHT, 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);
        emit(fd, EV_KEY, BTN_RIGHT, 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::move(int dx, int dy) const
    {
        emit(fd, EV_REL, REL_X, (int)(MOVE_SENSITIVITY / 10.0 * dx));
        emit(fd, EV_REL, REL_Y, (int)(MOVE_SENSITIVITY / 10.0 * dy));
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::scroll(int scroll_amount) const
    {
        emit(fd, EV_KEY, KEY_LEFTSHIFT, 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);

        emit(fd, EV_REL, REL_WHEEL, (int)((scroll_amount * (SCROLL_SENSITIVITY / 10.0))));
        emit(fd, EV_SYN, SYN_REPORT, 0);
        
        emit(fd, EV_KEY, KEY_LEFTSHIFT, 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::scroll_h(int scroll_amount) const
    {
        emit(fd, EV_REL, REL_WHEEL, (int)((scroll_amount * (SCROLL_SENSITIVITY / 10.0))));
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::down() const
    {
        emit(fd, EV_KEY, BTN_LEFT, 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::up() const 
    {
        emit(fd, EV_KEY, BTN_LEFT, 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::send_key(uint8_t keycode, uint8_t modifier) const
    {
        // Extract modifier keys from the bitmask and add the to the input array
        for(const auto& mod : MODMAP)
        {
            if(modifier & mod.first)
            {
                emit(fd, EV_KEY, mod.second, 1);
            }
        }

        auto it = KEYMAP.find(keycode);
        if(it != KEYMAP.end())
        {
            emit(fd, EV_KEY, it->second, 1);
            emit(fd, EV_SYN, SYN_REPORT, 0);
            emit(fd, EV_KEY, it->second, 0);
        }

        // Release modifier keys in reverse order
        for (auto it_mod = MODMAP.rbegin(); it_mod != MODMAP.rend(); ++it_mod) 
        {
            if (modifier & it_mod->first) 
            {
                emit(fd, EV_KEY, it_mod->second, 0);
            }
        }

        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::zoom(int scale) const
    {
        emit(fd, EV_KEY, KEY_LEFTCTRL, 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);

        emit(fd, EV_REL, REL_WHEEL, scale);
        emit(fd, EV_SYN, SYN_REPORT, 0);
        
        emit(fd, EV_KEY, KEY_LEFTCTRL, 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }

    void Linux::media(uint8_t action) const
    {
        emit(fd, EV_KEY, MEDIAMAP.at(action), 1);
        emit(fd, EV_SYN, SYN_REPORT, 0);

        emit(fd, EV_KEY, MEDIAMAP.at(action), 0);
        emit(fd, EV_SYN, SYN_REPORT, 0);
    }
}

#endif
#ifdef _WIN32

#include "input/win32/windowsinputmanager.h"
#include "input/win32/keymap.h"

namespace InputManager
{
    int MOVE_SENSITIVITY = 1;
    int SCROLL_SENSITIVITY = 1;

    Windows::Windows() { }
    Windows::~Windows() { }

    void Windows::click() const
    {
        INPUT input = {};
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN | MOUSEEVENTF_LEFTUP;

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::right_click() const
    {
        INPUT input;
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN | MOUSEEVENTF_RIGHTUP;

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::move(int dx, int dy) const
    {
        INPUT input = {};
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_MOVE;
        input.mi.dx = (int)(MOVE_SENSITIVITY / 10.0 * dx);
        input.mi.dy = (int)(MOVE_SENSITIVITY / 10.0 * dy);

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::scroll(int scroll_amount) const
    {
        INPUT input = {};
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_WHEEL;
        input.mi.mouseData = (int)(10 * (scroll_amount * (SCROLL_SENSITIVITY / 10.0)));

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::scroll_h(int scroll_amount) const
    {
        INPUT inputs[3] = {};
        ZeroMemory(inputs, sizeof(inputs));
        
        inputs[0].type = INPUT_KEYBOARD;
        inputs[0].ki.wVk = VK_SHIFT;

        inputs[1].type = INPUT_MOUSE;
        inputs[1].mi.dwFlags = MOUSEEVENTF_WHEEL;
        inputs[1].mi.mouseData = (int)(10 * (scroll_amount * (SCROLL_SENSITIVITY / 10.0)));

        inputs[2].type = INPUT_KEYBOARD;
        inputs[2].ki.wVk = VK_SHIFT;
        inputs[2].ki.dwFlags = KEYEVENTF_KEYUP;

        SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
    }

    void Windows::down() const
    {
        INPUT input = {};
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::up() const
    {
        INPUT input = {};
        ZeroMemory(&input, sizeof(input));
        input.type = INPUT_MOUSE;
        input.mi.dwFlags = MOUSEEVENTF_LEFTUP;

        SendInput(1, &input, sizeof(INPUT));
    }

    void Windows::send_key(uint8_t keycode, uint8_t modifier) const
    {
        INPUT inputs[20];
        ZeroMemory(inputs, sizeof(inputs));
        int count = 0;

        // Extract modifier keys from the bitmask and add the to the input array
        for(const auto& mod : MODMAP)
        {
            if(modifier & mod.first)
            {
                inputs[count].type = INPUT_KEYBOARD;
                inputs[count].ki.wVk = mod.second;
                inputs[count].ki.dwFlags = 0;
                count++;
            }
        }

        auto it = KEYMAP.find(keycode);
        if(it != KEYMAP.end())
        {
            WORD vk = it->second;

            // Key Down
            inputs[count].type = INPUT_KEYBOARD;
            inputs[count].ki.wVk = vk;
            inputs[count].ki.dwFlags = 0;
            count++;

            // Key Up
            inputs[count].type = INPUT_KEYBOARD;
            inputs[count].ki.wVk = vk;
            inputs[count].ki.dwFlags = KEYEVENTF_KEYUP;
            count++;
        }

        // Release modifier keys in reverse order
        for (auto it_mod = MODMAP.rbegin(); it_mod != MODMAP.rend(); ++it_mod) 
        {
            if (modifier & it_mod->first) 
            {
                inputs[count].type = INPUT_KEYBOARD;
                inputs[count].ki.wVk = it_mod->second;
                inputs[count].ki.dwFlags = KEYEVENTF_KEYUP;
                count++;
            }
        }

        if(count > 0) {
            SendInput(count, inputs, sizeof(INPUT));
        }
    }

    void Windows::zoom(int scale) const
    {
        INPUT inputs[3] = {};
        ZeroMemory(inputs, sizeof(inputs));
        
        inputs[0].type = INPUT_KEYBOARD;
        inputs[0].ki.wVk = VK_CONTROL;

        inputs[1].type = INPUT_MOUSE;
        inputs[1].mi.dwFlags = MOUSEEVENTF_WHEEL;
        inputs[1].mi.mouseData = scale;

        inputs[2].type = INPUT_KEYBOARD;
        inputs[2].ki.wVk = VK_CONTROL;
        inputs[2].ki.dwFlags = KEYEVENTF_KEYUP;

        SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
    }

    void Windows::media(uint8_t action) const
    {
        INPUT inputs[2] = {};
		ZeroMemory(inputs, sizeof(inputs));

		inputs[0].type = INPUT_KEYBOARD;
		inputs[0].ki.wVk = MEDIAMAP.at(action);

		inputs[1].type = INPUT_KEYBOARD;
		inputs[1].ki.wVk = MEDIAMAP.at(action);
		inputs[1].ki.dwFlags = KEYEVENTF_KEYUP;

		SendInput(2, inputs, sizeof(INPUT));
    }
}

#endif
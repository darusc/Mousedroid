#ifdef _WIN32

#include "input/WindowsInputManager.hpp"

namespace InputManager
{
    int MOVE_SENSITIVITY = 1;
    int SCROLL_SENSITIVITY = 1;

    Windows::Windows() { }
    Windows::~Windows() { }

    std::pair<int, bool> Windows::getFromKeyMap(char c) const
    {
        for(auto k : KEYMAP)
        {
            if(k.c == c)
                return {k.keycode, k.shouldShift};
        }

        return {0, 0};
    }

    int Windows::parse_char(char c, bool &shiftPressed) const
    {
        shiftPressed = false;

        if(c >= '0' && c <= '9')
            return c;

        if(c >= 'a' && c <= 'z')
            return c - 32;

        if(c >= 'A' && c <= 'Z')
        {
            shiftPressed = true;
            return c;
        }

        else
        {
            auto res = getFromKeyMap(c);
            shiftPressed = res.second;
            return res.first;
        }
    }

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

    void Windows::send_key(char c) const
    {
        INPUT inputs[4] = {};
        ZeroMemory(inputs, sizeof(inputs));

        bool isShiftPressed;
        int keycode = parse_char(c, isShiftPressed);

        if(isShiftPressed)
        {
            inputs[0].type = INPUT_KEYBOARD;
            inputs[0].ki.wVk = VK_SHIFT;

            inputs[3].type = INPUT_KEYBOARD;
            inputs[3].ki.wVk = VK_SHIFT;
            inputs[3].ki.dwFlags = KEYEVENTF_KEYUP;
        }

        inputs[1].type = INPUT_KEYBOARD;
        inputs[1].ki.wVk = keycode;

        inputs[2].type = INPUT_KEYBOARD;
        inputs[2].ki.wVk = keycode;
        inputs[2].ki.dwFlags = KEYEVENTF_KEYUP;

        SendInput(ARRAYSIZE(inputs), inputs, sizeof(INPUT));
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
}

#endif
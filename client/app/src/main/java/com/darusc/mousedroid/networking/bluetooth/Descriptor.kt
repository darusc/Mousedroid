package com.darusc.mousedroid.networking.bluetooth

const val REPORT_ID_KEYBOARD = 0x01
const val REPORT_ID_MOUSE = 0x02

val HID_REPORT_DESC = byteArrayOf(
    // KEYBOARD
    0x05.toByte(), 0x01.toByte(),  // Usage Page (Generic Desktop Ctrls)
    0x09.toByte(), 0x06.toByte(),  // Usage (Keyboard)
    0xA1.toByte(), 0x01.toByte(),  // Collection (Application)
    0x85.toByte(), REPORT_ID_KEYBOARD.toByte(),  //   Report ID (1)
    0x05.toByte(), 0x07.toByte(),  //   Usage Page (Kbrd/Keypad)
    0x19.toByte(), 0xE0.toByte(),  //   Usage Minimum (0xE0 - Left Control)
    0x29.toByte(), 0xE7.toByte(),  //   Usage Maximum (0xE7 - Right GUI)
    0x15.toByte(), 0x00.toByte(),  //   Logical Minimum (0)
    0x25.toByte(), 0x01.toByte(),  //   Logical Maximum (1)
    0x75.toByte(), 0x01.toByte(),  //   Report Size (1)
    0x95.toByte(), 0x08.toByte(),  //   Report Count (8)
    0x81.toByte(), 0x02.toByte(),  //   Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
    0x95.toByte(), 0x01.toByte(),  //   Report Count (1)
    0x75.toByte(), 0x08.toByte(),  //   Report Size (8)
    0x81.toByte(), 0x03.toByte(),  //   Input (Const,Var,Abs) - Padding
    0x95.toByte(), 0x05.toByte(),  //   Report Count (5)
    0x75.toByte(), 0x01.toByte(),  //   Report Size (1)
    0x05.toByte(), 0x08.toByte(),  //   Usage Page (LEDs)
    0x19.toByte(), 0x01.toByte(),  //   Usage Minimum (Num Lock)
    0x29.toByte(), 0x05.toByte(),  //   Usage Maximum (Kana)
    0x91.toByte(), 0x02.toByte(),  //   Output (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position,Non-volatile)
    0x95.toByte(), 0x01.toByte(),  //   Report Count (1)
    0x75.toByte(), 0x03.toByte(),  //   Report Size (3)
    0x91.toByte(), 0x03.toByte(),  //   Output (Const,Var,Abs,No Wrap,Linear,Preferred State,No Null Position,Non-volatile) - Padding
    0x95.toByte(), 0x06.toByte(),  //   Report Count (6)
    0x75.toByte(), 0x08.toByte(),  //   Report Size (8)
    0x15.toByte(), 0x00.toByte(),  //   Logical Minimum (0)
    0x25.toByte(), 0x65.toByte(),  //   Logical Maximum (101)
    0x05.toByte(), 0x07.toByte(),  //   Usage Page (Kbrd/Keypad)
    0x19.toByte(), 0x00.toByte(),  //   Usage Minimum (0x00)
    0x29.toByte(), 0x65.toByte(),  //   Usage Maximum (0x65)
    0x81.toByte(), 0x00.toByte(),  //   Input (Data,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
    0xC0.toByte(),  // End Collection

    // MOUSE
    0x05.toByte(), 0x01.toByte(),  // Usage Page (Generic Desktop Ctrls)
    0x09.toByte(), 0x02.toByte(),  // Usage (Mouse)
    0xA1.toByte(), 0x01.toByte(),  // Collection (Application)
    0x85.toByte(), REPORT_ID_MOUSE.toByte(),  //   Report ID (2)
    0x09.toByte(), 0x01.toByte(),  //   Usage (Pointer)
    0xA1.toByte(), 0x00.toByte(),  //   Collection (Physical)
    0x05.toByte(), 0x09.toByte(),  //     Usage Page (Button)
    0x19.toByte(), 0x01.toByte(),  //     Usage Minimum (0x01)
    0x29.toByte(), 0x03.toByte(),  //     Usage Maximum (0x03)
    0x15.toByte(), 0x00.toByte(),  //     Logical Minimum (0)
    0x25.toByte(), 0x01.toByte(),  //     Logical Maximum (1)
    0x95.toByte(), 0x03.toByte(),  //     Report Count (3)
    0x75.toByte(), 0x01.toByte(),  //     Report Size (1)
    0x81.toByte(), 0x02.toByte(),  //     Input (Data,Var,Abs)
    0x95.toByte(), 0x01.toByte(),  //     Report Count (1)
    0x75.toByte(), 0x05.toByte(),  //     Report Size (5)
    0x81.toByte(), 0x03.toByte(),  //     Input (Const,Var,Abs)
    0x05.toByte(), 0x01.toByte(),  //     Usage Page (Generic Desktop Ctrls)
    0x09.toByte(), 0x30.toByte(),  //     Usage (X)
    0x09.toByte(), 0x31.toByte(),  //     Usage (Y)
    0x09.toByte(), 0x38.toByte(),  //     Usage (Wheel)
    0x15.toByte(), 0x81.toByte(),  //     Logical Minimum (-127)
    0x25.toByte(), 0x7F.toByte(),  //     Logical Maximum (127)
    0x75.toByte(), 0x08.toByte(),  //     Report Size (8)
    0x95.toByte(), 0x03.toByte(),  //     Report Count (3)
    0x81.toByte(), 0x06.toByte(),  //     Input (Data,Var,Rel)
    0xC0.toByte(),  //   End Collection
    0xC0.toByte() // End Collection
)
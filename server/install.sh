#!/bin/bash

echo "INSTALLING TO /usr/share/mousedroid..."
sudo mkdir -p /usr/share/mousedroid

# Copy everything from our local dist to the system folder
sudo cp -r mousedroid_linux/* /usr/share/mousedroid/

echo "SETTING PERMISSIONS..."
sudo chmod -R +r /usr/share/mousedroid
sudo chmod +x /usr/share/mousedroid/Mousedroid

echo "CREATING DESKTOP ENTRY..."
# Using 'tee' allows sudo to write the file correctly
sudo tee /usr/share/applications/mousedroid.desktop > /dev/null <<EOF
[Desktop Entry]
Name=Mousedroid
Exec=/usr/share/mousedroid/Mousedroid
Icon=/usr/share/mousedroid/icon.png
Type=Application
Terminal=false
Categories=Utility;
EOF

sudo chmod +r /usr/share/applications/mousedroid.desktop

echo "SETTING UDEV RULES..."
echo 'KERNEL=="uinput", TAG+="uaccess"' | sudo tee /etc/udev/rules.d/50-uinput.rules > /dev/null

sudo udevadm control --reload-rules && sudo udevadm trigger

echo "DONE! You can now find Mousedroid in your application menu."
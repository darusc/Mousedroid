#!/bin/bash

echo "CREATING LOCAL DIST FOLDER..."
mkdir -p mousedroid_linux

# Copy binary and assets to local dist
cp cmake/bin/Mousedroid mousedroid_linux/
cp icon.png mousedroid_linux/

# Generate config.ini inside dist
cat <<EOF > mousedroid_linux/config.ini
MINIMIZE_TASKBAR=0
MOVE_SENSITIVITY=10
RUN_STARTUP=0
SCROLL_SENSITIVITY=3
EOF

#ifndef DEVICE_HPP
#define DEVICE_HPP

#include <string>

enum connection_type
{
    USB = 0,
    WIFI = 1
};

struct DeviceInfo
{
    int ID;
    std::string Address;
    std::string Manufacturer;
    std::string Name;
    std::string Model;
    int ctype;
};

#endif
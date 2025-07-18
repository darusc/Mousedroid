#pragma once

#include <wx/listctrl.h>
#ifdef _WIN32
    #include <wx/systhemectrl.h>
#endif 

#include <vector>

#include "net/Device.hpp"

class wxDeviceList: public wxListView
{
    public:
        wxDeviceList(wxWindow *parent, int id);
        ~wxDeviceList();

        void SetDevices(const std::vector<DeviceInfo>& devs);
        
    private:
        std::vector<DeviceInfo> devices;

        void UpdateView(bool refresh = false);
        void InsertDevice(int i, DeviceInfo &device);
};
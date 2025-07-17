#pragma once

#include "wx/wxprec.h"

#ifndef WX_PRECOMP
    #include "wx/wx.h"
#endif

#include "wxMain.hpp"
#include "net/Server.hpp"
#include "SettingsManager.hpp"
#include "Logger.hpp"

class wxApplication : public wxApp, public Server::ConnectionListener
{
    public:
        wxApplication();

        virtual bool OnInit() override;
        // virtual int OnExit() override;

        virtual void OnDeviceConnected(std::string device) const override;
        virtual void OnDeviceDisconnected(std::string device) const override;
        
    private:
        wxMain *main_frame = nullptr;
        Server *server = nullptr;

        INPUT_MANAGER inputManager;
        SettingsManager settings;

        void OnWindowCloseEvent(wxCloseEvent &evt);
};
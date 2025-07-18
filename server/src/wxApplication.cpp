#include "wxApplication.hpp"

wxIMPLEMENT_APP(wxApplication);

#include "icon.xpm"

wxApplication::wxApplication() { }

bool wxApplication::OnInit()
{        
    main_frame = new wxMain(settings);
    
    server = new Server(6969, *this, settings, inputManager);

    Server::HostInfo hostInfo = server->GetHostInfo();
    
    main_frame->Bind(wxEVT_CLOSE_WINDOW, &wxApplication::OnWindowCloseEvent, this);
    main_frame->SetHostInfo(std::get<0>(hostInfo), std::get<1>(hostInfo));

    if(!settings.GetRunAtStartup())
        main_frame->Show();
    
    server->Start();

    main_frame->UpdateUI();

    return true;
}

void wxApplication::OnDeviceConnected(std::string device) const
{
    auto devices = server->GetConnectedDevicesInfo();
    main_frame->wxdevlist->SetDevices(devices);
}

void wxApplication::OnDeviceDisconnected(std::string device) const
{
	auto devices = server->GetConnectedDevicesInfo();
    main_frame->wxdevlist->SetDevices(devices);
}

void wxApplication::OnWindowCloseEvent(wxCloseEvent &evt)
{
    wxMessageDialog *box = new wxMessageDialog(main_frame, "This will disconnect all connected devices. Proceed?", "Confirm", wxYES_NO | wxICON_INFORMATION);

    int res = box->ShowModal();

    if(res == wxID_YES)
    {
        server->Close();
        Logger::monitor->Destroy();
        main_frame->Destroy();
    }
}

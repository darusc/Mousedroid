cmake_minimum_required(VERSION 3.26)
project(Mousedroid)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED TRUE)

if(MSVC)
    add_compile_options(/Zc:preprocessor)
endif()

if(WIN32)
    # On Windows, vcpkg handles wxWidgets and Asio automatically.
    # find_package(asio CONFIG) is the vcpkg standard for Asio.
    find_package(asio CONFIG REQUIRED)
    find_package(wxWidgets REQUIRED net gl core base adv)
    
    # Define Windows-specific libraries (added user32 for SendInput)
    set(PLATFORM_LIBS ws2_32 wsock32 user32)
    
    # MSVC vs MinGW resource handling
    if(MSVC)
        set(PLATFORM_SRC "src/input/win32/windowsinputmanager.cpp" resource.rc)
    else()
        set(PLATFORM_SRC "src/input/win32/windowsinputmanager.cpp" resource.o)
        set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wl,-subsystem,windows")
    endif()
else()
    # Linux: Use system-installed wxWidgets
    find_package(wxWidgets REQUIRED net gl core base adv)
    set(PLATFORM_SRC "src/input/linux/linuxinputmanager.cpp")
    set(PLATFORM_LIBS "")
    
    # Manual ASIO path for Linux
    set(ASIO_INCLUDE_DIR "${ASIO_PATH}/include") 
endif()

# Collect all sources but avoid double-including input managers
file(GLOB_RECURSE ALL_SRC src/*.cpp)

if(WIN32)
    set(PLATFORM_SRC "src/input/win32/windowsinputmanager.cpp")
else()
    set(PLATFORM_SRC "src/input/linux/linuxinputmanager.cpp")
    set(PLATFORM_LIBS "")
endif()

list(REMOVE_ITEM ALL_SRC ${PLATFORM_SRC})

add_executable(Mousedroid WIN32 ${ALL_SRC} ${PLATFORM_SRC})



if(wxWidgets_FOUND)
    include(${wxWidgets_USE_FILE})
    target_link_libraries(Mousedroid PRIVATE ${wxWidgets_LIBRARIES})
endif()

target_link_libraries(Mousedroid PRIVATE ${PLATFORM_LIBS})

if(WIN32)
    # vcpkg-installed asio provides an imported target
    target_link_libraries(Mousedroid PRIVATE asio::asio)
else()
    target_include_directories(Mousedroid PRIVATE ./src ${ASIO_INCLUDE_DIR})
endif()

target_include_directories(Mousedroid PRIVATE ./src)
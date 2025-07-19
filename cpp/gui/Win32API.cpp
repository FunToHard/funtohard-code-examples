/**
 * Win32 API Programming Example
 * 
 * This example demonstrates basic Windows GUI programming using the Win32 API.
 * It creates a simple window with a button and handles window messages.
 */

#include <windows.h>

// Global variables
HINSTANCE hInst;
const wchar_t* WINDOW_CLASS = L"Win32App";
const wchar_t* WINDOW_TITLE = L"Win32 API Example";

// Forward declarations
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

int WINAPI wWinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, PWSTR pCmdLine, int nCmdShow) {
    // Store instance handle
    hInst = hInstance;
    
    // Register the window class
    WNDCLASSW wc = {0};
    wc.lpfnWndProc = WndProc;
    wc.hInstance = hInstance;
    wc.lpszClassName = WINDOW_CLASS;
    wc.hCursor = LoadCursor(nullptr, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    
    if (!RegisterClassW(&wc)) {
        MessageBoxW(nullptr, L"Window Registration Failed!", L"Error!", MB_ICONERROR | MB_OK);
        return 0;
    }
    
    // Create the window
    HWND hwnd = CreateWindowW(
        WINDOW_CLASS,
        WINDOW_TITLE,
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT, 400, 300,
        nullptr, nullptr, hInstance, nullptr
    );
    
    if (!hwnd) {
        MessageBoxW(nullptr, L"Window Creation Failed!", L"Error!", MB_ICONERROR | MB_OK);
        return 0;
    }
    
    // Create a button
    HWND hButton = CreateWindowW(
        L"BUTTON",
        L"Click Me!",
        WS_TABSTOP | WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
        150, 100, 100, 30,
        hwnd,
        (HMENU)1,
        hInstance,
        nullptr
    );
    
    // Show and update the window
    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);
    
    // Message loop
    MSG msg = {0};
    while (GetMessage(&msg, nullptr, 0, 0)) {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    
    return (int)msg.wParam;
}

// Window procedure
LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam) {
    switch (msg) {
        case WM_COMMAND:
            if (LOWORD(wParam) == 1) {  // Button clicked
                MessageBoxW(hwnd, L"Hello, Win32 API!", L"Message", MB_OK | MB_ICONINFORMATION);
            }
            break;
            
        case WM_CLOSE:
            if (MessageBoxW(hwnd, L"Are you sure you want to exit?", L"Exit", MB_OKCANCEL | MB_ICONQUESTION) == IDOK) {
                DestroyWindow(hwnd);
            }
            break;
            
        case WM_DESTROY:
            PostQuitMessage(0);
            break;
            
        default:
            return DefWindowProcW(hwnd, msg, wParam, lParam);
    }
    return 0;
}

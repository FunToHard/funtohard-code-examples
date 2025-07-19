/**
 * FLTK Applications Example
 * 
 * This example demonstrates the Fast Light Toolkit (FLTK) for C++ GUI development.
 * It creates a simple window with buttons and demonstrates event handling.
 */

#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Box.H>
#include <FL/fl_ask.H>

// Global variables
Fl_Box *label;
Fl_Button *clickButton;
Fl_Button *exitButton;
int clickCount = 0;

// Button callback function
void button_cb(Fl_Widget *w, void *data) {
    if (w == clickButton) {
        clickCount++;
        char buffer[50];
        snprintf(buffer, sizeof(buffer), "Button clicked %d times", clickCount);
        label->copy_label(buffer);
    } else if (w == exitButton) {
        if (fl_choice("Are you sure you want to quit?", "No", "Yes", 0)) {
            exitButton->window()->hide();
        }
    }
}

int main(int argc, char **argv) {
    // Create the main window
    Fl_Window *window = new Fl_Window(400, 300, "FLTK Example");
    window->begin();
    
    // Create a label
    label = new Fl_Box(FL_DOWN_BOX, 100, 50, 200, 40, "Click the button!");
    label->align(FL_ALIGN_CENTER | FL_ALIGN_INSIDE);
    
    // Create buttons
    clickButton = new Fl_Button(150, 120, 100, 30, "Click Me!");
    clickButton->callback(button_cb);
    
    exitButton = new Fl_Button(150, 170, 100, 30, "Exit");
    exitButton->callback(button_cb);
    
    // Set window properties
    window->end();
    window->show(argc, argv);
    
    // Run the application
    return Fl::run();
}

/**
 * GTK+ Development Example
 * 
 * This example demonstrates basic GTK+ programming with C++ using gtkmm.
 * It shows how to create a simple window with buttons and handle events.
 */

#include <gtkmm/application.h>
#include <gtkmm/window.h>
#include <gtkmm/button.h>
#include <gtkmm/box.h>
#include <gtkmm/label.h>
#include <gtkmm/messagedialog.h>

class ExampleWindow : public Gtk::Window {
public:
    ExampleWindow() {
        // Set window properties
        set_title("GTK+ Example");
        set_default_size(400, 300);
        
        // Create main container
        Gtk::Box *vbox = Gtk::manage(new Gtk::Box(Gtk::ORIENTATION_VERTICAL, 5));
        add(*vbox);
        
        // Create and pack widgets
        Gtk::Label *label = Gtk::manage(new Gtk::Label("Welcome to GTK+ Development"));
        vbox->pack_start(*label, Gtk::PACK_SHRINK);
        
        // Create buttons
        Gtk::Button *button1 = Gtk::manage(new Gtk::Button("Click Me!"));
        Gtk::Button *button2 = Gtk::manage(new Gtk::Button("Exit"));
        
        // Pack buttons
        vbox->pack_start(*button1, Gtk::PACK_SHRINK);
        vbox->pack_start(*button2, Gtk::PACK_SHRINK);
        
        // Connect button signals
        button1->signal_clicked().connect(sigc::mem_fun(*this, &ExampleWindow::on_button1_clicked));
        button2->signal_clicked().connect(sigc::mem_fun(*this, &ExampleWindow::on_button2_clicked));
        
        // Show all children of the container
        vbox->show_all();
    }
    
    virtual ~ExampleWindow() {}
    
protected:
    // Signal handlers
    void on_button1_clicked() {
        Gtk::MessageDialog dialog(*this, "Button Clicked!");
        dialog.set_secondary_text("You clicked the button!");
        dialog.run();
    }
    
    void on_button2_clicked() {
        hide(); // Close the window and end the program
    }
};

int main(int argc, char *argv[]) {
    // Create the application
    auto app = Gtk::Application::create(argc, argv, "org.gtkmm.example");
    
    // Create and show the window
    ExampleWindow window;
    
    // Run the application
    return app->run(window);
}

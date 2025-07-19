/**
 * Qt Framework Basics
 * 
 * This example demonstrates the basics of Qt framework including:
 * - Creating a main window
 * - Adding widgets (buttons, labels, layouts)
 * - Signal-slot connections
 * - Event handling
 */

#include <QApplication>
#include <QMainWindow>
#include <QPushButton>
#include <QLabel>
#include <QVBoxLayout>
#include <QMessageBox>

class MainWindow : public QMainWindow {
    Q_OBJECT
    
public:
    explicit MainWindow(QWidget *parent = nullptr) : QMainWindow(parent) {
        // Set window properties
        setWindowTitle("Qt Basics Example");
        setMinimumSize(400, 300);
        
        // Create central widget and layout
        QWidget *centralWidget = new QWidget(this);
        QVBoxLayout *mainLayout = new QVBoxLayout(centralWidget);
        
        // Create widgets
        QLabel *titleLabel = new QLabel("Welcome to Qt Basics", this);
        titleLabel->setAlignment(Qt::AlignCenter);
        
        QPushButton *clickMeButton = new QPushButton("Click Me!", this);
        QPushButton *exitButton = new QPushButton("Exit", this);
        
        // Add widgets to layout
        mainLayout->addWidget(titleLabel);
        mainLayout->addWidget(clickMeButton);
        mainLayout->addWidget(exitButton);
        mainLayout->addStretch();
        
        // Set the central widget
        setCentralWidget(centralWidget);
        
        // Connect signals to slots
        connect(clickMeButton, &QPushButton::clicked, this, &MainWindow::onClickMe);
        connect(exitButton, &QPushButton::clicked, this, &MainWindow::close);
        
        // Custom signal-slot connection with lambda
        connect(clickMeButton, &QPushButton::clicked, [=]() {
            clickMeButton->setText("Clicked!");
            clickMeButton->setEnabled(false);
        });
    }
    
private slots:
    void onClickMe() {
        QMessageBox::information(this, "Hello", "Button was clicked!");
    }
};

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    
    // Set application style
    app.setStyle("Fusion");
    
    // Create and show the main window
    MainWindow window;
    window.show();
    
    return app.exec();
}

// Include the generated moc file
#include "QtBasics.moc"

// Qt.cpp - Qt Framework Examples
// Cross-platform application development framework for desktop, mobile and embedded.

#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QMessageBox>
#include <QtCore/QTimer>
#include <QtCore/QDateTime>
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkRequest>
#include <QtNetwork/QNetworkReply>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include <iostream>

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr) : QMainWindow(parent) {
        setupUI();
        setupConnections();
        setupTimer();
        setupNetworking();
        
        std::cout << "=== QT FRAMEWORK EXAMPLES ===" << std::endl;
        std::cout << "Qt application initialized successfully!" << std::endl;
    }

private slots:
    void onButtonClicked() {
        QString text = lineEdit->text();
        if (!text.isEmpty()) {
            textEdit->append("You entered: " + text);
            lineEdit->clear();
            statusBar()->showMessage("Text processed: " + text, 2000);
        } else {
            QMessageBox::warning(this, "Warning", "Please enter some text!");
        }
    }
    
    void onClearClicked() {
        textEdit->clear();
        statusBar()->showMessage("Text cleared", 1000);
    }
    
    void updateTime() {
        QString currentTime = QDateTime::currentDateTime().toString("hh:mm:ss");
        timeLabel->setText("Current Time: " + currentTime);
    }
    
    void onNetworkReply(QNetworkReply* reply) {
        if (reply->error() == QNetworkReply::NoError) {
            QByteArray data = reply->readAll();
            QJsonDocument doc = QJsonDocument::fromJson(data);
            QJsonObject obj = doc.object();
            
            textEdit->append("Network Response:");
            textEdit->append(QString::fromUtf8(data));
        } else {
            textEdit->append("Network Error: " + reply->errorString());
        }
        reply->deleteLater();
    }
    
    void makeNetworkRequest() {
        QNetworkRequest request(QUrl("https://api.github.com/users/octocat"));
        request.setHeader(QNetworkRequest::UserAgentHeader, "Qt Application");
        
        QNetworkReply* reply = networkManager->get(request);
        connect(reply, &QNetworkReply::finished, [this, reply]() {
            onNetworkReply(reply);
        });
        
        statusBar()->showMessage("Making network request...", 3000);
    }

private:
    void setupUI() {
        // Central widget and layout
        QWidget* centralWidget = new QWidget(this);
        setCentralWidget(centralWidget);
        
        QVBoxLayout* mainLayout = new QVBoxLayout(centralWidget);
        
        // Title label
        QLabel* titleLabel = new QLabel("Qt Framework Demo Application");
        titleLabel->setStyleSheet("font-size: 18px; font-weight: bold; color: #2c3e50;");
        titleLabel->setAlignment(Qt::AlignCenter);
        mainLayout->addWidget(titleLabel);
        
        // Input section
        QHBoxLayout* inputLayout = new QHBoxLayout();
        
        QLabel* inputLabel = new QLabel("Enter text:");
        lineEdit = new QLineEdit();
        lineEdit->setPlaceholderText("Type something here...");
        
        inputLayout->addWidget(inputLabel);
        inputLayout->addWidget(lineEdit);
        mainLayout->addLayout(inputLayout);
        
        // Buttons section
        QHBoxLayout* buttonLayout = new QHBoxLayout();
        
        submitButton = new QPushButton("Submit");
        submitButton->setStyleSheet("QPushButton { background-color: #3498db; color: white; padding: 8px; border-radius: 4px; }");
        
        clearButton = new QPushButton("Clear");
        clearButton->setStyleSheet("QPushButton { background-color: #e74c3c; color: white; padding: 8px; border-radius: 4px; }");
        
        networkButton = new QPushButton("Network Request");
        networkButton->setStyleSheet("QPushButton { background-color: #2ecc71; color: white; padding: 8px; border-radius: 4px; }");
        
        buttonLayout->addWidget(submitButton);
        buttonLayout->addWidget(clearButton);
        buttonLayout->addWidget(networkButton);
        mainLayout->addLayout(buttonLayout);
        
        // Text display area
        textEdit = new QTextEdit();
        textEdit->setReadOnly(true);
        textEdit->setStyleSheet("QTextEdit { border: 1px solid #bdc3c7; border-radius: 4px; }");
        mainLayout->addWidget(textEdit);
        
        // Time display
        timeLabel = new QLabel("Current Time: --:--:--");
        timeLabel->setStyleSheet("font-family: monospace; color: #7f8c8d;");
        timeLabel->setAlignment(Qt::AlignCenter);
        mainLayout->addWidget(timeLabel);
        
        // Setup menu bar
        setupMenuBar();
        
        // Setup status bar
        statusBar()->showMessage("Ready", 2000);
        
        // Window properties
        setWindowTitle("Qt Framework Examples");
        setMinimumSize(600, 500);
        resize(800, 600);
    }
    
    void setupMenuBar() {
        QMenuBar* menuBar = this->menuBar();
        
        // File menu
        QMenu* fileMenu = menuBar->addMenu("&File");
        
        QAction* newAction = fileMenu->addAction("&New");
        newAction->setShortcut(QKeySequence::New);
        connect(newAction, &QAction::triggered, [this]() {
            textEdit->clear();
            statusBar()->showMessage("New document", 1000);
        });
        
        fileMenu->addSeparator();
        
        QAction* exitAction = fileMenu->addAction("E&xit");
        exitAction->setShortcut(QKeySequence::Quit);
        connect(exitAction, &QAction::triggered, this, &QWidget::close);
        
        // Edit menu
        QMenu* editMenu = menuBar->addMenu("&Edit");
        
        QAction* copyAction = editMenu->addAction("&Copy");
        copyAction->setShortcut(QKeySequence::Copy);
        connect(copyAction, &QAction::triggered, [this]() {
            textEdit->copy();
            statusBar()->showMessage("Copied to clipboard", 1000);
        });
        
        QAction* selectAllAction = editMenu->addAction("Select &All");
        selectAllAction->setShortcut(QKeySequence::SelectAll);
        connect(selectAllAction, &QAction::triggered, [this]() {
            textEdit->selectAll();
        });
        
        // Help menu
        QMenu* helpMenu = menuBar->addMenu("&Help");
        
        QAction* aboutAction = helpMenu->addAction("&About");
        connect(aboutAction, &QAction::triggered, [this]() {
            QMessageBox::about(this, "About", 
                "Qt Framework Examples\n\n"
                "This application demonstrates various Qt features:\n"
                "• GUI components and layouts\n"
                "• Event handling and signals/slots\n"
                "• Timers and networking\n"
                "• Menus and status bars\n\n"
                "Built with Qt Framework");
        });
    }
    
    void setupConnections() {
        // Connect button signals to slots
        connect(submitButton, &QPushButton::clicked, this, &MainWindow::onButtonClicked);
        connect(clearButton, &QPushButton::clicked, this, &MainWindow::onClearClicked);
        connect(networkButton, &QPushButton::clicked, this, &MainWindow::makeNetworkRequest);
        
        // Connect line edit return key to submit
        connect(lineEdit, &QLineEdit::returnPressed, this, &MainWindow::onButtonClicked);
        
        std::cout << "Signal-slot connections established" << std::endl;
    }
    
    void setupTimer() {
        // Create timer for updating time display
        timer = new QTimer(this);
        connect(timer, &QTimer::timeout, this, &MainWindow::updateTime);
        timer->start(1000); // Update every second
        
        // Initial time update
        updateTime();
        
        std::cout << "Timer initialized" << std::endl;
    }
    
    void setupNetworking() {
        networkManager = new QNetworkAccessManager(this);
        std::cout << "Network manager initialized" << std::endl;
    }

private:
    // UI components
    QLineEdit* lineEdit;
    QPushButton* submitButton;
    QPushButton* clearButton;
    QPushButton* networkButton;
    QTextEdit* textEdit;
    QLabel* timeLabel;
    
    // Other components
    QTimer* timer;
    QNetworkAccessManager* networkManager;
};

// Custom widget example
class CustomWidget : public QWidget {
    Q_OBJECT

public:
    CustomWidget(QWidget* parent = nullptr) : QWidget(parent) {
        setFixedSize(200, 100);
        setStyleSheet("background-color: #ecf0f1; border: 2px solid #34495e; border-radius: 8px;");
    }

protected:
    void paintEvent(QPaintEvent* event) override {
        QPainter painter(this);
        painter.setRenderHint(QPainter::Antialiasing);
        
        // Draw custom graphics
        painter.setPen(QPen(Qt::blue, 2));
        painter.setBrush(QBrush(Qt::yellow));
        painter.drawEllipse(20, 20, 60, 60);
        
        painter.setPen(QPen(Qt::red, 3));
        painter.drawText(100, 50, "Custom\nWidget");
    }
    
    void mousePressEvent(QMouseEvent* event) override {
        if (event->button() == Qt::LeftButton) {
            QMessageBox::information(this, "Custom Widget", "Custom widget clicked!");
        }
    }
};

// Application class
class QtExampleApp : public QApplication {
public:
    QtExampleApp(int argc, char* argv[]) : QApplication(argc, argv) {
        setApplicationName("Qt Framework Examples");
        setApplicationVersion("1.0.0");
        setOrganizationName("Example Organization");
        
        std::cout << "Qt Application created" << std::endl;
        std::cout << "Qt Version: " << QT_VERSION_STR << std::endl;
    }
    
    void showExamples() {
        // Main window
        MainWindow* mainWindow = new MainWindow();
        mainWindow->show();
        
        // Custom widget window
        CustomWidget* customWidget = new CustomWidget();
        customWidget->setWindowTitle("Custom Widget Example");
        customWidget->show();
        customWidget->move(mainWindow->x() + mainWindow->width() + 20, mainWindow->y());
        
        std::cout << "Example windows displayed" << std::endl;
    }
};

int main(int argc, char *argv[]) {
    std::cout << "=== QT FRAMEWORK EXAMPLES ===" << std::endl;
    std::cout << "Starting Qt application..." << std::endl;
    
    QtExampleApp app(argc, argv);
    
    // Set application style
    app.setStyle("Fusion");
    
    // Set application palette for dark theme (optional)
    QPalette darkPalette;
    darkPalette.setColor(QPalette::Window, QColor(53, 53, 53));
    darkPalette.setColor(QPalette::WindowText, Qt::white);
    darkPalette.setColor(QPalette::Base, QColor(25, 25, 25));
    darkPalette.setColor(QPalette::AlternateBase, QColor(53, 53, 53));
    darkPalette.setColor(QPalette::ToolTipBase, Qt::white);
    darkPalette.setColor(QPalette::ToolTipText, Qt::white);
    darkPalette.setColor(QPalette::Text, Qt::white);
    darkPalette.setColor(QPalette::Button, QColor(53, 53, 53));
    darkPalette.setColor(QPalette::ButtonText, Qt::white);
    darkPalette.setColor(QPalette::BrightText, Qt::red);
    darkPalette.setColor(QPalette::Link, QColor(42, 130, 218));
    darkPalette.setColor(QPalette::Highlight, QColor(42, 130, 218));
    darkPalette.setColor(QPalette::HighlightedText, Qt::black);
    
    // Uncomment to apply dark theme
    // app.setPalette(darkPalette);
    
    // Show example windows
    app.showExamples();
    
    std::cout << "Entering Qt event loop..." << std::endl;
    return app.exec();
}

#include "Qt.moc"

/*
=== QT FEATURES DEMONSTRATED ===

1. GUI Components:
   - QMainWindow with menu bar and status bar
   - QLabel, QPushButton, QLineEdit, QTextEdit
   - Custom layouts (QVBoxLayout, QHBoxLayout)
   - Custom widget with painting and mouse events

2. Event Handling:
   - Signal-slot connections
   - Button clicks and keyboard events
   - Mouse events and custom event handling

3. Timers and Threading:
   - QTimer for periodic updates
   - Real-time clock display

4. Networking:
   - QNetworkAccessManager for HTTP requests
   - JSON parsing with QJsonDocument
   - Asynchronous network operations

5. Styling and Theming:
   - CSS-like stylesheets
   - Custom color palettes
   - Application-wide styling

6. Menus and Actions:
   - Menu bar with File, Edit, Help menus
   - Keyboard shortcuts
   - Action handling

7. Message Boxes:
   - Information, warning, and about dialogs
   - User interaction and feedback

=== COMPILATION ===

# Using qmake (recommended)
qmake -project
qmake
make

# Or using CMake
cmake_minimum_required(VERSION 3.16)
project(QtExample)
find_package(Qt6 REQUIRED COMPONENTS Core Widgets Network)
qt_standard_project_setup()
qt_add_executable(QtExample Qt.cpp)
qt_add_resources(QtExample "resources")
target_link_libraries(QtExample Qt6::Core Qt6::Widgets Qt6::Network)

# Or direct compilation (not recommended for complex projects)
g++ -std=c++17 Qt.cpp -o qt_example `pkg-config --cflags --libs Qt6Core Qt6Widgets Qt6Network`

=== PROJECT STRUCTURE ===

QtExample/
├── main.cpp (this file)
├── CMakeLists.txt or .pro file
├── resources/
│   ├── icons/
│   └── images/
└── translations/
    └── qtexample_en.ts

=== COMMON QT MODULES ===

- QtCore: Core functionality (QObject, QString, QTimer, etc.)
- QtWidgets: GUI components for desktop applications
- QtNetwork: Network programming (HTTP, TCP, UDP)
- QtSql: Database integration
- QtMultimedia: Audio, video, camera support
- QtWebEngine: Web browser functionality
- QtQuick: Declarative UI with QML
- QtTest: Unit testing framework
- QtConcurrent: High-level threading APIs

=== QT DESIGN PATTERNS ===

1. Model-View Architecture:
   - Separation of data and presentation
   - QAbstractItemModel, QListView, QTableView

2. Signal-Slot Mechanism:
   - Type-safe callback system
   - Loose coupling between objects

3. Object Tree and Memory Management:
   - Parent-child relationships
   - Automatic cleanup

4. Meta-Object System:
   - Runtime type information
   - Property system and reflection

*/

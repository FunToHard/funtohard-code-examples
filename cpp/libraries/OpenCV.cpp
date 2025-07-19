// OpenCV.cpp - Computer Vision Library Examples
// Image processing, computer vision, and machine learning library.

#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/objdetect.hpp>
#include <iostream>
#include <vector>

using namespace cv;
using namespace std;

int main() {
    cout << "=== OPENCV COMPUTER VISION EXAMPLES ===" << endl;
    cout << "OpenCV Version: " << CV_VERSION << endl;
    
    // 1. Basic Image Operations
    basicImageOperations();
    
    // 2. Image Processing
    imageProcessing();
    
    // 3. Feature Detection
    featureDetection();
    
    // 4. Object Detection
    objectDetection();
    
    // 5. Video Processing
    videoProcessing();
    
    return 0;
}

void basicImageOperations() {
    cout << "\n1. Basic Image Operations:" << endl;
    
    // Create a simple image
    Mat image = Mat::zeros(400, 600, CV_8UC3);
    
    // Draw shapes
    rectangle(image, Point(50, 50), Point(200, 150), Scalar(0, 255, 0), 2);
    circle(image, Point(300, 100), 50, Scalar(255, 0, 0), -1);
    line(image, Point(400, 50), Point(550, 150), Scalar(0, 0, 255), 3);
    
    // Add text
    putText(image, "OpenCV Demo", Point(50, 300), 
            FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2);
    
    cout << "Created image with shapes and text" << endl;
    cout << "Image size: " << image.size() << endl;
    cout << "Image channels: " << image.channels() << endl;
    
    // Save image
    imwrite("opencv_demo.jpg", image);
    cout << "Image saved as opencv_demo.jpg" << endl;
}

void imageProcessing() {
    cout << "\n2. Image Processing:" << endl;
    
    // Create sample image
    Mat original = Mat::zeros(300, 300, CV_8UC3);
    rectangle(original, Point(50, 50), Point(250, 250), Scalar(100, 150, 200), -1);
    circle(original, Point(150, 150), 75, Scalar(255, 255, 255), -1);
    
    // Convert to grayscale
    Mat gray;
    cvtColor(original, gray, COLOR_BGR2GRAY);
    cout << "Converted to grayscale" << endl;
    
    // Apply Gaussian blur
    Mat blurred;
    GaussianBlur(gray, blurred, Size(15, 15), 0);
    cout << "Applied Gaussian blur" << endl;
    
    // Edge detection
    Mat edges;
    Canny(blurred, edges, 50, 150);
    cout << "Applied Canny edge detection" << endl;
    
    // Morphological operations
    Mat kernel = getStructuringElement(MORPH_RECT, Size(5, 5));
    Mat dilated;
    dilate(edges, dilated, kernel);
    cout << "Applied dilation" << endl;
    
    // Histogram equalization
    Mat equalized;
    equalizeHist(gray, equalized);
    cout << "Applied histogram equalization" << endl;
    
    // Save processed images
    imwrite("opencv_gray.jpg", gray);
    imwrite("opencv_edges.jpg", edges);
    imwrite("opencv_equalized.jpg", equalized);
    cout << "Processed images saved" << endl;
}

void featureDetection() {
    cout << "\n3. Feature Detection:" << endl;
    
    // Create test image with features
    Mat image = Mat::zeros(400, 400, CV_8UC1);
    
    // Add some features
    for (int i = 0; i < 10; i++) {
        Point center(rand() % 300 + 50, rand() % 300 + 50);
        circle(image, center, 20, Scalar(255), -1);
    }
    
    // Add rectangles
    for (int i = 0; i < 5; i++) {
        Point pt1(rand() % 200 + 50, rand() % 200 + 50);
        Point pt2(pt1.x + 40, pt1.y + 40);
        rectangle(image, pt1, pt2, Scalar(128), 2);
    }
    
    // Harris corner detection
    Mat corners, corners_norm;
    cornerHarris(image, corners, 2, 3, 0.04);
    normalize(corners, corners_norm, 0, 255, NORM_MINMAX, CV_32FC1, Mat());
    
    int corner_count = 0;
    for (int i = 0; i < corners_norm.rows; i++) {
        for (int j = 0; j < corners_norm.cols; j++) {
            if ((int)corners_norm.at<float>(i, j) > 100) {
                corner_count++;
            }
        }
    }
    
    cout << "Harris corners detected: " << corner_count << endl;
    
    // FAST feature detection
    vector<KeyPoint> keypoints;
    Ptr<FastFeatureDetector> detector = FastFeatureDetector::create();
    detector->detect(image, keypoints);
    
    cout << "FAST keypoints detected: " << keypoints.size() << endl;
    
    // Draw keypoints
    Mat image_with_keypoints;
    drawKeypoints(image, keypoints, image_with_keypoints, Scalar::all(-1), 
                  DrawMatchesFlags::DEFAULT);
    
    imwrite("opencv_features.jpg", image_with_keypoints);
    cout << "Feature detection results saved" << endl;
}

void objectDetection() {
    cout << "\n4. Object Detection:" << endl;
    
    // Create synthetic image for contour detection
    Mat image = Mat::zeros(400, 400, CV_8UC1);
    
    // Draw shapes to detect
    rectangle(image, Point(50, 50), Point(150, 150), Scalar(255), -1);
    circle(image, Point(250, 100), 60, Scalar(128), -1);
    ellipse(image, Point(300, 250), Size(80, 40), 45, 0, 360, Scalar(200), -1);
    
    // Find contours
    vector<vector<Point>> contours;
    vector<Vec4i> hierarchy;
    findContours(image, contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
    
    cout << "Contours found: " << contours.size() << endl;
    
    // Analyze contours
    Mat contour_image = Mat::zeros(image.size(), CV_8UC3);
    for (size_t i = 0; i < contours.size(); i++) {
        // Draw contour
        drawContours(contour_image, contours, (int)i, Scalar(0, 255, 0), 2);
        
        // Calculate properties
        double area = contourArea(contours[i]);
        double perimeter = arcLength(contours[i], true);
        
        cout << "Contour " << i << ": Area=" << area << ", Perimeter=" << perimeter << endl;
        
        // Approximate contour
        vector<Point> approx;
        approxPolyDP(contours[i], approx, 0.02 * perimeter, true);
        
        // Classify shape based on vertices
        string shape;
        if (approx.size() == 3) shape = "Triangle";
        else if (approx.size() == 4) shape = "Rectangle";
        else if (approx.size() > 6) shape = "Circle/Ellipse";
        else shape = "Polygon";
        
        cout << "  Shape: " << shape << " (vertices: " << approx.size() << ")" << endl;
        
        // Draw bounding rectangle
        Rect boundRect = boundingRect(contours[i]);
        rectangle(contour_image, boundRect, Scalar(255, 0, 0), 1);
    }
    
    imwrite("opencv_contours.jpg", contour_image);
    cout << "Contour detection results saved" << endl;
}

void videoProcessing() {
    cout << "\n5. Video Processing:" << endl;
    
    // Create synthetic video frames
    cout << "Creating synthetic video frames..." << endl;
    
    for (int frame = 0; frame < 30; frame++) {
        Mat video_frame = Mat::zeros(300, 400, CV_8UC3);
        
        // Moving circle
        int x = 50 + frame * 10;
        int y = 150 + 50 * sin(frame * 0.2);
        circle(video_frame, Point(x, y), 20, Scalar(0, 255, 255), -1);
        
        // Static background
        rectangle(video_frame, Point(0, 250), Point(400, 300), Scalar(100, 100, 100), -1);
        
        // Frame counter
        putText(video_frame, "Frame: " + to_string(frame), Point(10, 30),
                FONT_HERSHEY_SIMPLEX, 0.7, Scalar(255, 255, 255), 2);
        
        // Save frame
        string filename = "frame_" + to_string(frame) + ".jpg";
        imwrite(filename, video_frame);
    }
    
    cout << "Created 30 synthetic video frames" << endl;
    
    // Background subtraction simulation
    Mat background = Mat::zeros(300, 400, CV_8UC3);
    rectangle(background, Point(0, 250), Point(400, 300), Scalar(100, 100, 100), -1);
    
    Mat current_frame = Mat::zeros(300, 400, CV_8UC3);
    rectangle(current_frame, Point(0, 250), Point(400, 300), Scalar(100, 100, 100), -1);
    circle(current_frame, Point(200, 150), 20, Scalar(0, 255, 255), -1);
    
    Mat diff;
    absdiff(background, current_frame, diff);
    
    Mat gray_diff;
    cvtColor(diff, gray_diff, COLOR_BGR2GRAY);
    
    Mat thresh;
    threshold(gray_diff, thresh, 30, 255, THRESH_BINARY);
    
    imwrite("opencv_background_subtraction.jpg", thresh);
    cout << "Background subtraction example saved" << endl;
    
    // Motion detection simulation
    vector<vector<Point>> motion_contours;
    vector<Vec4i> motion_hierarchy;
    findContours(thresh, motion_contours, motion_hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);
    
    cout << "Motion regions detected: " << motion_contours.size() << endl;
    
    for (size_t i = 0; i < motion_contours.size(); i++) {
        double area = contourArea(motion_contours[i]);
        if (area > 100) {  // Filter small noise
            Rect boundRect = boundingRect(motion_contours[i]);
            cout << "Motion detected at: (" << boundRect.x << ", " << boundRect.y 
                 << ") size: " << boundRect.width << "x" << boundRect.height << endl;
        }
    }
}

/*
=== OPENCV FEATURES DEMONSTRATED ===

1. Basic Image Operations:
   - Image creation and manipulation
   - Drawing shapes and text
   - Image I/O operations

2. Image Processing:
   - Color space conversions
   - Filtering (Gaussian blur)
   - Edge detection (Canny)
   - Morphological operations
   - Histogram equalization

3. Feature Detection:
   - Harris corner detection
   - FAST feature detector
   - Keypoint visualization

4. Object Detection:
   - Contour detection and analysis
   - Shape classification
   - Bounding rectangles
   - Area and perimeter calculations

5. Video Processing:
   - Frame-by-frame processing
   - Background subtraction
   - Motion detection
   - Temporal analysis

=== COMPILATION ===

# Using pkg-config (Linux/Mac)
g++ -std=c++11 OpenCV.cpp -o opencv_example `pkg-config --cflags --libs opencv4`

# Manual linking
g++ -std=c++11 OpenCV.cpp -o opencv_example -lopencv_core -lopencv_imgproc -lopencv_highgui -lopencv_objdetect

# CMake approach
cmake_minimum_required(VERSION 3.12)
project(OpenCVExample)
find_package(OpenCV REQUIRED)
add_executable(opencv_example OpenCV.cpp)
target_link_libraries(opencv_example ${OpenCV_LIBS})

=== COMMON OPENCV MODULES ===

- opencv_core: Basic data structures and algorithms
- opencv_imgproc: Image processing functions
- opencv_highgui: GUI and I/O operations
- opencv_objdetect: Object detection algorithms
- opencv_features2d: Feature detection and description
- opencv_calib3d: Camera calibration and 3D reconstruction
- opencv_ml: Machine learning algorithms
- opencv_video: Video analysis and object tracking
- opencv_imgcodecs: Image codecs
- opencv_videoio: Video I/O

=== PRACTICAL APPLICATIONS ===

1. Image Enhancement:
   - Noise reduction
   - Contrast improvement
   - Color correction

2. Object Recognition:
   - Face detection
   - Template matching
   - Feature matching

3. Video Analysis:
   - Motion tracking
   - Activity recognition
   - Surveillance systems

4. Medical Imaging:
   - Image segmentation
   - Diagnostic assistance
   - 3D reconstruction

5. Robotics:
   - Visual navigation
   - Object manipulation
   - SLAM (Simultaneous Localization and Mapping)

*/

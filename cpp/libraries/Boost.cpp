// Boost.cpp - Boost C++ Libraries Examples
// Comprehensive collection of portable C++ source libraries.

#include <iostream>
#include <vector>
#include <string>
#include <boost/algorithm/string.hpp>
#include <boost/format.hpp>
#include <boost/lexical_cast.hpp>

int main() {
    std::cout << "=== BOOST C++ LIBRARIES EXAMPLES ===" << std::endl;
    
    // 1. String Algorithms
    std::string text = "  Hello, Boost World!  ";
    std::cout << "\n1. String Algorithms:" << std::endl;
    std::cout << "Original: '" << text << "'" << std::endl;
    
    boost::trim(text);
    std::cout << "Trimmed: '" << text << "'" << std::endl;
    
    boost::to_upper(text);
    std::cout << "Upper case: " << text << std::endl;
    
    // String splitting
    std::vector<std::string> words;
    boost::split(words, text, boost::is_any_of(" "));
    std::cout << "Split words: ";
    for (const auto& word : words) {
        std::cout << "[" << word << "] ";
    }
    std::cout << std::endl;
    
    // 2. Format Library
    std::cout << "\n2. Format Library:" << std::endl;
    boost::format fmt("Name: %1%, Age: %2%, Score: %3$.2f");
    fmt % "John" % 25 % 95.67;
    std::cout << fmt.str() << std::endl;
    
    // 3. Lexical Cast
    std::cout << "\n3. Lexical Cast:" << std::endl;
    try {
        int number = boost::lexical_cast<int>("123");
        std::cout << "String to int: " << number << std::endl;
        
        std::string str = boost::lexical_cast<std::string>(456);
        std::cout << "Int to string: " << str << std::endl;
        
        double pi = boost::lexical_cast<double>("3.14159");
        std::cout << "String to double: " << pi << std::endl;
    } catch (const boost::bad_lexical_cast& e) {
        std::cout << "Conversion error: " << e.what() << std::endl;
    }
    
    // 4. Smart Pointers (pre-C++11 alternative)
    std::cout << "\n4. Smart Pointers:" << std::endl;
    boost::shared_ptr<std::string> ptr1(new std::string("Shared pointer"));
    boost::shared_ptr<std::string> ptr2 = ptr1;
    
    std::cout << "Shared pointer content: " << *ptr1 << std::endl;
    std::cout << "Reference count: " << ptr1.use_count() << std::endl;
    
    return 0;
}

/*
=== BOOST FEATURES DEMONSTRATED ===

1. String Algorithms:
   - String trimming and case conversion
   - String splitting and joining
   - Pattern matching and replacement

2. Format Library:
   - Type-safe string formatting
   - Positional arguments
   - Custom formatting options

3. Lexical Cast:
   - Safe type conversions
   - Exception handling for invalid conversions
   - Support for custom types

4. Smart Pointers:
   - Memory management
   - Reference counting
   - RAII principles

=== COMPILATION ===
g++ -std=c++11 Boost.cpp -lboost_system -lboost_filesystem -o boost_example

=== COMMON BOOST LIBRARIES ===
- Algorithm: String and generic algorithms
- Filesystem: File and directory operations
- Format: Type-safe string formatting
- Lexical Cast: Type conversions
- Smart Ptr: Memory management
- Thread: Threading utilities
- Regex: Regular expressions
- Date Time: Date and time handling
*/

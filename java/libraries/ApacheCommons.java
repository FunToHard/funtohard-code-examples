// ApacheCommons.java - Apache Commons Utilities Examples
// Collection of reusable Java components for common programming tasks.

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.*;
import java.util.*;
import java.util.Date;
import java.nio.charset.StandardCharsets;

public class ApacheCommonsExamples {
    
    public static void main(String[] args) {
        System.out.println("=== APACHE COMMONS UTILITIES EXAMPLES ===");
        
        // 1. String Utilities (Commons Lang)
        stringUtilities();
        
        // 2. Array Utilities
        arrayUtilities();
        
        // 3. Collection Utilities
        collectionUtilities();
        
        // 4. Date Utilities
        dateUtilities();
        
        // 5. Number Utilities
        numberUtilities();
        
        // 6. File I/O Utilities (Commons IO)
        fileIOUtilities();
        
        // 7. Validation Utilities
        validationUtilities();
        
        // 8. Codec Utilities
        codecUtilities();
        
        // 9. Random Utilities
        randomUtilities();
    }
    
    // String utilities demonstration
    static void stringUtilities() {
        System.out.println("\n1. String Utilities (Commons Lang):");
        
        String text = "  Hello World  ";
        String nullText = null;
        String emptyText = "";
        
        // Basic string operations
        System.out.println("Original: '" + text + "'");
        System.out.println("Trimmed: '" + StringUtils.trim(text) + "'");
        System.out.println("Capitalized: " + StringUtils.capitalize(text.trim()));
        System.out.println("Reversed: " + StringUtils.reverse(text.trim()));
        
        // Null-safe operations
        System.out.println("Is blank (null): " + StringUtils.isBlank(nullText));
        System.out.println("Is blank (empty): " + StringUtils.isBlank(emptyText));
        System.out.println("Is blank (spaces): " + StringUtils.isBlank("   "));
        System.out.println("Default if blank: " + StringUtils.defaultIfBlank(nullText, "Default Value"));
        
        // String manipulation
        String sentence = "The quick brown fox jumps over the lazy dog";
        System.out.println("Original sentence: " + sentence);
        System.out.println("Abbreviated: " + StringUtils.abbreviate(sentence, 20));
        System.out.println("Left pad: " + StringUtils.leftPad("123", 8, '0'));
        System.out.println("Right pad: " + StringUtils.rightPad("Hello", 10, '*'));
        
        // String comparison
        System.out.println("Equals ignore case: " + StringUtils.equalsIgnoreCase("Hello", "HELLO"));
        System.out.println("Contains ignore case: " + StringUtils.containsIgnoreCase("Hello World", "WORLD"));
        
        // String joining and splitting
        String[] words = {"apple", "banana", "cherry"};
        String joined = StringUtils.join(words, ", ");
        System.out.println("Joined: " + joined);
        
        String[] split = StringUtils.split("one,two,three", ",");
        System.out.println("Split: " + Arrays.toString(split));
        
        // String replacement
        String template = "Hello ${name}, welcome to ${place}!";
        Map<String, String> values = new HashMap<>();
        values.put("name", "John");
        values.put("place", "Java World");
        // Note: For actual template replacement, use StrSubstitutor from Commons Text
        System.out.println("Template: " + template);
    }
    
    // Array utilities demonstration
    static void arrayUtilities() {
        System.out.println("\n2. Array Utilities:");
        
        int[] numbers = {1, 2, 3, 4, 5};
        String[] fruits = {"apple", "banana", "cherry"};
        
        // Array operations
        System.out.println("Original array: " + Arrays.toString(numbers));
        System.out.println("Array length: " + ArrayUtils.getLength(numbers));
        System.out.println("Is empty: " + ArrayUtils.isEmpty(numbers));
        System.out.println("Contains 3: " + ArrayUtils.contains(numbers, 3));
        System.out.println("Index of 4: " + ArrayUtils.indexOf(numbers, 4));
        
        // Array manipulation
        int[] reversed = ArrayUtils.clone(numbers);
        ArrayUtils.reverse(reversed);
        System.out.println("Reversed: " + Arrays.toString(reversed));
        
        int[] added = ArrayUtils.add(numbers, 6);
        System.out.println("Added element: " + Arrays.toString(added));
        
        int[] removed = ArrayUtils.remove(numbers, 2); // Remove index 2
        System.out.println("Removed index 2: " + Arrays.toString(removed));
        
        // Array conversion
        Integer[] boxed = ArrayUtils.toObject(numbers);
        System.out.println("Boxed array: " + Arrays.toString(boxed));
        
        int[] unboxed = ArrayUtils.toPrimitive(boxed);
        System.out.println("Unboxed array: " + Arrays.toString(unboxed));
        
        // Subarray operations
        int[] subarray = ArrayUtils.subarray(numbers, 1, 4);
        System.out.println("Subarray [1-4): " + Arrays.toString(subarray));
    }
    
    // Collection utilities demonstration
    static void collectionUtilities() {
        System.out.println("\n3. Collection Utilities:");
        
        List<String> list1 = Arrays.asList("a", "b", "c", "d");
        List<String> list2 = Arrays.asList("c", "d", "e", "f");
        
        // Collection operations
        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);
        System.out.println("Is empty: " + CollectionUtils.isEmpty(list1));
        System.out.println("Size: " + CollectionUtils.size(list1));
        
        // Set operations
        Collection<String> intersection = CollectionUtils.intersection(list1, list2);
        System.out.println("Intersection: " + intersection);
        
        Collection<String> union = CollectionUtils.union(list1, list2);
        System.out.println("Union: " + union);
        
        Collection<String> difference = CollectionUtils.subtract(list1, list2);
        System.out.println("Difference (list1 - list2): " + difference);
        
        // Map utilities
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 5);
        map.put("banana", 3);
        map.put("cherry", 8);
        
        System.out.println("Map: " + map);
        System.out.println("Map is empty: " + MapUtils.isEmpty(map));
        System.out.println("Map size: " + MapUtils.size(map));
        System.out.println("Get with default: " + MapUtils.getInteger(map, "orange", 0));
        
        // Safe map operations
        String value = MapUtils.getString(map, "apple", "Not found");
        System.out.println("Safe get string: " + value);
    }
    
    // Date utilities demonstration
    static void dateUtilities() {
        System.out.println("\n4. Date Utilities:");
        
        Date now = new Date();
        System.out.println("Current date: " + now);
        
        // Date manipulation
        Date tomorrow = DateUtils.addDays(now, 1);
        System.out.println("Tomorrow: " + tomorrow);
        
        Date nextWeek = DateUtils.addWeeks(now, 1);
        System.out.println("Next week: " + nextWeek);
        
        Date nextMonth = DateUtils.addMonths(now, 1);
        System.out.println("Next month: " + nextMonth);
        
        // Date truncation
        Date truncatedToDay = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        System.out.println("Truncated to day: " + truncatedToDay);
        
        Date truncatedToHour = DateUtils.truncate(now, Calendar.HOUR);
        System.out.println("Truncated to hour: " + truncatedToHour);
        
        // Date comparison
        Date yesterday = DateUtils.addDays(now, -1);
        boolean isSameDay = DateUtils.isSameDay(now, yesterday);
        System.out.println("Is same day (now vs yesterday): " + isSameDay);
        
        boolean isSameInstant = DateUtils.isSameInstant(now, now);
        System.out.println("Is same instant: " + isSameInstant);
    }
    
    // Number utilities demonstration
    static void numberUtilities() {
        System.out.println("\n5. Number Utilities:");
        
        String numberStr = "123";
        String invalidStr = "abc";
        String floatStr = "123.45";
        
        // Number validation and parsing
        System.out.println("Is number '" + numberStr + "': " + NumberUtils.isCreatable(numberStr));
        System.out.println("Is number '" + invalidStr + "': " + NumberUtils.isCreatable(invalidStr));
        System.out.println("Is digits only '" + numberStr + "': " + NumberUtils.isDigits(numberStr));
        
        // Safe parsing with defaults
        int intValue = NumberUtils.toInt(numberStr, 0);
        System.out.println("Parsed int: " + intValue);
        
        int invalidInt = NumberUtils.toInt(invalidStr, -1);
        System.out.println("Invalid int with default: " + invalidInt);
        
        double doubleValue = NumberUtils.toDouble(floatStr, 0.0);
        System.out.println("Parsed double: " + doubleValue);
        
        // Min/Max operations
        int[] numbers = {5, 2, 8, 1, 9};
        int max = NumberUtils.max(numbers);
        int min = NumberUtils.min(numbers);
        System.out.println("Array: " + Arrays.toString(numbers));
        System.out.println("Max: " + max + ", Min: " + min);
        
        // Comparison
        int comparison = NumberUtils.compare(10, 20);
        System.out.println("Compare 10 vs 20: " + comparison);
    }
    
    // File I/O utilities demonstration
    static void fileIOUtilities() {
        System.out.println("\n6. File I/O Utilities:");
        
        try {
            // Create a temporary file for demonstration
            File tempFile = File.createTempFile("commons-demo", ".txt");
            tempFile.deleteOnExit();
            
            String content = "Hello, Apache Commons IO!\nThis is a test file.\nWith multiple lines.";
            
            // Write to file
            FileUtils.writeStringToFile(tempFile, content, StandardCharsets.UTF_8);
            System.out.println("Written to file: " + tempFile.getAbsolutePath());
            
            // Read from file
            String readContent = FileUtils.readFileToString(tempFile, StandardCharsets.UTF_8);
            System.out.println("Read content: " + readContent.replace("\n", "\\n"));
            
            // Read lines
            List<String> lines = FileUtils.readLines(tempFile, StandardCharsets.UTF_8);
            System.out.println("Number of lines: " + lines.size());
            System.out.println("First line: " + lines.get(0));
            
            // File size
            long size = FileUtils.sizeOf(tempFile);
            System.out.println("File size: " + size + " bytes");
            System.out.println("Human readable size: " + FileUtils.byteCountToDisplaySize(size));
            
            // Copy file
            File copyFile = File.createTempFile("commons-copy", ".txt");
            copyFile.deleteOnExit();
            FileUtils.copyFile(tempFile, copyFile);
            System.out.println("File copied to: " + copyFile.getAbsolutePath());
            
            // Directory operations
            File tempDir = FileUtils.getTempDirectory();
            System.out.println("Temp directory: " + tempDir.getAbsolutePath());
            System.out.println("Temp dir size: " + FileUtils.byteCountToDisplaySize(FileUtils.sizeOfDirectory(tempDir)));
            
        } catch (IOException e) {
            System.err.println("File I/O error: " + e.getMessage());
        }
    }
    
    // Validation utilities demonstration
    static void validationUtilities() {
        System.out.println("\n7. Validation Utilities:");
        
        // Email validation
        EmailValidator emailValidator = EmailValidator.getInstance();
        String[] emails = {"test@example.com", "invalid-email", "user@domain.co.uk"};
        
        for (String email : emails) {
            boolean isValid = emailValidator.isValid(email);
            System.out.println("Email '" + email + "' is valid: " + isValid);
        }
        
        // URL validation
        UrlValidator urlValidator = new UrlValidator();
        String[] urls = {"http://www.example.com", "https://google.com", "invalid-url", "ftp://files.example.com"};
        
        for (String url : urls) {
            boolean isValid = urlValidator.isValid(url);
            System.out.println("URL '" + url + "' is valid: " + isValid);
        }
        
        // URL validation with specific schemes
        String[] schemes = {"http", "https"};
        UrlValidator httpValidator = new UrlValidator(schemes);
        
        for (String url : urls) {
            boolean isValid = httpValidator.isValid(url);
            System.out.println("URL '" + url + "' is valid HTTP/HTTPS: " + isValid);
        }
    }
    
    // Codec utilities demonstration
    static void codecUtilities() {
        System.out.println("\n8. Codec Utilities:");
        
        String text = "Hello, Apache Commons!";
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        
        // Hash functions
        String md5 = DigestUtils.md5Hex(text);
        System.out.println("MD5 hash: " + md5);
        
        String sha1 = DigestUtils.sha1Hex(text);
        System.out.println("SHA-1 hash: " + sha1);
        
        String sha256 = DigestUtils.sha256Hex(text);
        System.out.println("SHA-256 hash: " + sha256);
        
        // File hashing (using temporary file)
        try {
            File tempFile = File.createTempFile("hash-demo", ".txt");
            tempFile.deleteOnExit();
            FileUtils.writeStringToFile(tempFile, text, StandardCharsets.UTF_8);
            
            String fileMd5 = DigestUtils.md5Hex(new FileInputStream(tempFile));
            System.out.println("File MD5 hash: " + fileMd5);
            
        } catch (IOException e) {
            System.err.println("Error hashing file: " + e.getMessage());
        }
        
        // Base64 encoding (using Java 8+ built-in, but Commons Codec also provides this)
        String base64 = Base64.getEncoder().encodeToString(data);
        System.out.println("Base64 encoded: " + base64);
        
        byte[] decoded = Base64.getDecoder().decode(base64);
        String decodedText = new String(decoded, StandardCharsets.UTF_8);
        System.out.println("Base64 decoded: " + decodedText);
    }
    
    // Random utilities demonstration
    static void randomUtilities() {
        System.out.println("\n9. Random Utilities:");
        
        // Random strings
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(10);
        System.out.println("Random alphabetic (10): " + randomAlphabetic);
        
        String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(12);
        System.out.println("Random alphanumeric (12): " + randomAlphanumeric);
        
        String randomNumeric = RandomStringUtils.randomNumeric(8);
        System.out.println("Random numeric (8): " + randomNumeric);
        
        String randomAscii = RandomStringUtils.randomAscii(15);
        System.out.println("Random ASCII (15): " + randomAscii);
        
        // Random with custom characters
        String customChars = "ABCDEF0123456789";
        String randomCustom = RandomStringUtils.random(10, customChars);
        System.out.println("Random custom chars (10): " + randomCustom);
        
        // Random boolean and numbers (using standard Java Random)
        Random random = new Random();
        System.out.println("Random boolean: " + random.nextBoolean());
        System.out.println("Random int (0-100): " + random.nextInt(101));
        System.out.println("Random double: " + random.nextDouble());
    }
}

/*
=== APACHE COMMONS FEATURES DEMONSTRATED ===

1. Commons Lang (StringUtils, ArrayUtils, NumberUtils):
   - String manipulation and validation
   - Array operations and conversions
   - Number parsing and validation
   - Date utilities

2. Commons Collections:
   - Collection operations (union, intersection, difference)
   - Safe map operations
   - Null-safe collection handling

3. Commons IO:
   - File reading and writing
   - Directory operations
   - File size calculations
   - Stream utilities

4. Commons Validator:
   - Email validation
   - URL validation
   - Custom validation rules

5. Commons Codec:
   - Hash functions (MD5, SHA-1, SHA-256)
   - Base64 encoding/decoding
   - File hashing

6. Random Utilities:
   - Random string generation
   - Custom character sets
   - Various random data types

=== MAVEN DEPENDENCIES ===

<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-collections4</artifactId>
        <version>4.4</version>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.11.0</version>
    </dependency>
    <dependency>
        <groupId>commons-validator</groupId>
        <artifactId>commons-validator</artifactId>
        <version>1.7</version>
    </dependency>
    <dependency>
        <groupId>commons-codec</groupId>
        <artifactId>commons-codec</artifactId>
        <version>1.15</version>
    </dependency>
</dependencies>

=== COMMON USE CASES ===

1. String Processing:
   - Input validation and sanitization
   - Text formatting and manipulation
   - Null-safe string operations

2. File Operations:
   - Configuration file reading
   - Log file processing
   - Temporary file management

3. Data Validation:
   - User input validation
   - Configuration validation
   - API parameter validation

4. Security:
   - Password hashing
   - Data integrity checks
   - Secure random generation

5. Collections:
   - Data set operations
   - Safe map access
   - Collection transformations

*/

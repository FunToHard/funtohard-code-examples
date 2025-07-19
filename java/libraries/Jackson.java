// Jackson.java - JSON Processing Examples
// Parse, generate, and manipulate JSON data with Jackson library.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.*;
import java.util.*;
import java.time.LocalDateTime;

public class JacksonExamples {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static void main(String[] args) {
        System.out.println("=== JACKSON JSON PROCESSING EXAMPLES ===");
        
        // 1. Basic JSON Serialization/Deserialization
        basicJsonOperations();
        
        // 2. Working with Collections
        collectionOperations();
        
        // 3. Custom Annotations
        annotationExamples();
        
        // 4. Tree Model Processing
        treeModelExamples();
        
        // 5. Advanced Features
        advancedFeatures();
    }
    
    // Basic JSON operations
    static void basicJsonOperations() {
        System.out.println("\n1. Basic JSON Operations:");
        
        try {
            // Create a Person object
            Person person = new Person("John Doe", 30, "john@example.com");
            
            // Object to JSON
            String json = mapper.writeValueAsString(person);
            System.out.println("Object to JSON: " + json);
            
            // JSON to Object
            Person parsedPerson = mapper.readValue(json, Person.class);
            System.out.println("JSON to Object: " + parsedPerson);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Collection operations
    static void collectionOperations() {
        System.out.println("\n2. Collection Operations:");
        
        try {
            // List to JSON
            List<Person> people = Arrays.asList(
                new Person("Alice", 25, "alice@example.com"),
                new Person("Bob", 35, "bob@example.com")
            );
            
            String listJson = mapper.writeValueAsString(people);
            System.out.println("List to JSON: " + listJson);
            
            // JSON to List
            List<Person> parsedList = mapper.readValue(listJson, 
                new TypeReference<List<Person>>() {});
            System.out.println("JSON to List: " + parsedList);
            
            // Map operations
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("name", "Charlie");
            dataMap.put("age", 28);
            dataMap.put("active", true);
            
            String mapJson = mapper.writeValueAsString(dataMap);
            System.out.println("Map to JSON: " + mapJson);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Annotation examples
    static void annotationExamples() {
        System.out.println("\n3. Annotation Examples:");
        
        try {
            User user = new User("john_doe", "John Doe", "secret123");
            user.setCreatedAt(LocalDateTime.now());
            
            String json = mapper.writeValueAsString(user);
            System.out.println("User with annotations: " + json);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Tree model examples
    static void treeModelExamples() {
        System.out.println("\n4. Tree Model Examples:");
        
        try {
            String jsonString = "{\"name\":\"John\",\"age\":30,\"address\":{\"city\":\"NYC\",\"zip\":\"10001\"}}";
            
            JsonNode rootNode = mapper.readTree(jsonString);
            
            System.out.println("Name: " + rootNode.get("name").asText());
            System.out.println("Age: " + rootNode.get("age").asInt());
            System.out.println("City: " + rootNode.get("address").get("city").asText());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Advanced features
    static void advancedFeatures() {
        System.out.println("\n5. Advanced Features:");
        
        try {
            // Custom date format
            ObjectMapper customMapper = new ObjectMapper();
            customMapper.findAndRegisterModules();
            
            Product product = new Product("Laptop", 999.99, LocalDateTime.now());
            String json = customMapper.writeValueAsString(product);
            System.out.println("Product with custom formatting: " + json);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Basic Person class
class Person {
    private String name;
    private int age;
    private String email;
    
    public Person() {}
    
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, email='%s'}", name, age, email);
    }
}

// User class with annotations
class User {
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("full_name")
    private String fullName;
    
    @JsonIgnore
    private String password;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    public User() {}
    
    public User(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

// Product class
class Product {
    private String name;
    private double price;
    private LocalDateTime createdAt;
    
    public Product() {}
    
    public Product(String name, double price, LocalDateTime createdAt) {
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

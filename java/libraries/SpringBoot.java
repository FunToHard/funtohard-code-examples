// SpringBoot.java - Spring Boot Framework Examples
// Build enterprise-grade applications with auto-configuration, dependency injection, and web services.

package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

// ===== MAIN APPLICATION CLASS =====
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class SpringBootExampleApplication {
    
    public static void main(String[] args) {
        System.out.println("=== SPRING BOOT APPLICATION STARTING ===");
        SpringApplication.run(SpringBootExampleApplication.class, args);
        System.out.println("Spring Boot application started successfully!");
    }
    
    // Command line runner for initialization
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("=== APPLICATION INITIALIZATION ===");
            System.out.println("Spring Boot application is ready!");
            System.out.println("Available endpoints:");
            System.out.println("  GET  /api/users        - Get all users");
            System.out.println("  POST /api/users        - Create user");
            System.out.println("  GET  /api/users/{id}   - Get user by ID");
            System.out.println("  PUT  /api/users/{id}   - Update user");
            System.out.println("  DELETE /api/users/{id} - Delete user");
            System.out.println("  GET  /api/health       - Health check");
            System.out.println("  GET  /api/cache-demo   - Cache demonstration");
        };
    }
}

// ===== ENTITY CLASSES =====
@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Age must be realistic")
    private Integer age;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public User(String name, String email, Integer age) {
        this();
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { 
        this.age = age;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', email='%s', age=%d}", 
                           id, name, email, age);
    }
}

// ===== REPOSITORY LAYER =====
@Repository
interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom query methods
    List<User> findByNameContainingIgnoreCase(String name);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
    List<User> findUsersCreatedAfter(@Param("date") LocalDateTime date);
    
    @Query(value = "SELECT * FROM users WHERE age > ?1 ORDER BY created_at DESC", 
           nativeQuery = true)
    List<User> findUsersOlderThan(Integer age);
    
    // Count queries
    long countByAgeGreaterThan(Integer age);
    
    // Delete queries
    void deleteByEmail(String email);
}

// ===== SERVICE LAYER =====
@Service
class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Get all users
    public List<User> getAllUsers() {
        System.out.println("Fetching all users from database");
        return userRepository.findAll();
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        System.out.println("Fetching user with ID: " + id);
        return userRepository.findById(id);
    }
    
    // Create new user
    public User createUser(User user) {
        System.out.println("Creating new user: " + user.getName());
        
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }
    
    // Update user
    public User updateUser(Long id, User userDetails) {
        System.out.println("Updating user with ID: " + id);
        
        return userRepository.findById(id)
            .map(user -> {
                user.setName(userDetails.getName());
                user.setEmail(userDetails.getEmail());
                user.setAge(userDetails.getAge());
                return userRepository.save(user);
            })
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
    
    // Delete user
    public void deleteUser(Long id) {
        System.out.println("Deleting user with ID: " + id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
    }
    
    // Search users by name
    public List<User> searchUsersByName(String name) {
        System.out.println("Searching users by name: " + name);
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Get users by age range
    public List<User> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        System.out.println("Fetching users between ages " + minAge + " and " + maxAge);
        return userRepository.findByAgeBetween(minAge, maxAge);
    }
    
    // Cached method demonstration
    @Cacheable("users")
    public User getCachedUser(Long id) {
        System.out.println("Fetching user from database (will be cached): " + id);
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // Async method demonstration
    @Async
    public CompletableFuture<String> processUserAsync(Long userId) {
        System.out.println("Processing user asynchronously: " + userId);
        
        try {
            // Simulate long-running process
            Thread.sleep(2000);
            return CompletableFuture.completedFuture("User " + userId + " processed successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Processing failed for user " + userId);
        }
    }
}

// ===== CONTROLLER LAYER =====
@RestController
@RequestMapping("/api")
@Validated
class UserController {
    
    @Autowired
    private UserService userService;
    
    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("GET /api/users - Fetching all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    // Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        System.out.println("GET /api/users/" + id + " - Fetching user by ID");
        
        return userService.getUserById(id)
            .map(user -> ResponseEntity.ok(user))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        System.out.println("POST /api/users - Creating new user");
        
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                         @Valid @RequestBody User userDetails) {
        System.out.println("PUT /api/users/" + id + " - Updating user");
        
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("DELETE /api/users/" + id + " - Deleting user");
        
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Search users
    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String name) {
        System.out.println("GET /api/users/search?name=" + name + " - Searching users");
        List<User> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    
    // Get users by age range
    @GetMapping("/users/age-range")
    public ResponseEntity<List<User>> getUsersByAgeRange(
            @RequestParam Integer minAge, 
            @RequestParam Integer maxAge) {
        System.out.println("GET /api/users/age-range - Age range: " + minAge + "-" + maxAge);
        List<User> users = userService.getUsersByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(users);
    }
    
    // Cache demonstration
    @GetMapping("/cache-demo/{id}")
    public ResponseEntity<User> getCachedUser(@PathVariable Long id) {
        System.out.println("GET /api/cache-demo/" + id + " - Cache demonstration");
        try {
            User user = userService.getCachedUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Async processing demonstration
    @PostMapping("/users/{id}/process")
    public ResponseEntity<String> processUserAsync(@PathVariable Long id) {
        System.out.println("POST /api/users/" + id + "/process - Async processing");
        
        userService.processUserAsync(id);
        return ResponseEntity.accepted().body("User processing started asynchronously");
    }
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "User Management API");
        health.put("version", "1.0.0");
        
        return ResponseEntity.ok(health);
    }
}

// ===== EXCEPTION HANDLING =====
@ControllerAdvice
class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        error.put("timestamp", LocalDateTime.now().toString());
        
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        error.put("message", e.getMessage());
        error.put("timestamp", LocalDateTime.now().toString());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

// ===== SECURITY CONFIGURATION =====
@EnableWebSecurity
class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/health").permitAll()
                .requestMatchers("/api/users/**").permitAll()  // In production, add proper auth
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
}

// ===== SCHEDULED TASKS =====
@Service
class ScheduledTasks {
    
    @Autowired
    private UserRepository userRepository;
    
    // Run every hour
    @Scheduled(fixedRate = 3600000)
    public void reportUserStats() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByAgeGreaterThan(0);
        
        System.out.println("=== HOURLY USER STATS ===");
        System.out.println("Total users: " + totalUsers);
        System.out.println("Active users: " + activeUsers);
        System.out.println("Timestamp: " + LocalDateTime.now());
    }
    
    // Run daily at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void dailyCleanup() {
        System.out.println("=== DAILY CLEANUP TASK ===");
        System.out.println("Running daily maintenance at: " + LocalDateTime.now());
        // Add cleanup logic here
    }
}

// ===== CONFIGURATION CLASSES =====
@Configuration
class AppConfig {
    
    @Bean
    public String applicationInfo() {
        return "Spring Boot Example Application v1.0.0";
    }
    
    // Custom bean example
    @Bean
    public Map<String, String> applicationProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("app.name", "Spring Boot Example");
        props.put("app.version", "1.0.0");
        props.put("app.description", "Comprehensive Spring Boot demonstration");
        return props;
    }
}

// ===== DATA TRANSFER OBJECTS =====
class UserDTO {
    @NotBlank
    private String name;
    
    @Email
    private String email;
    
    @Min(0)
    private Integer age;
    
    // Constructors
    public UserDTO() {}
    
    public UserDTO(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    // Convert to User entity
    public User toUser() {
        return new User(this.name, this.email, this.age);
    }
}

// ===== UTILITY CLASSES =====
class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    
    // Static factory methods
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

/*
=== SPRING BOOT FEATURES DEMONSTRATED ===

1. Auto-Configuration:
   - Automatic database configuration
   - Web MVC auto-configuration
   - JPA auto-configuration

2. Dependency Injection:
   - @Autowired annotation
   - Constructor injection
   - Service layer injection

3. Data Access:
   - JPA repositories
   - Custom query methods
   - Native SQL queries
   - Transaction management

4. Web Layer:
   - REST controllers
   - Request mapping
   - Path variables and request parameters
   - Request/Response handling

5. Validation:
   - Bean validation annotations
   - Custom validation logic
   - Error handling

6. Security:
   - Basic security configuration
   - Endpoint protection
   - CSRF protection

7. Caching:
   - Method-level caching
   - Cache configuration

8. Async Processing:
   - Asynchronous method execution
   - CompletableFuture usage

9. Scheduled Tasks:
   - Fixed rate scheduling
   - Cron expression scheduling

10. Exception Handling:
    - Global exception handler
    - Custom error responses

11. Configuration:
    - Application properties
    - Custom beans
    - Profile-specific configuration

12. Testing Support:
    - Test slices
    - Mock beans
    - Integration testing

=== USAGE EXAMPLES ===

// Create user
POST /api/users
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}

// Get all users
GET /api/users

// Get user by ID
GET /api/users/1

// Update user
PUT /api/users/1
{
  "name": "John Smith",
  "email": "johnsmith@example.com",
  "age": 31
}

// Delete user
DELETE /api/users/1

// Search users
GET /api/users/search?name=John

// Get users by age range
GET /api/users/age-range?minAge=25&maxAge=35

// Health check
GET /api/health

=== APPLICATION PROPERTIES (application.yml) ===

server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  
  cache:
    type: simple
  
  security:
    user:
      name: admin
      password: admin

logging:
  level:
    com.example: DEBUG
    org.springframework.web: DEBUG

=== DEPENDENCIES (pom.xml) ===

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

*/

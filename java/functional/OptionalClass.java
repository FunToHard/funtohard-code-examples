import java.util.*;
import java.util.function.*;

/**
 * Optional Class in Java
 * 
 * Optional is a container object that may or may not contain a non-null value.
 * It helps avoid NullPointerException and makes code more readable and safer.
 * Optional provides methods to check for the presence of values and to handle
 * both present and absent values gracefully.
 */
public class OptionalClass {
    
    static class Person {
        private String name;
        private Optional<String> email;
        private Optional<Integer> age;
        
        public Person(String name, String email, Integer age) {
            this.name = name;
            this.email = Optional.ofNullable(email);
            this.age = Optional.ofNullable(age);
        }
        
        public String getName() { return name; }
        public Optional<String> getEmail() { return email; }
        public Optional<Integer> getAge() { return age; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', email=%s, age=%s}", 
                               name, email.orElse("N/A"), age.map(String::valueOf).orElse("N/A"));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Optional Class Demo ===\n");
        
        // 1. Creating Optional Objects
        creatingOptionals();
        
        // 2. Checking for Values
        checkingForValues();
        
        // 3. Retrieving Values
        retrievingValues();
        
        // 4. Conditional Actions
        conditionalActions();
        
        // 5. Transforming Values
        transformingValues();
        
        // 6. Filtering Values
        filteringValues();
        
        // 7. Combining Optionals
        combiningOptionals();
        
        // 8. Real-world Examples
        realWorldExamples();
    }
    
    // 1. Creating Optional Objects
    static void creatingOptionals() {
        System.out.println("1. Creating Optional Objects:");
        
        // Empty Optional
        Optional<String> empty = Optional.empty();
        System.out.println("Empty Optional: " + empty);
        
        // Optional with non-null value
        Optional<String> nonEmpty = Optional.of("Hello World");
        System.out.println("Non-empty Optional: " + nonEmpty);
        
        // Optional that might be null (safe)
        String nullableString = null;
        Optional<String> nullable = Optional.ofNullable(nullableString);
        System.out.println("Nullable Optional: " + nullable);
        
        String nonNullString = "Not null";
        Optional<String> nonNull = Optional.ofNullable(nonNullString);
        System.out.println("Non-null Optional: " + nonNull);
        
        // This would throw NullPointerException
        // Optional<String> willThrow = Optional.of(null); // Don't do this!
        
        System.out.println();
    }
    
    // 2. Checking for Values
    static void checkingForValues() {
        System.out.println("2. Checking for Values:");
        
        Optional<String> present = Optional.of("Present");
        Optional<String> absent = Optional.empty();
        
        // isPresent() - returns boolean
        System.out.println("present.isPresent(): " + present.isPresent());
        System.out.println("absent.isPresent(): " + absent.isPresent());
        
        // isEmpty() - returns boolean (Java 11+)
        System.out.println("present.isEmpty(): " + present.isEmpty());
        System.out.println("absent.isEmpty(): " + absent.isEmpty());
        
        // Traditional null check vs Optional
        String traditionalString = "Hello";
        if (traditionalString != null) {
            System.out.println("Traditional: " + traditionalString.toUpperCase());
        }
        
        Optional<String> optionalString = Optional.of("Hello");
        if (optionalString.isPresent()) {
            System.out.println("Optional: " + optionalString.get().toUpperCase());
        }
        
        System.out.println();
    }
    
    // 3. Retrieving Values
    static void retrievingValues() {
        System.out.println("3. Retrieving Values:");
        
        Optional<String> present = Optional.of("Present Value");
        Optional<String> absent = Optional.empty();
        
        // get() - returns value or throws NoSuchElementException
        System.out.println("present.get(): " + present.get());
        // System.out.println("absent.get(): " + absent.get()); // Would throw exception
        
        // orElse() - returns value or default
        System.out.println("present.orElse('Default'): " + present.orElse("Default"));
        System.out.println("absent.orElse('Default'): " + absent.orElse("Default"));
        
        // orElseGet() - returns value or result of supplier
        System.out.println("present.orElseGet(() -> 'Generated'): " + 
            present.orElseGet(() -> "Generated"));
        System.out.println("absent.orElseGet(() -> 'Generated'): " + 
            absent.orElseGet(() -> "Generated"));
        
        // orElseThrow() - returns value or throws exception
        System.out.println("present.orElseThrow(): " + present.orElseThrow());
        try {
            absent.orElseThrow(() -> new RuntimeException("Value not present"));
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // 4. Conditional Actions
    static void conditionalActions() {
        System.out.println("4. Conditional Actions:");
        
        Optional<String> present = Optional.of("Hello World");
        Optional<String> absent = Optional.empty();
        
        // ifPresent() - execute action if value is present
        System.out.println("Using ifPresent():");
        present.ifPresent(value -> System.out.println("  Present: " + value));
        absent.ifPresent(value -> System.out.println("  This won't print"));
        
        // ifPresentOrElse() - execute action if present, else execute other action (Java 9+)
        System.out.println("Using ifPresentOrElse():");
        present.ifPresentOrElse(
            value -> System.out.println("  Value found: " + value),
            () -> System.out.println("  No value found")
        );
        
        absent.ifPresentOrElse(
            value -> System.out.println("  Value found: " + value),
            () -> System.out.println("  No value found")
        );
        
        // Traditional approach vs Optional
        String traditionalValue = "Traditional";
        if (traditionalValue != null) {
            System.out.println("Traditional approach: " + traditionalValue.toUpperCase());
        }
        
        Optional.ofNullable(traditionalValue)
               .ifPresent(value -> System.out.println("Optional approach: " + value.toUpperCase()));
        
        System.out.println();
    }
    
    // 5. Transforming Values
    static void transformingValues() {
        System.out.println("5. Transforming Values:");
        
        Optional<String> name = Optional.of("john doe");
        Optional<String> empty = Optional.empty();
        
        // map() - transform value if present
        Optional<String> upperName = name.map(String::toUpperCase);
        System.out.println("Uppercase name: " + upperName.orElse("N/A"));
        
        Optional<String> upperEmpty = empty.map(String::toUpperCase);
        System.out.println("Uppercase empty: " + upperEmpty.orElse("N/A"));
        
        // Chain multiple transformations
        Optional<String> processed = name
            .map(String::trim)
            .map(String::toUpperCase)
            .map(s -> "Mr. " + s);
        System.out.println("Processed name: " + processed.orElse("N/A"));
        
        // map with different types
        Optional<Integer> nameLength = name.map(String::length);
        System.out.println("Name length: " + nameLength.orElse(0));
        
        // flatMap() - for nested Optionals
        Optional<Optional<String>> nestedOptional = Optional.of(Optional.of("Nested"));
        Optional<String> flattened = nestedOptional.flatMap(opt -> opt);
        System.out.println("Flattened: " + flattened.orElse("N/A"));
        
        // Practical flatMap example
        Optional<Person> person = Optional.of(new Person("Alice", "alice@example.com", 30));
        Optional<String> email = person.flatMap(Person::getEmail);
        System.out.println("Person email: " + email.orElse("No email"));
        
        System.out.println();
    }
    
    // 6. Filtering Values
    static void filteringValues() {
        System.out.println("6. Filtering Values:");
        
        Optional<String> longString = Optional.of("This is a long string");
        Optional<String> shortString = Optional.of("Short");
        Optional<String> empty = Optional.empty();
        
        // filter() - keep value only if it matches predicate
        Optional<String> filtered1 = longString.filter(s -> s.length() > 10);
        System.out.println("Long string filtered (>10): " + filtered1.orElse("Filtered out"));
        
        Optional<String> filtered2 = shortString.filter(s -> s.length() > 10);
        System.out.println("Short string filtered (>10): " + filtered2.orElse("Filtered out"));
        
        Optional<String> filtered3 = empty.filter(s -> s.length() > 10);
        System.out.println("Empty filtered (>10): " + filtered3.orElse("Was empty"));
        
        // Combining filter with other operations
        Optional<String> result = Optional.of("  Hello World  ")
            .filter(s -> !s.trim().isEmpty())
            .map(String::trim)
            .map(String::toUpperCase)
            .filter(s -> s.startsWith("HELLO"));
        
        System.out.println("Complex filtering result: " + result.orElse("Filtered out"));
        
        // Filtering with custom objects
        Optional<Person> person = Optional.of(new Person("Bob", null, 25));
        Optional<Person> adultWithEmail = person
            .filter(p -> p.getAge().orElse(0) >= 18)
            .filter(p -> p.getEmail().isPresent());
        
        System.out.println("Adult with email: " + adultWithEmail.orElse(null));
        
        System.out.println();
    }
    
    // 7. Combining Optionals
    static void combiningOptionals() {
        System.out.println("7. Combining Optionals:");
        
        Optional<String> first = Optional.of("First");
        Optional<String> second = Optional.of("Second");
        Optional<String> empty = Optional.empty();
        
        // or() - return first Optional if present, otherwise second (Java 9+)
        Optional<String> combined1 = first.or(() -> second);
        System.out.println("first.or(second): " + combined1.orElse("None"));
        
        Optional<String> combined2 = empty.or(() -> second);
        System.out.println("empty.or(second): " + combined2.orElse("None"));
        
        Optional<String> combined3 = empty.or(() -> Optional.empty());
        System.out.println("empty.or(empty): " + combined3.orElse("None"));
        
        // Combining multiple Optionals
        Optional<String> result = Optional.of("Start")
            .or(() -> Optional.of("Backup1"))
            .or(() -> Optional.of("Backup2"));
        System.out.println("Multiple or: " + result.orElse("None"));
        
        // Custom combination logic
        Optional<String> name = Optional.of("John");
        Optional<String> surname = Optional.of("Doe");
        
        Optional<String> fullName = name.flatMap(n -> 
            surname.map(s -> n + " " + s)
        );
        System.out.println("Full name: " + fullName.orElse("Incomplete"));
        
        System.out.println();
    }
    
    // 8. Real-world Examples
    static void realWorldExamples() {
        System.out.println("8. Real-world Examples:");
        
        // Example 1: Configuration values
        Optional<String> configValue = getConfigValue("database.url");
        String dbUrl = configValue.orElse("jdbc:h2:mem:testdb");
        System.out.println("Database URL: " + dbUrl);
        
        // Example 2: User input validation
        Optional<String> userInput = getUserInput();
        String processedInput = userInput
            .filter(input -> !input.trim().isEmpty())
            .map(String::trim)
            .map(String::toLowerCase)
            .orElse("default_value");
        System.out.println("Processed input: " + processedInput);
        
        // Example 3: Finding in collections
        List<Person> people = Arrays.asList(
            new Person("Alice", "alice@example.com", 30),
            new Person("Bob", null, 25),
            new Person("Charlie", "charlie@example.com", 35)
        );
        
        Optional<Person> foundPerson = findPersonByName(people, "Bob");
        foundPerson.ifPresentOrElse(
            person -> System.out.println("Found person: " + person),
            () -> System.out.println("Person not found")
        );
        
        // Example 4: Method chaining with Optional
        Optional<String> emailDomain = foundPerson
            .flatMap(Person::getEmail)
            .map(email -> email.substring(email.indexOf('@') + 1))
            .filter(domain -> !domain.isEmpty());
        
        System.out.println("Email domain: " + emailDomain.orElse("No email or invalid domain"));
        
        // Example 5: Avoiding nested null checks
        Optional<String> result = Optional.of(new Person("David", "david@example.com", 28))
            .flatMap(Person::getEmail)
            .filter(email -> email.contains("@"))
            .map(email -> "Email is valid: " + email);
        
        System.out.println("Validation result: " + result.orElse("Invalid email"));
        
        // Example 6: Optional in streams
        List<Optional<String>> optionalList = Arrays.asList(
            Optional.of("One"),
            Optional.empty(),
            Optional.of("Three"),
            Optional.empty(),
            Optional.of("Five")
        );
        
        List<String> presentValues = optionalList.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("Present values: " + presentValues);
        
        // Better approach using flatMap
        List<String> presentValues2 = optionalList.stream()
            .flatMap(Optional::stream)  // Java 9+
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("Present values (flatMap): " + presentValues2);
        
        System.out.println();
    }
    
    // Helper methods for real-world examples
    static Optional<String> getConfigValue(String key) {
        // Simulate configuration lookup
        Map<String, String> config = Map.of(
            "app.name", "MyApp",
            "app.version", "1.0.0"
        );
        return Optional.ofNullable(config.get(key));
    }
    
    static Optional<String> getUserInput() {
        // Simulate user input
        return Optional.of("  User Input  ");
    }
    
    static Optional<Person> findPersonByName(List<Person> people, String name) {
        return people.stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst();
    }
}

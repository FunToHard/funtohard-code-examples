import java.util.*;
import java.util.function.*;

/**
 * Method References in Java
 * 
 * Method references provide a way to refer to methods without invoking them.
 * They are a shorthand notation of lambda expressions to call a method.
 * There are four types of method references:
 * 1. Static method references
 * 2. Instance method references of particular objects
 * 3. Instance method references of arbitrary objects
 * 4. Constructor references
 */
public class MethodReferences {
    
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public Person(String name) {
            this(name, 0);
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        
        public static int compareByAge(Person a, Person b) {
            return Integer.compare(a.age, b.age);
        }
        
        public int compareByName(Person other) {
            return this.name.compareTo(other.name);
        }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d}", name, age);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Method References Demo ===\n");
        
        // 1. Static Method References
        staticMethodReferences();
        
        // 2. Instance Method References of Particular Objects
        instanceMethodReferencesParticularObject();
        
        // 3. Instance Method References of Arbitrary Objects
        instanceMethodReferencesArbitraryObject();
        
        // 4. Constructor References
        constructorReferences();
        
        // 5. Comparison: Lambda vs Method Reference
        lambdaVsMethodReference();
    }
    
    // 1. Static Method References
    static void staticMethodReferences() {
        System.out.println("1. Static Method References:");
        
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
        
        // Lambda expression
        List<Integer> parsed1 = numbers.stream()
                                      .map(s -> Integer.parseInt(s))
                                      .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        // Method reference (static method)
        List<Integer> parsed2 = numbers.stream()
                                      .map(Integer::parseInt)
                                      .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        System.out.println("Parsed numbers: " + parsed2);
        
        // Math operations
        List<Double> values = Arrays.asList(1.5, 2.7, 3.2, 4.8);
        
        // Using Math.ceil static method
        List<Double> ceilings = values.stream()
                                     .map(Math::ceil)
                                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Ceiling values: " + ceilings);
        
        // Using Math.max static method with reduce
        Optional<Double> max = values.stream()
                                    .reduce(Math::max);
        System.out.println("Maximum value: " + max.orElse(0.0));
        
        // Custom static method
        List<String> words = Arrays.asList("hello", "world", "java", "method", "reference");
        List<String> capitalized = words.stream()
                                       .map(MethodReferences::capitalize)
                                       .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Capitalized words: " + capitalized);
        
        System.out.println();
    }
    
    // Custom static method for demonstration
    static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    // 2. Instance Method References of Particular Objects
    static void instanceMethodReferencesParticularObject() {
        System.out.println("2. Instance Method References of Particular Objects:");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        
        // Using System.out.println instance method
        System.out.println("Words:");
        words.forEach(System.out::println);
        
        // Using a specific object's method
        StringBuilder sb = new StringBuilder();
        words.forEach(sb::append);
        System.out.println("Concatenated: " + sb.toString());
        
        // Using String's instance methods on a particular string
        String prefix = "Item: ";
        List<String> prefixed = words.stream()
                                    .map(prefix::concat)
                                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Prefixed words: " + prefixed);
        
        // Using custom object's method
        StringProcessor processor = new StringProcessor();
        List<String> processed = words.stream()
                                     .map(processor::process)
                                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Processed words: " + processed);
        
        System.out.println();
    }
    
    // Helper class for demonstration
    static class StringProcessor {
        public String process(String input) {
            return "[" + input.toUpperCase() + "]";
        }
    }
    
    // 3. Instance Method References of Arbitrary Objects
    static void instanceMethodReferencesArbitraryObject() {
        System.out.println("3. Instance Method References of Arbitrary Objects:");
        
        List<String> words = Arrays.asList("hello", "WORLD", "Java", "METHOD");
        
        // Using String's instance method on arbitrary String objects
        List<String> lowercase = words.stream()
                                     .map(String::toLowerCase)
                                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Lowercase: " + lowercase);
        
        List<String> uppercase = words.stream()
                                     .map(String::toUpperCase)
                                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Uppercase: " + uppercase);
        
        List<Integer> lengths = words.stream()
                                    .map(String::length)
                                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Lengths: " + lengths);
        
        // Sorting using instance method of arbitrary objects
        List<String> sorted = new ArrayList<>(words);
        sorted.sort(String::compareToIgnoreCase);
        System.out.println("Sorted (ignore case): " + sorted);
        
        // Using Person's instance method
        List<Person> people = Arrays.asList(
            new Person("Alice", 30),
            new Person("Bob", 25),
            new Person("Charlie", 35)
        );
        
        // Using static method reference for comparison
        people.sort(Person::compareByAge);
        System.out.println("People sorted by age: " + people);
        
        // Extract names using instance method reference
        List<String> names = people.stream()
                                  .map(Person::getName)
                                  .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Names: " + names);
        
        System.out.println();
    }
    
    // 4. Constructor References
    static void constructorReferences() {
        System.out.println("4. Constructor References:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        // Using constructor reference to create Person objects
        List<Person> people = names.stream()
                                  .map(Person::new)  // Constructor reference
                                  .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("People created with constructor reference: " + people);
        
        // Using Supplier with constructor reference
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        newList.addAll(names);
        System.out.println("New list created with constructor reference: " + newList);
        
        // Using Function with constructor reference
        Function<String, Person> personFactory = Person::new;
        Person person = personFactory.apply("Eve");
        System.out.println("Person created with factory: " + person);
        
        // Array constructor reference
        Function<Integer, String[]> arrayFactory = String[]::new;
        String[] array = arrayFactory.apply(5);
        System.out.println("Array created with length: " + array.length);
        
        // Converting list to array using constructor reference
        String[] namesArray = names.stream()
                                  .toArray(String[]::new);
        System.out.println("Names array: " + Arrays.toString(namesArray));
        
        System.out.println();
    }
    
    // 5. Comparison: Lambda vs Method Reference
    static void lambdaVsMethodReference() {
        System.out.println("5. Lambda vs Method Reference Comparison:");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        System.out.println("Lambda expressions:");
        
        // Lambda expressions
        words.stream()
             .map(s -> s.toUpperCase())
             .forEach(s -> System.out.println(s));
        
        System.out.println("\nMethod references (equivalent):");
        
        // Equivalent method references
        words.stream()
             .map(String::toUpperCase)
             .forEach(System.out::println);
        
        System.out.println("\nMore examples:");
        
        // Lambda vs Method Reference examples
        System.out.println("Sum using lambda: " + 
            numbers.stream().reduce(0, (a, b) -> a + b));
        
        System.out.println("Sum using method reference: " + 
            numbers.stream().reduce(0, Integer::sum));
        
        // When to use lambda vs method reference:
        // Use method reference when:
        // 1. You're calling a single method
        // 2. The method reference is more readable
        // 3. You're not doing any additional processing
        
        // Use lambda when:
        // 1. You need to do multiple operations
        // 2. You need to pass multiple arguments
        // 3. The lambda is more readable than the method reference
        
        // Example where lambda is better
        List<String> processed = words.stream()
                                     .map(s -> "Processed: " + s.toUpperCase() + "!")
                                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Complex processing (lambda better): " + processed);
        
        System.out.println();
    }
}

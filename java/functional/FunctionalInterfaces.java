import java.util.*;
import java.util.function.*;

/**
 * Functional Interfaces in Java
 * 
 * A functional interface is an interface that contains exactly one abstract method.
 * They can be used as lambda expressions or method references.
 * Java 8 introduced several built-in functional interfaces in java.util.function package.
 */
public class FunctionalInterfaces {
    
    // Custom functional interfaces
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
        
        // Default methods are allowed
        default int add(int a, int b) {
            return a + b;
        }
        
        // Static methods are allowed
        static int multiply(int a, int b) {
            return a * b;
        }
    }
    
    @FunctionalInterface
    interface StringValidator {
        boolean validate(String input);
    }
    
    @FunctionalInterface
    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Functional Interfaces Demo ===\n");
        
        // 1. Built-in Functional Interfaces
        builtInFunctionalInterfaces();
        
        // 2. Predicate Interface
        predicateInterface();
        
        // 3. Function Interface
        functionInterface();
        
        // 4. Consumer Interface
        consumerInterface();
        
        // 5. Supplier Interface
        supplierInterface();
        
        // 6. Custom Functional Interfaces
        customFunctionalInterfaces();
        
        // 7. Method References with Functional Interfaces
        methodReferencesWithFunctionalInterfaces();
        
        // 8. Functional Interface Composition
        functionalInterfaceComposition();
    }
    
    // 1. Built-in Functional Interfaces Overview
    static void builtInFunctionalInterfaces() {
        System.out.println("1. Built-in Functional Interfaces Overview:");
        
        // Predicate<T> - takes T, returns boolean
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println("Is empty string empty? " + isEmpty.test(""));
        System.out.println("Is 'hello' empty? " + isEmpty.test("hello"));
        
        // Function<T, R> - takes T, returns R
        Function<String, Integer> length = String::length;
        System.out.println("Length of 'functional': " + length.apply("functional"));
        
        // Consumer<T> - takes T, returns void
        Consumer<String> printer = System.out::println;
        printer.accept("This is printed by Consumer");
        
        // Supplier<T> - takes nothing, returns T
        Supplier<Double> randomValue = Math::random;
        System.out.println("Random value: " + randomValue.get());
        
        // UnaryOperator<T> - takes T, returns T (extends Function<T, T>)
        UnaryOperator<String> upperCase = String::toUpperCase;
        System.out.println("Uppercase: " + upperCase.apply("hello"));
        
        // BinaryOperator<T> - takes two T, returns T (extends BiFunction<T, T, T>)
        BinaryOperator<Integer> sum = Integer::sum;
        System.out.println("Sum: " + sum.apply(5, 3));
        
        System.out.println();
    }
    
    // 2. Predicate Interface
    static void predicateInterface() {
        System.out.println("2. Predicate Interface:");
        
        // Basic predicates
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<String> isLongString = s -> s.length() > 5;
        
        System.out.println("Is 8 even? " + isEven.test(8));
        System.out.println("Is 7 even? " + isEven.test(7));
        System.out.println("Is 10 positive? " + isPositive.test(10));
        System.out.println("Is 'programming' long? " + isLongString.test("programming"));
        
        // Predicate composition
        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);
        Predicate<Integer> isOdd = isEven.negate();
        
        System.out.println("Is 8 even and positive? " + isEvenAndPositive.test(8));
        System.out.println("Is -4 even and positive? " + isEvenAndPositive.test(-4));
        System.out.println("Is -3 even or positive? " + isEvenOrPositive.test(-3));
        System.out.println("Is 7 odd? " + isOdd.test(7));
        
        // Using predicates with collections
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> evenNumbers = numbers.stream()
                                          .filter(isEven)
                                          .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Even numbers: " + evenNumbers);
        
        List<Integer> evenAndPositive = numbers.stream()
                                              .filter(isEvenAndPositive)
                                              .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Even and positive: " + evenAndPositive);
        
        // BiPredicate - takes two arguments
        BiPredicate<String, String> startsWith = String::startsWith;
        System.out.println("Does 'hello' start with 'he'? " + startsWith.test("hello", "he"));
        
        System.out.println();
    }
    
    // 3. Function Interface
    static void functionInterface() {
        System.out.println("3. Function Interface:");
        
        // Basic functions
        Function<String, Integer> stringLength = String::length;
        Function<Integer, String> intToString = Object::toString;
        Function<String, String> upperCase = String::toUpperCase;
        
        System.out.println("Length of 'function': " + stringLength.apply("function"));
        System.out.println("42 as string: " + intToString.apply(42));
        System.out.println("Uppercase 'hello': " + upperCase.apply("hello"));
        
        // Function composition
        Function<String, String> upperCaseAndAddPrefix = upperCase.andThen(s -> "PREFIX_" + s);
        Function<String, Integer> trimAndGetLength = String::trim.andThen(String::length);
        
        System.out.println("Composed function: " + upperCaseAndAddPrefix.apply("world"));
        System.out.println("Trim and length: " + trimAndGetLength.apply("  hello  "));
        
        // Function.compose() - applies the function before
        Function<String, String> addPrefixThenUpper = upperCase.compose(s -> "prefix_" + s);
        System.out.println("Compose example: " + addPrefixThenUpper.apply("test"));
        
        // Using functions with collections
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        List<Integer> lengths = words.stream()
                                    .map(stringLength)
                                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Word lengths: " + lengths);
        
        List<String> upperWords = words.stream()
                                      .map(upperCase)
                                      .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("Uppercase words: " + upperWords);
        
        // BiFunction - takes two arguments
        BiFunction<String, String, String> concat = String::concat;
        System.out.println("Concatenation: " + concat.apply("Hello", " World"));
        
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        System.out.println("Multiplication: " + multiply.apply(6, 7));
        
        System.out.println();
    }
    
    // 4. Consumer Interface
    static void consumerInterface() {
        System.out.println("4. Consumer Interface:");
        
        // Basic consumers
        Consumer<String> printer = System.out::println;
        Consumer<String> upperPrinter = s -> System.out.println(s.toUpperCase());
        Consumer<List<String>> listPrinter = list -> list.forEach(System.out::println);
        
        printer.accept("Hello from Consumer");
        upperPrinter.accept("this will be uppercase");
        
        // Consumer chaining
        Consumer<String> printAndUpperPrint = printer.andThen(upperPrinter);
        printAndUpperPrint.accept("chained consumer");
        
        // Using consumers with collections
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        System.out.println("Printing with forEach:");
        words.forEach(printer);
        
        System.out.println("Printing with custom consumer:");
        words.forEach(word -> System.out.println("Item: " + word));
        
        // BiConsumer - takes two arguments
        BiConsumer<String, Integer> printWithIndex = (word, index) -> 
            System.out.println(index + ": " + word);
        
        System.out.println("BiConsumer example:");
        for (int i = 0; i < words.size(); i++) {
            printWithIndex.accept(words.get(i), i);
        }
        
        // Practical example: logging
        Consumer<String> logger = message -> System.out.println("[LOG] " + new Date() + ": " + message);
        logger.accept("Application started");
        logger.accept("Processing data");
        
        System.out.println();
    }
    
    // 5. Supplier Interface
    static void supplierInterface() {
        System.out.println("5. Supplier Interface:");
        
        // Basic suppliers
        Supplier<String> stringSupplier = () -> "Hello from Supplier";
        Supplier<Double> randomSupplier = Math::random;
        Supplier<Date> dateSupplier = Date::new;
        Supplier<List<String>> listSupplier = ArrayList::new;
        
        System.out.println("String supplier: " + stringSupplier.get());
        System.out.println("Random supplier: " + randomSupplier.get());
        System.out.println("Date supplier: " + dateSupplier.get());
        System.out.println("List supplier: " + listSupplier.get());
        
        // Lazy evaluation with suppliers
        Supplier<String> expensiveOperation = () -> {
            System.out.println("Performing expensive operation...");
            try {
                Thread.sleep(100); // Simulate expensive operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Expensive result";
        };
        
        System.out.println("Before calling supplier");
        String result = expensiveOperation.get(); // Only executed when needed
        System.out.println("Result: " + result);
        
        // Using suppliers for default values
        Optional<String> optional = Optional.empty();
        String value = optional.orElseGet(() -> "Default value from supplier");
        System.out.println("Default value: " + value);
        
        // Factory pattern with suppliers
        Supplier<Random> randomFactory = Random::new;
        Random random1 = randomFactory.get();
        Random random2 = randomFactory.get();
        System.out.println("Random 1: " + random1.nextInt(100));
        System.out.println("Random 2: " + random2.nextInt(100));
        
        System.out.println();
    }
    
    // 6. Custom Functional Interfaces
    static void customFunctionalInterfaces() {
        System.out.println("6. Custom Functional Interfaces:");
        
        // Using custom Calculator interface
        Calculator addition = (a, b) -> a + b;
        Calculator subtraction = (a, b) -> a - b;
        Calculator division = (a, b) -> b != 0 ? a / b : 0;
        
        System.out.println("Addition: " + addition.calculate(10, 5));
        System.out.println("Subtraction: " + subtraction.calculate(10, 5));
        System.out.println("Division: " + division.calculate(10, 5));
        
        // Using default method
        System.out.println("Default add method: " + addition.add(3, 7));
        
        // Using static method
        System.out.println("Static multiply method: " + Calculator.multiply(4, 6));
        
        // Using custom StringValidator interface
        StringValidator emailValidator = email -> email.contains("@") && email.contains(".");
        StringValidator lengthValidator = input -> input.length() >= 8;
        StringValidator notEmptyValidator = input -> !input.trim().isEmpty();
        
        String email = "user@example.com";
        System.out.println("Is valid email? " + emailValidator.validate(email));
        System.out.println("Is long enough? " + lengthValidator.validate(email));
        System.out.println("Is not empty? " + notEmptyValidator.validate(email));
        
        // Using custom TriFunction interface
        TriFunction<Integer, Integer, Integer, Integer> sumOfThree = (a, b, c) -> a + b + c;
        TriFunction<String, String, String, String> concatenateThree = (a, b, c) -> a + b + c;
        
        System.out.println("Sum of three: " + sumOfThree.apply(1, 2, 3));
        System.out.println("Concatenate three: " + concatenateThree.apply("Hello", " ", "World"));
        
        System.out.println();
    }
    
    // 7. Method References with Functional Interfaces
    static void methodReferencesWithFunctionalInterfaces() {
        System.out.println("7. Method References with Functional Interfaces:");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        
        // Static method references
        Function<String, Integer> parseInt = Integer::parseInt;
        Consumer<String> println = System.out::println;
        
        // Instance method references
        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, Integer> length = String::length;
        Predicate<String> isEmpty = String::isEmpty;
        
        // Constructor references
        Supplier<StringBuilder> sbSupplier = StringBuilder::new;
        Function<String, StringBuilder> sbFromString = StringBuilder::new;
        
        System.out.println("Using method references:");
        words.stream()
             .filter(word -> !isEmpty.test(word))
             .map(toUpperCase)
             .forEach(println);
        
        // Combining different functional interfaces
        System.out.println("\nWord lengths:");
        words.stream()
             .map(length)
             .forEach(println);
        
        System.out.println();
    }
    
    // 8. Functional Interface Composition
    static void functionalInterfaceComposition() {
        System.out.println("8. Functional Interface Composition:");
        
        // Predicate composition
        Predicate<String> isLong = s -> s.length() > 5;
        Predicate<String> startsWithA = s -> s.startsWith("A") || s.startsWith("a");
        Predicate<String> isLongAndStartsWithA = isLong.and(startsWithA);
        
        List<String> words = Arrays.asList("apple", "application", "cat", "amazing", "dog");
        
        System.out.println("Long words starting with 'a':");
        words.stream()
             .filter(isLongAndStartsWithA)
             .forEach(System.out::println);
        
        // Function composition
        Function<String, String> removeSpaces = s -> s.replaceAll("\\s+", "");
        Function<String, String> toLowerCase = String::toLowerCase;
        Function<String, Integer> getLength = String::length;
        
        Function<String, Integer> processAndGetLength = removeSpaces
            .andThen(toLowerCase)
            .andThen(getLength);
        
        String input = "  Hello World  ";
        System.out.println("Original: '" + input + "'");
        System.out.println("Processed length: " + processAndGetLength.apply(input));
        
        // Consumer composition
        Consumer<String> printOriginal = s -> System.out.println("Original: " + s);
        Consumer<String> printUpper = s -> System.out.println("Upper: " + s.toUpperCase());
        Consumer<String> printLength = s -> System.out.println("Length: " + s.length());
        
        Consumer<String> combinedConsumer = printOriginal
            .andThen(printUpper)
            .andThen(printLength);
        
        System.out.println("\nCombined consumer:");
        combinedConsumer.accept("functional");
        
        // Complex composition example
        Function<List<String>, List<String>> filterAndTransform = list -> list.stream()
            .filter(s -> s.length() > 3)
            .map(String::toUpperCase)
            .sorted()
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        List<String> testWords = Arrays.asList("hi", "hello", "world", "java", "functional", "programming");
        List<String> result = filterAndTransform.apply(testWords);
        System.out.println("Filtered and transformed: " + result);
        
        System.out.println();
    }
}

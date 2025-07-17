import java.util.*;
import java.util.function.*;

/**
 * Lambda Expressions in Java
 * 
 * Lambda expressions provide a clear and concise way to represent one method interface
 * using an expression. They enable you to treat functionality as method argument,
 * or code as data.
 */
public class LambdaExpressions {
    
    public static void main(String[] args) {
        System.out.println("=== Lambda Expressions Demo ===\n");
        
        // 1. Basic Lambda Syntax
        basicLambdaSyntax();
        
        // 2. Lambda with Collections
        lambdaWithCollections();
        
        // 3. Lambda with Custom Functional Interfaces
        customFunctionalInterfaces();
        
        // 4. Lambda with Built-in Functional Interfaces
        builtInFunctionalInterfaces();
        
        // 5. Lambda Variable Capture
        variableCapture();
    }
    
    // 1. Basic Lambda Syntax Examples
    static void basicLambdaSyntax() {
        System.out.println("1. Basic Lambda Syntax:");
        
        // Traditional anonymous class
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Traditional anonymous class");
            }
        };
        
        // Lambda expression equivalent
        Runnable r2 = () -> System.out.println("Lambda expression");
        
        r1.run();
        r2.run();
        
        // Lambda with parameters
        BinaryOperator<Integer> add = (a, b) -> a + b;
        System.out.println("5 + 3 = " + add.apply(5, 3));
        
        // Lambda with block body
        BinaryOperator<Integer> multiply = (a, b) -> {
            System.out.println("Multiplying " + a + " and " + b);
            return a * b;
        };
        System.out.println("4 * 6 = " + multiply.apply(4, 6));
        
        System.out.println();
    }
    
    // 2. Lambda with Collections
    static void lambdaWithCollections() {
        System.out.println("2. Lambda with Collections:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // Traditional iteration
        System.out.println("Traditional iteration:");
        for (String name : names) {
            System.out.println("Hello, " + name);
        }
        
        // Lambda with forEach
        System.out.println("\nLambda with forEach:");
        names.forEach(name -> System.out.println("Hello, " + name));
        
        // Method reference (even more concise)
        System.out.println("\nMethod reference:");
        names.forEach(System.out::println);
        
        // Sorting with lambda
        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort((a, b) -> a.compareTo(b));
        System.out.println("Sorted names: " + sortedNames);
        
        // Even more concise with method reference
        sortedNames.sort(String::compareTo);
        
        System.out.println();
    }
    
    // 3. Custom Functional Interfaces
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
    
    @FunctionalInterface
    interface StringProcessor {
        String process(String input);
    }
    
    static void customFunctionalInterfaces() {
        System.out.println("3. Custom Functional Interfaces:");
        
        // Using custom functional interface
        Calculator addition = (a, b) -> a + b;
        Calculator subtraction = (a, b) -> a - b;
        Calculator multiplication = (a, b) -> a * b;
        Calculator division = (a, b) -> b != 0 ? a / b : 0;
        
        int x = 10, y = 5;
        System.out.println(x + " + " + y + " = " + addition.calculate(x, y));
        System.out.println(x + " - " + y + " = " + subtraction.calculate(x, y));
        System.out.println(x + " * " + y + " = " + multiplication.calculate(x, y));
        System.out.println(x + " / " + y + " = " + division.calculate(x, y));
        
        // String processing examples
        StringProcessor upperCase = String::toUpperCase;
        StringProcessor reverse = str -> new StringBuilder(str).reverse().toString();
        StringProcessor addPrefix = str -> "Processed: " + str;
        
        String input = "Hello World";
        System.out.println("Original: " + input);
        System.out.println("Upper case: " + upperCase.process(input));
        System.out.println("Reversed: " + reverse.process(input));
        System.out.println("With prefix: " + addPrefix.process(input));
        
        System.out.println();
    }
    
    // 4. Built-in Functional Interfaces
    static void builtInFunctionalInterfaces() {
        System.out.println("4. Built-in Functional Interfaces:");
        
        // Predicate<T> - takes T, returns boolean
        Predicate<String> isLongString = str -> str.length() > 5;
        Predicate<Integer> isEven = num -> num % 2 == 0;
        
        System.out.println("Is 'Hello World' long? " + isLongString.test("Hello World"));
        System.out.println("Is 'Hi' long? " + isLongString.test("Hi"));
        System.out.println("Is 8 even? " + isEven.test(8));
        System.out.println("Is 7 even? " + isEven.test(7));
        
        // Function<T, R> - takes T, returns R
        Function<String, Integer> stringLength = String::length;
        Function<Integer, String> intToString = Object::toString;
        
        System.out.println("Length of 'Lambda': " + stringLength.apply("Lambda"));
        System.out.println("42 as string: " + intToString.apply(42));
        
        // Consumer<T> - takes T, returns void
        Consumer<String> printer = System.out::println;
        Consumer<List<String>> listPrinter = list -> list.forEach(System.out::println);
        
        printer.accept("This is printed by Consumer");
        listPrinter.accept(Arrays.asList("Item 1", "Item 2", "Item 3"));
        
        // Supplier<T> - takes nothing, returns T
        Supplier<String> randomString = () -> "Random-" + Math.random();
        Supplier<Date> currentTime = Date::new;
        
        System.out.println("Random string: " + randomString.get());
        System.out.println("Current time: " + currentTime.get());
        
        System.out.println();
    }
    
    // 5. Variable Capture in Lambdas
    static void variableCapture() {
        System.out.println("5. Variable Capture:");
        
        // Effectively final variables can be captured
        final String prefix = "Message: ";
        int multiplier = 10; // effectively final
        
        List<String> messages = Arrays.asList("Hello", "World", "Lambda");
        
        // Capturing final/effectively final variables
        messages.forEach(msg -> System.out.println(prefix + msg));
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach(num -> System.out.println(num + " * " + multiplier + " = " + (num * multiplier)));
        
        // Instance variables can also be captured
        LambdaExpressions instance = new LambdaExpressions();
        instance.instanceVariableCapture();
        
        System.out.println();
    }
    
    private String instanceMessage = "Instance variable captured!";
    
    void instanceVariableCapture() {
        Runnable r = () -> System.out.println(instanceMessage);
        r.run();
    }
}

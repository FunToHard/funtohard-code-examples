import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Java Streams API
 * 
 * The Stream API provides a powerful way to process collections of data
 * in a functional style. Streams support operations like filter, map, reduce,
 * and collect that can be chained together to create complex data processing pipelines.
 */
public class StreamsAPI {
    
    static class Person {
        private String name;
        private int age;
        private String city;
        private double salary;
        
        public Person(String name, int age, String city, double salary) {
            this.name = name;
            this.age = age;
            this.city = city;
            this.salary = salary;
        }
        
        // Getters
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getCity() { return city; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d, city='%s', salary=%.2f}", 
                               name, age, city, salary);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Java Streams API Demo ===\n");
        
        // Sample data
        List<Person> people = createSampleData();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // 1. Basic Stream Operations
        basicStreamOperations(numbers);
        
        // 2. Filtering and Mapping
        filteringAndMapping(people);
        
        // 3. Reduction Operations
        reductionOperations(numbers, people);
        
        // 4. Collecting Results
        collectingResults(people);
        
        // 5. Grouping and Partitioning
        groupingAndPartitioning(people);
        
        // 6. Advanced Stream Operations
        advancedOperations(people);
        
        // 7. Parallel Streams
        parallelStreams(numbers);
    }
    
    static List<Person> createSampleData() {
        return Arrays.asList(
            new Person("Alice", 30, "New York", 75000),
            new Person("Bob", 25, "Los Angeles", 65000),
            new Person("Charlie", 35, "Chicago", 80000),
            new Person("Diana", 28, "New York", 70000),
            new Person("Eve", 32, "Los Angeles", 85000),
            new Person("Frank", 29, "Chicago", 72000),
            new Person("Grace", 27, "New York", 68000),
            new Person("Henry", 33, "Los Angeles", 90000)
        );
    }
    
    // 1. Basic Stream Operations
    static void basicStreamOperations(List<Integer> numbers) {
        System.out.println("1. Basic Stream Operations:");
        System.out.println("Original numbers: " + numbers);
        
        // Creating streams
        Stream<Integer> stream1 = numbers.stream();
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
        IntStream stream3 = IntStream.range(1, 6);
        
        // Basic operations
        System.out.println("Even numbers: " + 
            numbers.stream()
                   .filter(n -> n % 2 == 0)
                   .collect(Collectors.toList()));
        
        System.out.println("Squared numbers: " + 
            numbers.stream()
                   .map(n -> n * n)
                   .collect(Collectors.toList()));
        
        System.out.println("First 5 numbers: " + 
            numbers.stream()
                   .limit(5)
                   .collect(Collectors.toList()));
        
        System.out.println("Skip first 3, take rest: " + 
            numbers.stream()
                   .skip(3)
                   .collect(Collectors.toList()));
        
        System.out.println();
    }
    
    // 2. Filtering and Mapping
    static void filteringAndMapping(List<Person> people) {
        System.out.println("2. Filtering and Mapping:");
        
        // Filter by age
        System.out.println("People over 30:");
        people.stream()
              .filter(p -> p.getAge() > 30)
              .forEach(System.out::println);
        
        // Filter by city and map to names
        System.out.println("\nNames of people in New York:");
        people.stream()
              .filter(p -> "New York".equals(p.getCity()))
              .map(Person::getName)
              .forEach(System.out::println);
        
        // Complex filtering and mapping
        System.out.println("\nHigh earners (>75k) with their cities:");
        people.stream()
              .filter(p -> p.getSalary() > 75000)
              .map(p -> p.getName() + " from " + p.getCity())
              .forEach(System.out::println);
        
        // FlatMap example
        List<List<String>> listOfLists = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("c", "d"),
            Arrays.asList("e", "f")
        );
        
        System.out.println("\nFlattened list:");
        listOfLists.stream()
                   .flatMap(List::stream)
                   .forEach(System.out::println);
        
        System.out.println();
    }
    
    // 3. Reduction Operations
    static void reductionOperations(List<Integer> numbers, List<Person> people) {
        System.out.println("3. Reduction Operations:");
        
        // Sum using reduce
        int sum = numbers.stream()
                         .reduce(0, Integer::sum);
        System.out.println("Sum of numbers: " + sum);
        
        // Product using reduce
        int product = numbers.stream()
                             .reduce(1, (a, b) -> a * b);
        System.out.println("Product of numbers: " + product);
        
        // Max and Min
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("Max: " + max.orElse(0));
        System.out.println("Min: " + min.orElse(0));
        
        // Count
        long count = people.stream()
                          .filter(p -> p.getAge() > 30)
                          .count();
        System.out.println("People over 30: " + count);
        
        // Average salary
        OptionalDouble avgSalary = people.stream()
                                        .mapToDouble(Person::getSalary)
                                        .average();
        System.out.println("Average salary: $" + 
            String.format("%.2f", avgSalary.orElse(0.0)));
        
        // Total salary
        double totalSalary = people.stream()
                                  .mapToDouble(Person::getSalary)
                                  .sum();
        System.out.println("Total salary: $" + String.format("%.2f", totalSalary));
        
        System.out.println();
    }
    
    // 4. Collecting Results
    static void collectingResults(List<Person> people) {
        System.out.println("4. Collecting Results:");
        
        // Collect to List
        List<String> names = people.stream()
                                  .map(Person::getName)
                                  .collect(Collectors.toList());
        System.out.println("Names as List: " + names);
        
        // Collect to Set
        Set<String> cities = people.stream()
                                  .map(Person::getCity)
                                  .collect(Collectors.toSet());
        System.out.println("Unique cities: " + cities);
        
        // Collect to Map
        Map<String, Integer> nameAgeMap = people.stream()
                                               .collect(Collectors.toMap(
                                                   Person::getName,
                                                   Person::getAge
                                               ));
        System.out.println("Name-Age mapping: " + nameAgeMap);
        
        // Joining strings
        String allNames = people.stream()
                               .map(Person::getName)
                               .collect(Collectors.joining(", "));
        System.out.println("All names joined: " + allNames);
        
        // Custom collector
        String formattedNames = people.stream()
                                     .map(Person::getName)
                                     .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Formatted names: " + formattedNames);
        
        System.out.println();
    }
    
    // 5. Grouping and Partitioning
    static void groupingAndPartitioning(List<Person> people) {
        System.out.println("5. Grouping and Partitioning:");
        
        // Group by city
        Map<String, List<Person>> peopleByCity = people.stream()
                                                       .collect(Collectors.groupingBy(Person::getCity));
        System.out.println("People grouped by city:");
        peopleByCity.forEach((city, persons) -> {
            System.out.println("  " + city + ": " + 
                persons.stream().map(Person::getName).collect(Collectors.toList()));
        });
        
        // Group by age range
        Map<String, List<Person>> peopleByAgeRange = people.stream()
                                                          .collect(Collectors.groupingBy(p -> {
                                                              if (p.getAge() < 30) return "Young";
                                                              else if (p.getAge() < 35) return "Middle";
                                                              else return "Senior";
                                                          }));
        System.out.println("\nPeople grouped by age range:");
        peopleByAgeRange.forEach((range, persons) -> {
            System.out.println("  " + range + ": " + 
                persons.stream().map(Person::getName).collect(Collectors.toList()));
        });
        
        // Partition by salary
        Map<Boolean, List<Person>> partitionedBySalary = people.stream()
                                                              .collect(Collectors.partitioningBy(p -> p.getSalary() > 75000));
        System.out.println("\nPartitioned by high salary (>75k):");
        System.out.println("  High earners: " + 
            partitionedBySalary.get(true).stream().map(Person::getName).collect(Collectors.toList()));
        System.out.println("  Regular earners: " + 
            partitionedBySalary.get(false).stream().map(Person::getName).collect(Collectors.toList()));
        
        // Counting by city
        Map<String, Long> countByCity = people.stream()
                                            .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));
        System.out.println("\nCount by city: " + countByCity);
        
        System.out.println();
    }
    
    // 6. Advanced Stream Operations
    static void advancedOperations(List<Person> people) {
        System.out.println("6. Advanced Stream Operations:");
        
        // Sorted
        System.out.println("People sorted by age:");
        people.stream()
              .sorted(Comparator.comparing(Person::getAge))
              .map(p -> p.getName() + " (" + p.getAge() + ")")
              .forEach(System.out::println);
        
        // Sorted with multiple criteria
        System.out.println("\nPeople sorted by city, then by salary (desc):");
        people.stream()
              .sorted(Comparator.comparing(Person::getCity)
                               .thenComparing(Person::getSalary, Comparator.reverseOrder()))
              .map(p -> p.getName() + " - " + p.getCity() + " - $" + p.getSalary())
              .forEach(System.out::println);
        
        // Distinct
        List<String> cities = people.stream()
                                   .map(Person::getCity)
                                   .distinct()
                                   .sorted()
                                   .collect(Collectors.toList());
        System.out.println("\nDistinct cities: " + cities);
        
        // Peek (for debugging)
        System.out.println("\nProcessing with peek:");
        people.stream()
              .filter(p -> p.getAge() > 30)
              .peek(p -> System.out.println("  Filtered: " + p.getName()))
              .map(Person::getName)
              .peek(name -> System.out.println("  Mapped: " + name))
              .collect(Collectors.toList());
        
        System.out.println();
    }
    
    // 7. Parallel Streams
    static void parallelStreams(List<Integer> numbers) {
        System.out.println("7. Parallel Streams:");
        
        // Create a larger dataset for demonstration
        List<Integer> largeNumbers = IntStream.range(1, 1000000)
                                            .boxed()
                                            .collect(Collectors.toList());
        
        // Sequential processing
        long startTime = System.currentTimeMillis();
        long sequentialSum = largeNumbers.stream()
                                        .mapToLong(i -> i * i)
                                        .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // Parallel processing
        startTime = System.currentTimeMillis();
        long parallelSum = largeNumbers.parallelStream()
                                      .mapToLong(i -> i * i)
                                      .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Sequential sum: " + sequentialSum + " (Time: " + sequentialTime + "ms)");
        System.out.println("Parallel sum: " + parallelSum + " (Time: " + parallelTime + "ms)");
        System.out.println("Speedup: " + (double)sequentialTime / parallelTime + "x");
        
        // Note: Parallel streams work best with:
        // 1. Large datasets
        // 2. CPU-intensive operations
        // 3. Stateless operations
        // 4. Operations that can be easily parallelized
        
        System.out.println();
    }
}

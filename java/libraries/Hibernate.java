// Hibernate.java - Object-Relational Mapping Examples
// Map Java objects to database tables with powerful ORM capabilities.

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import javax.persistence.*;
import java.util.*;
import java.time.LocalDateTime;

public class HibernateExamples {
    
    private static SessionFactory sessionFactory;
    
    public static void main(String[] args) {
        System.out.println("=== HIBERNATE ORM EXAMPLES ===");
        
        // Initialize Hibernate
        initializeHibernate();
        
        try {
            // 1. Basic CRUD Operations
            basicCRUDOperations();
            
            // 2. HQL Queries
            hqlQueries();
            
            // 3. Criteria API
            criteriaQueries();
            
            // 4. Relationships
            relationshipExamples();
            
            // 5. Advanced Features
            advancedFeatures();
            
        } finally {
            // Close session factory
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
    
    // Initialize Hibernate configuration
    static void initializeHibernate() {
        try {
            Configuration configuration = new Configuration();
            
            // Database configuration
            configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb");
            configuration.setProperty("hibernate.connection.username", "sa");
            configuration.setProperty("hibernate.connection.password", "");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            
            // Hibernate configuration
            configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            
            // Add annotated classes
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Department.class);
            configuration.addAnnotatedClass(Project.class);
            
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("Hibernate initialized successfully!");
            
        } catch (Exception e) {
            System.err.println("Failed to initialize Hibernate: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Basic CRUD operations
    static void basicCRUDOperations() {
        System.out.println("\n1. Basic CRUD Operations:");
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // CREATE - Save new employee
            Employee employee = new Employee("John Doe", "john@example.com", 50000.0);
            Long employeeId = (Long) session.save(employee);
            System.out.println("Created employee with ID: " + employeeId);
            
            // READ - Get employee by ID
            Employee retrievedEmployee = session.get(Employee.class, employeeId);
            System.out.println("Retrieved employee: " + retrievedEmployee);
            
            // UPDATE - Modify employee
            retrievedEmployee.setSalary(55000.0);
            session.update(retrievedEmployee);
            System.out.println("Updated employee salary to: " + retrievedEmployee.getSalary());
            
            // Save multiple employees
            List<Employee> employees = Arrays.asList(
                new Employee("Alice Smith", "alice@example.com", 60000.0),
                new Employee("Bob Johnson", "bob@example.com", 45000.0),
                new Employee("Carol Brown", "carol@example.com", 52000.0)
            );
            
            for (Employee emp : employees) {
                session.save(emp);
            }
            
            transaction.commit();
            System.out.println("All employees saved successfully!");
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // HQL (Hibernate Query Language) examples
    static void hqlQueries() {
        System.out.println("\n2. HQL Queries:");
        
        Session session = sessionFactory.openSession();
        
        try {
            // Simple HQL query
            Query<Employee> query1 = session.createQuery("FROM Employee", Employee.class);
            List<Employee> allEmployees = query1.list();
            System.out.println("Total employees: " + allEmployees.size());
            
            // HQL with WHERE clause
            Query<Employee> query2 = session.createQuery(
                "FROM Employee e WHERE e.salary > :minSalary", Employee.class);
            query2.setParameter("minSalary", 50000.0);
            List<Employee> highSalaryEmployees = query2.list();
            System.out.println("High salary employees: " + highSalaryEmployees.size());
            
            // HQL with ORDER BY
            Query<Employee> query3 = session.createQuery(
                "FROM Employee e ORDER BY e.salary DESC", Employee.class);
            List<Employee> sortedEmployees = query3.list();
            System.out.println("Top paid employee: " + sortedEmployees.get(0).getName());
            
            // HQL with aggregate functions
            Query<Double> query4 = session.createQuery(
                "SELECT AVG(e.salary) FROM Employee e", Double.class);
            Double avgSalary = query4.uniqueResult();
            System.out.println("Average salary: " + avgSalary);
            
            // HQL with GROUP BY
            Query<Object[]> query5 = session.createQuery(
                "SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department");
            List<Object[]> departmentCounts = query5.list();
            for (Object[] result : departmentCounts) {
                System.out.println("Department: " + result[0] + ", Count: " + result[1]);
            }
            
            // Named query (would be defined in Employee entity)
            Query<Employee> namedQuery = session.createNamedQuery("Employee.findByEmail", Employee.class);
            namedQuery.setParameter("email", "john@example.com");
            Employee employee = namedQuery.uniqueResult();
            if (employee != null) {
                System.out.println("Found employee by email: " + employee.getName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Criteria API examples
    static void criteriaQueries() {
        System.out.println("\n3. Criteria Queries:");
        
        Session session = sessionFactory.openSession();
        
        try {
            // JPA Criteria API (Hibernate 5.2+)
            CriteriaBuilder cb = session.getCriteriaBuilder();
            
            // Simple criteria query
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            cq.select(root);
            
            List<Employee> employees = session.createQuery(cq).getResultList();
            System.out.println("Criteria query - Total employees: " + employees.size());
            
            // Criteria with WHERE clause
            CriteriaQuery<Employee> cq2 = cb.createQuery(Employee.class);
            Root<Employee> root2 = cq2.from(Employee.class);
            cq2.select(root2).where(cb.greaterThan(root2.get("salary"), 50000.0));
            
            List<Employee> highSalaryEmps = session.createQuery(cq2).getResultList();
            System.out.println("High salary employees (Criteria): " + highSalaryEmps.size());
            
            // Criteria with multiple conditions
            CriteriaQuery<Employee> cq3 = cb.createQuery(Employee.class);
            Root<Employee> root3 = cq3.from(Employee.class);
            cq3.select(root3).where(
                cb.and(
                    cb.greaterThan(root3.get("salary"), 45000.0),
                    cb.like(root3.get("name"), "%John%")
                )
            );
            
            List<Employee> filteredEmps = session.createQuery(cq3).getResultList();
            System.out.println("Filtered employees: " + filteredEmps.size());
            
            // Criteria with ORDER BY
            CriteriaQuery<Employee> cq4 = cb.createQuery(Employee.class);
            Root<Employee> root4 = cq4.from(Employee.class);
            cq4.select(root4).orderBy(cb.desc(root4.get("salary")));
            
            List<Employee> sortedEmps = session.createQuery(cq4).getResultList();
            if (!sortedEmps.isEmpty()) {
                System.out.println("Highest paid employee: " + sortedEmps.get(0).getName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Relationship examples
    static void relationshipExamples() {
        System.out.println("\n4. Relationship Examples:");
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Create department
            Department department = new Department("Engineering", "Software Development");
            session.save(department);
            
            // Create employees with department
            Employee emp1 = new Employee("Tech Lead", "lead@example.com", 80000.0);
            emp1.setDepartment(department);
            
            Employee emp2 = new Employee("Senior Dev", "senior@example.com", 70000.0);
            emp2.setDepartment(department);
            
            session.save(emp1);
            session.save(emp2);
            
            // Create projects
            Project project1 = new Project("Web Application", "Customer portal development");
            Project project2 = new Project("Mobile App", "iOS and Android app");
            
            session.save(project1);
            session.save(project2);
            
            // Assign projects to employees (many-to-many)
            emp1.getProjects().add(project1);
            emp1.getProjects().add(project2);
            emp2.getProjects().add(project1);
            
            project1.getEmployees().add(emp1);
            project1.getEmployees().add(emp2);
            project2.getEmployees().add(emp1);
            
            session.update(emp1);
            session.update(emp2);
            
            transaction.commit();
            
            // Query relationships
            Query<Department> deptQuery = session.createQuery(
                "FROM Department d LEFT JOIN FETCH d.employees", Department.class);
            List<Department> departments = deptQuery.list();
            
            for (Department dept : departments) {
                System.out.println("Department: " + dept.getName());
                System.out.println("Employees: " + dept.getEmployees().size());
            }
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Advanced features
    static void advancedFeatures() {
        System.out.println("\n5. Advanced Features:");
        
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            // Batch processing
            System.out.println("Batch processing example:");
            for (int i = 0; i < 10; i++) {
                Employee emp = new Employee("Batch Employee " + i, 
                                         "batch" + i + "@example.com", 40000.0 + i * 1000);
                session.save(emp);
                
                if (i % 5 == 0) { // Flush every 5 entities
                    session.flush();
                    session.clear();
                }
            }
            
            // Native SQL query
            Query<?> nativeQuery = session.createNativeQuery(
                "SELECT name, salary FROM Employee WHERE salary > ?");
            nativeQuery.setParameter(1, 50000.0);
            List<?> results = nativeQuery.list();
            System.out.println("Native query results: " + results.size());
            
            // Stored procedure call (example)
            /*
            StoredProcedureQuery storedProc = session.createStoredProcedureQuery("GetEmployeeStats");
            storedProc.registerStoredProcedureParameter("minSalary", Double.class, ParameterMode.IN);
            storedProc.setParameter("minSalary", 50000.0);
            storedProc.execute();
            */
            
            // Second level cache example (would require cache configuration)
            Employee cachedEmployee = session.get(Employee.class, 1L);
            System.out.println("Employee loaded (may be from cache): " + 
                             (cachedEmployee != null ? cachedEmployee.getName() : "Not found"));
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

// Employee entity
@Entity
@Table(name = "employees")
@NamedQuery(name = "Employee.findByEmail", 
           query = "FROM Employee e WHERE e.email = :email")
class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column
    private Double salary;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();
    
    // Constructors
    public Employee() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Employee(String name, String email, Double salary) {
        this();
        this.name = name;
        this.email = email;
        this.salary = salary;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public Set<Project> getProjects() { return projects; }
    public void setProjects(Set<Project> projects) { this.projects = projects; }
    
    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', email='%s', salary=%.2f}", 
                           id, name, email, salary);
    }
}

// Department entity
@Entity
@Table(name = "departments")
class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();
    
    // Constructors
    public Department() {}
    
    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }
}

// Project entity
@Entity
@Table(name = "projects")
class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "employee_projects",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();
    
    // Constructors
    public Project() {}
    
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }
}

/*
=== HIBERNATE CONFIGURATION (hibernate.cfg.xml) ===

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="connection.driver_class">org.h2.Driver</property>
        <property name="connection.url">jdbc:h2:mem:testdb</property>
        <property name="connection.username">sa</property>
        <property name="connection.password"></property>
        
        <!-- SQL dialect -->
        <property name="dialect">org.hibernate.dialect.H2Dialect</property>
        
        <!-- Echo all executed SQL to stdout -->
        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        
        <!-- Drop and re-create the database schema on startup -->
        <property name="hbm2ddl.auto">create-drop</property>
        
        <!-- Connection pool settings -->
        <property name="connection.pool_size">10</property>
        
        <!-- Second-level cache -->
        <property name="cache.provider_class">org.hibernate.cache.internal.NoCacheProvider</property>
        
        <!-- Mapping files -->
        <mapping class="Employee"/>
        <mapping class="Department"/>
        <mapping class="Project"/>
    </session-factory>
</hibernate-configuration>

=== MAVEN DEPENDENCIES ===

<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.1.214</version>
    </dependency>
    <dependency>
        <groupId>javax.persistence</groupId>
        <artifactId>javax.persistence-api</artifactId>
        <version>2.2</version>
    </dependency>
</dependencies>

*/

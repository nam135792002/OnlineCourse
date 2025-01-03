-- Create table lessons
CREATE TABLE IF NOT EXISTS lessons(
    lesson_id INT(4) NOT NULL AUTO_INCREMENT COMMENT 'Lesson id, Primary key',
    lesson_name VARCHAR(100) NOT NULL COMMENT 'Name of lesson, not allow duplicate name lesson within same chapter',
    lesson_type VARCHAR(10) NOT NULL COMMENT 'Lesson type include: video, quiz, text',
    chapter_id INT(3) NOT NULL COMMENT 'Chapter id, Foreign key',
    lesson_order INT(3) NOT NULL COMMENT 'Order all of lesson within same chapter',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT lesson_pk PRIMARY KEY(lesson_id),
    CONSTRAINT lesson_chapter_fk FOREIGN KEY(chapter_id) REFERENCES chapters(chapter_id) ON DELETE CASCADE
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;

-- Add lesson value onto table lesson
INSERT IGNORE INTO lessons
       (lesson_name, lesson_type, chapter_id, lesson_order, created_at, created_by)
VALUES
    -- Lessons for Chapter 1: Introduction to Programming
    ("What is Programming?", "video", 1, 1, NOW(), "admin"),
    ("Installing Java and Setting up Development Environment", "text", 1, 2, NOW(), "admin"),
    ("First Java Program - Hello World", "video", 1, 3, NOW(), "admin"),
    ("Understanding the JVM", "quiz", 1, 4, NOW(), "admin"),

    -- Lessons for Chapter 2: Java Syntax
    ("Basic Syntax and Structure of Java", "video", 2, 1, NOW(), "admin"),
    ("Java Statements and Expressions", "text", 2, 2, NOW(), "admin"),
    ("Working with Identifiers and Keywords", "video", 2, 3, NOW(), "admin"),
    ("Understanding Java Comments", "quiz", 2, 4, NOW(), "admin"),

    -- Lessons for Chapter 3: Variables and Data Types
    ("Declaring Variables in Java", "video", 3, 1, NOW(), "admin"),
    ("Primitive Data Types in Java", "text", 3, 2, NOW(), "admin"),
    ("Type Casting in Java", "video", 3, 3, NOW(), "admin"),
    ("Working with Constants", "quiz", 3, 4, NOW(), "admin"),

    -- Lessons for Chapter 4: Control Structures
    ("Introduction to Control Structures", "video", 4, 1, NOW(), "admin"),
    ("If-Else Statements in Java", "text", 4, 2, NOW(), "admin"),
    ("Switch Case in Java", "video", 4, 3, NOW(), "admin"),
    ("Nested Control Statements", "quiz", 4, 4, NOW(), "admin"),

    -- Lessons for Chapter 5: Loops in Java
    ("Introduction to Loops", "video", 5, 1, NOW(), "admin"),
    ("For Loop in Java", "text", 5, 2, NOW(), "admin"),
    ("While and Do-While Loops", "video", 5, 3, NOW(), "admin"),
    ("Nested Loops", "quiz", 5, 4, NOW(), "admin"),

    -- Lessons for Chapter 6: Methods and Functions
    ("Defining Methods in Java", "video", 6, 1, NOW(), "admin"),
    ("Method Parameters and Return Types", "text", 6, 2, NOW(), "admin"),
    ("Method Overloading", "video", 6, 3, NOW(), "admin"),
    ("Recursion in Java", "quiz", 6, 4, NOW(), "admin"),

    -- Lessons for Chapter 7: Object-Oriented Programming
    ("Introduction to OOP Concepts", "video", 7, 1, NOW(), "admin"),
    ("Encapsulation in Java", "text", 7, 2, NOW(), "admin"),
    ("Understanding Inheritance", "video", 7, 3, NOW(), "admin"),
    ("Polymorphism in Java", "quiz", 7, 4, NOW(), "admin"),

    -- Lessons for Chapter 8: Classes and Objects
    ("Creating Classes and Objects in Java", "video", 8, 1, NOW(), "admin"),
    ("Constructors in Java", "text", 8, 2, NOW(), "admin"),
    ("Object Instantiation and Initialization", "video", 8, 3, NOW(), "admin"),
    ("Understanding Static Members", "quiz", 8, 4, NOW(), "admin"),

    -- Lessons for Chapter 9: Inheritance
    ("Introduction to Inheritance", "video", 9, 1, NOW(), "admin"),
    ("Method Overriding", "text", 9, 2, NOW(), "admin"),
    ("The 'super' Keyword in Java", "video", 9, 3, NOW(), "admin"),
    ("Inheritance and Code Reusability", "quiz", 9, 4, NOW(), "admin"),

    -- Lessons for Chapter 10: Polymorphism
    ("Understanding Polymorphism", "video", 10, 1, NOW(), "admin"),
    ("Compile-Time vs Run-Time Polymorphism", "text", 10, 2, NOW(), "admin"),
    ("Method Overloading vs Method Overriding", "video", 10, 3, NOW(), "admin"),
    ("Polymorphism in Java Interfaces", "quiz", 10, 4, NOW(), "admin"),

    -- Lessons for Chapter 11: Abstraction and Interfaces
    ("What is Abstraction in Java?", "video", 11, 1, NOW(), "admin"),
    ("Abstract Classes vs Interfaces", "text", 11, 2, NOW(), "admin"),
    ("Using Abstract Methods", "video", 11, 3, NOW(), "admin"),
    ("Implementing Interfaces in Java", "quiz", 11, 4, NOW(), "admin"),

    -- Lessons for Chapter 12: Exception Handling
    ("Introduction to Exception Handling", "video", 12, 1, NOW(), "admin"),
    ("Try, Catch, and Finally Blocks", "text", 12, 2, NOW(), "admin"),
    ("Throwing and Catching Exceptions", "video", 12, 3, NOW(), "admin"),
    ("Custom Exceptions in Java", "quiz", 12, 4, NOW(), "admin"),

    -- Lessons for Chapter 13: File Handling
    ("Introduction to File Handling in Java", "video", 13, 1, NOW(), "admin"),
    ("Reading from Files", "text", 13, 2, NOW(), "admin"),
    ("Writing to Files", "video", 13, 3, NOW(), "admin"),
    ("Serialization and Deserialization", "quiz", 13, 4, NOW(), "admin"),

    -- Lessons for Chapter 14: Java Collections
    ("Introduction to Java Collections Framework", "video", 14, 1, NOW(), "admin"),
    ("List, Set, and Map Interfaces", "text", 14, 2, NOW(), "admin"),
    ("Working with ArrayList and HashMap", "video", 14, 3, NOW(), "admin"),
    ("Iterators and List Iterators", "quiz", 14, 4, NOW(), "admin"),

    -- Lessons for Chapter 15: Streams and Lambda Expressions
    ("Introduction to Streams in Java", "video", 15, 1, NOW(), "admin"),
    ("Lambda Expressions in Java", "text", 15, 2, NOW(), "admin"),
    ("Functional Interfaces and Predicates", "video", 15, 3, NOW(), "admin"),
    ("Using Stream API for Data Processing", "quiz", 15, 4, NOW(), "admin"),

    -- Lessons for Chapter 16: Multithreading
    ("Introduction to Multithreading", "video", 16, 1, NOW(), "admin"),
    ("Creating and Running Threads", "text", 16, 2, NOW(), "admin"),
    ("Thread Synchronization", "video", 16, 3, NOW(), "admin"),
    ("Executor Service and Thread Pools", "quiz", 16, 4, NOW(), "admin"),

    -- Lessons for Chapter 17: Networking in Java
    ("Introduction to Java Networking", "video", 17, 1, NOW(), "admin"),
    ("Creating Server-Client Applications", "text", 17, 2, NOW(), "admin"),
    ("Socket Programming in Java", "video", 17, 3, NOW(), "admin"),
    ("Using URL and HTTP in Java", "quiz", 17, 4, NOW(), "admin"),

    -- Lessons for Chapter 18: Java GUI Programming
    ("Creating GUI Applications with Swing", "video", 18, 1, NOW(), "admin"),
    ("Handling Events in Java GUI", "text", 18, 2, NOW(), "admin"),
    ("Working with Buttons, Labels, and Text Fields", "video", 18, 3, NOW(), "admin"),
    ("Layout Managers in Java GUI", "quiz", 18, 4, NOW(), "admin"),

    -- Lessons for Chapter 19: Java Database Connectivity
    ("Connecting to Databases with JDBC", "video", 19, 1, NOW(), "admin"),
    ("Executing SQL Queries from Java", "text", 19, 2, NOW(), "admin"),
    ("Handling Result Sets and Statements", "video", 19, 3, NOW(), "admin"),
    ("Transaction Management in JDBC", "quiz", 19, 4, NOW(), "admin"),

    -- Lessons for Chapter 20: Final Project
    ("Project Requirements and Design", "video", 20, 1, NOW(), "admin"),
    ("Building the Final Project", "text", 20, 2, NOW(), "admin"),
    ("Testing and Debugging the Project", "video", 20, 3, NOW(), "admin"),
    ("Final Project Presentation", "quiz", 20, 4, NOW(), "admin");

    -- Add lesson value onto table lesson
INSERT IGNORE INTO lessons
       (lesson_name, lesson_type, chapter_id, lesson_order, created_at, created_by)
VALUES
    -- Lessons for Chapter 1: Getting Started with Spring Boot
    ("Setting Up Your Spring Boot Environment", "video", 1, 1, NOW(), "admin"),
    ("Creating a Simple Spring Boot Application", "video", 1, 2, NOW(), "admin"),
    ("Understanding Spring Boot Starters", "text", 1, 3, NOW(), "admin"),
    ("Exploring Spring Boot Autoconfiguration", "video", 1, 4, NOW(), "admin"),

    -- Lessons for Chapter 2: Creating REST APIs
    ("Introduction to RESTful Web Services", "video", 2, 1, NOW(), "admin"),
    ("Building Your First REST API with Spring Boot", "text", 2, 2, NOW(), "admin"),
    ("Mapping HTTP Methods with Spring MVC", "video", 2, 3, NOW(), "admin"),
    ("Handling Errors and Responses in REST APIs", "text", 2, 4, NOW(), "admin"),

    -- Lessons for Chapter 3: Advanced Dependency Injection
    ("Understanding Dependency Injection in Spring Boot", "video", 3, 1, NOW(), "admin"),
    ("Exploring Spring Boot Bean Scopes", "text", 3, 2, NOW(), "admin"),
    ("Working with Constructor Injection", "video", 3, 3, NOW(), "admin"),
    ("Using Profiles for Environment-Specific Configurations", "text", 3, 4, NOW(), "admin"),

    -- Lessons for Chapter 4: Spring Boot Security
    ("Introduction to Spring Security", "video", 4, 1, NOW(), "admin"),
    ("Setting Up Basic Authentication in Spring Boot", "video", 4, 2, NOW(), "admin"),
    ("Implementing JWT Authentication", "text", 4, 3, NOW(), "admin"),
    ("Configuring Role-Based Authorization", "video", 4, 4, NOW(), "admin"),

    -- Lessons for Chapter 5: Spring Boot Testing
    ("Writing Unit Tests with JUnit", "text", 5, 1, NOW(), "admin"),
    ("Mocking Dependencies with Mockito", "video", 5, 2, NOW(), "admin"),
    ("Testing REST APIs with Spring Boot", "text", 5, 3, NOW(), "admin"),
    ("Running Integration Tests in Spring Boot", "video", 5, 4, NOW(), "admin"),

    -- Lessons for Chapter 6: Spring Data JPA
    ("Introduction to Spring Data JPA", "video", 6, 1, NOW(), "admin"),
    ("Creating Entities and Repositories", "text", 6, 2, NOW(), "admin"),
    ("Query Methods in Spring Data JPA", "video", 6, 3, NOW(), "admin"),
    ("Using Pagination and Sorting in JPA", "text", 6, 4, NOW(), "admin"),

    -- Lessons for Chapter 7: Custom Queries in JPA
    ("Using JPQL for Custom Queries", "video", 7, 1, NOW(), "admin"),
    ("Native SQL Queries in Spring Data JPA", "text", 7, 2, NOW(), "admin"),
    ("Handling Complex Queries with @Query Annotation", "video", 7, 3, NOW(), "admin"),
    ("Optimizing Custom Queries for Performance", "text", 7, 4, NOW(), "admin"),

    -- Lessons for Chapter 8: Spring Boot Actuator
    ("What is Spring Boot Actuator?", "video", 8, 1, NOW(), "admin"),
    ("Adding Actuator to Your Spring Boot Application", "text", 8, 2, NOW(), "admin"),
    ("Exploring Actuator Endpoints", "video", 8, 3, NOW(), "admin"),
    ("Securing Actuator Endpoints", "text", 8, 4, NOW(), "admin"),

    -- Lessons for Chapter 9: Monitoring and Metrics
    ("Setting Up Metrics in Spring Boot", "video", 9, 1, NOW(), "admin"),
    ("Using Micrometer with Spring Boot", "text", 9, 2, NOW(), "admin"),
    ("Visualizing Metrics with Prometheus", "video", 9, 3, NOW(), "admin"),
    ("Setting Up Alerts for Critical Metrics", "text", 9, 4, NOW(), "admin"),

    -- Lessons for Chapter 10: Spring Boot Microservices
    ("Introduction to Microservices Architecture", "video", 10, 1, NOW(), "admin"),
    ("Building Microservices with Spring Boot", "text", 10, 2, NOW(), "admin"),
    ("Managing Inter-Services Communication", "video", 10, 3, NOW(), "admin"),
    ("Handling Fault Tolerance in Microservices", "text", 10, 4, NOW(), "admin"),

    -- Lessons for Chapter 11: Communication between Microservices
    ("Using RESTful APIs for Microservice Communication", "video", 11, 1, NOW(), "admin"),
    ("Messaging with RabbitMQ in Spring Boot", "text", 11, 2, NOW(), "admin"),
    ("Using Spring Cloud OpenFeign for Communication", "video", 11, 3, NOW(), "admin"),
    ("API Gateway and Routing in Microservices", "text", 11, 4, NOW(), "admin"),

    -- Lessons for Chapter 12: Service Discovery with Eureka
    ("Introduction to Service Discovery", "video", 12, 1, NOW(), "admin"),
    ("Setting Up Eureka Server in Spring Boot", "text", 12, 2, NOW(), "admin"),
    ("Registering Microservices with Eureka", "video", 12, 3, NOW(), "admin"),
    ("Client-Side Load Balancing with Eureka", "text", 12, 4, NOW(), "admin"),

    -- Lessons for Chapter 13: Load Balancing with Ribbon
    ("What is Load Balancing in Microservices?", "video", 13, 1, NOW(), "admin"),
    ("Configuring Ribbon for Load Balancing", "text", 13, 2, NOW(), "admin"),
    ("Client-Side Load Balancing with Spring Cloud", "video", 13, 3, NOW(), "admin"),
    ("Integrating Ribbon with Eureka for Dynamic Routing", "text", 13, 4, NOW(), "admin"),

    -- Lessons for Chapter 14: Circuit Breaker with Hystrix
    ("What is Circuit Breaking?", "video", 14, 1, NOW(), "admin"),
    ("Implementing Circuit Breaker with Hystrix", "text", 14, 2, NOW(), "admin"),
    ("Fallback Methods in Hystrix", "video", 14, 3, NOW(), "admin"),
    ("Monitoring and Tuning Hystrix for Better Performance", "text", 14, 4, NOW(), "admin"),

    -- Lessons for Chapter 15: Spring Boot Kafka Integration
    ("Introduction to Kafka", "video", 15, 1, NOW(), "admin"),
    ("Integrating Kafka with Spring Boot", "text", 15, 2, NOW(), "admin"),
    ("Producing Messages to Kafka", "video", 15, 3, NOW(), "admin"),
    ("Consuming Messages from Kafka", "text", 15, 4, NOW(), "admin"),

    -- Lessons for Chapter 16: Spring Boot WebFlux
    ("What is WebFlux?", "video", 16, 1, NOW(), "admin"),
    ("Setting Up WebFlux with Spring Boot", "text", 16, 2, NOW(), "admin"),
    ("Building Reactive REST APIs with WebFlux", "video", 16, 3, NOW(), "admin"),
    ("Handling Errors in Reactive Applications", "text", 16, 4, NOW(), "admin"),

    -- Lessons for Chapter 17: Spring Boot GraphQL
    ("Introduction to GraphQL", "video", 17, 1, NOW(), "admin"),
    ("Setting Up Spring Boot GraphQL", "text", 17, 2, NOW(), "admin"),
    ("Building a GraphQL Query Resolver", "video", 17, 3, NOW(), "admin"),
    ("Handling Mutations in GraphQL", "text", 17, 4, NOW(), "admin"),

    -- Lessons for Chapter 18: Deploying Spring Boot Applications
    ("Introduction to Deployment Strategies", "video", 18, 1, NOW(), "admin"),
    ("Packaging Spring Boot Applications as JARs", "text", 18, 2, NOW(), "admin"),
    ("Deploying Spring Boot on AWS", "video", 18, 3, NOW(), "admin"),
    ("Configuring Spring Boot for Production", "text", 18, 4, NOW(), "admin"),

    -- Lessons for Chapter 19: Optimizing Performance
    ("Analyzing Performance Bottlenecks", "video", 19, 1, NOW(), "admin"),
    ("Improving Application Performance with Caching", "text", 19, 2, NOW(), "admin"),
    ("Optimizing Database Access in Spring Boot", "video", 19, 3, NOW(), "admin"),
    ("Tuning Spring Boot for High Traffic", "text", 19, 4, NOW(), "admin"),

    -- Lessons for Chapter 20: Final Project
    ("Final Project Overview", "video", 20, 1, NOW(), "admin"),
    ("Planning Your Spring Boot Final Project", "text", 20, 2, NOW(), "admin"),
    ("Building the Final Project: Step-by-Step", "video", 20, 3, NOW(), "admin"),
    ("Presenting Your Spring Boot Project", "text", 20, 4, NOW(), "admin");

-- Add lesson value onto table lesson
INSERT IGNORE INTO lessons
       (lesson_name, lesson_type, chapter_id, lesson_order, created_at, created_by)
VALUES
    -- Lessons for Chapter 1: Introduction to Flutter
    ("Introduction to Flutter", "video", 1, 1, NOW(), "admin"),
    ("Setting Up Flutter Environment", "text", 1, 2, NOW(), "admin"),
    ("Creating Your First Flutter App", "video", 1, 3, NOW(), "admin"),
    ("Understanding Flutter Architecture", "text", 1, 4, NOW(), "admin"),

    -- Lessons for Chapter 2: Dart Basics
    ("Introduction to Dart", "video", 2, 1, NOW(), "admin"),
    ("Variables and Data Types in Dart", "text", 2, 2, NOW(), "admin"),
    ("Control Flow in Dart", "video", 2, 3, NOW(), "admin"),
    ("Functions and Methods in Dart", "text", 2, 4, NOW(), "admin"),

    -- Lessons for Chapter 3: Building Flutter Widgets
    ("Understanding Flutter Widgets", "video", 3, 1, NOW(), "admin"),
    ("Stateless vs Stateful Widgets", "text", 3, 2, NOW(), "admin"),
    ("Commonly Used Flutter Widgets", "video", 3, 3, NOW(), "admin"),
    ("Building Custom Widgets", "text", 3, 4, NOW(), "admin"),

    -- Lessons for Chapter 4: State Management with Provider
    ("Introduction to State Management", "video", 4, 1, NOW(), "admin"),
    ("Understanding Provider Package", "text", 4, 2, NOW(), "admin"),
    ("State Management with Provider", "video", 4, 3, NOW(), "admin"),
    ("Using Consumer and ChangeNotifier", "text", 4, 4, NOW(), "admin"),

    -- Lessons for Chapter 5: Navigation and Routing
    ("Introduction to Navigation in Flutter", "video", 5, 1, NOW(), "admin"),
    ("Routing with Named Routes", "text", 5, 2, NOW(), "admin"),
    ("Passing Data between Screens", "video", 5, 3, NOW(), "admin"),
    ("Navigation with Dynamic Routes", "text", 5, 4, NOW(), "admin"),

    -- Lessons for Chapter 6: Animations in Flutter
    ("Introduction to Flutter Animations", "video", 6, 1, NOW(), "admin"),
    ("Using AnimatedContainer", "text", 6, 2, NOW(), "admin"),
    ("Tween Animation in Flutter", "video", 6, 3, NOW(), "admin"),
    ("Advanced Animations with AnimatedBuilder", "text", 6, 4, NOW(), "admin"),

    -- Lessons for Chapter 7: Working with REST APIs
    ("Introduction to RESTful APIs", "video", 7, 1, NOW(), "admin"),
    ("Making HTTP Requests in Flutter", "text", 7, 2, NOW(), "admin"),
    ("Handling JSON Data in Flutter", "video", 7, 3, NOW(), "admin"),
    ("Using FutureBuilder for API Responses", "text", 7, 4, NOW(), "admin"),

    -- Lessons for Chapter 8: Handling User Input
    ("Handling Text Input", "video", 8, 1, NOW(), "admin"),
    ("Using Form Widgets in Flutter", "text", 8, 2, NOW(), "admin"),
    ("Validating User Input", "video", 8, 3, NOW(), "admin"),
    ("Handling Multiple Inputs", "text", 8, 4, NOW(), "admin"),

    -- Lessons for Chapter 9: Flutter Forms and Validation
    ("Creating Forms in Flutter", "video", 9, 1, NOW(), "admin"),
    ("Form Validation Techniques", "text", 9, 2, NOW(), "admin"),
    ("Using Form Keys for Validation", "video", 9, 3, NOW(), "admin"),
    ("Advanced Form Validation Techniques", "text", 9, 4, NOW(), "admin"),

    -- Lessons for Chapter 10: Database Integration
    ("Setting Up SQLite in Flutter", "video", 10, 1, NOW(), "admin"),
    ("Database CRUD Operations", "text", 10, 2, NOW(), "admin"),
    ("Using sqflite Package for Database", "video", 10, 3, NOW(), "admin"),
    ("Handling Database Transactions", "text", 10, 4, NOW(), "admin"),

    -- Lessons for Chapter 11: Local Storage
    ("Introduction to Local Storage", "video", 11, 1, NOW(), "admin"),
    ("Using SharedPreferences for Simple Storage", "text", 11, 2, NOW(), "admin"),
    ("Storing Complex Data with JSON", "video", 11, 3, NOW(), "admin"),
    ("Handling Local Files in Flutter", "text", 11, 4, NOW(), "admin"),

    -- Lessons for Chapter 12: Building Real-Time Applications
    ("Introduction to Real-Time Applications", "video", 12, 1, NOW(), "admin"),
    ("Using Firebase for Real-Time Data", "text", 12, 2, NOW(), "admin"),
    ("Real-Time Messaging with WebSockets", "video", 12, 3, NOW(), "admin"),
    ("Building a Chat Application with Firebase", "text", 12, 4, NOW(), "admin"),

    -- Lessons for Chapter 13: Testing Flutter Applications
    ("Introduction to Testing in Flutter", "video", 13, 1, NOW(), "admin"),
    ("Unit Testing with Mockito", "text", 13, 2, NOW(), "admin"),
    ("Widget Testing in Flutter", "video", 13, 3, NOW(), "admin"),
    ("Integration Testing in Flutter", "text", 13, 4, NOW(), "admin"),

    -- Lessons for Chapter 14: Publishing Apps
    ("Preparing Your App for Release", "video", 14, 1, NOW(), "admin"),
    ("Building APKs and iOS Apps", "text", 14, 2, NOW(), "admin"),
    ("Publishing Flutter Apps to Google Play", "video", 14, 3, NOW(), "admin"),
    ("Publishing Flutter Apps to the App Store", "text", 14, 4, NOW(), "admin"),

    -- Lessons for Chapter 15: Optimizing Flutter Apps
    ("Performance Optimization in Flutter", "video", 15, 1, NOW(), "admin"),
    ("Reducing App Size in Flutter", "text", 15, 2, NOW(), "admin"),
    ("Improving App Startup Time", "video", 15, 3, NOW(), "admin"),
    ("Using Flutter DevTools for Profiling", "text", 15, 4, NOW(), "admin"),

    -- Lessons for Chapter 16: Integrating Firebase
    ("Setting Up Firebase in Flutter", "video", 16, 1, NOW(), "admin"),
    ("Firebase Authentication in Flutter", "text", 16, 2, NOW(), "admin"),
    ("Firebase Firestore in Flutter", "video", 16, 3, NOW(), "admin"),
    ("Using Firebase Storage in Flutter", "text", 16, 4, NOW(), "admin"),

    -- Lessons for Chapter 17: Push Notifications
    ("Setting Up Push Notifications in Flutter", "video", 17, 1, NOW(), "admin"),
    ("Using Firebase Cloud Messaging", "text", 17, 2, NOW(), "admin"),
    ("Handling Push Notifications", "video", 17, 3, NOW(), "admin"),
    ("Customizing Push Notifications", "text", 17, 4, NOW(), "admin"),

    -- Lessons for Chapter 18: Building Cross-Platform Apps
    ("Introduction to Cross-Platform Development", "video", 18, 1, NOW(), "admin"),
    ("Building Cross-Platform Apps with Flutter", "text", 18, 2, NOW(), "admin"),
    ("Handling Platform-Specific Code", "video", 18, 3, NOW(), "admin"),
    ("Deploying Cross-Platform Apps", "text", 18, 4, NOW(), "admin"),

    -- Lessons for Chapter 19: Debugging and Troubleshooting
    ("Debugging Flutter Apps", "video", 19, 1, NOW(), "admin"),
    ("Handling Errors in Flutter", "text", 19, 2, NOW(), "admin"),
    ("Using Flutter DevTools for Debugging", "video", 19, 3, NOW(), "admin"),
    ("Troubleshooting Common Flutter Issues", "text", 19, 4, NOW(), "admin"),

    -- Lessons for Chapter 20: Capstone Project
    ("Capstone Project Overview", "video", 20, 1, NOW(), "admin"),
    ("Project Planning and Design", "text", 20, 2, NOW(), "admin"),
    ("Building Your Capstone Project", "video", 20, 3, NOW(), "admin"),
    ("Presenting Your Capstone Project", "text", 20, 4, NOW(), "admin");

INSERT IGNORE INTO lessons
       (lesson_name, lesson_type, chapter_id, lesson_order, created_at, created_by)
VALUES
    ("Introduction to React", "video", 1, 1, NOW(), "admin"),
    ("Setting Up React Environment", "text", 1, 2, NOW(), "admin"),
    ("Understanding JSX", "video", 1, 3, NOW(), "admin"),
    ("Components and Props", "text", 1, 4, NOW(), "admin"),
    ("State Management in React", "video", 2, 1, NOW(), "admin"),
    ("Lifecycle Methods", "text", 2, 2, NOW(), "admin"),
    ("Handling Events", "video", 2, 3, NOW(), "admin"),
    ("Conditional Rendering", "text", 2, 4, NOW(), "admin"),
    ("Lists and Keys", "video", 3, 1, NOW(), "admin"),
    ("React Router Basics", "text", 3, 2, NOW(), "admin"),
    ("Advanced React Routing", "video", 3, 3, NOW(), "admin"),
    ("React Hooks Overview", "text", 3, 4, NOW(), "admin"),
    ("Custom Hooks", "video", 4, 1, NOW(), "admin"),
    ("Context API", "text", 4, 2, NOW(), "admin"),
    ("State Management with Redux", "video", 4, 3, NOW(), "admin"),
    ("Optimizing Performance", "text", 4, 4, NOW(), "admin"),
    ("Server-Side Rendering with Next.js", "video", 5, 1, NOW(), "admin"),
    ("Testing React Applications", "text", 5, 2, NOW(), "admin"),
    ("Deploying React Applications", "video", 5, 3, NOW(), "admin"),
    ("Capstone Project", "text", 5, 4, NOW(), "admin"),
    ("Introduction to Cross-Platform Development", "video", 6, 1, NOW(), "admin"),
    ("Building Cross-Platform Apps with Flutter", "text", 6, 2, NOW(), "admin"),
    ("Handling Platform-Specific Code", "video", 6, 3, NOW(), "admin"),
    ("Deploying Cross-Platform Apps", "text", 6, 4, NOW(), "admin"),
    ("Debugging Flutter Apps", "video", 7, 1, NOW(), "admin"),
    ("Handling Errors in Flutter", "text", 7, 2, NOW(), "admin"),
    ("Using Flutter DevTools for Debugging", "video", 7, 3, NOW(), "admin"),
    ("Troubleshooting Common Flutter Issues", "text", 7, 4, NOW(), "admin"),
    ("Introduction to Web Development", "video", 8, 1, NOW(), "admin"),
    ("HTML Basics", "text", 8, 2, NOW(), "admin"),
    ("CSS Basics", "video", 8, 3, NOW(), "admin"),
    ("JavaScript Basics", "text", 8, 4, NOW(), "admin"),
    ("Responsive Web Design", "video", 9, 1, NOW(), "admin"),
    ("CSS Grid and Flexbox", "text", 9, 2, NOW(), "admin"),
    ("Asynchronous JavaScript", "video", 9, 3, NOW(), "admin"),
    ("JavaScript Frameworks Overview", "text", 9, 4, NOW(), "admin"),
    ("Creating React Components", "video", 10, 1, NOW(), "admin"),
    ("State and Props in React", "text", 10, 2, NOW(), "admin"),
    ("Handling Events in React", "video", 10, 3, NOW(), "admin"),
    ("Form Handling in React", "text", 10, 4, NOW(), "admin"),
    ("React Router Basics", "video", 11, 1, NOW(), "admin"),
    ("Nested Routes in React", "text", 11, 2, NOW(), "admin"),
    ("Error Boundaries", "video", 11, 3, NOW(), "admin"),
    ("React Suspense and Lazy Loading", "text", 11, 4, NOW(), "admin"),
    ("State Management with Redux", "video", 12, 1, NOW(), "admin"),
    ("Redux Middleware", "text", 12, 2, NOW(), "admin"),
    ("React Context vs Redux", "video", 12, 3, NOW(), "admin"),
    ("Optimizing Performance with React", "text", 12, 4, NOW(), "admin"),
    ("Building a Task Manager App", "video", 13, 1, NOW(), "admin"),
    ("Building a To-Do App with React", "text", 13, 2, NOW(), "admin"),
    ("Building a Weather App with React", "video", 13, 3, NOW(), "admin"),
    ("Building an E-Commerce App with React", "text", 13, 4, NOW(), "admin"),
    ("Introduction to Testing React Apps", "video", 14, 1, NOW(), "admin"),
    ("Testing React Components", "text", 14, 2, NOW(), "admin"),
    ("Debugging React Applications", "video", 14, 3, NOW(), "admin"),
    ("Using Jest for Unit Testing in React", "text", 14, 4, NOW(), "admin"),
    ("Optimizing React Performance", "video", 15, 1, NOW(), "admin"),
    ("Code Splitting in React", "text", 15, 2, NOW(), "admin"),
    ("Lazy Loading Components in React", "video", 15, 3, NOW(), "admin"),
    ("Memoization in React", "text", 15, 4, NOW(), "admin"),
    ("Introduction to Next.js", "video", 16, 1, NOW(), "admin"),
    ("Server-Side Rendering with Next.js", "text", 16, 2, NOW(), "admin"),
    ("Static Site Generation with Next.js", "video", 16, 3, NOW(), "admin"),
    ("Deploying Next.js Apps", "text", 16, 4, NOW(), "admin"),
    ("Capstone Project Overview", "video", 17, 1, NOW(), "admin"),
    ("Project Planning and Design", "text", 17, 2, NOW(), "admin"),
    ("Building Your Capstone Project", "video", 17, 3, NOW(), "admin"),
    ("Presenting Your Capstone Project", "text", 17, 4, NOW(), "admin"),
    ("Introduction to Mobile App Development", "video", 18, 1, NOW(), "admin"),
    ("Building Mobile Apps with React Native", "text", 18, 2, NOW(), "admin"),
    ("Handling Mobile-Specific UI Components", "video", 18, 3, NOW(), "admin"),
    ("Deploying React Native Apps", "text", 18, 4, NOW(), "admin"),
    ("Introduction to Progressive Web Apps", "video", 19, 1, NOW(), "admin"),
    ("Service Workers in PWAs", "text", 19, 2, NOW(), "admin"),
    ("Caching Strategies for PWAs", "video", 19, 3, NOW(), "admin"),
    ("Deploying Progressive Web Apps", "text", 19, 4, NOW(), "admin"),
    ("Final Project Overview", "video", 20, 1, NOW(), "admin"),
    ("Building the Final Project", "text", 20, 2, NOW(), "admin"),
    ("Final Project Presentation", "video", 20, 3, NOW(), "admin"),
    ("Submitting the Final Project", "text", 20, 4, NOW(), "admin");

INSERT IGNORE INTO lessons
       (lesson_name, lesson_type, chapter_id, lesson_order, created_at, created_by)
VALUES
    -- Chapter 1: Introduction to Data Science
    ("Introduction to Data Science", "video", 1, 1, NOW(), "admin"),
    ("Setting Up Python Environment", "text", 1, 2, NOW(), "admin"),
    ("Python Basics for Data Science", "video", 1, 3, NOW(), "admin"),
    ("Data Manipulation with Pandas", "text", 1, 4, NOW(), "admin"),

    -- Chapter 2: Data Visualization
    ("Data Visualization with Matplotlib", "video", 2, 1, NOW(), "admin"),
    ("Advanced Visualization with Seaborn", "text", 2, 2, NOW(), "admin"),
    ("Data Cleaning Techniques", "video", 2, 3, NOW(), "admin"),
    ("Introduction to Statistics", "text", 2, 4, NOW(), "admin"),

    -- Chapter 3: Probability and Hypothesis Testing
    ("Probability Distributions", "video", 3, 1, NOW(), "admin"),
    ("Hypothesis Testing", "text", 3, 2, NOW(), "admin"),
    ("Machine Learning Basics", "video", 3, 3, NOW(), "admin"),
    ("Supervised Learning Algorithms", "text", 3, 4, NOW(), "admin"),

    -- Chapter 4: Unsupervised Learning and Model Evaluation
    ("Unsupervised Learning Algorithms", "video", 4, 1, NOW(), "admin"),
    ("Model Evaluation Metrics", "text", 4, 2, NOW(), "admin"),
    ("Natural Language Processing Basics", "video", 4, 3, NOW(), "admin"),
    ("Deep Learning with TensorFlow", "text", 4, 4, NOW(), "admin"),

    -- Chapter 5: Time Series and Data Pipelines
    ("Time Series Analysis", "video", 5, 1, NOW(), "admin"),
    ("Building Data Pipelines", "text", 5, 2, NOW(), "admin"),
    ("Deploying Data Science Models", "video", 5, 3, NOW(), "admin"),
    ("Capstone Project", "text", 5, 4, NOW(), "admin"),

    -- Chapter 6: Data Preprocessing
    ("Data Collection and Acquisition", "video", 6, 1, NOW(), "admin"),
    ("Data Cleaning and Transformation", "text", 6, 2, NOW(), "admin"),
    ("Handling Missing Data", "video", 6, 3, NOW(), "admin"),
    ("Feature Engineering", "text", 6, 4, NOW(), "admin"),

    -- Chapter 7: Supervised Learning - Regression
    ("Introduction to Regression", "video", 7, 1, NOW(), "admin"),
    ("Linear Regression", "text", 7, 2, NOW(), "admin"),
    ("Polynomial Regression", "video", 7, 3, NOW(), "admin"),
    ("Regularization Techniques", "text", 7, 4, NOW(), "admin"),

    -- Chapter 8: Supervised Learning - Classification
    ("Introduction to Classification", "video", 8, 1, NOW(), "admin"),
    ("Logistic Regression", "text", 8, 2, NOW(), "admin"),
    ("Decision Trees", "video", 8, 3, NOW(), "admin"),
    ("Support Vector Machines", "text", 8, 4, NOW(), "admin"),

    -- Chapter 9: Unsupervised Learning - Clustering
    ("Introduction to Clustering", "video", 9, 1, NOW(), "admin"),
    ("K-Means Clustering", "text", 9, 2, NOW(), "admin"),
    ("Hierarchical Clustering", "video", 9, 3, NOW(), "admin"),
    ("DBSCAN Clustering", "text", 9, 4, NOW(), "admin"),

    -- Chapter 10: Dimensionality Reduction
    ("Introduction to Dimensionality Reduction", "video", 10, 1, NOW(), "admin"),
    ("Principal Component Analysis (PCA)", "text", 10, 2, NOW(), "admin"),
    ("t-SNE for Data Visualization", "video", 10, 3, NOW(), "admin"),
    ("Linear Discriminant Analysis (LDA)", "text", 10, 4, NOW(), "admin"),

    -- Chapter 11: Natural Language Processing - Basics
    ("Text Preprocessing Techniques", "video", 11, 1, NOW(), "admin"),
    ("Tokenization and Lemmatization", "text", 11, 2, NOW(), "admin"),
    ("Bag of Words Model", "video", 11, 3, NOW(), "admin"),
    ("TF-IDF and Word Embeddings", "text", 11, 4, NOW(), "admin"),

    -- Chapter 12: Deep Learning - Neural Networks
    ("Introduction to Neural Networks", "video", 12, 1, NOW(), "admin"),
    ("Feedforward Neural Networks", "text", 12, 2, NOW(), "admin"),
    ("Backpropagation Algorithm", "video", 12, 3, NOW(), "admin"),
    ("Convolutional Neural Networks (CNN)", "text", 12, 4, NOW(), "admin"),

    -- Chapter 13: Deep Learning - Advanced Topics
    ("Recurrent Neural Networks (RNN)", "video", 13, 1, NOW(), "admin"),
    ("Generative Adversarial Networks (GAN)", "text", 13, 2, NOW(), "admin"),
    ("Autoencoders", "video", 13, 3, NOW(), "admin"),
    ("Transfer Learning", "text", 13, 4, NOW(), "admin"),

    -- Chapter 14: Model Deployment
    ("Model Deployment Techniques", "video", 14, 1, NOW(), "admin"),
    ("Building REST APIs for ML Models", "text", 14, 2, NOW(), "admin"),
    ("Deploying Models on Cloud", "video", 14, 3, NOW(), "admin"),
    ("Model Monitoring and Maintenance", "text", 14, 4, NOW(), "admin"),

    -- Chapter 15: Time Series Forecasting
    ("Time Series Data Analysis", "video", 15, 1, NOW(), "admin"),
    ("ARIMA Model for Forecasting", "text", 15, 2, NOW(), "admin"),
    ("Exponential Smoothing", "video", 15, 3, NOW(), "admin"),
    ("Seasonality and Trend Analysis", "text", 15, 4, NOW(), "admin"),

    -- Chapter 16: Ethics in Data Science
    ("Ethics in Data Science", "video", 16, 1, NOW(), "admin"),
    ("Bias in Machine Learning Models", "text", 16, 2, NOW(), "admin"),
    ("Privacy and Data Protection", "video", 16, 3, NOW(), "admin"),
    ("Fairness in AI Systems", "text", 16, 4, NOW(), "admin"),

    -- Chapter 17: AI and Automation
    ("Introduction to AI and Automation", "video", 17, 1, NOW(), "admin"),
    ("AI in Business Applications", "text", 17, 2, NOW(), "admin"),
    ("Robotic Process Automation (RPA)", "video", 17, 3, NOW(), "admin"),
    ("AI-powered Decision Making", "text", 17, 4, NOW(), "admin"),

    -- Chapter 18: Big Data and Data Lakes
    ("Introduction to Big Data", "video", 18, 1, NOW(), "admin"),
    ("Data Lakes and Warehouses", "text", 18, 2, NOW(), "admin"),
    ("Hadoop and Spark Frameworks", "video", 18, 3, NOW(), "admin"),
    ("NoSQL Databases", "text", 18, 4, NOW(), "admin"),

    -- Chapter 19: Data Science Tools
    ("Overview of Data Science Tools", "video", 19, 1, NOW(), "admin"),
    ("Jupyter Notebooks", "text", 19, 2, NOW(), "admin"),
    ("Git and Version Control for Data Science", "video", 19, 3, NOW(), "admin"),
    ("Data Science Workflow", "text", 19, 4, NOW(), "admin"),

    -- Chapter 20: Capstone Project
    ("Capstone Project Overview", "video", 20, 1, NOW(), "admin"),
    ("Project Planning and Design", "text", 20, 2, NOW(), "admin"),
    ("Building Your Capstone Project", "video", 20, 3, NOW(), "admin"),
    ("Presenting Your Capstone Project", "text", 20, 4, NOW(), "admin");

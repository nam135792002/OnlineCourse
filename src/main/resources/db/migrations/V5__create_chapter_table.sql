-- Create table chapters
CREATE TABLE IF NOT EXISTS chapters(
    chapter_id INT(3) NOT NULL AUTO_INCREMENT COMMENT 'Chapter id, Primary key',
    chapter_name VARCHAR(100) NOT NULL COMMENT 'Name of chapter. not allow duplicate name chapter within same course',
    chapter_orders INT(2) NOT NULL COMMENT 'Order all of chapter within same course',
    course_id INT(3) NOT NULL COMMENT 'Foreign key chapter table',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT chapter_pk PRIMARY KEY(chapter_id),
    CONSTRAINT chapter_course_fk FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Add chapter value onto table chapters
INSERT IGNORE INTO chapters
       (chapter_name, chapter_orders, course_id, created_at, created_by)
VALUES
       -- Chapters for Course 1: Introduction to Java
    ("Introduction to Programming", 1, 1, NOW(), "admin"),
    ("Java Syntax", 2, 1, NOW(), "admin"),
    ("Variables and Data Types", 3, 1, NOW(), "admin"),
    ("Control Structures", 4, 1, NOW(), "admin"),
    ("Loops in Java", 5, 1, NOW(), "admin"),
    ("Methods and Functions", 6, 1, NOW(), "admin"),
    ("Object-Oriented Programming", 7, 1, NOW(), "admin"),
    ("Classes and Objects", 8, 1, NOW(), "admin"),
    ("Inheritance", 9, 1, NOW(), "admin"),
    ("Polymorphism", 10, 1, NOW(), "admin"),
    ("Abstraction and Interfaces", 11, 1, NOW(), "admin"),
    ("Exception Handling", 12, 1, NOW(), "admin"),
    ("File Handling", 13, 1, NOW(), "admin"),
    ("Java Collections", 14, 1, NOW(), "admin"),
    ("Streams and Lambda Expressions", 15, 1, NOW(), "admin"),
    ("Multithreading", 16, 1, NOW(), "admin"),
    ("Networking in Java", 17, 1, NOW(), "admin"),
    ("Java GUI Programming", 18, 1, NOW(), "admin"),
    ("Java Database Connectivity", 19, 1, NOW(), "admin"),
    ("Final Project", 20, 1, NOW(), "admin"),

    -- Chapters for Course 2: Advanced Spring Boot
    ("Getting Started with Spring Boot", 1, 2, NOW(), "admin"),
    ("Creating REST APIs", 2, 2, NOW(), "admin"),
    ("Advanced Dependency Injection", 3, 2, NOW(), "admin"),
    ("Spring Boot Security", 4, 2, NOW(), "admin"),
    ("Spring Boot Testing", 5, 2, NOW(), "admin"),
    ("Spring Data JPA", 6, 2, NOW(), "admin"),
    ("Custom Queries in JPA", 7, 2, NOW(), "admin"),
    ("Spring Boot Actuator", 8, 2, NOW(), "admin"),
    ("Monitoring and Metrics", 9, 2, NOW(), "admin"),
    ("Spring Boot Microservices", 10, 2, NOW(), "admin"),
    ("Communication between Microservices", 11, 2, NOW(), "admin"),
    ("Service Discovery with Eureka", 12, 2, NOW(), "admin"),
    ("Load Balancing with Ribbon", 13, 2, NOW(), "admin"),
    ("Circuit Breaker with Hystrix", 14, 2, NOW(), "admin"),
    ("Spring Boot Kafka Integration", 15, 2, NOW(), "admin"),
    ("Spring Boot WebFlux", 16, 2, NOW(), "admin"),
    ("Spring Boot GraphQL", 17, 2, NOW(), "admin"),
    ("Deploying Spring Boot Applications", 18, 2, NOW(), "admin"),
    ("Optimizing Performance", 19, 2, NOW(), "admin"),
    ("Final Project", 20, 2, NOW(), "admin"),

    -- Chapters for Course 3: Mobile App Development with Flutter
    ("Introduction to Flutter", 1, 3, NOW(), "admin"),
    ("Dart Basics", 2, 3, NOW(), "admin"),
    ("Building Flutter Widgets", 3, 3, NOW(), "admin"),
    ("State Management with Provider", 4, 3, NOW(), "admin"),
    ("Navigation and Routing", 5, 3, NOW(), "admin"),
    ("Animations in Flutter", 6, 3, NOW(), "admin"),
    ("Working with REST APIs", 7, 3, NOW(), "admin"),
    ("Handling User Input", 8, 3, NOW(), "admin"),
    ("Flutter Forms and Validation", 9, 3, NOW(), "admin"),
    ("Database Integration", 10, 3, NOW(), "admin"),
    ("Local Storage", 11, 3, NOW(), "admin"),
    ("Building Real-Time Applications", 12, 3, NOW(), "admin"),
    ("Testing Flutter Applications", 13, 3, NOW(), "admin"),
    ("Publishing Apps", 14, 3, NOW(), "admin"),
    ("Optimizing Flutter Apps", 15, 3, NOW(), "admin"),
    ("Integrating Firebase", 16, 3, NOW(), "admin"),
    ("Push Notifications", 17, 3, NOW(), "admin"),
    ("Building Cross-Platform Apps", 18, 3, NOW(), "admin"),
    ("Debugging and Troubleshooting", 19, 3, NOW(), "admin"),
    ("Capstone Project", 20, 3, NOW(), "admin");

-- Chapters for Course 4: Web Development with React
INSERT IGNORE INTO chapters
       (chapter_name, chapter_orders, course_id, created_at, created_by)
VALUES
    ("Introduction to React", 1, 4, NOW(), "admin"),
    ("Setting Up React Environment", 2, 4, NOW(), "admin"),
    ("Understanding JSX", 3, 4, NOW(), "admin"),
    ("Components and Props", 4, 4, NOW(), "admin"),
    ("State Management in React", 5, 4, NOW(), "admin"),
    ("Lifecycle Methods", 6, 4, NOW(), "admin"),
    ("Handling Events", 7, 4, NOW(), "admin"),
    ("Conditional Rendering", 8, 4, NOW(), "admin"),
    ("Lists and Keys", 9, 4, NOW(), "admin"),
    ("React Router Basics", 10, 4, NOW(), "admin"),
    ("Advanced React Routing", 11, 4, NOW(), "admin"),
    ("React Hooks Overview", 12, 4, NOW(), "admin"),
    ("Custom Hooks", 13, 4, NOW(), "admin"),
    ("Context API", 14, 4, NOW(), "admin"),
    ("State Management with Redux", 15, 4, NOW(), "admin"),
    ("Optimizing Performance", 16, 4, NOW(), "admin"),
    ("Server-Side Rendering with Next.js", 17, 4, NOW(), "admin"),
    ("Testing React Applications", 18, 4, NOW(), "admin"),
    ("Deploying React Applications", 19, 4, NOW(), "admin"),
    ("Capstone Project", 20, 4, NOW(), "admin");

-- Chapters for Course 5: Data Science with Python
INSERT IGNORE INTO chapters
       (chapter_name, chapter_orders, course_id, created_at, created_by)
VALUES
    ("Introduction to Data Science", 1, 5, NOW(), "admin"),
    ("Setting Up Python Environment", 2, 5, NOW(), "admin"),
    ("Python Basics for Data Science", 3, 5, NOW(), "admin"),
    ("Data Manipulation with Pandas", 4, 5, NOW(), "admin"),
    ("Data Visualization with Matplotlib", 5, 5, NOW(), "admin"),
    ("Advanced Visualization with Seaborn", 6, 5, NOW(), "admin"),
    ("Data Cleaning Techniques", 7, 5, NOW(), "admin"),
    ("Introduction to Statistics", 8, 5, NOW(), "admin"),
    ("Probability Distributions", 9, 5, NOW(), "admin"),
    ("Hypothesis Testing", 10, 5, NOW(), "admin"),
    ("Machine Learning Basics", 11, 5, NOW(), "admin"),
    ("Supervised Learning Algorithms", 12, 5, NOW(), "admin"),
    ("Unsupervised Learning Algorithms", 13, 5, NOW(), "admin"),
    ("Model Evaluation Metrics", 14, 5, NOW(), "admin"),
    ("Natural Language Processing Basics", 15, 5, NOW(), "admin"),
    ("Deep Learning with TensorFlow", 16, 5, NOW(), "admin"),
    ("Time Series Analysis", 17, 5, NOW(), "admin"),
    ("Building Data Pipelines", 18, 5, NOW(), "admin"),
    ("Deploying Data Science Models", 19, 5, NOW(), "admin"),
    ("Capstone Project", 20, 5, NOW(), "admin");

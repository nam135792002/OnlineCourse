-- Create table COURSE_INFO
CREATE TABLE IF NOT EXISTS course_info(
    course_info_id INT(3) NOT NULL AUTO_INCREMENT COMMENT 'Course info id, Primary key',
    value TEXT NOT NULL COMMENT 'Description shortly about course',
    type VARCHAR(20) NOT NULL COMMENT 'Type info include in TARGET OR REQUIREMENT',
    course_id INT(2) NOT NULL COMMENT 'Foreign key course table',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT course_info_pk PRIMARY KEY(course_info_id),
    CONSTRAINT course_info_fk FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Add course info value
INSERT INTO course_info (value, type, course_id, created_at, created_by)
VALUES
    -- Course 1: Introduction to Java
    ("Understand basic syntax", "REQUIREMENT", 1, NOW(), "Admin"),
    ("Object-oriented programming concepts", "TARGET", 1, NOW(), "Admin"),
    ("Java variables and data types", "TARGET", 1, NOW(), "Admin"),
    ("Control structures in Java", "REQUIREMENT", 1, NOW(), "Admin"),
    ("Build basic Java programs", "TARGET", 1, NOW(), "Admin"),

    -- Course 2: Advanced Spring Boot
    ("Knowledge of Spring Framework", "REQUIREMENT", 2, NOW(), "Admin"),
    ("Build scalable microservices", "TARGET", 2, NOW(), "Admin"),
    ("Integrate Spring Security", "TARGET", 2, NOW(), "Admin"),
    ("Use Spring Boot Actuator", "TARGET", 2, NOW(), "Admin"),
    ("Implement advanced RESTful APIs", "REQUIREMENT", 2, NOW(), "Admin"),

    -- Course 3: Mobile App Development with Flutter
    ("Basic programming knowledge", "REQUIREMENT", 3, NOW(), "Admin"),
    ("Build cross-platform apps", "TARGET", 3, NOW(), "Admin"),
    ("Use Dart programming language", "TARGET", 3, NOW(), "Admin"),
    ("Understand Flutter widgets", "TARGET", 3, NOW(), "Admin"),
    ("Debug mobile applications", "REQUIREMENT", 3, NOW(), "Admin"),

    -- Course 4: Web Development with React
    ("Basic knowledge of JavaScript", "REQUIREMENT", 4, NOW(), "Admin"),
    ("Understand React components", "TARGET", 4, NOW(), "Admin"),
    ("Build dynamic web applications", "TARGET", 4, NOW(), "Admin"),
    ("Work with React hooks", "REQUIREMENT", 4, NOW(), "Admin"),
    ("Use state management in React", "TARGET", 4, NOW(), "Admin"),

    -- Course 5: Data Science with Python
    ("Basic programming skills", "REQUIREMENT", 5, NOW(), "Admin"),
    ("Understand Python libraries", "REQUIREMENT", 5, NOW(), "Admin"),
    ("Perform data visualization", "TARGET", 5, NOW(), "Admin"),
    ("Use machine learning algorithms", "TARGET", 5, NOW(), "Admin"),
    ("Analyze large datasets", "TARGET", 5, NOW(), "Admin");



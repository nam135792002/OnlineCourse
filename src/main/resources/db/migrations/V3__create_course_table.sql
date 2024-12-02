-- Create table COURSE
CREATE TABLE IF NOT EXISTS courses(
    course_id INT(2) NOT NULL AUTO_INCREMENT COMMENT 'Course id, Primary key',
    course_title VARCHAR(60) NOT NULL UNIQUE COMMENT 'Course title is unique',
    course_slug VARCHAR(70) NOT NULL UNIQUE COMMENT 'Course slug is unique',
    description TEXT NOT NULL COMMENT 'Course description detail',
    thumbnail VARCHAR(100) NOT NULL COMMENT 'Avatar of course',
    price INT NOT NULL COMMENT 'Price of course VND',
    discount FLOAT COMMENT 'Discount of course is format by 0.xx',
    student_count INT COMMENT 'Number of student registered that course',
    published_at DATETIME COMMENT 'Date of course is published',
    is_enabled BOOLEAN COMMENT 'Status of course is blocked or unblocked',
    is_published BOOLEAN COMMENT 'Status of course is released or coming soon',
    is_finished BOOLEAN COMMENT 'Status of course is finish or continue',
    category_id INT(2) NOT NULL COMMENT 'FK for category table',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT course_pk PRIMARY KEY(course_id),
    CONSTRAINT course_category_fk FOREIGN KEY(category_id) REFERENCES categories(category_id) ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Add courses value
INSERT IGNORE INTO courses
    (course_title, course_slug, description, thumbnail, price, discount, student_count, published_at,
     is_enabled, is_published, is_finished, category_id, created_at)
VALUES
    -- Course 1
    ("Introduction to Java",
     "introduction-to-java",
     "Learn the basics of Java programming, including syntax, variables, and object-oriented concepts.",
     "https://example.com/images/java-course.jpg",
     2000000,
     0.1,
     150,
     "2024-01-01 10:00:00",
     TRUE,
     TRUE,
     FALSE,
     1,
     NOW()),

    -- Course 2
    ("Advanced Spring Boot",
     "advanced-spring-boot",
     "Master advanced topics in Spring Boot, such as microservices, security, and integration.",
     "https://example.com/images/spring-boot-course.jpg",
     3000000,
     0.15,
     200,
     "2024-02-01 10:00:00",
     TRUE,
     TRUE,
     TRUE,
     1,
     NOW()),

    -- Course 3
    ("Mobile App Development with Flutter",
     "mobile-app-flutter",
     "Create beautiful, high-performance mobile apps with Flutter for iOS and Android.",
     "https://example.com/images/flutter-course.jpg",
     2500000,
     0.2,
     120,
     "2024-03-01 10:00:00",
     TRUE,
     FALSE,
     FALSE,
     2,
     NOW()),

    -- Course 4
    ("Web Development with React",
     "web-development-react",
     "Learn React to build dynamic and interactive web applications from scratch.",
     "https://example.com/images/react-course.jpg",
     1800000,
     0.05,
     300,
     "2024-04-01 10:00:00",
     TRUE,
     TRUE,
     TRUE,
     1,
     NOW()),

    -- Course 5
    ("Data Science with Python",
     "data-science-python",
     "Learn data analysis, visualization, and machine learning using Python.",
     "https://example.com/images/python-course.jpg",
     3500000,
     0.25,
     80,
     "2024-05-01 10:00:00",
     TRUE,
     TRUE,
     FALSE,
     2,
     NOW());


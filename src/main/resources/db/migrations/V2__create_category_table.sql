-- Create table CATEGORY
CREATE TABLE IF NOT EXISTS categories(
    category_id INT(2) NOT NULL AUTO_INCREMENT COMMENT 'Category id, Primary Key',
    category_name VARCHAR(45) NOT NULL UNIQUE COMMENT 'Category name',
    category_slug VARCHAR(50) NOT NULL UNIQUE COMMENT 'Slug of category name',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT category_pk PRIMARY KEY(category_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Add categories value
INSERT IGNORE INTO categories
    (category_name, category_slug, created_at)
VALUES
    ("Lập trình Web", "lap-trinh-web", NOW()),
    ("Lập trình Mobile", "lap-trinh-mobile", NOW());
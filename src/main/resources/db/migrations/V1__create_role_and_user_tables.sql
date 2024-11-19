-- Create table ROLE
CREATE TABLE IF NOT EXISTS roles(
    role_id INT(2) NOT NULL COMMENT 'Role id, Primary Key',
    role_name VARCHAR(15) NOT NULL UNIQUE COMMENT 'Role name',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT role_pk PRIMARY KEY(role_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Create table USER
CREATE TABLE IF NOT EXISTS users(
    user_id INT(2) NOT NULL AUTO_INCREMENT COMMENT 'User id, Primary Key',
    full_name VARCHAR(45) NOT NULL COMMENT 'Full name',
    user_name VARCHAR(45) NOT NULL UNIQUE COMMENT 'Username of user is unique',
    email VARCHAR(30) NOT NULL UNIQUE COMMENT 'Email is unique',
    phone_number VARCHAR(11) NOT NULL UNIQUE COMMENT 'Phone number only accept phone vietnamese(+84)',
    photo VARCHAR(100) COMMENT 'Avatar of user, only save link url from cloudinary',
    password VARCHAR(64) NOT NULL COMMENT 'Password',
    enabled BOOLEAN COMMENT 'Confirm activation of user',
    verification_code VARCHAR(64) COMMENT 'verify code when user register account successfully, system will send to user',
    reset_password_token VARCHAR(30) COMMENT 'reset password token when user want to reset password, system will send to user',
    role_id INT(2) NOT NULL COMMENT 'FK for ROLE table',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created date',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated date',
    created_by VARCHAR(50) COMMENT 'Created by',
    updated_by VARCHAR(50) COMMENT 'Updated by',
    CONSTRAINT user_pk PRIMARY KEY(user_id),
    CONSTRAINT users_role_fk FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

-- Add roles values
INSERT IGNORE INTO roles
    (role_id, role_name, created_at)
VALUES
    (1, "ROLE_ADMIN", NOW()),
    (2, "ROLE_CUSTOMER", NOW());

-- Add users values
INSERT IGNORE INTO users
    (full_name, user_name, email, phone_number, password, enabled, role_id, created_at)
VALUES
    ("Nguyễn Phương Nam", "namnguyen", "phuongnam@gmail.com", "0334066055", "$2y$10$6WTPfH9fYWv146apEPvSv.vmBdlzcT7a0WwYvZWYtFBXmFERac8ze", true, 1, NOW()), -- P@ssw0rd
    ("Nguyễn Phương Tay", "taynguyen", "phuongtay@gmail.com", "0334066066", "$2y$10$6WTPfH9fYWv146apEPvSv.vmBdlzcT7a0WwYvZWYtFBXmFERac8ze", true, 1, NOW()); -- P@ssw0rd

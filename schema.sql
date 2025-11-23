-- Timetable Database Schema
-- MySQL Database Schema for Court Timetable Management System

-- Create database
CREATE DATABASE IF NOT EXISTS timetable CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE timetable;

-- Note: The entities reference schema "test" and "test22" but using single database "timetable" as per application.properties

-- Create user table
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create user_role table (for role mapping)
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    roles VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX idx_user_roles (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create hall table
CREATE TABLE IF NOT EXISTS hall (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date DATE,
    hiddencolloms TEXT,
    INDEX idx_hall_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create status table
CREATE TABLE IF NOT EXISTS status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    color VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create parameter table (for UI customization)
CREATE TABLE IF NOT EXISTS parameter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    parameter VARCHAR(255),
    textcolor VARCHAR(50),
    textfont VARCHAR(100),
    textsize INT,
    textbackground VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create event table
CREATE TABLE IF NOT EXISTS event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    defendant TEXT,
    plaintiff TEXT,
    contestation TEXT,
    description TEXT,
    date DATE,
    time TIME,
    composition TEXT,
    additionalstatus TEXT,
    idStatus INT NOT NULL,
    idHall INT NOT NULL,
    hide TINYINT(1) DEFAULT 0,
    ordernumber INT DEFAULT 0,
    INDEX idx_event_date (date),
    INDEX idx_event_hall (idHall),
    INDEX idx_event_date_hall (date, idHall),
    INDEX idx_event_status (idStatus),
    INDEX idx_event_number (number),
    FOREIGN KEY (idHall) REFERENCES hall(id) ON DELETE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES status(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default status values
INSERT INTO status (status, color) VALUES
    ('Scheduled', '#00FF00'),
    ('In Progress', '#FFFF00'),
    ('Completed', '#0000FF'),
    ('Cancelled', '#FF0000'),
    ('Postponed', '#FFA500')
ON DUPLICATE KEY UPDATE status=status;

-- Insert default parameter values (for UI customization)
INSERT INTO parameter (parameter, textcolor, textfont, textsize, textbackground) VALUES
    ('formHall', '#ff0000', 'Arial', 20, '#4cafff'),
    ('formTabletitle', '#ff0000', 'Arial', 20, '#4cafff'),
    ('formText', '#ff0000', 'Arial', 20, '#4cafff')
ON DUPLICATE KEY UPDATE parameter=parameter;

-- Insert default hall
INSERT INTO hall (name, date) VALUES
    ('Main Hall', CURDATE())
ON DUPLICATE KEY UPDATE name=name;

-- Create database user
-- Note: Adjust password and permissions as needed for production
CREATE USER IF NOT EXISTS 'judge'@'localhost' IDENTIFIED BY 'judge';
GRANT SELECT, INSERT, UPDATE, DELETE ON timetable.* TO 'judge'@'localhost';
FLUSH PRIVILEGES;

-- Insert default admin user (password: admin - plain text, change in production!)
-- Note: This uses NoOpPasswordEncoder, which is NOT recommended for production
INSERT INTO user (username, password, active) VALUES
    ('admin', 'admin', 1)
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_role (user_id, roles) VALUES
    ((SELECT id FROM user WHERE username = 'admin'), 'SUPERADMIN')
ON DUPLICATE KEY UPDATE roles=roles;

-- Create Lucene index directory (requires manual creation on filesystem)
-- mkdir -p /opt/indexes/lucene/
-- chown -R mysql:mysql /opt/indexes/lucene/

-- Timetable Database Schema for PostgreSQL
-- PostgreSQL Database Schema for Court Timetable Management System

-- Create database (run this separately as superuser if needed)
-- CREATE DATABASE timetable WITH ENCODING 'UTF8' LC_COLLATE='en_US.UTF-8' LC_CTYPE='en_US.UTF-8';

-- Connect to the database
\c timetable;

-- Create user table
CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active SMALLINT NOT NULL DEFAULT 1,
    CONSTRAINT chk_active CHECK (active IN (0, 1))
);

CREATE INDEX IF NOT EXISTS idx_username ON "user"(username);

-- Create user_role table (for role mapping)
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    roles VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_user_roles ON user_role(user_id);

-- Create hall table
CREATE TABLE IF NOT EXISTS hall (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    date DATE,
    hiddencolloms TEXT
);

CREATE INDEX IF NOT EXISTS idx_hall_name ON hall(name);

-- Create status table
CREATE TABLE IF NOT EXISTS status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(255),
    color VARCHAR(50)
);

-- Create parameter table (for UI customization)
CREATE TABLE IF NOT EXISTS parameter (
    id SERIAL PRIMARY KEY,
    parameter VARCHAR(255),
    textcolor VARCHAR(50),
    textfont VARCHAR(100),
    textsize INTEGER,
    textbackground VARCHAR(50)
);

-- Create event table
CREATE TABLE IF NOT EXISTS event (
    id SERIAL PRIMARY KEY,
    number VARCHAR(255),
    defendant TEXT,
    plaintiff TEXT,
    contestation TEXT,
    description TEXT,
    date DATE,
    time TIME,
    composition TEXT,
    additionalstatus TEXT,
    idStatus INTEGER NOT NULL,
    idHall INTEGER NOT NULL,
    hide SMALLINT DEFAULT 0,
    ordernumber INTEGER DEFAULT 0,
    FOREIGN KEY (idHall) REFERENCES hall(id) ON DELETE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES status(id) ON DELETE RESTRICT,
    CONSTRAINT chk_hide CHECK (hide IN (0, 1))
);

CREATE INDEX IF NOT EXISTS idx_event_date ON event(date);
CREATE INDEX IF NOT EXISTS idx_event_hall ON event(idHall);
CREATE INDEX IF NOT EXISTS idx_event_date_hall ON event(date, idHall);
CREATE INDEX IF NOT EXISTS idx_event_status ON event(idStatus);
CREATE INDEX IF NOT EXISTS idx_event_number ON event(number);

-- Insert default status values
INSERT INTO status (status, color) VALUES
    ('Scheduled', '#00FF00'),
    ('In Progress', '#FFFF00'),
    ('Completed', '#0000FF'),
    ('Cancelled', '#FF0000'),
    ('Postponed', '#FFA500')
ON CONFLICT DO NOTHING;

-- Insert default parameter values (for UI customization)
INSERT INTO parameter (parameter, textcolor, textfont, textsize, textbackground) VALUES
    ('formHall', '#ff0000', 'Arial', 20, '#4cafff'),
    ('formTabletitle', '#ff0000', 'Arial', 20, '#4cafff'),
    ('formText', '#ff0000', 'Arial', 20, '#4cafff')
ON CONFLICT DO NOTHING;

-- Insert default hall
INSERT INTO hall (name, date) VALUES
    ('Main Hall', CURRENT_DATE)
ON CONFLICT DO NOTHING;

-- Create database user
-- Note: Adjust password and permissions as needed for production
-- DROP USER IF EXISTS judge;
CREATE USER judge WITH PASSWORD 'judge';
GRANT CONNECT ON DATABASE timetable TO judge;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO judge;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO judge;

-- Grant permissions on future tables (PostgreSQL 10+)
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO judge;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE, SELECT ON SEQUENCES TO judge;

-- Insert default admin user (password: admin - plain text, change in production!)
-- Note: This uses NoOpPasswordEncoder, which is NOT recommended for production
INSERT INTO "user" (username, password, active) VALUES
    ('admin', 'admin', 1)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_role (user_id, roles) VALUES
    ((SELECT id FROM "user" WHERE username = 'admin'), 'SUPERADMIN')
ON CONFLICT DO NOTHING;

-- Create Lucene index directory (requires manual creation on filesystem)
-- mkdir -p /opt/indexes/lucene/
-- chown -R postgres:postgres /opt/indexes/lucene/

-- Display created tables
\dt

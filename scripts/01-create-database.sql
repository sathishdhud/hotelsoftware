-- Hotel Management System Database Creation Script
-- This script creates the database and initial setup

CREATE DATABASE IF NOT EXISTS hotel_management_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE hotel_management_db;

-- Create accounting year table for number generation
CREATE TABLE IF NOT EXISTS acc_year (
    year_id VARCHAR(10) PRIMARY KEY,
    year_name VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    h_folio_no INT DEFAULT 0,
    h_reservation_no INT DEFAULT 0,
    h_bill_no INT DEFAULT 0,
    h_receipt_no INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert current accounting year
INSERT INTO acc_year (year_id, year_name, start_date, end_date, is_active) 
VALUES ('2024-25', '2024-2025', '2024-04-01', '2025-03-31', TRUE)
ON DUPLICATE KEY UPDATE year_name = VALUES(year_name);

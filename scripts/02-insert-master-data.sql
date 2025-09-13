-- Hotel Management System Master Data Insertion Script
-- This script inserts initial master data required for the system

USE hotel_management_db;

-- Insert default hotel
INSERT INTO hotels (hotel_id, hotel_name, address, city, state, country, phone, email, created_at, updated_at) 
VALUES 
('HOTEL001', 'Grand Palace Hotel', '123 Main Street', 'Mumbai', 'Maharashtra', 'India', '+91-22-12345678', 'info@grandpalace.com', NOW(), NOW())
ON DUPLICATE KEY UPDATE hotel_name = VALUES(hotel_name);

-- Insert user types
INSERT INTO user_type (user_type_id, type_name, created_at, updated_at) 
VALUES 
('UT001', 'Admin', NOW(), NOW()),
('UT002', 'Manager', NOW(), NOW()),
('UT003', 'Front Desk', NOW(), NOW()),
('UT004', 'Cashier', NOW(), NOW()),
('UT005', 'Housekeeping', NOW(), NOW())
ON DUPLICATE KEY UPDATE type_name = VALUES(type_name);

-- Insert room types
INSERT INTO room_type (type_id, type_name, no_of_rooms, created_at, updated_at) 
VALUES 
('RT001', 'Standard', 20, NOW(), NOW()),
('RT002', 'Deluxe', 15, NOW(), NOW()),
('RT003', 'Suite', 10, NOW(), NOW()),
('RT004', 'Presidential Suite', 2, NOW(), NOW())
ON DUPLICATE KEY UPDATE type_name = VALUES(type_name);

-- Insert sample rooms
INSERT INTO rooms (room_id, room_no, floor, status, room_type_id, created_at, updated_at) 
VALUES 
('R001', '101', '1', 'VR', 'RT001', NOW(), NOW()),
('R002', '102', '1', 'VR', 'RT001', NOW(), NOW()),
('R003', '103', '1', 'VR', 'RT001', NOW(), NOW()),
('R004', '201', '2', 'VR', 'RT002', NOW(), NOW()),
('R005', '202', '2', 'VR', 'RT002', NOW(), NOW()),
('R006', '301', '3', 'VR', 'RT003', NOW(), NOW()),
('R007', '401', '4', 'VR', 'RT004', NOW(), NOW())
ON DUPLICATE KEY UPDATE room_no = VALUES(room_no);

-- Insert plan types
INSERT INTO plan_type (plan_id, plan_name, created_at, updated_at) 
VALUES 
('PT001', 'European Plan (EP)', NOW(), NOW()),
('PT002', 'Continental Plan (CP)', NOW(), NOW()),
('PT003', 'Modified American Plan (MAP)', NOW(), NOW()),
('PT004', 'American Plan (AP)', NOW(), NOW())
ON DUPLICATE KEY UPDATE plan_name = VALUES(plan_name);

-- Insert companies/OTAs
INSERT INTO company (company_id, company_name, address1, gst_number, created_at, updated_at) 
VALUES 
('C001', 'MakeMyTrip', 'Gurgaon, Haryana', '07AABCM1234A1Z5', NOW(), NOW()),
('C002', 'Booking.com', 'Amsterdam, Netherlands', NULL, NOW(), NOW()),
('C003', 'Goibibo', 'Gurgaon, Haryana', '07AABCG5678B2Y6', NOW(), NOW()),
('C004', 'Walk-in Guest', 'Direct Booking', NULL, NOW(), NOW())
ON DUPLICATE KEY UPDATE company_name = VALUES(company_name);

-- Insert bill settlement types
INSERT INTO bill_settlement_types (id, name, created_at, updated_at) 
VALUES 
('BST001', 'Cash', NOW(), NOW()),
('BST002', 'Credit Card', NOW(), NOW()),
('BST003', 'Debit Card', NOW(), NOW()),
('BST004', 'UPI', NOW(), NOW()),
('BST005', 'Net Banking', NOW(), NOW()),
('BST006', 'Cheque', NOW(), NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Insert hotel account heads
INSERT INTO hotel_account_head (acc_head_id, name, created_at, updated_at) 
VALUES 
('AH001', 'Room Charges', NOW(), NOW()),
('AH002', 'Restaurant', NOW(), NOW()),
('AH003', 'Laundry', NOW(), NOW()),
('AH004', 'Telephone', NOW(), NOW()),
('AH005', 'Mini Bar', NOW(), NOW()),
('AH006', 'Spa Services', NOW(), NOW()),
('AH007', 'Extra Bed', NOW(), NOW()),
('AH008', 'Early Check-in', NOW(), NOW()),
('AH009', 'Late Check-out', NOW(), NOW()),
('AH010', 'Miscellaneous', NOW(), NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Insert taxation
INSERT INTO taxation (tax_id, tax_name, percentage, created_at, updated_at) 
VALUES 
('TAX001', 'CGST', 9.00, NOW(), NOW()),
('TAX002', 'SGST', 9.00, NOW(), NOW()),
('TAX003', 'IGST', 18.00, NOW(), NOW()),
('TAX004', 'Service Charge', 10.00, NOW(), NOW())
ON DUPLICATE KEY UPDATE tax_name = VALUES(tax_name);

-- Insert nationalities
INSERT INTO nationality (id, nationality, created_at, updated_at) 
VALUES 
('N001', 'Indian', NOW(), NOW()),
('N002', 'American', NOW(), NOW()),
('N003', 'British', NOW(), NOW()),
('N004', 'German', NOW(), NOW()),
('N005', 'French', NOW(), NOW()),
('N006', 'Japanese', NOW(), NOW()),
('N007', 'Chinese', NOW(), NOW()),
('N008', 'Australian', NOW(), NOW())
ON DUPLICATE KEY UPDATE nationality = VALUES(nationality);

-- Insert arrival modes
INSERT INTO arrival_mode (id, arrival_mode, created_at, updated_at) 
VALUES 
('AM001', 'Flight', NOW(), NOW()),
('AM002', 'Train', NOW(), NOW()),
('AM003', 'Bus', NOW(), NOW()),
('AM004', 'Car', NOW(), NOW()),
('AM005', 'Taxi', NOW(), NOW()),
('AM006', 'Walk-in', NOW(), NOW())
ON DUPLICATE KEY UPDATE arrival_mode = VALUES(arrival_mode);

-- Insert reservation sources
INSERT INTO resv_source (id, resv_source, created_at, updated_at) 
VALUES 
('RS001', 'Direct Booking', NOW(), NOW()),
('RS002', 'Phone Booking', NOW(), NOW()),
('RS003', 'Online Portal', NOW(), NOW()),
('RS004', 'Travel Agent', NOW(), NOW()),
('RS005', 'Corporate Booking', NOW(), NOW()),
('RS006', 'Walk-in', NOW(), NOW())
ON DUPLICATE KEY UPDATE resv_source = VALUES(resv_source);

-- Insert reference modes
INSERT INTO ref_mode (id, ref_mode, created_at, updated_at) 
VALUES 
('RM001', 'Advertisement', NOW(), NOW()),
('RM002', 'Friend Reference', NOW(), NOW()),
('RM003', 'Internet Search', NOW(), NOW()),
('RM004', 'Travel Agent', NOW(), NOW()),
('RM005', 'Previous Stay', NOW(), NOW()),
('RM006', 'Corporate Tie-up', NOW(), NOW())
ON DUPLICATE KEY UPDATE ref_mode = VALUES(ref_mode);

-- Insert default admin user (password: admin123)
INSERT INTO hotelsoftusers (user_id, user_name, password, user_type_id, hotel_id, created_at, updated_at) 
VALUES 
('U001', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLVZqpjBdtND.6sSKbzu', 'UT001', 'HOTEL001', NOW(), NOW())
ON DUPLICATE KEY UPDATE user_name = VALUES(user_name);

-- Insert user rights for admin (full access)
INSERT INTO user_rights_data (id, user_id, module_name, permission_type, created_at, updated_at) 
VALUES 
('UR001', 'U001', 'reservations', 'write', NOW(), NOW()),
('UR002', 'U001', 'checkins', 'write', NOW(), NOW()),
('UR003', 'U001', 'rooms', 'write', NOW(), NOW()),
('UR004', 'U001', 'advances', 'write', NOW(), NOW()),
('UR005', 'U001', 'transactions', 'write', NOW(), NOW()),
('UR006', 'U001', 'billing', 'write', NOW(), NOW()),
('UR007', 'U001', 'users', 'write', NOW(), NOW()),
('UR008', 'U001', 'master-data', 'write', NOW(), NOW()),
('UR009', 'U001', 'reports', 'write', NOW(), NOW()),
('UR010', 'U001', 'dashboard', 'read', NOW(), NOW())
ON DUPLICATE KEY UPDATE permission_type = VALUES(permission_type);

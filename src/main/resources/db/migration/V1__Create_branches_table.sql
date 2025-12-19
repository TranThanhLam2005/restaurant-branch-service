-- Create branches table
CREATE TABLE branches (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone_number VARCHAR(20),
    opening_time TIME,
    closing_time TIME
);

-- Insert test data
INSERT INTO branches (name, address, phone_number, opening_time, closing_time) VALUES
('Downtown Branch', '123 Main Street, Downtown, New York, NY 10001', '+1-212-555-0101', '08:00:00', '22:00:00'),
('Uptown Branch', '456 Park Avenue, Uptown, New York, NY 10022', '+1-212-555-0102', '09:00:00', '23:00:00'),
('Brooklyn Branch', '789 Brooklyn Bridge Road, Brooklyn, NY 11201', '+1-718-555-0103', '07:30:00', '21:30:00'),
('Queens Branch', '321 Queens Boulevard, Queens, NY 11375', '+1-718-555-0104', '08:30:00', '22:30:00'),
('Bronx Branch', '654 Grand Concourse, Bronx, NY 10451', '+1-718-555-0105', '09:00:00', '21:00:00');



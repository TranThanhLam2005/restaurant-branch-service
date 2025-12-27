-- Add new columns
ALTER TABLE branches
ADD COLUMN state VARCHAR(50),
ADD COLUMN city VARCHAR(50);

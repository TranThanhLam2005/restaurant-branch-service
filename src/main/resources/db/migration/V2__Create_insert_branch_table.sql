CREATE TABLE branch_tables (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    table_number INT NOT NULL,
    seating_capacity INT NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE
);

INSERT INTO branch_tables (branch_id, table_number, seating_capacity, is_available) VALUES
(1, 1, 4, TRUE),
(1, 2, 2, TRUE),
(1, 3, 6, FALSE),
(2, 1, 4, TRUE),
(2, 2, 4, TRUE),
(2, 3, 8, TRUE),
(3, 1, 2, FALSE),
(3, 2, 4, TRUE),
(4, 1, 6, TRUE),
(4, 2, 2, TRUE),
(5, 1, 4, FALSE),
(5, 2, 4, TRUE);
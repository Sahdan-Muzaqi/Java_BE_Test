CREATE TABLE item_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE inventory_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT,
    qty INT NOT NULL,
    type CHAR(1) CHECK (type IN ('T', 'W')),
    FOREIGN KEY (item_id) REFERENCES item_entity(id)
);

CREATE TABLE order_entity (
    order_no VARCHAR(255) NOT NULL,
    item_id BIGINT,
    qty INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (item_id) REFERENCES item_entity(id)
);

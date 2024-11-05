INSERT INTO item_entity (id, name, price) VALUES (1, 'Pen', 5.00);
INSERT INTO item_entity (id, name, price) VALUES (2, 'Book', 10.00);
INSERT INTO item_entity (id, name, price) VALUES (3, 'Bag', 30.00);
INSERT INTO item_entity (id, name, price) VALUES (4, 'Pencil', 3.00);
INSERT INTO item_entity (id, name, price) VALUES (5, 'Shoe', 45.00);
INSERT INTO item_entity (id, name, price) VALUES (6, 'Box', 5.00);
INSERT INTO item_entity (id, name, price) VALUES (7, 'Cap', 25.00);

INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (1, 1, 5, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (2, 2, 10, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (3, 3, 30, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (4, 4, 3, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (5, 5, 45, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (6, 6, 5, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (7, 7, 25, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (8, 4, 7, 'T');
INSERT INTO inventory_entity (id, item_id, qty, type) VALUES (9, 5, 10, 'W');

INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O1', 1, 2, 5.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O2', 2, 3, 10.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O3', 5, 4, 45.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O4', 4, 1, 2.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O5', 5, 2, 55.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O6', 6, 3, 5.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O7', 1, 5, 5.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O8', 2, 4, 10.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O9', 3, 2, 30.00);
INSERT INTO order_entity (order_no, item_id, qty, price) VALUES ('O10', 4, 3, 5.00);
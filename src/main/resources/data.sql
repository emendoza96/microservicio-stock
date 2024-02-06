INSERT INTO unit (id, description) VALUES (1, 'Unit description');
INSERT INTO unit (id, description) VALUES (2, 'Unit description 2');

INSERT INTO material (id, name, description, price, current_stock, stock_min, unit_id) VALUES (1, 'Brick', 'Brick desc', 300, 55, 10, 1);
INSERT INTO material (id, name, description, price, current_stock, stock_min, unit_id) VALUES (2, 'Brick 2', 'Brick desc 2', 200, 30, 10, 2);

INSERT INTO order_detail (id, quantity, material_id) VALUES (1, 30, 1);
-- INSERT INTO order_detail (id, quantity, material_id) VALUES (2, 25, 2);

INSERT INTO provision (id, provision_date) VALUES (1, '2024-01-21');
INSERT INTO provision (id, provision_date) VALUES (2, '2023-12-01');

INSERT INTO provision_detail (id, quantity, provision_id, material_id) VALUES (1, 25, 1, 1);
INSERT INTO provision_detail (id, quantity, provision_id, material_id) VALUES (2, 30, 1, 2);
INSERT INTO provision_detail (id, quantity, provision_id, material_id) VALUES (3, 10, 2, 1);

INSERT INTO stock_movement (id, input_quantity, output_quantity, date, material_id, provision_detail_id) VALUES (1, 0, 25, '2024-02-06T02:58:05.038377Z', 1, 1);
INSERT INTO stock_movement (id, input_quantity, output_quantity, date, material_id, provision_detail_id) VALUES (2, 0, 30, '2024-02-06T02:58:15.038377Z', 2, 2);
INSERT INTO stock_movement (id, input_quantity, output_quantity, date, material_id, provision_detail_id) VALUES (3, 0, 10, '2023-12-01T02:58:15.038377Z', 1, 3);
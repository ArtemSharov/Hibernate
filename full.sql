BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), price int);
INSERT INTO products (title, price) VALUES
('IPhone', 100000),
('Samsung', 20000),
('LG', 30000),
('1+', 50000),
('Nexus', 25000);


COMMIT;
Added ServiceItem entity and /api/services endpoint.

To create the services table manually in MySQL run:

CREATE TABLE services (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(100) UNIQUE,
  title VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  description VARCHAR(2000),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

Example inserts:
INSERT INTO services (code,title,price,description) VALUES ('cleaning','Home Cleaning',1200,'Standard home cleaning');
INSERT INTO services (code,title,price,description) VALUES ('washing','Washing & Laundry',800,'Laundry & washing service');

Frontend: `happymopf/src/Bookslot.html` now fetches /api/services and supports selecting multiple services (cart) and payment methods (card, upi, cash).

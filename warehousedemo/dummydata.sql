-- ITEMS
INSERT INTO items (name) VALUES
	('banana'),
	('orange'),
	('apple');

DELETE FROM items;

SELECT * FROM items;

-- ITEM LOCATIONS
INSERT INTO item_locations (item_id, warehouse_id, quantity) VALUES
	(5, 1,1),
	(6, 1,2),
	(7,1,3);

SELECT * FROM item_locations;

-- WAREHOUSES
SELECT * FROM warehouses;
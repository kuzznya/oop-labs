INSERT INTO Store (id, name, address) VALUES
    (RANDOM_UUID(), 'DNS Shop', 'https://dns-shop.ru'),
    (RANDOM_UUID(), 'Apple', 'https://apple.com');

INSERT INTO Item (id, name) VALUES
    (RANDOM_UUID(), 'MacBook Air 2019'),
    (RANDOM_UUID(), 'iMac 2020');

INSERT INTO Product (ID, ITEM_ID, STORE_ID, AMOUNT, PRICE) VALUES
    (RANDOM_UUID(),
     (SELECT id FROM Item WHERE name = 'MacBook Air 2019'),
     (SELECT id FROM Store WHERE name = 'DNS Shop'),
     300,
     100000),
    (RANDOM_UUID(),
     (SELECT id FROM Item WHERE name = 'iMac 2020'),
     (SELECT id FROM Store WHERE name = 'Apple'),
     100,
     1000000);

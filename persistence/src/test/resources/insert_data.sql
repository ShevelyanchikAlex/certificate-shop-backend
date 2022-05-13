INSERT INTO tag(name)
VALUES ('#tag'),
       ('#cool'),
       ('#like');

INSERT INTO gift_certificate (name, description, price, duration)
VALUES ('Nike', 'For shopping', 1000, 20),
       ('Restaurant', 'Tasty food', 1500, 11),
       ('Swimming Pool', 'Swim for fun', 100, 7);

INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 3),
       (2, 2),
       (3, 2),
       (3, 3);
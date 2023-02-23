INSERT INTO power (name, description )
VALUES ('Batcape',  'A indestructable cape to protect its wielder'),
       ('Flying', 'A hero with this power can fly'),
       ('SpiderWeb', 'A power that shots strong spiderweb');
INSERT INTO superhero_power(superhero_id,power_id)
VALUES (1,1),(1,2),(1,3),(2,2),(3,3)
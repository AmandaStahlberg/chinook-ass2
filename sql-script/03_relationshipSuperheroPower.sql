CREATE TABLE superhero_power (
    superhero_id INTEGER,
    power_id INTEGER,
    PRIMARY KEY (superhero_id, power_id),
    CONSTRAINT fk_superhero_power_superhero
        FOREIGN KEY (superhero_id)
        REFERENCES superhero (id),
    CONSTRAINT fk_superhero_power_power
        FOREIGN KEY (power_id)
        REFERENCES power (id)
);
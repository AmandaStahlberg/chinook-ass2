CREATE TABLE SuperHero (
    Id SERIAL,
    Name varchar(255),
    Alias varchar(255),
    Origin varchar(255),
    PRIMARY KEY(Id)
);
CREATE TABLE Assistant (
    Id SERIAL,
    Name varchar(255),
    PRIMARY KEY(Id)
);
CREATE TABLE Power (
    Id SERIAL,
    Name varchar(255),
    Description TEXT,
    PRIMARY KEY(Id)
);
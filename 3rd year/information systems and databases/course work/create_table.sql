CREATE TABLE Materials(
    MATERIAL_ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    EFFECT VARCHAR(70),
    MULTIPLIER FLOAT
);
CREATE INDEX ON Materials USING HASH(MATERIAL_ID);

CREATE TABLE Armor (
    ARMOR_ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    TYPE_OF_ITEM varchar(20),
    DEFENCE INTEGER,
    PRICE_DIAMOND SMALLINT NOT NULL,
    PRICE_GOLD SMALLINT NOT NULL,
    CHANCE FLOAT NOT NULL,
	FL VARCHAR(50)
);
CREATE INDEX ON Armor USING HASH(ARMOR_ID);

CREATE TABLE Magic_items (
    SPELL_ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    TYPE_OF_ITEM VARCHAR(20),
    DAMAGE INTEGER,
    EFFECT VARCHAR(70),
    PRICE_DIAMOND SMALLINT NOT NULL,
    PRICE_GOLD SMALLINT NOT NULL,
    CHANCE FLOAT,
	FL VARCHAR(50)
);
CREATE INDEX ON Magic_items USING HASH(SPELL_ID);

CREATE TABLE  Pick_ups(
    PICKUP_ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    TYPE_OF_ITEM VARCHAR(20),
    HEALING SMALLINT,
    EFFECT VARCHAR(70),
    CHANCE FLOAT,
    PRICE_GOLD SMALLINT NOT NULL,
	FL VARCHAR(50)
);
CREATE INDEX ON Pick_ups USING HASH(PICKUP_ID);

CREATE TABLE Dungeon_Master(
    UPDATE_ID SERIAL PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    ADDITIONAL_HP SMALLINT,
    MULTIPLIER_COIN FLOAT,
    PRICE_DIAMOND SMALLINT NOT NULL,
	FL VARCHAR(50)
);
CREATE INDEX ON Dungeon_Master USING HASH(UPDATE_ID);

CREATE TABLE Player (
    PLAYER_ID SERIAL PRIMARY KEY,
    PASSWORD VARCHAR(20),
    NAME VARCHAR(20),
    HEALTH_POINTS SMALLINT,
    SUCCESS_CHANCE FLOAT,
    GOLD SMALLINT NOT NULL,
    DIAMONDS SMALLINT NOT NULL
);
CREATE INDEX ON Player USING HASH(PLAYER_ID);

CREATE TABLE Weapon (
    WEAPON_ID SERIAL PRIMARY KEY,
    MATERIAL_ID INTEGER REFERENCES Materials(MATERIAL_ID),
    NAME VARCHAR(50),
    DAMAGE INTEGER,
    PRICE_DIAMOND SMALLINT NOT NULL,
    PRICE_GOLD SMALLINT NOT NULL,
    CHANCE FLOAT,
	FL VARCHAR(50)
);
CREATE INDEX ON Weapon USING HASH(WEAPON_ID);

CREATE TABLE Shovel (
    SHOVEL_ID SERIAL PRIMARY KEY,
    MATERIAL_ID INTEGER REFERENCES Materials(MATERIAL_ID),
    NAME VARCHAR(50) NOT NULL,
    EFFECT VARCHAR(70),
    CHANCE FLOAT NOT NULL,
    PRICE_GOLD SMALLINT NOT NULL,
	FL VARCHAR(50)
);
CREATE INDEX ON Shovel USING HASH(SHOVEL_ID);

CREATE TYPE ITEM_TYPE_ENUM AS ENUM ('WEAPON', 'ARMOR', 'MAGIC ITEM', 'DUNGEON MASTER', 'PICKUP', 'SHOVEL');

CREATE TABLE Merlin (
    ITEM_ID INTEGER NOT NULL,
    ITEM_TYPE ITEM_TYPE_ENUM NOT NULL
);
CREATE INDEX ON Merlin USING HASH(ITEM_ID);

CREATE TABLE Hephaestus (
    ITEM_ID INTEGER NOT NULL,
    ITEM_TYPE ITEM_TYPE_ENUM NOT NULL
);
CREATE INDEX ON Hephaestus USING HASH(ITEM_ID);

CREATE TABLE Singer_Storage (
    ITEM_ID INTEGER NOT NULL,
    ITEM_TYPE ITEM_TYPE_ENUM NOT NULL
);
CREATE INDEX ON Singer_Storage USING HASH(ITEM_ID);

CREATE TABLE Diamond_dealer (
    ITEM_ID INTEGER NOT NULL,
    ITEM_TYPE ITEM_TYPE_ENUM NOT NULL
);
CREATE INDEX ON Diamond_dealer USING HASH(ITEM_ID);

CREATE TABLE Player_storage (
    PLAYER_ID INTEGER REFERENCES Player(PLAYER_ID),
    ITEM_ID INTEGER NOT NULL,
    ITEM_TYPE ITEM_TYPE_ENUM NOT NULL
);

CREATE TABLE Singer (
    FLOOR_ID SERIAL PRIMARY KEY,
    MOOD FLOAT NOT NULL,
    FIRST_ITEM_ID INTEGER,
    FIRST_ITEM_TYPE ITEM_TYPE_ENUM,
    SECOND_ITEM_ID INTEGER,
    SECOND_ITEM_TYPE ITEM_TYPE_ENUM,
    THIRD_ITEM_ID INTEGER,
    THIRD_ITEM_TYPE ITEM_TYPE_ENUM
);
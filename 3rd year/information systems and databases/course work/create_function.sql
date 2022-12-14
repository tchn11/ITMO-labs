CREATE OR REPLACE FUNCTION GetItemGoldCost(item_id INTEGER, enm ITEM_TYPE_ENUM)
    RETURNS INTEGER
AS $$
BEGIN
    IF (enm = 'WEAPON') THEN
        RETURN (SELECT PRICE_GOLD FROM Weapon WHERE WEAPON_ID = item_id);
    END IF;
    IF (enm = 'ARMOR') THEN
        RETURN (SELECT PRICE_GOLD FROM Armor WHERE ARMOR_ID = item_id);
    END IF;
    IF (enm = 'MAGIC ITEM') THEN
        RETURN (SELECT PRICE_GOLD FROM Magic_items WHERE SPELL_ID = item_id);
    END IF;
    IF (enm = 'PICKUP') THEN
        RETURN (SELECT PRICE_GOLD FROM Pick_ups WHERE PICKUP_ID = item_id);
    END IF;
    IF (enm = 'SHOVEL') THEN
        RETURN (SELECT PRICE_GOLD FROM Shovel WHERE SHOVEL_ID = item_id);
    END IF;
    RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetItemDiamondCost(item_id INTEGER, enm ITEM_TYPE_ENUM)
    RETURNS INTEGER
AS $$
BEGIN
    IF (enm = 'WEAPON') THEN
        RETURN (SELECT PRICE_DIAMOND FROM Weapon WHERE WEAPON_ID = item_id);
    END IF;
    IF (enm = 'ARMOR') THEN
        RETURN (SELECT PRICE_DIAMOND FROM Armor WHERE ARMOR_ID = item_id);
    END IF;
    IF (enm = 'MAGIC ITEM') THEN
        RETURN (SELECT PRICE_DIAMOND FROM Magic_items WHERE SPELL_ID = item_id);
    END IF;
    IF (enm = 'DUNGEON MASTER') THEN
        RETURN (SELECT PRICE_DIAMOND FROM Dungeon_Master WHERE Dungeon_Master.UPDATE_ID = item_id);
    END IF;
    RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetItemChance(item_id INTEGER, enm ITEM_TYPE_ENUM)
    RETURNS FLOAT
AS $$
BEGIN
    IF (enm = 'WEAPON') THEN
        RETURN (SELECT CHANCE FROM Weapon WHERE WEAPON_ID = item_id) * (SELECT MULTIPLIER FROM Materials WHERE MATERIAL_ID = (SELECT Weapon.MATERIAL_ID FROM Weapon WHERE WEAPON_ID = item_id));
    END IF;
    IF (enm = 'ARMOR') THEN
        RETURN (SELECT CHANCE FROM Armor WHERE ARMOR_ID = item_id);
    END IF;
    IF (enm = 'MAGIC ITEM') THEN
        RETURN (SELECT CHANCE FROM Magic_items WHERE SPELL_ID = item_id);
    END IF;
    IF (enm = 'PICKUP') THEN
        RETURN (SELECT CHANCE FROM Pick_ups WHERE PICKUP_ID = item_id);
    END IF;
    IF (enm = 'SHOVEL') THEN
        RETURN (SELECT CHANCE FROM Shovel WHERE SHOVEL_ID = item_id) * (SELECT MULTIPLIER FROM Materials WHERE MATERIAL_ID = (SELECT Shovel.MATERIAL_ID FROM Shovel WHERE SHOVEL_ID = item_id));
    END IF;
    RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetItemType(item_id INTEGER, enm ITEM_TYPE_ENUM)
    RETURNS VARCHAR(20)
AS $$
BEGIN
    if (enm = 'ARMOR') THEN
        RETURN (SELECT TYPE_OF_ITEM FROM Armor WHERE ARMOR_ID = item_id);
    ELSIF (enm = 'PICKUP') THEN
        RETURN (SELECT TYPE_OF_ITEM FROM Pick_ups WHERE PICKUP_ID = item_id);
    ELSIF (enm = 'MAGIC ITEM') THEN
        RETURN (SELECT TYPE_OF_ITEM FROM Magic_items WHERE SPELL_ID = item_id);
    END IF;
    RETURN NULL;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION UpdateChance()
    RETURNS TRIGGER
AS $$
DECLARE
    row Player_storage%ROWTYPE;
    sum FLOAT;
BEGIN
    sum := 0;
    FOR row IN (SELECT * FROM Player_storage WHERE PLAYER_ID = NEW.PLAYER_ID)
    LOOP
        IF row.ITEM_TYPE != 'DUNGEON MASTER' THEN
            sum := sum + GetItemChance(row.ITEM_ID, row.ITEM_TYPE);
        END IF;
    END LOOP;

    UPDATE Player SET SUCCESS_CHANCE = sum WHERE PLAYER_ID = NEW.PLAYER_ID;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER UpdateChance1 AFTER INSERT ON Player_storage
    FOR EACH ROW EXECUTE PROCEDURE UpdateChance();
CREATE TRIGGER UpdateChance2 AFTER UPDATE ON Player_storage
    FOR EACH ROW EXECUTE PROCEDURE UpdateChance();

CREATE OR REPLACE FUNCTION CheckItemType1()
    RETURNS TRIGGER
AS $$
BEGIN
    IF ((SELECT COUNT(*) FROM Player_storage WHERE Player_storage.ITEM_TYPE = NEW.ITEM_TYPE AND PLAYER_ID = NEW.PLAYER_ID AND ITEM_ID = NEW.ITEM_ID) >= 1) THEN
        if (NEW.ITEM_TYPE = 'ARMOR') THEN
            DELETE FROM Player_storage WHERE PLAYER_ID = NEW.PLAYER_ID AND GetItemType(new.ITEM_ID, new.ITEM_TYPE) = GetItemType(ITEM_ID, ITEM_TYPE);
        ELSIF (NEW.ITEM_TYPE = 'PICKUP') THEN
            DELETE FROM Player_storage WHERE PLAYER_ID = NEW.PLAYER_ID AND GetItemType(new.ITEM_ID, new.ITEM_TYPE) = GetItemType(ITEM_ID, ITEM_TYPE);
        ELSIF (NEW.ITEM_TYPE = 'MAGIC ITEM') THEN
            DELETE FROM Player_storage WHERE PLAYER_ID = NEW.PLAYER_ID AND GetItemType(new.ITEM_ID, new.ITEM_TYPE) = GetItemType(ITEM_ID, ITEM_TYPE);
        ELSE
            DELETE FROM Player_storage WHERE PLAYER_ID = NEW.PLAYER_ID AND ITEM_TYPE = NEW.ITEM_TYPE;
        END IF;
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER CheckItemType1 BEFORE INSERT ON Player_storage
    FOR EACH ROW EXECUTE PROCEDURE CheckItemType1();

CREATE OR REPLACE FUNCTION BuyFromSinger(pl_id INTEGER, item_num SMALLINT, floor_num INTEGER)
    RETURNS VOID
AS
$$
DECLARE
    num INTEGER;
    enm ITEM_TYPE_ENUM;
BEGIN
    IF (item_num = 1)
        THEN
        IF (SELECT COUNT(FIRST_ITEM_ID) FROM Singer WHERE Singer.FLOOR_ID = floor_num) = 0
            THEN
            RAISE notice'Item do not exists';
            RETURN;
        END IF;

        num := (SELECT Singer.FIRST_ITEM_ID FROM Singer WHERE Singer.FLOOR_ID = floor_num);
        enm := (SELECT Singer.FIRST_ITEM_TYPE FROM Singer WHERE Singer.FLOOR_ID = floor_num);

        IF (ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) > (SELECT GOLD FROM Player WHERE PLAYER_ID = pl_id))
            THEN
            RAISE notice'Not enough gold';
            RETURN;
        END IF;

        UPDATE Singer SET FIRST_ITEM_TYPE = NULL, FIRST_ITEM_ID = NULL WHERE Singer.FLOOR_ID = floor_num;

        UPDATE Player SET GOLD = GOLD - ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) WHERE PLAYER_ID = pl_id;

        INSERT INTO Player_storage (PLAYER_ID, ITEM_ID, ITEM_TYPE)
        VALUES (pl_id, num, enm);
    END IF;

    IF (item_num = 2)
    THEN
        IF (SELECT COUNT(SECOND_ITEM_ID) FROM Singer WHERE Singer.FLOOR_ID = floor_num) = 0
        THEN
            RAISE notice'Item do not exists';
            RETURN;
        END IF;

        num := (SELECT Singer.SECOND_ITEM_ID FROM Singer WHERE Singer.FLOOR_ID = floor_num);
        enm := (SELECT Singer.SECOND_ITEM_TYPE FROM Singer WHERE Singer.FLOOR_ID = floor_num);

        IF (ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) > (SELECT GOLD FROM Player WHERE PLAYER_ID = pl_id))
        THEN
            RAISE notice'Not enough gold';
            RETURN;
        END IF;

        UPDATE Singer SET SECOND_ITEM_TYPE = NULL, SECOND_ITEM_ID = NULL WHERE Singer.FLOOR_ID = floor_num;

        UPDATE Player SET GOLD = GOLD - ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) WHERE PLAYER_ID = pl_id;

        INSERT INTO Player_storage (PLAYER_ID, ITEM_ID, ITEM_TYPE)
        VALUES (pl_id, num, enm);
    END IF;

    IF (item_num = 3)
    THEN
        IF (SELECT COUNT(THIRD_ITEM_ID) FROM Singer WHERE Singer.FLOOR_ID = floor_num) = 0
        THEN
            RAISE notice'Item do not exists';
            RETURN;
        END IF;

        num := (SELECT Singer.THIRD_ITEM_ID FROM Singer WHERE Singer.FLOOR_ID = floor_num);
        enm := (SELECT Singer.THIRD_ITEM_TYPE FROM Singer WHERE Singer.FLOOR_ID = floor_num);

        IF (ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) > (SELECT GOLD FROM Player WHERE PLAYER_ID = pl_id))
        THEN
            RAISE notice'Not enough gold';
            RETURN;
        END IF;

        UPDATE Singer SET THIRD_ITEM_TYPE = NULL, THIRD_ITEM_ID = NULL WHERE Singer.FLOOR_ID = floor_num;

        UPDATE Player SET GOLD = GOLD - ROUND(GetItemGoldCost(num, enm) * (SELECT MOOD FROM Singer WHERE FLOOR_ID = floor_num)) WHERE PLAYER_ID = pl_id;

        INSERT INTO Player_storage (PLAYER_ID, ITEM_ID, ITEM_TYPE)
        VALUES (pl_id, num, enm);
    END IF;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION BuyFromMerlin(pl_id INTEGER, mg_id INTEGER)
    RETURNS VOID
AS
$$
BEGIN
    IF(NOT EXISTS(SELECT * FROM Magic_items WHERE SPELL_ID = mg_id))
    THEN
        RAISE notice'Wrong item ID';
        RETURN;
    END IF;

    IF(NOT EXISTS(SELECT * FROM Merlin WHERE ITEM_ID = mg_id))
    THEN
        RAISE notice'Merlin doesnt sell this';
        RETURN;
    END IF;

    IF(EXISTS(SELECT * FROM Singer_Storage WHERE ITEM_ID = mg_id AND ITEM_TYPE = 'MAGIC ITEM'))
    THEN
        RAISE notice'Item id already bought';
        RETURN;
    END IF;

    IF (GetItemDiamondCost(mg_id, 'MAGIC ITEM') > (SELECT DIAMONDS FROM Player WHERE PLAYER_ID = pl_id))
    THEN
        RAISE notice'Not enough diamonds';
        RETURN;
    END IF;

    UPDATE Player SET DIAMONDS = Player.DIAMONDS - GetItemDiamondCost(mg_id, 'MAGIC ITEM') WHERE PLAYER_ID = pl_id;

    INSERT INTO Singer_Storage (ITEM_ID, ITEM_TYPE)
    VALUES (mg_id, 'MAGIC ITEM');

    DELETE FROM Merlin WHERE ITEM_ID = mg_id;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION BuyHephaestus(pl_id INTEGER, wp_id INTEGER,it_type ITEM_TYPE_ENUM)
    RETURNS VOID
AS
$$
BEGIN
    IF (NOT EXISTS(SELECT * FROM Hephaestus WHERE ITEM_ID = wp_id AND ITEM_TYPE = it_type))
    THEN
        RAISE notice'Hephaestus doesnt sell this item';
    END IF;

    IF(EXISTS(SELECT * FROM Singer_Storage WHERE ITEM_ID = wp_id AND ITEM_TYPE = it_type))
    THEN
        RAISE notice'Item id already bought';
        RETURN;
    END IF;

    IF (GetItemDiamondCost(wp_id, it_type) > (SELECT DIAMONDS FROM Player WHERE PLAYER_ID = pl_id))
    THEN
        RAISE notice'Not enough diamonds';
        RETURN;
    END IF;

    UPDATE Player SET DIAMONDS = Player.DIAMONDS - GetItemDiamondCost(wp_id, it_type) WHERE PLAYER_ID = pl_id;

    INSERT INTO Singer_Storage (ITEM_ID, ITEM_TYPE)
    VALUES (wp_id, it_type);

    DELETE FROM Hephaestus WHERE ITEM_TYPE = it_type AND ITEM_ID = wp_id;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION RerollMerlin()
    RETURNS VOID
AS
$$
DECLARE
    id INTEGER;
BEGIN
    WHILE (SELECT count(*) FROM Merlin) < 3
    LOOP
        id := (SELECT SPELL_ID FROM Magic_items WHERE
            (NOT EXISTS(SELECT * FROM Merlin WHERE SPELL_ID = ITEM_ID))
            AND (NOT EXISTS(SELECT * FROM Singer_Storage WHERE SPELL_ID = ITEM_ID AND ITEM_TYPE = 'MAGIC ITEM'))
            ORDER BY random() LIMIT 1);

        INSERT INTO Merlin (ITEM_ID, ITEM_TYPE) VALUES (id, 'MAGIC ITEM');
    END LOOP;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION RerollHephaestus()
    RETURNS VOID
AS
$$
DECLARE
    id1 INTEGER;
    id2 INTEGER;
BEGIN
    WHILE (SELECT count(*) FROM Hephaestus) < 3
        LOOP
            id1 := (SELECT ARMOR_ID FROM Armor WHERE
                (NOT EXISTS(SELECT * FROM Hephaestus WHERE ARMOR_ID = ITEM_ID))
                AND (NOT EXISTS(SELECT * FROM Singer_Storage WHERE ARMOR_ID = ITEM_ID AND ITEM_TYPE = 'ARMOR'))
                ORDER BY random() LIMIT 1);

            id2 := (SELECT WEAPON_ID FROM Weapon WHERE
                (NOT EXISTS(SELECT * FROM Hephaestus WHERE WEAPON_ID = ITEM_ID))
                AND (NOT EXISTS(SELECT * FROM Singer_Storage WHERE WEAPON_ID = ITEM_ID AND ITEM_TYPE = 'WEAPON'))
                ORDER BY random() LIMIT 1);

            IF (random() > 0.5)
            THEN
                INSERT INTO Hephaestus (ITEM_ID, ITEM_TYPE) VALUES (id1, 'ARMOR');
            ELSE
                INSERT INTO Hephaestus (ITEM_ID, ITEM_TYPE) VALUES (id2, 'WEAPON');
            END IF;
        END LOOP;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION RerollDiamondDealler()
    RETURNS VOID
AS
$$
DECLARE
    id1 INTEGER;
    id2 INTEGER;
    id3 INTEGER;
    rand FLOAT;
BEGIN
    DELETE FROM Diamond_dealer;

    WHILE (SELECT count(*) FROM Diamond_dealer) < 3
        LOOP
            id1 := (SELECT ARMOR_ID FROM Armor ORDER BY random() LIMIT 1);

            id2 := (SELECT WEAPON_ID FROM Weapon ORDER BY random() LIMIT 1);

            id3 := (SELECT SPELL_ID FROM Magic_items ORDER BY random() LIMIT 1);

            rand := random();

            IF (rand < 0.33)
            THEN
                INSERT INTO Diamond_dealer (ITEM_ID, ITEM_TYPE) VALUES (id1, 'ARMOR');
            ELSEIF (rand < 0.66)
            THEN
                INSERT INTO Diamond_dealer (ITEM_ID, ITEM_TYPE) VALUES (id2, 'WEAPON');
            ELSE
                INSERT INTO Diamond_dealer (ITEM_ID, ITEM_TYPE) VALUES (id3, 'MAGIC ITEM');
            END IF;
        END LOOP;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION RerollSinger(floor_num INTEGER)
    RETURNS VOID
AS
$$
DECLARE
    iter INTEGER;
    row Singer_Storage%ROWTYPE;
BEGIN
    iter := 0;
    FOR row IN SELECT * FROM Singer_Storage ORDER BY random() LIMIT 3
    LOOP
        IF iter = 0 THEN
            UPDATE Singer SET FIRST_ITEM_ID = row.ITEM_ID, FIRST_ITEM_TYPE = row.ITEM_TYPE WHERE FLOOR_ID = floor_num;
        END IF;
        IF iter = 1 THEN
            UPDATE Singer SET SECOND_ITEM_ID = row.ITEM_ID, SECOND_ITEM_TYPE = row.ITEM_TYPE WHERE FLOOR_ID = floor_num;
        END IF;
        IF iter = 2 THEN
            UPDATE Singer SET THIRD_ITEM_ID = row.ITEM_ID, THIRD_ITEM_TYPE = row.ITEM_TYPE WHERE FLOOR_ID = floor_num;
        END IF;
        iter := iter + 1;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION BuyDungeonMaster(pl_id INTEGER, it_id INTEGER)
    RETURNS VOID
AS
$$
BEGIN
    IF(NOT EXISTS(SELECT * FROM Dungeon_Master WHERE UPDATE_ID = it_id))
    THEN
        RAISE notice'Item do not exists';
        RETURN;
    END IF;

    IF(EXISTS(SELECT * FROM Player_storage WHERE ITEM_ID = it_id AND ITEM_TYPE = 'DUNGEON MASTER' AND PLAYER_ID = pl_id))
    THEN
        RAISE notice'Item id already bought';
        RETURN;
    END IF;

    IF (GetItemDiamondCost(it_id, 'DUNGEON MASTER') > (SELECT DIAMONDS FROM Player WHERE PLAYER_ID = pl_id))
    THEN
        RAISE notice'Not enough diamonds';
        RETURN;
    END IF;

    UPDATE Player SET DIAMONDS = Player.DIAMONDS - GetItemDiamondCost(pl_id, 'DUNGEON MASTER') WHERE PLAYER_ID = pl_id;

    INSERT INTO Player_storage (PLAYER_ID, ITEM_ID, ITEM_TYPE)
    VALUES (pl_id, it_id, 'DUNGEON MASTER');
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION BuyDiamondDealer(pl_id INTEGER, it_id INTEGER, it_type ITEM_TYPE_ENUM)
    RETURNS VOID
AS
$$
BEGIN
    IF(NOT EXISTS(SELECT * FROM Diamond_dealer WHERE ITEM_ID = it_id))
    THEN
        RAISE notice'Item do not exists';
        RETURN;
    END IF;

    IF(EXISTS(SELECT * FROM Player_storage WHERE ITEM_ID = it_id AND ITEM_TYPE = it_type AND PLAYER_ID = pl_id))
    THEN
        RAISE notice'Item id already bought';
        RETURN;
    END IF;

    IF (GetItemDiamondCost(it_id, it_type) > (SELECT DIAMONDS FROM Player WHERE PLAYER_ID = pl_id))
    THEN
        RAISE notice'Not enough diamonds';
        RETURN;
    END IF;

    UPDATE Player SET DIAMONDS = Player.DIAMONDS - GetItemDiamondCost(pl_id, it_type) WHERE PLAYER_ID = pl_id;

    DELETE FROM Diamond_dealer where ITEM_TYPE = it_type and ITEM_ID = it_id;

    INSERT INTO Player_storage (PLAYER_ID, ITEM_ID, ITEM_TYPE)
    VALUES (pl_id, it_id, it_type);
END
$$ language plpgsql;
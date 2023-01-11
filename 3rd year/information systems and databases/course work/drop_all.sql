
DROP FUNCTION buyfromsinger(integer,smallint,integer);

DROP FUNCTION buyfrommerlin(integer,integer);

DROP FUNCTION buydungeonmaster(integer,integer);

DROP FUNCTION buydiamonddealer(integer,integer,item_type_enum);

DROP FUNCTION buyhephaestus(integer,integer,item_type_enum);

drop table if exists materials cascade;

drop table if exists armor cascade;

drop table if exists magic_items cascade;

drop table if exists pick_ups cascade;

drop table if exists dungeon_master cascade;

drop table if exists player cascade;

drop table if exists weapon cascade;

drop table if exists shovel cascade;

drop table if exists merlin cascade;

drop table if exists hephaestus cascade;

drop table if exists singer_storage cascade;

drop table if exists diamond_dealer cascade;

drop table if exists player_storage cascade;

drop table if exists singer cascade;

drop type if exists item_type_enum cascade;

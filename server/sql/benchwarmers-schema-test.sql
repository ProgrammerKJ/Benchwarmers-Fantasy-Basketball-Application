drop database if exists benchwarmers_test;
create database benchwarmers_test;
use benchwarmers_test;

-- create tables and relationships
create table team(
	team_id int primary key auto_increment,
	`name` varchar(250) not null,
    short_name varchar(250) not null,
    abbreviation varchar(25) not null,
    conference varchar(250) not null,
	division varchar(250) not null
);

create table `role`(
	role_id int primary key auto_increment,
    `name` varchar(250) not null
);

create table player(
	player_id int primary key auto_increment,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    position varchar(25) not null,
    jersey_number int not null,
    fantasy_points decimal(10,2) not null,
    team_id int not null,
    constraint fk_player_team_id
		foreign key (team_id)
        references team(team_id)
);

create table game(
	game_id int primary key auto_increment,
    season int not null,
    home_team_id int not null,
    away_team_id int not null,
    home_team_score int not null,
    away_team_score int not null,
    game_date date not null,
    constraint fk_game_home_team_id
		foreign key (home_team_id)
        references team(team_id),
	constraint fk_game_away_team_id
		foreign key (away_team_id)
		references team(team_id)
);

create table `user`(
	user_id int primary key auto_increment,
    username varchar(250) not null,
    email varchar(255) not null,
    favorite_team_id int not null,
    password_hash varchar (2048) not null,
	disabled boolean not null default(0),
	constraint fk_user_favorite_team_id
		foreign key (favorite_team_id)
        references team(team_id)
);

create table app_user_role (
    user_id int not null,
    role_id int not null,
    constraint pk_user_role
        primary key (user_id, role_id),
    constraint fk_user_role_user_id
        foreign key (user_id)
        references `user`(user_id),
    constraint fk_user_role_role_id
        foreign key (role_id)
        references `role`(role_id)
);

create table fantasy_league(
	fantasy_league_id int primary key auto_increment,
    `name` varchar(250) not null,
    season int not null,
    admin_id int not null,
	constraint fk_fantasy_league_admin_id
		foreign key (admin_id)
		references `user`(user_id)
);

create table fantasy_team(
	fantasy_team_id int not null auto_increment,
    user_id int not null,
    player_id int not null,
    fantasy_league_id int not null,
	constraint pk_fantasy_team
		primary key(fantasy_team_id, user_id, player_id),
    constraint fk_fantasy_team_user_id
		foreign key (user_id)
        references `user`(user_id),
	constraint fk_fantasy_team_player_id
		foreign key (player_id)
		references player(player_id),
	constraint fk_fantasy_team_fantasy_league_id
		foreign key (fantasy_league_id)
		references fantasy_league(fantasy_league_id)
);

create table league_members(
	fantasy_league_id int not null,
    user_id int not null,
	constraint pk_league_members
		primary key(fantasy_league_id, user_id),
	constraint fk_league_members_fantasy_league
		foreign key (fantasy_league_id)
        references fantasy_league(fantasy_league_id),
	constraint fk_league_members_user
		foreign key(user_id)
        references `user` (user_id)
);
    
delimiter //
create procedure set_known_good_state()
begin

	delete from game;
    alter table game auto_increment = 1;
	delete from fantasy_team;
    alter table fantasy_team auto_increment = 1;
	delete from league_members;
    alter table league_members auto_increment = 1;
	delete from fantasy_league;
    alter table fantasy_league auto_increment = 1;
	delete from app_user_role;
    alter table app_user_role auto_increment = 1;
	delete from `user`;
    alter table `user` auto_increment = 1;
	delete from player;
    alter table player auto_increment = 1;
	delete from team;
    alter table team auto_increment = 1;
	delete from `role`;
    alter table `role` auto_increment = 1;

	-- data
	insert into team(team_id, `name`, abbreviation, conference, division, short_name)
	values 
		(1, "Atlanta Hawks", "ATL", "Eastern", "Southeast", "HAWKS"),
		(2, "Boston Celtics", "BOS", "Eastern", "Atlantic", "CELTICS"),
		(3, "Brooklyn Nets", "BKN", "Eastern", "Atlantic", "NETS"),
		(4, "Charlotte Hornets", "CHA", "Eastern", "Southeast", "HORNETS"),
		(5, "Chicago Bulls", "CHI", "Eastern", "Central", "BULLS"),
		(6, "Cleveland Cavaliers", "CLE", "Eastern", "Central", "CAVALIERS"),
		(7, "Dallas Mavericks", "DAL", "Western", "Southwest", "MAVERICKS"),
		(8, "Denver Nuggets", "DEN", "Western", "Northwest", "NUGGETS"),
		(9, "Detroit Pistons", "DET", "Eastern", "Central", "PISTONS"),
		(10, "Golden State Warriors", "GSW", "Western", "Pacific", "WARRIORS"),
		(11, "Houston Rockets", "HOU", "Western", "Southwest", "ROCKETS"),
		(12, "Indiana Pacers", "IND", "Eastern", "Central", "PACERS"),
		(13, "Los Angeles Clippers", "LAC", "Western", "Pacific", "CLIPPERS"),
		(14, "Los Angeles Lakers", "LAL", "Western", "Pacific", "LAKERS"),
		(15, "Memphis Grizzlies", "MEM", "Western", "Southwest", "GRIZZLIES"),
		(16, "Miami Heat", "MIA", "Eastern", "Southeast", "HEAT"),
		(17, "Milwaukee Bucks", "MIL", "Eastern", "Central", "BUCKS"),
		(18, "Minnesota Timberwolves", "MIN", "Western", "Northwest", "TIMBERWOLVES"),
		(19, "New Orleans Pelicans", "NOP", "Western", "Southwest", "PELICANS"),
		(20, "New York Knicks", "NYK", "Eastern", "Atlantic", "KNICKS"),
		(21, "Oklahoma City Thunder", "OKC", "Western", "Northwest", "THUNDER"),
		(22, "Orlando Magic", "ORL", "Eastern", "Southeast", "MAGIC"),
		(23, "Philadelphia 76ers", "PHI", "Eastern", "Atlantic", "SIXERS"),
		(24, "Phoenix Suns", "PHX", "Western", "Pacific", "SUNS"),
		(25, "Portland Trail Blazers", "POR", "Western", "Northwest", "BLAZERS"),
		(26, "Sacramento Kings", "SAC", "Western", "Pacific", "KINGS"),
		(27, "San Antonio Spurs", "SAS", "Western", "Southwest", "SPURS"),
		(28, "Toronto Raptors", "TOR", "Eastern", "Atlantic", "RAPTORS"),
		(29, "Utah Jazz", "UTA", "Western", "Northwest", "JAZZ"),
		(30, "Washington Wizards", "WAS", "Eastern", "Southeast", "WIZARDS");
		
	insert into `role` (`name`)
	values
		('ADMIN'),
		('LEAGUE_MEMBER');
		
	insert into `user` (username, email, favorite_team_id, password_hash, disabled)
	values
		('ballislife', 'test@gmail.com', 1, 'aasdwdeaw', 0),
        ('anklebreaker', 'test@gmail.com', 2, '$2a$10$7Ucy.bfJQ3nBF1vYG19o/eTAUhGidYL52A3d2ZOkvDI6gBFj.f6qW', 0), -- use for update
        ('shakenbake', 'test@gmail.com', 23, 'afaefeafe', 0),
        ('test','john@smith.com', 21, '$2a$10$7Ucy.bfJQ3nBF1vYG19o/eTAUhGidYL52A3d2ZOkvDI6gBFj.f6qW', 0),
        ('newPlayer','new@player.com', 16, '$2a$10$7Ucy.bfJQ3nBF1vYG19o/eTAUhGidYL52A3d2ZOkvDI6gBFj.f6qW', 0); -- use for delete
        
	insert into app_user_role
    values
		(1, 2),
		(2, 1),
        (3, 2),
        (5,2),
        (4,1);
        
	insert into player (first_name, last_name, position, jersey_number, fantasy_points, team_id)
    values
		('Stephen', 'Curry', 'PG', 30, 241.23, 2),
        ('Jalen', 'Brunson', 'PG', 11, 999999.9999, 1), -- use for update
        ('Lebron', 'James', 'SF', 23, 45.14, 1),
        ('Luka', 'Doncic', 'PG', 77, -777.5, 1); -- use for delete
        
	insert into game (season, home_team_id, away_team_id, home_team_score, away_team_score, game_date)
    values 
		(2016, 1, 2, 104, 97, '2016-01-25');
    
    insert into fantasy_league (`name`, season, admin_id)
    values 
		('ShakeNBake', 2023, 1),
        ('FromDowntown', 2024, 2),  -- use for update
        ('CurryFromThree', 2020, 3);  -- use for delete
        
	insert into fantasy_team (fantasy_team_id, user_id, player_id, fantasy_league_id)
    values
		(1, 1, 1, 1),
        (1, 1, 2, 1),
        (1, 1, 3, 1),
		(4, 4, 2, 2), -- use for update
		(2, 2, 2, 2),
		(3, 3, 1, 1), -- use for delete player
        (3, 3, 2, 1),
        (3, 3, 3, 1),
		(4, 4, 1, 2), -- use for delete entire team
        (4, 4, 3, 2);
        
	insert into league_members (fantasy_league_id, user_id)
    values 
		(1, 1),
        (2, 2), -- use for update
		(3, 3), -- use for delete
        (1,4),
        (1,5);

    
end //
-- 4. Change the statement terminator back to the original.
delimiter ;
# Currently Unnamed NBA Fantasy Website
## HTD Cohort 6 - Group 6 AKA Exception Handlers
### Nicolas Perez, Kevin Sanchez, Krishna Joshi, Asim Shariff


## Problem Statement
* Context: 
  * Fantasy sports has grown into a massive global industry with many fans creating virtual teams based on current NBA athletes. While many major league platforms exist, they often provide fantasy sports as a small section or portion of their site, we focus our entire site on fantasy, to provide users with a more personal fantasy experience.

* Core Problem: 
  * Existing NBA fantasy platforms typically offer fantasy leagues as just one small feature among many different features. This leaves users who want to focus solely on fantasy sports with a cluttered interface full of unneeded elements that cloud their experience and distract from their primary interest.

* Impact on Users: 
  * NBA fantasy enthusiasts are left with depthless experiences that don’t fully satisfy their desire for a fully focused fantasy application, meaningful connections with other fantasy players, and real time stat and schedule tracking.

* Opportunity for Solution: 
  * By creating a dedicated application focused exclusively on NBA fantasy basketball, we can provide users with a richer, more personal experience that brings together comprehensive team building, detailed player and team statistics, and integrated social features. This specialized approach will allow fantasy enthusiasts to make better decisions regarding team creation, engage in a meaningful way with other fantasy players, and access an application whose sole focus is fantasy sports and nothing else.

## Technical Solution
* We will build a web application that is focused exclusively on NBA fantasy basketball that eliminates all the clutter found on other major fantasy platforms. Our application will provide users with a streamlined experience centered around creating and managing fantasy teams, accessing real-time player statistics, viewing game schedules, and interacting with other basketball fantasy enthusiasts. By focusing solely on fantasy basketball, we'll deliver a more personal and engaging platform that addresses the core needs of NBA fantasy players without other unnecessary distractions.

* Key Features:

  * User Registration and Profile Managment - Users can create accounts, customize profiles, and manage their fantasy participation setting. (All users, can create fantasy teams but only admins can create, modify and delete fantasy leagues).

  * Fantasy Team Creation and Management - Users can Create multiple fantasy teams, draft unique players within each fantasy team.

  * Player Statistics Dashboard - Users can view real time player statistics displayed through appealing visualization methods (possibly Chart.js), helping users make informaed decisions about players based on data.

  * Game Schedule - Users can see upcoming games and view team and player performances from recent games.

  * Social Interaction Features - There will be a chat feed for all users to communicate with each other and also a private leauge chat for users to communicate with other users in the leauges. (Stretch Goal)

  * Real-time Notifications - Injury reports and statistics for recent games will be updated in real time.

* User Scenarios:

  * Scenario 1: Krishna, a fantasy basketball fan, logs into the application to prepare for his upcoming fantasy season. After a successful authentication, he immediately checks the latest injury reports for key players he is considering for his team. He browses through player statistics, filtering by position and taking note of player performances. After analyzing several players' recent performances, he decides which players to target for his fantasy team. He creates a new league called the "Downtown Ballers", and invites his friends to join via the platform. As league creator, he automatically becomes the admin with privileges to manage league settings.

  * Scenario 2: Asim joins a fantasy league after his friend told him about a recently created league he created. After logging in, he navigates to the league dashboard and joins the team "Hoop City" and views which players are still up for selection. He strategically starts to build his team but notices that players selected by other team members are not available for selection anymore. Each day he checks his fantasy score, which updates automatically based on his players' performance from previous night's games. He really enjoys tracking his team's ranking within the league and the competitive aspect of seeing his calculated fantasy score compared to other teams.


* Technology Stack:

  * Frontend:

    * TypeScript for type-safe code development
    * React for building a responsive and interactive interface with reausable components
    * Chart.js for creating dynamic and visually appealing charts for player statistics.

  * Backend:

    * Java with SpringBoot for building a scalable REST API that can handle user authentication and data processing.
    * JWT for secure authentication and authorization

  * Database:

    * MySQL, a reliable relational database that can handle complex relationships between users, teams, players, and leauges.

  * External APIs:

    * SportRadar API for accessing comprehensive NBA team schedules and box scores
    * Swagger Sports API for accessing indivdual players and teams

* DevOps:

  * Docker for containerization, enuring consistent deployment across different enviorments.

## Glossary

* Player
  * A person who plays for their respective team. They can be drafted to teams, dropped from teams and edited positions based on their performance or if they're injured.

* User
  * User who can view and participate in fantasy leagues. Needs to create an account before they can access created leagues.

* Admins
  * Users who can create fantasy leagues and invite other users into their leagues.

* Fantasy League
  * Participants(Users and Admins) assemble virtual teams of real-life players, and their performance in actual games earns points for their fantasy teams, with the goal of accumulating the most points and winning against other teams in the league

 
## High Level Requirements
 * Manage 4-7 database tables (entities) that are independent concepts.
   * We will implement 6 tables to fit our needs and provide a place to put Users, Leagues, Teams, and Interactions.
 * MySQL for data management
   * We will be using a MySQL database contained within a Docker Container, to store the data we create, and access and modify it using JDBC
 * Spring Boot, MVC, JDBC, Testing
   * SpringBoot will be used to run our application and provide the environment to the application.
   * We will be organizing our code using the Model, View, Controller architecture to cleanly follow OOP Principals.
   * JDBC AS previously mentioned will be used to connect to our Database, and access and modify data.
   * We will be writing Unit Tests and performing Manual Integration tests.
 * An HTML and CSS UI that's built with React
   * We will be using React to create our frontend based on the created Wireframe, and will apply Accessibility principals to make sure our app can be enjoyed by everyone.
 * Sensible layering and pattern choices
   * We will make sure that our Data, Service and Controller layers will function independently of each other, and each only provides one role to the project, allowing for flexibility and modularity.
 * A full test suite that covers the domain and data layers
   * We will be testing both the Front and Back end of the project, both separately as Unit Tests and together as Manual Integration Tests.
   * We will in particular be writing unit tests for the data, and service layers to make sure the data we will be providing will be clean, validated, and accessible.
 * Must have at least 2 roles (example User and Admin)
   * User: Users will be able to join leagues, create teams, view teams/players 
   * Admin: Additionally, admins will be able to create and manage Leagues as well as User members of those Leagues.
 

## User Stories
* Admin (CRUD Operations)
  * Create
    * Create a Fantasy League:
      * Goal:
        * As an Admin, I want to be able to create leagues that other users can join. 
      * Plan to Meet Requirement:
        * We will create forms to create leagues, including name and season. 
      * Pre-condition:
        * Only Admins will be able to create Leagues. Admins must be logged in.
      * Post-Condition:
        * Leagues will be created immediately.
  * Update
    * Update a Fantasy League:
      * Goal:
        * As an Admin, I want to be able to update leagues to include or remove league members
      * Plan to Meet Requirement:
        * We will create button to add or remove users to a specific fantasy league, 
        * then we will update the league in the database, and once apart of a league the members can draft players for their fantasy team.
      * Pre-condition:
        * Admins will be able to update a single league if they are signed in and the league is not full.
      * Post-Condition:
        * Admins will be able to add users to the league
  * Delete
    * Delete a Fantasy League:
      * Goal:
        * As an Admin, I want to be able to delete leagues that have served their purpose.
      * Plan to Meet Requirement:
          * We will create a button on the league page that will delete the league, and this will only be visible if the user is an Admin.
      * Pre-condition:
          * Only Admins will be able to delete Leagues. Admins must be logged in.
      * Post-Condition:
          * Leagues will be deleted after a 2nd confirmation from the admin.

* Both Admin and LeagueMembers (CRUD Operations)
  * Create
    * Add Players to a Fantasy Team:
      * Goal:
        * As a league member, I want to be able to add players to my fantasy team.
      * Plan to Meet Requirement:
        * We will display a list of available players to the league members and have a button to add the clicked player to the league members team.
      * Pre-condition:
        * Only logged in users that are apart of a league can add players to their fantasy team.
      * Post-Condition:
        * Players will be added to the league member teams
  * Read
    * View all players on a Fantasy Team:
      * Goal:
        * As a league member, I want to be able to see all the players on my fantasy team.
      * Plan to Meet Requirement:
        * We will create a page to display the players for a specific league member's fantasy team.
      * Pre-condition:
        * Only logged in users that are apart of a league can view their fantasy team.
      * Post-Condition:
        * All players for a fantasy team will be displayed on a webpage.
    * View the leaderboard in a fantasy league:
      * Goal:
        * As a league member, I want to be able to see a leaderboard of a league, 
        * where the members are ordered by who's fantasy team has the most total fantasy points.
      * Plan to Meet Requirement:
        * We will create a page to display a leaderboard for a specific fantasy league.
      * Pre-condition:
        * Only logged in users that are apart of a league can view the leaderboard.
      * Post-Condition:
        * A leader board will be displayed to the league members.
  * Update
    * Update a player on a Fantasy Team:
      * Goal:
        * As a league member, I want to be able to update a player on my team to a different player (swap a player on my team).
      * Plan to Meet Requirement:
        * We will create a button that will allow the league member to select a player 
        * they want to swap, then a list of available players will be displayed, 
        * the member will select the new player, then we update the player fantasy team and display the updated team.
      * Pre-condition:
        * Only logged in users that are apart of a league can update a player on their fantasy team.
      * Post-Condition:
        * Player will be updated for a fantasy team.
  * Delete
    * Remove a player on a Fantasy Team:
      * Goal:
        * As a league member, I want to be able to remove a player on my fantasy team.
      * Plan to Meet Requirement:
        * We will create a button next to the players on a fantasy team to remove the player on the team.
      * Pre-condition:
        * Only logged in users that are apart of a league can remove a player from their fantasy team.
      * Post-Condition:
        * Player will be removed from a fantasy team.


## Learning Goal
* Learning Goal Item:
  * TypeScript
* Application:
  * We will use Typescript to exert static typing and stricter control on class member visibility over our app's programming.
* Research and Resources:
  * The official TypeScript Handbook will be invaluable to see the differences between base Javascript and Typescript
    * https://www.typescriptlang.org/docs/handbook/intro.html
  *
* Challenges:
  * Falling back on JS variables and typing would be easy, so we will commit to only using TypeScript throughout the project.
* Success Criteria:
  * If we are able to complete the project's frontend without falling back onto basic Javascript type handling, we'll consider this goal achieved.

## Class Diagram
src  
├───main  
│ ├───java  
│ │ └───learn  
│ │ └───fantasy  
│ │ │ App.java   
│ │ │  
│ │ ├───data  
│ │ │ DataException.java  
│ │ │ PlayerJdbcRepository.java  
│ │ │ PlayerRepository.java  
│ │ │ GameJdbcRepository.java  
│ │ │ GameRepository.java  
│ │ │ UserJdbcRepository.java  
│ │ │ UserRepository.java  
│ │ │ FantasyTeamRepository.java  
│ │ │ FantasyTeamJdbcRepository.java  
│ │ │ FantasyLeagueRepository.java  
│ │ │ FantasyLeagueJdbcRepository.java  
│ │ │  
│ │ └───mappers  
│ │ │ PlayerMapper.java  
│ │ │ GameMapper.java  
│ │ │ UserMapper.java  
│ │ │ FantasyTeamMapper.java  
│ │ │ FantasyLeagueMapper.java  
│ │  
│ │  
│ │ ├───domain  
│ │ │ PlayerService.java  
│ │ │ GameService.java  
│ │ │ UserService.java  
│ │ │ FantasyTeamService.java  
│ │ │ FantasyLeagueService.java  
│ │ │ Response.java  
│ │ │ Result.java  
│ │ │  
│ │ ├───models  
│ │ │ Team.java  
│ │ │ Player.java  
│ │ │ Game.java  
│ │ │ User.java  
│ │ │ FantasyTeam.java  
│ │ │ FantasyLeague.java  
│ │ │ Role.java  
│ │ │  
│ │ ├───security  
│ │ │ JwtConverter.java  
│ │ │ AuthController.java  
│ │ │ SecurityConfig.java  
│ │ │ JwtRequestFilter.java  
│ │ │  
│ │ └───controller  
│ │ PlayerController.java  
│ │ GameController.java  
│ │ UserController.java  
│ │ FantasyTeamController.java  
│ │ FantasyLeagueController.java  
│ │  
│ └───resources  
└───test  
└───java  
└───learn  
└───fantasy  
├───data   
│ PlayerJdbcRepositoryTest.java  
│ GameJdbcRepositoryTest.java  
│ UserJdbcRepositoryTest.java  
│ FantasyTeamJdbcRepositoryTest.java  
│ FantasyLeagueJdbcRepositoryTest.java  
│  
└───domain  
PlayerServiceTest.java  
GameServiceTest.java  
UserService.java  
FantasyTeamServiceTest.java  
FantasyLeagueServiceTest.java  

## Class List
### App
* public static void main(String[]) -- instantiate all required classes with valid arguments, dependency injection. run controller

### data.DataException
* public DataException(String, Exception) -- constructor
* public DataException( Exception) -- constructor

### data.PlayerRepository
* List<Player> findByPosition(String position)
* List<Player> findAll()
* Player add(Player)
* boolean update(Player)
* boolean deleteById(int)

### data.PlayerJdbcRepository
* private final JdbcTemplate jdbcTemplate
* List<Player> findByPosition(String position) -- returns list of all players for certain position
* List<Player> findAll() -- returns list of all players
* Player add(Player) -- adds a player to the sql database
* boolean update(Player) -- updates a player in the sql database, returns true if it was successful
* boolean deleteById(int) -- deletes a player in the sql database, returns true if it was successful

### data.GameRepository
* List<Game> findByDate(Date date)
* List<Game> findAll()
* List<Game> findByTeam(String team)
* Game add(Game)
* boolean update(Game)
* boolean deleteById(int)

### data.GameJdbcRepository
* private final JdbcTemplate jdbcTemplate
* List<Game> findByDate(Date date) -- return list of all games for a specific date
* List<Game> findAll() -- return list of all games
* List<Game> findByTeam(String team) -- return games for a specific team
* Game add(Game) -- adds a game to the sql database
* boolean update(Game)  -- update a game in the sql database, returns true if it was successful
* boolean deleteById(int) -- deletes a game in the sql database, returns true if it was successful

### data.UserRepository
* List<User> findAll()
* User add(User)
* boolean update(User)
* boolean deleteById(int)

### data.UserJdbcRepository
* private final JdbcTemplate jdbcTemplate
* List<User> findAll() -- return list of all users
* User add(User) -- adds a user to the sql database
* boolean update(User) -- update a user in the sql database, returns true if it was successful
* boolean deleteById(int) -- delete a user in the sql database, returns true if it was successful

### data.FantasyTeamRepository
* List<FantasyTeam> findAll()
* FantasyTeam add(FantasyTeam)
* boolean update(FantasyTeam)
* boolean deleteById(int)

### data.FantasyTeamJdbcRepository
* private final JdbcTemplate jdbcTemplate
* List<FantasyTeam> findAll() -- return list of all fantasy team
* FantasyTeam add(FantasyTeam) -- adds a fantasy team to the sql database
* boolean update(FantasyTeam) -- update a fantasy team in the sql database, returns true if it was successful
* boolean deleteById(int) -- delete a fantasy team in the sql database, returns true if it was successful

### data.FantasyLeagueRepository
* List<FantasyLeague> findAll()
* FantasyLeague add(FantasyLeague)
* boolean update(FantasyLeague)
* boolean deleteById(int)

### data.FantasyLeagueJdbcRepository
* private final JdbcTemplate jdbcTemplate
* List<FantasyLeague> findAll() -- return list of all fantasy league
* FantasyLeague add(FantasyLeague) -- adds a fantasy league to the sql database
* boolean update(FantasyLeague)  -- update a fantasy league in the sql database, returns true if it was successful
* boolean deleteById(int) -- delete a fantasy league in the sql database, returns true if it was successful

### data.mappers.PlayerMapper
* Player mapRow(ResultSet resultSet, int i) - maps sql to java object

### data.mappers.GameMapper
* Game mapRow(ResultSet resultSet, int i) - maps sql to java object

### data.mappers.UserMapper
* User mapRow(ResultSet resultSet, int i) - maps sql to java object

### data.mappers.FantasyTeamMapper
* FantasyTeam mapRow(ResultSet resultSet, int i) - maps sql to java object

### data.mappers.FantasyLeagueMapper
* FantasyLeague mapRow(ResultSet resultSet, int i) - maps sql to java object

### domain.Response
* private List<String> messages
* public boolean isSuccess() -- if there are no error messages, return true
* public List<String> getErrorMessages() -- return copy of list of error messages
* public void addErrorMessage(String) -- add an error message to list of
  messages

### domain.Result
* private T payload
* Getters and Setters

### domain.PlayerService
* private final PlayerRepository playerRepository
* List<Player> findByPosition(String position) -- returns list of all players for certain position
* List<Player> findAll() -- returns list of all players
* Result<Player> add(Player) -- adds a player to the sql database using player repository
* Result<Player> update(Player) -- updates a player in the sql database using player repository
* boolean deleteById(Player) -- deletes a player in the sql database using player repository
* Result<Player> validate(Player) -- validates the player follows all business logic

### domain.GameService
* private final GameRepository gameRepository
* List<Game> findByDate(Date date) -- return list of all games for a specific date
* List<Game> findAll() -- return list of all games
* List<Game> findByTeam(String team) -- return games for a specific team
* Result<Game> add(Game) -- adds a game to the sql database using game repository
* Result<Game> update(Game)  -- update a game in the sql database using game repository
* boolean deleteById(Game) -- deletes a game in the sql database using game repository
* Result<Game> validate(Game) -- validates the game follows all business logic

### domain.UserService
* private final UserRepository userRepository
* List<User> findAll() -- return list of all users
* Result<User> add(User) -- adds a user to the sql database using user repository
* Result<User> update(User) -- update a user in the sql database using user repository
* boolean deleteById(User) -- delete a user in the sql database using user repository
* Result<User> validate(User) -- validates the user follows all business logic

### domain.FantasyTeamService
* private final FantasyTeamRepository fantasyTeamRepository
* List<FantasyTeam> findAll() -- return list of all fantasy team
* Result<FantasyTeam> add(FantasyTeam) -- adds a fantasy team to the sql database using fantasy team repository
* Result<FantasyTeam> update(FantasyTeam) -- update a fantasy team in the sql database using fantasy team repository
* boolean deleteById(FantasyTeam) -- delete a fantasy team in the sql database using fantasy team repository
* Result<FantasyTeam> validate(FantasyTeam) -- validates the Fantasy Team follows all business logic

### domain.FantasyLeagueService
* private final FantasyLeagueRepository fantasyLeagueRepository
* List<FantasyLeague> findAll() -- return list of all fantasy league
* Result<FantasyLeague> add(FantasyLeague) -- adds a fantasy league to the sql database using fantasy league repository
* Result<FantasyLeague> update(FantasyLeague)  -- update a fantasy league in the sql database using fantasy league repository
* boolean deleteById(FantasyLeague) -- delete a fantasy league in the sql database using fantasy league repository
* Result<FantasyLeague> validate(FantasyLeague) -- validates the Fantasy League follows all business logic


### controller.ErrorResponse
* private final LocalDateTime timestamp
* private final String message
* public ErrorResponse(String message) -- constructor
* public static ResponseEntity<ErrorResponse> build(String message)
* public static \<T> ResponseEntity \<Object> build(Result\<T> result)
* Getters and Setters

### controller.GlobalExceptionHandler
* public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex) -- Specific exception handler
* public ResponseEntity<ErrorResponse> handleException(Exception ex) -- General Exception handler

### controller.PlayerController
* private final PlayerService playerService
* List<Player> findByPosition(String position) -- returns list of all players for http get request is made
* List<Player> findAll() -- returns list of all players for http get request is made
* ResponseEntity\<Object> add(Player) -- adds a player once http post request is made
* ResponseEntity\<Object> update(Player) -- updates a player once http put request is made
* ResponseEntity\<Void> deleteById(Player) -- deletes a player once http delete request is made

### controller.GameController
* private final GameService gameService
* List<Game> findByDate(Date date) -- return list of all games for a specific date for http get request is made
* List<Game> findAll() -- return list of all games for http get request is made
* List<Game> findByTeam(String team) -- return games for a specific team
* Result<Game> add(Game) -- adds a game once http post request is made
* Result<Game> update(Game)  -- update a game once http put request is made
* boolean deleteById(Game) -- deletes a game once http delete request is made

### controller.UserController
* private final UserService userService
* List<User> findAll() -- return list of all users for http get request is made
* Result<User> add(User) -- adds a user once http post request is made
* Result<User> update(User) -- update a user once http put request is made
* boolean deleteById(User) -- delete a user once http delete request is made

### controller.FantasyTeamController
* private final FantasyTeamService fantasyTeamService
* List<FantasyTeam> findAll() -- return list of all fantasy team for http get request is made
* Result<FantasyTeam> add(FantasyTeam) -- adds a fantasy team once http post request is made
* Result<FantasyTeam> update(FantasyTeam) -- update a fantasy team once http put request is made
* boolean deleteById(FantasyTeam) -- delete a fantasy team once http delete request is made

### controller.FantasyLeagueController
* private final FantasyLeagueService fantasyLeagueService
* List<FantasyLeague> findAll() -- return list of all fantasy league for http get request is made
* Result<FantasyLeague> add(FantasyLeague) -- adds a fantasy league once http post request is made
* Result<FantasyLeague> update(FantasyLeague)  -- update a fantasy league once http put request is made
* boolean deleteById(FantasyLeague) -- delete a fantasy league once http delete request is made

### security.JwtConverter
* String getTokenFromUser (User) -- returns string token from user
* User getUserFromToken (String) -- returns the user with granted authorities based on the token

### security.AuthController
* ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) -- function to authenticate the users login attempt, returns response for success or failure

### security.SecurityConfig
* void configure(HttpSecurity http) -- function to configure requests
* AuthenticationManager authenticationManager() -- helper method for authenticating 
* PasswordEncoder getEncoder() -- helper method for encrypting password

### security.JwtRequestFilter
* void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) -- function to apply servlet filter for establishing user permissions before a response is generated.

### models.Team
An enum with information for all 30 NBA teams.
* private String name
* private String abbreviation
* private String conference
* private String division

### models.Player
* private int playerId
* private String firstName
* private String lastName
* private int jerseyNumber
* private String position
* private double fantasyPoints
* private Team team
* Constructor
* Getters and Setters

### models.Game
* private int gameId
* private int season
* private Team homeTeam
* private Team awayTeam
* private int homeTeamScore
* private int awayTeamScore
* private Date gameDate
* Constructor
* Getters and Setters

### models.User
* private int userId
* private String username
* private String email
* private Role role
* private String favoriteTeam
* private String passwordHash
* Constructor
* Getters and Setters

### models.FantasyTeam
* private int fantasyTeamId
* private User user
* private List<Player> players
* private FantasyLeague fantasyLeague
* Constructor
* Getters and Setters

### models.FantasyLeague
* private int fantasyLeagueId
* private String name
* private int season
* private User admin
* private List<User> members
* Constructor
* Getters and Setters

### models.Role
An enum with all available roles (ex. Admin, LeagueMember)


## Task List
* Kevin and Nicolas on Frontend, Asim and Krishna on backend

## Day 1 (Monday)

## Backend Tasks
* Configure Spring Boot project structure and dependencies (30 min) - Krishna
* Create initial application properties file (15 min) - Krishna
* Set up Docker configuration for MySQL database (45 min) - Asim 
* Create database schema SQL files for all tables (1 hour) - Asim
* Create Player and Team model classes (45 min) - Asim
* Create Game model class (30 min) - Asim
* Create User and Role model classes (45 min) - Krishna
* Create FantasyTeam model class (30 min) - Krishna
* Create FantasyLeague model class (30 min) - Krishna
* Setup Spring Security base configuration (1 hour) - Krishna
* Configure JWT security components (1 hour) - Krishna
* Create DataException Class (15 min) - Asim
* Create Response and Result Classes (30 min) - Asim
* Create PlayerRepository interface (30 min) - Krishna
* Create GameRepository interface (30 min) - Krishna
* Create UserRepository interface (30 min) - Asim
* Create FantasyTeamRepository interface (30 min) - Krishna
* Create FantasyLeagueRepository interface (30 min) - Krishna
* Create mapper classes for database objects (1 hour) - Asim

## Frontend Tasks
* Set up React with TypeScript project (20 min) - Nicolas
* Configure routing with React Router (40 min) - Nicolas
* Create navigation component (1 hour) - Nicolas
* Create homepage component skeleton (1 hour) - Kevin
* Create login form o (1 hour) - Nicolas
* Create registration form component (1 hour) - Kevin
* Create basic UI layout and theme (1 hour) - Kevin
* Create user profile page (1 hour) - Nicolas
* Create teams page (1 hour) - Kevin
* Create players page (1 hour) - Nicolas
* Create create fantasy page (1 hour) - Kevin
* Implement all popup components (2 hours) - Nicolas & Kevin

## Day 2 (Tuesday)

## Backend Tasks
* Create PlayerJdbcRepository (1 hour) - Krishna
* Create GameJdbcRepository (1 hour) - Krishna
* Create UserJdbcRepository (1 hour) - Asim
* Create FantasyTeamJdbcRepository (1 hour) - Asim
* Create FantasyLeagueJdbcRepository (1 hour) - Krishna
* Create PlayerService with validation (1 hour) - Krishna
* Create GameService with validation (1 hour) - Asim
* Create UserService with validation (1 hour) - Asim
* Create FantasyTeamService with validation (1 hour) - Asim
* Create FantasyLeagueService with validation (1 hour) - Krishna
* Set up JWT token generation and validation (1 hour) - Asim
* Configure CORS settings (30 min) - Asim
* Create PlayerController endpoints (1 hour) - Krishna
* Create GameController endpoints (1 hour) - Krishna
* Create Jwtconverter class (30 min) - Asim
* Create JwtRequestFilter class (30 min) - Asim

## Frontend Tasks
* Create authentication context (1 hour) - Nicolas
* Create protected route component (30 min) - Nicolas
* Complete login functionality with API integration (1 hour) - Nicolas
* Complete registration functionality (1 hour) - Kevin
* Create Teams page with API integration (2 hours) - Nicolas
* Create Players page with basic listing (2 hours) - Kevin
* Create user profile component (1 hour) - Nicolas
* Implement edit profile functionality (1 hour) - Kevin
* Create fantasy team component (2 hours) - Nicolas
* Create league browsing component (2 hours) - Kevin

## Day 3 (Wednsday)

## Backend Tasks
* Create UserController endpoints (1 hour) - Asim
* Create FantasyTeamController endpoints (1 hour) - Krishna
* Create FantasyLeaugeController endpoints (1 hour) - Asim
* Create AuthController for login and register (1 hour) - Krishna
* Create GlobalExpectionHandler (30 min) - Asim
* Set up custom Error Response (30 min) - Krishna
* Write unit tests for PlayerRepositoryJdbc (1 hour) - Asim
* Write unit tests for GameRepositoryJdbc (1 hour) - Krishna
* Write unit tests for UserRepositoryJdbc (1 hour) - Asim
* Write unit tests for FantasyRepositoryJdbc (1 hour) - Krishna
* Write unit tests for PlayerService (1 hour) - Asim
* Write unit tests for GameService (1 hour) - Krishna
* Implement NBA API integration for player data (2 hours) - Asim
* Implement NBA API integration for game data (2 hours) - Krishna



## Frontend Tasks
* Implement player drafting interface (2 hours) - Nicolas
* Create league management components for admins (2 hours) - Kevin
* Implement fantasy team management (2 hours) - Nicolas
* Create team stats visualization with Chart.js (2 hours) - Kevin
* Implement user management for admin (1 hour) - Nicolas
* Add CSS slying using Bootstrap (2 hours) - Kevin
* Implement form validation on all forms (1 hour) - Kevin
* Integrate all components (1 hour) - Nicolas
* End-to-end testing of application flows (just as users, 2 hours) - Kevin & Nicolas


## Day 4 (Thursday)

## Final Testing and touchups
* Testing all endpoints (2 hours) - ALL
* Test user authentication flows (1 hour) - Nicolas & Krishna
* Test fantasy team creation and management (1 hour) - Kevin & Asim
* Test leauge creation and management (1 hour) - Nicolas & Krishna
* Fix any identified backend bugs (2 hours) - Asim & Krishna
* Fix and identified frontend bugs (2 hours) - Nicolas & Kevin
* Final performance check (1 hour) - ALL
* Preperation for demonstration (2 hours) - ALL


## WireFrames Labels

### HomePage
* Homepage for non logged in users (general homepage)

### Create Account Popup
* Popup that displays when signup button is selected

### Login Popup
* Popup that displays when login button is selected

### User Page
* Homepage for users once logged in. Will have account information, and some kind of picture or highlight displayed on their account. (Community feed is a stretch goal, if able to do this will be there instead of highlight)

### Edit Account Popup
* Popup that is displays once a user selects edit account and this will allow them to change their name and number or delete theie user account.

### Teams Page
* Page that will have all NBA teams/logos and once you click on a logo you will be directed to that teams page that will have details about that team

### Team Page
* Page that displays after selecting a team logo on teams page. this page will have the players that play for that team and also their schedule. (possibly news for that team)

### Players Page
* This page will have details about fantasy players and will display things like performance stats and injury updates.

### Fantasy Page
* This page will display to the user their leauge if they have one and if not it will have a create leauge button. This page will alos allow a user to view their leauge or leave their leauge. If the user is an admin they can select the edit button. Also this page will display other leauges within the application.

### View Leauge Page
* This page will display when a user selects on view leauge from the fantasy page. This will have leauge details for their specified leauge. This page will house a leader chart for the top performers within that leauge.

### Create Leauge Popup
* This will be a popup that displays a form to a user and it will allow them to create a new leauge.

### Add Player Popup
* This will be a page after you descide to create a leauge that will allow you to select players that you want on you fantasy team. 

### Edit Leauge Popup
* This page will allow only an admin to be able to change the name of the leauge or they can select a delete player or delete leauge button that will direct them to a popup.

### Delete Leauge Popup 
* This will be the popup that will show the admin details about the leauge and ask them if theu want to delete the leauge or if they want to cancel the operation.

### Remove Player Popup
* This will be a page that will show the admin what players are currently in that leauge and allow them to select players to remove from the leauge.
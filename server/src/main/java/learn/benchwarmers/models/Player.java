package learn.benchwarmers.models;

public class Player {
    //fields
    private int playerId;
    private String firstName;
    private String lastName;
    private String position;
    private int jerseyNumber;
    private double fantasyPoints;
    private Team team;

    //constructor
    //default constructor
    public Player(){}

    //constructor for testing
    public Player(int playerId, String firstName, String lastName,  String position, int jerseyNumber, double fantasyPoints, Team team) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.fantasyPoints = fantasyPoints;
        this.team = team;
        this.position = position;
    }

    //Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public double getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(double fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

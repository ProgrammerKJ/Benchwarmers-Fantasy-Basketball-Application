package learn.benchwarmers.models;

import java.util.ArrayList;
import java.util.List;

public class FantasyTeam {
    //fields
    private int fantasyTeamId;
    private AppUser appUser;
    private List<Player> players = new ArrayList<>();
    private FantasyLeague fantasyLeague;

    private double totalPoints;

    //constructor
    //default constructor
    public FantasyTeam(){}

    //constructor for testing
    public FantasyTeam(int fantasyTeamId, AppUser appUser, List<Player> players, FantasyLeague fantasyLeague) {
        this.fantasyTeamId = fantasyTeamId;
        this.appUser = appUser;
        this.players = players;
        this.fantasyLeague = fantasyLeague;
    }

    //Getters and Setters
    public int getFantasyTeamId() {
        return fantasyTeamId;
    }

    public void setFantasyTeamId(int fantasyTeamId) {
        this.fantasyTeamId = fantasyTeamId;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public FantasyLeague getFantasyLeague() {
        return fantasyLeague;
    }

    public void setFantasyLeague(FantasyLeague fantasyLeague) {
        this.fantasyLeague = fantasyLeague;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }
}

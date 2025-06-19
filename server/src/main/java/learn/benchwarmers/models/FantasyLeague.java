package learn.benchwarmers.models;

import java.util.List;

public class FantasyLeague {
    //fields
    private int fantasyLeagueId;
    private String name;
    private int season;
    private AppUser admin;
    private List<AppUser> members;

    //constructor
    //default constructor
    public FantasyLeague(){}

    //constructor for testing
    public FantasyLeague(int fantasyLeagueId, String name, int season, AppUser admin, List<AppUser> members) {
        this.fantasyLeagueId = fantasyLeagueId;
        this.name = name;
        this.season = season;
        this.admin = admin;
        this.members = members;
    }

    //Getters and Setters
    public int getFantasyLeagueId() {
        return fantasyLeagueId;
    }

    public void setFantasyLeagueId(int fantasyLeagueId) {
        this.fantasyLeagueId = fantasyLeagueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public AppUser getAdmin() {
        return admin;
    }

    public void setAdmin(AppUser admin) {
        this.admin = admin;
    }

    public List<AppUser> getMembers() {
        return members;
    }

    public void setMembers(List<AppUser> members) {
        this.members = members;
    }
}

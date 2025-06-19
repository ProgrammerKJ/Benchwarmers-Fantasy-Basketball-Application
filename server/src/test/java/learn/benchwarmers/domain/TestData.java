package learn.benchwarmers.domain;

import learn.benchwarmers.models.*;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static AppUser makeUser(){
        List<String> roles = new ArrayList<>();
        roles.add("LEAGUE_MEMBER");
        AppUser user = new AppUser(1, "testUserOne", "userPassword", false, roles);
        user.setEmail("testUserOne@yahoo.com");
        user.setFavoriteTeam(Team.LAKERS);
        return user;
    }

    public static AppUser makeAdmin(){
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        AppUser admin = new AppUser(2, "testAdmin", "adminPassword", false, roles);
        admin.setFavoriteTeam(Team.CELTICS);
        return admin;
    }

    public static Player makePlayer(){
        Player player = new Player();
        player.setPlayerId(1);
        player.setFirstName("Lebron");
        player.setLastName("James");
        player.setPosition("PF");
        player.setJerseyNumber(23);
        player.setFantasyPoints(2500);
        player.setTeam(Team.LAKERS);
        return player;
    }

    public static FantasyLeague makeLeague(){
        FantasyLeague league = new FantasyLeague();
        league.setFantasyLeagueId(1);
        league.setName("Test League");
        league.setSeason(2025);
        league.setAdmin(makeAdmin());
        league.setMembers(new ArrayList<>(List.of(makeUser())));
        return league;
    }
}

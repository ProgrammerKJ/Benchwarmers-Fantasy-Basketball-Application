package learn.benchwarmers.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.benchwarmers.models.Player;
import learn.benchwarmers.models.Team;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NbaApiService {
    private final String API_KEY = "x125OrsPfaNWVs2N2n7TsfDeFreD5XeJrCOhrCd9";
    private final String BASE_URL = "https://api.sportradar.com/nba/trial/v8/en";
    private final OkHttpClientConfig client;
    private final ObjectMapper mapper = new ObjectMapper();

    public NbaApiService(OkHttpClientConfig client) {
        this.client = client;
    }

    // Api Call to fetch the top 25 players from the points category
    public List<Player> getTopPointsLeaders() {
        try {

            // Building the request
            String url = String.format("%s/seasons/%s/REG/leaders.json?api_key=%s", BASE_URL, LocalDate.now().getYear() - 1 , API_KEY);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("accept", "application/json")
                    .build();

            // Executing
            Response response = client.okHttpClient().newCall(request).execute();

            if (!response.isSuccessful()) {
                System.err.println("API request failed: " + response.code());
                return new ArrayList<>();
            }

            try (response) {
                if (response.body() == null) {
                    System.err.println("API response body is null");
                    return new ArrayList<>();
                }

                // mapping into tree node to find category lists
                String responseBody = response.body().string();
                JsonNode rootNode = mapper.readTree(responseBody);

                // Find the points category
                JsonNode categories = rootNode.get("categories");
                JsonNode pointsCategory = null;

                for (JsonNode category : categories) {
                    if ("points".equals(category.get("name").asText())) {
                        pointsCategory = category;
                        break;
                    }
                }

                if (pointsCategory == null) {
                    System.err.println("Points category not found in API response");
                    return new ArrayList<>();
                }

                // Process the top 25 players
                List<Player> players = new ArrayList<>();
                JsonNode ranks = pointsCategory.get("ranks");

                for (int i = 0; i < 25 && i < ranks.size(); i++) {
                    JsonNode rank = ranks.get(i);
                    Player player = convertApiDataToPlayer(rank);
                    if (player != null) {
                        players.add(player);
                    }
                }

                return players;
            }

        } catch (IOException e) {
            System.err.println("Error fetching data from API: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Finding a player by First & Last name within the API
    public Player findPlayerByName(String firstName, String lastName) {
        List<Player> allPlayers = getTopPointsLeaders();

        return allPlayers.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }


    // Converting API data into a player object
    private Player convertApiDataToPlayer(JsonNode playerData){
        try{
            // Collecting the nodes with player, stat, and team data
            JsonNode playerNode = playerData.get("player");
            JsonNode statsNode = playerData.get("total");
            JsonNode teamNode = playerData.get("teams").get(0);

            Player player = new Player();

            // Setting all player info
            player.setFirstName(playerNode.get("first_name").asText());
            player.setLastName(playerNode.get("last_name").asText());
            player.setPosition(playerNode.get("primary_position").asText());
            player.setJerseyNumber(Integer.parseInt(playerNode.get("jersey_number").asText()));

            // Setting the players fantasy points
            double fantasyPoints = calculateFantasyPoints(statsNode);
            player.setFantasyPoints(fantasyPoints);

            player.setTeam(mapTeamFromApi(teamNode.get("name").asText()));

            return player;
        }catch (Exception e){
            System.err.println("Error converting player data: " + e.getMessage());
            return null;
        }
    }

    // Fantasy Score Calculation Helper
    private double calculateFantasyPoints(JsonNode statsNode){
        double points = statsNode.get("points").asDouble();
        double assists = statsNode.get("assists").asDouble();
        double rebounds = statsNode.get("rebounds").asDouble();
        double blocks = statsNode.get("blocks").asDouble() * 3;
        double steals = statsNode.get("steals").asDouble() * 3;

        return points + assists + rebounds + blocks + steals;
    }

    // Setting Player Team Helper
    private Team mapTeamFromApi(String apiTeamName){
        for(Team team : Team.values()){
            if(team.getName().contains(apiTeamName)){
                return team;
            }
        }
        return null;
    }
}

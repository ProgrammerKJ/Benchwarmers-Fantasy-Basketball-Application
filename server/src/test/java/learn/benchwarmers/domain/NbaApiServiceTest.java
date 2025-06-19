package learn.benchwarmers.domain;

import learn.benchwarmers.models.Player;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NbaApiServiceTest {

    @Test
    void shouldGetTopPointsLeaders() {
        OkHttpClientConfig client = new OkHttpClientConfig();
        NbaApiService service = new NbaApiService(client);

        List<Player> players = service.getTopPointsLeaders();

        System.out.println(players);

        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertTrue(players.size() == 25);
    }
}
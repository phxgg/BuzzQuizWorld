import bqz.model.Game;
import bqz.model.Player;
import bqz.utility.Helper;
import bqz.utility.Logs;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class LogsTest {
    @Test
    public void saveTest() {
        Player[] players = new Player[2];

        players[0] = new Player("Stam");
        players[0].addPoints(100);
        players[0].removePoints(50);
        players[0].addPoints(200);

        players[1] = new Player("Nikos");
        players[1].addPoints(100);
        players[1].removePoints(50);
        players[1].addPoints(200);

        String questionsCsvFilePath = Paths.get(Helper.jarPath(), "questions.csv").toAbsolutePath().toString();
        Game game = new Game(players, 2, 2, questionsCsvFilePath);

        //Logs.save(game);
    }
}

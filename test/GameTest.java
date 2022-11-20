import bqz.model.Game;
import bqz.model.Player;
import bqz.utility.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class GameTest {
    @Test
    public void constructorTest() {
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

        Assert.assertEquals(2, game.getRounds());
        Assert.assertEquals(2, game.getPlayers().length);
        Assert.assertEquals(2, game.getQuestionsPerRound());
        Assert.assertEquals(4, game.getTotalQuestions());
    }
}

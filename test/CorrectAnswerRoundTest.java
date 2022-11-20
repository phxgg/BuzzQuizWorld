import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;
import bqz.model.enums.Category;
import bqz.model.rounds.CorrectAnswerRound;
import bqz.model.rounds.RoundHandler;
import bqz.utility.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

public class CorrectAnswerRoundTest {
    @Test
    public void evaluateQuestionTest() {
        Player[] players = new Player[2];

        players[0] = new Player("Stam");
        players[0].addPoints(100);
        players[0].removePoints(50);
        players[0].addPoints(200);
        // Check points right now
        Assert.assertEquals(250, players[0].getPoints());

        players[1] = new Player("Nikos");
        players[1].addPoints(100);
        players[1].removePoints(50);
        players[1].addPoints(200);
        // Check points right now
        Assert.assertEquals(250, players[1].getPoints());

        String questionsCsvFilePath = Paths.get(Helper.jarPath(), "questions.csv").toAbsolutePath().toString();
        Game game = new Game(players, 2, 2, questionsCsvFilePath);

        RoundHandler rh;
        rh = new CorrectAnswerRound(game);

        ArrayList<String> answers = new ArrayList<>();
        answers.add("correct answer");
        answers.add("lol");
        answers.add("haha");
        answers.add("another wrong answer");
        Question q = new Question(Category.General, "Is this a question?", answers, 0, null);

        // Correct answer check
        Assert.assertEquals("correct answer", q.getCorrectAnswer());

        // Check ability to set an answer
        players[0].setCurrentAnswer(1);
        Assert.assertEquals(0, players[0].getCurrentAnswer());

        // Evaluate question check
        Assert.assertTrue(rh.evaluateQuestion(players[0], q));

        // Updated points
        Assert.assertEquals(1250, players[0].getPoints());
        Assert.assertEquals(250, players[1].getPoints());
    }
}

package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;

/**
 * The CorrectAnswerRound class is the class in which the Correct Answer round is implemented.
 *
 * @author      phxgg, NikosZoros3533
 * @version     1.0
 * @since       28/11/2020
 */
public class CorrectAnswerRound extends RoundHandler {
    public CorrectAnswerRound(Game game) {
        super(game);
        //this.game = game;
    }

    @Override
    public boolean evaluateQuestion(Player p, Question q) {
        if (p.getCurrentAnswer() == q.getCorrectAnswerIndex()) {
            p.incrementTotalCorrect();
            p.incrementRoundCorrect();
            p.addPoints(1000);

            return true;
        } else {
            p.removePoints(100);

            return false;
        }
    }
}

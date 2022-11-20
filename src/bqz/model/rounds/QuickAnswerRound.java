package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;

/**
 * The QuickAnswerRound class is the class in which the Quick Answer round is implemented.
 *
 * @author      phxgg, NikosZoros3533
 * @version     2.0
 * @since       07/12/2020
 */
public class QuickAnswerRound extends RoundHandler {
    private Player first = null;

    public QuickAnswerRound(Game game) {
        super(game);
    }

    public Player getFirst() {
        return this.first;
    }

    /**
     * This method will be called after each question of QuickAnswerRound
     * so we can reset the player who gave the first correct answer.
     */
    public void resetFirst() {
        this.first = null;
    }

    @Override
    public boolean evaluateQuestion(Player p, Question q) {
        if (p.getCurrentAnswer() == q.getCorrectAnswerIndex()) {
            p.incrementTotalCorrect();
            p.incrementRoundCorrect();

            if (this.first == null) {
                p.addPoints(1000);
                this.first = p;
            } else {
                p.addPoints(500);
            }

            return true;
        } else {
            if (this.first == null) {
                p.removePoints(500);
            } else {
                p.removePoints(1000);
            }

            return false;
        }
    }
}

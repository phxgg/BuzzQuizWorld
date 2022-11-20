package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;

import java.util.HashMap;

/**
 * The BetRound class is the class in which the Betting round is implemented.
 *
 * @author      phxgg, NikosZoros3533
 * @version     1.0
 * @since       28/11/2020
 */
public class BetRound extends RoundHandler {
    private HashMap<Player, Integer> bets;

    public BetRound(Game game) {
        super(game);
        //this.game = game;

        this.bets = new HashMap<>();
    }

    public HashMap<Player, Integer> getBets() {
        return this.bets;
    }

    @Override
    public boolean evaluateQuestion(Player p, Question q) {
        if (p.getCurrentAnswer() == q.getCorrectAnswerIndex()) {
            p.incrementTotalCorrect();
            p.incrementRoundCorrect();
            p.addPoints(this.bets.get(p));

            return true;
        } else {
            p.removePoints(this.bets.get(p));

            return false;
        }
    }
}

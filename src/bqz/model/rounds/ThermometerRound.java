package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;

import java.util.HashMap;
import java.util.Map;

/**
 * The ThermometerRound class is the class in which the Thermometer round is implemented.
 *
 * @author      phxgg, NikosZoros3533
 * @version     2.0
 * @since       12/01/2021
 */
public class ThermometerRound extends RoundHandler {
    private HashMap<Player, Integer> answers;

    /**
     * This variable is used to end the round when one player has correctly answered 5 questions.
     */
    private boolean roundFinished = false;

    public ThermometerRound(Game game) {
        super(game);
        this.answers = new HashMap<>();

        for (Player p : game.getPlayers())
            this.answers.put(p, 0);
    }

    /**
     * This method is used to return each player's correct answers for the round in a HashMap.
     * @return  HashMap<Player, Integer> Number of correct answers
     */
    public HashMap<Player, Integer> getCorrectAnswers() {
        return this.answers;
    }

    /**
     * This method is used to return whether the round has finished.
     * @return  boolean Whether the round has finished
     */
    public boolean getRoundFinished() {
        return this.roundFinished;
    }

    /**
     * This method is used to check if a player has correctly answered five questions.
     * If this is true, then end the round and show results.
     * @return  boolean Whether 5 questions have been completed
     */
    public boolean hasCompletedFiveQuestions() {
        boolean flag = false;
        for (Map.Entry<Player, Integer> entry : this.answers.entrySet()) {
            if (entry.getValue() == 5) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void incrementAnswers(Player p) {
        this.answers.put(p, this.answers.get(p) + 1);
    }

    @Override
    public boolean evaluateQuestion(Player p, Question q) {
        if (!this.roundFinished) {
            if (p.getCurrentAnswer() == q.getCorrectAnswerIndex()) {
                incrementAnswers(p);

                if (this.answers.get(p) == 5) {
                    p.incrementTotalCorrect();
                    p.incrementRoundCorrect();
                    p.addPoints(5000);
                    this.roundFinished = true;
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

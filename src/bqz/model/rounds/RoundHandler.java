package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;

/**
 * The RoundHandler class represents a round.
 *
 * @author      phxgg
 * @version     1.0
 * @since       14/11/2020
 */
public abstract class RoundHandler {
    protected Game game;

    public RoundHandler(Game game) {
        this.game = game;
    }

    /**
     * This method is used to evaluate the correct answer depending on the type of round.
     * If the answer is correct it will return true, otherwise false.
     * This differs for each round, so the method will actually be overwritten in the corresponding Round class.
     * @param p Player  The player that will answer the question
     * @param q Question   The question that the player has to answer
     * @return  boolean Whether the answer is correct or not
     */
    public boolean evaluateQuestion(Player p, Question q) { return false; }

}

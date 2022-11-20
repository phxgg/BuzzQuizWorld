package bqz.model;

import java.io.Serializable;

/**
 * The Player class represents a player.
 *
 * @author      phxgg, NikosZoros3533
 * @version     1.0
 * @since       12/11/2020
 */
public class Player implements Serializable {

    /**
     * The name that will be displayed for the player.
     */
    private final String name;

    /**
     * Player's current game points.
     */
    private int points;

    /**
     * Total correct answers the player has given in the current game.
     */
    private int totalCorrect;

    /**
     * Total correct answers the player has given in the current round.
     */
    private int roundCorrect;

    /**
     * Current answer the player has given
     * The current answer could be 0, 1, 2, 3 (4 possible answers)
     * By default, this is initialized to -1, which reflects to none of the possible answers.
     */
    private int currentAnswer;

    /**
     * Whether the player can give an answer.
     */
    private boolean canAnswer;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.totalCorrect = 0;
        this.roundCorrect = 0;
        this.currentAnswer = -1;
        this.canAnswer = true;
    }

    /**
     * This method is used to return the player's name.
     * @return  String  Player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is used to return the player's total game points.
     * @return  int Total game points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * This method is used to return the player's total correct answers in the game.
     * @return  int Total correct answers in game
     */
    public int getTotalCorrect() {
        return this.totalCorrect;
    }

    /**
     * This method is used to return the player's total correct answers in the current round.
     * @return  int Total correct answers in round
     */
    public int getRoundCorrect() {
        return this.roundCorrect;
    }

    /**
     * This method is used to increment the player's total correct answers in the game by 1
     */
    public void incrementTotalCorrect() {
        this.totalCorrect += 1;
    }

    /**
     * This method is used to increment the player's total correct answers in the round by 1
     */
    public void incrementRoundCorrect() {
        this.roundCorrect += 1;
    }


    /**
     * This method is used to increment the player's total game points by the given points.
     * @param points    Points to increment player's total game points
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * This method is used to remove the given points from the player's total game points.
     * @param points    Points to remove from the player's total game points
     */
    public void removePoints(int points) {
        this.points -= points;
    }

    /**
     * This method is used to return the player's current answer.
     * @return  int Player's current answer
     */
    public int getCurrentAnswer() {
        return this.currentAnswer;
    }

    /**
     * This method is used to set the player's current answer.
     * @param currentAnswer Set player's current answer
     */
    public void setCurrentAnswer(int currentAnswer) {
        // currentAnswer-1 because we are given an input value, and actually convert it to an array index value
        this.currentAnswer = currentAnswer - 1;
        this.canAnswer = false;
    }

    /**
     * This method is used to return if the player can give an answer at this round.
     * @return  boolean True if player can give an answer, false otherwise
     */
    public boolean getCanAnswer() {
        return this.canAnswer;
    }

    /**
     * This method is used to enable/disable the ability for the player to give an answer.
     * @param canAnswer True/false
     */
    public void setCanAnswer(boolean canAnswer) {
        this.canAnswer = canAnswer;
    }

    /**
     * This method is used to re-initialize the current answer to none.
     * Should be used when we're done processing the player's answer and are moving to a question.
     */
    public void resetCurrentAnswer() {
        this.currentAnswer = -1;
        this.canAnswer = true;
    }

    /**
     * This method is used to reset the total correct answers in the round.
     * Should be used when we're done processing a round and are moving to a new round.
     */
    public void resetRoundCorrect() {
        this.roundCorrect = 0;
    }

}

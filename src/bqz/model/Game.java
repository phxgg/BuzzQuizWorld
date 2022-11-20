package bqz.model;

import bqz.vault.QuestionVault;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Game class is where all the game data is saved.
 *
 * @author      phxgg, NikosZoros3533
 * @version     1.0
 * @since       12/11/2020
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Number of players that are gonna play the game.
     */
    private final int playerCount;

    /**
     * Total game rounds.
     */
    private final int rounds;

    /**
     * Current game round.
     */
    private int currentRound;

    /**
     * The number of questions that are going to be shown each round.
     */
    private final int questionsPerRound;

    /**
     * Variable to check whether the game has started or not.
     */
    private boolean gameFinished;

    /**
     * Array of players currently playing the game.
     */
    private final Player[] players;

    /**
     * Question vault.
     */
    private QuestionVault qv;

    /**
     * Total questions of this game.
     */
    private int totalQuestions;

    public Game(Player[] players, int rounds, int questionsPerRound, String questionsJsonFilePath) {
        this.rounds = rounds;
        this.currentRound = 1; // default starting round is 1
        this.questionsPerRound = questionsPerRound;
        this.totalQuestions = rounds * questionsPerRound;
        this.gameFinished = false;
        this.players = players;
        this.playerCount = players.length;

        // Parse questions from json file
        this.qv = new QuestionVault(questionsJsonFilePath);
    }

    /**
     * This method is used to return the game's total number of rounds.
     * @return  int Game's number of rounds
     */
    public int getRounds() {
        return this.rounds;
    }

    /**
     * This method is used to return the game's current round number.
     * @return  int Game's current round
     */
    public int getCurrentRound() {
        return this.currentRound;
    }

    /**
     * This method is used to return the questions that are going to be shown per round.
     * @return  int Questions per round
     */
    public int getQuestionsPerRound() {
        return this.questionsPerRound;
    }

    /**
     * This method is used to return the players array.
     * @return  Player[]    Players array
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * This method is used to return the number of players currently playing the game.
     * @return  int Number of players
     * @deprecated
     */
    @Deprecated
    public int getPlayerCount() {
        return this.playerCount; // this.players.length;
    }

    /**
     * This method is used to return whether the game has started or not.
     * @return  boolean Whether game has started
     */
    public boolean hasGameFinished() {
        return this.gameFinished;
    }

    /**
     * This method is used to set whether the game has finished or not
     * @param gameFinished Whether the game has finished
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * This method is used to return the question vault
     * @return  QuestionVault   The question vault
     */
    public QuestionVault getQv() {
        return this.qv;
    }

    /**
     * This method is used to return the total questions
     * @return  int Total questions
     */
    public int getTotalQuestions() {
        return this.totalQuestions;
    }

    /**
     * This method is used to increment the current round by 1.
     */
    public void incrementCurrentRound() {
        this.currentRound += 1;
    }

    /**
     * This method is used to return the winner(s).
     * In case of a draw, multiple winners are returned in an array.
     * Otherwise, only one winner exists in the array.
     * @return  ArrayList   Game winner(s)
     */

    public ArrayList<Player> getWinners() {
        if (this.gameFinished && this.players != null) {

            /*
            Initialize highestPoints and set its value to a player's points
            Otherwise there would be a bug where the following would happen:
            If all players had <=0 points, then we would get an empty array.
            This would result in a IndexOutOfBoundsException.
             */

            int highestPoints = this.players[0].getPoints();

            // get highest points
            for (Player p : this.players) {
                if (p.getPoints() > highestPoints) {
                    highestPoints = p.getPoints();
                }
            }

            ArrayList<Player> winners = new ArrayList<>();

            for (Player p : this.players) {
                if (p.getPoints() == highestPoints) {
                    winners.add(p);
                }
            }

            return winners;
        } else {
            return null;
        }
    }
}

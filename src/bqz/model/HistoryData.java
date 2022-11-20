package bqz.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * The HistoryData class is used as a model to insert data into,
 * so they can be probably displayed in the History form.
 *
 * @author  NikosZoros3533
 * @version 2.0
 * @since   12/01/2021
 */
public class HistoryData {

    private SimpleStringProperty player1;
    private SimpleStringProperty points1;
    private SimpleStringProperty player2;
    private SimpleStringProperty points2;
    private SimpleStringProperty winner;

    public HistoryData(String player1, String points1, String player2, String points2, String winner) {
        this.player1 = new SimpleStringProperty(player1);
        this.points1 = new SimpleStringProperty(points1);
        this.player2 = new SimpleStringProperty(player2);
        this.points2 = new SimpleStringProperty(points2);
        this.winner = new SimpleStringProperty(winner);
    }

    // Comment getters & setters?
    public String getPlayer1() {
        return this.player1.get();
    }

    public void setPlayer1(String player1) {
        this.player1 = new SimpleStringProperty(player1);
    }

    public String getPoints1() {
        return this.points1.get();
    }

    public void setPoints1(String points1) {
        this.points1 = new SimpleStringProperty(points1);
    }

    public String getPlayer2() {
        return this.player2.get();
    }

    public void setPlayer2(String player2) {
        this.player2 = new SimpleStringProperty(player2);
    }

    public String getPoints2() {
        return this.points2.get();
    }

    public void setPoints2(String points2) {
        this.points2= new SimpleStringProperty(points2);
    }

    public String getWinner() {
        return this.winner.get();
    }

    public void setWinner(String winner) {
        this.winner = new SimpleStringProperty(winner);
    }

}
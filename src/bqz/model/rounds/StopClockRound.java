package bqz.model.rounds;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;
import javafx.application.Platform;
import javafx.scene.control.Label;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * The StopClockRound class is the class in which the Stop Clock round is implemented.
 *
 * @author      phxgg, NikosZoros3533
 * @version     2.0
 * @since       07/12/2020
 */
public class StopClockRound extends RoundHandler {
    private HashMap<Player, Timer> timers;
    private HashMap<Player, Integer> counts;

    public StopClockRound(Game game) {
        super(game);
        this.counts = new HashMap<>();
        this.timers = new HashMap<>();
    }

    /**
     * This method is used to return the time left for each player in a HashMap.
     * @return  HashMap<Player, Integer> Players' time left
     */
    public HashMap<Player, Integer> getCounts() {
        return this.counts;
    }

    /**
     * This method is used to return the given player's timer.
     * @param p Player to get the timer from
     * @return  Timer   The given's player timer
     */
    public Timer getTimer(Player p) {
        return this.timers.get(p);
    }

    /**
     * This method is used to start the timer for the given player.
     * @param p Player to start their timer
     * @param lb GUI label corresponding to player's time left
     */
    public void startTimer(Player p, Label lb) {
        this.counts.put(p, 5000);

        Timer timer = new Timer(100, new ActionListener() {
            //private int count = 5000;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counts.get(p) <= 0) {
                    ((Timer) e.getSource()).stop();
                } else {
                    counts.put(p, counts.get(p) - 100);
                }
                if (lb != null)
                    Platform.runLater(() -> lb.setText(Integer.toString(counts.get(p))));
            }

        });
        this.timers.put(p, timer);
        this.timers.get(p).start();
    }

    @Override
    public boolean evaluateQuestion(Player p, Question q) {
        if (p.getCurrentAnswer() == q.getCorrectAnswerIndex()) {
            p.incrementTotalCorrect();
            p.incrementRoundCorrect();
            p.addPoints((int)(this.counts.get(p) * 0.2));

            return true;
        } else {
            p.removePoints(100);

            return false;
        }
    }
}

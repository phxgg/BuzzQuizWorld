package bqz.gui.controllers;

import bqz.model.Game;
import bqz.model.HistoryData;
import bqz.model.Player;
import bqz.utility.Helper;
import bqz.utility.Logs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class HistoryController {
    @FXML public Label lbName;
    @FXML public Label lbScore;
    @FXML public TableView<HistoryData> tbScores;
    @FXML public TableColumn<HistoryData, String> tcPlayer1;
    @FXML public TableColumn<HistoryData, String> tcPoints1;
    @FXML public TableColumn<HistoryData, String> tcPlayer2;
    @FXML public TableColumn<HistoryData, String> tcPoints2;
    @FXML public TableColumn<HistoryData, String> tcWinner;

    @FXML public void initialize() {
        ObservableList<HistoryData> historyData = FXCollections.observableArrayList();

        // Load saved multi games
        String multiPath = Paths.get(Helper.jarPath(), "Logs", "Multi").toAbsolutePath().toString();
        File multiDir = new File(multiPath);

        if (multiDir.listFiles() != null) {
            for (final File logEntry : Objects.requireNonNull(multiDir.listFiles())) {
                try {
                    Game g = (Game) Logs.load(logEntry.getPath());
                    assert g != null;
                    Player p1 = g.getPlayers()[0];
                    Player p2 = g.getPlayers()[1];

                    String winner;
                    if (p1.getPoints() > p2.getPoints()) {
                        winner = p1.getName();
                    } else if (p1.getPoints() < p2.getPoints()) {
                        winner = p2.getName();
                    } else {
                        winner = "DRAW";
                    }

                    historyData.add(
                            new HistoryData(p1.getName(), String.valueOf(p1.getPoints()), p2.getName(), String.valueOf(p2.getPoints()), winner)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        tcPlayer1.setCellValueFactory(new PropertyValueFactory<>("player1"));
        tcPoints1.setCellValueFactory(new PropertyValueFactory<>("points1"));
        tcPlayer2.setCellValueFactory(new PropertyValueFactory<>("player2"));
        tcPoints2.setCellValueFactory(new PropertyValueFactory<>("points2"));
        tcWinner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        tbScores.setItems(historyData);

        // Solo score
        String soloPath = Paths.get(Helper.jarPath(), "Logs", "Solo", "solo.dat").toAbsolutePath().toString();
        File solo = new File(soloPath);
        if (!solo.exists()) {
            lbName.setText("Unknown");
            lbScore.setText("No high score yet");
        } else {
            Game g = (Game) Logs.load(soloPath);

            assert g != null;
            lbName.setText(g.getPlayers()[0].getName());
            lbScore.setText(String.valueOf(g.getPlayers()[0].getPoints()));
        }
    }
}

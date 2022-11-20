package bqz.gui.controllers;

import bqz.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    private int maxPlayers = 2;

    @FXML public TextField tbPlayerCount;
    @FXML public MenuItem miNewGame;
    @FXML public MenuItem miQuit;
    @FXML public MenuItem miAbout;
    @FXML public MenuItem miViewHistory;
    @FXML public Button btnNext;

    /**
     * Initialize a player (expects player name input)
     * @param playerNumber  Only used for the GUI displaying
     * @return  Player  New player
     */
    private Player initializePlayer(int playerNumber) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Initialization");
        dialog.setHeaderText("Player " + playerNumber + " Name");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty()) {
                return initializePlayer(playerNumber);
            } else {
                return new Player(result.get());
            }
        } else {
            return initializePlayer(playerNumber);
        }
    }

    @FXML protected void handle_btnNext(ActionEvent event) {
        String sPlayerCount = tbPlayerCount.getText();

        // Validate integer
        if (sPlayerCount.isEmpty()
                || !sPlayerCount.matches("[0-9]*")
                || Integer.parseInt(sPlayerCount) < 1 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid amount of players.");
            alert.showAndWait();

            return;
        }

        // Check if given number is larger than maxPlayers
        if (Integer.parseInt(sPlayerCount) > this.maxPlayers) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Max players: " + this.maxPlayers);
            alert.showAndWait();

            return;
        }

        int iPlayerCount = Integer.parseInt(sPlayerCount);
        Player[] players = new Player[iPlayerCount];

        for (int i = 0; i < iPlayerCount; i++) {
            players[i] = initializePlayer(i+1);
        }

        // We finally got the players :)
        // We can now start the game by showing a new form

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bqz/gui/NewGameForm.fxml"));
            //NewGameController ngController = new NewGameController(players);
            //loader.setController(ngController);
            Parent newGame = loader.load();

            // using getController() we can set the fx:controller attribute
            NewGameController ngController = loader.<NewGameController>getController();
            ngController.setPlayers(players);

            Stage newGameStage = new Stage();
            newGameStage.setTitle("New Game");
            newGameStage.setResizable(false);
            newGameStage.setScene(new Scene(newGame)); // , 947, 665
            newGameStage.getScene().setOnKeyPressed(ngController::scene_setOnKeyPressed);
            newGameStage.setOnCloseRequest(ngController::stage_setOnCloseRequest);
            newGameStage.show();

            // this hides the main form
            // we need to find a way to re-display this when we wanna start a New Game
            btnNext.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handle_miAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Quiz! World Buzz");
        alert.setHeaderText(null);
        alert.setContentText("Project made by phxgg & Nikos Zoros for our CSD course.");
        alert.showAndWait();
    }

    @FXML protected void handle_miNewGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Start New Game");
        alert.setHeaderText(null);
        alert.setContentText("This should start a new game!");
        alert.showAndWait();
    }

    @FXML protected void handle_miQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML protected void handle_miViewHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bqz/gui/HistoryForm.fxml"));
            Parent history = loader.load();
            //HistoryController historyController = loader.<HistoryController>getController();

            Stage historyStage = new Stage();
            historyStage.setTitle("History");
            historyStage.setResizable(false);
            historyStage.setScene(new Scene(history)); // , 947, 665
            historyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

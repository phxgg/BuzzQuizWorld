package bqz.gui.controllers;

import bqz.model.Game;
import bqz.model.Player;
import bqz.model.Question;
import bqz.model.enums.RoundType;
import bqz.model.rounds.*;
import bqz.utility.Helper;
import bqz.utility.Logs;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * The NewGameController class is the GUI form controller that handles the whole gameplay process.
 *
 * @author  phxgg
 * @version 2.0
 * @since   07/12/2020
 */
public class NewGameController {
    @FXML public VBox vbMain;
    @FXML public MenuItem miNewGame;
    @FXML public MenuItem miQuit;
    @FXML public MenuItem miAbout;
    @FXML public MenuItem miViewHistory;
    @FXML public Label lbRoundType;
    @FXML public Label lbRoundNumber;
    @FXML public Label lbQuestionTitle;
    @FXML public Label lbCategory;
    @FXML public Label lbAnswersDescription;
    @FXML public Label lbAnswers;
    @FXML public Label lbPlayer1;
    @FXML public Label lbPlayer2;
    @FXML public Label lbPlayer1Points;
    @FXML public Label lbPlayer2Points;
    @FXML public Label lbPoints1;
    @FXML public Label lbPoints2;
    @FXML public Label lbTimer1;
    @FXML public Label lbTimer2;
    @FXML public ImageView imgPicture;
    @FXML public Button btnStartGame;

    private Question currentQuestion; // The currentQuestion variable is used in scene_setOnKeyPressed
    private RoundHandler roundHandler;
    private Player[] players;
    private volatile boolean enableAnswers = false; // This is used to let scene_setOnKeyPressed know when it should listen for keyboard presses.
    private Thread gameThread;

    private int answersGiven = 0; // when this is equal to the length of players, it means that all players have given their answers.
    private int betsGiven = 0; // same as above for bets

    @FXML public void initialize() {
        lbRoundType.setVisible(false);
        lbRoundNumber.setVisible(false);
        lbCategory.setVisible(false);
        lbQuestionTitle.setVisible(false);
        lbAnswersDescription.setVisible(false);
        lbAnswers.setVisible(false);
        lbTimer1.setVisible(false);
        lbTimer2.setVisible(false);
        btnStartGame.setVisible(true);

        Platform.runLater(() -> {
            lbPlayer1.setText(this.players[0].getName());

            if (this.players.length > 1) {
                lbPlayer2.setText(this.players[1].getName());
            } else {
                lbPlayer2.setVisible(false);
                lbPlayer2Points.setVisible(false);
                lbPoints2.setVisible(false);
            }

            lbPlayer1Points.setText("0");
            lbPlayer2Points.setText("0");
        });

        // Red = #C83030
        // Green = #24A555
        lbPlayer1.setTextFill(Color.web("#C83030"));
        lbPlayer2.setTextFill(Color.web("#C83030"));
    }

    /**
     * This method is used get the player's bet using a dialog.
     * @param playerName    Name of the player as string
     * @return  Integer Player's bet
     */
    private Integer inputBet(String playerName) {
        ArrayList<Integer> availableBets = new ArrayList<>();
        availableBets.add(250);
        availableBets.add(500);
        availableBets.add(750);
        availableBets.add(1000);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(250, availableBets);
        dialog.setTitle("Bet");
        dialog.setHeaderText(playerName + " placing bet...");
        dialog.setContentText("Bet:");

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.betsGiven++;
            checkMaxBets();
            return result.get();
        } else {
            return inputBet(playerName);
        }
    }

    /**
     * This function is used to check when every player has given an answer,
     * so we can move on to the next question.
     */
    private void checkMaxBets() {
        if (this.betsGiven == this.players.length) {
            this.betsGiven = 0;
            this.lbQuestionTitle.setVisible(true);
            this.imgPicture.setVisible(true);

            // All players have given their bets. Show question title (and image if exists).
        }
    }

    /**
     * This method is used to pass the initialized players from another form.
     * After the players are set, the game will be started.
     * @param players The players that were initialized in MainForm.
     */
    protected void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Start the actual game.
     * We're using a thread to handle the game so we can use its sleep() method to wait for player input.
     * After player input has been given, then we can continue the game logic.
     */
    protected void startGame() {
        this.gameThread = new Thread(() -> {
            //String questionsCsvFilePath = "/Users/stam/Dropbox/buzzquizworld-3271-3533/src/bqz/vault/questions.csv"; // macbook testing
            //String questionsCsvFilePath = "C:\\Users\\stamatis\\IdeaProjects\\buzzquizworld-3271-3533\\src\\bqz\\vault\\questions.csv";
            
            String questionsCsvFilePath = Paths.get(Helper.jarPath(), "questions.csv").toAbsolutePath().toString();
            Game game = new Game(players, 2, 2, questionsCsvFilePath);

            /*
            Randomly generate RoundType.
            For each RoundType, display questions and function based on that RoundType.
            All the questions in the round are going to have the same RoundType.
             */

            // Check if question vault is empty
            if (!game.getQv().getAllQuestions().isEmpty()) {
                // Round loop
                for (int i = 1; i <= game.getRounds(); i++, game.incrementCurrentRound()) { // starting from round 1
                    // Get random Round Type
                    RoundType roundType;
                    roundType = (this.players.length > 1) ? RoundType.getRandomType(0) : RoundType.getRandomType(2);

                    // Making use of Platform.runLater() because we cannot directly call methods from another thread.
                    int finalI = i;
                    Platform.runLater(() -> {
                        lbRoundType.setText("Round Type: " + roundType);
                        lbRoundNumber.setText("Round no. " + finalI);
                    });

                    switch(roundType) {
                        case StopClock:
                            roundHandler = new StopClockRound(game);
                            break;
                        case QuickAnswer:
                            roundHandler = new QuickAnswerRound(game);
                            break;
                        case Bet:
                            roundHandler = new BetRound(game);
                            break;
                        case Thermometer:
                            roundHandler = new ThermometerRound(game);
                            break;
                        default:
                            roundHandler = new CorrectAnswerRound(game);
                            break;
                    }

                    // Questions loop

                    // If round is ThermometerRound, we have be asking questions till a player correctly answers 5 of those.
                    // End the current round, if 5 questions have been asked.
                    // Otherwise, just ask the questions one by one.

                    int questionsToAsk = (roundHandler instanceof ThermometerRound) ? game.getQv().getAllQuestions().size() : game.getQuestionsPerRound();

                    for (int j = 0; j < questionsToAsk; j++) {

                        // Check whether the round should end, if round is type of ThermometerRound
                        if (roundHandler instanceof ThermometerRound)
                            if (((ThermometerRound) roundHandler).hasCompletedFiveQuestions())
                                break;

                        Question q = game.getQv().getRandomQuestion();

                        // Set question image if exists
                        if (q.hasPicture()) {
                            File picFile = new File(Paths.get(Helper.jarPath(), "img", q.getPicture()).toAbsolutePath().toString());
                            Image img = new Image(picFile.toURI().toString());
                            this.imgPicture.setImage(img);
                            this.imgPicture.setVisible(true);
                        } else {
                            this.imgPicture.setImage(null);
                            this.imgPicture.setVisible(false);
                        }

                        Platform.runLater(() -> lbCategory.setText("Category: " + q.getCategory().toString()));

                        // Display question depending on the type of round
                        if (roundHandler instanceof BetRound) {
                            this.lbQuestionTitle.setVisible(false);
                            this.imgPicture.setVisible(false);

                            for (Player p : game.getPlayers()) {
                                Helper.runAndWait(() -> ((BetRound) roundHandler).getBets().put(p, inputBet(p.getName())));
                            }
                        }

                        int finalJ = j;
                        Platform.runLater(() -> lbQuestionTitle.setText((finalJ +1) + ". " + q.getQuestion()));

                        // Display possible answers
                        String answers = "";
                        for (int k = 0; k < q.getAnswers().size(); k++) {
                            answers += (k+1) + ". " + q.getAnswers().get(k) + "\n";
                        }

                        String finalAnswers = answers;
                        Platform.runLater(() -> lbAnswers.setText(finalAnswers));

                        // After everything has been initialized, enable answers.
                        currentQuestion = q;
                        enableAnswers = true;

                        // Start timers if the round is type of StopClockRound
                        if (roundHandler instanceof StopClockRound) {
                            Platform.runLater(() -> {
                                this.lbTimer1.setVisible(true);
                                this.lbTimer2.setVisible(true);
                                ((StopClockRound) roundHandler).startTimer(this.players[0], this.lbTimer1);
                                ((StopClockRound) roundHandler).startTimer(this.players[1], this.lbTimer2);
                            });
                        } else {
                            Platform.runLater(() -> {
                                this.lbTimer1.setVisible(false);
                                this.lbTimer2.setVisible(false);
                            });
                        }

                        // Wait for players' answers here.
                        // Players' answers will be handled by the scene_setOnKeyPressed method.
                        while (enableAnswers) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // Question passed successfully

                        // Display answer result depending on the type of round
                        for (Player p : game.getPlayers()) {
                            // not sure why I used try catch in this but won't bother touching the code :P
                            // might have been for debugging :}
                            try {
                                Platform.runLater(() -> updatePoints(p, p.getPoints()));

                                // Reset for next question
                                p.resetCurrentAnswer();

                                lbPlayer1.setTextFill(Color.web("#C83030"));
                                lbPlayer2.setTextFill(Color.web("#C83030"));
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }

                        // reset answersGiven for next question
                        this.answersGiven = 0;

                        // Reset player who gave the first answer if round is type of QuickAnswerRound
                        if (roundHandler instanceof QuickAnswerRound) {
                            ((QuickAnswerRound) roundHandler).resetFirst();
                        }
                    }

                    // Round has finished
                    Helper.runAndWait(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Round finished!");
                        alert.setHeaderText("Finished round " + game.getCurrentRound() + "!");
                        alert.setContentText("Stats below!");

                        String txt = "";

                        // Display round results depending on type of round
                        if (roundHandler instanceof ThermometerRound) {
                            Player roundWinPlayer = null;
                            for (Map.Entry<Player, Integer> entry : ((ThermometerRound) roundHandler).getCorrectAnswers().entrySet()) {
                                if (entry.getValue() == 5) {
                                    roundWinPlayer = entry.getKey();
                                    break;
                                }
                            }

                            assert roundWinPlayer != null;
                            txt += roundWinPlayer.getName() + " won the Thermometer round by being first to correctly "
                                    + "answer 5 questions first";

                            for (Player p : game.getPlayers()) {
                                p.resetRoundCorrect();
                            }
                        } else {
                            for (Player p : game.getPlayers()) {
                                txt += p.getName() + " got " + p.getRoundCorrect() + "/" + game.getQuestionsPerRound() + " questions correct!\n";
                                p.resetRoundCorrect();
                            }
                        }

                        TextArea textArea = new TextArea(txt);
                        textArea.setEditable(false);
                        textArea.setWrapText(true);

                        textArea.setMaxWidth(Double.MAX_VALUE);
                        textArea.setMaxHeight(Double.MAX_VALUE);
                        GridPane.setVgrow(textArea, Priority.ALWAYS);
                        GridPane.setHgrow(textArea, Priority.ALWAYS);

                        GridPane expContent = new GridPane();
                        expContent.setMaxWidth(Double.MAX_VALUE);
                        expContent.add(textArea, 0, 0);

                        alert.getDialogPane().setExpandableContent(expContent);

                        alert.showAndWait();
                    });

                    // Reset player who gave the first answer
                    if (roundHandler instanceof QuickAnswerRound) {
                        ((QuickAnswerRound) roundHandler).resetFirst();
                    }
                }

                // Game has finished
                game.setGameFinished(true);

                // Save game in logs
                Logs.save(game);

                // Display stats
                Helper.runAndWait(() -> {
                    String txt = "";

                    ArrayList<Player> winners = game.getWinners();

                    // Check if we got a draw
                    if (winners.size() > 1) {
                        txt += "We got a DRAW of " + winners.get(0).getPoints() + " points!\n";
                        for (Player w : winners) {
                            txt += w.getName() + " / ";
                        }

                    } else {
                        txt += "The winner is " + winners.get(0).getName() + "!";
                    }

                    txt += "\n\n*Final stats*\n\n";

                    for (Player p : game.getPlayers()) {
                        txt += p.getName() + " got " + p.getTotalCorrect() + "/" + game.getTotalQuestions() + " questions correct"
                                + "and has " + p.getPoints() + " points!\n";

                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game finished!");
                    alert.setHeaderText("The game has finished!");
                    alert.setContentText("Stats below!");

                    TextArea textArea = new TextArea(txt);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);

                    textArea.setMaxWidth(Double.MAX_VALUE);
                    textArea.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setVgrow(textArea, Priority.ALWAYS);
                    GridPane.setHgrow(textArea, Priority.ALWAYS);

                    GridPane expContent = new GridPane();
                    expContent.setMaxWidth(Double.MAX_VALUE);
                    expContent.add(textArea, 0, 0);

                    alert.getDialogPane().setExpandableContent(expContent);

                    alert.showAndWait();
                });

                Platform.runLater(() -> this.miNewGame.fire());
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText(null);
                    alert.setContentText("No questions to display. Exiting.");
                    alert.showAndWait();
                    System.exit(0);
                });
            }
        });

        gameThread.start();
    }

    /**
     * This function is used to check when every player has given an answer,
     * so we can move on to the next question.
     */
    private void checkMaxAnswers() {
        if (this.answersGiven == this.players.length) {
            enableAnswers = false;

            // All players have answered. Move on...
        }
    }

    /**
     * This function is used to update the points label of a player in the GUI.
     * @param player    The player whose points will be updated
     * @param points    Points to update to
     */
    private void updatePoints(Player player, int points) {
        Label lb = null;

        if (player == this.players[0]) // Player 1
            lb = this.lbPlayer1Points;
        else if(player == this.players[1]) // Player 2
            lb = this.lbPlayer2Points;

        assert lb != null;
        lb.setText(String.valueOf(points));
    }

    /**
     * This event handler is used to get the players' answers.
     * Player 1 will be using DIGITS 1-4 to give an answer.
     * Player 2 will be using A,S,D,F to give an answer.
     * @param event KeyEvent
     */
    @FXML protected void scene_setOnKeyPressed(KeyEvent event) {
        if (this.enableAnswers) {
            int ans = 0;

            switch (event.getCode()) {
                case DIGIT1:
                case A:
                    ans = 1;
                    break;
                case DIGIT2:
                case S:
                    ans = 2;
                    break;
                case DIGIT3:
                case D:
                    ans = 3;
                    break;
                case DIGIT4:
                case F:
                    ans = 4;
                    break;
            }

            // Player 1
            if (this.players[0].getCanAnswer()) {
                if (event.getCode() == KeyCode.DIGIT1
                || event.getCode() == KeyCode.DIGIT2
                || event.getCode() == KeyCode.DIGIT3
                || event.getCode() == KeyCode.DIGIT4) {
                    if (roundHandler instanceof StopClockRound) {
                        if (((StopClockRound) roundHandler).getCounts().get(this.players[0]) <= 0)
                            ans = -1; // set to wrong answer if the timer is 0

                        ((StopClockRound) roundHandler).getTimer(this.players[0]).stop();
                    }

                    this.players[0].setCurrentAnswer(ans);
                    this.answersGiven++; // Update given answers

                    // If the round is type of ThermometerRound check for correct answer
                    // If the answer is correct, instantly go to the next question.
                    // We go to the next question by setting this.answersGiven = this.players.length
                    if (roundHandler instanceof ThermometerRound) {
                        if (roundHandler.evaluateQuestion(this.players[0], currentQuestion)) {
                            this.answersGiven = this.players.length;
                        }
                    } else {
                        roundHandler.evaluateQuestion(this.players[0], currentQuestion);
                    }

                    lbPlayer1.setTextFill(Color.web("#24A555"));
                    checkMaxAnswers();
                }
            }

            // If we got more than 1 players
            if (this.players.length > 1) {
                // Player 2
                if (this.players[1].getCanAnswer()) {
                    if (event.getCode() == KeyCode.A
                    || event.getCode() == KeyCode.S
                    || event.getCode() == KeyCode.D
                    || event.getCode() == KeyCode.F) {
                        if (roundHandler instanceof StopClockRound) {
                            if (((StopClockRound) roundHandler).getCounts().get(this.players[1]) <= 0)
                                ans = -1;

                            ((StopClockRound) roundHandler).getTimer(this.players[1]).stop();
                        }

                        this.players[1].setCurrentAnswer(ans);
                        this.answersGiven++;

                        if (roundHandler instanceof ThermometerRound) {
                            if (roundHandler.evaluateQuestion(this.players[1], currentQuestion)) {
                                this.answersGiven = this.players.length;
                            }
                        } else {
                            roundHandler.evaluateQuestion(this.players[1], currentQuestion);
                        }

                        lbPlayer2.setTextFill(Color.web("#24A555"));
                        checkMaxAnswers();
                    }
                }
            }
        }
    }

    @FXML protected void stage_setOnCloseRequest(WindowEvent event) {
        // Platform.exit(); will not shutdown the application if a thread has started.
        // Instead, System.exit(0); will terminate the Java Virtual Machine (threads included)

        //Platform.exit();
        System.exit(0);
    }

    @FXML protected void handle_btnStartGame(ActionEvent event) {
        lbRoundType.setVisible(true);
        lbRoundNumber.setVisible(true);
        lbCategory.setVisible(true);
        lbQuestionTitle.setVisible(true);
        lbAnswersDescription.setVisible(true);
        lbAnswers.setVisible(true);
        btnStartGame.setVisible(false);

        startGame();
    }

    @FXML protected void handle_miAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Quiz! World Buzz");
        alert.setHeaderText(null);
        alert.setContentText("Project made by phxgg & Nikos Zoros for our CSD course.");
        alert.showAndWait();
    }

    @FXML protected void handle_miNewGame(ActionEvent event) {
        try {
            if (gameThread.isAlive()) {
                gameThread.stop();
            }
        } catch (NullPointerException e) {
            // ignore exception
        }

        Stage stage = (Stage) this.btnStartGame.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/bqz/gui/MainForm.fxml"));
            primaryStage.setTitle("Buzz! Quiz World");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root)); // , 711, 563
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handle_miQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML protected void handle_miViewHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bqz/gui/HistoryForm.fxml"));
            Parent history = loader.load();

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

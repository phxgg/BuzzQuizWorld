import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class
 *
 * @author      phxgg, NikosZoros3533
 * @version     2.0
 * @since       12/11/2020
 */
public class Main extends Application {
    // TODO: JUnit tests

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/bqz/gui/MainForm.fxml"));
        primaryStage.setTitle("Buzz! Quiz World");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root)); // , 711, 563
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

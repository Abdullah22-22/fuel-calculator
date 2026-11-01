import i18n.Messages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/main-view.fxml"),
                Messages.getBundle());

        Parent root = loader.load();

        stage.setTitle(Messages.get("app.title"));
        stage.setScene(new Scene(root, 620, 420));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
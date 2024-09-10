package Client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BasicWindowAction basicWindowActions=new BasicWindowAction();
        basicWindowActions.OpenWindow("HelloWindow.fxml", 600,400);
    }
    public static void main(String[] args) {
        launch();
    }
}

package Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BasicWindowAction {
    private static Stage stage = new Stage();
    public void OpenWindow(String windowName, int width, int height)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowName));
            Parent root1 = fxmlLoader.load();
            stage.setScene(new Scene(root1, width, height));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CloseWindow()
    {
        stage.close();
        stage = new Stage();
    }
}

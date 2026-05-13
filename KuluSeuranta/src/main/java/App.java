import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/TapahtumatView.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Kuluseuranta");
        stage.setScene(scene);
        stage.show();
    }
}
package ldg.progettoispw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainProgetto extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/iconApp.jpeg")));
        stage.setTitle("TutorOnline");
        stage.setResizable(false);
        stage.show();
    }
    public void run(){ launch();}
}

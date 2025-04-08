package ldg.progettoispw.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ldg.progettoispw.exception.ViewException;

import java.io.IOException;

public class FirstPageGCon {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //questo metodo avvia la schermata del LoginPage dopo aver ricevuto l'evento del click sul tasto login
    @FXML
    void startlogin(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("/ldg/progettoispw/LoginPage.fxml"));
        } catch (IOException e) {
            throw new ViewException("Errore nel caricamento della view LoginPage.fxml", e);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //questo metodo avvia la schermata del RegisterPage dopo aver ricevuto l'evento del click sul tasto register
    @FXML
    void startRegister(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("/ldg/progettoispw/RegisterPage.fxml"));
        } catch (IOException e) {
            throw new ViewException("Errore nel caricamento della view RegisterPage.fxml", e);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

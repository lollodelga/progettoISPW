package ldg.progettoispw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ldg.progettoispw.controller.LoginController;
import ldg.progettoispw.util.GController;

import java.io.IOException;

public class LoginGCon implements GController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    LoginController controller;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private Label warningLabel;
    @FXML
    private Rectangle warningRectangle;

    @FXML
    public void initialize() {
        warningLabel.setVisible(false);
        warningRectangle.setVisible(false);
    }

    @FXML
    void BACKaction(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/ldg/progettoispw/FirstPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Login(ActionEvent event) {
        String INemail = email.getText();
        String INpassword = password.getText();
        controller = new LoginController(INemail, INpassword, this, event);
        controller.start();
    }

    @Override
    public void changeView(int result, ActionEvent event) {
        switch(result){
            case 0:
                try {
                    root = FXMLLoader.load(getClass().getResource("/ldg/progettoispw/HomePage.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case 1:
                warningLabel.setText("ERRORE: Password errata.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 2:
                warningLabel.setText("ERRORE: L'utente non esiste.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 3:
                warningLabel.setText("ERRORE: Riempi tutti i campi.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 4:
                warningLabel.setText("ERRORE: Email non valida.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
        }
    }
}

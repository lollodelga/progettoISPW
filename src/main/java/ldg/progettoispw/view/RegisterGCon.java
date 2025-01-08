package ldg.progettoispw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ldg.progettoispw.controller.RegisterController;
import ldg.progettoispw.util.GController;

import java.io.IOException;

public class RegisterGCon implements GController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField Cognome;
    @FXML
    private TextField Email;
    @FXML
    private TextField Nascita;
    @FXML
    private TextField Nome;
    @FXML
    private TextField materie;
    @FXML
    private TextField password;
    @FXML
    private ToggleGroup roleGroup;
    @FXML
    private RadioButton studenteButton;
    @FXML
    private RadioButton tutorButton;
    @FXML
    private Label warningLabel;
    @FXML
    private Rectangle warningRectangle;
    @FXML
    private Text textMateria;

    @FXML
    public void initialize() {
        warningLabel.setVisible(false);
        warningRectangle.setVisible(false);
        textMateria.setVisible(false);
        materie.setVisible(false);
    }

    @FXML
    void clickStudente(ActionEvent event) {
        textMateria.setText("Materie di studio (materia1, materia2, ...)");
        textMateria.setVisible(true);
        materie.setVisible(true);
    }

    @FXML
    void clickTutor(ActionEvent event) {
        textMateria.setText("Materie trattate (materia1, materia2, ...)");
        textMateria.setVisible(true);
        materie.setVisible(true);
    }

    @FXML
    private void BACKaction(ActionEvent event){
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
    private void Register(ActionEvent event){
        RegisterController controller = new RegisterController(this, event);
        String nomeTF = Nome.getText();
        String cognomeTF = Cognome.getText();
        String birthTF = Nascita.getText();
        String emailTF = Email.getText();
        String passwordTF = password.getText();
        String materieTF = materie.getText();
        String ruoloTG = "";
        // Controllo del bottone selezione per dare un valore al ruolo scelto
        // ruolo = 1 allora ho scelto tutor, mentre ruolo = 2 ho scelto studente
        if (roleGroup.getSelectedToggle() == tutorButton) {
            ruoloTG = "1";
        } else if (roleGroup.getSelectedToggle() == studenteButton) {
            ruoloTG = "2";
        }
        controller.Register(nomeTF, cognomeTF, birthTF, emailTF, passwordTF, materieTF, ruoloTG);
    }


    @Override
    public void changeView(int result, ActionEvent event) {
        //in base al risultato dell'applicativo dopo aver premuto il tasto di registrazione
        //avrò un risultato ben preciso, che verrà comunicato dall'applicativo e di conseguenza avrò questi casi.
        switch (result) {
            case 0:
                try {
                    root = FXMLLoader.load(getClass().getResource("/ldg/progettoispw/LoginPage.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case 1:
                warningLabel.setText("ERRORE: Email già in uso.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 2:
                warningLabel.setText("ERRORE: Riempi tutti i campi.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 3:
                warningLabel.setText("ERRORE: Email non valida.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 4:
                warningLabel.setText("ERRORE: La password non rispetta i requisiti.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            case 5:
                warningLabel.setText("ERRORE: La data non è valida.");
                warningLabel.setVisible(true);
                warningRectangle.setVisible(true);
                break;
            default:
                warningLabel.setText("ERRORE DI SISTEMA: riprovare");
        }
    }
}

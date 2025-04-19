package ldg.progettoispw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ldg.progettoispw.exception.ViewException;

import java.io.IOException;

public abstract class BaseGCon {
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected Label warningLabel;
    protected Rectangle warningRectangle;

    protected void setWarningElements(Label label, Rectangle rectangle) {
        this.warningLabel = label;
        this.warningRectangle = rectangle;
    }
    protected void switchScene(String fxmlPath, ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new ViewException("Errore nel caricamento della view: " + fxmlPath, e);
        }
    }

    protected void showWarning(String message) {
        if (warningLabel != null && warningRectangle != null) {
            warningLabel.setText(message);
            warningLabel.setVisible(true);
            warningRectangle.setVisible(true);
        }
    }
}

package ldg.progettoispw.View;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldg.progettoispw.Model.Bean.BeanManager;
import ldg.progettoispw.Model.Bean.UserBean;

public class HomePageGCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private UserBean userBean;
    private final BeanManager beanManager = BeanManager.getInstance();
    private String[] user;

    @FXML
    public void initialize() {
        userBean = beanManager.getUserBean();
        user = userBean.get();
    }
}

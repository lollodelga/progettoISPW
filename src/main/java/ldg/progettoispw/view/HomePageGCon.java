package ldg.progettoispw.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ldg.progettoispw.model.bean.BeanManager;
import ldg.progettoispw.model.bean.UserBean;

public class HomePageGCon {
    private UserBean userBean;
    private final BeanManager beanManager = BeanManager.getInstance();
    private String[] user;

    @FXML
    public void initialize() {
        userBean = beanManager.getUserBean();
        user = userBean.get();
    }
}

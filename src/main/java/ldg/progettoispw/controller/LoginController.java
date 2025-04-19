package ldg.progettoispw.controller;

import javafx.event.ActionEvent;
import ldg.progettoispw.model.bean.UserBean;
import ldg.progettoispw.model.applicativo.Login;
import ldg.progettoispw.util.GController;

public class LoginController {
    private final Login login;
    private final UserBean user;

    public LoginController(String email, String password, GController gController, ActionEvent event) {
        this.user = new UserBean();
        this.login = new Login();
        login.setInstance(gController, event, email, password, user);
    }

    public void start() {
        login.login();
    }
}
package ldg.progettoispw.controller;

import javafx.event.ActionEvent;
import ldg.progettoispw.model.bean.UserBean;
import ldg.progettoispw.model.Login;
import ldg.progettoispw.util.GController;

public class LoginController {
    private UserBean user = new UserBean();
    private Login login = new Login();
    private GController loginGC;
    private String email;
    private String password;
    private ActionEvent actionEvent;

    public LoginController(String emailValue, String passwordValue, GController gController, ActionEvent actionEventvalue) {
        this.email = emailValue;
        this.password = passwordValue;
        this.loginGC = gController;
        this.actionEvent = actionEventvalue;
        login.setIstance(this.loginGC, this.actionEvent, this.email, this.password, this.user);
    }

    public void start(){
        login.login();
    }
}

package ldg.progettoispw.model.applicativo;

import javafx.event.ActionEvent;
import ldg.progettoispw.exception.DBException;

import ldg.progettoispw.model.bean.UserBean;
import ldg.progettoispw.model.dao.LoginDAO;
import ldg.progettoispw.model.dao.UserDAO;
import ldg.progettoispw.util.GController;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {

    // Codici di risposta
    private static final int OK = 0;
    private static final int WRONG_PASSWORD = 1;
    private static final int USER_NOT_FOUND = 2;
    private static final int EMPTY_FIELDS = 3;
    private static final int INVALID_EMAIL = 4;
    private static final int DB_ERROR = 5;

    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private final LoginDAO loginDAO = new LoginDAO();
    private final UserDAO userDAO = new UserDAO();

    private GController controller;
    private ActionEvent event;
    private UserBean userBean;
    private String email;
    private String password;

    public void setInstance(GController controller, ActionEvent event, String email, String password, UserBean userBean) {
        this.controller = controller;
        this.event = event;
        this.email = email;
        this.password = password;
        this.userBean = userBean;
    }

    public void login() {
        int result = validateInput();

        try {
            if (result == OK) {
                int loginResult = loginDAO.start(email, password);
                switch (loginResult) {
                    case OK:
                        String[] data = userDAO.takeData(email, password);
                        data[5] = userDAO.takeSubjects(email); // aggiungi materie all'array
                        userBean.setOfAll(data); // aggiorna il bean
                        LoginSessionManager.saveUserSession(userBean);// salva la sessione
                        int viewCode = (loginDAO.getUserRole(email, password) == 1) ? 0 : 1;
                        controller.changeView(viewCode, event);
                        return;

                    case WRONG_PASSWORD:
                        result = WRONG_PASSWORD;
                        break;

                    case USER_NOT_FOUND:
                        result = USER_NOT_FOUND;
                        break;

                    default:
                        result = DB_ERROR;
                        break;
                }
            }
        } catch (DBException e) {
            logger.severe("Errore durante l'accesso al database: " + e.getMessage());
            result = DB_ERROR;
        }
        controller.changeView(result, event);
    }

    private int validateInput() {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return EMPTY_FIELDS;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return INVALID_EMAIL;
        }

        return OK;
    }
}
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
    private static final int ERROR = -1;
    private static final int OK = 0;
    private static final int WRONG_PASSWORD = 2;
    private static final int EMPTY_FIELDS = 3;
    private static final int INVALID_EMAIL = 4;
    private static final int USER_NOT_FOUND = 5;
    private static final int DB_ERROR = 6;

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
        int validationResult = validateInput();
        if (validationResult != OK) {
            return; // La view è già aggiornata da validateInput()
        }

        try {
            int loginResult = loginDAO.start(email, password);

            switch (loginResult) {
                case LoginDAO.SUCCESS:
                    // Login riuscito, recupera i dati
                    String[] data = userDAO.takeData(email, password);
                    data[5] = userDAO.takeSubjects(email);
                    userBean.setOfAll(data);
                    LoginSessionManager.saveUserSession(userBean);

                    // Ruolo: 1 = Tutor, 2 = Studente
                    int role = loginDAO.getUserRole(email, password);
                    int viewCode = (role == 1) ? 0 : 1;

                    logger.info("Login riuscito per: " + email + " con ruolo: " + (role == 1 ? "Tutor" : "Studente"));
                    controller.changeView(viewCode, event);
                    break;

                case LoginDAO.WRONG_PASSWORD:
                    logger.info("Login fallito: password errata per " + email);
                    controller.changeView(WRONG_PASSWORD, event); // codice 2: password errata
                    break;

                case LoginDAO.USER_NOT_FOUND:
                    logger.info("Login fallito: utente non trovato con email " + email);
                    controller.changeView(USER_NOT_FOUND, event); // codice 3: utente non esiste
                    break;

                default:
                    logger.severe("Login fallito: codice sconosciuto " + loginResult);
                    controller.changeView(DB_ERROR, event);
                    break;
            }

        } catch (DBException e) {
            logger.severe("Errore DB: " + e.getMessage());
            controller.changeView(DB_ERROR, event);
        }
    }



    private int validateInput() {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            controller.changeView(EMPTY_FIELDS, event);
            return ERROR;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            controller.changeView(INVALID_EMAIL, event);
            return ERROR;
        }

        return OK;
    }
}
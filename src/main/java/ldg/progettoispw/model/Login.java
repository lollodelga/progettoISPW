package ldg.progettoispw.model;

import javafx.event.ActionEvent;
import ldg.progettoispw.exception.DBException;
import ldg.progettoispw.model.bean.BeanManager;
import ldg.progettoispw.model.bean.UserBean;
import ldg.progettoispw.model.dao.LoginDAO;
import ldg.progettoispw.model.dao.UserDAO;
import ldg.progettoispw.util.GController;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    private GController loginGC;
    private LoginDAO loginDAO = new LoginDAO();
    private ActionEvent actionEvent;
    private UserBean userBean;
    private String email;
    private String password;
    private int result = 0;
    private final UserDAO userDAO = new UserDAO();
    private final BeanManager beanManager = BeanManager.getInstance();
    private static final Logger loggerLogin = Logger.getLogger(Login.class.getName());

    public void setIstance(GController loginGCvalue, ActionEvent actionEventValue, String emailValue, String passwordValue, UserBean userBeanvalue) {
        this.loginGC = loginGCvalue;
        this.actionEvent = actionEventValue;
        this.email = emailValue;
        this.password = passwordValue;
        this.userBean = userBeanvalue;
    }
    public void login(){
        try{
            checkData(email, password);
            switch(result) {
                case 0:
                    //check non trova problemi, allora vado in DAO per il login
                    switch (loginDAO.start(this.email, this.password)) {
                        case 0:
                            //esiste tale utente e la password è corretta perciò posso procedere con il
                            //set del bean e la notifica alla view
                            String[] data = userDAO.takeData(this.email, this.password);
                            data[5] = userDAO.takeSubjects(this.email);
                            this.userBean.set(data);
                            beanManager.setUserBean(this.userBean);
                            result = 0;
                            this.loginGC.changeView(result, this.actionEvent);
                            break;
                        //dopo aver fatto il set del bean informo la view, per poi fare il cambio in
                        //homepage e mandare alla home l'istanza della view
                        case 1:
                            //esiste l'utente ma la password non è corretta, notifico alla view
                            result = 1;
                            this.loginGC.changeView(result, this.actionEvent);
                            break;
                        case 2:
                            //non esiste tale utente
                            result = 2;
                            this.loginGC.changeView(result, this.actionEvent);
                            break;
                        default:
                            loggerLogin.severe("Errore! loginDAO.start() ha preso un valore non consentito");
                            break;
                    }
                    break;
                case 1:
                    //check dice che non ho riempito tutti gli spazi. notifico alla view
                    result = 3;
                    this.loginGC.changeView(result, this.actionEvent);
                    break;
                case 2:
                    //check dice che ho riempito tutti gli spazzi, ma il formato mail non è valido. NOTIFICO ALLA VIEW
                    result = 4;
                    this.loginGC.changeView(result, this.actionEvent);
                    break;
                default:
                    loggerLogin.severe("Errore! result ha preso un valore non consentito");
                    break;
            }
        } catch (DBException e) {

        }
    }

    private void checkData(String email, String password){
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // con il primo controllo vedo se ho elementi nulli
            result = 1;
        }
        if(result!=1){
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                result = 2;
            }
        }
    }

}
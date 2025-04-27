package ldg.progettoispw.controller;

import java.util.logging.Logger;

import ldg.progettoispw.view.HomeGCon;
import ldg.progettoispw.model.applicativo.LoginSessionManager;
import ldg.progettoispw.model.bean.UserBean;

public class HomePageController {
    private static final Logger logger = Logger.getLogger(HomePageController.class.getName());

    public void refreshUserData(HomeGCon view) {
        try {
            UserBean user = LoginSessionManager.loadUserSession();
            if (user != null) {
                view.updateUserData(
                        user.getName(),
                        user.getSurname(),
                        user.getBirthDate(),
                        user.getSubjects() // oppure "Tutor" o "Studente" se hai un campo ruolo separato
                );
            } else {
                logger.warning("Nessuna sessione utente trovata.");
            }
        } catch (Exception e) {
            logger.severe("Errore durante il caricamento dei dati utente: " + e.getMessage());
        }
    }
}

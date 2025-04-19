package ldg.progettoispw.model.applicativo;

import ldg.progettoispw.model.bean.UserBean;

import java.io.*;
import java.nio.file.*;
import java.util.logging.Logger;

public class LoginSessionManager {
    private static final String SESSION_FILE = "session.csv";
    private static final Logger logger = Logger.getLogger(LoginSessionManager.class.getName());

    private LoginSessionManager() {
        // Costruttore privato per evitare l'istanziazione
    }
    // Salva i dati dell'utente loggato nel file
    public static void saveUserSession(UserBean userBean) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SESSION_FILE))) {
            String[] data = userBean.getArray();
            writer.write(String.join(",", data));
        } catch (IOException e) {
            logger.severe("Errore durante il salvataggio della sessione: " + e.getMessage());
        }
    }

    // Legge i dati dell'utente loggato dal file
    public static UserBean loadUserSession() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(SESSION_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                UserBean userBean = new UserBean();
                userBean.setOfAll(data); // ho il set di tutti i dati del login
                return userBean;
            }
        } catch (IOException e) {
            logger.warning("Errore durante il caricamento della sessione: " + e.getMessage());
        }
        return null;
    }

    // Cancella il file della sessione
    public static void clearSession() {
        try {
            Files.deleteIfExists(Paths.get(SESSION_FILE));
        } catch (IOException e) {
            logger.warning("Errore durante la cancellazione della sessione: " + e.getMessage());
        }
    }
}

package ldg.progettoispw.model.dao;

import ldg.progettoispw.exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    // Singleton dell'istanza della classe
    private static ConnectionFactory instance = null;

    // Connessione singola per tutta la durata dell'app (per utente)
    private Connection conn = null;

    private ConnectionFactory() {
        // costruttore privato
    }

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public synchronized Connection getDBConnection(){
        try {
            if (conn == null || conn.isClosed()) {

                Properties properties = new Properties();
                properties.put("user", "root");
                properties.put("password", "Forzalazio1900");

                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/progetto_ispw",
                        properties
                );

                logger.info("Connessione al database stabilita.");
            }

        } catch (SQLException e) {
            logger.severe("Errore SQL durante la connessione: " + e.getMessage());
            System.exit(1);
        }
        return conn;
    }

    // Metodo per chiudere manualmente la connessione, es. alla chiusura dell'app
    public synchronized void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                logger.info("Connessione al database chiusa.");
            }
        } catch (SQLException e) {
            logger.warning("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
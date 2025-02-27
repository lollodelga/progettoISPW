package ldg.progettoispw.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static ConnectionFactory instance = null;
    private Connection conn = null;
    private static final Logger loggerConnection = Logger.getLogger(ConnectionFactory.class.getName());

    private ConnectionFactory() {
    }

    /** Singleton */
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() {
        if (this.conn == null) {
            try {
                Properties properties = new Properties();
                properties.put("user", "root");
                properties.put("password", "Forzalazio1900");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_ispw", properties);

            } catch (SQLException e) {
                loggerConnection.warning("Errore durante la connessione al database: " + e.getMessage());
            }
        }
        return this.conn;
    }
}

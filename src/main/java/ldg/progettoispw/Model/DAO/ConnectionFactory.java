package ldg.progettoispw.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance = null;
    private Connection conn = null;

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
                System.err.println("Errore durante la connessione al database: " + e.getMessage());
            }
        }
        return this.conn;
    }

    /*private static Connection connection;

    private ConnectionFactory() {}

    static {

        try {
            Properties properties = new Properties();
            properties.put("user", "root");
            properties.put("password", "Forzalazio1900");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto_ispw", properties);

        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
        }
    }

    public static Connection getDBConnection() throws SQLException {
        return connection;
    }*/
}

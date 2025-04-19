package ldg.progettoispw.model.dao;

import ldg.progettoispw.exception.DBException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

public class LoginDAO {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private static final Logger logger = Logger.getLogger(LoginDAO.class.getName());

    // Codici di risposta
    public static final int SUCCESS = 0;
    public static final int WRONG_PASSWORD = 1;
    public static final int USER_NOT_FOUND = 2;

    public int start(String email, String password) throws DBException {
        if (userExists(email)) {
            if (checkPassword(email, password)) {
                return SUCCESS;
            } else {
                return WRONG_PASSWORD;
            }
        } else {
            return USER_NOT_FOUND;
        }
    }

    private boolean userExists(String email) throws DBException {
        try (Connection conn = connectionFactory.getDBConnection();
             CallableStatement cs = conn.prepareCall("{call checkExistence(?, ?)}")) {

            cs.setString(1, email);
            cs.registerOutParameter(2, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(2);

        } catch (SQLException e) {
            throw new DBException("Errore durante la verifica dell'esistenza dell'utente", e);
        }
    }

    private boolean checkPassword(String email, String password) throws DBException {
        try (Connection conn = connectionFactory.getDBConnection();
             CallableStatement cs = conn.prepareCall("{call checkPassword(?, ?, ?)}")) {

            cs.setString(1, email);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(3);

        } catch (SQLException e) {
            throw new DBException("Errore durante la verifica della password", e);
        }
    }
}
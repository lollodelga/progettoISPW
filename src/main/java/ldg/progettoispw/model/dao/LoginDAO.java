package ldg.progettoispw.model.dao;

import ldg.progettoispw.exception.DBException;
import ldg.progettoispw.model.Login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

public class LoginDAO {
    private int result = 0;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private static final Logger loggerLoginDAO = Logger.getLogger(Login.class.getName());


    public int start(String email, String password) {
        try{
            if (userExists(email)) {
                result = 1;
                if (finalCheck(email, password)) {
                    result = 0;
                }
            } else {
                result = 2;
            }
        } catch (DBException e) {
            loggerLoginDAO.warning("Errore nel check dell'utente" + e.getMessage());
        }
        return result;
    }

    private boolean userExists(String email) throws DBException {
        boolean check;
        try(Connection conn = connectionFactory.getDBConnection();
            CallableStatement callableStatement = conn.prepareCall("{call checkExistence(?, ?)}")){
            callableStatement.setString(1, email);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.executeQuery();
            check = callableStatement.getBoolean(2);
        } catch (SQLException e) {
            throw new DBException("Errore durante la verifica dell'esistenza dell'utente");
        }
        return check;
    }

    private boolean finalCheck(String email, String password) throws DBException {
        boolean check;
        try (Connection conn = connectionFactory.getDBConnection();
             CallableStatement callableStatement = conn.prepareCall("{call checkPassword(?, ?, ?)}")){
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.executeQuery();
            check = callableStatement.getBoolean(3);
        } catch (SQLException e) {
            throw new DBException("Errore durante la verifica dell'esistenza dell'utente");
        }
        return check;
    }
}

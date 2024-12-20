package ldg.progettoispw.Model.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDAO {
    private int result = 0;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    public int start(String email, String password) {
        if(userExists(email)){
            result = 1;
            if(finalCheck(email, password)){
                result = 0;
            }
        }else{
            result = 2;
        }
        return result;
    }

    private boolean userExists(String email) {
        boolean check;
        try{
            Connection conn = connectionFactory.getDBConnection();
            CallableStatement callableStatement = conn.prepareCall("{call checkExistence(?, ?)}");
            callableStatement.setString(1, email);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);
            callableStatement.executeQuery();
            check = callableStatement.getBoolean(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    private boolean finalCheck(String email, String password){
        boolean check;
        try{
            Connection conn = connectionFactory.getDBConnection();
            CallableStatement callableStatement = conn.prepareCall("{call checkPassword(?, ?, ?)}");
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.executeQuery();
            check = callableStatement.getBoolean(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
}

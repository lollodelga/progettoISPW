package ldg.progettoispw.Model.DAO;

import java.sql.*;
import java.text.SimpleDateFormat;

public class UserDAO {
    private String[] data = new String[6];
    private String subjects;
    private final ConnectionFactory cf = ConnectionFactory.getInstance();
    public String[] takeData(String email, String password){
        try{
            Connection conn = cf.getDBConnection();
            CallableStatement cs = conn.prepareCall("{call takeData(?, ?, ?, ?, ?)}");
            cs.setString(1, email);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.DATE);
            cs.execute();
            this.data[0] = cs.getString(3);
            this.data[1] = cs.getString(4);
            Date date = cs.getDate(5);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.data[2] = dateFormat.format(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.data[3] = email;
        this.data[4] = password;
        return this.data;
    }
    public String takeSubjects(String email){
        /*devo creare un metodo, che mi permetta di prendere le subject disaccoppiate e riaccoppiarle. Processo
        * inverso da quello fatto in precedenza per la registrazione*/
        try{
            Connection conn = cf.getDBConnection();
            CallableStatement cs = conn.prepareCall("{call getSubjects(?, ?)}");
            cs.setString(1, email);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            this.subjects = cs.getString(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.subjects;
    }
}

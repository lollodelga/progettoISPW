package ldg.progettoispw.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.text.ParseException;


public class RegistrationDAO {
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private int ris = 0;
    private static final Logger loggerRegistrationDAO = Logger.getLogger(RegistrationDAO.class.getName());

    //check nel db dell'esistenza di un utiente con tale email e di conseguenza o crea un nuovo utente o ritorna error
    public int checkInDB(String[] values){
        try {
            Connection conn = connectionFactory.getDBConnection();
            CallableStatement cstmt = conn.prepareCall("{call checkEmail(?,?,?,?,?,?,?)}"); //cambiare la chiamata di tale procedura
            //poiché ho cambiato la procedure sul db e invece di fare solo il check fa direttamente anche l'insert, qesta cosa mi serve
            //per garantire la serializzabilità per non fare un check poi un altro e avere una lettura inconsistente.
            cstmt.setString(1, values[3]);
            cstmt.setString(2, values[4]);
            cstmt.setString(3, values[0]);
            cstmt.setString(4, values[1]);
            cstmt.setDate(5, convertToSQLDate(values[2]));
            cstmt.setString(6, values[6]);
            cstmt.registerOutParameter(7, Types.INTEGER);
            cstmt.execute();
            ris = cstmt.getInt(7);
            cstmt.close();
//          il valore di ritorno può essere diverso da 0 e in tal caso significa che l'utente già esisteva
            return ris;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo per convertire la stringa in una data SQL
    private java.sql.Date convertToSQLDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            loggerRegistrationDAO.warning("Errore nel parsing della data: " + e.getMessage());
            throw new IllegalArgumentException("Formato data invalido: " + dateString, e);
        }
    }

    //inserisco la materia, solo se non è già presente nek DB
    public void InsertSubject(String subject){
        try{
            Connection conn = connectionFactory.getDBConnection();
            CallableStatement cstmt = conn.prepareCall("{call insertSubject(?)}");
            cstmt.setString(1, subject);
            cstmt.execute();
            cstmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //creo l'associazione molti a molti tra users e materie
    public void CreateAssociation(String email, String subject){
        try{
            Connection conn = connectionFactory.getDBConnection();
            CallableStatement cstmt = conn.prepareCall("{call creaAssociazione(?,?)}");
            cstmt.setString(1, email);
            cstmt.setString(2, subject);
            cstmt.execute();
            cstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

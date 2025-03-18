package ldg.progettoispw.model.dao;

import ldg.progettoispw.exception.DBException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.text.ParseException;


public class RegistrationDAO {
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private static final Logger loggerRegistrationDAO = Logger.getLogger(RegistrationDAO.class.getName());
    private static final String errore_chiusura = "Errore nel chiudere le risorse: ";

    //check nel db dell'esistenza di un utiente con tale email e di conseguenza o crea un nuovo utente o ritorna error
    public int checkInDB(String[] values) throws DBException {
        CallableStatement cstmt = null;
        try {
            Connection conn = connectionFactory.getDBConnection();
            cstmt = conn.prepareCall("{call checkEmail(?,?,?,?,?,?,?)}");
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
            int ris = cstmt.getInt(7);
//          il valore di ritorno può essere diverso da 0 e in tal caso significa che l'utente già esisteva
            return ris;
        } catch (SQLException e) {
            throw new DBException("Errore durante il controllo dell'email nel DB");
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                loggerRegistrationDAO.warning(errore_chiusura + e.getMessage());
            }
        }
    }

    // Metodo per convertire la stringa in una data SQL
    private java.sql.Date convertToSQLDate(String dateString) throws DBException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            loggerRegistrationDAO.warning("Errore nel parsing della data: " + e.getMessage());
            throw new DBException("Formato data invalido: " + dateString, e);
        }
    }

    //inserisco la materia, solo se non è già presente nek DB
    public void insertSubject(String subject) throws DBException {
        CallableStatement cstmt = null;
        try{
            Connection conn = connectionFactory.getDBConnection();
            cstmt = conn.prepareCall("{call insertSubject(?)}");
            cstmt.setString(1, subject);
            cstmt.execute();
            cstmt.close();
        } catch (SQLException e) {
            throw new DBException("Errore durante l'inserimento della materia nel DB", e);
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                loggerRegistrationDAO.warning(errore_chiusura + e.getMessage());
            }
        }
    }

    //creo l'associazione molti a molti tra users e materie
    public void createAssociation(String email, String subject) throws DBException {
        CallableStatement cstmt = null;
        try {
            Connection conn = connectionFactory.getDBConnection();
            cstmt = conn.prepareCall("{call creaAssociazione(?,?)}");
            cstmt.setString(1, email);
            cstmt.setString(2, subject);
            cstmt.execute();
            cstmt.close();
        } catch (SQLException e) {
            throw new DBException("Errore durante la creazione dell'associazione tra utente e materia", e);
        } finally {
            try {
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                loggerRegistrationDAO.warning(errore_chiusura + e.getMessage());
            }
        }
    }
}

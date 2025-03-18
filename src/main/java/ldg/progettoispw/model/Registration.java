package ldg.progettoispw.model;

import javafx.event.ActionEvent;
import ldg.progettoispw.model.dao.RegistrationDAO;
import ldg.progettoispw.util.GController;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {
    private String email;
    private String password;
    private int result = 0;
    private final GController gContIstance;
    private final ActionEvent eventIstance;
    public Registration(GController gController, ActionEvent event) {
        gContIstance = gController;
        eventIstance = event;
    }

    public void start(String[] strings){
        String[] valori = strings;
        int change;
        email = strings[3];
        password = strings[4];
        firstControl(valori);
        if(result ==0){
            RegistrationDAO dao = new RegistrationDAO();
            if(dao.checkInDB(valori)==1){
                //già esiste tale user e allora va notificato alla view INVIA 1
                change = 1;
                gContIstance.changeView(change, this.eventIstance);
            } else{
                // nuovo utente aggiunto. Dividi la stringa in base alla virgola e rimuovi eventuali spazi
                // per poi chiamare n volte quanti sono le materie il metodo per creare l'associazione materia user
                String[] materie = valori[5].split(",");
                for (String materia : materie) {
                    String materiaFinale = materia.trim(); // Rimuovi spazi bianchi
                    dao.InsertSubject(materiaFinale);
                    dao.CreateAssociation(email, materiaFinale);
                }
                change = 0;
                gContIstance.changeView(change, eventIstance);
            }
        }else if(result ==1){
            //errore: compilare tutti i campi INVIA 2
            change = 2;
            gContIstance.changeView(change, eventIstance);
        }else if(result ==2){
            //errore: email errata o inesistente INVIA 3
            change = 3;
            gContIstance.changeView(change, eventIstance);
        }else if(result ==3){
            //errore: password non rispetta i requisiti INVIA 4
            change = 4;
            gContIstance.changeView(change, eventIstance);
        }else if(result ==4){
            //errore: data non valida INVIA 5
            change = 5;
            gContIstance.changeView(change, eventIstance);
        }
    }

    private void firstControl(String[] strings) {
        if (checkForNullOrEmpty(strings)) {
            result = 1; // Se c'è un valore null o vuoto
        } else if (!isValidEmail(email)) {
            result = 2; // Se l'email non è valida
        } else if (!isValidPassword(password)) {
            result = 3; // Se la password non soddisfa i requisiti
        } else if (!isValidDate(strings[2])) {
            result = 4; // Se la data non è valida
        }
    }

    // Metodo che verifica se ci sono valori nulli o vuoti
    private boolean checkForNullOrEmpty(String[] strings) {
        for (int i = 0; i < 6; i++) {
            if (strings[i] == null || strings[i].trim().isEmpty()) {
                return true; // Se c'è un elemento null o vuoto
            }
        }
        return false;
    }

    // Metodo che verifica la validità dell'email tramite regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Metodo che verifica la validità della password tramite regex
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[:!?$%&;])[A-Za-z\\d:!?$%&;]{8,16}$";
        return password.matches(passwordRegex);
    }

    // Metodo che verifica la validità della data
    private boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Rende il controllo rigoroso
        try {
            Date date = dateFormat.parse(dateString);
            return !date.after(new Date()); // Controlla che la data non sia nel futuro
        } catch (ParseException e) {
            return false; // La data non è valida
        }
    }
}
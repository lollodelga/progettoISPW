package ldg.progettoispw.Model;

import javafx.event.ActionEvent;
import ldg.progettoispw.Model.DAO.RegistrationDAO;
import ldg.progettoispw.Util.GController;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {
    private String[] valori;
    private String email, password;
    private int result = 0;
    private GController gContIstance;
    private ActionEvent eventIstance;
    private int change;
    public Registration(GController gController, ActionEvent event) {
        this.gContIstance = gController;
        this.eventIstance = event;
    }

    public void start(String[] strings){
        this.valori = strings;
        this.email = strings[3];
        this.password = strings[4];
        firstControl(valori);
        if(this.result ==0){
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
            };
        }else if(this.result ==1){
            //errore: compilare tutti i campi INVIA 2
            change = 2;
            gContIstance.changeView(change, eventIstance);
        }else if(this.result ==2){
            //errore: email errata o inesistente INVIA 3
            change = 3;
            gContIstance.changeView(change, eventIstance);
        }else if(this.result ==3){
            //errore: password non rispetta i requisiti INVIA 4
            change = 4;
            gContIstance.changeView(change, eventIstance);
        }else if(this.result ==4){
            //errore: data non valida INVIA 5
            change = 5;
            gContIstance.changeView(change, eventIstance);
        };
    }

    private void firstControl(String[] strings){
        for (int i = 0; i < 6; i++) {
            if (strings[i] == null || "".equals(strings[i].trim())) {
                // con il primo controllo vedo se ho elementi nulli
                this.result = 1;
                break; // Uscita immediata dal ciclo
            }
        }
        if(this.result!=1){
            //faccio un controllo tramite regex, che mi da lo schema dell'email.
            //il controllo più specifico dell'email lo faccio alla fine
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                this.result = 2;
            }}
        if(this.result!=1 && this.result!=2){
            //regex per i requisiti della password
            String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[:!?$%&;])[A-Za-z\\d:!?$%&;]{8,16}$";

            if (!password.matches(passwordRegex)) {
                this.result = 3;
            }
        }
        if(this.result!=1 && this.result!=2 && this.result!=3){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Rende il controllo rigoroso
            try {
                // Tenta di fare il parsing della data
                Date date = dateFormat.parse(strings[2]);
                // Controlla che la data non sia nel futuro
                if (date.after(new Date())) {
                    //la data è nel futuro
                    this.result = 4;
                }
            } catch (ParseException e) {
                //il formato della data non è valido
                this.result = 4;
            }
        }
    }
}

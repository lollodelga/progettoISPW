package ldg.progettoispw.Controller;

import javafx.event.ActionEvent;
import ldg.progettoispw.Model.Registration;
import ldg.progettoispw.Util.GController;

public class RegisterController {
    private String[] RegistValues = new String[7];
    private GController gControllerIstance;
    private ActionEvent actionEvent;

/*chiamando il costruttore non di default, passo al controller l'indirizzo del grafico, affinché possa passarlo al model,
 che avrà il compito finale di contattare la view per aggiornarla*/
    public RegisterController(GController gController, ActionEvent event) {
        this.gControllerIstance = gController;
        this.actionEvent = event;
    }

/*il controller grafico chiama il comando della registrazione e manda i dati scritti nella view. Dopo di ciò spetta al
* controller applicativo chiamare metodi del model, affinché si possano fare gli appositi controlli sui dati*/
    public void Register(String Name, String Surname, String Birth, String Email, String Password, String Subjects, String Role){
        this.RegistValues[0] = Name;
        this.RegistValues[1] = Surname;
        this.RegistValues[2] = Birth;
        this.RegistValues[3] = Email;
        this.RegistValues[4] = Password;
        this.RegistValues[5] = Subjects;
        this.RegistValues[6] = Role;
        Registration reg = new Registration(gControllerIstance, actionEvent);
        reg.start(RegistValues);
    }
}

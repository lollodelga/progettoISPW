package ldg.progettoispw.controller;

import javafx.event.ActionEvent;
import ldg.progettoispw.model.Registration;
import ldg.progettoispw.util.GController;

public class RegisterController {
    private String[] registValues = new String[7];
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
    public void register(String name, String surname, String birth, String email, String password, String subjects, String role){
        this.registValues[0] = name;
        this.registValues[1] = surname;
        this.registValues[2] = birth;
        this.registValues[3] = email;
        this.registValues[4] = password;
        this.registValues[5] = subjects;
        this.registValues[6] = role;
        Registration reg = new Registration(gControllerIstance, actionEvent);
        reg.start(registValues);
    }
}

package JUnit;

import javafx.event.ActionEvent;
import ldg.progettoispw.controller.LoginController;
import ldg.progettoispw.util.GController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    /**
     * Dummy implementation of GController to capture result of changeView.
     */
    static class DummyGController implements GController {
        int result = -1;

        @Override
        public void changeView(int result, ActionEvent event) {
            this.result = result;
        }
    }

    @Test
    void loginWithValidUser_shouldReturnSuccessOrStudentOrTutorViewCode() {
        // 🔧 Inserisci credenziali reali di test presenti nel DB
        String testEmail = "studente@gmail.com";
        String testPassword = "Password1234?";

        DummyGController dummyGController = new DummyGController();

        LoginController controller = new LoginController(
                testEmail,
                testPassword,
                dummyGController,
                null
        );

        controller.start();

        // ✅ Verifica che il risultato sia valido: 0 = tutor, 1 = studente
        assertTrue(dummyGController.result == 0 || dummyGController.result == 1,
                "Login fallita o result non atteso. Trovato: " + dummyGController.result);
    }
}


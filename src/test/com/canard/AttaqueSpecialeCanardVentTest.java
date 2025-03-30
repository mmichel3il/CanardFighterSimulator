package src.test.com.canard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("L'attaque spéciale du canard de type vent")
public class AttaqueSpecialeCanardVentTest {

    @Test
    @DisplayName("doit augmenter la vitesse du canard.")
    void doitAppliquerLEtatGeleurSurLAttaquantPourLaProchaineAttaqueUniquement() {
        // Given
        CanardVent canardDeVent = new CanardVent("CanardDeTest", 80, 10);

        canardDeVent.activerCapaciteSpeciale();
        assertEquals(2, canardDeVent.getVitesse());
    }

    @Test
    @DisplayName("doit être cumulable.")
    void doitEtreCumulable() {
        // Given
        CanardVent canardDeVent = new CanardVent("CanardDeTest", 80, 10);

        canardDeVent.activerCapaciteSpeciale();
        assertEquals(2, canardDeVent.getVitesse());

        canardDeVent.activerCapaciteSpeciale();
        assertEquals(3, canardDeVent.getVitesse());

        canardDeVent.activerCapaciteSpeciale();
        assertEquals(4, canardDeVent.getVitesse());
    }
}

package src.test.com.canard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("L'attaque spéciale du canard de type feu")
public class AttaqueSpecialeCanardFeuTest {

    @Test
    @DisplayName("doit ajouter la puissance du canard sur la prochaine attaque uniquement.")
    void doitAjouterLaPuissanceDuCanardALaProchaineAttaqueUniquement() {
        // Given
        CanardFeu canardAttaquant = new CanardFeu("CanardDeTest", 80, 10);
        CanardFeu canardCible = new CanardFeu("CanardDeTest", 80, 10);

        canardAttaquant.activerCapaciteSpeciale();

        canardAttaquant.attaquer(canardCible);
        assertEquals(60, canardCible.getPointsDeVie());

        canardAttaquant.attaquer(canardCible);
        assertEquals(50, canardCible.getPointsDeVie());
    }

    @Test
    @DisplayName("doit mettre le canard de feu dans l'état enragé jusqu'à la prochaine attaque.")
    void doitMettreLeCanardDansLEtatEnrage() {
        // Given
        CanardFeu canardAttaquant = new CanardFeu("CanardDeTest", 80, 10);
        CanardFeu canardCible = new CanardFeu("CanardDeTest", 80, 10);

        canardAttaquant.activerCapaciteSpeciale();
        assertTrue(canardAttaquant.estEnrage());

        canardAttaquant.attaquer(canardCible);
        assertFalse(canardAttaquant.estEnrage());
    }
}

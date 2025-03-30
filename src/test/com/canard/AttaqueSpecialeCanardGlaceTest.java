package src.test.com.canard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("L'attaque spéciale du canard de type glace")
public class AttaqueSpecialeCanardGlaceTest {

    @Test
    @DisplayName("doit appliquer l'état geleur sur l'attaquant pour la prochaine attaque uniquement.")
    void doitAppliquerLEtatGeleurSurLAttaquantPourLaProchaineAttaqueUniquement() {
        // Given
        CanardGlace canardDeGlace = new CanardGlace("CanardDeTest", 80, 10);
        CanardFeu canardFeu = new CanardFeu("CanardFeuTest", 80, 10);

        canardDeGlace.activerCapaciteSpeciale();
        assertTrue(canardDeGlace.estGeleur());

        canardDeGlace.attaquer(canardFeu);
        assertFalse(canardDeGlace.estGeleur());
    }

    @Test
    @DisplayName("doit appliquer l'état gelé sur la cible lors de sa prochaine attaque.")
    void doitAppliquerLEtatGeleSurLaCibleLorsDaProchaineAttaqueUniquement() {
        // Given
        CanardGlace canardAttaquant = new CanardGlace("CanardDeTest", 80, 10);
        CanardFeu canardCible = new CanardFeu("CanardFeuTest", 80, 10);

        assertEquals(Canard.Statut.NORMAL, canardCible.getStatut());

        canardAttaquant.activerCapaciteSpeciale();
        canardAttaquant.attaquer(canardCible);

        assertEquals(Canard.Statut.GEL, canardCible.getStatut());
    }
}

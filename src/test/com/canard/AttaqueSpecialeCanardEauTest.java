package src.test.com.canard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("L'attaque spéciale du canard de type eau")
public class AttaqueSpecialeCanardEauTest {

    @Test
    @DisplayName("doit soigner de 20 points de vie")
    void doitSoignerDeVingtPointsDeVie() {
        // Given
        CanardEau canardEau = new CanardEau("CanardDeTest", 80, 10);
        canardEau.subirDegats(40);

        // When
        canardEau.activerCapaciteSpeciale();

        // Then
        assertEquals(60, canardEau.getPointsDeVie());
    }

    @Test
    @DisplayName("doit au mieux soigner jusqu'à son maximum de points de vie ")
    void doitAuMieuxSoignerJusquASonMaximumDePointsDeVie() {
        // Given
        CanardEau canardEau = new CanardEau("CanardDeTest", 80, 10);
        canardEau.subirDegats(15);

        // When
        canardEau.activerCapaciteSpeciale();

        // Then
        assertEquals(80, canardEau.getPointsDeVie());
    }
}

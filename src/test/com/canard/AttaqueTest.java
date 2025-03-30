package src.test.com.canard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttaqueTest {

    @Test
    void uneAttaqueDeCanardDoitFairePerdreLesPointsDAttaqueDuCanardAttaquantAuxPointsDeVieDuCanardCible() {

        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.FEU, 50, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var source = new CanardDeTest();
        var cible = new CanardDeTest();

        source.attaquer(cible);

        assertEquals(50, source.getPointsDeVie());
        assertEquals(40, cible.getPointsDeVie());
    }

    @RepeatedTest(20)
    void leMultiplicateurDuCanardAttaquantDoitEtrePrisEnCompteLorsDUneAttaque() {

        class CanardDeTest extends Canard {
            public CanardDeTest(TypeCanard type) {
                super("CanardDeTest", type, 50, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var source = new CanardDeTest(TypeCanard.FEU);
        var cible = new CanardDeTest(TypeCanard.EAU);

        var multiplicateur = TypeCanard.getMultiplicateur(source.getType(), cible.getType());

        source.attaquer(cible);

        assertEquals(50, source.getPointsDeVie());
        assertEquals(50 - 10 * multiplicateur, cible.getPointsDeVie());
    }
}

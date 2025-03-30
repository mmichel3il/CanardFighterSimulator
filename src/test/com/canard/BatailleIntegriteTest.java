package src.test.com.canard;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class BatailleIntegriteTest {

    @Test
    void lePremierCanardDeCombatNePeutEtreNull() {
        Canard secondCombattant = TestUtils.genereCanardSansCapaciteSpeciale();

        var iae = assertThrows(IllegalArgumentException.class, () -> new Bataille(null, secondCombattant));

        assertEquals("Le premier combattant ne peut être null.", iae.getMessage());
    }

    @Test
    void leSecondCanardDeCombatNePeutEtreNull() {
        Canard premierCombattant = TestUtils.genereCanardSansCapaciteSpeciale();

        var iae = assertThrows(IllegalArgumentException.class, () -> new Bataille(premierCombattant, null));

        assertEquals("Le second combattant ne peut être null.", iae.getMessage());
    }

    @Test
    void lesDeuxCanardNeDoiventPasEtreLesMemesEnUtilisantLeurReference() {
        Canard premierCombattant = TestUtils.genereCanardSansCapaciteSpeciale();
        Canard secondCombattant = premierCombattant;

        var iae = assertThrows(IllegalArgumentException.class, () -> new Bataille(premierCombattant, secondCombattant));

        assertEquals("Un canard ne peut pas être en conflit avec lui-même.", iae.getMessage());
    }

    @Test
    void laBatailleNePeutPasCommencerSiAuMoinsUnDesCanardsEstKo() {
        Canard premierCombattant1 = TestUtils.genereCanardSansPointsDeVie();
        Canard secondCombattant1 = TestUtils.genereCanardSansCapaciteSpeciale();

        var iae1 = assertThrows(IllegalArgumentException.class, () -> new Bataille(premierCombattant1, secondCombattant1));
        assertEquals("Aucun canard ne doit déjà être KO.", iae1.getMessage());

        Canard premierCombattant2 = TestUtils.genereCanardSansCapaciteSpeciale();
        Canard secondCombattant2 = TestUtils.genereCanardSansPointsDeVie();

        var iae2 = assertThrows(IllegalArgumentException.class, () -> new Bataille(premierCombattant2, secondCombattant2));
        assertEquals("Aucun canard ne doit déjà être KO.", iae2.getMessage());
    }

    @Test
    void laBatailleSeTermineLorsquUnDesDeuxCanardsEstKo() {
        Canard premierCombattant = TestUtils.genereCanardSansCapaciteSpeciale(TypeCanard.GLACE, 10, 2);
        Canard secondCombattant = TestUtils.genereCanardSansCapaciteSpeciale(TypeCanard.GLACE, 20, 2);

        Bataille bataille = new Bataille(premierCombattant, secondCombattant);

        AtomicInteger nombreDeToursDuPremierCombattant = new AtomicInteger(0);
        AtomicInteger nombreDeToursDuSecondCombattant = new AtomicInteger(0);

        Canard vainqueur = bataille.tantQueLaBatailleNEstPasTerminee((tour, canardAttaquant, canardCible) -> {
            if (canardAttaquant == premierCombattant) {
                nombreDeToursDuPremierCombattant.getAndIncrement();
            } else {
                nombreDeToursDuSecondCombattant.getAndIncrement();
            }

            return Bataille.Action.ATTAQUE;
        });

        assertEquals(5, nombreDeToursDuPremierCombattant.get());
        assertEquals(5, nombreDeToursDuSecondCombattant.get());

        assertEquals(0, premierCombattant.getPointsDeVie());
        assertEquals(10, secondCombattant.getPointsDeVie());

        assertEquals(secondCombattant, vainqueur);
    }

    @Test
    void laBatailleDoitAppelerLAttaqueSpecialeDuCanardQuandLActionChoisieEstLAttaqueSpeciale() {
        AtomicInteger capaciteSpecialeEnclenchements = new AtomicInteger(0);

        Canard premierCombattant = TestUtils.genererCanardAvecCapaciteSpeciale(capaciteSpecialeEnclenchements::incrementAndGet);
        Canard secondCombattant = TestUtils.genereCanardSansCapaciteSpeciale(TypeCanard.GLACE, 20, 2);

        Bataille bataille = new Bataille(premierCombattant, secondCombattant);

        bataille.tantQueLaBatailleNEstPasTerminee((numero, attaquant, autre) -> {
           if (numero == 1 && attaquant == premierCombattant) {
               return Bataille.Action.ATTAQUE_SPECIALE;
           }
           return Bataille.Action.ATTAQUE;
        });

        assertEquals(1, capaciteSpecialeEnclenchements.get());
    }
}
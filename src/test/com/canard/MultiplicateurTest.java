package src.test.com.canard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicateurTest {

    @ParameterizedTest
    @CsvSource({
            "GLACE, FEU, 0.5",
            "GLACE, EAU, 1.0",
            "GLACE, VENT, 1.5",
            "GLACE, GLACE, 1.0",

            "FEU, FEU, 1.0",
            "FEU, EAU, 0.5",
            "FEU, VENT, 1.0",
            "FEU, GLACE, 1.5",

            "EAU, FEU, 1.5",
            "EAU, EAU, 1.0",
            "EAU, VENT, 0.5",
            "EAU, GLACE, 1.0",

            "VENT, FEU, 1.0",
            "VENT, EAU, 1.5",
            "VENT, VENT, 1.0",
            "VENT, GLACE, 0.5",
    })
    void leMultiplicateurDoitEtreCorrect(TypeCanard source, TypeCanard cible, Double multiplicateur) {
        assertEquals(multiplicateur, TypeCanard.getMultiplicateur(source, cible));
    }
}

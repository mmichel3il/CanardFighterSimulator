package src.test.com.canard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CanardIntegriteTest {

    @Test
    void leNomDuCanardNeDoitPasEtreNull() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super(null, TypeCanard.GLACE, 6, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, () -> new CanardGlace(null, 6, 10));
        assertEquals("Le nom du canard ne doit pas être null ou blanc.", iae.getMessage());
    }

    @Test
    void leNomDuCanardNeDoitPasEtreBlanc() {
        class CanardDeTest extends Canard {
            public CanardDeTest(String nom) {
                super(nom, TypeCanard.GLACE, 2, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        assertThrows(IllegalArgumentException.class, () -> {
            new CanardDeTest("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CanardDeTest("  ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CanardDeTest("\t");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new CanardDeTest("\s\t\n\r ");
        });
    }

    @Test
    void leTypeDuCanardNeDoitPasEtreNull() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", null, 5, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, CanardDeTest::new);
        assertEquals("Le type du canard ne doit pas être null.", iae.getMessage());
    }

    @Test
    void leCanardDoitEtreMarqueCommeEnEtatNormal() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.FEU, 10, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();
        assertEquals(Canard.Statut.NORMAL, canard.getStatut());
    }

    @Test
    void lesPointsDeVieDuCanardNeDoiventPasEtreNegatifsLorsDeLaConstruction() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", null, -5, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, CanardDeTest::new);
        assertEquals("Le type du canard ne doit pas être null.", iae.getMessage());
    }

    @Test
    void lesPointsDeVieDuCanardNeDoiventPasEtreEgauxAZeroLorsDeLaConstruction() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", null, 0, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, CanardDeTest::new);
        assertEquals("Le type du canard ne doit pas être null.", iae.getMessage());
    }

    @Test
    void laPuissanceDuCanardNeDoitPasEtreNegative() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, 10, -10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, CanardDeTest::new);
        assertEquals("La puissance du canard doit être positive ou égale à 0.", iae.getMessage());
    }

    @Test
    void laPuissanceDuCanardPeutEtreEgaleAZero() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, -1, 0);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var iae = assertThrows(IllegalArgumentException.class, CanardDeTest::new);
        assertEquals("Un canard doit avoir au moins un point de vie.", iae.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 90, 10",
            "90, 80, 10",
            "90, 89, 1",
            "70, 0, 70",
            "5, 5, 0",
    })
    void leCanardDoitSubirLesDegatsInfliges(int pdv, int degats, int expected) {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, pdv, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();

        canard.subirDegats(degats);

        assertEquals(expected, canard.getPointsDeVie());
    }

    @Test
    void leCanardDoitAvoirZeroPointDeVieQuandIlSubitUneAttaquePlusEleveeQueSonNombreActuelDePointsDeVie() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, 10, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();

        canard.subirDegats(12);

        assertEquals(0, canard.getPointsDeVie());
    }

    @Test
    void leCanardDoitEtreConsidereCommeKoQuandSesPointsDeVieSontAZero() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, 1, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();
        canard.subirDegats(1);

        assertTrue(canard.estKO());
    }

    @Test
    void leCanardNeDoitPasEtreConsidereCommeKoQuandSesPointsDeVieSontStrictementSuperieursAZero() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.GLACE, 2, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();
        canard.subirDegats(1);

        assertFalse(canard.estKO());
    }

    @Test
    void leCanardDoitEtreMarqueCommeGele() {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", TypeCanard.FEU, 10, 10);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        CanardDeTest canard = new CanardDeTest();
        canard.gel();

        assertEquals(Canard.Statut.GEL, canard.getStatut());
    }
}

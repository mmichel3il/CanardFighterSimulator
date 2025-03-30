package src.test.com.canard;

import java.util.Random;

public final class TestUtils {

    private static final Random RANDOM = new Random();

    private TestUtils() {}

    public static TypeCanard genereTypeCanard() {
        var types = TypeCanard.values();
        return types[RANDOM.nextInt(types.length)];
    }

    public static int generePointsDeVie() {
        return RANDOM.nextInt(10, 100);
    }

    public static int generePuissance() {
        return RANDOM.nextInt(10, 30);
    }

    public static Canard genererCanardAvecCapaciteSpeciale(Runnable action) {
        class CanardDeTest extends Canard {
            public CanardDeTest() {
                super("CanardDeTest", genereTypeCanard(), generePointsDeVie(), generePuissance());
            }

            @Override
            void activerCapaciteSpeciale() {
                action.run();
            }
        }

        return new CanardDeTest();
    }

    public static Canard genereCanardSansCapaciteSpeciale(TypeCanard type, int pointsDeVie, int puissance) {
        class CanardDeTest extends Canard {
            public CanardDeTest(TypeCanard type, int pointsDeVie, int puissance) {
                super("CanardDeTest", type, pointsDeVie, puissance);
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        return new CanardDeTest(type, pointsDeVie, puissance);
    }

    public static Canard genereCanardSansCapaciteSpeciale() {
        return genereCanardSansCapaciteSpeciale(genereTypeCanard(), generePointsDeVie(), generePuissance());
    }

    public static Canard genereCanardSansPointsDeVie() {
        final int pointsDeVie = generePointsDeVie();

        class CanardDeTest extends Canard {
            public CanardDeTest(int pointsDeVie) {
                super("CanardDeTest", genereTypeCanard(), pointsDeVie, RANDOM.nextInt(10, 20));
            }

            @Override
            void activerCapaciteSpeciale() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }

        var canard = new CanardDeTest(pointsDeVie);
        canard.subirDegats(pointsDeVie);
        return canard;
    }
}

package src;

import java.util.HashMap;
import java.util.Map;

public enum TypeCanard {
    EAU,
    FEU,
    GLACE,
    VENT;

    public static double getMultiplicateur(TypeCanard attaquant, TypeCanard cible) {
        Map<TypeCanard, Map<TypeCanard, Double>> map = Map.of(
                EAU, Map.of(FEU, 1.5, VENT, 0.5),
                FEU, Map.of(EAU, 0.5, GLACE, 1.5),
                GLACE, Map.of(FEU, 0.5, VENT, 1.5),
                VENT, Map.of(EAU, 1.5, VENT, 0.5));

        return map.get(attaquant).getOrDefault(cible, 1.0);
    }
}

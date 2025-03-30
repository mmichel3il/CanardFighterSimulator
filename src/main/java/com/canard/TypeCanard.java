package com.canard;

public enum TypeCanard {
    VENT, GLACE, FEU, EAU;

    private static final double[][] MULTIPLICATEURS = new double[][] {
            { 1.0, 0.5, 1.0, 1.5 }, // VENT
            { 1.5, 1.0, 0.5, 1.0 }, // GLACE
            { 1.0, 1.5, 1.0, 0.5 }, // FEU
            { 0.5, 1.0, 1.5, 1.0 } // EAU
    };

    public static double getMultiplicateur(TypeCanard attaquant, TypeCanard cible) {
        return MULTIPLICATEURS[attaquant.ordinal()][cible.ordinal()];
    }

    @Override
    public String toString() {
        return switch (this) {
            case VENT -> "Vent";
            case GLACE -> "Glace";
            case FEU -> "Feu";
            case EAU -> "Eau";
        };
    }
}

package com.canard;

public final class CanardVent extends Canard {


    public CanardVent(String nom, int pointsDeVie, int puissance) {
        super(nom, TypeCanard.VENT, pointsDeVie, puissance);
    }

    @Override
    protected void capaciteSpeciale() {
        vitesse += 2;
    }
}

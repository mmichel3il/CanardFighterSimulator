package com.canard;

public final class CanardEau extends Canard {
    public CanardEau(String nom, int pointsDeVie, int puissance) {
        super(nom, TypeCanard.EAU, pointsDeVie, puissance);
    }

    @Override
    protected void capaciteSpeciale() {
        pointsDeVie = Math.min(maxPointsDeVie, pointsDeVie + 20);
    }
}

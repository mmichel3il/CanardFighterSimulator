package com.canard;

public final class CanardFeu extends Canard {

    private boolean enrage = false;

    public CanardFeu(String nom, int pointsDeVie, int puissance) {
        super(nom, TypeCanard.FEU, pointsDeVie, puissance);
    }

    @Override
    protected void capaciteSpeciale() {
        enrage = true;
    }

    @Override
    void attaquer(Canard autreCanard) {
        super.attaquer(autreCanard);
        enrage = false;
    }

    @Override
    protected int bonusAttaque() {
        return enrage ? puissance : 0;
    }

    public boolean estEnrage() {
        return enrage;
    }
}

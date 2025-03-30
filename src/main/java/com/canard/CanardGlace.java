package com.canard;

public final class CanardGlace extends Canard {

    private boolean geleur;

    public CanardGlace(String nom, int pointsDeVie, int puissance) {
        super(nom, TypeCanard.GLACE, pointsDeVie, puissance);
        geleur = false;
    }

    @Override
    protected void capaciteSpeciale() {
        geleur = true;
    }

    @Override
    void attaquer(Canard autreCanard) {
        super.attaquer(autreCanard);
        if (geleur) {
            autreCanard.gel();
            geleur = false;
        }
    }

    boolean estGeleur() {
        return geleur;
    }
}

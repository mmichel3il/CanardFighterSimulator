package com.canard;


public abstract class Canard {

    public static final int COUT_ENERGIE_ATTAQUE = 5;
    public static final int COUT_ENERGIE_ATTAQUE_SPECIALE = 15;

    protected final String nom;
    protected final TypeCanard typeCanard;
    protected final int maxPointsDeVie;
    protected int pointsDeVie;
    protected final int puissance;
    protected Statut statut;
    protected int vitesse;
    protected int energie;

    public Canard(String nom, TypeCanard typeCanard, int pointsDeVie, int puissance) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du canard ne doit pas être null ou blanc.");
        }
        if (typeCanard == null) {
            throw new IllegalArgumentException("Le type du canard ne doit pas être null.");
        }
        if (pointsDeVie <= 0) {
            throw new IllegalArgumentException("Un canard doit avoir au moins un point de vie.");
        }
        if (puissance < 0) {
            throw new IllegalArgumentException("La puissance du canard doit être positive ou égale à 0.");
        }

        this.nom = nom;
        this.typeCanard = typeCanard;
        this.pointsDeVie = pointsDeVie;
        this.maxPointsDeVie = pointsDeVie;
        this.puissance = puissance;
        this.statut = Statut.NORMAL;
        this.vitesse = 1;
        this.energie = 50;
    }

    final int getEnergie() {
        return energie;
    }

    final String getNom() {
        return nom;
    }

    final Statut getStatut() {
        return statut;
    }

    final int getPointsDeVie() {
        return pointsDeVie;
    }

    final int getPointsAttaque() {
        return puissance;
    }

    final TypeCanard getType() {
        return typeCanard;
    }

    void attaquer(Canard autreCanard) {
        energie -= COUT_ENERGIE_ATTAQUE;

        int degats = (int) (puissance * TypeCanard.getMultiplicateur(typeCanard, autreCanard.typeCanard)) + bonusAttaque();

        // 10% de chance de critique
        if (Math.random() >= 0.9) {
            degats *= 2;
        }
        autreCanard.subirDegats(degats);
    }

    protected int bonusAttaque() {
        return 0;
    }

    final void subirDegats(int degats) {
        pointsDeVie = Math.max(pointsDeVie - degats, 0);
    }

    final boolean estKO() {
        return pointsDeVie == 0;
    }

    final void gel() {
        statut = Statut.GEL;
    }

    final void degel() {
        statut = Statut.NORMAL;
    }

    final int getVitesse() {
        return vitesse;
    }

    final boolean decrementeVitesseSiPossible() {
        if (vitesse - 1 >= 1) {
            vitesse--;
            return true;
        } else {
            return false;
        }
    }

    final void activerCapaciteSpeciale() {
        energie -= COUT_ENERGIE_ATTAQUE_SPECIALE;
        capaciteSpeciale();
    }

    protected abstract void capaciteSpeciale();

    @Override
    public String toString() {
        return nom + " (" + getType() + ") avec " + pointsDeVie + " point" + (pointsDeVie > 1 ? "s" : "") + " de vie et une puissance de " + puissance + ".";
    }

    public void appliqueChangementDEnergie(int energie) {
        this.energie -= energie;
    }

    public enum Statut {
        NORMAL,
        GEL
    }
}

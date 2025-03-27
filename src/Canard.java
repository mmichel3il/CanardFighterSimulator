package src;

public abstract class Canard {
    private String nom;
    private TypeCanard type;
    private int pointsDeVie;
    private int pointsAttaque;

    public Canard(String nom, TypeCanard type, int pointsDeVie, int pointsAttaque) {
        this.nom = nom;
        this.type = type;
        this.pointsDeVie = pointsDeVie;
        this.pointsAttaque = pointsAttaque;
    }
    public String getNom() {
        return nom;
    }

    public TypeCanard getType() {
        return type;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public int getPointsAttaque() {
        return pointsAttaque;
    }

    public void attaquer(Canard autreCanard) {
        autreCanard.subirDegats(0); // TODO
    }

    public void subirDegats(int degats) {
        pointsDeVie -= degats;
        if (pointsDeVie <= 0) {
            pointsDeVie = 0;
            System.out.println("Votre canard est KO.");
        }
    }

    public boolean estKO() {
        return pointsDeVie == 0;
    }
}

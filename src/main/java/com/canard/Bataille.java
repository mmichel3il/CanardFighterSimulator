package com.canard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Bataille {

    private final Canard premierCombattant;
    private final Canard secondCombattant;

    public int tour;

    public Bataille(Canard premierCombattant, Canard secondCombattant) {
        if (premierCombattant == null) {
            throw new IllegalArgumentException("Le premier combattant ne peut être null.");
        } else if (secondCombattant == null) {
            throw new IllegalArgumentException("Le second combattant ne peut être null.");
        } else if (premierCombattant == secondCombattant) {
            throw new IllegalArgumentException("Un canard ne peut pas être en conflit avec lui-même.");
        } else if (premierCombattant.estKO() || secondCombattant.estKO()) {
            // Le canard peut techniquement subir des dégâts via la méthode
            // subirDegats() en dehors de la bataille. Nous avons pris le parti de ne
            // pas prendre en compte ce risque dans cet exercice
            // malgré tout, je sais que cela implique des potentiels bugs d'intégrité de
            // mes données dans la bataille. Pour palier à ce problème,
            // il faudrait faire une deep copy des canards dans la bataille, par exemple.
            throw new IllegalArgumentException("Aucun canard ne doit déjà être KO.");
        }
        this.premierCombattant = premierCombattant;
        this.secondCombattant = secondCombattant;
        tour = 1;
    }


    public boolean estTerminee() {
        return secondCombattant.estKO() || premierCombattant.estKO();
    }

    public Canard tantQueLaBatailleNEstPasTerminee(
            FonctionTour recupereActionAPartirDuTour,
            Consumer<List<EvenementDansLeTour>> faitQuelqueChoseDesEvenementsDansLeTour
    ) {
        while (!premierCombattant.estKO()) {

            List<EvenementDansLeTour> evenementsDuTourDuPremierCombattant = tourDUnCanard(premierCombattant, secondCombattant, recupereActionAPartirDuTour);

            if (secondCombattant.estKO()) {
                evenementsDuTourDuPremierCombattant.add(new EvenementDansLeTour(secondCombattant.getNom() + " s'est pris la paté (de canard) !" + premierCombattant.getNom() + " obtient la palme d'or 🏆 !"));
                faitQuelqueChoseDesEvenementsDansLeTour.accept(evenementsDuTourDuPremierCombattant);
                return premierCombattant;
            } else {
                faitQuelqueChoseDesEvenementsDansLeTour.accept(evenementsDuTourDuPremierCombattant);
            }

            List<EvenementDansLeTour> evenementsDuTourDuSecondCombattant = tourDUnCanard(secondCombattant, premierCombattant, recupereActionAPartirDuTour);

            if (premierCombattant.estKO()) {
                evenementsDuTourDuSecondCombattant.add(new EvenementDansLeTour(premierCombattant.getNom() + " s'est pris la paté (de canard) ! " + secondCombattant.getNom() + " obtient la palme d'or 🏆 !"));
                faitQuelqueChoseDesEvenementsDansLeTour.accept(evenementsDuTourDuSecondCombattant);
                return premierCombattant;
            } else {
                faitQuelqueChoseDesEvenementsDansLeTour.accept(evenementsDuTourDuSecondCombattant);
            }

            tour++;
        }
        return secondCombattant;
    }

    private List<EvenementDansLeTour> tourDUnCanard(Canard attaquant, Canard cible, FonctionTour recupereActionAPartirDuTour) {
        List<EvenementDansLeTour> evenements = new ArrayList<EvenementDansLeTour>();

        evenements.addAll(switch (attaquant.getStatut()) {
            case NORMAL -> {
                Action action = recupereActionAPartirDuTour.choixDeLAction(tour, attaquant, cible, actionsDisponibles(attaquant));
                yield executeLActionPourLAttaquant(action, attaquant, cible);
            }
            case GEL -> {
                attaquant.degel();
                yield List.of(new EvenementDansLeTour(attaquant.getNom() + " est gelé, décongélation en cours..."));
            }
        });
        if (attaquant.decrementeVitesseSiPossible()) {
            evenements.add(new EvenementDansLeTour(attaquant.getNom() + " voit une canne au loin, se déconcentre et perd donc de la vitesse."));
        }
        return evenements;
    }

    private static List<Action> actionsDisponibles(Canard canard) {
        return Arrays.stream(Action.values()).filter(action -> action.energieNecessaire <= canard.getEnergie()).toList();
    }

    private List<EvenementDansLeTour> executeLActionPourLAttaquant(Action action, Canard canardAttaquant, Canard canardCible) {

        ArrayList<EvenementDansLeTour> evenementsDuTourDuCanard = new ArrayList<>();
        evenementsDuTourDuCanard.add(new EvenementDansLeTour(canardAttaquant.getNom() + " récupère " + Action.ATTAQUE.energieNecessaire + " points d'énergie..."));

        switch (action) {
            case ATTAQUE -> {
                evenementsDuTourDuCanard.addAll(attaque(canardAttaquant, canardCible));
            }
            case ATTAQUE_SPECIALE -> {
                evenementsDuTourDuCanard.addAll(attaqueSpeciale(canardAttaquant));
                evenementsDuTourDuCanard.addAll(attaque(canardAttaquant, canardCible));
            }
            case PASSER -> {
                canardAttaquant.appliqueChangementDEnergie(-Action.ATTAQUE.energieNecessaire);
                evenementsDuTourDuCanard.add(new EvenementDansLeTour(canardAttaquant.getNom() + " passe son tour, il est au petit coin et regagne de l'énergie !"));
            }
        }

        return evenementsDuTourDuCanard;
    }

    private static List<EvenementDansLeTour> attaque(Canard canardAttaquant, Canard canardCible) {
        List<EvenementDansLeTour> evenements = new ArrayList<>();
        for (int attaque = 0; attaque < canardAttaquant.getVitesse() && canardAttaquant.getEnergie() >= Canard.COUT_ENERGIE_ATTAQUE; attaque++) {
            int pointsDeVieDuCanardCibleAvantLAttaque = canardCible.getPointsDeVie();
            canardAttaquant.attaquer(canardCible);
            int degats = pointsDeVieDuCanardCibleAvantLAttaque - canardCible.getPointsDeVie();
            evenements.add(new EvenementDansLeTour(canardAttaquant.getNom() + " attaque " + canardCible.getNom() + ". Il subit " + degats + " de dégâts."));
        }
        return evenements;
    }

    private static List<EvenementDansLeTour> attaqueSpeciale(Canard canard) {
        canard.activerCapaciteSpeciale();

        return switch (canard.getType()) {
            case VENT ->
                    List.of(new EvenementDansLeTour("La vitesse de " + canard.getNom() + " augmente, ce n'est plus un canard boiteux."));

            case GLACE ->
                    List.of(new EvenementDansLeTour("Il fait un froid de canard, la prochaine cible de " + canard.getNom() + " sera gelée pour son prochain tour !"));

            case FEU ->
                    List.of(new EvenementDansLeTour(canard.getNom() + " s'enrage, sa prochaine attaque risque de vous en boucher un coin !"));

            case EAU ->
                    List.of(new EvenementDansLeTour(canard.getNom() + " est dans un meilleur état, c'est l'effet canard WC !"));
        };
    }

    public interface FonctionTour {
        Action choixDeLAction(int numero, Canard attaquant, Canard autre, List<Action> actionsDisponibles);
    }

    public enum Action {
        PASSER(-5),
        ATTAQUE(Canard.COUT_ENERGIE_ATTAQUE),
        ATTAQUE_SPECIALE(Canard.COUT_ENERGIE_ATTAQUE_SPECIALE);


        public final int energieNecessaire;

        Action(int energieNecessaire) {this.energieNecessaire = energieNecessaire;}
    }

    public record EvenementDansLeTour(
            String description
    ) {}
}

package com.canard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SequencedCollection;

import static com.canard.Bataille.Action.ATTAQUE;

public class Application {
    private final static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        boolean quitter = false;

        SequencedCollection<Canard> canards = new ArrayList<>();

        System.out.println("Bienvenue dans Canard Fighter Simulator !");

        canards.add(new CanardGlace("Jean", 1000, 50));
        canards.add(new CanardVent("Paul", 1000, 50));

        do {
            if (canards.size() <= 1) {
                System.out.println("""
                        Vous souhaitez ...
                        
                        1. ... Créer un canard 🦆
                        2. ... Quitter l'application 🥲
                        """);
            } else {
                System.out.println("""
                        Vous souhaitez ...
                        
                        1. ... Créer un canard 🦆
                        2. ... Lancer un conflit de canards 🥊
                        3. ... Quitter l'application 🥲
                        """);
            }

            switch (SCANNER.nextLine()) {
                case "1" -> {
                    Canard nouveauCanard = creerCanard();
                    System.out.println(nouveauCanard.getNom() + " est incroyablement beau, fort et intelligent !");
                    canards.add(nouveauCanard);
                }
                case "2" -> {
                    if (canards.size() <= 1) {
                        quitter = true;
                    } else {
                        System.out.println("Pour ce conflit, c'est le moment de choisir vos canards, commençons par votre premier combattant...");
                        List<Canard> canardsDisponibles = new ArrayList<>(canards);
                        Canard premierCombattant = choisirUnCanard(canardsDisponibles);
                        canardsDisponibles.remove(premierCombattant);
                        System.out.println("Vous avez sélectionné " + premierCombattant.getNom() + " !");
                        System.out.println("Maintenant je vous laisse choisir votre deuxième canard...");
                        Canard secondCombattant = choisirUnCanard(canardsDisponibles);
                        System.out.println("Vous avez sélectionné comme deuxième combattant " + secondCombattant.getNom() + " !");
                        lancerUnCombat(premierCombattant, secondCombattant);
                    }
                }
                case "3" -> {
                    if (canards.size() > 1) {
                        quitter = true;
                    }
                }
                default -> {}
            }

        } while (!quitter);

        System.out.println("Vous avez raison de quitter ce programme, il est codé en Java");
    }

    private static void lancerUnCombat(Canard premierCombattant, Canard secondCombattant) {
        System.out.println("Il est temps de régler ce conflit de canards...");

        Bataille bataille = new Bataille(premierCombattant, secondCombattant);

        bataille.tantQueLaBatailleNEstPasTerminee(
                Application::choixDeLAction,
                evenementsDansLeTour -> evenementsDansLeTour.forEach(evenement -> System.out.println(evenement.description()))
        );
    }

    private static Bataille.Action choixDeLAction(int numero, Canard attaquant, Canard autre, List<Bataille.Action> actionsDisponibles) {
        System.out.println("C'est le tour " + numero + " de " + attaquant.getNom() + " (" + attaquant.getPointsDeVie() + " points de vie et " + attaquant.getEnergie() + " points d'énergie) !");
        System.out.println("Il reste " + autre.getPointsDeVie() + " points de vie à " + autre.getNom());

        System.out.println("C'est au tour de " + attaquant.getNom() + ", vous souhaitez...");
        do {
            actionsDisponibles.forEach(action -> {
                switch (action) {
                    case PASSER ->
                            System.out.println("1. ... Passer votre tour et en profiter pour aller au petit coin.");
                    case ATTAQUE ->
                            System.out.println("2. ... Effectuer une attaque normale (" + action.energieNecessaire + " points d'énergie)");
                    case ATTAQUE_SPECIALE ->
                            System.out.println("3. ... Lancer votre capacité spéciale (" + action.energieNecessaire + " points d'énergie) puis attaquer (" + ATTAQUE.energieNecessaire + " points d'énergie)");
                }
            });

            if (SCANNER.hasNextInt()) {
                int saisie = SCANNER.nextInt();

                if (0 < saisie && saisie <= actionsDisponibles.size()) {
                    SCANNER.nextLine();
                    return actionsDisponibles.get(saisie - 1);
                } else {
                    System.out.println("Vous devez choisir parmi les options disponibles !");
                }

            } else {
                System.out.println("Choisissez un nombre entier parmi ceux disponibles !");
            }
            SCANNER.nextLine();
        } while (true);
    }

    private static Canard choisirUnCanard(List<Canard> canards) {

        int choix = -1;

        do {
            System.out.println("Quel canard voulez-vous choisir pour ce conflit ?");

            for (int indexDuCanard = 1; indexDuCanard <= canards.size(); indexDuCanard++) {
                Canard canard = canards.get(indexDuCanard - 1);
                System.out.println(indexDuCanard + ". " + canard);
            }

            if (SCANNER.hasNextInt()) {
                int saisie = SCANNER.nextInt();

                if (saisie < 1) {
                    System.out.println("Franchement, je ne comprends, je marque exprès des nombres entre 1 et " + canards.size() + " mais vous me donnez un nombre négatif, qu'ai-je fait pour mériter ça ?");
                } else if (saisie > canards.size()) {
                    System.out.println("Si vous voulez ce canard, il va falloir en créer encore " + (saisie - canards.size()));
                } else {
                    choix = saisie;
                }
            } else {
                System.out.println("Ici, je vous demande de choisir un canard dans la liste en indiquant son numéro !");
            }

            SCANNER.nextLine();

        } while (choix == -1);

        return canards.get(choix - 1);
    }

    private static Canard creerCanard() {
        System.out.println("Vous êtes sur le point de créer le plus beau des canards, mais quel est son nom ?");
        var nom = SCANNER.nextLine();

        while (nom.isBlank()) {
            System.out.println("Oups ! Je n'ai pas bien compris, c'était quoi son nom déjà ?");
            nom = SCANNER.nextLine();
        }

        TypeCanard type = null;
        TypeCanard[] types = TypeCanard.values();

        System.out.println(nom + " maitrise un élément, mais lequel ?");

        do {
            for (int indexDuType = 0; indexDuType < types.length; indexDuType++) {
                System.out.println((indexDuType + 1) + ". " + types[indexDuType]);
            }

            try {
                int indexDuType = Integer.parseInt(SCANNER.nextLine());
                if (indexDuType <= 0 || indexDuType > types.length) {
                    System.out.println("Pourtant, je pensais avoir été claire. Je vous demande un nombre entre 1 et " + types.length);
                } else {
                    type = types[indexDuType - 1];
                }
            } catch (NumberFormatException e) {
                System.out.println("Je ne suis pas convaincue que votre choix soit un nombre, je vous rappelle que je ne comprends que des nombres ou des noms mais là, c'est un nombre que je demande.");
            }

        } while (type == null);

        int pointsDeVie = 0;

        System.out.println(nom + " a déjà l'air exceptionel, je vois en lui une force vitale, mais je n'arrive pas à déterminer sa valeur, combien a-t-il de points de vie ?");

        do {

            if (SCANNER.hasNextInt()) {
                int saisie = SCANNER.nextInt();
                if (saisie <= 0) {
                    System.out.println("Pourtant, je suis sûre d'avoir vu une aura en lui, je ne pense pas qu'il soit déjà mort. Combien a-t-il de points de vie ?");
                } else {
                    pointsDeVie = saisie;
                }
            } else {
                System.out.println("Là, dans ce cas précis, ce que je vous demande, ce sont ses points de vie, pas un mot, pas un code, pas votre mot de passe.");
            }
            SCANNER.nextLine();

        } while (pointsDeVie == 0);

        int puissance = -1;

        System.out.println("Avec une force vitale pareille, " + nom + " est déjà hors du commun ! Mais qu'en est-t-il de sa puissance d'attaque ?");

        do {

            if (SCANNER.hasNextInt()) {
                int saisie = SCANNER.nextInt();
                if (saisie < 0) {
                    System.out.println("Je trouve que vous êtes dur(e) avec " + nom + ", il m'a l'air d'avoir une force difficile à percevoir mais tout de même ! Vérifier au fond delui sa puissance, quelle est-elle ?");
                } else {
                    puissance = saisie;
                }
            } else {
                System.out.println("Je ne comprends pas, comment pouvez-vous vous tromper encore malgré toutes les questions que je vous ai déjà posées ?");
            }
            SCANNER.nextLine();

        } while (puissance == -1);

        Canard canard = switch (type) {
            case VENT -> new CanardVent(nom, pointsDeVie, puissance);
            case GLACE -> new CanardGlace(nom, pointsDeVie, puissance);
            case FEU -> new CanardFeu(nom, pointsDeVie, puissance);
            case EAU -> new CanardEau(nom, pointsDeVie, puissance);
        };

        return canard;
    }
}

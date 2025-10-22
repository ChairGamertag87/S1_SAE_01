import java.util.Scanner;

/**
 * Programme principal de gestion d'une épreuve équestre avec saisie de deux manches,
 * calcul des temps compensés, éliminations et affichage du podium final.
 *
 * @author Clément, Léo, Kerdiwen
 * @version 1.0
 */
public class Main {

    /** Scanner global utilisé pour toutes les saisies utilisateur */
    static Scanner sc = new Scanner(System.in);

    /**
     * Point d'entrée du programme.
     * Initialise les données de l'épreuve, saisit les manches et affiche les résultats.
     *
     * @param args arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {

        int maxCompetiteur = 50;

        // Saisie information Épreuve
        int nbCompetiteurs = saisieEntierBorne(sc, 1, maxCompetiteur,
                "Veuillez saisir le nombre de compétiteurs (entre 1 et " + maxCompetiteur + ") : ");
        double longueurPiste = saisieReelMinimum(sc, 0.0,
                "Veuillez saisir la longueur de la piste (> 0.0) : ");
        int nbObstacles = saisieEntierBorne(sc, 1, Integer.MAX_VALUE,
                "Veuillez saisir le nombre d'obstacles (>= 1) : ");

        int minBarres = nbObstacles * 2;
        int maxBarres = nbObstacles * 4;
        int nbBarresMax = saisieEntierBorne(sc, minBarres, maxBarres,
                "Veuillez saisir le nombre total de barres (entre " + minBarres + " et " + maxBarres + ") : ");

        System.out.println("\nRécapitulatif de l'épreuve: ");
        System.out.println("Nombre de compétiteurs : " + nbCompetiteurs);
        System.out.println("Longueur de la piste : " + longueurPiste);
        System.out.println("Nombre d'obstacles : " + nbObstacles);
        System.out.println("Nombre total de barres : " + nbBarresMax);
        System.out.println();

        // Données par joueur
        int[] nbBarresM1 = new int[nbCompetiteurs];
        int[] nbRefusM1 = new int[nbCompetiteurs];
        int[] tempsMsM1 = new int[nbCompetiteurs];
        boolean[] chuteM1 = new boolean[nbCompetiteurs];
        boolean[] elimineM1 = new boolean[nbCompetiteurs];

        int[] nbBarresM2 = new int[nbCompetiteurs];
        int[] nbRefusM2 = new int[nbCompetiteurs];
        int[] tempsMsM2 = new int[nbCompetiteurs];
        boolean[] chuteM2 = new boolean[nbCompetiteurs];
        boolean[] elimineM2 = new boolean[nbCompetiteurs];

        // === Saisie de la première manche
        System.out.println("Nous allons procéder à la saisie de la première manche:");
        for (int i = 0; i < nbCompetiteurs; i++) {
            System.out.println("\n--- Joueur " + (i + 1) + " (Manche 1) ---");
            saisieManche(i, nbBarresMax, longueurPiste, nbBarresM1, nbRefusM1, tempsMsM1, chuteM1, elimineM1);
        }

        // === Résultats de la première manche
        System.out.println("\n=== Résultats Manche 1 ===");
        for (int i = 0; i < nbCompetiteurs; i++) {
            afficherManche(
                    nbCompetiteurs,
                    nbBarresMax,
                    longueurPiste,
                    nbBarresM1,
                    nbRefusM1,
                    chuteM1,
                    tempsMsM1,
                    elimineM1,
                    1,
                    i
            );
        }

        // === Saisie de la deuxième manche
        System.out.println("\nNous allons procéder à la saisie de la deuxième manche:");
        for (int i = 0; i < nbCompetiteurs; i++) {
            if (elimineM1[i]) {
                System.out.println("\n--- Joueur " + (i + 1) + " (Manche 2) ---");
                System.out.println("Ce joueur a déjà été éliminé à la manche 1, il ne participe pas à la manche 2.");
                elimineM2[i] = true;
                continue;
            }
            System.out.println("\n--- Joueur " + (i + 1) + " (Manche 2) ---");
            saisieManche(i, nbBarresMax, longueurPiste, nbBarresM2, nbRefusM2, tempsMsM2, chuteM2, elimineM2);
        }

        // === Résultats de la deuxième manche
        System.out.println("\n=== Résultats Manche 2 ===");
        for (int i = 0; i < nbCompetiteurs; i++) {
            afficherManche(
                    nbCompetiteurs,
                    nbBarresMax,
                    longueurPiste,
                    nbBarresM2,
                    nbRefusM2,
                    chuteM2,
                    tempsMsM2,
                    elimineM2,
                    2,
                    i
            );
        }

        // === Calcul du podium final
        boolean[] elimFinal = new boolean[nbCompetiteurs];
        double[] tempsCompenseFinal = new double[nbCompetiteurs];
        for (int i = 0; i < nbCompetiteurs; i++) {
            elimFinal[i] = elimineM1[i] || elimineM2[i];

            double t1 = (!elimineM1[i] && tempsMsM1[i] > 0)
                    ? computeTempsCompense(tempsMsM1[i], nbBarresM1[i])
                    : -1;
            double t2 = (!elimineM2[i] && tempsMsM2[i] > 0)
                    ? computeTempsCompense(tempsMsM2[i], nbBarresM2[i])
                    : -1;

            if (elimFinal[i]) tempsCompenseFinal[i] = -1;
            else if (t1 == -1 && t2 == -1) elimFinal[i] = true;
            else if (t1 == -1) tempsCompenseFinal[i] = t2;
            else if (t2 == -1) tempsCompenseFinal[i] = t1;
            else tempsCompenseFinal[i] = Math.min(t1, t2);
        }

        System.out.println("\n=== PODIUM FINAL ===");
        afficherPodium(nbCompetiteurs, elimFinal, tempsCompenseFinal);
    }

    // ======================== MÉTHODES DE SAISIE ===========================

    /**
     * Lit un entier saisi par l'utilisateur.
     *
     * @param pfScanner scanner d'entrée
     * @return la valeur entière saisie
     */
    private static int lireEntier(Scanner pfScanner) {
        while (!pfScanner.hasNextInt()) {
            System.out.println("Erreur : veuillez saisir un nombre entier.");
            pfScanner.next();
        }
        return pfScanner.nextInt();
    }

    /**
     * Lit un nombre réel saisi par l'utilisateur.
     *
     * @param pfScanner scanner d'entrée
     * @return la valeur réelle saisie
     */
    private static double lireDouble(Scanner pfScanner) {
        while (!pfScanner.hasNextDouble()) {
            System.out.println("Erreur : veuillez saisir un nombre réel.");
            pfScanner.next();
        }
        return pfScanner.nextDouble();
    }

    /**
     * Demande la saisie d’un entier compris entre deux bornes incluses.
     *
     * @param pfScanner scanner d'entrée
     * @param borneInf  borne minimale (incluse)
     * @param borneSup  borne maximale (incluse)
     * @param message   message affiché à l'utilisateur
     * @return l'entier saisi
     */
    public static int saisieEntierBorne(Scanner pfScanner, int borneInf, int borneSup, String message) {
        int valeur;
        System.out.print(message);
        valeur = lireEntier(pfScanner);
        while (valeur < borneInf || valeur > borneSup) {
            System.out.println("Erreur : la valeur doit être comprise entre " + borneInf + " et " + borneSup + ".");
            System.out.print(message);
            valeur = lireEntier(pfScanner);
        }
        return valeur;
    }

    /**
     * Demande la saisie d’un nombre réel supérieur à une borne minimale.
     *
     * @param pfScanner scanner d'entrée
     * @param borneMin  borne minimale (exclue)
     * @param message   message affiché à l'utilisateur
     * @return le nombre saisi
     */
    public static double saisieReelMinimum(Scanner pfScanner, double borneMin, String message) {
        System.out.print(message);
        double valeur = lireDouble(pfScanner);
        while (valeur <= borneMin) {
            System.out.println("Erreur : la valeur doit être strictement supérieure à " + borneMin + ".");
            System.out.print(message);
            valeur = lireDouble(pfScanner);
        }
        return valeur;
    }

    // ======================== MANCHE ===========================

    /**
     * Saisit les données d'une manche pour un joueur.
     *
     * @param joueur        index du joueur
     * @param nbBarresMax   nombre maximum de barres
     * @param longueurPiste longueur de la piste en mètres
     * @param nbBarres      tableau du nombre de barres tombées
     * @param nbRefus       tableau du nombre de refus
     * @param tempsMs       tableau des temps en millisecondes
     * @param chute         tableau indiquant si le joueur est tombé
     * @param elimine       tableau indiquant si le joueur est éliminé
     */
    public static void saisieManche(
            int joueur,
            int nbBarresMax,
            double longueurPiste,
            int[] nbBarres,
            int[] nbRefus,
            int[] tempsMs,
            boolean[] chute,
            boolean[] elimine
    ) {
        nbBarres[joueur] = 0;
        nbRefus[joueur] = 0;
        tempsMs[joueur] = 0;
        chute[joueur] = false;
        elimine[joueur] = false;

        while (tempsMs[joueur] == 0 && !elimine[joueur]) {
            System.out.print("Le joueur a-t-il fait tomber une barre (oui/non) ? ");
            String rep = sc.next().trim();
            if (rep.equalsIgnoreCase("oui")) nbBarres[joueur]++;

            System.out.print("Le cheval a-t-il refusé de sauter un obstacle (oui/non) ? ");
            rep = sc.next().trim();
            if (rep.equalsIgnoreCase("oui")) nbRefus[joueur]++;

            System.out.print("Le joueur est-il tombé (oui/non) ? ");
            rep = sc.next().trim();
            if (rep.equalsIgnoreCase("oui")) {
                chute[joueur] = true;
                elimine[joueur] = true;
                System.out.println("Le joueur " + (joueur + 1) + " est éliminé pour chute.");
                break;
            }

            System.out.print("Le parcours est-il fini (oui/non) ? ");
            rep = sc.next().trim();
            if (rep.equalsIgnoreCase("oui")) {
                System.out.print("Saisissez le temps en millisecondes : ");
                tempsMs[joueur] = lireEntier(sc);
            }

            elimine[joueur] = checkElimination(nbRefus[joueur], longueurPiste, tempsMs[joueur]);
            if (elimine[joueur]) System.out.println("Le joueur " + (joueur + 1) + " est éliminé.");
        }
    }

    /**
     * Vérifie si un joueur doit être éliminé selon le nombre de refus ou le temps.
     *
     * @param nbRefus       nombre de refus du cheval
     * @param longueurPiste longueur de la piste
     * @param tempsMs       temps réalisé en millisecondes
     * @return true si le joueur est éliminé, sinon false
     */
    private static boolean checkElimination(int nbRefus, double longueurPiste, int tempsMs) {
        if (nbRefus >= 3) return true;
        if (tempsMs > 0) {
            int limite = (longueurPiste < 600.0) ? 120_000 : 180_000;
            return tempsMs > limite;
        }
        return false;
    }

    // ======================== AFFICHAGES ===========================

    /**
     * Affiche les résultats d'une manche pour un joueur.
     *
     * @param nbCompetiteurs nombre total de compétiteurs
     * @param nbBarresMax    nombre maximum de barres
     * @param longueurPiste  longueur de la piste
     * @param nbBarres       tableau du nombre de barres tombées
     * @param nbRefus        tableau du nombre de refus
     * @param chute          tableau des chutes
     * @param tempsMs        tableau des temps
     * @param elimine        tableau des éliminations
     * @param numeroManche   numéro de la manche (1 ou 2)
     * @param numeroJoueur   index du joueur
     */
    public static void afficherManche(
            int nbCompetiteurs,
            int nbBarresMax,
            double longueurPiste,
            int[] nbBarres,
            int[] nbRefus,
            boolean[] chute,
            int[] tempsMs,
            boolean[] elimine,
            int numeroManche,
            int numeroJoueur
    ) {
        if (numeroManche != 1 && numeroManche != 2) {
            System.out.println("Erreur ! La manche n°" + numeroManche + " n'existe pas!");
            return;
        }
        int j = numeroJoueur;
        System.out.println("Joueur " + (j + 1) + " — Manche " + numeroManche);
        if (elimine[j]) {
            System.out.println("  -> Éliminé.");
            return;
        }

        System.out.println("  Barres tombées : " + nbBarres[j] + " / " + nbBarresMax);
        System.out.println("  Refus : " + nbRefus[j]);
        System.out.println("  Chute : " + (chute[j] ? "oui" : "non"));

        if (tempsMs[j] > 0) {
            int tempsFinal = (int) computeTempsCompense(tempsMs[j], nbBarres[j]);
            System.out.println("  Temps brut : " + formatTemps(tempsMs[j]));
            if (nbBarres[j] > 0)
                System.out.println("  Pénalité : " + (nbBarres[j] * 8000) + " ms (" + nbBarres[j] + " barre(s))");
            System.out.println("  Temps compensé : " + formatTemps(tempsFinal));
        } else System.out.println("  Temps non saisi.");
    }

    /**
     * Calcule le temps compensé en ajoutant une pénalité de 8000 ms par barre tombée.
     *
     * @param tempsMs  temps de base en millisecondes
     * @param nbBarres nombre de barres tombées
     * @return le temps total compensé
     */
    public static double computeTempsCompense(int tempsMs, int nbBarres) {
        return tempsMs + (long) nbBarres * 8000L;
    }

    /**
     * Affiche le podium (1er, 2e, 3e) selon les meilleurs temps compensés.
     *
     * @param nbCompetiteurs nombre de compétiteurs
     * @param elimine        tableau des éliminations
     * @param tempsCompense  tableau des temps compensés
     */
    public static void afficherPodium(int nbCompetiteurs, boolean[] elimine, double[] tempsCompense) {
        double meilleur = -1, deuxieme = -1, troisieme = -1;

        for (int i = 0; i < nbCompetiteurs; i++) {
            if (!elimine[i] && tempsCompense[i] >= 0) {
                double t = tempsCompense[i];
                if (meilleur == -1 || t < meilleur) {
                    troisieme = deuxieme; deuxieme = meilleur; meilleur = t;
                } else if ((t > meilleur && (deuxieme == -1 || t < deuxieme))) {
                    troisieme = deuxieme; deuxieme = t;
                } else if ((t > deuxieme && (troisieme == -1 || t < troisieme))) {
                    troisieme = t;
                }
            }
        }

        System.out.println("=== PODIUM ===");
        afficherRang(1, meilleur, elimine, tempsCompense);
        afficherRang(2, deuxieme, elimine, tempsCompense);
        afficherRang(3, troisieme, elimine, tempsCompense);
    }

    /**
     * Affiche les concurrents correspondant à un rang donné du podium.
     *
     * @param rang          position du podium (1, 2 ou 3)
     * @param temps         temps correspondant au rang
     * @param elimine       tableau des éliminations
     * @param tempsCompense tableau des temps compensés
     */
    private static void afficherRang(int rang, double temps, boolean[] elimine, double[] tempsCompense) {
        if (temps == -1) {
            System.out.println(" " + rang + "e : (personne)");
            return;
        }
        System.out.print(" " + rang + "e : ");
        for (int i = 0; i < elimine.length; i++) {
            if (!elimine[i] && tempsCompense[i] == temps)
                System.out.print("Concurrent " + (i + 1) + " ");
        }
        System.out.println("(" + formatTemps(temps) + ")");
    }

    /**
     * Formate un temps exprimé en millisecondes sous la forme :
     * "X min Y s Z ms".
     *
     * @param tempsMs temps en millisecondes
     * @return chaîne formatée lisible
     */

    public static String formatTemps(double tempsMs) {
        double tempTempsMs = tempsMs;
        if  (tempTempsMs < 0) return "Temps invalide";
        int totalSecondes = (int) (tempTempsMs / 1000);
        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        int millisecondes = (int) (tempTempsMs % 1000);
        return minutes + " min " + secondes + " s " + millisecondes + " ms";
    }
}

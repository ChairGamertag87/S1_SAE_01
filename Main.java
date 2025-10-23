import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    /**
     * Point d'entrée de l'application.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        System.out.println(formatTemps(83456));
        int max_Competiteur = 50;

        // Saisie information Epreuve (leo)
        int nbCompetiteurs = saisieEntierBorne(sc, 1, max_Competiteur, "Veuillez saisir le nombre de compétiteurs (entre 1 et " + max_Competiteur + ") : ");
        double longueurPiste = saisieReelMinimum(sc, 0.0, "Veuillez saisir la longueur de la piste (>=0.0) : ");
        int nbObstacles = saisieEntierBorne(sc, 1, Integer.MAX_VALUE, "Veuillez saisir le nombre d'obstacles (>1) : ");

        int minBarres = nbObstacles * 2;
        int maxBarres = nbObstacles * 4;
        int nbBarresTotal = saisieEntierBorne(sc, minBarres, maxBarres, "Veuillez saisir le nombre de Barres (entre " + minBarres + " et " + maxBarres + ") : ");

        System.out.println("Récapitulatif de l'épreuve: ");
        System.out.println("Nombre de compétiteurs : " + nbCompetiteurs);
        System.out.println("Longueur de la piste : " + longueurPiste);
        System.out.println("Nombre d'obstacles : " + nbObstacles);
        System.out.println("Nombre total de barres : " + nbBarresTotal);

        // Saisie de la première manche (keridwen)
        int[] nbBarres = new int[nbCompetiteurs];
        int[] nbRefus = new int[nbCompetiteurs];
        int[] tempsMs  = new int[nbCompetiteurs];
        boolean[] chute = new boolean[nbCompetiteurs];
        boolean[] elimine = new boolean[nbCompetiteurs];

        System.out.println("Nous allons procéder à la saisie de la première manche: ");
        for (int i = 0; i < nbCompetiteurs; i++) {
            tempsMs[i] = 0;
            saisieManche(i, nbBarres, nbRefus, tempsMs, chute, elimine, nbBarresTotal, longueurPiste);
        }

        // Résultats de la première manche (affichage simple joueur par joueur)(keridwen/clement)
        for (int i = 0; i < nbCompetiteurs; i++) {
            // paramètres "barreTombe" et "refus" ne sont pas utilisés pour la logique,
            // on passe false juste pour afficher.
            afficherManche(nbCompetiteurs, nbBarresTotal, longueurPiste, nbBarres, false, nbRefus, false, chute[i], tempsMs, elimine[i], 1, i + 1);
        }

        // Saisie de la deuxième manche(keridwen)
        System.out.println("Nous allons procéder à la saisie de la deuxième manche: ");
        for (int i = 0; i < nbCompetiteurs; i++) {
            if (elimine[i]) {
                System.out.println("Ce joueur a déjà été éliminé");
                continue;
            }
            saisieManche(i, nbBarres, nbRefus, tempsMs, chute, elimine, nbBarresTotal, longueurPiste);
        }

        // Résultats de la deuxième manche (affichage simple joueur par joueur)(keridwen)
        for (int i = 0; i < nbCompetiteurs; i++) {
            afficherManche(nbCompetiteurs, nbBarresTotal, longueurPiste, nbBarres, false, nbRefus, false, chute[i], tempsMs, elimine[i], 2, i + 1);
        }

        // === CALCUL, AFFICHAGE DES RÉSULTATS FINAUX ET PODIUM (clement) ===
        double[] tempsCompenseFinal = new double[nbCompetiteurs];
        for (int i = 0; i < nbCompetiteurs; i++) {
            if (elimine[i]) {
                tempsCompenseFinal[i] = 0.0; // valeur ignorée par le podium vu qu'elimine[i] == true
            } else {
                tempsCompenseFinal[i] = (double) tempsCompense(tempsMs[i], nbBarres[i]);
            }
        }

        afficherResultats(nbCompetiteurs, elimine, tempsCompenseFinal);

        System.out.println();
        afficherPodium(nbCompetiteurs, elimine, tempsCompenseFinal);
    }

    // === SAISIE DES INFORMATIONS DE LA COURSE ( Léo Péron )===

    /**
     * Lit un entier depuis le scanner, en redemandant tant que l'entrée n'est pas valide.
     *
     * @param pfScanner Scanner d'entrée
     * @return entier lu
     */
    private static int lireEntier(Scanner pfScanner) {
        while (!pfScanner.hasNextInt()) {
            System.out.println("Erreur : veuillez saisir un nombre entier.");
            pfScanner.next();
        }
        return pfScanner.nextInt();
    }

    /**
     * Lit un réel depuis le scanner, en redemandant tant que l'entrée n'est pas valide.
     *
     * @param pfScanner Scanner d'entrée
     * @return réel lu
     */
    private static double lireDouble(Scanner pfScanner) {
        while (!pfScanner.hasNextDouble()) {
            System.out.println("Erreur : veuillez saisir un nombre réel.");
            pfScanner.next();
        }
        return pfScanner.nextDouble();
    }

    /**
     * Lit un entier compris entre deux bornes incluses.
     *
     * @param pfScanner Scanner d'entrée
     * @param pfBorneInf Borne inférieure (incluse)
     * @param pfBorneSup Borne supérieure (incluse)
     * @param pfMessage  Message d'invite
     * @return valeur entière validée dans [pfBorneInf, pfBorneSup]
     */
    public static int saisieEntierBorne(Scanner pfScanner, int pfBorneInf, int pfBorneSup, String pfMessage) {
        int valeur;
        System.out.print(pfMessage);
        valeur = lireEntier(pfScanner);
        while (valeur < pfBorneInf || valeur > pfBorneSup) {
            System.out.println("Erreur : la valeur doit être comprise entre " + pfBorneInf + " et " + pfBorneSup + ".");
            System.out.print(pfMessage);
            valeur = lireEntier(pfScanner);
        }
        return valeur;
    }

    /**
     * Lit un réel strictement supérieur à une borne donnée.
     *
     * @param pfScanner Scanner d'entrée
     * @param pfBorneMin Borne minimale (exclue)
     * @param pfMessage  Message d'invite
     * @return valeur réelle strictement supérieure à pfBorneMin
     */
    public static double saisieReelMinimum(Scanner pfScanner, double pfBorneMin, String pfMessage) {
        System.out.print(pfMessage);
        double valeur = lireDouble(pfScanner);
        while (valeur <= pfBorneMin) {
            System.out.println("Erreur : la valeur doit être strictement supérieure à " + pfBorneMin + ".");
            System.out.print(pfMessage);
            valeur = lireDouble(pfScanner);
        }
        return valeur;
    }
	
	// === SAISIE D’UNE MANCHE (keridwen) ===
    /**
     * Saisie d'une manche pour un joueur : barres, refus, chute, temps.
     * Met à jour les tableaux et détermine l'élimination selon les règles simples.
     *
     * @param joueur        Index du joueur (0..n-1)
     * @param nbBarres      Tableau du nombre de barres tombées par joueur (sortie)
     * @param nbRefus       Tableau du nombre de refus par joueur (sortie)
     * @param tempsMs       Tableau des temps bruts en ms par joueur (sortie)
     * @param chute         Tableau d'état de chute par joueur (sortie)
     * @param elimine       Tableau d'état d'élimination par joueur (sortie)
     * @param nbBarresMax   Nombre maximum de barres autorisées par parcours
     * @param longueurPiste Longueur de la piste (m)
     */
    public static void saisieManche(
            int joueur,
            int[] nbBarres,
            int[] nbRefus,
            int[] tempsMs,
            boolean[] chute,
            boolean[] elimine,
            int nbBarresMax,
            double longueurPiste
    ) {
        // barres tombées
        System.out.print("J" + (joueur + 1) + " - Entrez le nombre de barres tombées (0.." + nbBarresMax + ") : ");
        int b = lireEntier(sc);
        if (b < 0) b = 0;
        if (b > nbBarresMax) b = nbBarresMax;
        nbBarres[joueur] = b;

        // refus
        System.out.print("J" + (joueur + 1) + " - Entrez le nombre de refus (>=0) : ");
        int r = lireEntier(sc);
        if (r < 0) r = 0;
        nbRefus[joueur] = r;

        // chute
        System.out.print("J" + (joueur + 1) + " - Le joueur est-il tombé ? (oui/non) : ");
        String repChute = sc.next().trim().toLowerCase();
        chute[joueur] = repChute.equals("oui");

        // temps (ms)
        System.out.print("J" + (joueur + 1) + " - Entrez le temps en millisecondes (0 si pas de temps) : ");
        int t = lireEntier(sc);
        if (t < 0) t = 0;
        tempsMs[joueur] = t;

        // élimination simple
        boolean out = false;
        if (chute[joueur]) out = true;                     // chute -> éliminé
        if (nbRefus[joueur] >= 3) out = true;              // 3 refus -> éliminé
        if (tempsMs[joueur] > 0) {                         // dépassement temps max
            int limite = (longueurPiste <= 600.0) ? 120_000 : 180_000;
            if (tempsMs[joueur] > limite) out = true;
        }
        elimine[joueur] = out;
    }

    // === AFFICHAGE DES RÉSULTATS D’UNE MANCHE (keridwen) ===

    /**
     * Affiche les informations d'une manche pour un joueur donné (par numéro de brassard).
     *
     * @param nbCompetiteurs Nombre total de compétiteurs
     * @param nbBarresMax    Nombre total de barres sur le parcours
     * @param longueurPiste  Longueur de la piste (m)
     * @param nbBarres       Tableau du nombre de barres tombées par joueur
     * @param barreTombe     Indicateur générique d'affichage (barres tombées)
     * @param nbRefus        Tableau du nombre de refus par joueur
     * @param refus          Indicateur générique d'affichage (refus)
     * @param chute          Indicateur de chute pour ce joueur (affichage)
     * @param tempsMs        Tableau des temps bruts en ms par joueur
     * @param elimine        Indicateur d'élimination pour ce joueur (affichage)
     * @param numeroManche   Numéro de la manche (1 ou 2)
     * @param numeroJoueur   Numéro de brassard (1..nbCompetiteurs)
     */
    public static void afficherManche(
            int nbCompetiteurs,
            int nbBarresMax,
            double longueurPiste,
            int[] nbBarres,
            boolean barreTombe,
            int[] nbRefus,
            boolean refus,
            boolean chute,
            int[] tempsMs,
            boolean elimine,
            int numeroManche,
            int numeroJoueur
    ) {
        if (numeroManche != 1 && numeroManche != 2) {
            System.out.println("Erreur ! La manche n°" + numeroManche + " n'existe pas!");
        } else if (numeroJoueur < 1 || numeroJoueur > nbCompetiteurs) {
            System.out.println("Erreur ! Le joueur n°" + numeroJoueur + " n'existe pas!");
        } else {
            if (numeroManche != 1 && elimine) {
                System.out.println("Le joueur n°" + numeroJoueur + " a été éliminé à la première manche...");
            } else {
                int idx = numeroJoueur - 1;
                System.out.println("Voici les résultats de la manche n°" + numeroManche + " pour le joueur n°" + numeroJoueur + " :");
                System.out.println("Ce joueur était sur un parcours comportant " + nbBarresMax + " et en a fait tomber " + nbBarres[idx] + ".");
                System.out.println("Son cheval a refusé " + nbRefus[idx] + " fois de sauter un obstacle.");
                if (chute) {
                    System.out.println("Ce joueur est malheureusement tombé de son cheval durant la manche, il est donc disqualifié.");
                } else {
                    System.out.println("Ce joueur n'est pas tombé durant son parcours, il est donc toujours en course pour le podium.");
                }
                System.out.println("Ce joueur a réalisé un temps de " + tempsMs[idx]);
                if (barreTombe) {
                    int tempsFinal = tempsCompense(tempsMs[idx], nbBarres[idx]);
                    System.out.println("Il a cependant fait tomber des barres, ramenant son temps final à " + tempsFinal + ".");
                }
            }
        }
    }

    // === SAISIES UNITAIRES (keridwen)===

    /**
     * Incrémente le nombre de barres tombées pour un joueur si {@code barreTombe} est vrai.
     *
     * @param nbBarresMax Nombre maximum de barres pour le parcours
     * @param nbBarres    Tableau du nombre de barres tombées par joueur (modifié)
     * @param barreTombe  Indique si une barre est tombée lors de cet appel
     * @param joueur      Index du joueur (0..n-1)
     * @return Nouveau nombre de barres pour ce joueur
     */
    public static int saisirNbBarresTombees(int nbBarresMax, int[] nbBarres, boolean barreTombe, int joueur) {
        if (barreTombe) {
            nbBarres[joueur] += 1;
        }
        if (nbBarres[joueur] > nbBarresMax) {
            System.out.println("Toutes les barres sont tombées!");
        }
        return nbBarres[joueur];
    }

    /**
     * Incrémente le nombre de refus pour un joueur si {@code refus} est vrai.
     *
     * @param nbRefus Tableau du nombre de refus par joueur (modifié)
     * @param refus   Indique si un refus vient d'avoir lieu
     * @param joueur  Index du joueur (0..n-1)
     * @return Nouveau nombre de refus pour ce joueur
     */
    public static int saisirNbRefus(int[] nbRefus, boolean refus, int joueur) {
        if (refus) {
            nbRefus[joueur] += 1;
        }
        return nbRefus[joueur];
    }

    /**
     * Détermine l'élimination selon le nombre de refus et le dépassement du temps maximal.
     * (La chute est gérée ailleurs.)
     *
     * @param nbRefusJoueur Nombre de refus du joueur
     * @param longueurPiste Longueur de la piste (m)
     * @param joueur        Index/numéro du joueur (utilisé pour les messages)
     * @param tempsMs       Temps brut en ms
     * @return {@code true} si le joueur est éliminé, sinon {@code false}
     */
    public static boolean elimine(int nbRefusJoueur, double longueurPiste, int joueur, int tempsMs) {
        boolean elimine = false;
        if (nbRefusJoueur > 3) {
            elimine = true;
            System.out.println("Le joueur n°" + joueur + " vient d'être éliminé!");
            return elimine;
        }
        if (longueurPiste < 600) {
            if (tempsMs > 120000) {
                System.out.println("Le joueur n°" + joueur + " vient d'être éliminé!");
                elimine = true;
            }
        } else {
            if (tempsMs > 180000) {
                System.out.println("Le joueur n°" + joueur + " vient d'être éliminé!");
                elimine = true;
            }
        }
        return elimine;
    }

    /**
     * Indique l'état de chute sous forme booléenne et affiche un message si chute.
     *
     * @param chute {@code true} si le joueur est tombé, sinon {@code false}
     * @return {@code true} si chute, sinon {@code false}
     */
    public static boolean chute(boolean chute) {
        if (chute) {
            System.out.println("Le joueur est tombé, élimination.");
            return true;
        }
        return false;
    }

    /**
     * Demande et lit un temps en millisecondes.
     *
     * @param tempsMs Valeur initiale
     * @return Temps lu en millisecondes (entier)
     */
    public static int saisirTempsMs(int tempsMs) {
        System.out.println("Saisissez le temps :");
        tempsMs = lireEntier(sc);
        return tempsMs;
    }

    // === CALCULS (clement)===

    /**
     * Calcule le temps compensé en ajoutant 8 000 ms par barre tombée.
     *
     * @param tempsMs  Temps brut en millisecondes
     * @param nbBarres Nombre de barres tombées
     * @return Temps compensé en millisecondes
     */
    public static int tempsCompense(int tempsMs, int nbBarres) {
        for (int i = 0; i < nbBarres; i++) {
            tempsMs = tempsMs + 8000;
        }
        return tempsMs;
    }

    // === AFFICHAGES (clement)===

    /**
     * Affiche les résultats finaux par brassard (temps formaté ou "Éliminé").
     *
     * @param nbCompetiteurs Nombre de compétiteurs
     * @param elimine        Tableau d'élimination par joueur
     * @param tempsCompense  Tableau des temps compensés par joueur
     */
    public static void afficherResultats(
            int nbCompetiteurs,
            boolean[] elimine,
            double[] tempsCompense
    ) {
        System.out.println("=== RÉSULTATS (par brassard) ===");
        for (int i = 0; i < nbCompetiteurs; i++) {
            System.out.print("Joueur " + (i + 1) + " : ");
            if (elimine[i]) {
                System.out.println("Éliminé");
            } else {
                System.out.println(formatTemps(tempsCompense[i]));
            }
        }
    }

    /**
     * Affiche le podium (1er, 2e, 3e) en tenant compte d'éventuelles égalités.
     *
     * @param nbCompetiteurs Nombre de compétiteurs
     * @param elimine        Tableau d'élimination par joueur
     * @param tempsCompense  Tableau des temps compensés par joueur
     */
    public static void afficherPodium(
            int nbCompetiteurs,
            boolean[] elimine,
            double[] tempsCompense
    ) {

        double meilleur = -1;
        double deuxieme = -1;
        double troisieme = -1;

        // On cherche les 3 meilleurs temps distincts
        for (int i = 0; i < nbCompetiteurs; i++) {
            if (!elimine[i]) {
                double t = tempsCompense[i];

                if (meilleur == -1 || t < meilleur) {
                    troisieme = deuxieme;
                    deuxieme = meilleur;
                    meilleur = t;
                } else if ((t > meilleur && (deuxieme == -1 || t < deuxieme))) {
                    troisieme = deuxieme;
                    deuxieme = t;
                } else if ((t > deuxieme && (troisieme == -1 || t < troisieme))) {
                    troisieme = t;
                }
            }
        }

        System.out.println("=== PODIUM ===");

        if (meilleur != -1) {
            System.out.print(" 1er : ");
            for (int i = 0; i < nbCompetiteurs; i++) {
                if (!elimine[i] && tempsCompense[i] == meilleur)
                    System.out.print("Concurrent " + (i + 1) + " ");
            }
            System.out.println("(" + formatTemps(meilleur) + ")");
        }

        if (deuxieme != -1) {
            System.out.print(" 2e : ");
            for (int i = 0; i < nbCompetiteurs; i++) {
                if (!elimine[i] && tempsCompense[i] == deuxieme)
                    System.out.print("Concurrent " + (i + 1) + " ");
            }
            System.out.println("(" + formatTemps(deuxieme) + ")");
        }
        if (troisieme != -1) {
            System.out.print(" 3e : ");
            for (int i = 0; i < nbCompetiteurs; i++) {
                if (!elimine[i] && tempsCompense[i] == troisieme)
                    System.out.print("Concurrent " + (i + 1) + " ");
            }
            System.out.println("(" + formatTemps(troisieme) + ")");
        }
    }

    /**
     * Formatte un temps en millisecondes vers une chaîne lisible "X min Y s Z ms".
     *
     * @param tempsMs Temps en millisecondes
     * @return Chaîne formatée lisible
     */
    public static String formatTemps(double tempsMs) {
        int totalSecondes = (int) (tempsMs / 1000);
        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        int millisecondes = (int) (tempsMs % 1000);
        return minutes + " min " + secondes + " s " + millisecondes + " ms";
    }
}


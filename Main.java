import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);


    // === FONCTION PRINCIPALE ===
	//les tests -> clement
    public static void main(String[] args) {
        System.out.println(formatTemps(83456));
		int max_Competiteur = 50;
		
        // Saisie information Epreuve 
        int nbCompetiteurs = saisieEntierBorne(sc, 1, max_Competiteur, "Veuillez saisir le nombre de compétiteurs (entre 1 et " + max_Competiteur + ")");
        double longueurPiste = saisieReelMinimum(sc, 0.0, "Veuillez saisir la longueur de la piste (>=0.0)");
        int nbObstacles = saisieEntierBorne(sc, 1, Integer.MAX_VALUE, "Veuillez saisir le nombre d'obstacles (>1)");

        int minBarres = nbObstacles * 2;
        int maxBarres = nbObstacles * 4;
        int nbBarres = saisieEntierBorne(sc, minBarres, maxBarres, "Veuillez saisir le nombre de Barres (entre " + minBarres + " et " + maxBarres + ")");

        System.out.println("Récapitulatif de l'épreuve: ");
        System.out.println("Nombre de compétiteurs : " + nbCompetiteurs);
        System.out.println("Longeur de la piste : " + longueurPiste);
        System.out.println("Nombre d'obstacles : " + nbObstacles);
        System.out.println("Nombre total de barres : " + nbBarres);
		
		//Saisie de la première manche
		int competiteurs= nbCompetiteurs-1;
		int i=0;
		System.out.println("Nous allons procéder à la saisie de la première manche: ");
		for(i=0; i < competiteurs; i++){
			saisieManche();
		}
		
		//Résultats de la première manche
		for( i=0;  i < competiteurs;  i++){
			afficherManche(nbCompetiteurs, nbBarresMax, longueurPiste, nbBarres[i], barreTombe, nbRefus[i],refus, chute[i], tempsMs[i], elimine[i], tempsCompense[i], 1 , i);
		}
		
		//Saisie de la deuxième manche
		System.out.println("Nous allons procéder à la saisie de la deuxième manche: ");
		for(i=0; i < competiteurs; i++){
			saisieManche();
		}
		
		//Résultats de la deuxième manche
		for(i=0; i<competiteurs;  i++){
			afficherManche(nbCompetiteurs, nbBarresMax, longueurPiste, nbBarres[i], barreTombe, nbRefus[i],refus, chute[i], tempsMs[i], elimine[i], tempsCompense[i], 2 , i);
		}

        // === TEST 1 : aucun éliminé, tous temps différents ===
        System.out.println("TEST 1 : tous temps différents");
        boolean[] elimine1 = {false, false, false, false};
        double[] temps1 = {85000, 90000, 95000, 100000};
        afficherPodium(4, elimine1, temps1);
        System.out.println();

        // === TEST 2 : égalité au 1er ===
        System.out.println("TEST 2 : égalité au 1er");
        boolean[] elimine2 = {false, false, false, false};
        double[] temps2 = {85000, 85000, 90000, 95000};
        afficherPodium(4, elimine2, temps2);
        System.out.println();

        // === TEST 3 : égalité au 2e ===
        System.out.println("TEST 3 : égalité au 2e");
        boolean[] elimine3 = {false, false, false, false, false};
        double[] temps3 = {83000, 85000, 85000, 85000, 90000};
        afficherPodium(5, elimine3, temps3);
        System.out.println();

        // === TEST 4 : égalité au 3e ===
        System.out.println("TEST 4 : égalité au 3e");
        boolean[] elimine4 = {false, false, false, false, false, false};
        double[] temps4 = {80000, 85000, 90000, 90000, 90000, 95000};
        afficherPodium(6, elimine4, temps4);
        System.out.println();

        // === TEST 5 : 4 concurrents ex aequo au 1er ===
        System.out.println("TEST 5 : 4 premiers ex aequo");
        boolean[] elimine5 = {false, false, false, false, false};
        double[] temps5 = {87000, 87000, 87000, 87000, 90000};
        afficherPodium(5, elimine5, temps5);
        System.out.println();

        // === TEST 6 : certains éliminés ===
        System.out.println("TEST 6 : avec éliminés");
        boolean[] elimine6 = {false, true, false, true, false};
        double[] temps6 = {85000, 86000, 83000, 87000, 88000};
        afficherPodium(5, elimine6, temps6);
        System.out.println();

        // === TEST 7 : tout le monde éliminé ===
        System.out.println("TEST 7 : tout le monde éliminé");
        boolean[] elimine7 = {true, true, true};
        double[] temps7 = {85000, 86000, 87000};
        afficherPodium(3, elimine7, temps7);
        System.out.println();


    }

    // === SAISIE DES INFORMATIONS DE LA COURSE ( Léo Péron )===
    private static int lireEntier(Scanner pfScanner) {
        while (!pfScanner.hasNextInt()) {
            System.out.println("Erreur : veuillez saisir un nombre entier.");
            pfScanner.next();
        }
        return pfScanner.nextInt();
    }
    
    private static double lireDouble(Scanner pfScanner) { 
        while(!pfScanner.hasNextDouble()) {
            System.out.println("Erreur : veuillez saisir un nombre réel.");
            pfScanner.next();
        }
        return pfScanner.nextDouble();
    }

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
	// === SAISIE D'UNE MANCHE (keridwen) ===
	public static void saisieManche(){
		
	}
	
    // === AFFICHAGE DES RÉSULTATS D’UNE MANCHE (keridwen) ===
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
		if (numeroManche != 1 || numeroManche != 2){
			System.out.println("Erreur ! La manche n°" + numeroManche + " n'existe pas!");
		}else if(numeroJoueur<0 || numeroJoueur>nbCompetiteurs){
			System.out.println("Erreur ! Le joueur n°" + numeroJoueur + " n'existe pas!");
		}else{
			if(numeroManche!=1 || elimine ){
				System.out.println("Le joueur n°" + numeroJoueur + " a été éliminé à la première manche...");
			}else{
				System.out.println("Voici les résultats de la manche n°" + numeroManche + " pour le joueur n°" + numeroJoueur + " :");
				System.out.println("Ce joueur était sur un parcours comportant " + nbBarresMax + " et en a fait tomber " + nbBarres[numeroJoueur-1] + "." );
				System.out.println("Son cheval a refusé " + nbRefus[numeroJoueur-1] + " fois de sauter un obstacle.");
				if (chute){
					System.out.println("Ce joueur est malheureusement tombé de son cheval durant la manche, il est donc disqualifié.");
				}else{
					System.out.println("Ce joueur n'est pas tombé durant son parcours, il est donc toujours en course pour le podium.");
				}
				System.out.println("Ce joueur a réalisé un temps de " + tempsMs[numeroJoueur-1]);
				if (barreTombe){
					int tempsFinal = tempsCompense(tempsMs[numeroJoueur-1], nbBarres[numeroJoueur-1]);
					System.out.println("Il a cependant fait tomber des barres, ramenant son temps final à " + tempsFinal + ".");
				}
				
			}
	}


    // === SAISIES UNITAIRES (keridwen)===
    public static int saisirNbBarresTombees(int nbBarresMax, int nbBarres[], boolean barreTombe) { 
		if (barreTombe){
			nbBarres[joueur] += 1;
			barreTombe = false;
		}
		if (nbBarres[joueur]>nbBarresMax){
			System.out.println("Toutes les barres sont tombées!");
		}
		return nbBarres[joueur]; 
	}

    public static int saisirNbRefus(int[]nbRefus, boolean refus) { 
		if (refus){
			nbRefus[joueur]+=1;
			refus=false;
		}	
		return nbRefus[joueur]; 
	}

    public static boolean elimine(int nbRefus[], double longueurPiste) { 
		if (nbRefus[joueur]>3){
			elimine=true;
			return elimine;
		}
		if (longueurPiste<600){
			if(tempMs>120000){
				elimine=true;
			}else{
				if(tempMs>180000){
					elimine=true;
				}
			}
        }
		return elimine; 
	}
	
	public static boolean chute(boolean chute){
		if (chute) {
			elimine=true;
		}
		return  elimine;
	}

    public static long saisirTempsMs() { 
		return 0; 
	}

    // === CALCULS (clement)===

    public static int tempsCompense(int tempsMs, int nbBarres) {
        for  (int i = 0; i < nbBarres; i++) {
            tempsMs = tempsMs + 8000;
        }
        return tempsMs;
    }

    // === AFFICHAGES (clement)===
    public static void afficherResultats(
            int nbCompetiteurs,
            boolean[] elimine,
            double[] tempsCompense) {

    }

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

	//clement
    public static String formatTemps(double tempsMs) {
        int totalSecondes = (int)(tempsMs / 1000);

        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        int millisecondes = (int)(tempsMs % 1000);

        return minutes + " min " + secondes + " s " + millisecondes + " ms";
    }
}

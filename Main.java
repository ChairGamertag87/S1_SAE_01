import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // === FONCTION PRINCIPALE ===
    public static void main(String[] args) {
        System.out.println(formatTemps(83456));

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

    // === SAISIE DES INFORMATIONS DE LA COURSE ===
    public static int saisirNombreCompetiteurs() { return 0; }

    public static double saisirLongueurPiste() { return 0; }

    public static int saisirNombreObstacles() { return 0; }

    public static int saisirNombreBarres(int nbObstacles) { return 0; }

    // === SAISIE DES RÉSULTATS D’UNE MANCHE ===
    public static void saisirManche(
            int nbCompetiteurs,
            int nbBarresMax,
            double longueurPiste,
            int[] nbBarres,
			boolean barreTombe,
            int[] nbRefus,
			boolean refus,
            boolean[] chute,
            long[] tempsMs,
            boolean[] elimine,
            double[] tempsCompense
    ) { }

    // === SAISIES UNITAIRES ===
    public static int saisirNbBarresTombees(int nbBarresMax) { 
		if barreTombe{
			nbBarres[joueur] += 1;
			barreTombe = false;
		}
		if (nbBarres[joueur]>nbBarresMax){
			System.out.println("Toutes les barres sont tombées!");
		}
		return nbBarres[joueur]; 
	}

    public static int saisirNbRefus(int[]nbRefus) { 
		if refus{
			nbRefus[joueur]+=1;
			refus=false;
		}	
		return nbRefus[joueur]; 
	}

    public static boolean elimine(nbRefus[], chute, longueurPiste) { 
		if (nbRefus[joueur]>3){
			elimine=true;
			return elimine;
		}
		if chute{
			elimine=true;
			return  elimine;
		}
		if (longueurPiste<600){
			if(tempMs>120000){
				elimine=true;
			}else{
				if(tempMs>180000){
					elimine=true;
				}
			}
		return elimine; 
	}

    public static long saisirTempsMs() { 
		return 0; 
	}

    // === CALCULS ===
    public static boolean estElimine(int nbRefus, boolean chute, long tempsMs, double longueurPiste) { return false; }



    public static double calculerTempsCompense(long tempsMs, int nbBarres) {
        for  (int i = 0; i < nbBarres; i++) {
            tempsMs += tempsMs + 8000;
        }
        return tempsMs;
    }

    // === AFFICHAGES ===
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

    public static String formatTemps(double tempsMs) {
        int totalSecondes = (int)(tempsMs / 1000);

        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        int millisecondes = (int)(tempsMs % 1000);

        return minutes + " min " + secondes + " s " + millisecondes + " ms";
    }
}

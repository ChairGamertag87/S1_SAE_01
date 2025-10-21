import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // === FONCTION PRINCIPALE ===
    public static void main(String[] args) {
        // Point d’entrée du programme
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

    public static double calculerTempsCompense(long tempsMs, int nbBarres) { return 0; }

    // === AFFICHAGES ===
    public static void afficherResultats(
            int nbCompetiteurs,
            boolean[] elimine,
            double[] tempsCompense
    ) { }

    public static void afficherPodium(
            int nbCompetiteurs,
            boolean[] elimine,
            double[] tempsCompense
    ) { }

    // === OUTIL : FORMATAGE DU TEMPS ===
    public static String formatTemps(double tempsMs) { return ""; }
}

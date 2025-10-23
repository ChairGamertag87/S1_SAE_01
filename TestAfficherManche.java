public class TestAfficherManche {
    public static void main(String[] args) {
        int nbCompetiteurs = 2;
        int nbBarresMax = 12;
        double longueurPiste = 550.0;
        int[] nbBarres = {2, 0};
        int[] nbRefus  = {1, 0};
        int[] tempsMs  = {90_000, 0};

        Main.afficherManche(nbCompetiteurs, nbBarresMax, longueurPiste, nbBarres, true, nbRefus, true, false, tempsMs, false, 1, 1);
    }
}

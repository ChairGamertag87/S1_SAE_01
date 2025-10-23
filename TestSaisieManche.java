public class TestSaisieManche {
    public static void main(String[] args) {
        int n = 1;
        int[] nbBarres = new int[n];
        int[] nbRefus  = new int[n];
        int[] tempsMs  = new int[n];
        boolean[] chute = new boolean[n];
        boolean[] elimine = new boolean[n];

        System.out.println("Répondez dans l'ordre : barres, refus, (oui/non), temps (ms)");
        Main.saisieManche(0, nbBarres, nbRefus, tempsMs, chute, elimine, 12, 550.0);

        System.out.println("Barres = " + nbBarres[0]);
        System.out.println("Refus = " + nbRefus[0]);
        System.out.println("Chute = " + chute[0]);
        System.out.println("Temps  = " + tempsMs[0]);
        System.out.println("Eliminé= " + elimine[0]);
    }
}

public class TestPodium {

    public static void main(String[] args) {
        System.out.println("\nTEST 1 : tous temps différents");
        boolean[] elimine1 = {false, false, false, false};
        double[] temps1 = {85000, 90000, 95000, 100000};
        Main.afficherPodium(4, elimine1, temps1);

        System.out.println("\nTEST 2 : égalité au 1er");
        boolean[] elimine2 = {false, false, false, false};
        double[] temps2 = {85000, 85000, 90000, 95000};
        Main.afficherPodium(4, elimine2, temps2);

        System.out.println("\nTEST 3 : égalité au 2e");
        boolean[] elimine3 = {false, false, false, false, false};
        double[] temps3 = {83000, 85000, 85000, 85000, 90000};
        Main.afficherPodium(5, elimine3, temps3);

        System.out.println("\nTEST 4 : égalité au 3e");
        boolean[] elimine4 = {false, false, false, false, false, false};
        double[] temps4 = {80000, 85000, 90000, 90000, 90000, 95000};
        Main.afficherPodium(6, elimine4, temps4);

        System.out.println("\nTEST 5 : 4 premiers ex aequo");
        boolean[] elimine5 = {false, false, false, false, false};
        double[] temps5 = {87000, 87000, 87000, 87000, 90000};
        Main.afficherPodium(5, elimine5, temps5);

        System.out.println("\nTEST 6 : avec éliminés");
        boolean[] elimine6 = {false, true, false, true, false};
        double[] temps6 = {85000, 86000, 83000, 87000, 88000};
        Main.afficherPodium(5, elimine6, temps6);

        System.out.println("\nTEST 7 : tout le monde éliminé");
        boolean[] elimine7 = {true, true, true};
        double[] temps7 = {85000, 86000, 87000};
        Main.afficherPodium(3, elimine7, temps7);
    }
}
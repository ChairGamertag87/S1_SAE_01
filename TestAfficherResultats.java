public class TestAfficherResultats {
    public static void main(String[] args) {
        boolean[] elimine = {false, true, false, false};
        double[] temps = {85_000, 0, 90_000, 87_500};
        Main.afficherResultats(4, elimine, temps);
    }
}

public class TestSaisirNbBarresTombees {
    public static void main(String[] args) {
        int[] nbBarres = {0, 0, 0};
        System.out.println(Main.saisirNbBarresTombees(3, nbBarres, true, 1)); // 1
        System.out.println(Main.saisirNbBarresTombees(3, nbBarres, true, 1)); // 2
        System.out.println(Main.saisirNbBarresTombees(3, nbBarres, true, 1)); // 3
        System.out.println(Main.saisirNbBarresTombees(3, nbBarres, true, 1)); // 4
    }
}

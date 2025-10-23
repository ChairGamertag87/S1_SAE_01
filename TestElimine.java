public class TestElimine {
    public static void main(String[] args) {
        System.out.println(Main.elimine(0, 600.0, 1, 119_999));  // false
        System.out.println(Main.elimine(0, 600.0, 1, 120_001));  // true
        System.out.println(Main.elimine(0, 650.0, 1, 179_999));  // false
        System.out.println(Main.elimine(0, 650.0, 1, 180_001));  // true
        System.out.println(Main.elimine(4, 500.0, 1, 0));        // true (>=3 refus)
    }
}

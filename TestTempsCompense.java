public class TestTempsCompense {
    public static void main(String[] args) {
        System.out.println(Main.tempsCompense(60_000, 0));  // 60000
        System.out.println(Main.tempsCompense(60_000, 1));  // 68000
        System.out.println(Main.tempsCompense(60_000, 3));  // 84000
        System.out.println(Main.tempsCompense(0, 5));       // 40000
    }
}

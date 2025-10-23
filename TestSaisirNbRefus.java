public class TestSaisirNbRefus {
    public static void main(String[] args) {
        int[] nbRefus = {0, 0};
        System.out.println(Main.saisirNbRefus(nbRefus, true, 0));   // 1
        System.out.println(Main.saisirNbRefus(nbRefus, false, 0));  // 1
        System.out.println(Main.saisirNbRefus(nbRefus, true, 0));   // 2
    }
}

import java.util.Scanner;

public class TestSaisieEntierBorne {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(Main.saisieEntierBorne(s, 1, 10, "Saisir (1..10): "));
        s.close();
    }
}

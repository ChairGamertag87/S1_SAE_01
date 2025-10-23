import java.util.Scanner;

public class TestLireEntier {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Entrez un entier: ");
        System.out.println(Main.saisieEntierBorne(s, Integer.MIN_VALUE, Integer.MAX_VALUE, ""));
        s.close();
    }
}

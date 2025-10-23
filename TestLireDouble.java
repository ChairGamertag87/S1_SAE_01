import java.util.Scanner;

public class TestLireDouble {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Entrez un réel: ");
        System.out.println(Main.saisieReelMinimum(s, -Double.MAX_VALUE, ""));
        s.close();
    }
}

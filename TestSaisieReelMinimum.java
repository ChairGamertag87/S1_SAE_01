import java.util.Scanner;

public class TestSaisieReelMinimum {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(Main.saisieReelMinimum(s, 0.0, "Saisir (>0): "));
        s.close();
    }
}

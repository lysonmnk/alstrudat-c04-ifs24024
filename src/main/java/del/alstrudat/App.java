package del.alstrudat;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Ini adalah entry point yang dipanggil oleh TestScript.sh
        Scanner scanner = new Scanner(System.in);
        Program.solve(scanner);
        scanner.close();
    }
}
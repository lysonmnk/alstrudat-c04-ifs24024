
package del.alstrudat;

import java.util.Scanner;

/**
 * Test.java — Local runner for "Expedition to the Ancient Network"
 *
 * This class feeds a sample input directly into Program.solve()
 * so you can test your solution without needing GitHub Actions.
 *
 * Expected output: Minimum cost: 12
 */
public class Test {

    public static void main(String[] args) {

        // -------------------------------------------------------
        // Sample input (matches example in README.txt):
        //
        //   6 cities, 8 roads, 2 cursed cities
        //   Cursed: city 3 penalty=15, city 5 penalty=10
        //   Edges: see README
        //   Source=1, Dest=6
        //
        // Optimal: portal at city 1 -> 1->2 free (0)
        //          -> 2->4 (3) -> 4->5 (2) -> 5->6 (7)
        //          curse at 5 skipped (portal already used)
        //          Total = 0 + 3 + 2 + 7 = 12
        // -------------------------------------------------------

        String input =
            "6 8 2\n" +
            "3 15 5 10\n" +
            "1 2 10\n" +
            "1 3 5\n" +
            "3 2 4\n" +
            "3 4 8\n" +
            "2 4 3\n" +
            "4 5 2\n" +
            "5 6 7\n" +
            "2 6 30\n" +
            "1 6\n";

        System.out.println("=== INPUT ===");
        System.out.print(input);
        System.out.println("=== OUTPUT ===");

        Scanner scanner = new Scanner(input);
        Program.solve(scanner);
        scanner.close();

        System.out.println("=============");
        System.out.println("Expected: Minimum cost: 12");

        // -------------------------------------------------------
        // Additional test: No path exists
        // -------------------------------------------------------
        System.out.println("\n=== TEST 2 (no path) ===");

        String input2 =
            "4 2 0\n" +
            "1 2 5\n" +
            "3 4 3\n" +
            "1 4\n";

        System.out.println("INPUT:");
        System.out.print(input2);
        System.out.println("OUTPUT:");

        Scanner scanner2 = new Scanner(input2);
        Program.solve(scanner2);
        scanner2.close();

        System.out.println("Expected: Minimum cost: -1");

        // -------------------------------------------------------
        // Additional test: Single cursed city
        // -------------------------------------------------------
        System.out.println("\n=== TEST 3 (curse + portal interaction) ===");

        String input3 =
            "3 2 1\n" +
            "2 100\n" +
            "1 2 10\n" +
            "2 3 50\n" +
            "1 3\n";

        System.out.println("INPUT:");
        System.out.print(input3);
        System.out.println("OUTPUT:");

        Scanner scanner3 = new Scanner(input3);
        Program.solve(scanner3);
        scanner3.close();

        System.out.println("Expected corrected: Minimum cost: 50");
    }
}


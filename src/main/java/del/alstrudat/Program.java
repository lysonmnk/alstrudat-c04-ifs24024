package del.alstrudat;

import java.util.*;

/**
 * ALSTRUDAT Circle Battle — Expedition to the Ancient Network
 *
 * PROBLEM SUMMARY:
 * Find the minimum cost path from S to T in a directed weighted graph.
 * You may use a "Time Warp Portal" at most ONCE: activating it at city U
 * makes the next outgoing edge from U cost 0.
 * Cursed cities add a penalty when you leave them, BUT only if you have
 * not yet used the portal (penalty is applied before deciding portal use).
 *
 * INPUT  : Read from System.in via the provided Scanner.
 * OUTPUT : Print exactly "Minimum cost: X" where X is the answer,
 *          or "Minimum cost: -1" if no path exists.
 */
public class Program {

    /**
     * Solves the Expedition to the Ancient Network problem.
     *
     * @param scanner A Scanner connected to standard input.
     *
     * Input format:
     *   Line 1 : N M K
     *   Line 2 : c1 p1 c2 p2 ... cK pK   (omitted if K == 0)
     *   Next M : u v w
     *   Last   : S T
     *
     * Output (print to System.out):
     *   "Minimum cost: X"  or  "Minimum cost: -1"
     */
    public static void solve(Scanner scanner) {

        // -------------------------------------------------------
        // Step 1: Read input
        // -------------------------------------------------------
        int N = scanner.nextInt();   // number of cities
        int M = scanner.nextInt();   // number of directed roads
        int K = scanner.nextInt();   // number of cursed cities

        // curse[city] = extra penalty paid when leaving that city
        // (only if portal has not been used yet)
        long[] curse = new long[N + 1];

        for (int i = 0; i < K; i++) {
            int city    = scanner.nextInt();
            long penalty = scanner.nextLong();
            curse[city] = penalty;
        }

        // Adjacency list: adj.get(u) = list of int[]{v, w}
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            adj.get(u).add(new int[]{v, w});
        }

        int S = scanner.nextInt();
        int T = scanner.nextInt();

        // -------------------------------------------------------
        // Step 2: Implement the algorithm
        //
        // TODO: Implement a modified Dijkstra algorithm that
        //       tracks whether the Time Warp Portal has been used.
        //
        // State: (city, portalUsed)  where portalUsed ∈ {0, 1}
        //
        // Transition from state (u, portalUsed):
        //   For each edge (u -> v, cost w):
        //
        //   effectiveCost = w + (portalUsed == 0 ? curse[u] : 0)
        //   // curse is added to the LEAVING cost if portal not yet used
        //
        //   Option A — do NOT use portal:
        //     new state : (v, portalUsed)
        //     added cost: effectiveCost
        //
        //   Option B — USE portal at u (only if portalUsed == 0):
        //     The road cost becomes 0, but curse[u] still applies.
        //     effectivePortalCost = 0 + (curse[u])   // road is free
        //     new state : (v, 1)
        //     added cost: effectivePortalCost
        //
        // Use dist[city][portalUsed] to track minimum costs.
        // Use a PriorityQueue<long[]> sorted by cost (min-heap).
        // Answer = min(dist[T][0], dist[T][1])
        // -------------------------------------------------------

        // TODO: Remove this placeholder and write your solution.
        System.out.println("Minimum cost: -10");
    }
}

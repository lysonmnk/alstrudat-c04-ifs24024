package del.alstrudat;

import java.util.*;

public class Program {
    public static void solve(Scanner scanner) {
        if (!scanner.hasNextInt()) return;

        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();

        long[] curse = new long[N + 1];
        for (int i = 0; i < K; i++) {
            int city = scanner.nextInt();
            long penalty = scanner.nextLong();
            if (city >= 1 && city <= N) {
                curse[city] = penalty;
            }
        }

        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            // Validasi indeks
            if (u >= 1 && u <= N && v >= 1 && v <= N) {
                adj.get(u).add(new int[]{v, w});
            }
        }

        int S = scanner.nextInt();
        int T = scanner.nextInt();

        // Gunakan INF yang cukup besar
        long INF = Long.MAX_VALUE / 4; 
        long[][] dist = new long[N + 1][2];
        for (long[] row : dist) Arrays.fill(row, INF);
        
        dist[S][0] = 0L;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0L, S, 0L});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long d = top[0];
            int u = (int) top[1];
            int p = (int) top[2];

            if (d > dist[u][p]) continue;

            for (int[] edge : adj.get(u)) {
                int v = edge[0];
                int w = edge[1];

                // --- Opsi 1: Tetap di status p ---
                // Jika p == 0, kita membayar kutukan di kota u SEBELUM meninggalkan u
                long penalty = (p == 0) ? curse[u] : 0L;
                if (dist[u][p] + w + penalty < dist[v][p]) {
                    dist[v][p] = dist[u][p] + w + penalty;
                    pq.offer(new long[]{dist[v][p], v, p});
                }

                // --- Opsi 2: Pakai Portal di kota u (hanya jika p == 0) ---
                if (p == 0) {
                    // Pakai portal: biaya jalan 0, kutukan tetap kena
                    if (dist[u][0] + penalty < dist[v][1]) {
                        dist[v][1] = dist[u][0] + penalty;
                        pq.offer(new long[]{dist[v][1], v, 1});
                    }
                }
            }
        }

        long ans = Math.min(dist[T][0], dist[T][1]);
        if (ans >= INF) {
            System.out.println("Minimum cost: -1");
        } else {
            System.out.println("Minimum cost: " + ans);
        }
    }
}
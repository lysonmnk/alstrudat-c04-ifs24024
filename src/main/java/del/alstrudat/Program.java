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
            if (city >= 0 && city <= N) {
                curse[city] = penalty;
            }
        }

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

        // Menggunakan nilai INF yang cukup besar tapi aman dari overflow
        final long INF = 1_000_000_000_000_000L; 
        long[][] dist = new long[N + 1][2];
        for (long[] row : dist) Arrays.fill(row, INF);

        // PriorityQueue: {cost, city, portalUsed}
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        dist[S][0] = 0L;
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

                // --- OPSI 1: TANPA PORTAL (p tetap) ---
                // Kutukan di kota u dibayar saat meninggalkan u jika portal belum dipakai
                long penalty = (p == 0) ? curse[u] : 0L;
                long costNormal = d + w + penalty;
                
                if (costNormal < dist[v][p]) {
                    dist[v][p] = costNormal;
                    pq.offer(new long[]{dist[v][p], v, p});
                }

                // --- OPSI 2: PAKAI PORTAL DI KOTA u (Hanya jika p == 0) ---
                // Sesuai aturan: biaya kutukan dibayar SEBELUM portal dipakai
                if (p == 0) {
                    long costPortal = d + penalty; // Jalan jadi 0, tapi kutukan tetap bayar
                    if (costPortal < dist[v][1]) {
                        dist[v][1] = costPortal;
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
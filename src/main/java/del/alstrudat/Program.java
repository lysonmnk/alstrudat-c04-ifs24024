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
            curse[city] = penalty;
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

        final long INF = Long.MAX_VALUE / 2;
        long[][] dist = new long[N + 1][2];
        for (long[] row : dist) Arrays.fill(row, INF);
        
        dist[S][0] = 0L;
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0L, S, 0L});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long cost = top[0];
            int u = (int) top[1];
            int portal = (int) top[2];

            if (cost > dist[u][portal]) continue;

            // JANGAN PAKAI BREAK di sini agar kedua status dist[T] terhitung semua

            for (int[] edge : adj.get(u)) {
                int v = edge[0];
                long w = edge[1];

                // --- Opsi 1: Tidak menggunakan portal (atau tetap tidak pakai) ---
                // Kutukan di kota u dibayar saat meninggalkan u
                long penalty = (portal == 0) ? curse[u] : 0L;
                long newCost = cost + w + penalty;
                
                if (newCost < dist[v][portal]) {
                    dist[v][portal] = newCost;
                    pq.offer(new long[]{newCost, v, portal});
                }

                // --- Opsi 2: Gunakan portal di kota u (hanya jika portal == 0) ---
                // Kutukan di kota u tetap dibayar sebelum portal aktif, jalan jadi 0
                if (portal == 0) {
                    long portalCost = cost + penalty; // road 0, tapi kutukan tetap kena
                    if (portalCost < dist[v][1]) {
                        dist[v][1] = portalCost;
                        pq.offer(new long[]{portalCost, v, 1L});
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
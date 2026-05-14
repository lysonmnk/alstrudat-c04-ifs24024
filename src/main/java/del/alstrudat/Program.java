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
            adj.get(u).add(new int[]{v, w});
        }

        int S = scanner.nextInt();
        int T = scanner.nextInt();

        // dist[kota][portalUsed]
        long[][] dist = new long[N + 1][2];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE);

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        dist[S][0] = 0;
        pq.add(new long[]{0, S, 0});

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            long d = current[0];
            int u = (int) current[1];
            int p = (int) current[2];

            if (d > dist[u][p]) continue;

            for (int[] edge : adj.get(u)) {
                int v = edge[0];
                int w = edge[1];

                // Opsi A: Tidak menggunakan portal (atau portal sudah dipakai sebelumnya)
                // Jika p == 0, kita masih kena kutukan di kota u saat meninggalkannya.
                // Jika p == 1, kita sudah kebal kutukan.
                long costNormal = d + w + (p == 0 ? curse[u] : 0);
                if (costNormal < dist[v][p]) {
                    dist[v][p] = costNormal;
                    pq.add(new long[]{dist[v][p], v, p});
                }

                // Opsi B: Aktifkan portal di kota u (hanya jika p == 0)
                // Biaya perjalanan menjadi 0, dan kutukan di u tidak berlaku 
                // karena portal digunakan untuk "time warp" keluar dari kota tersebut.
                if (p == 0) {
                    long costPortal = d + 0; 
                    if (costPortal < dist[v][1]) {
                        dist[v][1] = costPortal;
                        pq.add(new long[]{dist[v][1], v, 1});
                    }
                }
            }
        }

        long ans = Math.min(dist[T][0], dist[T][1]);
        if (ans >= Long.MAX_VALUE / 2) {
            System.out.println("Minimum cost: -1");
        } else {
            System.out.println("Minimum cost: " + ans);
        }
    }
}
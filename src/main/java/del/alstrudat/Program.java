package del.alstrudat;

import java.util.*;

/**
 * Solusi Lengkap: Expedition to the Ancient Network
 * Menggunakan Dijkstra dengan 2 State (city, portalUsed)
 */
public class Program {

    public static void solve(Scanner scanner) {
        // Membaca N, M, K
        if (!scanner.hasNextInt()) return;
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();

        // Menyimpan penalty kutukan
        long[] curse = new long[N + 1];
        for (int i = 0; i < K; i++) {
            int city = scanner.nextInt();
            long penalty = scanner.nextLong();
            if (city >= 1 && city <= N) {
                curse[city] = penalty;
            }
        }

        // Membangun Adjacency List
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            adj.get(u).add(new int[]{v, w});
        }

        int S = scanner.nextInt();
        int T = scanner.nextInt();

        // Dijkstra State: dist[city][portalUsed]
        // portalUsed: 0 = belum pakai, 1 = sudah pakai
        final long INF = Long.MAX_VALUE / 4;
        long[][] dist = new long[N + 1][2];
        for (long[] row : dist) Arrays.fill(row, INF);

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        // Inisialisasi awal
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

                // --- Opsi 1: Bergerak tanpa menggunakan portal ---
                // Jika p=0, kita terkena kutukan kota u saat meninggalkannya
                long penalty = (p == 0) ? curse[u] : 0L;
                if (d + w + penalty < dist[v][p]) {
                    dist[v][p] = d + w + penalty;
                    pq.offer(new long[]{dist[v][p], v, p});
                }

                // --- Opsi 2: Mengaktifkan portal di kota u (hanya jika p=0) ---
                // Sesuai aturan: Kutukan tetap dibayar sebelum portal aktif (w = 0)
                if (p == 0) {
                    if (d + penalty < dist[v][1]) {
                        dist[v][1] = d + penalty;
                        pq.offer(new long[]{dist[v][1], v, 1});
                    }
                }
            }
        }

        // Output hasil akhir
        long ans = Math.min(dist[T][0], dist[T][1]);
        if (ans >= INF) {
            System.out.println("Minimum cost: -1");
        } else {
            System.out.println("Minimum cost: " + ans);
        }
    }
}
class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, -1);

        Deque<int[]> bfsQueue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    bfsQueue.offer(new int[]{i, j});
                }
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // multi source bfs to compute distance to nearest thief
        while (!bfsQueue.isEmpty()) {
            int[] cur = bfsQueue.poll();
            int r = cur[0], c = cur[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    bfsQueue.offer(new int[]{nr, nc});
                }
            }
        }

        // Dijkstra like widest path that maximize the minimum dist value on the path
        int[][] best = new int[n][n];
        for (int[] row : best) Arrays.fill(row, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]); 
        // max-heap by safeness
        best[0][0] = dist[0][0];
        pq.offer(new int[]{dist[0][0], 0, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int safeness = cur[0], r = cur[1], c = cur[2];

            if (safeness < best[r][c]) continue; // stale entry

            if (r == n - 1 && c == n - 1) {
                return safeness;
            }

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int candidate = Math.min(safeness, dist[nr][nc]);
                    if (candidate > best[nr][nc]) {
                        best[nr][nc] = candidate;
                        pq.offer(new int[]{candidate, nr, nc});
                    }
                }
            }
        }

        return best[n - 1][n - 1];
    }
}
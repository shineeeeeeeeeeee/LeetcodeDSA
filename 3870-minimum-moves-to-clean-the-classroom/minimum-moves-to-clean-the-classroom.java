class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;
        int litterCount = 0;

        int[][] litter = new int[10][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);

                if (c == 'S') {
                    sr = i;
                    sc = j;
                } else if (c == 'L') {
                    litter[litterCount][0] = i;
                    litter[litterCount][1] = j;
                    litterCount++;
                }
            }
        }

        if (litterCount == 0) {
            return 0;
        }

        int totalMasks = 1 << litterCount;

        int[][][] dist = new int[m][n][totalMasks];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                java.util.Arrays.fill(dist[i][j], -1);
            }
        }

        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();

        int startMask = 0;

        for (int i = 0; i < litterCount; i++) {
            if (litter[i][0] == sr && litter[i][1] == sc) {
                startMask |= 1 << i;
            }
        }

        dist[sr][sc][startMask] = energy;
        queue.offer(new int[]{sr, sc, startMask, energy, 0});

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            int r = cur[0];
            int c = cur[1];
            int mask = cur[2];
            int e = cur[3];
            int moves = cur[4];

            if (mask == totalMasks - 1) {
                return moves;
            }

            if (e == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                int newEnergy = e - 1;
                int newMask = mask;

                for (int i = 0; i < litterCount; i++) {
                    if (litter[i][0] == nr && litter[i][1] == nc) {
                        newMask |= 1 << i;
                    }
                }

                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (newEnergy > dist[nr][nc][newMask]) {
                    dist[nr][nc][newMask] = newEnergy;
                    queue.offer(new int[]{
                        nr, nc, newMask, newEnergy, moves + 1
                    });
                }
            }
        }

        return -1;
    }
}
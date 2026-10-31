class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long r = right[i][j];
                long d = down[i][j];

                if (grid[i][j] == 1) {
                    if (i + 1 < m) {
                        down[i + 1][j] = (int) ((down[i + 1][j] + r) % MOD);
                    }

                    if (j + 1 < n) {
                        right[i][j + 1] = (int) ((right[i][j + 1] + d) % MOD);
                    }
                } else {
                    long total = (r + d) % MOD;

                    if (j + 1 < n) {
                        right[i][j + 1] = (int) ((right[i][j + 1] + total) % MOD);
                    }

                    if (i + 1 < m) {
                        down[i + 1][j] = (int) ((down[i + 1][j] + total) % MOD);
                    }
                }
            }
        }

        return (int) ((right[m - 1][n - 1] + (long) down[m - 1][n - 1]) % MOD);
    }
}
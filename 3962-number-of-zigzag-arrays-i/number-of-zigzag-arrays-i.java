class Solution {
    static final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        long[][] up = new long[n + 1][m];
        long[][] down = new long[n + 1][m];

        for (int i = 0; i < m; i++) {
            up[1][i] = 1;
            down[1][i] = 1;
        }

        for (int len = 2; len <= n; len++) {
            long prefix = 0;
            for (int i = 0; i < m; i++) {
                up[len][i] = prefix;
                prefix = (prefix + down[len - 1][i]) % MOD;
            }

            long suffix = 0;
            for (int i = m - 1; i >= 0; i--) {
                down[len][i] = suffix;
                suffix = (suffix + up[len - 1][i]) % MOD;
            }
        }

        long ans = 0;
        if (n == 1) {
            ans = m % MOD; // each single element counts once, not twice
        } else {
            for (int i = 0; i < m; i++) {
                ans = (ans + up[n][i] + down[n][i]) % MOD;
            }
        }

        return (int) ans;
    }
}
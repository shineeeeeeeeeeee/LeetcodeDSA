class Solution {
    int n;
    int[][] dp;
    int[] suffix;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        dp = new int[n][n + 1];

        return solve(0, 1);
    }

    private int solve(int i, int M) {
        // all remaining stones can be taken
        if (i >= n) {
            return 0;
        }

        if (2 * M >= n - i) {
            return suffix[i];
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        int best = 0;

        // try taking X piles, where 1 <= X <= 2M
        for (int X = 1; X <= 2 * M && i + X <= n; X++) {

            // if i take X piles, opponent gets the best
            // possible result from the remaining piles.
            int opponent = solve(i + X, Math.max(M, X));

            // total remaining stones - opponent's stones
            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        return dp[i][M] = best;
    }
}
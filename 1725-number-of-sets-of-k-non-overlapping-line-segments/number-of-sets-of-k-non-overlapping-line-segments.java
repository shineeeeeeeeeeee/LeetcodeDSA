class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;
        long[][] dp = new long[k + 1][n];

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int j = 1; j <= k; j++) {
            long sum = 0;
            for (int i = 1; i < n; i++) {
                sum = (sum + dp[j - 1][i - 1]) % MOD;
                dp[j][i] = (dp[j][i - 1] + sum) % MOD;
            }
        }

        return (int) dp[k][n - 1];
    }
}
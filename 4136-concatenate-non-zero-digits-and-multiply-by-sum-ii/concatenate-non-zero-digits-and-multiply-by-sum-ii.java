class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        final int MOD = 1_000_000_007;
        int n = s.length();

        int[] pos = new int[n];       // original index of each nonzero digit
        long[] P = new long[n + 1];   // prefix concatenation mod MOD
        long[] S = new long[n + 1];   // prefix digit sum
        long[] pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) pow10[i] = (pow10[i - 1] * 10) % MOD;

        int m = 0;
        for (int j = 0; j < n; j++) {
            int d = s.charAt(j) - '0';
            if (d != 0) {
                pos[m] = j;
                P[m + 1] = (P[m] * 10 + d) % MOD;
                S[m + 1] = S[m] + d;
                m++;
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];

            int start = lowerBound(pos, m, l);       // first pos >= l
            int end = upperBound(pos, m, r) - 1;      // last pos <= r

            if (start > end) {
                ans[i] = 0;
                continue;
            }

            int a = start + 1, b = end + 1; // 1-indexed into P/S
            long x = (P[b] - P[a - 1] * pow10[b - a + 1]) % MOD;
            if (x < 0) x += MOD;

            long sum = S[b] - S[a - 1];

            ans[i] = (int) ((x * (sum % MOD)) % MOD);
        }

        return ans;
    }

    private int lowerBound(int[] arr, int len, int target) {
        int lo = 0, hi = len;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    private int upperBound(int[] arr, int len, int target) {
        int lo = 0, hi = len;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= target) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
}
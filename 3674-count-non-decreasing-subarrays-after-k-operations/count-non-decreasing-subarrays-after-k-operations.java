class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int log = 1;
        while ((1 << log) <= n) log++;

        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Find the next strictly greater element for every index.
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? n : stack[top];
            stack[++top] = i;
        }

        // Binary lifting over next-greater links.
        int[][] up = new int[log][n + 1];
        long[][] cost = new long[log][n + 1];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
            cost[0][i] = (long) nums[i] * (next[i] - i)
                       - (prefix[next[i]] - prefix[i]);
        }

        up[0][n] = n;

        for (int j = 1; j < log; j++) {
            for (int i = 0; i <= n; i++) {
                int mid = up[j - 1][i];
                up[j][i] = up[j - 1][mid];
                cost[j][i] = cost[j - 1][i] + cost[j - 1][mid];
            }
        }

        long ans = 0;
        int right = -1;

        for (int left = 0; left < n; left++) {
            right = Math.max(right, left - 1);

            while (right + 1 < n &&
                   getCost(left, right + 1, nums, prefix, up, cost, log) <= k) {
                right++;
            }

            ans += right - left + 1;
        }

        return ans;
    }

    private long getCost(
        int left, int right,
        int[] nums, long[] prefix,
        int[][] up, long[][] cost, int log
    ) {
        long result = 0;
        int pos = left;

        for (int j = log - 1; j >= 0; j--) {
            int nextPos = up[j][pos];

            if (nextPos <= right) {
                result += cost[j][pos];
                pos = nextPos;
            }
        }

        result += (long) nums[pos] * (right - pos + 1)
                - (prefix[right + 1] - prefix[pos]);

        return result;
    }
}
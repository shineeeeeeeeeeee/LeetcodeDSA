class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long INF = Long.MAX_VALUE / 4;

        long[] cost = new long[n - x + 1];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0, rightSum = 0;
        int leftSize = 0, rightSize = 0;

        for (int i = 0; i < x; i++) {
            right.merge(nums[i], 1, Integer::sum);
            rightSum += nums[i];
            rightSize++;
        }

        for (int start = 0; start <= n - x; start++) {
            while (leftSize < (x + 1) / 2) {
                int v = right.firstKey();
                remove(right, v);
                rightSum -= v;
                rightSize--;

                left.merge(v, 1, Integer::sum);
                leftSum += v;
                leftSize++;
            }

            while (leftSize > (x + 1) / 2) {
                int v = left.lastKey();
                remove(left, v);
                leftSum -= v;
                leftSize--;

                right.merge(v, 1, Integer::sum);
                rightSum += v;
                rightSize++;
            }

            long median = left.lastKey();
            cost[start] = median * leftSize - leftSum
                    + rightSum - median * rightSize;

            if (start == n - x) break;

            int out = nums[start];
            if (left.containsKey(out)) {
                remove(left, out);
                leftSum -= out;
                leftSize--;
            } else {
                remove(right, out);
                rightSum -= out;
                rightSize--;
            }

            int in = nums[start + x];
            if (!left.isEmpty() && in <= left.lastKey()) {
                left.merge(in, 1, Integer::sum);
                leftSum += in;
                leftSize++;
            } else {
                right.merge(in, 1, Integer::sum);
                rightSum += in;
                rightSize++;
            }
        }

        long[][] dp = new long[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }

        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {
            for (int i = j * x; i <= n; i++) {
                dp[j][i] = Math.min(
                    dp[j][i - 1],
                    dp[j - 1][i - x] + cost[i - x]
                );
            }
        }

        return dp[k][n];
    }

    private void remove(TreeMap<Integer, Integer> map, int v) {
        int count = map.get(v);
        if (count == 1) {
            map.remove(v);
        } else {
            map.put(v, count - 1);
        }
    }
}
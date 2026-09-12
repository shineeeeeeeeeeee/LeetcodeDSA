class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Integer[] idx = new Integer[n];

        for (int i = 0; i < n; i++) idx[i] = i;

        Arrays.sort(idx, (a, b) -> {
            int c = Integer.compare(intervals.get(a).get(0), intervals.get(b).get(0));
            if (c != 0) return c;
            c = Integer.compare(intervals.get(a).get(1), intervals.get(b).get(1));
            if (c != 0) return c;
            return Integer.compare(a, b);
        });

        long[][] dp = new long[n + 1][5];
        List<Integer>[][] best = new ArrayList[n + 1][5];

        long NEG = Long.MIN_VALUE / 4;

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], NEG);
            dp[i][0] = 0;
            for (int k = 0; k <= 4; k++) {
                best[i][k] = new ArrayList<>();
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int id = idx[i];
            int r = intervals.get(id).get(1);
            long w = intervals.get(id).get(2);

            int lo = i + 1, hi = n;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (intervals.get(idx[mid]).get(0) > r)
                    hi = mid;
                else
                    lo = mid + 1;
            }

            int next = lo;

            for (int k = 0; k <= 4; k++) {
                dp[i][k] = dp[i + 1][k];
                best[i][k] = new ArrayList<>(best[i + 1][k]);

                if (k > 0 && dp[next][k - 1] != NEG) {
                    long take = w + dp[next][k - 1];

                    List<Integer> candidate = new ArrayList<>();
                    candidate.add(id);
                    candidate.addAll(best[next][k - 1]);
                    Collections.sort(candidate);

                    if (take > dp[i][k] ||
                        (take == dp[i][k] && compare(candidate, best[i][k]) < 0)) {
                        dp[i][k] = take;
                        best[i][k] = candidate;
                    }
                }
            }
        }

        int bestK = 0;

        for (int k = 1; k <= 4; k++) {
            if (dp[0][k] > dp[0][bestK] ||
                (dp[0][k] == dp[0][bestK] &&
                 compare(best[0][k], best[0][bestK]) < 0)) {
                bestK = k;
            }
        }

        int[] ans = new int[best[0][bestK].size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = best[0][bestK].get(i);
        }

        return ans;
    }

    private int compare(List<Integer> a, List<Integer> b) {
        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }
}
class Solution {
    static final int MOD = 1_000_000_007;

    long[] sumTree, squareTree, lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sumTree = new long[4 * n];
        squareTree = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;
            update(1, 0, n - 1, left, i, 1);

            ans = (ans + querySquare(1, 0, n - 1, 0, i)) % MOD;
            last[nums[i]] = i;
        }

        return (int) ans;
    }

    private void apply(int node, int start, int end, long val) {
        long len = end - start + 1;
        squareTree[node] = (squareTree[node]
                + 2 * val % MOD * sumTree[node] % MOD
                + val * val % MOD * len % MOD) % MOD;

        sumTree[node] = (sumTree[node] + val * len) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }

    private void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) return;

        int mid = (start + end) / 2;
        apply(node * 2, start, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, end, lazy[node]);
        lazy[node] = 0;
    }

    private void update(int node, int start, int end, int l, int r, long val) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            apply(node, start, end, val);
            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;
        update(node * 2, start, mid, l, r, val);
        update(node * 2 + 1, mid + 1, end, l, r, val);

        sumTree[node] = (sumTree[node * 2] + sumTree[node * 2 + 1]) % MOD;
        squareTree[node] = (squareTree[node * 2] + squareTree[node * 2 + 1]) % MOD;
    }

    private long querySquare(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return 0;

        if (l <= start && end <= r) {
            return squareTree[node];
        }

        push(node, start, end);

        int mid = (start + end) / 2;
        return (querySquare(node * 2, start, mid, l, r)
                + querySquare(node * 2 + 1, mid + 1, end, l, r)) % MOD;
    }
}
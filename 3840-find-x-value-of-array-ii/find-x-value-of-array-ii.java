class Solution {
    int n, k;
    int[][] cnt;
    int[] prod;
    int[] nums;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.nums = nums;

        cnt = new int[4 * n][k];
        prod = new int[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            nums[index] = value;
            update(1, 0, n - 1, index);

            int[] res = query(1, 0, n - 1, start, n - 1);
            ans[i] = res[x];
        }

        return ans;
    }

    void build(int node, int l, int r) {
        if (l == r) {
            int x = nums[l] % k;
            prod[node] = x;
            cnt[node][x] = 1;
            return;
        }

        int mid = (l + r) >>> 1;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        merge(node, node * 2, node * 2 + 1);
    }

    void update(int node, int l, int r, int index) {
        if (l == r) {
            Arrays.fill(cnt[node], 0);

            int x = nums[index] % k;
            prod[node] = x;
            cnt[node][x] = 1;
            return;
        }

        int mid = (l + r) >>> 1;

        if (index <= mid) {
            update(node * 2, l, mid, index);
        } else {
            update(node * 2 + 1, mid + 1, r, index);
        }

        merge(node, node * 2, node * 2 + 1);
    }

    void merge(int node, int left, int right) {
        Arrays.fill(cnt[node], 0);

        prod[node] = (int) ((long) prod[left] * prod[right] % k);

        for (int r = 0; r < k; r++) {
            cnt[node][r] += cnt[left][r];

            int nr = (int) ((long) prod[left] * r % k);
            cnt[node][nr] += cnt[right][r];
        }
    }

    int[] query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return cnt[node].clone();
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        int[] left = query(node * 2, l, mid, ql, qr);
        int[] right = query(node * 2 + 1, mid + 1, r, ql, qr);

        int[] res = new int[k];

        int leftProd = getProduct(node * 2, l, mid, ql, qr);

        for (int i = 0; i < k; i++) {
            res[i] += left[i];

            int nr = (int) ((long) leftProd * i % k);
            res[nr] += right[i];
        }

        return res;
    }

    int getProduct(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return prod[node];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return getProduct(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return getProduct(node * 2 + 1, mid + 1, r, ql, qr);
        }

        int a = getProduct(node * 2, l, mid, ql, qr);
        int b = getProduct(node * 2 + 1, mid + 1, r, ql, qr);

        return (int) ((long) a * b % k);
    }
}
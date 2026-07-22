class Solution {
    static class ST {
        int n;
        int[] st;

        ST(int[] pairs) {
            this.n = pairs.length;
            this.st = new int[4 * n];
            build(pairs, 1, 0, n - 1);
        }

        int build(int[] pairs, int node, int l, int r) {
            if (l == r) {
                st[node] = pairs[l];
                return st[node];
            }
            int mid = l + (r - l) / 2;
            int lc = build(pairs, node * 2, l, mid);
            int rc = build(pairs, node * 2 + 1, mid + 1, r);
            st[node] = Math.max(lc, rc);
            return st[node];
        }

        int query(int node, int l, int r, int ql, int qr) {
            if (l > qr || r < ql) return 0;
            if (ql <= l && r <= qr) return st[node];
            int mid = l + (r - l) / 2;
            int lc = query(node * 2, l, mid, ql, qr);
            int rc = query(node * 2 + 1, mid + 1, r, ql, qr);
            return Math.max(lc, rc);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int ones = 0;
        List<int[]> zeros = new ArrayList<>();

        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '0') {
                int j = i;
                while (j < n && s.charAt(j) == s.charAt(i)) j++;
                zeros.add(new int[]{i, j - 1}); // [l, r]
                i = j;
            } else {
                ones++;
                i++;
            }
        }

        List<Integer> res = new ArrayList<>();

        if (zeros.size() < 2) {
            for (int q = 0; q < queries.length; q++) res.add(ones);
            return res;
        }

        int m = zeros.size();
        int[] pairs = new int[m - 1];
        for (i = 1; i < m; i++) {
            int l1 = zeros.get(i - 1)[0], r1 = zeros.get(i - 1)[1];
            int l2 = zeros.get(i)[0], r2 = zeros.get(i)[1];
            pairs[i - 1] = (r1 - l1 + 1) + (r2 - l2 + 1);
        }
        ST st = new ST(pairs);

        int[] starts = new int[m];
        int[] ends = new int[m];
        for (i = 0; i < m; i++) {
            starts[i] = zeros.get(i)[0];
            ends[i] = zeros.get(i)[1];
        }

        for (int[] query : queries) {
            int l = query[0], r = query[1];

            int first = lowerBound(ends, l);
            int last = upperBound(starts, r) - 1;

            if (first >= last) {
                res.add(ones);
                continue;
            }

            int best = st.query(1, 0, st.n - 1, first + 1, last - 2);

            // touching left
            int prevl = Math.min(zeros.get(first)[1], r) - Math.max(zeros.get(first)[0], l) + 1;
            int nextl = Math.min(zeros.get(first + 1)[1], r) - Math.max(zeros.get(first + 1)[0], l) + 1;
            best = Math.max(best, prevl + nextl);

            // touching right
            prevl = Math.min(zeros.get(last - 1)[1], r) - Math.max(zeros.get(last - 1)[0], l) + 1;
            nextl = Math.min(zeros.get(last)[1], r) - Math.max(zeros.get(last)[0], l) + 1;
            best = Math.max(best, prevl + nextl);

            res.add(ones + best);
        }

        return res;
    }

    // equivalent to bisect.bisect_left
    private int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // equivalent to bisect.bisect_right
    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
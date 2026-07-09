class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + (nums[i] == target ? 1 : -1);
        }

        // coordinate compress all prefix values
        long[] sorted = prefix.clone();
        Arrays.sort(sorted);
        int m = 0;
        long[] unique = new long[n + 1];
        for (long v : sorted) {
            if (m == 0 || unique[m - 1] != v) {
                unique[m++] = v;
            }
        }

        Fenwick fenwick = new Fenwick(m);
        long result = 0;

        for (int j = 0; j <= n; j++) {
            int rank = lowerBound(unique, m, prefix[j]); // 0 indexed rank

            // count how many previously inserted prefix values are < prefix[j]
            if (rank > 0) {
                result += fenwick.query(rank); // sum of ranks [1..rank] in 1 indexed BIT
            }

            fenwick.update(rank + 1, 1); // insert this prefix value (1-indexed)
        }

        return result;
    }

    private int lowerBound(long[] arr, int len, long target) {
        int lo = 0, hi = len;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    static class Fenwick {
        int[] tree;
        int size;

        Fenwick(int size) {
            this.size = size;
            tree = new int[size + 1];
        }

        void update(int i, int delta) {
            for (; i <= size; i += i & (-i)) {
                tree[i] += delta;
            }
        }

        int query(int i) {
            int sum = 0;
            for (; i > 0; i -= i & (-i)) {
                sum += tree[i];
            }
            return sum;
        }
    }
}
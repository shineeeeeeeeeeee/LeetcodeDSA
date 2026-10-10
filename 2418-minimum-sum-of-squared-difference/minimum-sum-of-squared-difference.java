class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int[] diff = new int[nums1.length];
        int max = 0;

        for (int i = 0; i < nums1.length; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
        }

        long total = 0;
        for (int d : diff) {
            total += d;
        }

        if (total <= k) {
            return 0;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long remaining = k;

        for (int i = 0; i < diff.length; i++) {
            if (diff[i] > level) {
                remaining -= diff[i] - level;
                diff[i] = level;
            }
        }

        for (int i = 0; i < diff.length && remaining > 0; i++) {
            if (diff[i] == level && level > 0) {
                diff[i]--;
                remaining--;
            }
        }

        long ans = 0;
        for (int d : diff) {
            ans += (long) d * d;
        }

        return ans;
    }
}
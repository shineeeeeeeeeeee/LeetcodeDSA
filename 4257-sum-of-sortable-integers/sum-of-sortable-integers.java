class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;

                for (int i = start; i < start + k; i++) {
                    min = Math.min(min, nums[i]);
                    max = Math.max(max, nums[i]);
                }

                if (min != sorted[start] || max != sorted[start + k - 1]) {
                    valid = false;
                    break;
                }

                int pivot = -1;
                for (int i = start; i < start + k - 1; i++) {
                    if (nums[i] > nums[i + 1]) {
                        if (pivot != -1) {
                            valid = false;
                            break;
                        }
                        pivot = i;
                    }
                }

                if (valid && pivot != -1 && nums[start + k - 1] > nums[start]) {
                    valid = false;
                }

                if (valid) {
                    int[] block = Arrays.copyOfRange(nums, start, start + k);
                    Arrays.sort(block);

                    for (int i = 0; i < k; i++) {
                        if (block[i] != sorted[start + i]) {
                            valid = false;
                            break;
                        }
                    }
                }
            }

            if (valid) ans += k;
        }

        return ans;
    }
}
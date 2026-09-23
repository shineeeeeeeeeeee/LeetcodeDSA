class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;
        for (int num : nums) total += num;

        int target = total - x;
        int n = nums.length;

        if (target < 0) return -1;
        if (target == 0) return n;

        int left = 0, sum = 0, maxLen = -1;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum > target) {
                sum -= nums[left++];
            }

            if (sum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        return maxLen == -1 ? -1 : n - maxLen;
    }
}
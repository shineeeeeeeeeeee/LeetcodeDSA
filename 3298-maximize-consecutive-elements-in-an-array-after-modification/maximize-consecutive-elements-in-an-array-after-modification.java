class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int x : nums) {
            int len = dp.getOrDefault(x, 0) + 1;
            int nextLen = dp.getOrDefault(x + 1, 0) + 1;

            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), len));
            dp.put(x + 2, Math.max(dp.getOrDefault(x + 2, 0), nextLen));

            ans = Math.max(ans, Math.max(len, nextLen));
        }

        return ans;
    }
}
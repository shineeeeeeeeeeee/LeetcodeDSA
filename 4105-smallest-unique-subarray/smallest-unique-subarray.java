
class Solution {
    private int[] nums;
    private final long base = 19;
    private final long modulo = 1_000_000_007L;
    private long[] powers;
    private Map<Long, Integer> hashValues;

    public int smallestUniqueSubarray(int[] nums) {
        this.nums = nums;
        this.powers = new long[nums.length + 1];
        this.hashValues = new HashMap<>();

        int minPossibleLen = nums.length;
        int minLen = 1;
        int maxLen = nums.length;

        while (minLen <= maxLen) {
            int midLen = minLen + (maxLen - minLen) / 2;
            if (checkUniqueness(midLen)) {
                if (midLen < minPossibleLen) {
                    minPossibleLen = midLen;
                }
                maxLen = midLen - 1;
            } else {
                minLen = midLen + 1;
            }
        }
        return minPossibleLen;
    }

    private boolean checkUniqueness(int subarrayLen) {
        powers[0] = 1;
        for (int idx = 1; idx <= nums.length; idx++) {
            powers[idx] = (powers[idx - 1] * base) % modulo;
        }

        long currentHash = 0;
        for (int idx = 0; idx < subarrayLen; idx++) {
            currentHash = (currentHash * base + nums[idx]) % modulo;
        }

        hashValues.clear();
        hashValues.put(currentHash, 1);

        for (int idx = 1; idx <= nums.length - subarrayLen; idx++) {
            long removeVal = (powers[subarrayLen - 1] * nums[idx - 1]) % modulo;
            currentHash = (currentHash - removeVal + modulo) % modulo;

            currentHash = (currentHash * base + nums[idx + subarrayLen - 1]) % modulo;

            hashValues.put(currentHash, hashValues.getOrDefault(currentHash, 0) + 1);
        }

        return hashValues.containsValue(1);
    }
}

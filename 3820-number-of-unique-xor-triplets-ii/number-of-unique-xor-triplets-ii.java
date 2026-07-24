class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048; // nums[i] <= 1500, so XOR values < 2048

        boolean[] seen = new boolean[MAX];
        boolean[] pair = new boolean[MAX];
        boolean[] triple = new boolean[MAX];

        // values obtainable with one element
        for (int x : nums)
            seen[x] = true;

        // values obtainable with two elements
        for (int i = 0; i < MAX; i++) {
            if (!seen[i]) continue;
            for (int j = 0; j < MAX; j++) {
                if (seen[j])
                    pair[i ^ j] = true;
            }
        }

        // values obtainable with three elements
        for (int i = 0; i < MAX; i++) {
            if (!pair[i]) continue;
            for (int j = 0; j < MAX; j++) {
                if (seen[j])
                    triple[i ^ j] = true;
            }
        }

        int ans = 0;
        for (boolean b : triple)
            if (b) ans++;

        return ans;
    }
}
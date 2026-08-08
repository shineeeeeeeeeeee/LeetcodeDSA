class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        char[] a = word1.toCharArray();
        char[] b = word2.toCharArray();

        // dp[i] = number of characters of word2 that can be
        // matched exactly using word1[i...n-1]
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && a[i] == b[j]) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;
        boolean usedMismatch = false;

        while (i < n && j < m) {

            // normal exact match
            if (a[i] == b[j]) {
                ans[j] = i;
                j++;
            }

            // using this position as the one allowed mismatch
            else if (!usedMismatch &&
                     dp[i + 1] >= m - j - 1) {

                ans[j] = i;
                j++;
                usedMismatch = true;
            }

            i++;
        }

        // couldn't match all characters
        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}
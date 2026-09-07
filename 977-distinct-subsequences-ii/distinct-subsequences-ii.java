class Solution {
    public int distinctSubseqII(String s) {
        int mod = 1000000007;
        int[] last = new int[26];
        int dp = 1;

        for (char c : s.toCharArray()) {
            int next = (dp * 2) % mod;
            next = (next - last[c - 'a'] + mod) % mod;
            last[c - 'a'] = dp;
            dp = next;
        }

        return (dp - 1 + mod) % mod;
    }
}
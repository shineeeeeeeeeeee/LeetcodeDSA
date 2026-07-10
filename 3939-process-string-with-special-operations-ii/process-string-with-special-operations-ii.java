class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n + 1];

        // length after each operation
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            long cur = len[i];

            if (c >= 'a' && c <= 'z') {
                len[i + 1] = cur + 1;
            } else if (c == '*') {
                len[i + 1] = Math.max(0, cur - 1);
            } else if (c == '#') {
                len[i + 1] = Math.min((long)1e15, cur * 2);
            } else { // '%'
                len[i + 1] = cur;
            }
        }

        if (k >= len[n]) return '.';

        // backwards
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            long prev = len[i];
            long cur = len[i + 1];

            if (c >= 'a' && c <= 'z') {
                if (k == prev) return c;
            } else if (c == '*') {
                // deletion as previous string lost last character
                // k unchanged
            } else if (c == '#') {
                if (k >= prev) k -= prev;
            } else { // '%'
                k = cur - 1 - k;
            }
        }

        return '.';
    }
}
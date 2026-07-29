class Solution {
    private long k;
    private int[] freq = new int[26];

    public String smallestPalindrome(String s, int k) {
        this.k = k;
        int n = s.length();
        int half = n / 2;
        for (int i = 0; i < half; i++) freq[s.charAt(i) - 'a']++;

        List<Character> left = new ArrayList<>();
        long start = 0;

        for (int i = 0; i < half; i++) {
            boolean selected = false;
            for (int ci = 0; ci < 26; ci++) {
                if (freq[ci] == 0) continue;
                freq[ci]--;

                long p = perm(half - i - 1);
                if (start + p >= k) {
                    left.add((char) ('a' + ci));
                    selected = true;
                    break;
                }

                freq[ci]++;
                start += p;
            }

            if (!selected) return "";
        }

        StringBuilder h1 = new StringBuilder();
        for (char c : left) h1.append(c);

        String mid = (n % 2 == 1) ? String.valueOf(s.charAt(n / 2)) : "";

        StringBuilder h2 = new StringBuilder();
        for (int i = left.size() - 1; i >= 0; i--) h2.append(left.get(i));

        return h1.toString() + mid + h2.toString();
    }

    private long perm(int rem) {
        long acc = 1;
        for (int ci = 0; ci < 26; ci++) {
            int f = freq[ci];
            if (f == 0) continue;
            if (f > rem) return 0;
            acc *= comb(rem, f);
            if (acc < 0 || acc > k) return Math.max(acc, k + 1); // overflow or already too big
            rem -= f;
        }
        return acc;
    }

    // capped nCr: stops growing once it's already bigger than k, so it can't overflow
    private long comb(int n, int r) {
        if (r > n - r) r = n - r;
        long result = 1;
        for (int i = 0; i < r; i++) {
            result = result * (n - i) / (i + 1);
            if (result > k) return k + 1; // clamp - exact value no longer matters
        }
        return result;
    }
}
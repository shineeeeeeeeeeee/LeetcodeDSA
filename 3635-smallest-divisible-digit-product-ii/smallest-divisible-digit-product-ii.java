class Solution {
    public String smallestNumber(String num, long t) {
        int n = num.length();

        long curr = t;
        for (int f : new int[]{2, 3, 5, 7}) {
            while (curr % f == 0) {
                curr /= f;
            }
        }
        if (curr != 1) return "-1";

        long[] rem = new long[n + 1];
        rem[0] = t;
        int stopIdx = n; // index where we broke due to '0', if any
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                stopIdx = i;
                break;
            }
            rem[i + 1] = rem[i] / gcd(rem[i], num.charAt(i) - '0');
        }
        if (stopIdx == n && rem[n] == 1) return num;

        int z = num.indexOf('0');
        int start = (z != -1) ? z : n - 1;

        for (int i = start; i >= 0; i--) {
            int endSize = n - i - 1;
            for (int d = (num.charAt(i) - '0') + 1; d <= 9; d++) {
                String last = buildEnd(rem[i] / gcd(rem[i], d), endSize);
                if (last.length() == endSize) {
                    return num.substring(0, i) + d + last;
                }
            }
        }

        return buildEnd(t, n + 1);
    }

    private String buildEnd(long req, int size) {
        StringBuilder res = new StringBuilder();
        for (int f = 9; f >= 2; f--) {
            while (req % f == 0) {
                req /= f;
                res.append((char) ('0' + f));
            }
        }
        while (res.length() < size) {
            res.append('1');
        }
        return res.reverse().toString();
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
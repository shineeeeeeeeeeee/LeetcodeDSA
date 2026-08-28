class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        int[] exact = half.clone();
        StringBuilder left = new StringBuilder();

        for (int i = 0; i < halfLen; i++) {
            int c = target.charAt(i) - 'a';

            if (exact[c] == 0) {
                left.setLength(0);
                break;
            }

            left.append(target.charAt(i));
            exact[c]--;
        }

        if (left.length() == halfLen) {
            String candidate = build(left.toString(), middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        for (int pivot = halfLen - 1; pivot >= 0; pivot--) {
            int[] remaining = half.clone();
            StringBuilder prefix = new StringBuilder();
            boolean possible = true;

            for (int i = 0; i < pivot; i++) {
                int c = target.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    possible = false;
                    break;
                }

                remaining[c]--;
                prefix.append(target.charAt(i));
            }

            if (!possible) {
                continue;
            }

            int targetChar = target.charAt(pivot) - 'a';

            for (int c = targetChar + 1; c < 26; c++) {
                if (remaining[c] == 0) {
                    continue;
                }

                int[] temp = remaining.clone();
                temp[c]--;

                StringBuilder newLeft = new StringBuilder(prefix);
                newLeft.append((char) ('a' + c));

                for (int x = 0; x < 26; x++) {
                    while (temp[x] > 0) {
                        newLeft.append((char) ('a' + x));
                        temp[x]--;
                    }
                }

                return build(newLeft.toString(), middle);
            }
        }

        return "";
    }

    private String build(String left, int middle) {
        StringBuilder result = new StringBuilder(left);

        if (middle != -1) {
            result.append((char) ('a' + middle));
        }

        for (int i = left.length() - 1; i >= 0; i--) {
            result.append(left.charAt(i));
        }

        return result.toString();
    }
}
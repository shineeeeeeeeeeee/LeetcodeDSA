class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        for (int pivot = n - 1; pivot >= 0; pivot--) {

            int[] remaining = freq.clone();

            boolean possible = true;

            for (int i = 0; i < pivot; i++) {
                int c = target.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    possible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!possible) continue;

            int targetChar = target.charAt(pivot) - 'a';

            for (int c = targetChar + 1; c < 26; c++) {
                if (remaining[c] == 0) continue;

                remaining[c]--;

                StringBuilder result = new StringBuilder();

                result.append(target, 0, pivot);

                result.append((char) ('a' + c));

                for (int x = 0; x < 26; x++) {
                    while (remaining[x] > 0) {
                        result.append((char) ('a' + x));
                        remaining[x]--;
                    }
                }

                return result.toString();
            }
        }

        return "";
    }
}
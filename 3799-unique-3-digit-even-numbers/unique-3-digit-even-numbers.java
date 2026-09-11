class Solution {
    public int totalNumbers(int[] digits) {
        int count = 0;

        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                for (int c = 0; c < 10; c++) {
                    if (c % 2 != 0 || a == 0) continue;

                    int[] used = new int[10];
                    used[a]++;
                    used[b]++;
                    used[c]++;

                    boolean possible = true;
                    int[] freq = new int[10];

                    for (int d : digits) freq[d]++;

                    for (int d = 0; d < 10; d++) {
                        if (used[d] > freq[d]) {
                            possible = false;
                            break;
                        }
                    }

                    if (possible) count++;
                }
            }
        }

        return count;
    }
}
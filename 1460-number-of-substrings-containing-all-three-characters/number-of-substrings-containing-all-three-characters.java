class Solution {
    public int numberOfSubstrings(String s) {
        int[] count = new int[3]; // counts of 'a', 'b', 'c' in current window
        int left = 0;
        long result = 0;

        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'a']++;

            // shrink from the left while window still has all 3 characters
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                count[s.charAt(left) - 'a']--;
                left++;
            }

            // at this point, [left, right] does NOT have all 3 (or left == 0),
            // so [0, right], [1, right], ..., [left-1, right] all do.
            result += left;
        }

        return (int) result;
    }
}
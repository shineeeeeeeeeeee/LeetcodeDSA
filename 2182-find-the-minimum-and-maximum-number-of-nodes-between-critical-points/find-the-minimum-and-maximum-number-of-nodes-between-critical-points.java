class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int prev = -1;
        int min = Integer.MAX_VALUE;

        ListNode left = head;
        ListNode curr = head.next;
        int index = 1;

        while (curr != null && curr.next != null) {
            ListNode right = curr.next;

            if ((curr.val > left.val && curr.val > right.val) ||
                (curr.val < left.val && curr.val < right.val)) {

                if (first == -1) {
                    first = index;
                } else {
                    min = Math.min(min, index - prev);
                }

                prev = index;
            }

            left = curr;
            curr = right;
            index++;
        }

        if (first == -1 || first == prev) {
            return new int[]{-1, -1};
        }

        int max = prev - first;

        return new int[]{min, max};
    }
}
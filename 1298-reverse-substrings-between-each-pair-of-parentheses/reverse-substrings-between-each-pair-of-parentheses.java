class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder cur = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(cur);
                cur = new StringBuilder();
            } else if (c == ')') {
                cur.reverse();
                cur = stack.pop().append(cur);
            } else {
                cur.append(c);
            }
        }

        return cur.toString();
    }
}
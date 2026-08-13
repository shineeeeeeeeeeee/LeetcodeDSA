class Solution {

    class Node {
        int leftChar;
        int rightChar;
        int prefix;
        int suffix;
        int best;
        int length;

        Node() {
        }

        Node(char c) {
            leftChar = rightChar = c - 'a';
            prefix = suffix = best = length = 1;
        }
    }

    Node[] tree;

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {
        int n = s.length();

        tree = new Node[4 * n];

        build(s, 1, 0, n - 1);

        int k = queryIndices.length;
        int[] answer = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            answer[i] = tree[1].best;
        }

        return answer;
    }

    private void build(String s, int node, int left, int right) {

        if (left == right) {
            tree[node] = new Node(s.charAt(left));
            return;
        }

        int mid = (left + right) / 2;

        build(s, node * 2, left, mid);
        build(s, node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
        int node,
        int left,
        int right,
        int index,
        char ch
    ) {

        if (left == right) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = (left + right) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, right, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {

        Node result = new Node();

        result.length = a.length + b.length;

        result.leftChar = a.leftChar;
        result.rightChar = b.rightChar;

        result.prefix = a.prefix;

        if (a.prefix == a.length && a.rightChar == b.leftChar) {
            result.prefix = a.length + b.prefix;
        }

        result.suffix = b.suffix;

        if (b.suffix == b.length && a.rightChar == b.leftChar) {
            result.suffix = b.length + a.suffix;
        }

        result.best = Math.max(a.best, b.best);

        if (a.rightChar == b.leftChar) {
            result.best = Math.max(
                result.best,
                a.suffix + b.prefix
            );
        }

        return result;
    }
}
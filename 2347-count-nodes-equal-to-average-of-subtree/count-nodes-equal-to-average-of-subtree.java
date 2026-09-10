class Solution {
    public int averageOfSubtree(TreeNode root) {
        int[] count = new int[1];
        dfs(root, count);
        return count[0];
    }

    private int[] dfs(TreeNode node, int[] count) {
        if (node == null) {
            return new int[]{0, 0};
        }

        int[] left = dfs(node.left, count);
        int[] right = dfs(node.right, count);

        int sum = left[0] + right[0] + node.val;
        int nodes = left[1] + right[1] + 1;

        if (sum / nodes == node.val) {
            count[0]++;
        }

        return new int[]{sum, nodes};
    }
}
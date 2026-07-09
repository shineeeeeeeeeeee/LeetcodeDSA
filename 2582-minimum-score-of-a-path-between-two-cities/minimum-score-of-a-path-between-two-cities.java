class Solution {
    public int minScore(int n, int[][] roads) {
        List<int[]>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int[] road : roads) {
            int a = road[0], b = road[1], d = road[2];
            graph[a].add(new int[]{b, d});
            graph[b].add(new int[]{a, d});
        }

        boolean[] visited = new boolean[n + 1];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        visited[1] = true;

        int minScore = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            for (int[] edge : graph[cur]) {
                int next = edge[0], dist = edge[1];
                minScore = Math.min(minScore, dist);
                if (!visited[next]) {
                    visited[next] = true;
                    stack.push(next);
                }
            }
        }

        return minScore;
    }
}
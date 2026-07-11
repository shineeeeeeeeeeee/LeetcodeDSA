class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int complete = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(i, graph, visited, component);

                int size = component.size();
                int edgeCount = 0;

                for (int node : component) {
                    edgeCount += graph[node].size();
                }

                edgeCount /= 2; // each edge counted twice

                if (edgeCount == size * (size - 1) / 2) {
                    complete++;
                }
            }
        }

        return complete;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] visited, List<Integer> component) {
        visited[node] = true;
        component.add(node);

        for (int next : graph[node]) {
            if (!visited[next]) {
                dfs(next, graph, visited, component);
            }
        }
    }
}
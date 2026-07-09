class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        // keep only edges where both endpoints are online
        // (an offline intermediate node can never be visited)
        List<int[]> filtered = new ArrayList<>();
        for (int[] e : edges) {
            if (online[e[0]] && online[e[1]]) {
                filtered.add(e);
            }
        }

        if (filtered.isEmpty()) return -1;

        // build adjacency list and indegree for topological sort
        List<List<int[]>> adj = new ArrayList<>();
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] e : filtered) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            indegree[e[1]]++;
        }

        // kahn's algorithm for topological order
        int[] topo = new int[n];
        int idx = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) queue.offer(i);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            topo[idx++] = u;
            for (int[] edge : adj.get(u)) {
                if (--indegree[edge[0]] == 0) queue.offer(edge[0]);
            }
        }

        // collect distinct edge costs to binary search over
        TreeSet<Integer> costSet = new TreeSet<>();
        for (int[] e : filtered) costSet.add(e[2]);
        int[] costs = new int[costSet.size()];
        int ci = 0;
        for (int c : costSet) costs[ci++] = c;

        int lo = 0, hi = costs.length - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (feasible(n, topo, adj, costs[mid], k)) {
                ans = costs[mid];
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }

    private boolean feasible(int n, int[] topo, List<List<int[]>> adj, int threshold, long k) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        for (int u : topo) {
            if (dist[u] == Long.MAX_VALUE) continue;
            for (int[] edge : adj.get(u)) {
                int v = edge[0], cost = edge[1];
                if (cost < threshold) continue;
                if (dist[u] + cost < dist[v]) {
                    dist[v] = dist[u] + cost;
                }
            }
        }

        return dist[n - 1] != Long.MAX_VALUE && dist[n - 1] <= k;
    }
}
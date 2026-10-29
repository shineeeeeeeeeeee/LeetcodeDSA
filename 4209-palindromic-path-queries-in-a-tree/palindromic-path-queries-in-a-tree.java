class Solution {
    private int n;
    private List<Integer>[] adj;
    private int[] inTime, outTime;
    private int[][] up;
    private int timer, LOG;
    private int[] depth;
    private char[] currentChars;

    class SegmentTree {
        int[] tree;
        int[] lazy;
        int size;

        SegmentTree(int n) {
            this.size = n;
            tree = new int[4 * n];
            lazy = new int[4 * n];
        }

        void push(int node, int start, int end) {
            if (lazy[node] != 0) {
                tree[node] ^= lazy[node];
                if (start != end) {
                    lazy[2 * node] ^= lazy[node];
                    lazy[2 * node + 1] ^= lazy[node];
                }
                lazy[node] = 0;
            }
        }

        void updateRange(int node, int start, int end, int l, int r, int val) {
            push(node, start, end);
            if (start > end || start > r || end < l) return;
            if (start >= l && end <= r) {
                lazy[node] ^= val;
                push(node, start, end);
                return;
            }
            int mid = (start + end) / 2;
            updateRange(2 * node, start, mid, l, r, val);
            updateRange(2 * node + 1, mid + 1, end, l, r, val);
            tree[node] = tree[2 * node] ^ tree[2 * node + 1];
        }

        int queryPoint(int node, int start, int end, int idx) {
            push(node, start, end);
            if (start == end) return tree[node];
            int mid = (start + end) / 2;
            if (idx <= mid) return queryPoint(2 * node, start, mid, idx);
            else return queryPoint(2 * node + 1, mid + 1, end, idx);
        }
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        this.currentChars = s.toCharArray();
        this.adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        this.LOG = (int) (Math.log(n) / Math.log(2)) + 1;
        this.up = new int[n][LOG];
        this.inTime = new int[n];
        this.outTime = new int[n];
        this.depth = new int[n];
        this.timer = 0;

        int[] initialXorMask = new int[n];
        dfs(0, 0, 0, initialXorMask);

        SegmentTree segTree = new SegmentTree(n);
        for (int i = 0; i < n; i++) {
            segTree.updateRange(1, 0, n - 1, inTime[i], inTime[i], initialXorMask[i]);
        }

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int node = Integer.parseInt(parts[1]);
                char newChar = parts[2].charAt(0);
                char oldChar = currentChars[node];
                
                if (oldChar != newChar) {
                    int changeXor = (1 << (oldChar - 'a')) ^ (1 << (newChar - 'a'));
                    segTree.updateRange(1, 0, n - 1, inTime[node], outTime[node], changeXor);
                    currentChars[node] = newChar;
                }
            } else { // query
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int lcaNode = getLCA(u, v);

                int maskU = segTree.queryPoint(1, 0, n - 1, inTime[u]);
                int maskV = segTree.queryPoint(1, 0, n - 1, inTime[v]);
                int maskLCA = 1 << (currentChars[lcaNode] - 'a');

                int pathMask = maskU ^ maskV ^ maskLCA;
                
                boolean canFormPalindrome = (pathMask & (pathMask - 1)) == 0;
                result.add(canFormPalindrome);
            }
        }

        return result;
    }

    private void dfs(int node, int parent, int currentMask, int[] initialXorMask) {
        inTime[node] = timer++;
        up[node][0] = parent;
        for (int j = 1; j < LOG; j++) {
            up[node][j] = up[up[node][j - 1]][j - 1];
        }

        currentMask ^= (1 << (currentChars[node] - 'a'));
        initialXorMask[node] = currentMask;

        for (int neighbor : adj[node]) {
            if (neighbor != parent) {
                depth[neighbor] = depth[node] + 1;
                dfs(neighbor, node, currentMask, initialXorMask);
            }
        }
        outTime[node] = timer - 1;
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u; u = v; v = temp;
        }
        for (int j = LOG - 1; j >= 0; j--) {
            if (depth[u] - (1 << j) >= depth[v]) {
                u = up[u][j];
            }
        }
        if (u == v) return u;
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }
        return up[u][0];
    }
}

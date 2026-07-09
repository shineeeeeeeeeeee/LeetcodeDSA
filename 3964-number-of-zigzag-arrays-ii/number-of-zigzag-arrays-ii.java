class Solution {
    static final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        if (n == 1) return m % MOD;

        int size = 2 * m; // [0..m-1] = up-states, [m..2m-1] = down-states
        long[][] T = new long[size][size];
        for (int v = 0; v < m; v++) {
            for (int j = 0; j < v; j++) T[v][m + j] = 1;       // up[v]   <- down[j], j<v
            for (int j = v + 1; j < m; j++) T[m + v][j] = 1;   // down[v] <- up[j],   j>v
        }

        long[][] Tn = matpow(T, n - 1);

        long[] base = new long[size];
        for (int i = 0; i < m; i++) { base[i] = 1; base[m + i] = 1; }

        long[] finalState = apply(Tn, base);

        long ans = 0;
        for (long v : finalState) ans = (ans + v) % MOD;
        return (int) ans;
    }

    private long[][] matpow(long[][] mat, long p) {
        int s = mat.length;
        long[][] res = new long[s][s];
        for (int i = 0; i < s; i++) res[i][i] = 1;
        while (p > 0) {
            if ((p & 1) == 1) res = matmul(res, mat);
            mat = matmul(mat, mat);
            p >>= 1;
        }
        return res;
    }

    private long[][] matmul(long[][] a, long[][] b) {
        int s = a.length;
        long[][] c = new long[s][s];
        for (int i = 0; i < s; i++) {
            for (int k = 0; k < s; k++) {
                if (a[i][k] == 0) continue;
                for (int j = 0; j < s; j++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return c;
    }

    private long[] apply(long[][] a, long[] v) {
        int s = a.length;
        long[] res = new long[s];
        for (int i = 0; i < s; i++) {
            long sum = 0;
            for (int j = 0; j < s; j++) sum = (sum + a[i][j] * v[j]) % MOD;
            res[i] = sum;
        }
        return res;
    }
}
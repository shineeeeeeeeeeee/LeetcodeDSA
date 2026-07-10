class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        int m = restrictions.length;

        // add building 1 with height 0 and building n with initial max n-1
        int[][] arr = new int[m + 2][2];
        arr[0] = new int[]{1, 0};

        for (int i = 0; i < m; i++) {
            arr[i + 1] = restrictions[i];
        }

        arr[m + 1] = new int[]{n, n - 1};

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int len = arr.length;

        // left to right pass
        for (int i = 1; i < len; i++) {
            arr[i][1] = Math.min(arr[i][1],
                    arr[i - 1][1] + (arr[i][0] - arr[i - 1][0]));
        }

        // right to left pass
        for (int i = len - 2; i >= 0; i--) {
            arr[i][1] = Math.min(arr[i][1],
                    arr[i + 1][1] + (arr[i + 1][0] - arr[i][0]));
        }

        long ans = 0;

        // maximum possible peak between consecutive restrictions
        for (int i = 1; i < len; i++) {
            int x1 = arr[i - 1][0], h1 = arr[i - 1][1];
            int x2 = arr[i][0], h2 = arr[i][1];

            int dist = x2 - x1;
            long peak = (long)(h1 + h2 + dist) / 2;

            ans = Math.max(ans, peak);
        }

        return (int) ans;
    }
}
class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        int[][] a = new int[n][2];
        for(int i=0;i<n;i++){
            a[i][0]=nums[i];
            a[i][1]=i;
        }

        Arrays.sort(a,(x,y)->Integer.compare(x[0],y[0]));

        int[] pos=new int[n];
        for(int i=0;i<n;i++)
            pos[a[i][1]]=i;

        int[] next=new int[n];
        int j=0;
        for(int i=0;i<n;i++){
            while(j+1<n && a[j+1][0]-a[i][0]<=maxDiff)
                j++;
            next[i]=j;
        }

        int LOG=18;
        int[][] up=new int[LOG][n];

        for(int i=0;i<n;i++)
            up[0][i]=next[i];

        for(int k=1;k<LOG;k++){
            for(int i=0;i<n;i++){
                up[k][i]=up[k-1][ up[k-1][i] ];
            }
        }

        int[] ans=new int[queries.length];

        for(int qi=0;qi<queries.length;qi++){

            int l=pos[queries[qi][0]];
            int r=pos[queries[qi][1]];

            if(l>r){
                int t=l;
                l=r;
                r=t;
            }

            if(l==r){
                ans[qi]=0;
                continue;
            }

            if(next[l]==l){
                ans[qi]=-1;
                continue;
            }

            int cur=l;
            int jumps=0;

            for(int k=LOG-1;k>=0;k--){
                if(up[k][cur]<r){
                    cur=up[k][cur];
                    jumps+=1<<k;
                }
            }

            cur=next[cur];
            jumps++;

            if(cur>=r)
                ans[qi]=jumps;
            else
                ans[qi]=-1;
        }

        return ans;
    }
}
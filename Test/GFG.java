import java.util.Arrays;

public class GFG {

    // Returns true if there is a subset of set[] with sum equal to given sum
    static boolean isSubsetSum(int[][] dp, int set[],
                               int n, int s)
    {
        // Base Cases
        if (s == 0) return true;
        else if (s < 0) return false;
        if (n == 0 && s != 0) // reached 0, but sum wasn't found
            return false;

        if (dp[n][s] != -1) return dp[n][s] == 1 ? true: false;

        boolean res = isSubsetSum(dp, set, n-1, s) ||
                isSubsetSum(dp, set, n-1, s-set[n-1]);
        dp[n][s] = res? 1:0;
        return res;
    }

    /* Driver program to test above function */
    public static void main (String args[])
    {
        int set[] = {2,3};
        int sum = 5;
        int n = set.length;
        int dp[][] = new int[n+1][sum+1];
        for(int i = 0; i<=n; i++) Arrays.fill(dp[i], -1);

        System.out.println(isSubsetSum(dp, set, n, sum));
    }
}
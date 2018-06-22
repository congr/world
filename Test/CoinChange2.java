import java.util.Arrays;

class CoinChange2 {
    static public int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int[][] dp = new int[n+1][s+1];

        for (int i = 0; i<=n; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        minSum(dp, nums, n, s, 0);
        for (int i = 0; i<=n; i++) System.out.println(Arrays.toString(dp[i]));

        return dp[n][s];
    }

    static int minSum(int[][] dp, int[] nums, int n, int s, int cnt) {
        System.out.println(n + " " + s);
        if (s == 0) return cnt;
        else if (s<0) return Integer.MAX_VALUE;
        if (n <= 0 && s != 0) return Integer.MAX_VALUE;

        if (dp[n][s] != Integer.MAX_VALUE) return dp[n][s];

        dp[n][s] = Math.min(minSum(dp, nums, n-1, s-nums[n-1], cnt+1), minSum(dp, nums, n-1, s, cnt));
        return dp[n][s];
    }

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(15, new int[] {1,2,3,4,5}));

    }
}
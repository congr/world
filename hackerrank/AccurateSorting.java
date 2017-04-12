import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 4. 11..
 */
// https://www.hackerrank.com/contests/w31/challenges/accurate-sorting
public class AccurateSorting {
    public static void main(String[] args) { // class Solution
        Scanner in = new Scanner(System.in);

        int Q = in.nextInt();
        while (Q-- > 0) {
            int N = in.nextInt();
            int[] nums = new int[N];
            for (int i = 0; i < N; i++) {
                nums[i] = in.nextInt();
            }

            System.out.println(solve(nums) ? "Yes" : "No");
        }
    }

    static boolean solve(int[] nums) {
        boolean result = true;

        for (int i = 0, j = i + 1; j < nums.length; i++, j++) {
            if (nums[i] - 1 == nums[j]) {
                nums[i] = nums[i] ^ nums[j] ^ (nums[j] = nums[i]); // x = x ^ y ^ (y = x);
            }
        }

        //System.out.println(Arrays.toString(nums));
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1])
                return false;
        }

        return result;
    }
}

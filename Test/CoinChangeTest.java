import java.util.Arrays;

public class CoinChangeTest {

    public static void main(String[] args) {
        int[] A = new int[]{2, 4, 10};
        System.out.println("A: " + Arrays.toString(A) + " sum: " + 15);

        int ways = coinChangePossibleWays(A, 15);
        System.out.println("possible ways " + ways);

        int minCount = coinChangeMinCoins(A, 15);
        System.out.println("min count " + minCount);

        int minCountWithoutDup = coinChangePossibleWaysWithoutDuplicate(A, 15);
        System.out.println("PossibleWaysWithoutDuplicate " + minCountWithoutDup);


        A = new int[]{1, 2, 5, 10};
        System.out.println("\nA: " + Arrays.toString(A) + " sum: " + 15);

        ways = coinChangePossibleWays(A, 15);
        System.out.println("possible ways " + ways);

        minCount = coinChangeMinCoins(A, 15);
        System.out.println("min count " + minCount);

        minCountWithoutDup = coinChangePossibleWaysWithoutDuplicate(A, 15);
        System.out.println("PossibleWaysWithoutDuplicate " + minCountWithoutDup);
    }

    static int coinChangePossibleWays(int[] A, int sum) {
        int N = A.length;
        int[] D = new int[sum + 1];

        D[0] = 1; // !!! 0을 만들수 있는 가지수는 1
        for (int n = 0; n < N; n++) {
            for (int s = 0; s <= sum; s++) {
                if (s - A[n] >= 0)
                    D[s] += D[s - A[n]];
            }
        }

        System.out.println(Arrays.toString(D));
        return D[sum];
    }


    static int coinChangeMinCoins(int[] A, int sum) {
        int N = A.length;
        int[] D = new int[sum + 1];

        Arrays.fill(D, sum + 1); // !!! 큰값으로 초기화
        D[0] = 0; // !!! 합을 0으로 만들 최소 동전 개수는 0

        for (int n = 0; n < N; n++) {
            for (int s = 0; s <= sum; s++) {
                if (s - A[n] >= 0)
                    D[s] = Math.min(D[s], D[s - A[n]] + 1);
            }
        }

        System.out.println(Arrays.toString(D));

        if (D[sum] > sum) return -1; // !!!
        else return D[sum];
    }

    static int coinChangePossibleWaysWithoutDuplicate(int[] A, int sum) {
        int N = A.length;
        int[] D = new int[sum + 1];

        // Arrays.fill(D, sum + 1);
        D[0] = 1; // !!! 0을 만들수 있는 방법 1

        for (int n = 0; n < N; n++) {
            for (int s = sum; s - A[n] >= 0; s--) {
                if (D[s - A[n]] > 0)
                    D[s] = 1;
            }
        }

        System.out.println(Arrays.toString(D));

        return D[sum];
    }
}

/* Dynamic Programming Java implementation of Coin
Change problem */

import java.util.LinkedList;
import java.util.List;

class CoinChange {
    // Driver Function to test above function
    public static void main(String args[]) {
        int arr[] = {1, 4, 5, 9, 10};
        int m = arr.length;
        int n = 8;
        int k = 5;
        List<Integer> coins = new LinkedList<>();
        change(n, coins, 0, k);

        //System.out.println("V " + n + " " + change(n, coins, 0));
    }

    static final int[] VALUES = {1, 4, 5, 9, 10}; // N
    static int WEIGHTS[] = new int[]{1, 3, 2, 3, 2}; // K
    static int KofN[] = new int[]{0, 1, 0, 0, 3, 2, 0, 0, 0, 3, 2};

    private static boolean change(int remaining, List<Integer> coins, int pos, int K) {
        if (remaining == 0) {
            int cnt = 0;
            for (int i = 0; i < coins.size(); i++) {
                cnt += KofN[coins.get(i)];
            }
            System.out.println(coins + " " + cnt);
            if (cnt == K) return true;

        } else {
            if (remaining >= VALUES[pos]) {
                coins.add(VALUES[pos]);
                change(remaining - VALUES[pos], coins, pos, K);
                coins.remove(coins.size() - 1);
            }
            if (pos + 1 < VALUES.length) {
                change(remaining, coins, pos + 1, K);
            }
        }
        return false;
    }
}

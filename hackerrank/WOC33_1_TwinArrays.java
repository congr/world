import javafx.util.Pair;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 12..
 */
public class WOC33_1_TwinArrays {
    static int twinArrays(int[] ar1, int[] ar2) {

        PriorityQueue<Pair<Integer, Integer>> pq1 = new PriorityQueue<>((o1, o2) -> o1.getKey() - o2.getKey());
        PriorityQueue<Pair<Integer, Integer>> pq2 = new PriorityQueue<>((o1, o2) -> o1.getKey() - o2.getKey());
        for (int i = 0; i < ar1.length; i++) {
            pq1.add(new Pair(ar1[i], i)); // key: number, value: index
            pq2.add(new Pair(ar2[i], i));
        }

        int prea = pq1.peek().getKey();
        int preb = pq2.peek().getKey();
        while (!pq1.isEmpty() && !pq2.isEmpty()) {
            Pair a = pq1.remove();
            Pair b = pq2.remove();

            if (a.getValue() == b.getValue()) { // index equal
                prea = (int) a.getKey();
                preb = (int) b.getKey();
            } else {
                return Math.min(prea + (int) b.getKey(), preb + (int) a.getKey());
            }
        }

        return prea + preb;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] ar1 = new int[n];
        for (int ar1_i = 0; ar1_i < n; ar1_i++) {
            ar1[ar1_i] = in.nextInt();
        }
        int[] ar2 = new int[n];
        for (int ar2_i = 0; ar2_i < n; ar2_i++) {
            ar2[ar2_i] = in.nextInt();
        }
        int result = twinArrays(ar1, ar2);
        System.out.println(result);
    }
}


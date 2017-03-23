import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 2. 24..
 */
public class FindTheRunningMedian {
    static PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // bigger elements than median
    static PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // smaller elements than median

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];

        float median = -1;
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();

            if (a[a_i] < median) maxHeap.add(a[a_i]); // smaller element should be added to maxHeap which has the smallest element on root
            else minHeap.add(a[a_i]);

            adjustHeap();
            median = findRunningMedian();
            System.out.println(median);
        }
    }

    static float findRunningMedian() {
        int maxSize = maxHeap.size(), minSize = minHeap.size();

        if (maxSize > minSize) return maxHeap.peek();      // if maxHeap is bigger by 1, then peek from maxHeap
        else if (maxSize < minSize) return minHeap.peek();
        else return (float) (maxHeap.peek() + minHeap.peek()) / 2;
    }

    // minHeap, maxHeap size should be same or 1 difference
    // move element until heaps have the same size or 1 difference
    static void adjustHeap() {
        while (maxHeap.size() > minHeap.size() + 1) minHeap.add(maxHeap.poll()); // maxHeap is bigger, then move elements from maxHeap to minHeap
        while (minHeap.size() > maxHeap.size() + 1) maxHeap.add(minHeap.poll());
    }
}

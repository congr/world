import java.util.Scanner;

/**
 * Created by cutececil on 2017. 3. 5..
 */
public class BubbleSort {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];

        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();
        }

        int swaps = bubbleSort(a);
        System.out.println("Array is sorted in " + swaps + " swaps.");
        System.out.println("First Element: " + a[0]);
        System.out.println("Last Element: " + a[a.length - 1]);
    }

static int bubbleSort(int[] a) {
    int n = a.length;
    int total = 0;

    for (int i = 0; i < n; i++) {
        // Track number of elements swapped during a single array traversal
        int numberOfSwaps = 0;

        for (int j = 0; j < n - 1; j++) {
            // Swap adjacent elements if they are in decreasing order
            if (a[j] > a[j + 1]) {
                swap(a, j, j + 1);
                numberOfSwaps++;
            }
        }

        total += numberOfSwaps;

        // If no elements were swapped during a traversal, array is sorted
        if (numberOfSwaps == 0) {
            break;
        }
    }
    return total;
}

static void swap(int[]a, int p1, int p2) {
    int t = a[p1];
    a[p1] = a[p2];
    a[p2] = t;
}
}
import java.util.Arrays;

public class Quick {
    public static void main(String[] args) {
        int[] A = {2, 8, 7, 1, 3, 5, 6, 4};
        quickSort(A, 0, A.length - 1);
        System.out.println(Arrays.toString(A));
    }

    // Start pivot as last index, and find the pivot's proper position
    // move all less elements to the left, all greater elements to the right
    // return pivot's new proper position
    static int partition(int[] A, int start, int end) {
        int pivot = A[end];
        int left = start - 1;
        for (int right = start; right <= end; right++) {
            if (A[right] <= pivot) { // !!! less than pivot, swap it
                left++; // left points at the last value less than pivot
                swap(A, left, right);
            }
        }

        return left; // position where pivot sits
    }

    static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    static void quickSort(int[] A, int start, int end) {
        if (start > end) return;

        int pivot = partition(A, start, end);
        System.out.println(pivot);

        quickSort(A, start, pivot - 1);
        quickSort(A, pivot + 1, end);
    }
}

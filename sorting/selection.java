import java.util.Arrays;

// The Selection sort algorithm is based on the idea of finding the minimum or maximum element in an unsorted array
// and then putting it in its correct position in a sorted array.
public class Selection {
    static void selectionSort(int[] A) {
        for (int i = 0; i < A.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < A.length; j++) {
                if (A[min] > A[j])
                    min = j;
            }

            swap(A, i, min);
        }
    }

    static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static void main(String[] args) {
        int[] A = {3, 4, 5, 1, 9, 19, 2, 7};
        selectionSort(A);
        System.out.println(Arrays.toString(A));
    }
}

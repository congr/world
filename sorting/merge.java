import java.util.Arrays;

public class Merge {
    static void mergeSort(int[] A, int start, int end) {
        if (start < end) { // !!!
            int m = (start + end) / 2;

            mergeSort(A, start, m);
            mergeSort(A, m + 1, end);

            mergeTwo(A, start, m, end);
        }
    }

    static void mergeTwo(int[] A, int start, int mid, int end) {
        int[] merged = new int[end - start + 1]; // 0 9 => 9 - 0 + 1 => 10 (0 ~ 9)

        int i = start, j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (A[i] > A[j]) merged[k++] = A[j++];
            else merged[k++] = A[i++];
        }

        while (i <= mid) {
            merged[k++] = A[i++];
        }

        while (j <= end) {
            merged[k++] = A[j++];
        }

        System.arraycopy(merged, 0, A, start, end - start + 1);
    }

    public static void main(String[] args) {
        int[] A = {3, 4, 5, 1, 9, 19, 2, 7};
        mergeSort(A, 0, A.length - 1);
        System.out.println(Arrays.toString(A));
    }
}

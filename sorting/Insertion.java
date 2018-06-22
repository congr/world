import java.util.Arrays;

public class Insertion {
    static void insertionSort(int A[]) {
        int N = A.length;

        for (int i = 0; i < N; i++) {
            int key = A[i];
            int j = i - 1;
            while (j >= 0 && key < A[j]) {
                A[j + 1] = A[j];
                j--;
            }
            A[j+1] = key;
        }
    }

    public static void main(String[] args) {
        int[] A = {3, 4, 5, 1, 9, 19, 2, 7};
        insertionSort(A);
        System.out.println(Arrays.toString(A));
    }
}

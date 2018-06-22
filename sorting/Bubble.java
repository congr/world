import java.util.Arrays;

public class Bubble {
    static int[] bubbleSort(int[] A) {
        boolean swapping = true;
        while (swapping) {
            swapping = false; // !!!

            for (int i = 0; i < A.length - 1; i++) {
                if (A[i] > A[i + 1]) {
                    int temp = A[i];
                    A[i] = A[i + 1];
                    A[i + 1] = temp;
                    swapping = true; // !!!
                }
            }
        }

        return A;
    }

    public static void main(String[] args) {
        int[] A = {3, 4, 5, 1, 9, 19, 2, 7};
        bubbleSort(A);
        System.out.println(Arrays.toString(A));
    }
}

import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 12..
 */

/* lower는 가장 작은 인덱스, upper는 target보다 처음으로 큰 index, target이 발견되지 않으면 upper와 lower는 같은 index,
찾았다면, binsearch와 같은것은 lower bound (찾았다면, 찾은 인덱스 반환, 못찾았다면 insert 해야할 포지션 즉, 가장 작으면서 큰 인덱스)
6
1 4 9 16 25 36
7
lower2
upper2

6
1 4 9 16 25 36
4
lower1
upper2

6
1 4 4 9 9 25
7
lower3
upper3

6
1 4 4 9 9 25
4
lower1
upper3

6
1 4 4 9 9 25
9
lower3
upper5
 */
public class LowerBound {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        int K = sc.nextInt();
        int index = lowerBound(A, A.length, K);
        System.out.println("lower" + index);

        index = upperBound(A, A.length, K);
        System.out.println("upper" + index);

        int[] v = lowerBound2(A, K);
        System.out.println("lower*] index " + v[0] + " found " + v[1]);
    }

    public static int lowerBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value <= array[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static int upperBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            if (value >= array[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public static int[] lowerBound2(int[] array, int target) {
        int s = 0;
        int e = array.length;
        int m = 0;

        while (s < e) {
            m = (s + e) / 2;

            if (array[m] < target) s = m + 1;
            else e = m;
        }

        if (array[s] == target)
            return new int[]{s, 1};
        else
            return new int[]{s, 0};
    }
}

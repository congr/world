import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 18..
 */
// 자연수 A를 B번 곱한 수를 알고 싶다. 단 구하려는 수가 매우 커질 수 있으므로 이를 C로 나눈 나머지를 구하는 프로그램을 작성하시오.
// 첫째 줄에 A, B, C가 빈 칸을 사이에 두고 순서대로 주어진다. A, B, C는 모두 2,147,483,647 이하의 자연수이다.
// 첫째 줄에 A를 B번 곱한 수를 C로 나눈 나머지를 출력한다.
// => 분할 정복 recursive, Mod 연산 주의 (wa 이유가 mod 연산을 계속 안해서임)
public class BOJ1629_곱셈 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();

        // A ^ B % C 출력
        long result = multiply(A, B, C) % C;
        System.out.println(result);
    }

    /* A^B 제곱을 구하는 방법 (분할정복 recursive)
        A^0 = 1
        A^1 = A
        A^2 = A * A
        A^3 = A^2 * A
        A^4 = A^2 * A^2
         => B가 0, 1 인 경우 base case
         => B가 짝수이면 multiply(A, B/2) * multiply(A, B/2)
         => B가 홀수이면 multiply(B-1) * A
    */
    static long multiply(long A, long B, long C) {
        // base case
        if (B == 0) return 1;
        else if (B == 1) return A % C;                 // !!! Mod 꼭 해야함

        if (B % 2 == 0) { // 짝수
            long temp = multiply(A, B / 2, C) % C;  // 중복연산을 피하기위해 꼭 변수에 담아서 곱해야 한다
            return (temp * temp) % C;                  // !!! Mod 꼭 해야함
        } else // 홀수
            return (A * multiply(A, B - 1, C)) % C; // A * A^(B-1) !!! A를 왜 안곱했어? !!! Mod 꼭 해야함
    }
}

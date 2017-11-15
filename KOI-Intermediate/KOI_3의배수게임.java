import java.util.Scanner;

/**
 * Created by cutececil on 2017. 10. 31..
 */
public class KOI_3의배수게임 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 입력은 10미만 자연수
        for (int i = 1; i <= N; i++) {
            if (i % 3 == 0) System.out.print("X ");
            else System.out.print(i + " ");
        }
    }
}

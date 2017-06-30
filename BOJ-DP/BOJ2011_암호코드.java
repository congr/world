import java.util.Scanner;

/**
 * Created by cutececil on 2017. 6. 28..
 */
/*
 A를 1이라고 하고, B는 2로, 그리고 Z는 26으로 하는거야.
 첫째 줄에 5000자리 이하의 암호가 주어진다.
 어떤 암호가 주어졌을 때, 그 암호의 해석이 몇 가지가 나올 수 있는지 구하는 프로그램을 작성하시오. 1000000으로 나눈 나머지를 출력한다.

 "BEAN"을 암호화하면 25114가 나오는데,  25114를 다시 영어로 바꾸면, "BEAAD", "YAAD", "YAN", "YKD", "BEKD", "BEAN" 총 6가지
 */
public class BOJ2011_암호코드 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String S = " " + sc.next(); // 25114
        int N = S.length();
        int[] D = new int[N + 1];

        D[1] = 1;

        for (int i = 2; i <= N; i++) {
            int s1 = S.charAt(i - 2) - 48; // 10자리, 48 is '0'
            int s2 = S.charAt(i - 1) - 48; // 1자리
            int s = s1 * 10 + s2;

            if (s2 != 0)            // !!! 10인 경우, 1 + 0, 10 이렇게 두가지가 아니고, 10만 가능하다. 알파벳은 1부터 시작이므로
                D[i] = D[i - 1];

            if (s >= 10 && s <= 26) // 10 ~ 26 두자리로 만들 수 있다면 두자리 표현이 가능
                D[i] += D[i - 2];

            D[i] %= 1000000;
        }

        //System.out.println(Arrays.toString(D));
        System.out.println(D[N]);
    }
}

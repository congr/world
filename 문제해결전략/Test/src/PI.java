import java.util.Arrays;
import java.util.Scanner;

public class PI {
    static final int INF = 987654321;
    static int[] cache = new int[10002];
    static String N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int C = Integer.parseInt(sc.next());
        while (C-- > 0) {
            N = sc.next();
            Arrays.fill(cache, -1);

            int res = memorize(0);
            System.out.println(res);
        }
        sc.close();
    }

    // 수열 N[begin]을 외우는 방법 중 난이도의 최소합을 리턴
    static int memorize(int begin) {
        //System.out.println("Memorize " + N.substring(begin));
        // base case : 수열 끝에 도달한 경우 0리턴
        if (begin == N.length())
            return 0;

        if (cache[begin] != -1)
            return cache[begin];

        cache[begin] = INF;
        for (int L = 3; L <= 5; ++L) {
            //System.out.println("L " + L);
            if (begin + L <= N.length())
                cache[begin] = Math.min(cache[begin], memorize(begin + L) + classify(begin, begin + L - 1));
        }

        return cache[begin];
    }

    // 각 부분 문자열의 난이도를 리턴
    static int classify(int a, int b) {
        int len = b - a + 1;
        String M = N.substring(a, a + len);
        //System.out.println("classfiy " + M);
        char[] C = M.toCharArray();

        // 난이도 1인가? 2222, 444, 55555
        if (isSameChar(C))
            return 1;

        // 난이도 2인가? 등차수열인지 검사하고 공차가 1혹은 -1 : 숫자가 1씩 단조 증가/감소 1234 or 3210
        boolean progressive = true;
        for (int i = 0; i < M.length() - 1; i++) {
            if (C[i + 1] - C[i] != C[1] - C[0]) { // 등차수열인가?
                progressive = false;
                break;
            }
        }
        // 공차가 1 혹은 -1 인가?
        if (progressive && Math.abs(C[1] - C[0]) == 1)
            return 2; // 공차가 1인 등차수열
        if (progressive)
            return 5;// 공차가 1이 아닌 등차 수열은 난이도 5

        boolean alternating = true;
        for (int i = 0; i < M.length(); i++) {
            if (C[i] != C[i % 2]) {
                alternating = false;
                break;
            }
        }
        if (alternating)
            return 4; // 두수가 번갈아 나오는 경우

        return 10; // 해당하는 경우가 없다면 난이도 10
    }

    static boolean isSameChar(char[] ch) {
        for (char c : ch)
            if (ch[0] != c)
                return false;

        return true;
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by cutececil on 2017. 2. 13..
 */
public class ChildrenDay {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] line = br.readLine().split(" ");
            String d = line[0];
            int n = Integer.parseInt(line[1]);
            int m = Integer.parseInt(line[2]);
            String result = gift(d, n, m);
            System.out.println(result);
        }

        br.close();
        out.close();
    }

    static int append(int here, int edge, int mod) {
        int there = here * 10 + edge;
        if (there >= mod) return mod + there % mod;
        return there % mod;
    }

    // digits에 속한 숫자들만으로 구성되고
    // C mod n == m인 최소의 C를 찾는다
    static String gift(String digits, int n, int m) {
        // 간선의 번호를 오름차순으로 정렬해 두면 사전순으로 가장 앞에 있는 경로를 찾을 수 있다
        char[] digitsArray = digits.toCharArray();
        Arrays.sort(digitsArray);
        digits = String.valueOf(digitsArray);

        // 흰색 정점 i는 0 ~ n-1, 회색 정점은 i는 n ~ 2n -1
        // choice[i] = parent[i]에서 i로 연결된 간선의 번호
        int[] parent = new int[2 * n];
        int[] choice = new int[2 * n];
        Arrays.fill(parent, -1);
        Arrays.fill(choice, -1);
        Queue<Integer> queue = new LinkedList<>();

        // 흰색 0번을 큐에 추가 (루트)
        parent[0] = 0;
        queue.add(0);

        while (!queue.isEmpty()) {
            int here = queue.poll(); // retrieve and remove
            for (int i = 0; i < digits.length(); i++) {
                int there = append(here, digits.charAt(i) - '0', n);
                if (parent[there] == -1) {
                    parent[there] = here;
                    choice[there] = digits.charAt(i) - '0';
                    queue.add(there);
                }
            }
        }

        // 회색 m에 도달하지 못했으면 실패
        if (parent[n + m] == -1) return "IMPOSSIBLE";

        // 부모로 가는 연결을 따라가면서 C를 계산한다
        StringBuffer sb = new StringBuffer(); // string + 보다 string buffer 를 쓰는 것이 낫다. 이유는...
        int here = n + m;
        while (parent[here] != here) {
            sb.append(choice[here]); // digits.charAt(i) - '0'를 하지 않아도 append(int i) 가 있어서 코드가 간결하다
            here = parent[here];
        }

        return sb.reverse().toString(); // reverse가 편하다
    }
}

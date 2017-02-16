import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by cutececil on 2017. 2. 15..
 */
public class HANOI4B {
    // BFS - precalc
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 원반이 12개, 기둥이 4개 - 12 * 12 * 12 * 12 대략 400만 공간이 필요함
        // 불가능한 시간과 메모리이므로 상태 표현을 좀더 압축해야함
        // 미리 원반의 모든 상태의 그래프를 만들어둠
        precalcBFS(12, 0, 3);

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());

            int state = 0;
            for (int i = 0; i < N-1; ++i) {
                String[] AB = br.readLine().split(" ");
                int A = Integer.parseInt(AB[0]);

                for (int j = 0; j < A; j++) {
                    int value = Integer.parseInt(AB[1]); // index기둥에 value원반이 있다고 셋팅
                    state = set(state, j, value);
                }

                System.out.println();
            }
        }

        br.close();
        out.close();
    }

    public static int MAX_DISCS = 12;

    static int get(int state, int index) {
        return (state >> (index * 2)) & 3;
    }

    static int set(int state, int index, int value) {
        return (state & ~(3 << (index * 2))) | (value << (index * 2));
    }

    static int[] discovered = new int[1 << (MAX_DISCS)];

    // discs개의 원반이 있고 각 원반의 시작 위치와 목표 위치가 begin, end에 주어질 때 최소 움직임의 수를 계산한다
    // sortGame과 같은 형태이나 map을 정수(비트)로 처리한 것이 차이.
    static int precalcBFS(int discs, int begin, int end) {
        if (begin == end) return 0;

        Queue<Integer> queue = new LinkedList<>();
        Arrays.fill(discovered, -1);

        // begin root
        queue.add(begin);
        discovered[begin] = 0;

        while (!queue.isEmpty()) {
            int here = queue.poll(); // retrieve and remove

            // 각 기둥에서 제일 위에 있는 원반의 번호를 계산한다
            int[] top = {-1, -1, -1, -1};
            for (int i = discs - 1; i >= 0; --i) {
                int index = get(here, i);
                top[index] = i;
            }

            //i번 기둥의 맨 위에 있는 원반을 j번 기둥으로 옮긴다
            for (int i = 0; i < 4; i++) {
                // i번 기둥에 원반이 없으면 안된다
                if (top[i] == -1) continue;

                for (int j = 0; j < 4; j++) {
                    // j번 기둥은 비어 있거나 맨위의 원반이 더 커야 한다
                    if (i != j && (top[j] == -1 || top[j] > top[i])) { // 이동 가능
                        int there = set(here, top[i], j);
                        if (discovered[there] == -1) {
                            discovered[here] = discovered[there] + 1;
                            if (there == end) return discovered[there];
                            queue.add(there);
                        }
                    }
                }
            }

        }

        return -1;
    }

}

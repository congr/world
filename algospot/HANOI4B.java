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
    public static int MAX_DISCS = 12;
    static int[] discovered = new int[1 << (2 * MAX_DISCS)]; // 2에 2*12

    // BFS - precalc
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 원반이 12개, 기둥이 4개 - 12 * 12 * 12 * 12 대략 400만 공간이 필요함
        // 불가능한 시간과 메모리이므로 상태 표현을 좀더 압축해야함
        // 미리 원반의 모든 상태의 그래프를 만들어둠
        // 0 ~ 3 기둥에 원반은 {1,.. ~ 12} value
//        precalcBFS(12, 0, 3);

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine()); // count of disc

            // 시작 상태
            int begin = 0;
            for (int i = 0; i < 4; ++i) { // 기둥 0, 1, 2, 3으로 총 4개
                String[] AB = br.readLine().split(" ");
                int A = Integer.parseInt(AB[0]); // i기둥에 놓인 원반 개수

                for (int j = 0; j < A; j++) { // 원반 개수만큼 루프를 돌면서 어떤 원반이 i기둥에 있는지 set
                    int value = Integer.parseInt(AB[j + 1]);
                    begin = set(begin, i, value -1); // i 기둥에 value 원반이 있다고 set
                }
            }

            // 끝 상태 - 모두 옮겨저서 정렬된 상태
            int end = 0;
            for (int j = N-1; j >= 0; --j) { // disc
                end = set(end, 3, j); // 기둥은 항상 4개이므로 마지막 3번째 기둥에 모든 원반을 세팅
            }

            System.out.println(precalcBFS(N, begin, end));
        }


        br.close();
        out.close();
    }

    static int get(int state, int index) {
        return (state >> (index * 2)) & 3;
    }

    static int set(int state, int index, int value) {
        state &= ~(3 << (index * 2));
        state |= value << (index * 2);
        return state;
//        return (state & ~(3 << (index * 2))) | (value << (index * 2));
    }

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
                            discovered[there] = discovered[here] + 1;
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

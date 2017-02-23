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
    public static int MAX_TOWER = 4;
    static int[] discovered = new int[1 << (2 * MAX_DISCS)]; // 2에 2*12

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 원반이 12개, 기둥이 4개 - 4에 12승으로 4대략 400만? 공간이 필요함
        // 불가능한 시간과 메모리이므로 상태 표현을 좀더 압축해야함
        // 미리 원반의 모든 상태의 그래프를 만들어두거나 Bidirectional Search를 해야함
        // {0,1,2,3} 기둥 4개에 원반은 12개로 {0,.. ~ 11} value (0이 가장 작은 원반
//        bfsHanoi(12, 0, 3);

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());            // count of disc

            // 시작 상태 - 입력
            int begin = 0;
            for (int i = 0; i < MAX_TOWER; ++i) {               // 기둥 {0, 1, 2, 3}으로 총 4개
                String[] AB = br.readLine().split(" ");
                int eachN = Integer.parseInt(AB[0]);            // i기둥에 놓인 원반 개수

                for (int j = 0; j < eachN; j++) {               // 원반 개수만큼 루프를 돌면서 어떤 원반이 i기둥에 있는지 set
                    int value = Integer.parseInt(AB[j + 1]);    // value는 원반 번호로 {1~..12} 로 입력됨
                    begin = set(begin, value - 1, i);     // i 기둥에 value 원반이 있다고 set, 내부적으로 0부터 시작하도록 - 1
                }
            }

            // 끝 상태 - 모두 옮겨저서 기둥 3번에 정렬된 상태
            int end = 0;
            for (int j = 0; j < N; j++) {                       // 원반 개수 N
                end = set(end, j, 3);                     // 기둥은 항상 4개이므로 마지막 3번째 기둥에 모든 원반을 세팅
            }

            System.out.println(bfsHanoi(N, begin, end));        // begin 상태에서 완전 정렬된 end까지 옮긴 횟수
        }

        br.close();
        out.close();
    }

    // disc번 원반은 어느 기둥에 있는가?
    static int get(int state, int disc) {
        return (state >> (disc * 2)) & 3;
    }

    // tower번 기둥에 disc번 원반이 있다고 state에 해당 비트를 켠다
    static int set(int state, int disc, int tower) {
        state &= ~(3 << (disc * 2));
        state |= tower << (disc * 2);
        return state;
        // return (state & ~(3 << (index * 2))) | (value << (index * 2));
    }

    // discs개의 원반이 있고 각 원반의 시작 위치와 목표 위치가 begin, end에 주어질 때 최소 움직임의 수를 계산한다
    // sortGame과 같은 형태이나 map을 정수(비트)로 처리한 것이 차이.
    static int bfsHanoi(int discs, int begin, int end) {                // discs 원반 개수
        if (begin == end) return 0;

        Queue<Integer> queue = new LinkedList<>();
        Arrays.fill(discovered, -1);

        // begin root
        queue.add(begin);
        discovered[begin] = 0;

        while (!queue.isEmpty()) {
            int here = queue.poll();

            // 각 기둥에서 제일 위에 있는 원반의 번호를 계산한다
            int[] top = {-1, -1, -1, -1};
            for (int i = discs - 1; i >= 0; --i) {                      // 원반은 {0,~11} 0번이 가장 작은 원반
                int tower = get(here, i);                               // 11번원반은 어느 기둥에 있나? tower[0~3] = 원반 번호 기록
                top[tower] = i;                                         // top[0~3]은 10번 기록후 3번으로 계속 덮어써지나,
            }                                                           // 작은 원반이 위에 있으므로 유효한 로직

            //i번 기둥의 맨 위에 있는 원반을 j번 기둥으로 옮긴다
            for (int i = 0; i < MAX_TOWER; i++) {

                if (top[i] == -1) continue;                             // i번 기둥에 원반이 없다면 다음 i기둥 진행

                for (int j = 0; j < MAX_TOWER; j++) {
                    // j번 기둥은 비어 있거나 맨위의 원반이 더 커야 한다
                    if (i != j && (top[j] == -1 || top[j] > top[i])) {  // j번 기둥에 아무것도 없거나 j번 기둥의 원반이 i보다 크다면 이동가능
                        int there = set(here, top[i], j);               // j기둥으로 i에 있는 원반번호 top[i]를 옮긴다
                        if (discovered[there] == -1) {                  // 이런 4개 기둥 상태가 이미 발견되었었나?
                            discovered[there] = discovered[here] + 1;   // 옮긴 횟수 + 1
                            if (there == end) return discovered[there]; // end 는 찾고자하는 기둥 상태로 모두 3기둥에 정렬된 상태라면 횟수를 리턴
                            queue.add(there);                           // 큐에 넣어서 there기둥 상태에서 더 옮겨볼 상태가 있는지 기둥 상태를 더 바꿔보자
                        }
                    }
                }
            }
        }

        return -1;
    }
}

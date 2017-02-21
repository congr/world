import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by cutececil on 2017. 2. 15..
 */
public class HANOI4 {
    public static int MAX_DISCS = 12;
    static BitSet[] hanoi = new BitSet[MAX_DISCS + 1];
    static LinkedHashMap<BitSet[], Integer> discovered = new LinkedHashMap<>();

    // BFS - precalc
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        // 원반이 12개, 기둥이 4개 - 12 * 12 * 12 * 12 대략 400만 공간이 필요함
        // 불가능한 시간과 메모리이므로 상태 표현을 좀더 압축해야함
        // 미리 원반의 모든 상태의 그래프를 만들어두는데 오른쪽 기둥에 정렬된 상태를 루트로 둠 (sortgame과 같은 이치)
        // 0 ~ 3 기둥에 원반은 {0,.. ~ 11} value
//        for (int i = 0; i < MAX_DISCS; i++) {
//            hanoi[i] = new BitSet(MAX_DISCS);
//        }
        BitSet[] state = new BitSet[4];
        for (int i = 1; i <= MAX_DISCS; i++) {
            state[0] = new BitSet(i);
            state[1] = new BitSet(i);
            state[2] = new BitSet(i);
            state[3] = new BitSet(i);
            state[3].set(0, i); // 0부터 끝까지 모든 비트를 true셋팅
            precalcBFS(state, i); // i: 원반 개수
        }

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine()); // count of disc

            // 입력 상태
            int begin = 0;
            for (int i = 0; i < 4; ++i) { // 기둥 0, 1, 2, 3으로 총 4개
                String[] AB = br.readLine().split(" ");
                int A = Integer.parseInt(AB[0]); // i기둥에 놓인 원반 개수

                for (int j = 0; j < A; j++) { // 원반 개수만큼 루프를 돌면서 어떤 원반이 i기둥에 있는지 set
                    int value = Integer.parseInt(AB[j + 1]);
                    begin = set(begin, i, value); // i 기둥에 value 원반이 있다고 set
                }
            }

            // 끝 상태 - 모두 옮겨저서 정렬된 상태
//            int end = 0;
//            for (int j = N - 1; j >= 0; --j) { // disc
//                end = set(end, 3, j); // 기둥은 항상 4개이므로 마지막 3번째 기둥에 모든 원반을 세팅
//            }

           System.out.println(discovered.get(begin));
        }


        br.close();
        out.close();
    }

    static int get(int state, int index) {
        return (state >> (index * 2)) & 3;
    }

    static int set(int state, int index, int value) {
        state &= ~(3 << (index));
        state |= value << (index);
        return state;
    }

//    static String toString(BitSet bits, int N) {
//        StringBuffer sb = new StringBuffer(N);
//        for (int i = N - 1; i >= 0; i--)
//            sb.append(bits.get(i) ? 1 : 0);
//        return sb.toString();
//    }

    // state 현재 상태, N 원반의 개수
    // sortGame과 같은 형태이나 Bit 처리한 것이 차이로 미리 모든 형태의 그래프를 준비해둠.
    static void precalcBFS(BitSet[] state, int N) {
        //System.out.println(toString(state, N));
               
        Queue<BitSet[]> queue = new LinkedList<>();
        queue.add(state);
        discovered.put(state, 0);

        while (!queue.isEmpty()) {
            BitSet[] here = queue.poll(); // retrieve and remove
            int cost = discovered.get(here);

            // 각 기둥에서 제일 위에 있는 원반의 번호를 계산한다
            int[] top = {-1, -1, -1, -1};
            top[3] = here[3].nextSetBit(0);
            top[2] = here[2].nextSetBit(0);
            top[1] = here[1].nextSetBit(0);
            top[0] = here[0].nextSetBit(0);
//            for (int i = 0; i < N; ++i) {
//                int index = here.nextSetBit(i);
//                top[index / 3] = i; // 11번 기둥 위치는? 4개 index중에 있다고 업뎃, 10기둥위치는? 같은 기둥이라면 덮어씀... 가장 작은 기둥으로 top이 다 채워짐
//            }

            //i번 기둥의 맨 위에 있는 원반을 j번 기둥으로 옮긴다
            for (int i = 0; i < 4; i++) {
                // i번 기둥에 원반이 없으면 안된다
                if (top[i] == -1) continue;

                for (int j = 0; j < 4; j++) {
                    // j번 기둥은 비어 있거나 맨위의 원반이 더 커야 한다
                    if (i != j && (top[j] == -1 || top[j] > top[i])) { // 이동 가능
                        BitSet[] there = (BitSet[]) here.clone();
                        there[i].clear(top[i]); // top[i] 원반을 j기둥으로 이동
                        there[j].set(top[i]);
                        if (discovered.containsKey(there) == false) {
                            discovered.put(there, cost + 1);
                        }
                    }
                }
            }
        }
    }

    public static int bitSetToInt(BitSet bitSet)
    {
        int bitInteger = 0;
        for(int i = 0 ; i < 32; i++)
            if(bitSet.get(i))
                bitInteger |= (1 << i);
        return bitInteger;
    }

}


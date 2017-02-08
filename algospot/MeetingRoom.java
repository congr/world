import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by cutececil on 2017. 2. 8..
 */
public class MeetingRoom {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());

            Graph g = new Graph(N); // team cnt
            ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                String[] ABCD = br.readLine().split(" ");
                int A = Integer.parseInt(ABCD[0]); // a meeting start time
                int B = Integer.parseInt(ABCD[1]); // a meeting end time
                int C = Integer.parseInt(ABCD[2]); // b meeting start time
                int D = Integer.parseInt(ABCD[3]); // b meeting end time

                pairs.add(new Pair(A, B));
                pairs.add(new Pair(C, D));
            }
            g.makeGraph(pairs);

        }

        br.close();
        out.close();
    }

    // 2-SAT 문제의 답을 반환한다.
    int[] solve2SAT(Graph g) {
        int n = g.adjList.length / 2; // 변수의 수

        // 함의 그래프의 정점들을 강결합 요소별로 묶는다
        ArrayList<Integer> label = g.tarjanSCC();

        // 이 SAT문제를 푸는 것이 불가능한지 확인 - 한 변수를 나타내는 두 정점(!A, A)이 같은 강결합 요소에 속할 경우 답없음
        for (int i = 0; i < 2 * n; ++i) {
            if (label.get(i) == label.get(i + 1)) return null;
        }

        // 여기까지 왔다면 SAT문제를 푸는 것이 가능함!
        int[] value = new int[n * 2]; // ???

        // Tarjan 알고리즘에서 SCC번호는 위상정렬의 역순이다. reverse 하면 위상정렬 순서가 된다.
        ArrayList<Pair<Integer, Integer>> order = new ArrayList<>(2 * n);
        for (int i = 0; i < 2 * n; ++i) {
            order.add(new Pair(label.get(i) * -1, i));
        }
        Collections.reverse(order);

        // 각 정점에 값을 배정한다
        for (int i = 0; i < 2 * n; ++i) {
            int vertex = order.get(i).getValue();
            int variable = vertex / 2;
            boolean isTrue = vertex % 2 == 0;

            if (value[variable] != -1) continue;

            //~A가 A보다 먼저 나왔으면 A는 참
            // A가 ~A보다 먼저 나왔으면 A는 거짓
            value[variable] = isTrue ? 0 : 1;
        }

        return value;
    }

    static class Graph {
        ArrayList<Integer>[] adjList;
        int V;

        Graph(int V) {
            this.V = V;
            adjList = (ArrayList<Integer>[]) new ArrayList[V * 2];
            for (int i = 0; i < V * 2; ++i) {
                adjList[i] = new ArrayList<>();
            }
        }

        // tarjanSCC
        int[] sccId;
        int[] discovered;
        Stack<Integer> st;
        int sccCounter, vertexCounter;

        int[] tarjanSCC() {
            sccId = new int[adjList.length];
            discovered = new int[adjList.length];
            st = new Stack<>();

            // discovered array initialize
            Arrays.fill(discovered, -1);

            // scc
            for (int i = 0; i < adjList.length; ++i) { // dfsAll()과 같음
                if (discovered[i] == -1) // not visited
                    scc(i);
            }

            return sccId;
        }

        int scc(int here) { // dfs()
            int ret = discovered[here] = vertexCounter++;

            for (int there : adjList[here]) {
                if(discovered[there] == -1)
                    ret = Math.min(ret, scc(there));

            }

            // 정점 방문 종료시점

        }

        // ith team's first meeting is meetings[2*i], second meeting is meetings[2*i+1]
        void makeGraph(ArrayList<Pair<Integer, Integer>> meetings) {
            int vars = meetings.size();

            // true or false vertex
            for (int i = 0; i < vars; i += 2) {
                int j = i + 1;
                adjList[i * 2 + 1].add(j * 2); // ~i => j
                adjList[j * 2 + 1].add(i * 2); // ~j => i
            }

            for (int i = 0; i < vars; ++i) {
                for (int j = 0; j < i; ++j) {
                    // i번 회의과 j번 회의가 안 겹칠 경우
                    if (!disjoint(meetings.get(i), meetings.get(j))) {
                        // i번 회의가 열리지 않거나 j번 회의가 열리지 않아야 한다. ~i or ~j 절을 추가한다
                        adjList[i * 2].add(j * 2 + 1);// i => ~j
                        adjList[j * 2].add(i * 2 + 1);// j => ~i
                    }
                }
            }
        }

        // 두팀간 회의시간이 겹치지 않는다면 true
        boolean disjoint(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
            return a.getValue() <= b.getKey() || b.getValue() <= a.getKey();
        }
    }

}

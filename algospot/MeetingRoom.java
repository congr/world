import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

            Graph g = new Graph(N);
            ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                String[] ABCD = br.readLine().split(" ");
                int A = Integer.parseInt(ABCD[0]);
                int B = Integer.parseInt(ABCD[1]);
                int C = Integer.parseInt(ABCD[2]);
                int D = Integer.parseInt(ABCD[3]);

                pairs.add(new Pair(A, B));
                pairs.add(new Pair(C, D));
            }
            g.makeGraph(pairs);

        }

        br.close();
        out.close();
    }

    ArrayList<Integer> solve2SAT(Graph g) {
        int n = g.adjList.length/2; // 변수의 수

        // 함의 그래프의 정점들을 강결합 요소별로 묶는다
        ArrayList<Integer> label = tarjanSCC();

        // 이 SAT문제를 푸는 것이 불가능한지 확인 - 한 변수를 나타내는 두 정점이 같은 강결합 요소에 속할 경우 답없음
        for(int i =0; i<2*n; ++i){
            if (label.get(i) == label.get(i+1)) return null;
        }

        // SAT문제를 푸는 것이 가능함!
        ArrayList<Integer> value = new ArrayList<>(); // ???

        // Tarjan 알고리즘에서 SCC번호는 위상정렬의 역순이다. reverse 하면 위상정렬 순서가 된다.
//        ArrayList<Integer>



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
        boolean disjoint(Pair<Integer,Integer> a, Pair<Integer, Integer> b) {
            return a.getValue() <= b.getKey() || b.getValue() <= a.getKey();
        }
    }

}

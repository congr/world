import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TestRouting {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            Vertex[] input = new Vertex[N]; // 간선들의 집합 (시작점 기준)
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                double val = Double.parseDouble(st.nextToken());
                input[a] = new Vertex(b, val, input[a]);
                input[b] = new Vertex(a, val, input[b]);
            } // 입력 끝

            double[] dijkstra = new double[N];// 다익스트라 배열 초기화
            Arrays.fill(dijkstra, Double.MAX_VALUE);
            dijkstra[0] = 1;

            PriorityQueue<Node> que = new PriorityQueue<>(N, new Comparator<Node>() {

                @Override
                public int compare(Node o1, Node o2) {
                    if (o1.val > o2.val) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            que.add(new Node(0, 1));
            while (!que.isEmpty()) {
                Node n = que.poll();
                Vertex v = input[n.index];
                while (v != null) { // 시작점이 n.index인 모든 Vertex 체크
                    if (dijkstra[v.to] > n.val * v.val) {
                        dijkstra[v.to] = n.val * v.val;
                        que.add(new Node(v.to, dijkstra[v.to]));
                    }
                    v = v.next;
                }
            }
            System.out.printf("%.10f\n", dijkstra[N - 1]);
        }
    }

    static class Vertex {
        int to;
        double val;
        Vertex next;

        public Vertex(int to, double val, Vertex next) {
            super();
            this.to = to;
            this.val = val;
            this.next = next;
        }
    }

    static class Node {
        int index;
        double val;

        public Node(int index, double val) {
            super();
            this.index = index;
            this.val = val;
        }

    }
}
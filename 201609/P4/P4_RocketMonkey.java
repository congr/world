import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 9. 4..
 */
public class P4_RocketMonkey {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P4/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt(); // 나무개수 <= 100
            int M = sc.nextInt(); // 간선개수 <= 500

            // N개 나무 높이
            int[] H = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                H[i] = sc.nextInt();
            }
            //System.out.println(Arrays.toString(H));

            // M개 나무 이동 간선
            EdgeWeightedGraph g = new EdgeWeightedGraph(N + 1);
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int d = sc.nextInt();
                g.addEdge(Math.min(u, v), Math.max(u, v), d);
            }

            int from = sc.nextInt();
            int to = sc.nextInt();

            g.printGraph();

            // 로케트 없이
            int minDist = g.bfs(1, H, from, to, 0);

            // 로케트를 나무마다 한번씩 추가해서
            for (int i = 1; i <= N; i++) {
                int dist = g.bfs(1, H, from, to, i);
                minDist = Math.min(dist, minDist);
            }

            int result = (minDist == Integer.MAX_VALUE) ? -1 : minDist;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class EdgeWeightedGraph { // int or double?
        ArrayList<Edge>[] adjList;
        int[] distance; // weight type
        int V; // Vertex Cnt

        EdgeWeightedGraph(int V) {
            this.V = V;
            distance = new int[V];
            //Arrays.fill(distance, Integer.MAX_VALUE);            // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }

        int bfs(int start, int[] H, int from, int to, int rocket) { // H 나무 높이
            System.out.println(rocket + " rocket " + "\n" + Arrays.toString(H));

            PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.d > o2.d) return 1;
                else return -1;
            });

            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[start] = 0;                                // root
            pq.add(new Edge(start, start, 0, from)); // 1번 나무의 from 이 현재 위치

            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();
                int u = hereE.u;
                int v = hereE.v; // here
                int d = hereE.d;
                int l = hereE.l; // u의 마지막 높이
                System.out.print(hereE);

                if (distance[v] < d) continue;

                for (Edge thereE : adjList[v]) {
                    int here = v;
                    int there = thereE.v;
                    int dist = thereE.d;
                    int last = 0; // there나무의 last
                    int climb = 0;
                    if (l < dist + 1) { // 나무를 올라가서 이동
                        climb = dist + 1 - l;
                        last = 1;
                        if (here != rocket && climb + l > H[here])// here 의 마지막 높이에 더 올라간다해도 나무 높이를 초과할 수 없다
                            continue;
                    } else {// 그냥 진행
                        last = l - dist; // 항상 양수
                    }

                    if (here == rocket) { // rocket를 쓰는 나무라면 climb 필요 없을 수 있는데 오히려 내려가야 하는 경우도 있다
                        last = l; // 점프시 아래로 내려가지 않는다
                        climb = 0; // 위로 올라가서 점프할 필요없다
                    }

                    if (last > H[there]) { // 로케트로 이동하려는 위치가 다음 나무의 높이보다 높다면
                        climb = last - H[there]; // 아래로 이동한다
                        last = H[there];          // last 를 climb 만큼 아래로 이동
                    }

                    int distSum = climb + d + dist; // climb + 누적 거리 + 현재 간선 거리
                    if (there == V - 1) // 1 -> N 나무 이동이고 N나무가 다음 위치라면, to 나무높이까지 가는 거리를 합해준다
                        distSum += Math.abs(to - last); // (마지막 나무 end 높이 - there 나무 높이) 차이

                    if (distance[there] > distSum) {
                        distance[there] = distSum;

                        pq.add(new Edge(here, there, distSum, last));
                    }
                }
            }

            System.out.println("");
            System.out.println(Arrays.toString(distance));

            return distance[V - 1];
        }

        public void printGraph() {
            int id = 0;
            for (ArrayList list : adjList) {
                System.out.print(id + " -> ");
                for (int i = 0; i < list.size(); ++i) {
                    System.out.print(list.get(i) + " ");
                }
                System.out.println();
                ++id;
            }
        }

        // undirected graph - add Edge
        public void addEdge(int u, int v, int w) {
            adjList[u].add(new Edge(u, v, w, 0));
            adjList[v].add(new Edge(v, u, w, 0)); // if directed graph, remove one.
        }

        class Edge {
            int u, v, d, l;

            Edge(int u, int v, int d, int l) {
                this.u = u;
                this.v = v; // to vertex
                this.d = d; // weight
                this.l = l; // u의 마지막 위치
            }

            @Override
            public String toString() {
                return "[" + u + "~" + v + "(" + d + ") " + l + "]";
            }
        }
    }
}

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 8. 24..
 */
// 201608 3번 접선
// 두 요원이 만나는 가장 가까운 간선상 위치 - 동일 간선이 여러개 존재할 때는 i j 간선중 i가 작은 것을 출력
public class P3_Contact {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201608/P3/00.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int K = sc.nextInt();

            EdgeWeightedGraph g = new EdgeWeightedGraph(N + 1);
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int w = sc.nextInt();
                g.addEdge(u, v, w);
            }

            for (int i = 0; i < K; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                long minDist = g.dijkstra(u, v); // 두요원 사이 최소거리

                // minDist/2 지점의 모든 정점 번호를 찾아야함
                g.resultEdges.clear(); // !!! 쿼리를 할 때마다 resultEdge는 초기화
                g.calcPath(u, v, (double) minDist / 2, 0);
                g.resultEdges.sort((o1, o2) -> {
                    int a = o1[0] - o2[0];
                    if (a == 0) return o1[1] - o2[1];
                    else return a;
                });

                String result = g.resultEdges.get(0)[0] + " " + g.resultEdges.get(0)[1];
                System.out.println(result);
                wr.write(result + "\n");
            }
        }

        sc.close();
        wr.close();
    }

    static class EdgeWeightedGraph { // int or double?
        ArrayList<Edge>[] adjList;
        ArrayList<Edge>[] visitList;
        long[] distance; // weight type
        int V; // Vertex Cnt

        EdgeWeightedGraph(int V) {
            this.V = V;
            distance = new long[V];
            //Arrays.fill(distance, Long.MAX_VALUE);            // cost를 우선 Max로 설정하고 더 작은 cost가 있다면 업데이트 되도록 한다

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }

        // 참고 : priority - 가중치(0이상) 있는 그래프는 dijkstra, 없다면 일반적인 bfs 사용
        // dijkstra는 root에서 모든 정점을 거치면서 최단 거리(cost)를 저정하고 마지막 정점에는 최종 최단거리 값이 저장되므로 dist[v-1]을 리턴함
        long dijkstra(int start, int end) {

            // !!! 이전 패스를 담기 위해 start - end 가 항상 다르므로
            Arrays.fill(distance, Long.MAX_VALUE);
            visitList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) visitList[i] = new ArrayList<>();

            PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.w > o2.w) return 1;
                else return -1;
            });

            distance[start] = 0;                                // root
            pq.add(new Edge(0, start, 0));

            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();                         // retrieve and remove
                int here = hereE.v;
                long hereCost = hereE.w;                        // here 까지 오는데 최단 비용

                if (here == end) break;

                if (distance[here] < hereCost) continue;        // here 점까지 온적이 있었는데, 그때 cost가 더 낮았다면 큐에서 꺼내기만하고 패스 다음진행

                for (Edge thereE : adjList[here]) {
                    int there = thereE.v;
                    long cost = thereE.w + hereCost;            // cost를 비교해서 방문을 할지 말지를 결정하는 단계
                    if (distance[there] >= cost) {               // bfs에서는 처음 발견하면 큐에 넣었지만,
                        distance[there] = cost;                 // dij는 [Here까지의 cost + There 까지 cost]가 기존 there보다 작으면 dist를 갱신하고 pq에 넣는다
                        pq.add(new Edge(here, there, cost));    // ** new Edge(v, w) : 기존 입력된 w자체를 바꾸면 안된다. new해서 큐에 넣자!

                        visitList[there].add(thereE);           // previous를 기록 u->v 거슬러 올라갈 수 있도록 if 문에 =를 꼭 추가해야함 같은경로 여러개일수 있어서
                    }
                }
            }

            return distance[end];                                // 마지막 지점의 dist 값을 리턴
            //return distance[V - 1];                            // distance[V-1]에 0부터 마지막점까지의 cost가 저장되어 있음.
        }

        ArrayList<int[]> resultEdges = new ArrayList<>(); // 출력을 위한 edge list (동일한 halfCost를 갖지만 서로 다른 경로
        ArrayList<Edge> pathEdges = new ArrayList<>(); // start->end를 거슬러서 만든 경로

        // end 부터 start로 가면서 같은 최단 경로를 갖는 모든 경로를 찾아,
        // 중간지점에서 만날 수 있는 (start, end) 중 start가 가장 작은 숫자인 것을 찾는다
        void calcPath(int target, int end, double halfCost, long sumCost) { // half : 만나는 중간지점 sumCost: 지금까지 경로 cost합
            if (target == end) { // u->v 끝까지 경로를 다 만들었다면 leaf 노드라면
                if (sumCost == halfCost * 2) {
                    for (Edge e : pathEdges) {
                        if (e.w > halfCost) {
                            resultEdges.add(new int[]{Math.min(e.u, e.v), Math.max(e.u, e.v)});
                            break;
                        } else if (e.w == halfCost) {
                            resultEdges.add(new int[]{e.v, e.v});
                            break;
                        }
                    }
                }
            }

            // adjList 에서 end -> start 역순으로 거슬러가면서 pathEdges에 추가한다
            for (int i = 0; i < visitList[end].size(); i++) {
                Edge parentEdge = visitList[end].get(i); // end -> start로 거슬러 간다
                Edge saveEdge = new Edge(parentEdge.v, parentEdge.u, parentEdge.w + sumCost);
                pathEdges.add(saveEdge);
                calcPath(target, parentEdge.u, halfCost, parentEdge.w + sumCost);
                pathEdges.remove(saveEdge);
            }
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
        public void addEdge(int u, int v, long w) {
            adjList[u].add(new Edge(u, v, w));
            adjList[v].add(new Edge(v, u, w)); // if directed graph, remove one.
        }

        class Edge {
            int u;
            int v;
            long w;

            Edge(int u, int v, long w) {
                this.u = u;
                this.v = v; // to vertex
                this.w = w; // weight
            }

            @Override
            public String toString() {
                return u + " - " + v + "(" + w + ")";
            }
        }

        /*public static int binarySearchList(ArrayList<Edge> a, double key) {
            int lo = 0;
            int hi = a.size() - 1;
            int mid = lo + (hi - lo) / 2;
            while (lo <= hi) {
                // Key is in a[lo..hi] or not present.
                mid = lo + (hi - lo) / 2;
                if (key < a.get(mid).w) hi = mid - 1;
                else if (key > a.get(mid).w) lo = mid + 1;
                else return mid;
            }
            return mid;
        }*/
    }
}

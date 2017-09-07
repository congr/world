import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 4..
 */
// 201609 final 4번 로케트 원숭이
// 로케트를 쓰면 직선이동하고 원래는 대각선 아래로 간선 거리만큼 이동한다 1 -> N이동이고 각자 시작종료 위치가 입력된다
// N 정점의 범위가 최고 100이므로 로케트는 모든 정점에 다 적용해서 최소 거리를 찾는다
// 조건.
// 현재 위치가 점프해서 다음나무로 갈때, l < 나무 거리 + 1 면 climb 하여 이동, 현재 높이가 충분하면 바로 점프
// 로케트를 쓰면 climb할 필요없고 land = l이 된다 동일한 높이로 이동
// 로케트를 쓰건 높이가 충분해서 그냥 점프할 경우, land가 다음 나무보다 높게 계산되었다면 내려간다.
//                                      climb에 내려가는 거리(land - 다음 나무 높이)를 저장하고 land도 다음 나무 꼭대기로 변경
// !!! 일반적인 Dijkstra와 다르게 로케트를 쓰는 경우에 distance[here] 보다 일단 더 먼 거리지만 이후 로케트에 의해 짧은 거리로 변경될 수 있다 !!!
public class P4_RocketMonkey {
    static boolean debug = false;
    
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201609/P4/05.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            if (T == 74) {
                System.out.println("T " + T);
                //debug = true;
            } else debug = false;
            int N = sc.nextInt(); // 나무개수 <= 100
            int M = sc.nextInt(); // 간선개수 <= 500
    
            if (debug)
            System.out.println("N " + N + " M" + M);
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
            
            if (debug)
            g.printGraph();
            
            // 로케트 없이
            int minDist = g.bfs(1, H, from, to, -1);
            
            // 로케트를 나무마다 한번씩 추가해서
            for (int i = 1; i < N; i++) {
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

            adjList = (ArrayList<Edge>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }
        
        int bfs(int start, int[] H, int from, int to, int rocket) { // H 나무 높이
            //System.out.println(rocket + " rocket " + "\n" + Arrays.toString(H));
            
            PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.d > o2.d) return 1;
                else return -1;
            });

            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[start] = 0;                                // root
            pq.add(new Edge(start, start, 0, from));        // 1번 나무의 from 이 현재 위치
            
            while (!pq.isEmpty()) {
                Edge hereE = pq.poll();
                int u = hereE.u;
                int v = hereE.v; // here
                int d = hereE.d;
                int l = hereE.l; // here로 점프해온 마지막 지점
                if(debug)
                System.out.print(hereE);

                //if (distance[v] < d) continue; // !!! 로케트를 쓰면 d가 현재는 큰데 앞으로 작아지는 경우가 발생함
                
                for (Edge thereE : adjList[v]) {
                    int here = v;
                    int there = thereE.v;
                    int dist = thereE.d;
                    int land = 0;                   // there 나무로 점프후 착지 지점
                    int climb = 0;

                    if (here == rocket) {           // rocket를 쓰는 나무라면 climb 필요 없을 수 있는데 오히려 내려가야 하는 경우도 있다
                        land = l;                   // 점프시 아래로 내려가지 않고 직선이동. l은 here, land 는 there 랜딩 지점
                        climb = 0;                  // 위로 올라가서 점프할 필요없다
                    } else {
                        if(H[here] - dist < 1) continue;
                        if (l < dist + 1) {         // 현재 here 나무 위치가 점프후 there 랜딩 지점 보다 낮다면, 나무를 올라가서 이동
                            climb = dist + 1 - l;
                            land = 1;               // there 나무 랜딩 높이
                            if (climb + l > H[here])// here 의 마지막 높이에 더 올라간다해도 나무 높이를 초과할 수 없다
                                continue;
                        } else {                    // 랜딩 위치가 1보다 높아서 그냥 점프 진행
                            land = l - dist;        // 항상 양수
                        }
                    }
    
                    if (land > H[there]) {          // 로케트를 쓴 경우 혹은 here가 높은데 there가 낮은 나무인 경우, there 나무 높이보다 랜딩이 높다면,
                        climb = land - H[there];    // 아래로 이동한다. 원래계산된 착지지점에서 나무 높이만큼 뺀 diff를 이동거리로서 climb에 추가하고
                        land -= climb;              // land를 climb 만큼 아래로 이동. 즉 there 나무 꼭대기로 이동한다는 뜻
                        if (land < 1) continue;
                    }
                    
                    int distSum = climb + d + dist;     // climb + 누적 거리 + 현재 간선 거리
                    if (there == V - 1) {               // 1 -> N 나무 이동이고 N나무가 다음 위치라면, to 나무높이까지 가는 거리를 합해준다
                        distSum += Math.abs(to - land); // (마지막 나무 end 높이 - there 나무 높이) 차이
                        land = to;
                    }
                    if (distance[there] > distSum) {    // 더 가까운 거리라면 갱신하고 pq에 넣어 다음 간선도 더 가본다
                        distance[there] = distSum;
                        pq.add(new Edge(here, there, distSum, land)); // here -> there로 landing한 마지막 위치와 총 누적거리를 저장
                    }
                }
            }
            
            if (debug) {
                System.out.println("");
                System.out.println(Arrays.toString(distance));
            }
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
                this.u = u; // from
                this.v = v; // to vertex
                this.d = d; // dist
                this.l = l; // u의 마지막 위치
            }
            
            @Override
            public String toString() {
                return "[" + u + "~" + v + "(" + d + ") " + l + "]";
            }
        }
    }
}

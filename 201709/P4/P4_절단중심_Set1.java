import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 18..
 */
public class P4_절단중심_Set1 {

    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P4/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);

        int T = sc.nextInt();
        while (T-- > 0) {
            int V = sc.nextInt();
            Graph g = new Graph(V + 1);

            for (int i = 0; i < V - 1; i++)
                g.addEdge(sc.nextInt(), sc.nextInt());

            long maxCnt = g.setCutCount();

            // maxCnt 절단 개수를 갖는 절단점 출력
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= V; i++) {
                //System.out.println("v " + i + " cutCnt " + g.cutCnt[i]);
                if (g.cutCnt[i] == maxCnt) {
                    sb.append(i + " ");
                }
            }

            String result = sb.toString() + "\n" + maxCnt;
            System.out.println(result);
            wr.write(result + "\n");
        }

        sc.close();
        wr.close();
    }

    static class Graph {
        ArrayList<Integer>[] adjList;
        long[] cutCnt;
        boolean[] cut;
        boolean[] visited;
        boolean[] isChild;
        int V;

        Graph(int V) {
            this.V = V; // Vertex cnt

            cutCnt = new long[V];
            cut = new boolean[V];
            visited = new boolean[V];
            isChild = new boolean[V];

            adjList = (ArrayList<Integer>[]) new ArrayList[V];
            for (int i = 1; i < V; ++i) adjList[i] = new ArrayList<>();

        }

        //void setCutVertex() {
        //    for (int i = 1; i < V; i++) {
        //        if (adjList[i].size() > 1) cut[i] = true;
        //    }
        //}

        long maxCnt = 0;

        public long setCutCount() {
            int root = 0;
            for (int i = 1; i < V; i++) {
                if (isChild[i] == false) { // 단한번도 child로 등장 안했다면
                    root = i;
                    break;
                }
            }

            for (int i = 1; i < V; i++) {
                if (adjList[i].size() > 0) {
                    cut[i] = true;
                    cutCnt[i] = getCutDivisionCnt(i);
                    maxCnt = Math.max(maxCnt, cutCnt[i]);
                }
            }
            return maxCnt;
        }

        int getCutDivisionCnt(int root) {
            int sum = 0;

            int subRootSize = adjList[root].size(); // 루트 바로 밑 자식 수
            //if (subRootSize > 2) { // 자식이 2개보다 크다면
            //    sum += subRootSize * (subRootSize - 1) / 2; // 서브루트끼리 조합한 수 = N * (N-1) / 2
            //} else if (subRootSize == 2)
            //    sum += 1;


            // sub root children 개수
            int[] subRootChildren = new int[subRootSize];

            for (int i = 0; i < adjList[root].size(); i++) {
                int there = adjList[root].get(i); // sub root
                Arrays.fill(visited, false);
                subRootChildren[i] = dfs(root, there) + 1;

                //System.out.println("root " + root + " subRoot " + i + " " + Arrays.toString(subRootChildren));
            }


            // 분할되는 카운트
            for (int i = 0; i < subRootSize; i++) {
                for (int j = i + 1; j < subRootSize; j++) {
                    sum += subRootChildren[i] * subRootChildren[j];
                }
            }

            System.out.println("root " + root + " subRoot " + (subRootSize - 1) + " " + Arrays.toString(subRootChildren) + " sum " + sum);

            return sum;
        }

        // sub root에서 시작해서 sub root의 총 자식수를 cnt로 리턴한다
        int dfs(int root, int here) {
            visited[here] = true; // 방문 시작 시점
            int cnt = 0;
            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
                if (visited[there] == false && there != root) {
                    cnt = dfs(root, there) + 1;
                }
            }
            return cnt;
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
            adjList[v].add(u);
            isChild[v] = true;
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
    }
}

//static class Graph {
//    int V, E, min, counter;
//    int[] discovered;
//    boolean[] isCutVertex;
//    ArrayList<Integer>[] list = new ArrayList[100001];
//
//    boolean[] visited;
//
//    Graph(int V, int E) {
//        this.V = V;
//        this.E = E;
//        discovered = new int[V + 1];
//        isCutVertex = new boolean[V + 1];
//        counter = 0;
//
//        for (int i = 1; i <= V; i++) {
//            list[i] = new ArrayList<Integer>();
//            discovered[i] = 0;
//        }
//
//        visited = new boolean[V + 1];
//    }
//
//    void setCutVertex(int root) {
//
//    }
//
//    public int dfs(int A, boolean isRoot) {
//        discovered[A] = ++counter;
//        int ret = discovered[A];
//
//        int child = 0; //정점 A가 루트 노드 일 경우를 대비해서 DFS스패닝 트리에서의 자식 수 세어준다.
//        for (int i = 0; i < list[A].size(); i++) {
//            int next = list[A].get(i);
//            if (discovered[next] == 0) {
//                child++;
//                //low : 정점 A의 자식 노드가 갈 수 있는 노드중 가장 일찍 방문한 노드
//                int low = dfs(next, false);
//                if (!isRoot && low >= discovered[A])
//                    isCutVertex[A] = true;
//                ret = Math.min(ret, low);
//            } else {
//                ret = Math.min(ret, discovered[next]);
//            }
//        }
//
//        if (isRoot)
//            isCutVertex[A] = (child >= 2);
//        return ret;
//    }
//
//    int sum = 0;
//
//    int bfs(int start, int cut) {
//        Arrays.fill(visited, false);
//        sum = 0;
//        Queue<Integer> queue = new LinkedList<>();
//        // root
//        queue.add(start);
//
//        while (!queue.isEmpty()) {
//            int here = queue.poll(); // retrieve and remove
//
//            if (cut == here) {
//                int size = list[here].size();
//                if (size > 1) {
//                    size = size * (size - 1) / 2;
//                    sum += size;
//                }
//            } else {
//                int size = list[here].size() - 1;
//                int rootSize = list[start].size() - 1;
//                if (size >= 1) {
//                    size = size * rootSize;
//                    sum += size;
//                }
//            }
//
//            //System.out.println("here " + here +  " sum " + sum);
//            for (int there : list[here]) {
//                // 처음 발견된 정점이면 큐에 넣는다
//                if (visited[there] == false && cut != there) {
//                    queue.add(there);
//                    visited[there] = true;
//                }
//            }
//        }
//        return sum;
//    }
//
//
//}



import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 9. 22..
 */
public class P4_절단중심_Set2 {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201709/P4/Set2.in"; // path from root
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

        long maxCnt = 0;

        public long setCutCount() {
            //int root = 0;
            //for (int i = 1; i < V; i++) {
            //    if (isChild[i] == false) { // 단한번도 child로 등장 안했다면
            //        root = i;
            //        break;
            //    }
            //}

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

            // sub root children 개수
            int[] subRootChildren = new int[subRootSize];
            bfs(root, root+1);
            for (int i = 0; i < subRootSize; i++) {
                int there = adjList[root].get(i); // sub root
                Arrays.fill(visited, false);
                //if (adjList[there].size() > 0)
                    //subRootChildren[i] = bfs(root, there);
            }

            // 분할되는 카운트
            for (int i = 0; i < subRootSize; i++) {
                for (int j = i + 1; j < subRootSize; j++) {
                    sum += subRootChildren[i] * subRootChildren[j];
                }
            }

            //System.out.println("root " + root + " subRoot " + (subRootSize - 1) + " " + Arrays.toString(subRootChildren) + " sum " + sum);

            return sum;
        }

        // sub root에서 시작해서 sub root의 총 자식수를 cnt로 리턴한다
        int dfs(int root, int here) {
            visited[here] = true; // 방문 시작 시점
            int cnt = 0;
            for (int there : adjList[here]) { // here를 루트로 하는 서브트리 자식, here에 인접한 정점들 there
                if (visited[there] == false && there != root) {
                    cnt += dfs(root, there) + 1;
                }
            }
            return cnt;
        }

        int bfs(int root, int start) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(new Node(start, start, 1));
            visited[start] = true;
            int cnt = 0;

            while (!queue.isEmpty()) {
                Node node = queue.poll(); // retrieve and remove
                int here = node.there;
                int subRoot = node.subRoot;
                int depth = node.depth;

                if (adjList[here].size() == 1 && root != here) { // if leaf
                    cnt += depth;
                }

                for (int there : adjList[here]) {
                    // 처음 발견된 정점이면 큐에 넣는다
                    if (visited[there] == false && root != there) {
                        visited[there] = true;
                        queue.add(new Node(there, subRoot, depth + 1));
                    }
                }
            }

            return cnt;
        }

        class Node {
            int there;
            int subRoot;
            int depth;

            Node(int there, int subRoot, int depth) {
                this.there = there;
                this.subRoot = subRoot;
                this.depth = depth;
            }
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

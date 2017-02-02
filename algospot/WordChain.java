import java.io.*;
import java.util.ArrayList;

/**
 * Created by cutececil on 2017. 2. 1..
 */
public class WordChain {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] input = new String[N];
            for (int i = 0; i < N; ++i) {
                input[i] = br.readLine();
            }

            Graph g = new Graph(26); // alphabet cnt
            g.makeGraph(input);

            ArrayList<Integer> circuit = g.getEulerTrailOrCircuit();

            System.out.println(circuit.toString());
        }

        br.close();
        out.close();
    }

    static class Graph {
        int[][] adjMatrix;
        // boolean[] visited;
        int V;
        int E;
        String[][] wordMatrix;
        int[] indegree;
        int[] outdegree;

        Graph(int V) {
            this.V = V;
            adjMatrix = new int[V][V];
            // visited = new boolean[V];
        }

        // directed graph
        void makeGraph(String[] input) {
            wordMatrix = new String[V][V];
            indegree = new int[V];
            outdegree = new int[V];

            for (int i = 0; i < input.length; ++i) {
                String str = input[i];
                int a = str.charAt(0) - 'a';
                int b = str.charAt(str.lastIndexOf(str)) - 'a';
                wordMatrix[a][b] = str;
                adjMatrix[a][b]++;
                outdegree[a]++;
                indegree[b]++;
            }
        }

//        void addEdge(int v, int w) {
//            if (!adjMatrix[v][w]) E++;
//            adjMatrix[v][w] = true;
//            adjMatrix[w][v] = true;
//        }

        void getEulerCircuit(int here, ArrayList<Integer> circuit) {
            for (int there = 0; there < V; ++there) {
                while (adjMatrix[here][there] > 0) {
                    adjMatrix[here][there]--;
                    getEulerCircuit(there, circuit);
                }
            }
            circuit.add(here);
        }

        // trail or circuit list visiting vertexes - return trail list first, if it's not trail, circuit list will be returned.
        // trail start point -> v : out = in + 1
        // circuit starts anywhere having outdegree
        ArrayList<Integer> getEulerTrailOrCircuit() {
            ArrayList<Integer> circuit = new ArrayList<>(V);

            // check trail
            for (int i = 0; i < V; ++i) {
                if (indegree[i] + 1 == outdegree[i]) { // this vertex should be started
                    getEulerCircuit(i, circuit);
                    return circuit;
                }
            }

            // Trail would be returned above
            // Circuit - any vertex can be started.
            for (int i = 0; i < V; ++i) {
                if (outdegree[i] > 0) {
                    getEulerCircuit(i, circuit);
                }
                return circuit;
            }

            return circuit;
        }

//        void dfs(int here) {
//            visited[here] = true;
//
//            for (int there = 0; there < V; ++there) {
//                if (adjMatrix[here][there] > 0 && !visited[there]) {
//                    dfs(there);
//                }
//            }
//        }
//
//        void dfsAll() {
//            for (int i = 0; i < V; i++) {
//                if (visited[i] == false)
//                    dfs(i);
//            }
//        }
    }
}

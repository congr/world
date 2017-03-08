import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            for (int i = 0; i < N; ++i) input[i] = br.readLine();

            Graph g = new Graph(26); // alphabet cnt
            g.makeGraph(input);

            ArrayList<String> words = solve(g, input);
            if (words == null || words.size() == 0) out.println("IMPOSSIBLE");
            else {
                for (String s : words)
                    out.print(s + " ");
                out.println();
            }
        }

        br.close();
        out.close();
    }

    static ArrayList<String> solve(Graph g, String[] input) {
        // 각 차수를 검사
        if (!g.checkEuler()) return null;

        ArrayList<Integer> circuit = g.getEulerTrailOrCircuit();
        if (circuit.size() != input.length + 1) return null; // circuit: a -> b -> c, input[]: {ab, bc}

        // sort as reverse order
        Collections.reverse(circuit);

        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < circuit.size() - 1; i++) {
            int a = circuit.get(i);
            int b = circuit.get(i + 1);
            List<String> list = g.wordMatrix[a][b]; // a -> b {aoob, aob, ab}
            int lastIndex = list.size() - 1;
            String str = list.get(lastIndex);
            list.remove(lastIndex); // list - if remove head, memory changes a lot

            if (str == null || str.length() < 2) return null;// no need
            words.add(str);
        }

        if (words.size() != input.length) return null; // no need

        return words;
    }

    static class Graph {
        int[][] adjMatrix;
        List<String>[][] wordMatrix; // word는 하나의 String이 아니라 list여야 한다
        int[] indegree;
        int[] outdegree;
        int V; // vertex cnt

        Graph(int V) {
            this.V = V;
            adjMatrix = new int[V][V];
        }

        // directed graph
        void makeGraph(String[] input) {
            wordMatrix = new List[V][V]; // wordMatrix [a][b] -> word list {aa, aba, abca, ...}
            indegree = new int[V];
            outdegree = new int[V];

            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    wordMatrix[i][j] = new ArrayList<String>();
                }
            }

            // build graph with adding edge cnt (directed graph)
            for (int i = 0; i < input.length; ++i) {
                String str = input[i];
                int a = str.charAt(0) - 'a';
                int b = str.charAt(str.length() - 1) - 'a';
                wordMatrix[a][b].add(str); // add input string into List
                adjMatrix[a][b]++;
                outdegree[a]++;
                indegree[b]++;
            }
        }

        void getEulerCircuit(int here, ArrayList<Integer> circuit) {
            for (int there = 0; there < V; ++there) {
                while (adjMatrix[here][there] > 0) { // ** while **
                    adjMatrix[here][there]--; // remove connected edge cnt
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
                if (outdegree[i] > 0 /*&& indegree[i] == outdegree[i]*/) {
                    getEulerCircuit(i, circuit);
                    return circuit;
                }
            }

            return circuit;
        }

        // check in/out degree and return true if it can be euler trail or circuit
        boolean checkEuler() {
            int plus1 = 0;
            int minus1 = 0;
            for (int i = 0; i < V; i++) {
                int delta = outdegree[i] - indegree[i];

                // every vertex should have -1 or 0 or 1
                if (delta < -1 || 1 < delta) return false;
                if (delta == 1) plus1++;
                if (delta == -1) minus1++;
            }

            // 시작점과 끝점은 각 하나씩 있거나 하나도 없어야 한다
            return (plus1 == 1 && minus1 == 1) || (plus1 == 0 && minus1 == 0);
        }
    }
}

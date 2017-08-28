import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 8. 27..
 */
// 2015 본선 3번 삼총사 만들
public class P3_TriangleGraph {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201509/P3/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        // logic starts here
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt(); //edge cnt
            
            EdgeWeightedGraph g = new EdgeWeightedGraph(N + 1);
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                g.addEdge(u, v, 1);
            }
            
            int result = solve(g);
            
            System.out.println(result);
            wr.write(result + "\n");
        }
        
        sc.close();
        wr.close();
    }
    
    static int solve(EdgeWeightedGraph g) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        int maxCnt = 0;
        
        for (int i = 1; i < g.V; i++) {
            int u = i;
            for (int j = 0; j < g.adjList[u].size(); j++) {
                int v = g.adjList[u].get(j);
                
                if (u == v) continue;
                
                for (int k = 0; k < g.adjList[v].size(); k++) {
                    int w = g.adjList[v].get(k);
                    
                    if (u == w || v == w) continue;
                    
                    Object[] warr = g.adjList[w].toArray();
                    int mid = Arrays.binarySearch(warr, u);
                    if (mid < 0) {// not found - 삼총사가 될 수 있으므로 map 에 저장, 이미 삼총사면 연결이 필요 없다
                        // u - w 를 연결하면 삼총사가 된다. 즉 동일한 u-w 연결로 인해 최대 몇개 삼총사가 연결되는가, 개수를 출력하는 문제
                        
                        // u - v - w 가 이미 카운트 되었다면 스킵하도록 hashtable에 저장 ( 236, 632 동일하므로 한번만 카운트 하도록
                        int[] temp = new int[]{u, v, w};
                        Arrays.sort(temp);
                        if (hashtable.getOrDefault(Arrays.toString(temp), 0) == 0) {
                            hashtable.put(Arrays.toString(temp), 1);
                            //System.out.println(Arrays.toString(temp));
                            
                            String key = Math.min(u, w) + "," + Math.max(u, w);
                            int cnt = map.getOrDefault(key, 0) + 1;
                            map.put(key, cnt);
                            //System.out.println(u + " " + v + " " + w + " " + key + " cnt " + cnt);
                            if (maxCnt < cnt) {
                                maxCnt = cnt;
                            }
                        }
                    }
                }
            }
        }
        
        return maxCnt;
    }
    
    static class EdgeWeightedGraph { // int or double?
        ArrayList<Integer>[] adjList;
        int V; // Vertex Cnt
        
        EdgeWeightedGraph(int V) {
            this.V = V;
            
            adjList = (ArrayList<Integer>[]) new ArrayList[V];
            for (int i = 0; i < V; ++i) adjList[i] = new ArrayList<>();
        }
        
        // undirected graph - add Edge
        public void addEdge(int u, int v, long w) {
            adjList[u].add(v);
            adjList[v].add(u); // if directed graph, remove one.
        }
    }
}

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class TSP1 {
    static final int MAX = 8;
    static int N;
    static double[][] dist = new double[MAX][MAX];

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int C = sc.nextInt();
        while (C-- > 0) {
            N = sc.nextInt();
            double ret = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++)
                    dist[i][j] = sc.nextDouble();
            }

            ArrayDeque<Integer> path = new ArrayDeque<Integer>();
            boolean [] visited = new boolean [MAX];
            path.add(0);
            visited[0] = true;
            ret = shortestPath(path, visited, 0);
            System.out.println(ret);
        }
        sc.close();
    } 

    static double shortestPath(ArrayDeque<Integer> path, boolean[] visited, double currentLength) {
        System.out.println(path);
        // base case
        if (path.size() == N) 
            return currentLength;// + dist[path.getFirst()][path.getLast()];
        
        double ret = Double.MAX_VALUE;
        
        // 다음 방문할 도시를 모두 방문해본다.
        for (int next =0; next<N; next++) {
            if (visited[next]) continue;
            int here = path.getLast();
            path.add(next);
            visited[next] = true;
            
            System.out.println(currentLength +  " "   +dist[here][next]);
            double cand = shortestPath(path, visited, currentLength + dist[here][next]);
            System.out.println(ret +  " "   +cand);
            ret = Math.min(ret, cand);
            visited[next] = false;
            path.removeLast();
        }
        return ret;
    }
}

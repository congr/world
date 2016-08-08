
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FloydWarshall {
	static class Graph {
		public static final int START = 1;
		public static final int MAX = 100001;

		int [][] adjMatrix;
		int vCnt;

		public Graph() {
			//this.vCnt = vCnt;
			adjMatrix = new int[MAX][MAX];
		}
		
		public void setVertexCnt(int vCnt) {
			this.vCnt = vCnt;
		}

		void clear() {
			for (int i = 0; i < vCnt + 1; i++) {
				Arrays.fill(adjMatrix[i], INF);
			}
//			adjMatrix[0][0] = 0;
//			adjMatrix[1][1] = 0;
//			for (int i = START; i < adjMatrix.length; i++) {
//	            for (int j = START; j < adjMatrix.length; j++) {
//	                if (i == j) {
//	                    adjMatrix[i][j] = 0;
//	                    adjMatrix[j][i] = 0;
//	                } else {
//	                    adjMatrix[i][j] = INF;
//	                    adjMatrix[j][i] = INF;
//	                }
//	            }
//	        }
		}

		void addEdge(int v1, int v2, int w) {
			adjMatrix[v1][v2] = w;
			adjMatrix[v2][v1] = w;
		}

		void editEdge(int v1, int v2, int w) {
			if (adjMatrix[v1][v2] > w) { // 더 작은 값이라면 업데이트.
				adjMatrix[v1][v2] = w;
				adjMatrix[v2][v1] = w;
			}
		}

		static final int INF = Integer.MAX_VALUE / 2;

		// precondition: d[i][i] == 0
		public int[][] floydWarshall(int[][] dist) {
			int n = vCnt;
			//int[][] pred = new int[n][n];
//			for (int i = START; i < n; i++)
//				for (int j = START; j < n; j++)
//					;//pred[i][j] = (i != j && dist[i][j] != INF) ? i : -1;
			for (int k = START; k < n; k++) {
				for (int i = START; i < n; i++) {
					if (dist[i][k] == INF)
						continue;
					for (int j = START; j < n; j++) {
						if (dist[k][j] == INF)
							continue;
						if (dist[i][j] > dist[i][k] + dist[k][j]) {
							dist[i][j] = dist[i][k] + dist[k][j];
							dist[i][j] = Math.max(dist[i][j], -INF);
							//pred[i][j] = pred[k][j];
						}
					}
				}
			}
//			for (int i = START; i < n; i++)
//				if (dist[i][i] < 0)
//					return null;

			return null;
		}

		public ArrayList<Integer> getLeafVertices() {
			ArrayList<Integer> al = new ArrayList<Integer>();
			for (int i = START + 1; i < vCnt + 1; i++) {
				int cnt = 0;
				for (int j = START; j < vCnt + 1; j++) {
					if (adjMatrix[i][j] != INF) {
						cnt++;
					}
					if (cnt > 1)
						break;
				}
				if (cnt == 1)
					al.add(i);
			}
			return al;
		}

		void printAdjMatrix() {
			System.out.println("\nprint adjMatrix");
			for (int i = START; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = START; j < vCnt + 1; j++)
					System.out.print(adjMatrix[i][j] + " ");
				System.out.println("");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem_4_Set3.in"));
//		 Scanner sc = new Scanner(new File(args[0]));
//		 String out = args[0].replace("in", "out");
//		 FileWriter wr = new FileWriter(new File(out));
	        
		int tc = sc.nextInt();
		Graph g = new Graph();
		while (tc-- > 0) {
			int N = sc.nextInt();
			int Q = sc.nextInt();

			g.setVertexCnt(N);
			g.clear();
			
			for (int i = 1; i < N; i++) { // N-1개의 Edge
				g.addEdge(i + 1, sc.nextInt(), sc.nextInt());
			}

			// edge 개수가 1인 leaf node의 개수가 M
			ArrayList<Integer> vertices = g.getLeafVertices();
			int M = vertices.size();
			int[] tempWeight = new int[M];
			for (int i = 0; i < M; i++) { //
				tempWeight[i] = sc.nextInt();
			}

			int[] qv = new int[Q];
			int[] qu = new int[Q];
			int prevSum = 0, afterSum = 0;
			for (int i = 0; i < Q; i++) {
				qv[i] = sc.nextInt();
				qu[i] = sc.nextInt();
			}

			
			// g.printAdjMatrix();
			g.floydWarshall(g.adjMatrix);

			// prev
			for (int i = 0; i < Q; i++) {
				int dist = g.adjMatrix[qv[i]][qu[i]];
				if (qv[i] == qu[i]) 
					dist = 0;
//				System.out.println(qv[i] + " " + qu[i] + " " + dist);
				prevSum += dist;
			}

			// after - M개 점을 1과 연결.
			for (int i = 0; i < M; i++) { //
				g.editEdge(1, vertices.get(i), tempWeight[i]);
			}

			// g.printAdjMatrix();
			g.floydWarshall(g.adjMatrix);

			for (int i = 0; i < Q; i++) {
				int dist = g.adjMatrix[qv[i]][qu[i]];
				if (qv[i] == qu[i]) 
					dist = 0;
//				System.out.println(qv[i] + " " + qu[i] + " " + dist);
				afterSum += dist;
			}

			String ret = prevSum + " " + afterSum;
//			 wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
//		 wr.close();
	}

}

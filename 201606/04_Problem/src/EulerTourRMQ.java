import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class EulerTourRMQ {
	public static final int START = 1;
	public static final long INF = Long.MAX_VALUE;

	static public class Edge {
		int v;
		long w;

		public Edge(int v, long w) {
			this.v = v;
			this.w = w;
		}

		public String toString() {
			return v + " (" + w + ")";
		}
	}

	static class Graph {
		int vCnt;
		private ArrayList<Edge>[] adjList;// 인접 리스트.

		public Graph(int max) {
			adjList = new ArrayList[max + 1];

			this.vCnt = max;
			for (int i = START; i < vCnt + 1; i++) {
				adjList[i] = new ArrayList<Edge>();
			}
		}

		void addEdge(int u, int v, long w) {
			adjList[u].add(new Edge(v, w));
			adjList[v].add(new Edge(u, w));
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = START; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjList[i].size(); j++)
					System.out.print(adjList[i].get(j).toString() + " ");
				System.out.println("");
			}
		}

		public ArrayList<Integer> getLeafVertices() {
			ArrayList<Integer> leaves = new ArrayList<Integer>();
			for (int i = 1 + 1; i < vCnt + 1; i++) {// 1번도시제외.
				if (adjList[i].size() == 1) {
					leaves.add(i);
				}
			}
			return leaves;
		}
	}

	static class Dijkstra {
		static private int[] edgeTo; // edgeTo[v] prev edge on shortest s-v
		static private long[] distTo; // distTo[v] weight of edges shortest s-v

		static public long[] goDijkstra(Graph g, int s) {
			// System.out.println("goDijkstra " + s);
			edgeTo = new int[g.vCnt + 1];
			distTo = new long[g.vCnt + 1];

			Arrays.fill(edgeTo, 0, g.vCnt + 1, -1);
			Arrays.fill(distTo, 0, g.vCnt + 1, INF);

			distTo[s] = 0;
			PriorityQueue<Long> q = new PriorityQueue<>();
			q.add((long) s);
			while (!q.isEmpty()) {
				long cur = q.remove();
				int curu = (int) cur;
				if (cur >>> 32 != distTo[curu])
					continue;
				for (Edge e : g.adjList[curu]) {
					int v = e.v;
					long nprio = distTo[curu] + e.w;
					if (distTo[v] > nprio) {
						distTo[v] = nprio;
						edgeTo[v] = curu;
						if (nprio >= INF)
							System.out.println("xxxx");
						;
						q.add(((long) nprio << 32) + v);
					}
				}
			}

			if (debug)
				printShortestPath(g);
			return distTo;
		}

		static void printShortestPath(Graph g) {
			System.out.println("\nprint ShortestPath");
			for (int i = START; i < g.vCnt + 1; i++) {
				System.out.println("To " + i + " edgeTo:" + edgeTo[i] + " distTo: " + distTo[i]);
			}
		}

	}

	static class Euler {
		boolean[] visited;
		int eulerTour[]; // 오일러투어를 하면서 방문한 정점을 기록. 실제 정점수 * 2배정도 공간필요.
		int tourInd; // eulerTour 배열 인덱스.
		int eulerLevel[]; // 오일러투어를 하면서 트리 레벨(depth)을 기록.
		int level;
		int firstVisit[]; // 정점을 처음 방문할 때 index
		int parent[]; // 각 정점의 parent를 기록. lca를 이용한 거리를 알아내서 부모방향으로 거슬러 올라가야함.
		int vCnt;

		void goDfs(Graph g, int k, int p) { // 인접리스트 배열의 다음 배열 인덱스 k, k의 parent는
											// p
			visited[k] = true;
			eulerTour[++tourInd] = k;
			eulerLevel[tourInd] = ++level;
			firstVisit[k] = firstVisit[k] == 0 ? tourInd : firstVisit[k];
			parent[k] = p;

			for (int i = 0, len = g.adjList[k].size(); i < len; i++) {
				int adjNext = g.adjList[k].get(i).v;
				if (!visited[adjNext]) {

					goDfs(g, adjNext, k);
					eulerTour[++tourInd] = k;
					eulerLevel[tourInd] = --level;
					firstVisit[k] = firstVisit[k] == 0 ? tourInd : firstVisit[k];
				}
			}
		}

		// 오일러투어를 DFS로 수해서 오일러투어 Array와 level[]를 채우고, 각 정점이 처음 방문된
		// firstVisit[]을 만든다. level[]로 segment Tree 빌드해서 lca를 찾을때 빠름.
		SegmentTree stree;

		void goEuler(Graph g) {
			vCnt = g.vCnt;
			eulerTour = new int[vCnt * 2 + 1];
			eulerLevel = new int[vCnt * 2 + 1];
			firstVisit = new int[vCnt + 1];
			parent = new int[vCnt + 1];
			visited = new boolean[vCnt + 1];
			tourInd = 0;
			level = 0;

			goDfs(g, 1, 1);

			if (debug)
				printEuler();

			// 레벨을 이용해 세그먼트 트리로 최소레벨값을 찾는 RMQ를 할 수 있도록한다.
			stree = new SegmentTree(eulerLevel);
		}

		// 오일러 투어 결과 프린트.
		void printEuler() {
			System.out.println("\neulerTour ");
			for (int i = START, len = eulerTour.length; i < len; i++) {
				if (eulerTour[i] == 0)
					break;
				System.out.print(eulerTour[i] + " (" + eulerLevel[i] + ") ");
			}

			System.out.println("\nfirst occurrence");
			for (int i = START, len = vCnt + 1; i < len; i++) {
				System.out.print(firstVisit[i] + " ");
			}

			System.out.println("\nparent");
			for (int i = START, len = vCnt + 1; i < len; i++) {
				System.out.print(i + " (" + parent[i] + ") ");
			}
		}

		int queryLca(int from, int to) {
			// System.out.println("\nquery " + from + " " + to);
			int startRange = Math.min(firstVisit[from], firstVisit[to]);
			int endRange = Math.max(firstVisit[from], firstVisit[to]);

			// Least Minimum Ancestor (lca)
			int lowestLevel = stree.RMinQ(startRange, endRange);// 최소값 RMQ from
																// segmentTree
			int lcaIndex = findIndex(lowestLevel, startRange, endRange);

			return eulerTour[lcaIndex];
		}

		// TODO : segment tree build시 leaf노드의 index를 카피하도록 하자.
		// segment tree 에서 최소값을 갖는 인덱스는 찾을 수 없어서, 최소값을 갖는 배열의 인덱스를 다시 찾는다.
		int findIndex(int target, int fromInd, int toInd) {
			for (int i = fromInd; i <= toInd; i++) {
				if (target == eulerLevel[i])
					return i;
			}
			return -1;
		}
	}

	public static void main(String[] args) throws Exception {
		// Scanner sc = new Scanner(new File("problem_4_Set4.in"));
		// FileWriter wr = new FileWriter(new File("problem_4_Set4.out"));
		Scanner sc = new Scanner(new File(args[0]));
		String out = args[0].replace("in", "out");
		FileWriter wr = new FileWriter(new File(out));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int N = sc.nextInt();
			int Q = sc.nextInt();

			// 0. 그래프 입력.
			Graph g = new Graph(N);
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

			if (debug)
				g.printAdjList();

			// 1. 오일러 투어를 해서 각 노드의 깊이를 저장해서 s-d 간 lca를 RMQ할 수 있게 준비.
			Euler euler = new Euler();
			euler.goEuler(g);

			// 2. 다익스트라로 1번 루트로 부터 모든 정점에 최단 거리를 모두 저장. (int는 overflow발)
			long[] dij = Dijkstra.goDijkstra(g, 1);

			// 3.300만건 이상 되는 s-d 거리 쿼리 입력받아, 다리 연결전 최단 거리 비용 출력.
			long prevSum = 0, afterSum = 0; // 다리 연결 전, 후 결과 값.
			ArrayList<Query> qList = new ArrayList();
			for (int i = 0; i < Q; i++) {
				int a = sc.nextInt();
				int b = sc.nextInt();
				if (a == b)
					continue; // 같다면 넣지마.- 어차피 결과가 0이라서 더할 필요 없음.

				int s = Math.min(a, b);
				int d = Math.max(a, b);
				int lca = euler.queryLca(s, d); // 오일러투어 인덱스를 이용해 lca구함.
				long sum = dij[s] + dij[d] - 2 * dij[lca]; // s-d cost using lca
				qList.add(new Query(s, d, sum)); // s-d 의 query결과를 sum 과 함께 저
				prevSum += sum; // prevSum total
				println("prev " + s + " " + d + " " + lca + " " + sum);
			}

			// 4. 다리연결(edge추가)
			// after - M개 점을 1과 연결.
			for (int i = 0; i < M; i++) { //
				g.addEdge(1, vertices.get(i), tempWeight[i]);
			}

			if (debug)
				g.printAdjList();

			// 5. 추가된 간선이 있어서 다익스트라 최단거리 다시 찾음.
			// g.goEuler(); // 트리 depth 자체가 변하지 않아 다시 할 필요없음.
			dij = Dijkstra.goDijkstra(g, 1);

			// 6.s-d의 최단비용 저장해둔 값(q.dist)과, 다리 연결 후의 값 중 작은 값을 채택해 결과 출력.
			// 1번 루트까지 s, d가 연결되어 있어서 lca는 루트라서, 2*dij[lca]를 뺄필요 없다. 
			// 원래 두 노드간 거리 공식은 dij[s] + dij[d] - 2*dij[lca]
			for (Query q : qList) {
				afterSum += Math.min(q.dist, dij[q.src] + dij[q.dst]);
				println("after " + q.dist + " " + (dij[q.src] + dij[q.dst]));
			}

			// 결과 출력.
			String ret = prevSum + " " + afterSum;
			wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
		wr.close();
	}

	static class Query {
		int src, dst;
		long dist;

		public Query(int a, int b, long d) {
			this.src = a;
			this.dst = b;
			this.dist = d;
		}

		public String toString() {
			return " " + src + " " + dst;
		}

		void printQList(ArrayList<Query>[] qList) {
			for (int i = 1, l = qList.length; i < l; i++) {
				if (qList[i].isEmpty())
					continue;

				for (Query q : qList[i]) {
					println(q.toString());
				}
				println(" ");
			}
		}
	}

	static boolean debug = false;

	static void println(String x) { // DFS 에 쓰면 stackoverflow주의.
		if (debug)
			System.out.println(x);
	}

	void print(String x) {
		if (debug)
			System.out.print(x + " ");
	}

	// 출처 :
	// http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/SegmentTree.java.html
	static public class SegmentTree {
		private Node[] heap;
		private int[] array;
		private int size;

		public SegmentTree(int[] array) {
			this.array = Arrays.copyOf(array, array.length);
			// The max size of this array is about 2 * 2 ^ log2(n) + 1
			size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
			heap = new Node[size];
			build(1, 0, array.length);
		}

		// Initialize the Nodes of the Segment tree
		private void build(int v, int from, int size) {
			heap[v] = new Node();
			heap[v].from = from;
			heap[v].to = from + size - 1;

			if (size == 1) {
				heap[v].sum = array[from];
				heap[v].min = array[from];
				heap[v].minOwner = array[from];
			} else {
				// Build childs
				build(2 * v, from, size / 2);
				build(2 * v + 1, from + size / 2, size - size / 2);

				heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
				// min = min of the children
				heap[v].min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);

				// minOwner = min값을 갖고 있는 child의 원래 배열 인덱스.
				if (heap[2 * v].min > heap[2 * v + 1].min)
					heap[v].minOwner = heap[2 * v + 1].minOwner;
				else {
					heap[v].minOwner = heap[2 * v].minOwner;
				}
			}
		}

		public int RMinQ(int from, int to) {
			return RMinQ(1, from, to);
		}

		private int RMinQ(int v, int from, int to) {
			Node n = heap[v];

			// If you did a range update that contained this node, you can infer
			// the Min value without going down the tree
			if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
				return n.pendingVal;
			}

			if (contains(from, to, n.from, n.to)) {
				return heap[v].min;
			}

			if (intersects(from, to, n.from, n.to)) {
				propagate(v);
				int leftMin = RMinQ(2 * v, from, to);
				int rightMin = RMinQ(2 * v + 1, from, to);

				return Math.min(leftMin, rightMin);
			}

			return Integer.MAX_VALUE;
		}

		// Propagate temporal values to children
		private void propagate(int v) {
			Node n = heap[v];

			if (n.pendingVal != null) {
				change(heap[2 * v], n.pendingVal);
				change(heap[2 * v + 1], n.pendingVal);
				n.pendingVal = null; // unset the pending propagation value
			}
		}

		// Save the temporal values that will be propagated lazily
		private void change(Node n, int value) {
			n.pendingVal = value;
			n.sum = n.size() * value;
			n.min = value;
			array[n.from] = value;

		}

		// Test if the range1 contains range2
		private boolean contains(int from1, int to1, int from2, int to2) {
			return from2 >= from1 && to2 <= to1;
		}

		// check inclusive intersection, test if range1[from1, to1] intersects
		// range2[from2, to2]
		private boolean intersects(int from1, int to1, int from2, int to2) {
			return from1 <= from2 && to1 >= from2 // (.[..)..] or (.[...]..)
					|| from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
		}

		// The Node class represents a partition range of the array.
		public class Node {
			int sum;
			int min;
			// Here We store the value that will be propagated lazily
			Integer pendingVal = null;
			int from;
			int to;
			int minOwner; // min값을 갖는 최초 배열 인덱스. min값 주인.

			int size() {
				return to - from + 1;
			}

		}
	}
}


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File(args[0]));
		String out = args[0].replace("in", "out");
		FileWriter wr = new FileWriter(new File(out));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();

			Node[] tree = new Node[N + 1];
			for (int i = 1; i < N + 1; i++) {
				tree[i] = new Node(i);
			}

			for (int i = 0; i < N - 1; i++) { // edge
				int p = sc.nextInt();
				int c = sc.nextInt();

				tree[p].addChild(c);
				tree[c].parent = p;
			}

			int root = -1;
			for (int i = 1; i < N + 1; i++) {
				if (tree[i].parent == 0) {
					root = i;
					break;
				}
			}

			// File write
			int ret = height(tree, root); // root
			wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
		wr.close();
	}

	static int height(Node[] tree, int node) {
		int h = 0;

		// 자식이 없다면 h =0 return; - base case
		for (int child : tree[node].children) {
			h = Math.max(h, height(tree, child));
		}

		return h+1;
	}

	static class Node {
		ArrayList<Integer> children = new ArrayList<Integer>();
		int parent;
		int self;

		public Node(int self) {
			this.self = self;
		}

		// 자식은 여러개.
		void addChild(int ind) {
			children.add(ind);
		}

		// 부모는 하나.
		void setParent(int ind) {
			parent = ind;
		}

		public String toString() {
			return "" + parent;
		}

	}
}

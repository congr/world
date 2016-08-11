
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	static int N;

	public static void main(String[] args) throws Exception {
		// Scanner sc = new Scanner(new File("problem_5_Set1.in"));
		Scanner sc = new Scanner(new File(args[0]));
		String out = args[0].replace("in", "out");
		FileWriter wr = new FileWriter(new File(out));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			N = sc.nextInt();
			int ret = 0;

			PriorityQueue<Node> pq = new PriorityQueue<Node>(50, (a, b) -> b.value - a.value);
			PriorityQueue<Node> minPq = new PriorityQueue<Node>(50, (a, b) -> a.value - b.value);
			CircularList<Node> al = new CircularList<Node>();
			for (int i = 0; i < N; i++) {
				al.add(new Node(i, sc.nextInt())); // pos, value
			}

			for (int i = 0; i < N; i++) {
				int gen = sc.nextInt();
				al.get(i).gender = gen;
				if (gen == 1) { // 암컷일 경우 pq에..
					pq.add(al.get(i));
					minPq.add(al.get(i));
				}
			}

			ret = Math.max(solve(al, pq), solve(al, minPq));

			wr.write(ret + "\n");
			System.out.println(ret);
		}

		sc.close();
		wr.close();
	}

	static int solve(CircularList<Node> al, PriorityQueue<Node> pq) {
		int result = 0;
		for (Node node : al) {
			if (node.gender == 2)
				node.gender = 0;
		}

		// System.out.println("----------");
		// for (Node node : al) {
		// System.out.print(node + "");
		// }
		// System.out.println();

		while (!pq.isEmpty()) {
			Node node = pq.poll();

			int rmax = 0, lmax = 0, r = 0, l = 0;
			boolean lDisable = false, rDisable = false; // 방향.
			Node rEnd = null, lEnd = null;

			Node prev = al.get(node.position - 1);
			Node next = al.get(node.position + 1);
			if (al.size() > 3 && prev.gender == 1) {
				lDisable = true;
				lEnd = al.get(prev.position - 1);
			}
			if (al.size() > 3 && next.gender == 1) {
				rDisable = true;
				rEnd = al.get(next.position + 1);
			}

			// find right side
			if (!rDisable) {
				int right = node.position + 1;
				for (int i = right;; i++) {
					Node other = al.get(i);
					if (lDisable && other == lEnd)
						break;
					if (other.gender > 0)
						break;
					if (rmax < other.value) {
						rmax = other.value;
						r = i;
					}
				}
			}

			// find left side
			if (!lDisable) {
				int left = node.position - 1;
				for (int i = left;; i--) {
					Node other = al.get(i);
					if (rDisable && other == rEnd)
						break;
					if (other.gender > 0)
						break;
					if (lmax < other.value) {
						lmax = other.value;
						l = i;
					}
				}
			}

			int max = 0;
			if (lmax > rmax) {
				al.get(l).gender = 2;
				max = lmax;
			} else if (lmax < rmax) {
				al.get(r).gender = 2;
				max = rmax;
			} else { // same
				// int m = Math.min(Math.abs(node.position - l),
				// Math.abs(node.position - r));
				// al.get(m).gender = 2;
				// max = lmax;
			}

			// System.out.println(node.value + " " + max + " = "+ (node.value *
			// max));

			result += (node.value * max);
			// System.out.println("result = "+result );
		}

		return result;
	}

	static class Node {
		int position;
		int value;
		int gender;

		public Node(int position, int value) {
			this.value = value;
			this.position = position;
		}

		void setGender(int g) {
			this.gender = g;
		}

		public String toString() {
			return value + "(" + gender + ") ";
		}

	}

	static class CircularList<E> extends ArrayList<E> {

		@Override
		public E get(int index) {
			if (0 <= index && index < size())
				return super.get(index);
			else if (index >= size())
				return super.get(index - size());
			else// negative case
				return super.get(size() + index);
		}
	}
}

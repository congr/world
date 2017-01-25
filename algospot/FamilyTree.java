import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cutececil on 2017. 1. 25..
 */
public class FamilyTree {

    static class Node {
        int data;
        int depth;
        ArrayList<Node> children = new ArrayList<>();

        Node(int data) {
            this.data = data;
            this.depth = 0;
        }

        void addChild(Node child) {
            children.add(child);
        }

        public String toString() {
            return data + "(" + depth + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] NQ = br.readLine().split(" ");
            int N = Integer.parseInt(NQ[0]);
            int Q = Integer.parseInt(NQ[1]);

            String[] P = br.readLine().split(" ");
            int[] parents = new int[N];
            parents[0] = 0; // root's parent - just set 0


            // build tree
            ArrayList<Node> tree = new ArrayList<>();
            for (int i = 0; i < N; i++) tree.add(new Node(i));

            for (int i = 0; i < N - 1; i++) {
                parents[i + 1] = Integer.parseInt(P[i]);
                //Node self = new Node(i+1);
                //tree.add (self);
                tree.get(parents[i + 1]).addChild(tree.get(i + 1));
            }

            // set each node's depth
            tour(tree.get(0));
            out.println("tree" + tree.toString());

            SegmentTree st = new SegmentTree(parents);
            for (int i = 0; i < Q; i++) {
                String[] query = br.readLine().split(" ");
                int a = Integer.parseInt(query[0]);
                int b = Integer.parseInt(query[1]);

                if (a == b) out.println(0);
                else {
                    int from = Math.min(a, b);
                    int to = Math.max(a, b);
                    int lca = st.rMinQ(from, to); // find LCA

                    int fromDepth = tree.get(from).depth;
                    int toDepth = tree.get(to).depth;
                    int lcaDepth = tree.get(lca).depth;
                    int totalDist = fromDepth + toDepth - 2 * lcaDepth;
                    out.println(totalDist);
                }
            }

            h = 0;
        }

        br.close();
        out.close();
    }

    static int h = 0;

    // set node's depth (root depth: 0)
    static void tour(Node root) {
        for (Node node : root.children) {
            node.depth = ++h;
            tour(node);
            h--;
        }
    }

    // Do not travel at every query
    /*static int findPath(int[] parents, int child) {
        int dist = 0;
        int pIndex = child;

        while (pIndex > 0) {
            pIndex = parents[pIndex];
            dist++;
        }

        return dist;
    }*/

    static public class SegmentTree {

        private SegmentTree.Node[] heap;
        private int[] array;
        private int size;

        /**
         * Time-Complexity:  O(n*log(n))
         *
         * @param array the Initialization array
         */
        public SegmentTree(int[] array) {
            this.array = Arrays.copyOf(array, array.length);
            //The max size of this array is about 2 * 2 ^ log2(n) + 1
            size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
            heap = new SegmentTree.Node[size];
            build(1, 0, array.length);
        }


        public int size() {
            return array.length;
        }

        //Initialize the Nodes of the Segment tree
        private void build(int v, int from, int size) {
            heap[v] = new SegmentTree.Node();
            heap[v].from = from;
            heap[v].to = from + size - 1;

            if (size == 1) {
                heap[v].sum = array[from];
                heap[v].min = array[from];
                heap[v].max = array[from];
            } else {
                //Build childs
                build(2 * v, from, size / 2);
                build(2 * v + 1, from + size / 2, size - size / 2);

                heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
                //min = min of the children
                heap[v].min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
                heap[v].max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
            }
        }

        /**
         * Range Sum Query
         * <p>
         * Time-Complexity: O(log(n))
         *
         * @param from from index
         * @param to   to index
         * @return sum
         */
        public int rsq(int from, int to) {
            return rsq(1, from, to);
        }

        private int rsq(int v, int from, int to) {
            SegmentTree.Node n = heap[v];

            //If you did a range update that contained this node, you can infer the Sum without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                return (to - from + 1) * n.pendingVal;
            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].sum;
            }

            if (intersects(from, to, n.from, n.to)) {
                propagate(v);
                int leftSum = rsq(2 * v, from, to);
                int rightSum = rsq(2 * v + 1, from, to);

                return leftSum + rightSum;
            }

            return 0;
        }

        /**
         * Range Min Query
         * <p>
         * Time-Complexity: O(log(n))
         *
         * @param from from index
         * @param to   to index
         * @return min
         */
        public int rMinQ(int from, int to) {
            return rMinQ(1, from, to);
        }

        private int rMinQ(int v, int from, int to) {
            SegmentTree.Node n = heap[v];


            //If you did a range update that contained this node, you can infer the Min value without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                return n.pendingVal;
            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].min;
            }

            if (intersects(from, to, n.from, n.to)) {
                propagate(v);
                int leftMin = rMinQ(2 * v, from, to);
                int rightMin = rMinQ(2 * v + 1, from, to);

                return Math.min(leftMin, rightMin);
            }

            return Integer.MAX_VALUE;
        }

        /**
         * Range Max Query
         * <p>
         * Time-Complexity: O(log(n))
         *
         * @param from from index
         * @param to   to index
         * @return max
         */
        public int rMaxQ(int from, int to) {
            return rMaxQ(1, from, to);
        }

        private int rMaxQ(int v, int from, int to) {
            SegmentTree.Node n = heap[v];


            //If you did a range update that contained this node, you can infer the Min value without going down the tree
            if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
                return n.pendingVal;
            }

            if (contains(from, to, n.from, n.to)) {
                return heap[v].max;
            }

            if (intersects(from, to, n.from, n.to)) {
                propagate(v);
                int leftMax = rMaxQ(2 * v, from, to);
                int rightMax = rMaxQ(2 * v + 1, from, to);

                return Math.max(leftMax, rightMax);
            }

            return Integer.MIN_VALUE;
        }


        /**
         * Range Update Operation.
         * With this operation you can update either one position or a range of positions with a given number.
         * The update operations will update the less it can to update the whole range (Lazy Propagation).
         * The values will be propagated lazily from top to bottom of the segment tree.
         * This behavior is really useful for updates on portions of the array
         * <p>
         * Time-Complexity: O(log(n))
         *
         * @param from  from index
         * @param to    to index
         * @param value value
         */
        public void update(int from, int to, int value) {
            update(1, from, to, value);
        }

        private void update(int v, int from, int to, int value) {

            //The Node of the heap tree represents a range of the array with bounds: [n.from, n.to]
            SegmentTree.Node n = heap[v];

            /**
             * If the updating-range contains the portion of the current Node  We lazily update it.
             * This means We do NOT update each position of the vector, but update only some temporal
             * values into the Node; such values into the Node will be propagated down to its children only when they need to.
             */
            if (contains(from, to, n.from, n.to)) {
                change(n, value);
            }

            if (n.size() == 1) return;

            if (intersects(from, to, n.from, n.to)) {
                /**
                 * Before keeping going down to the tree We need to propagate the
                 * the values that have been temporally/lazily saved into this Node to its children
                 * So that when We visit them the values  are properly updated
                 */
                propagate(v);

                update(2 * v, from, to, value);
                update(2 * v + 1, from, to, value);

                n.sum = heap[2 * v].sum + heap[2 * v + 1].sum;
                n.min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
                n.max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
            }
        }

        //Propagate temporal values to children
        private void propagate(int v) {
            SegmentTree.Node n = heap[v];

            if (n.pendingVal != null) {
                change(heap[2 * v], n.pendingVal);
                change(heap[2 * v + 1], n.pendingVal);
                n.pendingVal = null; //unset the pending propagation value
            }
        }

        //Save the temporal values that will be propagated lazily
        private void change(SegmentTree.Node n, int value) {
            n.pendingVal = value;
            n.sum = n.size() * value;
            n.min = value;
            n.max = value;
            array[n.from] = value;

        }

        //Test if the range1 contains range2
        private boolean contains(int from1, int to1, int from2, int to2) {
            return from2 >= from1 && to2 <= to1;
        }

        //check inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
        private boolean intersects(int from1, int to1, int from2, int to2) {
            return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                    || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
        }

        //The Node class represents a partition range of the array.
        class Node {
            int sum;
            int min;
            int max;
            //Here We store the value that will be propagated lazily
            Integer pendingVal = null;
            int from;
            int to;

            int size() {
                return to - from + 1;
            }

        }
    }
}

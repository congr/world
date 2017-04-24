import java.util.*;

/**
 * Created by cutececil on 2017. 4. 16..
 */
public class SplitPlane {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) { // tc

            int N = in.nextInt();
            ArrayList<SegmentHV> vsegments = new ArrayList<>();
            SegmentHV[] segments = new SegmentHV[N];
            PriorityQueue<Event> pq = new PriorityQueue<>();
            for (int i = 0; i < N; i++) {
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();

                if (x1 > x2 || y1 > y2)
                    segments[i] = new SegmentHV(x2, y2, x1, y1);
                else
                    segments[i] = new SegmentHV(x1, y1, x2, y2);

                if (segments[i].isVertical()) {
                    if (vsegments.size() == 0) vsegments.add(segments[i]);

                    boolean updated = false;
                    for (int j = 0; j < vsegments.size(); j++) {
                        SegmentHV test = vsegments.get(j);
                        if (test.x1 == segments[i].x1) {
                            updated = true;
                            if (test.y1 > segments[i].y1) {
                                test.y1 = segments[i].y1;
                            }

                            if (test.y2 < segments[i].y2) {
                                test.y2 = segments[i].y2;
                            }
                        }
                    }

                    if (updated == false)
                        vsegments.add(segments[i]);
                }
                else if (segments[i].isHorizontal()) {
                    Event e1 = new Event(segments[i].x1, segments[i]);
                    Event e2 = new Event(segments[i].x2, segments[i]);
                    pq.add(e1);
                    pq.add(e2);
                }
            }


            for (int j = 0; j < vsegments.size(); j++) {
                Event e = new Event(vsegments.get(j).x1, vsegments.get(j));
                pq.add(e);
            }

            int result = solve(pq);
            System.out.println(result > 0 ? result+1 : 1);
        }
    }

    static int solve(PriorityQueue<Event> pq) {
        int INFINITY = Integer.MAX_VALUE;   // -INFINITY is second smallest integer

        // run sweep-line algorithm
        RangeSearch<SegmentHV> st = new RangeSearch<SegmentHV>();
        RangeSearch<SegmentHV> vdiv = new RangeSearch<>();
        ArrayList<SegmentHV> keep = new ArrayList<>();

        int total = 0;
        while (!pq.isEmpty()) {
            Event e = pq.remove();
            int sweep = e.time;
            SegmentHV segment = e.segment;

            // vertical segment
            if (segment.isVertical()) {
                // a bit of a hack here - use infinity to handle degenerate intersections
                SegmentHV seg1 = new SegmentHV(-INFINITY, segment.y1, -INFINITY, segment.y1);
                SegmentHV seg2 = new SegmentHV(+INFINITY, segment.y2, +INFINITY, segment.y2);
                Iterable<SegmentHV> list = st.range(seg1, seg2);

                ArrayList<SegmentHV> curradd = new ArrayList<>();
                //System.out.println("Intersection:  " + segment);
                for (SegmentHV seg : list) {
                //    System.out.println("               " + seg);
                    curradd.add(seg);// hor
                }
                int cnt = 0;
                for (SegmentHV curr : curradd) {
                    if (keep.contains(curr)) {
                        keep.remove(curr);
                        cnt++;
                    }
                }
                //ystem.out.println("cnt " + cnt);
                if (cnt >= 2) total += cnt - 1;

                for (SegmentHV curr : curradd) {
                    keep.add(curr);
                }
            }

            // next event is left endpoint of horizontal h-v segment
            else if (sweep == segment.x1) {
                st.add(segment);
            }

            // next event is right endpoint of horizontal h-v segment
            else if (sweep == segment.x2) {
            //    System.out.println("remove " + segment);
                st.remove(segment);
                keep.remove(segment);
            }
        }

        return total;
    }
}

// helper class for events in sweep line algorithm
class Event implements Comparable<Event> {
    int time;
    SegmentHV segment;

    public Event(int time, SegmentHV segment) {
        this.time = time;
        this.segment = segment;
    }

    public int compareTo(Event that) {
        if (this.time < that.time)
            return -1;
        else if (this.time > that.time)
            return +1;
        else {
            // 세로줄과 가로줄이 겹치는데, 세로줄이 겹치는 지점이 시작점 x1일 때는 horizontal 먼저 add하고 끝점 x2일때는 horizontal 나중에 remove하도록
            if (this.segment.x1 == that.segment.x1) {
                if (this.segment.isHorizontal() && that.segment.isVertical()) return -1;
            } else if (this.segment.x2 == that.segment.x1) {
                if (this.segment.isHorizontal() && that.segment.isVertical()) return 1;
            }
            return 0;
        }
    }

    @Override
    public String toString() {
        return segment.toString();
    }
}

class SegmentHV implements Comparable<SegmentHV> {
    public int x1, y1;  // lower left
    public int x2, y2;  // upper right

    // precondition: x1 <= x2, y1 <= y2 and either x1 == x2 or y1 == y2
    public SegmentHV(int x1, int y1, int x2, int y2) {
        if (x1 > x2 || y1 > y2) throw new RuntimeException("Illegal hv-segment");
        if (x1 != x2 && y1 != y2) throw new RuntimeException("Illegal hv-segment");
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    // is this segment horizontal or vertical?
    public boolean isHorizontal() {
        return (y1 == y2);
    }

    public boolean isVertical() {
        return (x1 == x2);
    }

    // compare on y1 coordinate; break ties with other coordinates
    public int compareTo(SegmentHV that) {
        if (this.y1 < that.y1) return -1;
        else if (this.y1 > that.y1) return +1;
        else if (this.y2 < that.y2) return -1;
        else if (this.y2 > that.y2) return +1;
        else if (this.x1 < that.x1) return -1;
        else if (this.x1 > that.x1) return +1;
        else if (this.x2 < that.x2) return -1;
        else if (this.x2 > that.x2) return +1;
        return 0;
    }

    public String toString() {
        String s = "";
        if (isHorizontal()) s = "horizontal: ";
        else if (isVertical()) s = "vertical:   ";
        return s + "(" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")";
    }

}

class RangeSearch<Key extends Comparable<Key>> {

    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        Key key;            // key
        Node left, right;   // left and right subtrees
        int N;              // node count of descendents

        public Node(Key key) {
            this.key = key;
            this.N = 1;
        }
    }

    /***************************************************************************
     *  BST search
     ***************************************************************************/

    public boolean contains(Key key) {
        return contains(root, key);
    }

    private boolean contains(Node x, Key key) {
        if (x == null) return false;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return true;
        else if (cmp < 0) return contains(x.left, key);
        else return contains(x.right, key);
    }

    /***************************************************************************
     *  randomized insertion
     ***************************************************************************/
    public void add(Key key) {
        root = add(root, key);
    }

    // make new node the root with uniform probability
    private Node add(Node x, Key key) {
        if (x == null) return new Node(key);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (StdRandom.bernoulli(1.0 / (size(x) + 1.0))) return addRoot(x, key);
        if (cmp < 0) x.left = add(x.left, key);
        else x.right = add(x.right, key);
        // (x.N)++;
        fix(x);
        return x;
    }


    private Node addRoot(Node x, Key key) {
        if (x == null) return new Node(key);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        } else if (cmp < 0) {
            x.left = addRoot(x.left, key);
            x = rotR(x);
        } else {
            x.right = addRoot(x.right, key);
            x = rotL(x);
        }
        return x;
    }


    /***************************************************************************
     *  deletion
     ***************************************************************************/
    private Node joinLR(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        if (StdRandom.bernoulli((double) size(a) / (size(a) + size(b)))) {
            a.right = joinLR(a.right, b);
            fix(a);
            return a;
        } else {
            b.left = joinLR(a, b.left);
            fix(b);
            return b;
        }
    }

    private Node remove(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) x = joinLR(x.left, x.right);
        else if (cmp < 0) x.left = remove(x.left, key);
        else x.right = remove(x.right, key);
        fix(x);
        return x;
    }

    // remove given key if it exists
    public void remove(Key key) {
        root = remove(root, key);
    }


    /***************************************************************************
     *  Range searching
     ***************************************************************************/

    // return all keys between k1 and k2
    public Iterable<Key> range(Key k1, Key k2) {
        Queue<Key> list = new LinkedList<Key>();
        if (less(k2, k1)) return list;
        range(root, k1, k2, list);
        return list;
    }

    private void range(Node x, Key k1, Key k2, Queue<Key> list) {
        if (x == null) return;
        if (lte(k1, x.key)) range(x.left, k1, k2, list);
        if (lte(k1, x.key) && lte(x.key, k2)) list.add(x.key);
        if (lte(x.key, k2)) range(x.right, k1, k2, list);
    }


    /***************************************************************************
     *  Utility functions
     ***************************************************************************/

    // return the smallest key
    public Key min() {
        Key key = null;
        for (Node x = root; x != null; x = x.left)
            key = x.key;
        return key;
    }

    // return the largest key
    public Key max() {
        Key key = null;
        for (Node x = root; x != null; x = x.right)
            key = x.key;
        return key;
    }


    /***************************************************************************
     *  useful binary tree functions
     ***************************************************************************/

    // return number of nodes in subtree rooted at x
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    // height of tree (empty tree height = 0)
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    /***************************************************************************
     *  helper BST functions
     ***************************************************************************/

    // fix subtree count field
    private void fix(Node x) {
        if (x == null) return;                 // check needed for remove
        x.N = 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotR(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        fix(h);
        fix(x);
        return x;
    }

    // left rotate
    private Node rotL(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        fix(h);
        fix(x);
        return x;
    }


    /***************************************************************************
     *  Debugging functions that test the integrity of the tree
     ***************************************************************************/

    // check integrity of subtree count fields
    public boolean check() {
        return checkCount() && isBST();
    }

    // check integrity of count fields
    private boolean checkCount() {
        return checkCount(root);
    }

    private boolean checkCount(Node x) {
        if (x == null) return true;
        return checkCount(x.left) && checkCount(x.right) && (x.N == 1 + size(x.left) + size(x.right));
    }


    // does this tree satisfy the BST property?
    private boolean isBST() {
        return isBST(root, min(), max());
    }

    // are all the values in the BST rooted at x between min and max, and recursively?
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (less(x.key, min) || less(max, x.key)) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }


    /***************************************************************************
     *  helper comparison functions
     ***************************************************************************/

    private boolean less(Key k1, Key k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean lte(Key k1, Key k2) {
        return k1.compareTo(k2) <= 0;
    }
}

final class StdRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    private StdRandom() {
    }

    /**
     * Sets the seed of the pseudorandom number generator.
     * This method enables you to produce the same sequence of "random"
     * number for each execution of the program.
     * Ordinarily, you should call this method at most once per program.
     *
     * @param s the seed
     */
    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    /**
     * Returns the seed of the pseudorandom number generator.
     *
     * @return the seed
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     *
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive");
        return random.nextInt(n);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     * @deprecated Replaced by {@link #uniform()}.
     */
    @Deprecated
    public static double random() {
        return uniform();
    }

    /**
     * Returns a random integer uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if {@code b <= a}
     * @throws IllegalArgumentException if {@code b - a >= Integer.MAX_VALUE}
     */
    public static int uniform(int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + "]");
        }
        return a + uniform(b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless {@code a < b}
     */
    public static double uniform(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + "]");
        }
        return a + uniform() * (b - a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>.
     *
     * @param p the probability of returning {@code true}
     * @return {@code true} with probability {@code p} and
     * {@code false} with probability {@code p}
     * @throws IllegalArgumentException unless {@code 0} &le; {@code p} &le; {@code 1.0}
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0");
        return uniform() < p;
    }

}

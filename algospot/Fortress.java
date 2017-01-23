import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 1. 23..
 */
public class Fortress {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {

            int N = Integer.parseInt(sc.next());
            ArrayList<Circle> circleList = new ArrayList<>();

            // input circles
            while (N-- > 0) {
                int x = Integer.parseInt(sc.next());
                int y = Integer.parseInt(sc.next());
                int r = Integer.parseInt(sc.next());

                circleList.add(new Circle(x, y, r));
            }

            // build tree
            circleList.sort((Circle c1, Circle c2) -> -c1.r + c2.r); // sort descending order (the biggest circle comes at first)

            for (int i = circleList.size() - 1; i > 0; i--) { // find the smallest circle's parent first
                Circle current = circleList.get(i);
                Circle parent = findParent(circleList, current, i); // find a parent circle having this circle
                parent.addChild(current); // the biggest circle has no parent
            }

            // get solution how many go thru the circles
            // get tree's diameter (tree's longest path)
            longest = 0;
            int height = height(circleList.get(0));
            int result = Math.max(height, longest);

            System.out.println(result);
        }
        sc.close();
    }

    static int longest;

    static int height(Circle root) {
        if (root == null) return 0;

        // store children's heights into hlist,
        // get longest path size among children's subtree
        ArrayList<Integer> hlist = new ArrayList<>();
        for (Circle c : root.children) {
            hlist.add(height(c));
        }
        if (hlist.isEmpty()) return 0; // ***** if there's no children, it means leaf node, just return 0

        hlist.sort((a, b) -> -a + b); // 10, 9, 8 order

        if (hlist.size() >= 2)
            longest = Math.max(longest, hlist.get(0) + hlist.get(1) + 2);

        return hlist.get(0) + 1; // return the biggest height among children's subtree
    }

    static Circle findParent(ArrayList<Circle> circles, Circle current, int curIndex) { // curIndex is the index of the current circle on ArrayList
        // circles[] is sorted as 10,9,8 order, so check smaller one first , biggest one later
        for (int i = curIndex - 1; i >= 0; i--) {
            Circle c = circles.get(i);
            if (c != current && isContained(c, current)) // true
                return c;
        }

        return null; // the root(the biggest circle) otherwise error
    }

    // true if c1 is bigger than c2, and c1 has c2.
    static boolean isContained(Circle c1, Circle c2) {
        // c1.r > c2.r + d ==> c1.r - c2.r > d : means c1 includes c2
        int d = getSqrDistance(c1, c2);
        int dr = c1.r - c2.r;
        if ((c1.r > c2.r) && (dr * dr) > d) return true;
        return false;
    }

    static int getSqrDistance(Circle c1, Circle c2) {
        int a = (c1.x - c2.x);
        int b = (c1.y - c2.y);
        return a * a + b * b; // distance should be square root, but it might slower then a*a
    }

    static class Circle {
        int x, y, r;
        ArrayList<Circle> children = new ArrayList<Circle>();

        Circle(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        void addChild(Circle child) {
            children.add(child);
        }

        public String toString() {
            return x + " " + y + " " + r;
        }
    }
}

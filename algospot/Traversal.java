import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by cutececil on 2017. 1. 21..
 */
public class Traversal {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int tc = Integer.parseInt(sc.next());
        while (tc-- > 0) {
            int cnt = Integer.parseInt(sc.next());

            ArrayList<Integer> preorder = new ArrayList(cnt);
            ArrayList<Integer> inorder = new ArrayList(cnt);
            for (int i = 0; i < cnt; i++) {
                preorder.add(Integer.parseInt(sc.next()));
            }

            for (int i = 0; i < cnt; i++) {
                inorder.add(Integer.parseInt(sc.next()));
            }

            traversal(preorder, inorder);
            System.out.println();
        }
        sc.close();
    }

    public static void traversal(List<Integer> preorder, List<Integer> inorder) {
        if (preorder.size() == 0) return;
        int root = preorder.get(0);
        int lsize = 0; // left child node cnt
        for (int i = 0; i < inorder.size(); i++) {
            if (root == inorder.get(i)) {
                lsize = i;
                break;
            }
        }

        traversal(preorder.subList(1, lsize + 1), inorder.subList(0, lsize));
        traversal(preorder.subList(lsize + 1, preorder.size()), inorder.subList(lsize + 1, inorder.size()));
        System.out.print(root + " ");
    }
}

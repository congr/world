import java.util.Scanner;

/**
 * Created by cutececil on 2017. 2. 26..
 */

public class Contacts {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        TrieNode root = new TrieNode(' ');
        for (int a0 = 0; a0 < n; a0++) {
            String op = in.next();
            String contact = in.next();
            if (op.equals("add")) {
                root.insert(contact.toCharArray(), 0);
            } else { // find
                TrieNode node = root.search(contact.toCharArray(), 0);
                System.out.println(node == null ? "0" : node.prefixCnt);
            }
        }
    }

    static class TrieNode {
        char ch;
        TrieNode[] children = new TrieNode[26];             // alphabet small case
        int prefixCnt;

        TrieNode(char ch) {
            this.ch = ch;
        }

        void insert(char[] chArr, int pos) {
            if (pos == chArr.length) return;

            int index = chArr[pos] - 'a';
            if (children[index] == null)
                children[index] = new TrieNode(chArr[pos]); // children['a'] is not made yet, then new it

            ++children[index].prefixCnt;                    // cnt to pass through this node. (same prefix)
            children[index].insert(chArr, ++pos);           // insert next positioned character by recursive function
        }

        TrieNode search(char[] chArr, int pos) {            // chArr to find string
            if (pos == chArr.length) return this;           // found

            int index = chArr[pos] - 'a';                   // 'a' index is 0, 'b' index is 1..
            if (children[index] != null) return children[index].search(chArr, ++pos);
            return null;                                    // not found
        }
    }
}
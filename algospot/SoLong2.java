import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 1. 26..
 */
public class SoLong2 {

    static class Dictionary {
        int frequency;
        String letter;

        Dictionary(String l, int f) {
            frequency = f;
            letter = l;
        }

        public String toString() {
            return letter + "(" + frequency + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] NM = br.readLine().split(" ");
            int N = Integer.parseInt(NM[0]);
            int M = Integer.parseInt(NM[1]);

            ArrayList<Dictionary> dictList = new ArrayList<>(N);

            for (int i = 0; i < N; i++) {
                String[] dic = br.readLine().split(" ");
                dictList.add(new Dictionary(dic[0], Integer.parseInt(dic[1])));
            }

            dictList.sort((Dictionary a, Dictionary b) -> {
                if (a.frequency == b.frequency) {
                    return a.letter.compareTo(b.letter);
                }
                return b.frequency - a.frequency;
            });
            System.out.println(dictList.toString());

            int index = 0;
            Trie trie = new Trie();
            for (Dictionary w : dictList)
                trie.insert(w.letter, index++);

            String[] input = br.readLine().split(" ");
            int typings = 0;
            for (int i = 0; i < M; i++) {
                System.out.println("input " + input[i]);

                int cnt = count(input[i], trie);
                typings += cnt;
                System.out.println("input " + cnt);
            }
            System.out.println(typings + M - 1);
        }

        br.close();
        out.close();
    }

    static int count(String input, Trie trie) {
        TrieNode node = trie.searchNode(input);
        if (node == null || node.isLeaf == false)
            return input.length();

        int type = trie.type(input, node.id);
        return type;
    }

    static class TrieNode {
        char c;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isLeaf;
        int id = -1;
        boolean isFirst;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
        }

    }

    static public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public int type(String word, int index) {
            HashMap<Character, TrieNode> children = root.children;
            int cnt = 0;

            for (int i = 0; i < word.length(); i++) {
                cnt++;
                char c = word.charAt(i);

                TrieNode t;
                if (children.containsKey(c)) {
                    t = children.get(c);
                    if (i == word.length() - 1) // 마지막 글자라면 탭 불필요
                        return cnt;
                    if (t.isFirst && t.id == index)
                        return 1 + cnt;// tab
                } else {
                    return cnt;
                }
                children = t.children;
            }

            return cnt;
        }

        // Inserts a word into the trie.
        public void insert(String word, int index) {
            HashMap<Character, TrieNode> children = root.children;

            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                TrieNode t;
                if (children.containsKey(c)) {
                    t = children.get(c);
                } else {
                    t = new TrieNode(c);
                    children.put(c, t);
                    t.isFirst = true;
                    t.id = index;
                }

                children = t.children;

                //setWithoutDup leaf node
                if (i == word.length() - 1) {
                    t.isLeaf = true;
                    t.id = index;
                }
            }
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            TrieNode t = searchNode(word);

            if (t != null && t.isLeaf)
                return true;
            else
                return false;
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            if (searchNode(prefix) == null)
                return false;
            else
                return true;
        }

        public TrieNode searchNode(String str) {
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (children.containsKey(c)) {
                    t = children.get(c);
                    children = t.children;
                } else {
                    return null;
                }
            }

            return t;
        }
    }

}

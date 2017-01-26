import com.sun.xml.internal.xsom.impl.scd.Iterators;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 1. 26..
 */
public class SoLong {

    static class Dictionary {
        int frequency;
        String letter;

        Dictionary(String l, int f) {
            frequency = f;
            letter = l;
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

            Trie trie = new Trie();
            for (Dictionary w : dictList)
                trie.insert(w.letter);

            String[] input = br.readLine().split(" ");
            int typings = 0;
            for (int i = 0; i < M; i++) {
                String word = input[i];
                System.out.println("word " + word);

                int cnt = 0;
                while (word.length() > cnt) {
                    typings++;
                    String test = word.substring(0, ++cnt);
                    System.out.println("test " + test + " " + typings);
                    TrieNode foundNode = trie.searchNode(test);
                   // boolean found = trie.startsWith(test);

                    if (test.equals(word)==false && foundNode!=null && foundNode.children.size() == 1/* && word.equals(foundNode.c)*/) {
                        typings++; // tab key
                        System.out.println("tab " + test + " " + typings);
                        break;
                    }
                }
            }
            System.out.println(typings + M - 1);
        }

        br.close();
        out.close();
    }

    static class TrieNode {
        char c;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isLeaf;

        public TrieNode() {}

        public TrieNode(char c){
            this.c = c;
        }
    }

    static public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
            HashMap<Character, TrieNode> children = root.children;

            for(int i=0; i<word.length(); i++){
                char c = word.charAt(i);

                TrieNode t;
                if(children.containsKey(c)){
                    t = children.get(c);
                }else{
                    t = new TrieNode(c);
                    children.put(c, t);
                }

                children = t.children;

                //set leaf node
                if(i==word.length()-1)
                    t.isLeaf = true;
            }
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
            TrieNode t = searchNode(word);

            if(t != null && t.isLeaf)
                return true;
            else
                return false;
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            if(searchNode(prefix) == null)
                return false;
            else
                return true;
        }

        public TrieNode searchNode(String str){
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;
            for(int i=0; i<str.length(); i++){
                char c = str.charAt(i);
                if(children.containsKey(c)){
                    t = children.get(c);
                    children = t.children;
                }else{
                    return null;
                }
            }

            return t;
        }
    }

}

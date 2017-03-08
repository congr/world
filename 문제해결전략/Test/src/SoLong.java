import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by cutececil on 2017. 1. 26..
 */
public class SoLong {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            String[] NM = br.readLine().split(" ");
            int N = Integer.parseInt(NM[0]);
            int M = Integer.parseInt(NM[1]);

            ArrayList<Word> wordList = new ArrayList<>(N);

            for (int i = 0; i < N; i++) {
                String[] dic = br.readLine().split(" ");
                wordList.add(new Word(dic[0], Integer.parseInt(dic[1])));
            }

            // sort by count(frequency), and by word
            wordList.sort((Word a, Word b) -> {
                if (a.count == b.count) {
                    return a.word.compareTo(b.word);
                }
                return b.count - a.count;
            });
            //System.out.println(wordList.toString());

            int index = 0;
            Trie trie = new Trie();
            for (Word w : wordList) {
                trie.insert(w.word, index++);
            }

            String[] input = br.readLine().split(" ");
            int typings = 0;
            for (int i = 0; i < M; i++) {
                //System.out.println("input " + input[i]);

                int cnt = countTypings(input[i], trie, wordList);
                typings += cnt;
                //System.out.println("input " + cnt);
            }
            System.out.println(typings + M - 1); // space count: M - 1
        }

        br.close();
       //out.close();
    }

    static int countTypings(String input, Trie trie, ArrayList<Word> wordList) {
        TrieNode node = trie.search(input); // input 단어를 trie에서 가지고 있나

        if (node == null || node.isLeaf == false) // not found
            return input.length();

        String word = wordList.get(node.leafId).word;
        int wordLen = word.length();
        int inputLen = input.length();
        if (inputLen != wordLen)
            return input.length();

        int typings = trie.type(input, node.leafId);

        return typings;
    }

    static class Word {
        int count;
        String word;

        Word(String word, int count) {
            this.count = count;
            this.word = word;
        }
        public String toString() {
            return word + "(" + count + ")";
        }
    }

    static class TrieNode {
        char c;
        TrieNode[] children;
        boolean isLeaf;
        boolean isFirst;
        int leafId;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
        }

        public String toString() {
            return this.c + "(" + leafId + ")";
        }

        void insert(char[] ch, int pos, int wordIndex) {
            if (ch.length == pos) return;

            int index = ch[pos] - 'A'; // toNumber
            if (children == null) {
                children = new TrieNode[26];
                //children[index] = new TrieNode(ch[pos]);
                //children[index].isFirst = true;
                //children[index].leafId = wordIndex;
            }

            if (children[index] == null) {
                children[index] = new TrieNode(ch[pos]);
                children[index].isFirst = true;
                children[index].leafId = wordIndex;
            }

            if (ch.length - 1 == pos) {
                children[index].isLeaf = true;
                children[index].leafId = wordIndex;
            } else {
                children[index].insert(ch, pos + 1, wordIndex);
            }
        }

        TrieNode search(char[] ch, int pos) {
            // ???
            if (isLeaf == false && ch.length <= pos) return null;

            //if (isLeaf) {
            if (isLeaf || ch.length == pos) {
                return this;
            }

            int index = ch[pos] - 'A';
            if (children[index] == null) // no more children
                return null;

            return children[index].search(ch, pos + 1);
        }

        int type(char[] ch, int pos, int wordIndex) {
            if (ch.length == pos)
                return 0; // last character

            if (isFirst && leafId == wordIndex)
                return 1;// tab key

            int index = ch[pos] - 'A';
            if (children[index] == null) return 0;
            return 1 + children[index].type(ch, pos + 1, wordIndex);
        }
    }

    static public class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word, int wordListIndex) {
            root.insert(word.toCharArray(), 0, wordListIndex);
        }

        public TrieNode search(String word) {
            return root.search(word.toCharArray(), 0);
        }

        public int type (String word, int wordListIndex) {
            return root.type(word.toCharArray(),0,wordListIndex);
        }
    }
}

/**
 * Created by cutececil on 2017. 9. 15..
 */

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class P1_Main {
    public static void main(String[] args) throws Exception {
        String inFilename = (args != null && args.length > 0) ? args[0] : "201606/P1/sample.in"; // path from root
        File inFile = new File(inFilename);
        File outFile = new File(inFilename.replace("in", "out"));
        FileWriter wr = new FileWriter(outFile);
        Scanner sc = new Scanner(System.in);
        if (inFile.exists()) sc = new Scanner(inFile);
        
        int tc = sc.nextInt();
        while (tc-- > 0) {
            // input tc #
            int N = sc.nextInt();
            
            Tree tree = new Tree(N + 1); // 노드 개수
            
            for (int i = 0; i < N - 1; i++) { // edge
                int p = sc.nextInt();
                int c = sc.nextInt();
                tree.addChild(p, c);
            }
            
            int root = tree.getRoot();
            int ret = tree.height(root); // root
            int diameter = tree.getDiameter(root);
            System.out.println("diameter " + diameter);
            wr.write(ret + "\n");
            System.out.println(ret);
        }
        
        sc.close();
        wr.close();
    }
    
    static class Tree {
        Node[] tree;
        int N;
        int root;
        
        Tree(int N) {
            this.N = N;
            tree = new Node[N];
            for (int i = 1; i < N; i++) {
                tree[i] = new Node(i);
            }
        }
        
        void addChild(int p, int c) {
            tree[p].addChild(c);
            tree[c].setParent(p);
        }
        
        int getRoot() {
            for (int i = 1; i < N; i++) {
                if (tree[i].parent == 0) {
                    root = i;
                    break;
                }
            }
            return root;
        }
        
        int height(int node) {
            int h = 1; // root 를 1 height로 치면 1로 초기화
            
            // 자식이 없다면 h =0 return; - base case
            for (int child : tree[node].children) {
                h = Math.max(h, height(child) + 1);
            }
            
            return h;
        }
        
        // 트리의 최장 경로
        // 트리의 높이 혹은 leaf노드 가장 긴 두개 +1
        int longest;
        
        // return tree height, not diameter
        private int heightForDiameter(int node) {
            if (tree[node].children.isEmpty()) return 1;
            
            // store children's heights into hlist,
            // get longest path size among children's subtree
            ArrayList<Integer> hlist = new ArrayList<>();
            for (int child : tree[node].children) {
                hlist.add(heightForDiameter(child));
            }
            if (hlist.isEmpty()) return 0; // ***** if there's no children, it means leaf node, just return 0
            
            hlist.sort((a, b) -> -a + b); // 10, 9, 8 order
            
            if (hlist.size() >= 2)
                longest = Math.max(longest, hlist.get(0) + hlist.get(1) + 1); // 가장 긴 잎 두개 + 1
            
            return hlist.get(0) + 1; // return the biggest height among children's subtree
        }
        
        // tree diameter 최장 경로
        int getDiameter(int root) {
            int height = heightForDiameter(root);
            return Math.max(longest, height); // 잎에서 다른 잎길이와 트리 높이 중 큰 것이 diameter가 된다
        }
    }
    
    static class Node {
        ArrayList<Integer> children = new ArrayList<Integer>();
        int parent;
        int self;
        
        Node(int self) {
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

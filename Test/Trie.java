class Trie {
    Trie[] nodes;
    int hit;

    /** Initialize your data structure here. */
    public Trie() {
        nodes = new Trie[26];
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        insert(word, 0);
    }

    private void insert(String word, int pos) {
        if (pos == word.length()) return;

        char ch = word.charAt(pos);
        int ind = ch - 'a';
        if (nodes[ind] == null)
            nodes[ind] = new Trie();

        nodes[ind].hit++;
        nodes[ind].insert(word, pos+1);
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return search(word, 0);
    }

    private boolean search(String word, int pos) {
        if (pos == word.length()) return true;

        char ch = word.charAt(pos);
        int ind = ch - 'a';
        if (nodes[ind] != null && nodes[ind].hit > 0)
            return nodes[ind].search(word, pos+1);
        return false;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return startsWith(prefix, 0);
    }

    private boolean startsWith(String prefix, int pos) {
        if (pos == prefix.length()) return true;

        char ch = prefix.charAt(pos);
        int ind = ch - 'a';
        if (nodes[ind] != null && nodes[ind].hit > 0)
            return nodes[ind].startsWith(prefix, pos+1);
        return false;
    }

    public static void main(String[] args) {
        Trie obj = new Trie();
        obj.insert("apple");
        boolean found =obj.search("app");
        System.out.println(found);
    }

}



/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
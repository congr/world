/**
 * Definition for binary tree
 * class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) {
 *      val = x;
 *      left=null;
 *      right=null;
 *     }
 * }
 */
public class Solution {
    Stack<TreeNode> st = new Stack<>();
    public ArrayList<Integer> postorderTraversal(TreeNode A) {
        ArrayList<Integer> al = new ArrayList<>();

        // Add left children including root itself
        addLeft(A);
        TreeNode lastNode = null;
        
        while(!st.isEmpty()) {
            TreeNode node = st.peek();
            
            if(node.right != null && lastNode != node.right) { 
                addLeft(node.right);
            } else {
                al.add(node.val);
                lastNode = st.pop();
            }
        }
        
        return al;        
    }
    
    void addLeft(TreeNode root) {
        TreeNode node = root;
        
        while(node != null) {
            st.push(node);
            node = node.left;
        }
    }
}

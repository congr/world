class Solution {
    Stack eleSt = new Stack(), minSt = new Stack();
    
    public void push(int x) {
        int min = Math.min(x, getMin());
        if (min == -1) min = x;
        eleSt.push(x);
        minSt.push(min);
    }

    public void pop() {
        if (eleSt.isEmpty()) return;
        eleSt.pop(); 
        minSt.pop();
    }

    public int top() {
        if (eleSt.isEmpty()) return -1;
        return (int)eleSt.peek();
    }

    public int getMin() {
        if (minSt.isEmpty()) return -1;
        return (int)minSt.peek();
    }
}

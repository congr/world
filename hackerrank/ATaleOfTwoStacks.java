/**
 * Created by cutececil on 2017. 2. 15..
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class ATaleOfTwoStacks {
    public static void main(String[] args) {
        MyQueue2<Integer> queue = new MyQueue2<Integer>();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
                queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
                queue.dequeue();
            } else if (operation == 3) { // print/peek
                System.out.println(queue.peek());
            }
        }
        scan.close();
    }

    // If there's no need to need 2 stacks, one stack can simulate queue
    static class MyQueue<Integer> {
        Integer head, tail;
        Stack<Integer> pushStack = new Stack<>();

        public void enqueue(Integer i) {
            if (pushStack.isEmpty()) head = i; // first
            pushStack.add(i);
        }

        public void dequeue() {
            if (!pushStack.isEmpty())
                pushStack.remove(0);
        }

        public String peek() {
            if (!pushStack.isEmpty()) {
                Integer a = pushStack.elementAt(0);
                return a.toString();
            }
            return null;
        }
    }

    // Original question is supposed to use 2 stacks
    static class MyQueue2<Integer> {
        Stack<Integer> pushStack = new Stack<>();
        Stack<Integer> popStack = new Stack<>();

        public void enqueue(Integer i) {
            pushStack.add(i);
        }

        public void dequeue() {
            if (popStack.isEmpty()) { // ** only if popStack is empty, move it into pushStatck
                while (!pushStack.isEmpty()) popStack.add(pushStack.pop());
            }
            popStack.pop(); // now pushStack is empty
        }

        public String peek() {
            if (popStack.isEmpty()) { // ** only if popStack is empty, move it into pushStatck
                while (!pushStack.isEmpty()) popStack.add(pushStack.pop());
            }
            return popStack.peek().toString();
        }
    }
}

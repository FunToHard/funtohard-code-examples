import java.util.*;

/**
 * Stacks and Queues in Java
 * 
 * This class demonstrates stack and queue data structures, their implementations,
 * and common algorithms and applications.
 */
public class StacksAndQueues {
    
    // Custom Stack implementation using array
    static class ArrayStack<T> {
        private Object[] array;
        private int top;
        private int capacity;
        
        @SuppressWarnings("unchecked")
        public ArrayStack(int capacity) {
            this.capacity = capacity;
            this.array = new Object[capacity];
            this.top = -1;
        }
        
        public void push(T item) {
            if (isFull()) {
                throw new RuntimeException("Stack overflow");
            }
            array[++top] = item;
        }
        
        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Stack underflow");
            }
            T item = (T) array[top];
            array[top--] = null; // Help GC
            return item;
        }
        
        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Stack is empty");
            }
            return (T) array[top];
        }
        
        public boolean isEmpty() {
            return top == -1;
        }
        
        public boolean isFull() {
            return top == capacity - 1;
        }
        
        public int size() {
            return top + 1;
        }
        
        @Override
        public String toString() {
            if (isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i <= top; i++) {
                sb.append(array[i]);
                if (i < top) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    // Custom Queue implementation using array (circular)
    static class ArrayQueue<T> {
        private Object[] array;
        private int front;
        private int rear;
        private int size;
        private int capacity;
        
        public ArrayQueue(int capacity) {
            this.capacity = capacity;
            this.array = new Object[capacity];
            this.front = 0;
            this.rear = -1;
            this.size = 0;
        }
        
        public void enqueue(T item) {
            if (isFull()) {
                throw new RuntimeException("Queue overflow");
            }
            rear = (rear + 1) % capacity;
            array[rear] = item;
            size++;
        }
        
        @SuppressWarnings("unchecked")
        public T dequeue() {
            if (isEmpty()) {
                throw new RuntimeException("Queue underflow");
            }
            T item = (T) array[front];
            array[front] = null; // Help GC
            front = (front + 1) % capacity;
            size--;
            return item;
        }
        
        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            return (T) array[front];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public boolean isFull() {
            return size == capacity;
        }
        
        public int size() {
            return size;
        }
        
        @Override
        public String toString() {
            if (isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < size; i++) {
                int index = (front + i) % capacity;
                sb.append(array[index]);
                if (i < size - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    // Stack using two queues
    static class StackUsingQueues<T> {
        private Queue<T> q1;
        private Queue<T> q2;
        
        public StackUsingQueues() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }
        
        public void push(T item) {
            q2.offer(item);
            while (!q1.isEmpty()) {
                q2.offer(q1.poll());
            }
            Queue<T> temp = q1;
            q1 = q2;
            q2 = temp;
        }
        
        public T pop() {
            if (q1.isEmpty()) {
                throw new RuntimeException("Stack is empty");
            }
            return q1.poll();
        }
        
        public T peek() {
            if (q1.isEmpty()) {
                throw new RuntimeException("Stack is empty");
            }
            return q1.peek();
        }
        
        public boolean isEmpty() {
            return q1.isEmpty();
        }
        
        public int size() {
            return q1.size();
        }
    }
    
    // Queue using two stacks
    static class QueueUsingStacks<T> {
        private Stack<T> stack1;
        private Stack<T> stack2;
        
        public QueueUsingStacks() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }
        
        public void enqueue(T item) {
            stack1.push(item);
        }
        
        public T dequeue() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            
            return stack2.pop();
        }
        
        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty");
            }
            
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            
            return stack2.peek();
        }
        
        public boolean isEmpty() {
            return stack1.isEmpty() && stack2.isEmpty();
        }
        
        public int size() {
            return stack1.size() + stack2.size();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Stacks and Queues Demo ===\n");
        
        // 1. Custom Stack Implementation
        customStackImplementation();
        
        // 2. Custom Queue Implementation
        customQueueImplementation();
        
        // 3. Java Built-in Stack and Queue
        javaBuiltInStackAndQueue();
        
        // 4. Stack Applications
        stackApplications();
        
        // 5. Queue Applications
        queueApplications();
        
        // 6. Advanced Stack and Queue Problems
        advancedStackQueueProblems();
        
        // 7. Stack and Queue Conversions
        stackQueueConversions();
    }
    
    // 1. Custom Stack Implementation
    static void customStackImplementation() {
        System.out.println("1. Custom Stack Implementation:");
        
        ArrayStack<Integer> stack = new ArrayStack<>(5);
        
        // Push elements
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack after pushes: " + stack);
        System.out.println("Size: " + stack.size());
        
        // Peek
        System.out.println("Peek: " + stack.peek());
        
        // Pop elements
        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());
        System.out.println("Stack after pops: " + stack);
        
        // Check if empty
        System.out.println("Is empty: " + stack.isEmpty());
        
        System.out.println();
    }
    
    // 2. Custom Queue Implementation
    static void customQueueImplementation() {
        System.out.println("2. Custom Queue Implementation:");
        
        ArrayQueue<String> queue = new ArrayQueue<>(5);
        
        // Enqueue elements
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        System.out.println("Queue after enqueues: " + queue);
        System.out.println("Size: " + queue.size());
        
        // Peek
        System.out.println("Peek: " + queue.peek());
        
        // Dequeue elements
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println("Queue after dequeues: " + queue);
        
        // Test circular nature
        queue.enqueue("Fourth");
        queue.enqueue("Fifth");
        System.out.println("Queue after more enqueues: " + queue);
        
        System.out.println();
    }
    
    // 3. Java Built-in Stack and Queue
    static void javaBuiltInStackAndQueue() {
        System.out.println("3. Java Built-in Stack and Queue:");
        
        // Stack
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);
        System.out.println("Pop: " + stack.pop());
        System.out.println("Peek: " + stack.peek());
        System.out.println("Search for 1: " + stack.search(1)); // 1-based position from top
        
        // Queue using LinkedList
        Queue<String> queue = new LinkedList<>();
        queue.offer("A");
        queue.offer("B");
        queue.offer("C");
        System.out.println("Queue: " + queue);
        System.out.println("Poll: " + queue.poll());
        System.out.println("Peek: " + queue.peek());
        
        // Priority Queue
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        System.out.println("Priority Queue: " + pq);
        System.out.println("Poll (min): " + pq.poll());
        System.out.println("Priority Queue after poll: " + pq);
        
        // Deque (Double-ended queue)
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);
        System.out.println("Deque: " + deque);
        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("Deque after removals: " + deque);
        
        System.out.println();
    }
    
    // 4. Stack Applications
    static void stackApplications() {
        System.out.println("4. Stack Applications:");
        
        // Balanced parentheses
        String[] expressions = {"()", "()[]{}", "([)]", "((", "))"};
        for (String expr : expressions) {
            System.out.println("'" + expr + "' is balanced: " + isBalanced(expr));
        }
        
        // Infix to postfix conversion
        String infix = "a+b*c-d";
        String postfix = infixToPostfix(infix);
        System.out.println("Infix: " + infix + " -> Postfix: " + postfix);
        
        // Evaluate postfix expression
        String postfixExpr = "23*54*+";
        int result = evaluatePostfix(postfixExpr);
        System.out.println("Postfix: " + postfixExpr + " = " + result);
        
        // Next greater element
        int[] arr = {4, 5, 2, 25, 7, 8};
        int[] nextGreater = nextGreaterElement(arr);
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Next greater: " + Arrays.toString(nextGreater));
        
        // Largest rectangle in histogram
        int[] heights = {2, 1, 5, 6, 2, 3};
        int maxArea = largestRectangleArea(heights);
        System.out.println("Heights: " + Arrays.toString(heights));
        System.out.println("Largest rectangle area: " + maxArea);
        
        System.out.println();
    }
    
    // 5. Queue Applications
    static void queueApplications() {
        System.out.println("5. Queue Applications:");
        
        // Level order traversal simulation
        System.out.println("Level order traversal simulation:");
        levelOrderTraversal();
        
        // Sliding window maximum
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] maxInWindows = slidingWindowMaximum(arr, k);
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Sliding window maximum (k=" + k + "): " + Arrays.toString(maxInWindows));
        
        // First negative in every window
        int[] arr2 = {12, -1, -7, 8, -15, 30, 16, 28};
        int[] firstNegative = firstNegativeInWindow(arr2, 3);
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("First negative in window: " + Arrays.toString(firstNegative));
        
        // Generate binary numbers
        String[] binaryNumbers = generateBinaryNumbers(8);
        System.out.println("Binary numbers 1 to 8: " + Arrays.toString(binaryNumbers));
        
        System.out.println();
    }
    
    // 6. Advanced Stack and Queue Problems
    static void advancedStackQueueProblems() {
        System.out.println("6. Advanced Stack and Queue Problems:");
        
        // Min stack
        MinStack minStack = new MinStack();
        minStack.push(3);
        minStack.push(5);
        System.out.println("Min: " + minStack.getMin()); // 3
        minStack.push(2);
        minStack.push(1);
        System.out.println("Min: " + minStack.getMin()); // 1
        minStack.pop();
        System.out.println("Min after pop: " + minStack.getMin()); // 2
        
        // Stock span problem
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] spans = stockSpan(prices);
        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("Stock spans: " + Arrays.toString(spans));
        
        // Trapping rain water
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int trappedWater = trap(heights);
        System.out.println("Heights: " + Arrays.toString(heights));
        System.out.println("Trapped water: " + trappedWater);
        
        // Circular queue
        CircularQueue cq = new CircularQueue(3);
        System.out.println("Enqueue 1: " + cq.enQueue(1));
        System.out.println("Enqueue 2: " + cq.enQueue(2));
        System.out.println("Enqueue 3: " + cq.enQueue(3));
        System.out.println("Enqueue 4: " + cq.enQueue(4)); // Should fail
        System.out.println("Rear: " + cq.Rear());
        System.out.println("Front: " + cq.Front());
        System.out.println("Dequeue: " + cq.deQueue());
        System.out.println("Enqueue 4: " + cq.enQueue(4)); // Should succeed now
        
        System.out.println();
    }
    
    // 7. Stack and Queue Conversions
    static void stackQueueConversions() {
        System.out.println("7. Stack and Queue Conversions:");
        
        // Stack using queues
        StackUsingQueues<Integer> stackFromQueues = new StackUsingQueues<>();
        stackFromQueues.push(1);
        stackFromQueues.push(2);
        stackFromQueues.push(3);
        System.out.println("Stack from queues - peek: " + stackFromQueues.peek());
        System.out.println("Stack from queues - pop: " + stackFromQueues.pop());
        System.out.println("Stack from queues - pop: " + stackFromQueues.pop());
        
        // Queue using stacks
        QueueUsingStacks<String> queueFromStacks = new QueueUsingStacks<>();
        queueFromStacks.enqueue("A");
        queueFromStacks.enqueue("B");
        queueFromStacks.enqueue("C");
        System.out.println("Queue from stacks - peek: " + queueFromStacks.peek());
        System.out.println("Queue from stacks - dequeue: " + queueFromStacks.dequeue());
        System.out.println("Queue from stacks - dequeue: " + queueFromStacks.dequeue());
        
        System.out.println();
    }
    
    // Helper methods for stack applications
    
    // Check balanced parentheses
    static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> pairs = Map.of(')', '(', '}', '{', ']', '[');
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    // Convert infix to postfix
    static String infixToPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        Map<Character, Integer> precedence = Map.of('+', 1, '-', 1, '*', 2, '/', 2);
        
        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Remove '('
            } else {
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       precedence.getOrDefault(stack.peek(), 0) >= precedence.getOrDefault(c, 0)) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.toString();
    }
    
    // Evaluate postfix expression
    static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                int b = stack.pop();
                int a = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        
        return stack.pop();
    }
    
    // Next greater element
    static int[] nextGreaterElement(int[] arr) {
        int[] result = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(arr[i]);
        }
        
        return result;
    }
    
    // Largest rectangle in histogram
    static int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];
            
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            stack.push(i);
        }
        
        return maxArea;
    }
    
    // Helper methods for queue applications
    
    // Level order traversal simulation
    static void levelOrderTraversal() {
        // Simulate a binary tree level order traversal
        Queue<String> queue = new LinkedList<>();
        queue.offer("Root");
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level: ");
            
            for (int i = 0; i < levelSize; i++) {
                String node = queue.poll();
                System.out.print(node + " ");
                
                // Add children (simulation)
                if (node.equals("Root")) {
                    queue.offer("Left");
                    queue.offer("Right");
                } else if (node.equals("Left")) {
                    queue.offer("LL");
                    queue.offer("LR");
                } else if (node.equals("Right")) {
                    queue.offer("RL");
                    queue.offer("RR");
                }
            }
            System.out.println();
            
            // Stop after 3 levels for demo
            if (queue.peek() != null && queue.peek().length() > 2) break;
        }
    }
    
    // Sliding window maximum
    static int[] slidingWindowMaximum(int[] arr, int k) {
        int[] result = new int[arr.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < arr.length; i++) {
            // Remove elements outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove smaller elements from rear
            while (!deque.isEmpty() && arr[deque.peekLast()] <= arr[i]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            // Add to result if window is complete
            if (i >= k - 1) {
                result[i - k + 1] = arr[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    // First negative in every window
    static int[] firstNegativeInWindow(int[] arr, int k) {
        int[] result = new int[arr.length - k + 1];
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < arr.length; i++) {
            // Add negative numbers to queue
            if (arr[i] < 0) {
                queue.offer(i);
            }
            
            // Remove elements outside window
            while (!queue.isEmpty() && queue.peek() < i - k + 1) {
                queue.poll();
            }
            
            // Add to result if window is complete
            if (i >= k - 1) {
                result[i - k + 1] = queue.isEmpty() ? 0 : arr[queue.peek()];
            }
        }
        
        return result;
    }
    
    // Generate binary numbers from 1 to n
    static String[] generateBinaryNumbers(int n) {
        String[] result = new String[n];
        Queue<String> queue = new LinkedList<>();
        queue.offer("1");
        
        for (int i = 0; i < n; i++) {
            String binary = queue.poll();
            result[i] = binary;
            
            queue.offer(binary + "0");
            queue.offer(binary + "1");
        }
        
        return result;
    }
    
    // Advanced data structures
    
    // Min Stack
    static class MinStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;
        
        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }
        
        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }
        
        public void pop() {
            if (stack.pop().equals(minStack.peek())) {
                minStack.pop();
            }
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return minStack.peek();
        }
    }
    
    // Stock span problem
    static int[] stockSpan(int[] prices) {
        int[] spans = new int[prices.length];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            spans[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        
        return spans;
    }
    
    // Trapping rain water
    static int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int water = 0;
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) break;
                
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                water += distance * boundedHeight;
            }
            stack.push(i);
        }
        
        return water;
    }
    
    // Circular Queue
    static class CircularQueue {
        private int[] queue;
        private int front;
        private int rear;
        private int size;
        private int capacity;
        
        public CircularQueue(int k) {
            this.capacity = k;
            this.queue = new int[k];
            this.front = 0;
            this.rear = -1;
            this.size = 0;
        }
        
        public boolean enQueue(int value) {
            if (isFull()) return false;
            rear = (rear + 1) % capacity;
            queue[rear] = value;
            size++;
            return true;
        }
        
        public boolean deQueue() {
            if (isEmpty()) return false;
            front = (front + 1) % capacity;
            size--;
            return true;
        }
        
        public int Front() {
            return isEmpty() ? -1 : queue[front];
        }
        
        public int Rear() {
            return isEmpty() ? -1 : queue[rear];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public boolean isFull() {
            return size == capacity;
        }
    }
}

import java.util.*;

/**
 * Linked Lists in Java
 * 
 * This class demonstrates various linked list implementations and operations,
 * including singly linked lists, doubly linked lists, and common algorithms.
 */
public class LinkedLists {
    
    // Node class for singly linked list
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
    
    // Node class for doubly linked list
    static class DoublyListNode {
        int val;
        DoublyListNode prev;
        DoublyListNode next;
        
        DoublyListNode() {}
        DoublyListNode(int val) { this.val = val; }
        DoublyListNode(int val, DoublyListNode prev, DoublyListNode next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
    
    // Simple Singly Linked List implementation
    static class SinglyLinkedList {
        private ListNode head;
        private int size;
        
        public SinglyLinkedList() {
            this.head = null;
            this.size = 0;
        }
        
        // Add element at the beginning
        public void addFirst(int val) {
            ListNode newNode = new ListNode(val);
            newNode.next = head;
            head = newNode;
            size++;
        }
        
        // Add element at the end
        public void addLast(int val) {
            ListNode newNode = new ListNode(val);
            if (head == null) {
                head = newNode;
            } else {
                ListNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }
        
        // Add element at specific index
        public void add(int index, int val) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            
            if (index == 0) {
                addFirst(val);
                return;
            }
            
            ListNode newNode = new ListNode(val);
            ListNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
        
        // Remove first element
        public int removeFirst() {
            if (head == null) {
                throw new NoSuchElementException("List is empty");
            }
            int val = head.val;
            head = head.next;
            size--;
            return val;
        }
        
        // Remove last element
        public int removeLast() {
            if (head == null) {
                throw new NoSuchElementException("List is empty");
            }
            
            if (head.next == null) {
                int val = head.val;
                head = null;
                size--;
                return val;
            }
            
            ListNode current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            int val = current.next.val;
            current.next = null;
            size--;
            return val;
        }
        
        // Remove element at specific index
        public int remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            
            if (index == 0) {
                return removeFirst();
            }
            
            ListNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            int val = current.next.val;
            current.next = current.next.next;
            size--;
            return val;
        }
        
        // Get element at specific index
        public int get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            
            ListNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.val;
        }
        
        // Check if list contains element
        public boolean contains(int val) {
            ListNode current = head;
            while (current != null) {
                if (current.val == val) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        // Get size of list
        public int size() {
            return size;
        }
        
        // Check if list is empty
        public boolean isEmpty() {
            return size == 0;
        }
        
        // Convert to string
        @Override
        public String toString() {
            if (head == null) {
                return "[]";
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            ListNode current = head;
            while (current != null) {
                sb.append(current.val);
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            sb.append("]");
            return sb.toString();
        }
        
        // Get head node (for algorithm demonstrations)
        public ListNode getHead() {
            return head;
        }
        
        // Set head node (for algorithm demonstrations)
        public void setHead(ListNode head) {
            this.head = head;
            // Recalculate size
            this.size = 0;
            ListNode current = head;
            while (current != null) {
                this.size++;
                current = current.next;
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Linked Lists Demo ===\n");
        
        // 1. Basic Linked List Operations
        basicLinkedListOperations();
        
        // 2. Linked List Algorithms
        linkedListAlgorithms();
        
        // 3. Two Pointer Techniques
        twoPointerTechniques();
        
        // 4. Linked List Manipulation
        linkedListManipulation();
        
        // 5. Advanced Linked List Problems
        advancedLinkedListProblems();
        
        // 6. Java Built-in LinkedList
        javaBuiltInLinkedList();
    }
    
    // 1. Basic Linked List Operations
    static void basicLinkedListOperations() {
        System.out.println("1. Basic Linked List Operations:");
        
        SinglyLinkedList list = new SinglyLinkedList();
        
        // Adding elements
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        System.out.println("After adding 1, 2, 3: " + list);
        
        list.addFirst(0);
        System.out.println("After adding 0 at beginning: " + list);
        
        list.add(2, 10);
        System.out.println("After adding 10 at index 2: " + list);
        
        // Accessing elements
        System.out.println("Element at index 2: " + list.get(2));
        System.out.println("List contains 10: " + list.contains(10));
        System.out.println("List size: " + list.size());
        
        // Removing elements
        int removed = list.removeFirst();
        System.out.println("Removed first element: " + removed + ", List: " + list);
        
        removed = list.removeLast();
        System.out.println("Removed last element: " + removed + ", List: " + list);
        
        removed = list.remove(1);
        System.out.println("Removed element at index 1: " + removed + ", List: " + list);
        
        System.out.println();
    }
    
    // 2. Linked List Algorithms
    static void linkedListAlgorithms() {
        System.out.println("2. Linked List Algorithms:");
        
        // Create test list: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Original list: " + printList(head));
        
        // Reverse linked list
        ListNode reversed = reverseList(head);
        System.out.println("Reversed list: " + printList(reversed));
        
        // Restore original list
        head = createLinkedList(new int[]{1, 2, 3, 4, 5});
        
        // Find middle of linked list
        ListNode middle = findMiddle(head);
        System.out.println("Middle element: " + middle.val);
        
        // Detect cycle (create a cycle first)
        ListNode cycleHead = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createCycle(cycleHead, 2); // Create cycle at index 2
        boolean hasCycle = hasCycle(cycleHead);
        System.out.println("Has cycle: " + hasCycle);
        
        // Remove cycle and test again
        cycleHead = createLinkedList(new int[]{1, 2, 3, 4, 5});
        hasCycle = hasCycle(cycleHead);
        System.out.println("Has cycle (after removal): " + hasCycle);
        
        // Find nth node from end
        int nthFromEnd = findNthFromEnd(head, 2);
        System.out.println("2nd node from end: " + nthFromEnd);
        
        // Check if palindrome
        ListNode palindromeList = createLinkedList(new int[]{1, 2, 3, 2, 1});
        boolean isPalindrome = isPalindrome(palindromeList);
        System.out.println("Is palindrome: " + isPalindrome);
        
        System.out.println();
    }
    
    // 3. Two Pointer Techniques
    static void twoPointerTechniques() {
        System.out.println("3. Two Pointer Techniques:");
        
        // Remove nth node from end
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Original: " + printList(head));
        head = removeNthFromEnd(head, 2);
        System.out.println("After removing 2nd from end: " + printList(head));
        
        // Find intersection of two linked lists
        ListNode list1 = createLinkedList(new int[]{4, 1, 8, 4, 5});
        ListNode list2 = createLinkedList(new int[]{5, 6, 1});
        
        // Create intersection at node with value 8
        ListNode intersection = list1.next.next; // Node with value 8
        ListNode current = list2;
        while (current.next != null) {
            current = current.next;
        }
        current.next = intersection;
        
        ListNode intersectionNode = getIntersectionNode(list1, list2);
        System.out.println("Intersection node value: " + (intersectionNode != null ? intersectionNode.val : "null"));
        
        System.out.println();
    }
    
    // 4. Linked List Manipulation
    static void linkedListManipulation() {
        System.out.println("4. Linked List Manipulation:");
        
        // Merge two sorted lists
        ListNode list1 = createLinkedList(new int[]{1, 2, 4});
        ListNode list2 = createLinkedList(new int[]{1, 3, 4});
        System.out.println("List 1: " + printList(list1));
        System.out.println("List 2: " + printList(list2));
        
        ListNode merged = mergeTwoLists(list1, list2);
        System.out.println("Merged: " + printList(merged));
        
        // Sort linked list
        ListNode unsorted = createLinkedList(new int[]{4, 2, 1, 3});
        System.out.println("Unsorted: " + printList(unsorted));
        ListNode sorted = sortList(unsorted);
        System.out.println("Sorted: " + printList(sorted));
        
        // Remove duplicates from sorted list
        ListNode withDuplicates = createLinkedList(new int[]{1, 1, 2, 3, 3});
        System.out.println("With duplicates: " + printList(withDuplicates));
        ListNode withoutDuplicates = deleteDuplicates(withDuplicates);
        System.out.println("Without duplicates: " + printList(withoutDuplicates));
        
        // Partition list
        ListNode toPartition = createLinkedList(new int[]{1, 4, 3, 2, 5, 2});
        System.out.println("Original: " + printList(toPartition));
        ListNode partitioned = partition(toPartition, 3);
        System.out.println("Partitioned around 3: " + printList(partitioned));
        
        System.out.println();
    }
    
    // 5. Advanced Linked List Problems
    static void advancedLinkedListProblems() {
        System.out.println("5. Advanced Linked List Problems:");
        
        // Rotate list
        ListNode head = createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Original: " + printList(head));
        ListNode rotated = rotateRight(head, 2);
        System.out.println("Rotated right by 2: " + printList(rotated));
        
        // Swap nodes in pairs
        head = createLinkedList(new int[]{1, 2, 3, 4});
        System.out.println("Original: " + printList(head));
        ListNode swapped = swapPairs(head);
        System.out.println("Swapped pairs: " + printList(swapped));
        
        // Reverse nodes in k-group
        head = createLinkedList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Original: " + printList(head));
        ListNode reversedKGroup = reverseKGroup(head, 2);
        System.out.println("Reversed in groups of 2: " + printList(reversedKGroup));
        
        // Add two numbers represented as linked lists
        ListNode num1 = createLinkedList(new int[]{2, 4, 3}); // represents 342
        ListNode num2 = createLinkedList(new int[]{5, 6, 4}); // represents 465
        System.out.println("Number 1: " + printList(num1) + " (represents 342)");
        System.out.println("Number 2: " + printList(num2) + " (represents 465)");
        ListNode sum = addTwoNumbers(num1, num2);
        System.out.println("Sum: " + printList(sum) + " (represents 807)");
        
        System.out.println();
    }
    
    // 6. Java Built-in LinkedList
    static void javaBuiltInLinkedList() {
        System.out.println("6. Java Built-in LinkedList:");
        
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        // Adding elements
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.addFirst(0);
        linkedList.addLast(4);
        System.out.println("LinkedList: " + linkedList);
        
        // Accessing elements
        System.out.println("First element: " + linkedList.getFirst());
        System.out.println("Last element: " + linkedList.getLast());
        System.out.println("Element at index 2: " + linkedList.get(2));
        
        // Removing elements
        linkedList.removeFirst();
        linkedList.removeLast();
        System.out.println("After removing first and last: " + linkedList);
        
        // Using as queue (FIFO)
        LinkedList<String> queue = new LinkedList<>();
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("Queue: " + queue);
        System.out.println("Poll: " + queue.poll());
        System.out.println("Queue after poll: " + queue);
        
        // Using as stack (LIFO)
        LinkedList<String> stack = new LinkedList<>();
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");
        System.out.println("Stack: " + stack);
        System.out.println("Pop: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
        
        System.out.println();
    }
    
    // Helper methods for linked list operations
    
    // Create linked list from array
    static ListNode createLinkedList(int[] values) {
        if (values.length == 0) return null;
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    // Print linked list
    static String printList(ListNode head) {
        if (head == null) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
    
    // Create cycle in linked list for testing
    static void createCycle(ListNode head, int pos) {
        if (head == null || pos < 0) return;
        
        ListNode cycleNode = head;
        for (int i = 0; i < pos; i++) {
            if (cycleNode == null) return;
            cycleNode = cycleNode.next;
        }
        
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = cycleNode;
    }
    
    // Algorithm implementations
    
    // Reverse linked list
    static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    // Find middle of linked list
    static ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    // Detect cycle in linked list
    static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    // Find nth node from end
    static int findNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode second = head;
        
        // Move first pointer n steps ahead
        for (int i = 0; i < n; i++) {
            if (first == null) return -1; // n is larger than list length
            first = first.next;
        }
        
        // Move both pointers until first reaches end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        return second.val;
    }
    
    // Check if linked list is palindrome
    static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        
        // Find middle
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse second half
        ListNode secondHalf = reverseList(slow.next);
        
        // Compare first and second half
        ListNode firstHalf = head;
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        
        return true;
    }
    
    // Remove nth node from end
    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        
        // Move first pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        
        // Move both pointers until first reaches end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        // Remove the nth node
        second.next = second.next.next;
        
        return dummy.next;
    }
    
    // Find intersection of two linked lists
    static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        ListNode pA = headA;
        ListNode pB = headB;
        
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }
        
        return pA;
    }
    
    // Merge two sorted lists
    static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Append remaining nodes
        current.next = (list1 != null) ? list1 : list2;
        
        return dummy.next;
    }
    
    // Sort linked list using merge sort
    static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Find middle and split
        ListNode mid = findMiddle(head);
        ListNode secondHalf = mid.next;
        mid.next = null;
        
        // Recursively sort both halves
        ListNode left = sortList(head);
        ListNode right = sortList(secondHalf);
        
        // Merge sorted halves
        return mergeTwoLists(left, right);
    }
    
    // Remove duplicates from sorted list
    static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return head;
    }
    
    // Partition list around value x
    static ListNode partition(ListNode head, int x) {
        ListNode beforeHead = new ListNode(0);
        ListNode afterHead = new ListNode(0);
        ListNode before = beforeHead;
        ListNode after = afterHead;
        
        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }
        
        after.next = null;
        before.next = afterHead.next;
        
        return beforeHead.next;
    }
    
    // Rotate list to the right by k places
    static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        
        // Find length and make it circular
        ListNode tail = head;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        tail.next = head;
        
        // Find new tail (length - k % length - 1) steps from head
        k = k % length;
        int stepsToNewTail = length - k;
        ListNode newTail = head;
        for (int i = 1; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }
        
        ListNode newHead = newTail.next;
        newTail.next = null;
        
        return newHead;
    }
    
    // Swap every two adjacent nodes
    static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;
            
            prev.next = second;
            first.next = second.next;
            second.next = first;
            
            prev = first;
        }
        
        return dummy.next;
    }
    
    // Reverse nodes in k-group
    static ListNode reverseKGroup(ListNode head, int k) {
        ListNode current = head;
        int count = 0;
        
        // Check if we have k nodes
        while (current != null && count < k) {
            current = current.next;
            count++;
        }
        
        if (count == k) {
            // Reverse first k nodes
            current = reverseKGroup(current, k);
            
            while (count > 0) {
                ListNode next = head.next;
                head.next = current;
                current = head;
                head = next;
                count--;
            }
            head = current;
        }
        
        return head;
    }
    
    // Add two numbers represented as linked lists
    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }
        
        return dummy.next;
    }
}

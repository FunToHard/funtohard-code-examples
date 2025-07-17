import java.util.*;

/**
 * Trees and Graphs in Java
 * 
 * This class demonstrates tree and graph data structures and algorithms.
 */
public class TreesAndGraphs {
    
    // Binary Tree Node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    // Graph Node
    static class GraphNode {
        int val;
        List<GraphNode> neighbors;
        
        GraphNode() {
            val = 0;
            neighbors = new ArrayList<>();
        }
        
        GraphNode(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }
    }
    
    // Binary Search Tree
    static class BST {
        TreeNode root;
        
        public void insert(int val) {
            root = insertRec(root, val);
        }
        
        private TreeNode insertRec(TreeNode root, int val) {
            if (root == null) {
                return new TreeNode(val);
            }
            
            if (val < root.val) {
                root.left = insertRec(root.left, val);
            } else if (val > root.val) {
                root.right = insertRec(root.right, val);
            }
            
            return root;
        }
        
        public boolean search(int val) {
            return searchRec(root, val);
        }
        
        private boolean searchRec(TreeNode root, int val) {
            if (root == null) return false;
            if (root.val == val) return true;
            
            return val < root.val ? searchRec(root.left, val) : searchRec(root.right, val);
        }
        
        public void delete(int val) {
            root = deleteRec(root, val);
        }
        
        private TreeNode deleteRec(TreeNode root, int val) {
            if (root == null) return root;
            
            if (val < root.val) {
                root.left = deleteRec(root.left, val);
            } else if (val > root.val) {
                root.right = deleteRec(root.right, val);
            } else {
                if (root.left == null) return root.right;
                if (root.right == null) return root.left;
                
                root.val = minValue(root.right);
                root.right = deleteRec(root.right, root.val);
            }
            
            return root;
        }
        
        private int minValue(TreeNode root) {
            int minVal = root.val;
            while (root.left != null) {
                minVal = root.left.val;
                root = root.left;
            }
            return minVal;
        }
    }
    
    // Graph representation using adjacency list
    static class Graph {
        private int vertices;
        private List<List<Integer>> adjList;
        
        public Graph(int vertices) {
            this.vertices = vertices;
            adjList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjList.add(new ArrayList<>());
            }
        }
        
        public void addEdge(int src, int dest) {
            adjList.get(src).add(dest);
            adjList.get(dest).add(src); // For undirected graph
        }
        
        public void dfs(int start) {
            boolean[] visited = new boolean[vertices];
            dfsUtil(start, visited);
        }
        
        private void dfsUtil(int v, boolean[] visited) {
            visited[v] = true;
            System.out.print(v + " ");
            
            for (int neighbor : adjList.get(v)) {
                if (!visited[neighbor]) {
                    dfsUtil(neighbor, visited);
                }
            }
        }
        
        public void bfs(int start) {
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            
            visited[start] = true;
            queue.offer(start);
            
            while (!queue.isEmpty()) {
                int v = queue.poll();
                System.out.print(v + " ");
                
                for (int neighbor : adjList.get(v)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Trees and Graphs Demo ===\n");
        
        // 1. Binary Tree Operations
        binaryTreeOperations();
        
        // 2. Binary Search Tree
        binarySearchTree();
        
        // 3. Tree Traversals
        treeTraversals();
        
        // 4. Graph Operations
        graphOperations();
        
        // 5. Tree Algorithms
        treeAlgorithms();
        
        // 6. Graph Algorithms
        graphAlgorithms();
    }
    
    // 1. Binary Tree Operations
    static void binaryTreeOperations() {
        System.out.println("1. Binary Tree Operations:");
        
        // Create a binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        System.out.println("Tree height: " + height(root));
        System.out.println("Tree size: " + size(root));
        System.out.println("Is balanced: " + isBalanced(root));
        System.out.println("Maximum value: " + findMax(root));
        System.out.println("Minimum value: " + findMin(root));
        
        System.out.println();
    }
    
    // 2. Binary Search Tree
    static void binarySearchTree() {
        System.out.println("2. Binary Search Tree:");
        
        BST bst = new BST();
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 25: " + bst.search(25));
        
        System.out.print("Inorder traversal: ");
        inorderTraversal(bst.root);
        System.out.println();
        
        bst.delete(20);
        System.out.print("After deleting 20: ");
        inorderTraversal(bst.root);
        System.out.println();
        
        System.out.println();
    }
    
    // 3. Tree Traversals
    static void treeTraversals() {
        System.out.println("3. Tree Traversals:");
        
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        
        System.out.print("Preorder: ");
        preorderTraversal(root);
        System.out.println();
        
        System.out.print("Inorder: ");
        inorderTraversal(root);
        System.out.println();
        
        System.out.print("Postorder: ");
        postorderTraversal(root);
        System.out.println();
        
        System.out.print("Level order: ");
        levelOrderTraversal(root);
        System.out.println();
        
        System.out.println();
    }
    
    // 4. Graph Operations
    static void graphOperations() {
        System.out.println("4. Graph Operations:");
        
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        
        System.out.print("DFS from vertex 0: ");
        graph.dfs(0);
        System.out.println();
        
        System.out.print("BFS from vertex 0: ");
        graph.bfs(0);
        System.out.println();
        
        System.out.println();
    }
    
    // 5. Tree Algorithms
    static void treeAlgorithms() {
        System.out.println("5. Tree Algorithms:");
        
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        System.out.println("Maximum depth: " + maxDepth(root));
        System.out.println("Minimum depth: " + minDepth(root));
        System.out.println("Diameter: " + diameter(root));
        System.out.println("Has path sum 12: " + hasPathSum(root, 12));
        
        TreeNode lca = lowestCommonAncestor(root, root.left, root.right.left);
        System.out.println("LCA of 9 and 15: " + (lca != null ? lca.val : "null"));
        
        System.out.println();
    }
    
    // 6. Graph Algorithms
    static void graphAlgorithms() {
        System.out.println("6. Graph Algorithms:");
        
        // Topological sort
        int[][] prerequisites = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[] order = topologicalSort(4, prerequisites);
        System.out.println("Topological order: " + Arrays.toString(order));
        
        // Shortest path (unweighted)
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        int shortestPath = shortestPath(4, edges, 0, 3);
        System.out.println("Shortest path from 0 to 3: " + shortestPath);
        
        // Detect cycle
        boolean hasCycle = hasCycle(4, edges);
        System.out.println("Graph has cycle: " + hasCycle);
        
        System.out.println();
    }
    
    // Helper methods for tree operations
    static int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }
    
    static int size(TreeNode root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }
    
    static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }
    
    static int checkBalance(TreeNode root) {
        if (root == null) return 0;
        
        int left = checkBalance(root.left);
        if (left == -1) return -1;
        
        int right = checkBalance(root.right);
        if (right == -1) return -1;
        
        if (Math.abs(left - right) > 1) return -1;
        
        return Math.max(left, right) + 1;
    }
    
    static int findMax(TreeNode root) {
        if (root == null) return Integer.MIN_VALUE;
        return Math.max(root.val, Math.max(findMax(root.left), findMax(root.right)));
    }
    
    static int findMin(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        return Math.min(root.val, Math.min(findMin(root.left), findMin(root.right)));
    }
    
    // Tree traversal methods
    static void preorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + " ");
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }
    
    static void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.val + " ");
            inorderTraversal(root.right);
        }
    }
    
    static void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.print(root.val + " ");
        }
    }
    
    static void levelOrderTraversal(TreeNode root) {
        if (root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
    
    // Tree algorithm methods
    static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
    
    static int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        
        int minLeft = root.left != null ? minDepth(root.left) : Integer.MAX_VALUE;
        int minRight = root.right != null ? minDepth(root.right) : Integer.MAX_VALUE;
        
        return 1 + Math.min(minLeft, minRight);
    }
    
    static int diameter(TreeNode root) {
        int[] diameter = new int[1];
        diameterHelper(root, diameter);
        return diameter[0];
    }
    
    static int diameterHelper(TreeNode root, int[] diameter) {
        if (root == null) return 0;
        
        int left = diameterHelper(root.left, diameter);
        int right = diameterHelper(root.right, diameter);
        
        diameter[0] = Math.max(diameter[0], left + right);
        
        return Math.max(left, right) + 1;
    }
    
    static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        
        return hasPathSum(root.left, targetSum - root.val) || 
               hasPathSum(root.right, targetSum - root.val);
    }
    
    static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
    
    // Graph algorithm methods
    static int[] topologicalSort(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
            indegree[prereq[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] result = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[index++] = course;
            
            for (int neighbor : graph.get(course)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return index == numCourses ? result : new int[0];
    }
    
    static int shortestPath(int n, int[][] edges, int start, int end) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        
        queue.offer(start);
        visited[start] = true;
        int distance = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                if (node == end) return distance;
                
                for (int neighbor : graph.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            distance++;
        }
        
        return -1;
    }
    
    static boolean hasCycle(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (dfsHasCycle(graph, i, -1, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    static boolean dfsHasCycle(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (dfsHasCycle(graph, neighbor, node, visited)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
        
        return false;
    }
}

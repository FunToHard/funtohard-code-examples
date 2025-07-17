import java.util.*;

/**
 * Arrays and Strings in Java
 * 
 * This class demonstrates fundamental operations on arrays and strings,
 * including common algorithms and data manipulation techniques.
 */
public class ArraysAndStrings {
    
    public static void main(String[] args) {
        System.out.println("=== Arrays and Strings Demo ===\n");
        
        // 1. Array Operations
        arrayOperations();
        
        // 2. String Operations
        stringOperations();
        
        // 3. Array Algorithms
        arrayAlgorithms();
        
        // 4. String Algorithms
        stringAlgorithms();
        
        // 5. Two Pointer Technique
        twoPointerTechnique();
        
        // 6. Sliding Window Technique
        slidingWindowTechnique();
    }
    
    // 1. Array Operations
    static void arrayOperations() {
        System.out.println("1. Array Operations:");
        
        // Array declaration and initialization
        int[] arr1 = new int[5];
        int[] arr2 = {1, 2, 3, 4, 5};
        int[] arr3 = new int[]{10, 20, 30, 40, 50};
        
        System.out.println("Array 2: " + Arrays.toString(arr2));
        System.out.println("Array 3: " + Arrays.toString(arr3));
        
        // Array traversal
        System.out.print("Traversal: ");
        for (int i = 0; i < arr2.length; i++) {
            System.out.print(arr2[i] + " ");
        }
        System.out.println();
        
        // Enhanced for loop
        System.out.print("Enhanced for: ");
        for (int num : arr2) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Array copying
        int[] copied = Arrays.copyOf(arr2, arr2.length);
        System.out.println("Copied array: " + Arrays.toString(copied));
        
        // Array sorting
        int[] unsorted = {5, 2, 8, 1, 9, 3};
        Arrays.sort(unsorted);
        System.out.println("Sorted array: " + Arrays.toString(unsorted));
        
        // Array searching
        int index = Arrays.binarySearch(unsorted, 5);
        System.out.println("Index of 5: " + index);
        
        // Array filling
        int[] filled = new int[5];
        Arrays.fill(filled, 42);
        System.out.println("Filled array: " + Arrays.toString(filled));
        
        // Multi-dimensional arrays
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        
        System.out.println();
    }
    
    // 2. String Operations
    static void stringOperations() {
        System.out.println("2. String Operations:");
        
        String str1 = "Hello";
        String str2 = "World";
        String str3 = "Hello";
        
        // String concatenation
        String concatenated = str1 + " " + str2;
        System.out.println("Concatenated: " + concatenated);
        
        // String comparison
        System.out.println("str1.equals(str3): " + str1.equals(str3));
        System.out.println("str1 == str3: " + (str1 == str3)); // Reference comparison
        
        // String methods
        String text = "  Java Programming  ";
        System.out.println("Original: '" + text + "'");
        System.out.println("Length: " + text.length());
        System.out.println("Trimmed: '" + text.trim() + "'");
        System.out.println("Uppercase: " + text.toUpperCase());
        System.out.println("Lowercase: " + text.toLowerCase());
        System.out.println("Substring: " + text.substring(2, 6));
        System.out.println("Replace: " + text.replace("Java", "Python"));
        System.out.println("Contains 'Java': " + text.contains("Java"));
        System.out.println("Starts with '  Java': " + text.startsWith("  Java"));
        System.out.println("Ends with 'ing  ': " + text.endsWith("ing  "));
        
        // String splitting
        String csv = "apple,banana,cherry,date";
        String[] fruits = csv.split(",");
        System.out.println("Split CSV: " + Arrays.toString(fruits));
        
        // String joining
        String joined = String.join(" | ", fruits);
        System.out.println("Joined: " + joined);
        
        // StringBuilder for mutable strings
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        sb.insert(5, " Beautiful");
        sb.reverse();
        System.out.println("StringBuilder result: " + sb.toString());
        
        System.out.println();
    }
    
    // 3. Array Algorithms
    static void arrayAlgorithms() {
        System.out.println("3. Array Algorithms:");
        
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        System.out.println("Original array: " + Arrays.toString(arr));
        
        // Find maximum element
        int max = findMax(arr);
        System.out.println("Maximum element: " + max);
        
        // Find minimum element
        int min = findMin(arr);
        System.out.println("Minimum element: " + min);
        
        // Linear search
        int target = 5;
        int linearIndex = linearSearch(arr, target);
        System.out.println("Linear search for " + target + ": " + linearIndex);
        
        // Binary search (on sorted array)
        int[] sortedArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sortedArr);
        System.out.println("Sorted array: " + Arrays.toString(sortedArr));
        int binaryIndex = binarySearch(sortedArr, target);
        System.out.println("Binary search for " + target + ": " + binaryIndex);
        
        // Remove duplicates
        int[] unique = removeDuplicates(arr);
        System.out.println("Array without duplicates: " + Arrays.toString(unique));
        
        // Rotate array
        int[] rotated = rotateArray(arr, 3);
        System.out.println("Array rotated by 3: " + Arrays.toString(rotated));
        
        // Find second largest
        int secondLargest = findSecondLargest(arr);
        System.out.println("Second largest: " + secondLargest);
        
        System.out.println();
    }
    
    // 4. String Algorithms
    static void stringAlgorithms() {
        System.out.println("4. String Algorithms:");
        
        String text = "programming";
        System.out.println("Original string: " + text);
        
        // Check if palindrome
        String palindrome = "racecar";
        System.out.println("Is '" + palindrome + "' a palindrome? " + isPalindrome(palindrome));
        System.out.println("Is '" + text + "' a palindrome? " + isPalindrome(text));
        
        // Reverse string
        String reversed = reverseString(text);
        System.out.println("Reversed: " + reversed);
        
        // Count character frequency
        Map<Character, Integer> frequency = countCharacterFrequency(text);
        System.out.println("Character frequency: " + frequency);
        
        // Find first non-repeating character
        char firstNonRepeating = findFirstNonRepeatingChar(text);
        System.out.println("First non-repeating character: " + firstNonRepeating);
        
        // Check if anagram
        String str1 = "listen";
        String str2 = "silent";
        System.out.println("Are '" + str1 + "' and '" + str2 + "' anagrams? " + areAnagrams(str1, str2));
        
        // Longest common prefix
        String[] strings = {"flower", "flow", "flight"};
        String prefix = longestCommonPrefix(strings);
        System.out.println("Longest common prefix of " + Arrays.toString(strings) + ": '" + prefix + "'");
        
        // String compression
        String toCompress = "aabcccccaaa";
        String compressed = compressString(toCompress);
        System.out.println("Compressed '" + toCompress + "': " + compressed);
        
        System.out.println();
    }
    
    // 5. Two Pointer Technique
    static void twoPointerTechnique() {
        System.out.println("5. Two Pointer Technique:");
        
        // Two sum problem
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        int[] twoSumResult = twoSum(arr, target);
        System.out.println("Two sum indices for target " + target + ": " + Arrays.toString(twoSumResult));
        
        // Three sum problem
        int[] arr2 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> threeSumResult = threeSum(arr2);
        System.out.println("Three sum triplets: " + threeSumResult);
        
        // Container with most water
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int maxArea = maxArea(heights);
        System.out.println("Maximum water area: " + maxArea);
        
        // Remove duplicates from sorted array
        int[] sortedWithDuplicates = {1, 1, 2, 2, 2, 3, 4, 4, 5};
        int newLength = removeDuplicatesFromSorted(sortedWithDuplicates);
        System.out.println("Array after removing duplicates: " + 
            Arrays.toString(Arrays.copyOf(sortedWithDuplicates, newLength)));
        
        System.out.println();
    }
    
    // 6. Sliding Window Technique
    static void slidingWindowTechnique() {
        System.out.println("6. Sliding Window Technique:");
        
        // Maximum sum subarray of size k
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;
        int maxSum = maxSumSubarray(arr, k);
        System.out.println("Maximum sum of subarray of size " + k + ": " + maxSum);
        
        // Longest substring without repeating characters
        String str = "abcabcbb";
        int longestLength = lengthOfLongestSubstring(str);
        System.out.println("Length of longest substring without repeating chars in '" + str + "': " + longestLength);
        
        // Minimum window substring
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String minWindow = minWindow(s, t);
        System.out.println("Minimum window substring of '" + s + "' containing '" + t + "': '" + minWindow + "'");
        
        System.out.println();
    }
    
    // Helper methods for array algorithms
    static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    static int findMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    static int[] removeDuplicates(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        List<Integer> result = new ArrayList<>();
        for (int num : arr) {
            if (!seen.contains(num)) {
                seen.add(num);
                result.add(num);
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }
    
    static int[] rotateArray(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = arr[i];
        }
        return result;
    }
    
    static int findSecondLargest(int[] arr) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
        return secondLargest;
    }
    
    // Helper methods for string algorithms
    static boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
    
    static Map<Character, Integer> countCharacterFrequency(String str) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : str.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        return frequency;
    }
    
    static char findFirstNonRepeatingChar(String str) {
        Map<Character, Integer> frequency = countCharacterFrequency(str);
        for (char c : str.toCharArray()) {
            if (frequency.get(c) == 1) {
                return c;
            }
        }
        return '\0'; // No non-repeating character found
    }
    
    static boolean areAnagrams(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        
        return Arrays.equals(chars1, chars2);
    }
    
    static String longestCommonPrefix(String[] strings) {
        if (strings.length == 0) return "";
        
        String prefix = strings[0];
        for (int i = 1; i < strings.length; i++) {
            while (strings[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
    
    static String compressString(String str) {
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(str.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        compressed.append(str.charAt(str.length() - 1)).append(count);
        
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
    
    // Helper methods for two pointer technique
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
    
    static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
    
    static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int area = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, area);
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
    
    static int removeDuplicatesFromSorted(int[] nums) {
        if (nums.length == 0) return 0;
        
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
    
    // Helper methods for sliding window technique
    static int maxSumSubarray(int[] arr, int k) {
        int maxSum = 0;
        int windowSum = 0;
        
        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        maxSum = windowSum;
        
        // Slide the window
        for (int i = k; i < arr.length; i++) {
            windowSum = windowSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        
        Map<Character, Integer> tCount = new HashMap<>();
        for (char c : t.toCharArray()) {
            tCount.put(c, tCount.getOrDefault(c, 0) + 1);
        }
        
        int required = tCount.size();
        int formed = 0;
        Map<Character, Integer> windowCount = new HashMap<>();
        
        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        while (right < s.length()) {
            char c = s.charAt(right);
            windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);
            
            if (tCount.containsKey(c) && windowCount.get(c).intValue() == tCount.get(c).intValue()) {
                formed++;
            }
            
            while (left <= right && formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                char leftChar = s.charAt(left);
                windowCount.put(leftChar, windowCount.get(leftChar) - 1);
                if (tCount.containsKey(leftChar) && windowCount.get(leftChar) < tCount.get(leftChar)) {
                    formed--;
                }
                left++;
            }
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
}

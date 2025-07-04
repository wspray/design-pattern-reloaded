package algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AllSolution {
    //    1.两数之和
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map.containsKey(key)) {
                return new int[]{map.get(key), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

    //    2.两数相加
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode();
        ListNode cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;
            cur.next = new ListNode((v1 + v2 + carry) % 10);
            carry = (v1 + v2 + carry) / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (carry == 1) {
            cur.next = new ListNode(1);
        }
        return pre.next;
    }

    //    3. 无重复字符的最长子串   abcbcdf
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int n = s.length();
        int start = 0, end = 0;
        int answer = 0;
        while (start < n && end < n) {
            if (!set.contains(s.charAt(end))) {
                set.add(s.charAt(end++));
                answer = Math.max(answer, end - start);
            } else {
                set.remove(s.charAt(start++));
            }
        }
        return answer;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        int start = 0;
        int answer = 0;
        for (int end = 0; end < n; end++) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                // 如果字符c已经在窗口中出现过，则移动窗口起始位置到c上一次出现位置+1
                start = Math.max(map.get(c) + 1, start);
            }
            // 更新字符c的最新位置
            map.put(c, end);
            // 计算当前窗口长度end - start + 1，并更新最大值
            answer = Math.max(answer, end - start + 1);
        }
        return answer;
    }

    // 206. 反转链表
    public ListNode reverseList(ListNode head) {
        ListNode reverse = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = reverse;
            reverse = head;
            head = next;
        }
        return reverse;
    }
//    public static void main(String[] args) {
//        ListNode listNode = new ListNode(1,new ListNode(2,new ListNode(3)));
//        ListNode result = new AllSolution().reverseList(listNode);
//        while (result!=null) {
//            System.out.println(result.val);
//            result = result.next;
//        }
//    }

    // 2095 删除链表的中间节点
    public ListNode deleteMiddle(ListNode head) {
        int size = 0;
        ListNode result = head;
        ListNode temp = head;
        while (head != null) {
            size++;
            head = head.next;
        }
        if (size == 1) {
            return null;
        }
        int index = 0;
        int pre = size / 2 - 1;
        while (temp != null) {
            if (index == pre) {
                removeNext(temp);
            }
            index++;
            temp = temp.next;
        }
        return result;
    }

    private void removeNext(ListNode pre) {
        if (pre.next == null) {
            return;
        }
        ListNode next = pre.next;
        pre.next = next.next;
        next.next = null;
    }

    public ListNode deleteMiddle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode left = head, right = head, pre = null;
        while (right != null && right.next != null) { //关键
            pre = left;
            left = left.next;
            right = right.next.next;
        }
        removeNext(pre);
        return head;
    }

    // 328. 奇偶链表  1 3 2 4 5
    public ListNode oddEvenList(ListNode head) {
        ListNode left = head;
        if (head == null || head.next == null) { //处理0个或1个元素的时候
            return head;
        }
        ListNode right = head.next, temp = right;
        if (right.next == null) { // 处理2个元素的时候
            return head;
        }
        while (right != null && right.next != null) {
            ListNode leftNext = right.next;
            ListNode rightNext = right.next.next;
            left.next = leftNext;
            left = leftNext;
            right.next = rightNext;
            right = rightNext;
        }
        left.next = temp;
        return head;
    }

//    public static void main(String[] args) {
//        ListNode list = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        ListNode result = new AllSolution().oddEvenList(list);
//        while (result != null) {
//            System.out.println(result.val);
//            result = result.next;
//        }
//    }

    // 1768. 交替合并字符串
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int length1 = word1.length();
        int length2 = word2.length();
        boolean big = length1 > length2;
        int t = Math.max(length1, length2);
        int diff = Math.abs(length1 - length2);
        int a = 0, b = 0;
        for (int i = 0; i < t; i++) {
            if (a == b && a < length1 && b < length2) {
                result.append(word1.charAt(i)).append(word2.charAt(i));
            } else if (big) {
                result.append(word1, length1 - diff, length1);
                break;
            } else {
                result.append(word2, length2 - diff, length2);
                break;
            }
            a++;
            b++;
        }
        return result.toString();
    }

    public static String mergeAlternately2(String word1, String word2) {
        StringBuilder merged = new StringBuilder();
        int i = 0, j = 0;
        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) {
                merged.append(word1.charAt(i++));
            }
            if (j < word2.length()) {
                merged.append(word2.charAt(j++));
            }
        }
        return merged.toString();
    }
//    public static void main(String[] args) {
//        System.out.println(new AllSolution().mergeAlternately("abcd", "pq"));
//    }

    // 2390. 从字符串中移除星号
    public String removeStars(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            stack.add(c);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int star = 0;
        while (!stack.isEmpty()) {
            Character top = stack.pop();
            if (star == 0 && '*' != top) {
                stringBuilder.insert(0, top);
            }
            if ('*' == top) {
                star++;
            } else if (star > 0) {
                star--;
            }
        }
        return stringBuilder.toString();
    }

    public String removeStars2(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : s.toCharArray()) {
            if ('*' != c) {
                stringBuilder.append(c);
            } else {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
        }
        return stringBuilder.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(new AllSolution().removeStars2("leet**cod*e"));
//    }

    // 735. 小行星碰撞
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> positive = new Stack<>();
        Stack<Integer> negative = new Stack<>();
        for (int asteroid : asteroids) {
            if (asteroid < 0) {
                negative.push(asteroid);
            } else {
                positive.push(asteroid);
            }
        }
        while (!positive.empty() && !negative.isEmpty()) {
            if (Math.abs(positive.peek()) > Math.abs(negative.peek())) {
                negative.pop();
            } else if (Math.abs(positive.peek()) < Math.abs(negative.peek())) {
                positive.pop();
            } else {
                negative.pop();
                positive.pop();
            }
        }
        Stack<Integer> remain = positive.empty() ? negative : positive;
        int[] result = new int[remain.size()];
        for (int i = 0; i < remain.size(); i++) {
            result[i] = remain.get(i);
        }
        return result;
    }

    // 394. 字符串解码
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder current = new StringBuilder();
        int k = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                countStack.push(k);
                stringStack.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder decoded = stringStack.pop();
                int repeat = countStack.pop();
                decoded.append(String.valueOf(current).repeat(Math.max(0, repeat)));
                current = decoded;
            } else {
                current.append(ch);
            }
        }
        return current.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(new AllSolution().decodeString("ab10[cd]"));//输出abccdcd
//    }

    // 649. Dota2参议院
    public String predictPartyVictory(String senate) {
        int n = senate.length();
        Queue<Integer> radiant = new LinkedList<>();
        Queue<Integer> dire = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            if (senate.charAt(i) == 'R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }
        while (!radiant.isEmpty() && !dire.isEmpty()) {
            int radiantIndex = radiant.poll();
            int direIndex = dire.poll();
            if (radiantIndex < direIndex) {
                radiant.offer(radiantIndex + n);
            } else {
                dire.offer(direIndex + n);
            }
        }
        return !radiant.isEmpty() ? "Radiant" : "Dire";
    }
//    public static void main(String[] args) {
//        System.out.println(new AllSolution().predictPartyVictory("RDD"));
//    }

    // 151.反转字符串中的单词
    public String reverseWords(String s) {
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);
    }

    public String reverseWords2(String s) {
        int left = 0, right = s.length() - 1;
        // 去掉字符串开头的空白字符
        while (left <= right && s.charAt(left) == ' ') {
            ++left;
        }
        // 去掉字符串末尾的空白字符
        while (left <= right && s.charAt(right) == ' ') {
            --right;
        }
        Deque<String> d = new ArrayDeque<>();
        StringBuilder word = new StringBuilder();
        while (left <= right) {
            char c = s.charAt(left);
            if ((word.length() != 0) && (c == ' ')) {
                // 将单词 push 到队列的头部
                d.offerFirst(word.toString());
                word.setLength(0);
            } else if (c != ' ') {
                word.append(c);
            }
            ++left;
        }
        d.offerFirst(word.toString());
        return String.join(" ", d);
    }
//    public static void main(String[] args) {
//        System.out.println(new AllSolution().reverseWords2("example   good a 1"));
//    }

    // 11. 盛最多水的容器
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while (left <= right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }
//    public static void main(String[] args) {
//        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
//        System.out.println(new AllSolution().maxArea(height));
//    }

    // 1431. 拥有最多糖果的孩子
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        if (candies.length < 1) {
            return null;
        }
        int max = Arrays.stream(candies).max().getAsInt();
        List<Boolean> result = new ArrayList<>(candies.length);
        for (int candy : candies) {
            if (candy + extraCandies < max) {
                result.add(false);
            } else {
                result.add(true);
            }
        }
        return result;
    }
//    public static void main(String[] args) {
//        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
//        System.out.println(new AllSolution().kidsWithCandies(height, 3));
//    }

    // 1071. 字符串的最大公因子
    public String gcdOfStrings(String str1, String str2) {
        if (str1.length() > str2.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }
        String result = "";
        int length1 = str1.length();
        for (int i = length1; i > 0; i--) {       //    最小公因子：for (int i = 1; i < length1; i++) {
            if (str1.length() % i == 0 && str2.length() % i == 0) {
                String substring = str1.substring(0, i);
                if (gcd(substring, str1) && gcd(substring, str2)) {
                    return substring;
                }
            }
        }
        return result;
    }

    private boolean gcd(String subString, String str) {
        int lenx = str.length() / subString.length();
        return subString.repeat(lenx).equals(str);
    }

//    public static void main(String[] args) {
//        System.out.println(new AllSolution().gcdOfStrings("ABABABAB", "ABAB"));
//    }

    // 283. 移动零
    public void moveZeroes(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i : nums) {
            if (i != 0) {
                list.addLast(i);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (!list.isEmpty()) {
                nums[i] = list.poll();
            } else {
                nums[i] = 0;
            }
        }
    }

    public void moveZeroes2(int[] nums) {
        int left = 0, right = 0;
        while (right < nums.length) {
            if (nums[right] != 0) {     // 当右指针值为0时，左指针停滞
                swapDigit(nums, left, right);
                left++;
            }
            right++;
        }
    }

    private void swapDigit(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }
        int temp = nums[right];
        nums[right] = nums[left];
        nums[left] = temp;
    }

//    public static void main(String[] args) {
////        Integer[] array1 = Arrays.asList(1, 2).toArray(new Integer[0]);
//        int[] array = IntStream.of(0, 1, 0, 3, 12).toArray();
//        new AllSolution().moveZeroes2(array);

    /// /        Arrays.stream(array).forEach(System.out::println);
//        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(",")));
//    }

    // 643. 子数组最大平均数 I
    public double findMaxAverage(int[] nums, int k) {
        int left = 0, right = 0, sum = 0;
        double avg = Integer.MIN_VALUE;
        while (right < nums.length) {
            sum += nums[right];
            if (right + 1 > k) {
                sum = sum - nums[left];
                left++;
                avg = Math.max(avg, sum * 1.0 / k);
            } else if (right + 1 == k) {
                avg = Math.max(avg, sum * 1.0 / k);
            }
            right++;
        }
        return avg;
    }

//    public static void main(String[] args) {
//        int[] nums = {-1};
//        System.out.println(new AllSolution().findMaxAverage(nums, 1));
//    }

    // 1456. 定长子串中元音的最大数目
    public int maxVowels(String s, int k) {
        Set<Character> set = Stream.of('a', 'e', 'i', 'o', 'u').collect(Collectors.toSet());
        int[] nums = new int[s.length()];
        char[] array = s.toCharArray();
        int max = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            nums[i] = set.contains(array[i]) ? 1 : 0;
            sum += nums[i];
            if (i + 1 == k) {
                max = Math.max(max, sum);
                ;
            } else if (i + 1 > k) {
                sum = sum - nums[i - k];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // 1004. 最大连续1的个数 III   111000110011   不定长滑动窗口
    public int longestOnes(int[] nums, int k) {
        int left = 0, right, zeros = 0, result = 0;
        for (right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > k) {
                if (nums[left] == 0) {
                    zeros--;
                }
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }

    // 1493. 删掉一个元素以后全为1的最长子数组
    public int longestSubarray(int[] nums) {
        int left = 0, right, zeros = 0, result = 0;
        for (right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > 1) {
                if (nums[left] == 0) {
                    zeros--;
                }
                left++;
            }
            result = Math.max(result, right - left);
        }
        return result;
    }

    // 1732. 找到最高海拔 （前缀和思想）
    public int largestAltitude(int[] gain) {
        int ans = 0, sum = 0;
        for (int x : gain) {
            sum += x;
            ans = Math.max(ans, sum);
        }
        return ans;
    }

//    public static void main(String[] args) {
//        int[] gain = {-5, 1, 5, 0, -7};
//        System.out.println(new AllSolution().largestAltitude(gain));
//    }

    // 724. 寻找数组的中心索引
    public int pivotIndex(int[] nums) {
        // s[i] = sum - n[i] -s[i]
        int sum = Arrays.stream(nums).sum();
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == sum - 2 * preSum) {
                return i;
            }
            preSum += nums[i];
        }
        return -1;
    }

    // 2215. 找出两数组的不同
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>(2);
        nums1 = Optional.ofNullable(nums1).orElse(new int[0]);
        nums2 = Optional.ofNullable(nums2).orElse(new int[0]);
        Set<Integer> s1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> s2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        for (int i : nums1) {
            if (!s2.contains(i) && !l1.contains(i)) {
                l1.add(i);
            }
        }
        for (int i : nums2) {
            if (!s1.contains(i) && !l2.contains(i)) {
                l2.add(i);
            }
        }
        result.add(l1);
        result.add(l2);
        return result;
    }

//    public static void main(String[] args) {
//        int[] nums1 = {};
//        int[] nums2 = null;
//        System.out.println(new AllSolution().findDifference(nums1, nums2));
//    }

    // 1207. 独一无二的出现次数
    public boolean uniqueOccurrences(int[] arr) {
        arr = Optional.ofNullable(arr).orElse(new int[0]);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        return map.size() == new HashSet<>(map.values()).size();
    }

    // 1657. 确定两个字符串是否接近
    public boolean closeStrings(String word1, String word2) {
        Map<Character, Integer> s1 = new HashMap<>();
        Map<Character, Integer> s2 = new HashMap<>();
        for (char c : word1.toCharArray()) {
            s1.put(c, s1.getOrDefault(c, 0) + 1);
        }
        for (char c : word2.toCharArray()) {
            if (!s1.containsKey(c)) {
                return false;
            }
            s2.put(c, s2.getOrDefault(c, 0) + 1);
        }
        if (s1.size() != s2.size()) {
            return false;
        }
        List<Integer> l1 = s1.values().stream().sorted().toList();
        List<Integer> l2 = s2.values().stream().sorted().toList();
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i).intValue() != l2.get(i).intValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean closeStrings2(String word1, String word2) {
        int[] w1 = new int[26];
        int[] w2 = new int[26];
        for (char c : word1.toCharArray()) {
            w1[c - 'a']++;
        }
        for (char c : word2.toCharArray()) {
            w2[c - 'a']++;
        }
        for (int i = 0; i < w1.length; i++) {
            if (w1[i] > 0 && w2[i] == 0 || w2[i] > 0 && w1[i] == 0) {
                return false;
            }
        }
        Arrays.sort(w1);
        Arrays.sort(w2);
        return Arrays.equals(w1, w2);
    }

//    public static void main(String[] args) {
//        System.out.println(new AllSolution().closeStrings2("abc", "bca"));
//    }

    // 2352. 相等行列对
    public int equalPairs(int[][] grid) {
        int result = 0;
        Map<List<Integer>, Integer> map = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            List<Integer> key = Arrays.stream(grid[i]).boxed().toList();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        for (int i = 0; i < grid.length; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < grid.length; j++) {
                list.add(grid[j][i]);
            }
            if (map.containsKey(list)) {
                result = result + map.get(list);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        int[] r1 = {3, 2, 1};
//        int[] r2 = {1, 7, 6};
//        int[] r3 = {2, 7, 7};
//        int[][] grid = {r1, r2, r3};
//        System.out.println(new AllSolution().equalPairs(grid));
//    }
}
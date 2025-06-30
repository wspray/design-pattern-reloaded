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
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                removeMiddle(temp);
            }
            index++;
            temp = temp.next;
        }
        return result;
    }

    private void removeMiddle(ListNode pre) {
        if (pre.next == null) {
            return;
        }
        ListNode next = pre.next;
        pre.next = next.next;
        next.next = null;
    }

    public ListNode deleteMiddle2(ListNode head) {
        if (head==null || head.next==null){
            return null;
        }
        if (head.next.next==null){
            removeMiddle(head);
            return head;
        }
        ListNode left = head, right = head.next.next, pre = null;
        while (right != null) {
            pre = left;
            left = left.next;
            if (right.next != null) {
                right = right.next.next;
            } else {
                right = null;
            }
        }
        removeMiddle(pre);
        return head;
    }

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

    public static void main(String[] args) {
//        Integer[] array1 = Arrays.asList(1, 2).toArray(new Integer[0]);
        int[] array = IntStream.of(0, 1, 0, 3, 12).toArray();
        new AllSolution().moveZeroes2(array);
//        Arrays.stream(array).forEach(System.out::println);
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(",")));
    }
}
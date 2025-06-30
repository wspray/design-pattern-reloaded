package doc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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


}
import com.sun.source.tree.NewArrayTree;

import java.util.*;

/**
 * ClassName: StringLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/12 22:04
 * @Version 1.0
 */


public class StringLearning {

    static class Solution {
        public int pivotIndex(int[] nums) {
            int len = nums.length;
            int left = 0, right = len - 1;
            int leftSum = 0,  sum = 0;
            // 先求总和
            for (int i = 0; i < len; i++) {
                sum += nums[i];
            }
            // 如果i左边元素之和，等于(sum-i左边元素之和-i本身)，那么说明当前元素左右相等
            for (int i = 0; i < len; i++) {
                sum -= nums[i];
                if(leftSum == sum){
                    return i;
                }
                leftSum += nums[i];
            }


            return -1;
        }

        public int searchInsert(int[] nums, int target) {
            int len = nums.length, left = 0, right = len-1 ,mid;
            while(left <= right){
                mid = (left+right)/2;
                if(nums[mid] == target){
                    return mid;
                }else if(nums[mid] < target){
                    left = mid + 1;
                }else{
                    right = mid - 1;
                }
            }
            return left;
        }

        public int[][] merge(int[][] intervals) {
            int len = intervals.length, pos = 0, left, right, retLen = 0;
            Arrays.sort(intervals, (a,b)->a[0]-b[0]);
            List<int[]> ret = new ArrayList<>();
            while(pos < len){
                left = intervals[pos][0];
                right = intervals[pos][1];
                while(pos + 1 < len && right >= intervals[pos + 1][0]){
                    pos ++;
                    if(right < intervals[pos][1]) right = intervals[pos][1];
                }
                ret.add(new int[]{left,right});
                retLen++;
                pos++;
            }
            return ret.toArray(new int[ret.size()][2]);
        }



        public String longestCommonPrefix(String[] strs) {
            int len = strs.length, minLen = strs[0].length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                minLen = Math.min(strs[i].length(), minLen);
            }
            a:for (int i = 0; i < minLen; i++) {
                String s = strs[0].substring(i,i+1);
                for (int j = 0; j < len; j++) {
                    if(!strs[j].substring(i,i+1).equals(s)) break a;
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        }

        // 核心思想：公共前缀的长度只减不增
        public String longestCommonPrefix_optimized(String[] strs) {
            String prefix = strs[0];
            for (String s : strs) {
                // 如果当前
                if(prefix.startsWith(s)){
                    prefix = s;
                    continue;
                }
                for (int j = 0; j < prefix.length(); j++) {
                    if(prefix.charAt(j) != s.charAt(j)){
                        prefix = new String(Arrays.copyOf(prefix.toCharArray(),j));
                    }
                }
            }
            return prefix;
        }

        public String longestPalindrome(String s) {
            int resIdx = 0, resLen = 1;
            for (int i = 0; i < s.length()-1; i++) {
                int len1 = getLongest(s, i, i);
                int len2 = getLongest(s, i, i+1);
                if(len1 > resLen){
                    resLen = len1;
                    resIdx = i - len1/2;
                }
                if(len2 > resLen){
                    resLen = len2;
                    resIdx = i - len2/2 + 1;
                }
            }
            return s.substring(resIdx,resIdx+resLen);
        }

        int getLongest(String s, int left, int right){
            while(left >=0 && right < s.length() && s.charAt(left) == s.charAt(right)){
                left--;
                right++;
            }
            // 退出循环时 s.charAt(left) != s.charAt(right) 或者已经到s两头了，所以left和right中间的几个数字才是回文串
            return right-left-1;
        }


        public String reverseWords(String s) {
            StringBuilder sb = new StringBuilder();
            Stack<String> stack = new Stack<>();
            int i = 0;
            while(i<s.length()){
                int left = i;
                while(i < s.length() && s.charAt(i) != ' ') i++;
                stack.push(s.substring(left,i));
                while(i < s.length() && s.charAt(i) == ' ') i++;
            }
            while(!stack.isEmpty()){
                sb.append(stack.pop() + " ");
            }

            return sb.toString().trim();
        }

        public String reverseWords_optimized(String s) {
            StringBuilder sb = new StringBuilder();
            String[] strs = s.split(" ");
            for (int i = strs.length-1; i >= 0 ; i--) {
                if(!strs[i].isEmpty())
                    sb.append(strs[i] + " ");
            }

            return sb.toString().trim();
        }


        public int strStr(String haystack, String needle) {
            if(needle.length()==0) return 0;
            int m = haystack.length(), i = 0;
            int n = needle.length(), j = 0;

            int[] next = nextBuilder(needle);
            while(i<m && j<n){
                if(j<0 || haystack.charAt(i) == needle.charAt(j)){
                    i++; j++;
                }else {
                    j = next[j];
                }
            }
            if(j == n) return i-j;
            return -1;
        }

        private int[] nextBuilder(String needle){
            int m = needle.length();
            int[] next = new int[m];
            next[0] = -1;
            int t = -1, j = 0;
            while(j < m - 1){
                if(t<0 || needle.charAt(t) == needle.charAt(j)){
                    t++; j++; next[j]=t;
                }else{
                    t = next[t];
                }
            }
            return next;
        }

        public void reverseString(char[] s) {
            int left=0, right = s.length-1;
            while(left<right){
                char tmp = s[left];
                s[left] = s[right];
                s[right] = tmp;
                left++; right--;
            }
        }

        public int arrayPairSum(int[] nums) {
            Arrays.sort(nums);
            int res = 0;
            for (int i = 0; i < nums.length; i+=2) {
                res += nums[i];
            }

            return res;
        }

        public int[] twoSum(int[] numbers, int target) {
            for (int i = 0; i < numbers.length; i++) {
                int left = i+1,right = numbers.length-1;
                while(left<=right){
                    int mid = (left+right)/2, curr = numbers[mid] + numbers[i];
                    if(curr == target){
                        return new int[]{i+1, mid+1};
                    } else if (curr > target) {
                        right = mid-1;
                    }else{
                        left = mid+1;
                    }
                }
            }
            return new int[2];
        }

        public int removeElement(int[] nums, int val) {
            int slow = 0;
            for (int i = 0; i < nums.length; i++) {
                if(nums[i] != val){
                    nums[slow++] = nums[i];
                }
            }

            return slow+1;
        }

        public int findMaxConsecutiveOnes(int[] nums) {
            int len = 0;
            for (int i = 0; i < nums.length; i++) {
                // 找到第一个 1;
                while(i< nums.length && nums[i] == 0) i++;
                if(i == nums.length) break;
                int currLen = 0;
                while(i< nums.length && nums[i] == 1){
                    i++; currLen++;
                }
                if(currLen > len){
                    len = currLen;
                }
            }
            return len;
        }

        public int minSubArrayLen(int target, int[] nums) {
            int res = nums.length, slow = 0, fast = 0, sum = 0;
            while(fast<nums.length){
                sum+=nums[fast];
                while(sum-nums[slow]>=target)
                {
                    sum-=nums[slow];
                    slow++;
                }
                if(sum >= target) res= Math.min(fast - slow + 1, res);
                fast++;
            }
            if(res == nums.length && sum < target) return 0;
            return res;
        }

        // 反转字符串中的单词 III
        public String reverseWords_3(String s) {
            String[] strs = s.split(" ");
            StringBuilder res = new StringBuilder();
            for(String curr : strs){
                char[] temp = curr.toCharArray();
                for (int i = 0; i < temp.length/2; i++) {
                    char swap = temp[i];
                    temp[i] = temp[temp.length-i-1];
                    temp[temp.length-i-1] = swap;
                }
                res.append(temp).append(" ");
            }
            return res.toString().trim();
        }

        public String reverseWords_3_optimized(String s) {
            char[] origin = s.toCharArray();
            // i 用来找当前单词的最后一个位置,j用来标记当前单词的开头位置
            int i = 0, j = 0, len = origin.length;
            for(; i < len; i ++){
                char ch = origin[i];
                if(ch == ' ' || i == len-1){
                    // m 取当前单词左边，n 取当前单词右边，如果不是空格说明到末尾了，就取当前位置即可
                    int m = j, n = ch ==' '?i-1:i;
                    while(m<n){
                        char temp = origin[m];
                        origin[m++] = origin[n];
                        origin[n--] = temp;
                    }
                    j = i+1;
                }
            }
            return new String(origin);
        }

        // 寻找旋转排序数组中的最小值
        public int findMin(int[] nums) {
            int left = 0, right = nums.length-1;
            if(nums[left]<=nums[right]) return nums[left];
            while(left < right){
                int mid = (left+right)/2;
                if(nums[mid] > nums[left]) {
                    left = mid;
                }else{
                    right = mid;
                }
            }
            return nums[left+1];
        }

        // 删除排序数组中的重复项
        public int removeDuplicates(int[] nums) {
            int slow = 0, fast = 1;
            for (; fast < nums.length; fast++) {
                if(nums[fast] != nums[slow]){
                    nums[++slow] = nums[fast];
                }
            }
            return slow+1;
        }

        // 移动零
        public void moveZeroes(int[] nums) {
            int slow = 0;
            for (int i = 0; i < nums.length; i++) {
                if(nums[i] == 0){
                    continue;
                }else{
                    nums[slow++] = nums[i];
                }
            }
            for(;slow<nums.length;slow++){
                nums[slow] = 0;
            }
        }


        // 字符串解码
        // 思路：递归处理，这个递归函数用于解析 "数字[子串]" 中的 "子串" 部分
        public String decodeString(String s) {
            char[] strs = s.toCharArray();
            // 存储子串的结果
            StringBuilder once = new StringBuilder();
            // pos 用于遍历子串
            int pos = 0;
            // 当 pos 大于strs.length时说明这是最外层，不再往后解析
            // while中通过一些方法会跳过内部的']'，所以pos==']'的时候表明已经解析完成
            while(pos < strs.length && strs[pos] != ']'){
                // 普通字母不用重复
                if(!isNumber(strs[pos]) && strs[pos] != '[' && strs[pos] != ']') once.append(strs[pos]);
                // 如果遇见数字，说明后面有需要处理的递归
                if(isNumber(strs[pos])){
                    // 获取内层递归"数字[子串]"中的"数字"number
                    int recurLeft = pos;
                    while(isNumber(strs[pos])){
                        pos++;
                    }// 退出时strs[pos]指向左括号

                    // 用栈查找递归的工作范围, pos会停留在']'的下一位字符或字符串末尾的下一位
                    int countLeft = 1, right = pos+1;
                    while(countLeft > 0 && right < s.length()){
                        if(strs[right] == '[') countLeft++;
                        if(strs[right] == ']') countLeft--;
                        right++;
                    }

                    int number = Integer.parseInt(s.substring(recurLeft, pos));
                    pos++; // pos跳过左括号
                    // 给结果添加上number个递归子串
                    // 注意：这里一定要将子串先计算再while，否则会做很多重复的递归运算
                    String subString = decodeString(s.substring(pos));
                    while(number -- > 0){
                        once.append(subString);
                    }
                    // pos后移到递归处理的结尾右括号']'
                    // 注意：这里不能直接去找 pos 后面的第一个']',因为可能还有内层递归
                    pos=right-1;
                }
                // 如果没有递归则处理下一位，如果处理了递归就跳过右括号
                pos++;
            }

            return once.toString();
        }

        private Boolean isNumber(char c){
            return c >= '0' && c <= '9';
        }






    }
    public static void main(String[] args) {



/*        String s = "2[a]c";
        System.out.println(new Solution().decodeString(s));*/

/*        int[] nums = new int[]{4,5,6,7,0,1,2};
        System.out.println(new Solution().findMin(nums));*/

/*        String s = "Let's take LeetCode contest";
        System.out.println(new Solution().reverseWords_3_optimized(s));*/

/*        int[] numbers = new int[]{2,7,11,15};
        int target = 9;
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(new Solution().twoSum(numbers, target)));*/

    }



}

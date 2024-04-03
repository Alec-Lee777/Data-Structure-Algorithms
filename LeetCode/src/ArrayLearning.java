import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * ClassName: ArrayLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/23 17:28
 * @Version 1.0
 */
public class ArrayLearning {


    public int removeDuplicates(int[] nums) {
        int left = 1;
        for (int i = 1; i < nums.length; i++) {
            if( i > 0 && i < nums.length - 1 && nums[i - 1] == nums[i] && nums[i] == nums[i + 1]) continue;
            nums[left++] = nums[i];
        }

        return left;
    }

    public void sortColors(int[] nums) {
        int left = 0, right = nums.length - 1, curr = 0;
        while(curr <= right){
            if(nums[curr] == 0){
                nums[curr++] = 1;
                nums[left++] = 0;
            }else if(nums[curr] == 2){
                nums[curr] = nums[right];
                nums[right--] = 2;
            }else{
                curr ++;
            }
        }
    }

    private void buildMaxHeap(int[] nums) {
        for (int i = nums.length/2; i >= 1; i--) {
            adjust(nums, i, nums.length);
        }
    }

    private void adjust(int[]nums, int i, int length){
        while(2 * i <= length){
            if(2 * i + 1 > length){
                if(nums[2 * i - 1] > nums[i-1]){
                    swap(nums, 2 * i - 1, i-1);
                }
                return;
            }
            int maxIdx = nums[2 * i - 1] > nums[2 * i - 1 + 1]? 2 * i - 1 : 2 * i - 1 + 1;
            if(nums[maxIdx] > nums[i-1]){
                swap(nums, maxIdx, i-1);
                i = maxIdx + 1;
            }else{
                return;
            }
        }
    }

    private void  swap(int[] nums, int x, int y){
        nums[x] = nums[x] + nums[y];
        nums[y] = nums[x] - nums[y];
        nums[x] = nums[x] - nums[y];
    }

    public int findKthLargest(int[] nums, int k) {
        buildMaxHeap(nums);
        int length = nums.length;
        for (int i = 1; i < k; i++) {
            swap(nums, 0, length-1);
            adjust(nums, 1, --length);
        }
        return nums[0];
    }


    public int findKthLargest_quicksort(int[] nums, int k) {
        return quickSortKth(nums, 0, nums.length - 1, k);
    }

    private int quickSortKth(int[] nums, int left, int right, int k) {
        int l = left, r = right, pivot = nums[l];
        while(l < r){
            while(l < r && nums[r] >= pivot) r--;
            nums[l] = nums[r];
            while(l < r && nums[l] <= pivot) l++;
            nums[r] = nums[l];
        }
        nums[l] = pivot;
        if(l == nums.length - k) return nums[l];
        else if(l < nums.length - k) return quickSortKth(nums, l + 1, right, k);
        else return quickSortKth(nums, left, l - 1, k);
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = nums1.length - 1; i >= nums1.length - m; i--) {
            nums1[i] = nums1[i - n];
        }

        int idx1 = n, idx2 = 0, idx = 0;
        while(idx1 < m + n && idx2 < n){
            if(nums1[idx1] < nums2[idx2]){
                nums1[idx++] = nums1[idx1++];
            }else{
                nums1[idx++] = nums2[idx2++];
            }
        }

        while(idx1 < m + n)
            nums1[idx++] = nums1[idx1++];
        while(idx2 < n)
            nums1[idx++] = nums2[idx2++];
    }


    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        s = s.toLowerCase();
        while(left < right){
            while(left < right && !Character.isLetter(s.charAt(left)) && !Character.isDigit(s.charAt(left))) left ++;
            while(left < right && !Character.isLetter(s.charAt(right)) && !Character.isDigit(s.charAt(right))) right --;
            if(s.charAt(left) != s.charAt(right)) return false;
            left++; right --;
        }
        return true;
    }


    public String reverseVowels(String s) {
        char[] strs = s.toCharArray();
        int left = 0, right = s.length() - 1;
        while(left < right){
            while(left < right && !isVowel(strs[left])) left ++;
            while(left < right && !isVowel(strs[right])) right --;
            char tmp = strs[left];
            strs[left] = strs[right];
            strs[right] = tmp;
            left ++ ; right --;
        }
        return new String(strs);
    }

    boolean isVowel(char c){
        if(c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' || c == 'o' || c == 'O' || c =='u' || c == 'U')
            return true;
        return false;
    }

    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, res = 0, currArea;
        while(left < right){
            currArea = Math.min(height[left], height[right]) * (right - left);
            if(currArea > res) res = currArea;
            if(height[left] < height[right]) left ++;
            else{
                right --;
            }
        }

        return res;
    }

    // 删除排序数组中的重复项
    public int removeDuplicates2(int[] nums) {
        if(nums == null) return 0;
        if(nums.length == 1) return 1;
        int k = 0, slow = 0, fast = 1;
        while(fast < nums.length){
            while(fast < nums.length && nums[slow] == nums[fast]) fast ++;
            nums[k ++] = nums[slow];
            slow = fast;
        }
        return k;
    }

    // 买卖股票的最佳时机 II
    public int maxProfit(int[] prices) {
        int buy = 0, sell = 1, profit = 0;
        while(sell < prices.length){
            while(sell < prices.length && prices[sell] >= prices[sell - 1]) sell ++;
            if(buy != sell - 1){
                profit += prices[sell - 1] - prices[buy];
                buy = sell;
            }
            while(sell < prices.length && prices[sell] < prices[sell - 1]) sell ++;
            buy = sell - 1;
        }
        return profit;
    }

    public int maxProfit_dp(int[] prices) {
        int[] dp = new int[prices.length];
        for (int i = 1; i < prices.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 1] + prices[i] - prices[i - 1]);
        }
        return dp[prices.length - 1];
    }


    public void rotate(int[] nums, int k) {
        if(nums == null || nums.length == 0 || nums.length == 1) return;
        while(k > nums.length){
            k %= nums.length;
        }
        int lleft = 0, lright = nums.length - k - 1, rleft = nums.length - k, rright = nums.length - 1;
        rotateByIndex(nums, lleft, lright);
        rotateByIndex(nums, rleft, rright);
        rotateByIndex(nums, 0, nums.length - 1);

    }

    private void rotateByIndex(int[]nums,int left,int right){
        while(left < right){
            int temp = nums[left];
            nums[left++] = nums[right];
            nums[right--] = temp;
        }
    }


    public int singleNumber(int[] nums) {
        int res = 0;
        for(int i : nums){
            res ^= i;
        }
        return res;
    }


    // 加一
    public int[] plusOne(int[] digits) {
        int pos = digits.length - 1;
        while(pos >= 0){
            if(digits[pos] != 9){
                digits[pos] ++ ;
                break;
            }else{
                digits[pos --] = 0;
            }
        }
        if(pos == -1){
            int[] temp = digits;
            digits = new int[digits.length + 1];
            digits[0] = 1;
            for (int i = 1; i <= temp.length; i++) {
                digits[i] = temp[i-1];
            }
        }
        return digits;
    }


    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(target - nums[i])){
                int[] res = new int[]{map.get(target - nums[i]), i};
                return res;
            }else{
                map.put(nums[i], i);
            }
        }
        return new int[0];
    }

    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < board.length; i += 3) {
            for (int j = 0; j < board[0].length; j += 3) {
                // 方阵检查
                set.clear();
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
                        if(board[k][l] != '.' && set.contains(board[k][l])){
                            return false;
                        }else{
                            set.add(board[k][l]);
                        }
                    }
                }
            }
        }
        // 行检查

        for (int l = 0; l < 9; l++) {
            set.clear();
            for (int k = 0; k < 9; k++) {
                if(board[l][k] != '.' && set.contains(board[l][k])){
                    return false;
                }else{
                    set.add(board[l][k]);
                }
            }
        }

        // 列检查
        for (int l = 0; l < 9; l++) {
            set.clear();
            for (int k = 0; k < board.length; k++) {
                if (board[k][l] != '.' && set.contains(board[k][l])) {
                    return false;
                } else {
                    set.add(board[k][l]);
                }
            }
        }
        return true;
    }

    public boolean isValidSudoku_2(char[][] board) {
        int len = board.length;
        int[][] col = new int[len][len];
        int[][] row = new int[len][len];
        int[][] cell = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if(board[i][j] == '.') continue;
                int num = board[i][j] - '0' - 1;
                int k = i / 3 * 3 + j / 3;
                if(row[i][num] != 0 || col[j][num] != 0 || cell[k][num] != 0)
                    return false;
                row[i][num] = col[j][num] = cell[k][num] = 1;
            }
        }
        return true;
    }

    public boolean isValidSudoku_3(char[][] board) {
        int len = board.length;
        int[] col = new int[len];
        int[] row = new int[len];
        int[] cell = new int[len];

        int shift = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if(board[i][j] == '.') continue;
                shift = 1 << board[i][j] - '0';
                int k = i / 3 * 3 + j / 3;
                if((shift & col[i]) != 0 || (shift & row[j]) != 0 || (shift & cell[k]) != 0)
                    return false;
                col[i] |= shift;
                row[j] |= shift;
                cell[k] |= shift;
            }
        }
        return true;
    }

    public void rotate(int[][] matrix) {
        int high = matrix.length, wid = matrix[0].length;
        for (int i = 0; i < high; i++) {
            for (int j = i; j < wid; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int i = 0; i < high; i++) {
            for (int j = 0; j < wid/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][wid - j - 1];
                matrix[i][wid - j - 1] = temp;
            }
        }
    }



    public static void main(String[] args) {
//        String s = "0P";
        int[] prices = new int[]{1,2,3,4,5,6,7};
        new ArrayLearning().rotate(prices, 3);
        System.out.println(Arrays.toString(prices));
    }

}

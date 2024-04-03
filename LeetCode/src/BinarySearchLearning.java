import jdk.jfr.Unsigned;

import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ClassName: BinarySearchLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/19 15:27
 * @Version 1.0
 */
public class BinarySearchLearning {

    // x 的平方根
    public int mySqrt(int x) {
        long left = 0, right = x;
        while(left <= right){
            long mid = (left + right) / 2;
            long less = mid * mid, more = (mid + 1) * (mid + 1);
            if(less <= x && more > x) return (int)mid;
            else if(x >= more) left = mid + 1;
            else  right = mid - 1;
        }
        return -1;
    }


/*    public int guessNumber(int n) {
        long left = 0, right = n;
        while(true){
            long mid = (left + right) / 2;
            int pick = guess(mid);
            if(pick == 0) return mid;
            else if(pick == -1) right = mid - 1;
            else left = mid + 1;
        }

    }*/


    public int search(int[] nums, int target) {
        if(nums == null) return -1;
        if(nums.length == 1){
            return nums[0] == target? 0:-1;
        }
        int left = 0, right = nums.length - 1;
        if(nums[0] > nums[nums.length - 1]) {
            while (left != right - 1) {
                int mid = (left + right) / 2;
                if (nums[mid] > nums[left]) left = mid;
                if (nums[mid] < nums[right]) right = mid;
            }
            if(target >= nums[0]){
                right = left;
                left = 0;
            }else{
                left = right;
                right = nums.length - 1;
            }
        }
        while(left <= right){
            int mid = (left + right) / 2;
            if(nums[mid] == target) return mid;
            else if(nums[mid] > target) right = mid - 1;
            else left = mid + 1;
        }
        return -1;
    }

/*    public int firstBadVersion(int n) {
        int left = 0, right = n;
        while(left < right){
            int mid = left + (right - left)/2;
            boolean bad = isBadVersion(mid);
            if(bad) right = mid;
            else left = mid + 1;
        }

        return left;
    }*/


/*    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while(left < right){
            int mid = left + (right - left)/2;
            if(nums[mid] > nums[mid + 1]) right = mid;
            else left = mid + 1;
        }
        return left;
    }*/


    public int[] searchRange(int[] nums, int target) {
        if(nums.length == 0 || target < nums[0] || target > nums[nums.length-1]) return new int[]{-1,-1};
        int left = 0, right = nums.length;
        while(left < right){
            int mid = left + (right - left)/ 2;
            if(nums[mid] == target) {
                left = mid;
                right = mid;
            }else if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        if(nums[left] != target) return new int[]{-1,-1};
        right = left;
        while(left >=0 && nums[left] == target) left --;
        while(right <= nums.length - 1 && nums[right] == target) right++;
        return new int[]{left+1,right-1};
    }


    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        if(arr.length == 1) return new ArrayList<>(Arrays.asList(arr[0]));
        int l = 0, r = arr.length - 1;
        // 找到x的位置，如果有x则l和r都指向x，否则l在x位置的左边，r在x位置的右边
        while(l != r - 1){
            int mid = l + (r - l) / 2;
            if(arr[mid] == x){
                l = mid;
                r = mid;
                break;
            }else if(arr[mid] > x){
                r = mid;
            }else{
                l = mid;
            }
        }
        // 让l和r指向不同的数分别进行判断
        if(r == l) r ++;
        for (int i = 0; i < k; i++) {
            if(l < 0){
                r++; continue;
            }
            if(r > arr.length - 1){
                l--; continue;
            }
            int leftDiff = Math.abs(arr[l] - x);
            int rightDiff = Math.abs(arr[r] - x);
            if(leftDiff <= rightDiff){
                l--;
            }else{
                r++;
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        // return IntStream.rangeClosed(l+1,r-1).mapToObj(idx->arr[idx]).collect(Collectors.toList());
        for (int i = l + 1; i < r; i++) {
            list.add(arr[i]);
        }
        return list;
    }


    public int findPeakElement(int[] nums) {
        if(nums.length == 1) return 0;
        int l = 0, r = nums.length - 1;
        while(l < r){
            int mid = l + (r - l)/ 2;
            if(nums[mid] > nums[mid + 1]){
                r = mid;
            }else{
                l = mid + 1;
            }
        }
        return l;
    }


    public double myPow(double x, int n) {
        if(n == 0) return 1;
        double res = x;
        int round = n >= 0? n - 1:-n - 1;
        int k = 1;
        while(round > 0){
            round -= k;
            if(round <= 0) break;
            res *= res;
            k *= 2;
        }
        round += k;
        for (int i = 0; i < round; i++) {
            res *= x;
        }

        if(n < 0) res = 1.0/res;
        return res;
    }


    public double myPow_optimized(double x, int n){
        // long N = n;
        return n >= 0 ? quickMul(x, n): 1.0 / quickMul(x, -n);
    }

    public double quickMul(double x, long N){
        if(N == 0) return 1;
        double y = quickMul(x, N / 2);
        return  N % 2 == 0 ? y * y : y * y * x;
    }
    
    public double quickMul_optimized(double x, long N){
        double ans = 1.0;
        double x_contribute = x;
        while( N > 0){
            if(N % 2 == 1){
                ans *= x_contribute;
            }
            x_contribute *= x_contribute;
            N /= 2;
        }
        return ans;
    }
    



    public boolean isPerfectSquare(int num) {
        int left = 0, right = num;
        while(left < right){
            int mid = left + (right - left) / 2;
            long y = (long)mid * mid;
            if(y == num) return true;
            else if(y > num) right = mid;
            else left = mid + 1;
        }
        return left * left == num? true : false;
    }


    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0, right = letters.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (letters[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return right == letters.length ? letters[0] : letters[right];
    }


    public int findMin(int[] nums) {
        if(nums[0] < nums[nums.length-1]) return nums[0];
        int left = 0, right = nums.length-1;
        while(left + 1 < right){
            while(left < right && nums[left] == nums[right]){
                left++;
            }
            if(left == right || nums[left] < nums[right]) return nums[left];
            int mid = left + (right - left)/2;
            if(nums[mid] >= nums[left]) left = mid;
            else right = mid;
        }
        return nums[right];
    }


    public int findDuplicate(int[] nums) {
        // 用left和right查找要找的重复值，初始范围为 1~n
        int left = 1, right = nums.length - 1;
        while(left < right){
            int cnt = 0, mid = left + (right - left) / 2;
            for (int i = 0; i < nums.length; i++) {
                if(nums[i] <= mid) cnt ++;
            }
            if(cnt <= mid){
                left = mid + 1;
            }else if(cnt > mid){
                right = mid;
            }
        }

        return right;
    }


    public int findDuplicate_optimized(int[] nums) {
        int slow = 0, fast = 0;
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while(slow != fast);
        fast = 0;
        while(fast != slow){
            slow = nums[slow];
            fast = nums[fast];
        }
        return fast;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        // 奇数则中间，偶数则中右
        int left = (n + m + 1)/2;
        // 奇数则中间，偶数则中左
        int right = (n + m + 2)/2;
        // 奇数则算出两个一样的再除二，偶数则算出中左和中右取平均
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1,left) + getKth(nums1, 0, n - 1, nums2, 0 , m - 1, right)) * 0.5;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        // 保证nums1的长度小一些，如果不满足则翻转计算
        if(len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        // 如果nums1空了，则nums2从start开始的第k个即为中值
        if(len1 == 0) return nums2[start2 + k - 1];
        // 如果k已经递减到1了，则返回nums1和nums2最左边中更小的那个即为中值
        if(k == 1) return Math.min(nums1[start1], nums2[start2]);

        // i和j指向含start开始的第k/2个元素，不足则指向最后一个元素
        int i = start1 + Math.min(len1, k/2) - 1;
        int j = start2 + Math.min(len2, k/2) - 1;

        // 从单边截掉更小的那k/2个一定不会把第k小的给截掉
        // 注意：不能一次截掉k-1个，因为有可能两个nums的最小值是交替来的，截一边可能会将答案截去
        if(nums1[i] > nums2[j]){
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        }else{
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }


    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int l = 0, r = (int) 1e6;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (check(nums, mid) >= k) r = mid;
            else l = mid + 1;
        }
        return r;
    }

    int check(int[] nums, int x) {
        int n = nums.length, ans = 0;
        for (int i = 0, j = 1; i < n; i++) {
            while (j < n && nums[j] - nums[i] <= x) j++;
            ans += j - i - 1;
        }

        return ans;
    }




    public static void main(String[] args) {
        new BinarySearchLearning().findDuplicate(new int[]{1,3,4,2,2});
    }




}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: StringLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/12 22:04
 * @Version 1.0
 */

class Solution {
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

}
public class StringLearning {
    public static void main(String[] args) {
        int[][] nums = {{1,3},{2,6},{8,10},{15,18}};
        Solution solution = new Solution();
        int[][] res = solution.merge(nums);
        System.out.println(Arrays.deepToString(res));
    }



}

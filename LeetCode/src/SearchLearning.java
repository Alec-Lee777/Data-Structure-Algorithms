import com.sun.source.tree.NewArrayTree;

import java.util.*;
import java.util.LinkedList;

/**
 * ClassName: SearchLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/17 20:25
 * @Version 1.0
 */
public class SearchLearning {

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            set2.add(nums2[i]);
        }
        for (Integer num : set1){
            if(set2.contains(num)){
                list.add(num);
            }
        }
        int i = 0;
        int[] res = new int[list.size()];
        for(Integer num : list){
            res[i++] = num;
        }

        return res;
    }

    // 交集
    public int[] intersection_optimize(int[] nums1, int[] nums2) {
        int[] dp = new int[1001];
        int[] res = new int[1001];
        for(int num : nums1){
            dp[num] = 1;
        }
        int i = 0;
        for(int num : nums2){
            if(dp[num] == 1){
                res[i++] = num;
            }
        }
        return Arrays.copyOf(res,i);
    }


    // 快乐数
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();

        while(true){
            int sum = 0;
            while(n != 0){
                sum += (n%10)*(n%10);
                n /= 10;
            }
            if(sum == 1) return true;
            if(set.contains(sum)){
                return false;
            }else{
                set.add(sum);
            }
            n = sum;

        }

    }


    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i : nums){
            if(!set.add(i)) return true;
        }
        return false;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        int[] dp1 = new int[1001];
        int[] dp2 = new int[1001];
        int[] res = new int[1001];
        for(int num : nums1){
            dp1[num] ++;
        }
        for(int num : nums2){
            dp2[num] ++;
        }
        int i = 0;
        for(int j = 0; j < 1001; j++){
            if(dp1[j] != 0 && dp2[j] != 0){
                int n = Math.min(dp1[j], dp2[j]);
                while(n-- > 0){
                    res[i++] = j;
                }
            }
        }
        return Arrays.copyOf(res,i);

    }

    public int[] intersect_optimize(int[] nums1, int[] nums2) {
        int[] dp1 = new int[1001];
        int[] res = new int[1001];
        for(int num : nums1){
            dp1[num] ++;
        }
        int i = 0;
        for(int num : nums2){
            if(dp1[num] > 0){
                res[i++] = num;
                dp1[num]--;
            }
        }
        return Arrays.copyOf(res,i);

    }

    public boolean isAnagram(String s, String t) {
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        int[] countS = new int[26];
        for(char c : sChar){
            countS[(c-'a')]++;
        }
        for(char c : tChar){
            countS[(c-'a')]--;
        }
        for(int i : countS){
            if(i != 0) return false;
        }

        return true;
    }


    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()) return false;
        Map<Character, Character> map = new HashMap<>();
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        for(int i = 0; i < sChar.length ; i++){
            if(!map.containsKey(tChar[i]) && !map.containsValue(sChar[i]))
                map.put(tChar[i], sChar[i]);
        }
        for (int i = 0; i < tChar.length; i++) {
            if(!map.containsKey(tChar[i]) || sChar[i] != map.get(tChar[i])) return false;
        }
        return true;
    }


    public String frequencySort(String s) {
        char[] strs = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for(char c : strs){
            if(map.containsKey(c)){
                map.put(c, map.get(c) + 1);
            }else{
                map.put(c, 1);
            }
        }
        List<Map.Entry<Character, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry : entries){
            for (int j = 0; j < (int)entry.getValue(); j++) {
                sb.append(entry.getKey());
            }
        }
        return sb.toString();
    }

    public String frequencySort_optimized(String s) {
        char[] strs = s.toCharArray();
        int[] cnt = new int[128];
        for(char c : strs){
            cnt[c] ++;
        }
        int i = 0;
        while(i < s.length()){
            int max = 0;
            for (int j = 1; j < cnt.length; j++) {
                if(cnt[max] < cnt[j]) max = j;
            }
            while(cnt[max]-- > 0){
                strs[i++] = (char)max;
            }
        }
        return String.copyValueOf(strs);
    }


    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> index = new HashMap<>();
        Map<Integer, Integer> indexForSecond = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(index.containsKey(nums[i])){
                indexForSecond.put(nums[i], i);
            }else {
                index.put(nums[i], i);
            }
        }
        Arrays.sort(nums);
        int left = 0, right = nums.length-1;
        while(left < right){
            int curr = nums[left] + nums[right];
            if(curr == target){
                if(nums[left] != nums[right]) {
                    return new int[]{index.get(nums[left]), index.get(nums[right])};
                }else{
                    return new int[]{index.get(nums[left]), indexForSecond.get(nums[right])};
                }
            }else if(curr > target){
                right --;
            }else{
                left ++;
            }
        }
        return new int[]{-1,-1};
    }

    public int[] twoSum_optimized(int[] nums, int target) {
        Map<Integer,Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(hashtable.containsKey(target - nums[i])){
                return new int[]{hashtable.get(target-nums[i]), i};
            }else{
                hashtable.put(nums[i], i);
            }
        }
        return new int[0];
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int k = 0; k < nums.length; k++) {
            for (int i = k + 1; i < nums.length; i++) {
                int neg = -(nums[i] + nums[k]);
                ArrayList<Integer> newRes = getOrder(nums[k], nums[i], neg);
                if(map.containsKey(neg) && map.get(neg) != i && map.get(neg) != k && i != k && !res.contains(newRes)) {
                    res.add(newRes);
                }
                map.put(nums[i], i);
            }
        }

        return res;
    }

    int getMax(int x, int y, int z){
        if(x >= y && x >= z) return x;
        if(y >= x && y >= z) return y;
        return z;
    }
    int getMin(int x, int y, int z){
        if(x <= y && x <= z) return x;
        if(y <= x && y <= z) return y;
        return z;
    }
    ArrayList<Integer> getOrder(int x, int y, int z){
        int max = getMax(x,y,z);
        int min = getMin(x,y,z);
        int mid;
        if(x >= y && x <= z || x <= y && x>= z) mid = x;
        else if(y >= x && y <= z || y <= x && y >= z) mid = y;
        else mid = z;
        return new ArrayList<Integer>(Arrays.asList(min, mid, max));
    }


    public List<List<Integer>> threeSum_optimized(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int first = 0; first < n; first++) {
            if(nums[first] > 0) return ans;
            if(first > 0 && nums[first] == nums[first - 1]) continue;
            int second = first + 1;
            int third = n - 1;
            while(second < third){
                int sum = nums[first] + nums[second] + nums[third];
                if(sum < 0){
                    second++;
                }else if(sum > 0){
                    third--;
                }else{
                    ans.add(new ArrayList<>(Arrays.asList(nums[first], nums[second], nums[third])));
                    while (third > second && nums[third - 1] == nums[third]){third --;}
                    while (third > second && nums[second + 1] == nums[second]){second ++;}
                    third --;
                    second ++;
                }
            }
        }
        return ans;
    }

    // 四位相加
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int first = 0; first < nums.length-3; first++) {
            if(nums[first] > 0 && nums[first] > target) return res;
            if((long)nums[first] + nums[first+1] + nums[first+2] + nums[first+3] > target) return res;
            if(first > 0 && nums[first] == nums[first - 1]) continue;
            for (int second = first + 1; second < nums.length; second++) {
                if(second > first + 1 && nums[second] == nums[second - 1]) continue;
                int left = second + 1, right = nums.length - 1;
                while(left < right){
                    long sum = (long)nums[first] + nums[second] + nums[left] + nums[right];
                    if(sum < target){
                        left++;
                    } else if (sum > target) {
                        right--;
                    }else{
                        res.add(Arrays.asList(nums[first], nums[second], nums[left], nums[right]));
                        while(left < right && nums[left] == nums[left+1]) left++;
                        while(left < right && nums[right] == nums[right-1]) right--;
                        left ++;
                        right --;
                    }
                }
            }
        }

        return res;
    }

    public int fourSumCount_optimized(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for(int n : nums1){
            for(int m : nums2){
                map.merge(n+m, 1, Integer::sum);
            }
        }
        for(int n : nums3){
            for(int m : nums4){
                res += map.getOrDefault(-n-m, 0);
            }
        }
        return res;

    }


    // 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        for(String s : strs){
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String keyS = new String(chars);
            if(!map.containsKey(keyS)){
                map.put(keyS,new ArrayList<String>(List.of(s)));
            }else {
                map.get(keyS).add(s);
            }
        }
        List<List<String>> res = new ArrayList<>();
        for(ArrayList<String> arrayList : map.values()){
            res.add(arrayList);
        }
        return res;
    }



    public List<List<String>> groupAnagrams_optimized(String[] strs) {
        HashMap<ArrayKey, ArrayList<String>> map = new HashMap<ArrayKey, ArrayList<String>>();
        for(String s : strs){
            ArrayKey arrayKey = new ArrayKey(s);
            ArrayList<String> list = map.computeIfAbsent(arrayKey, val -> new ArrayList<>());
            list.add(s);
        }

        return new ArrayList<>(map.values());
    }

    class ArrayKey{
        int[] key = new int[26];

        public ArrayKey(String s){
            for (int i = 0; i < s.length(); i++) {
                this.key[s.charAt(i)-'a'] ++;
            }
        }

        @Override
        public int hashCode(){
            return Arrays.hashCode(key);
        }

        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || o.getClass() != this.getClass()) return false;
            ArrayKey arrayKey = (ArrayKey) o;
            return Arrays.equals(arrayKey.key, this.key);
        }

    }

    // 回旋镖的数量
    public int numberOfBoomerangs(int[][] points){
        int ans = 0;
        // 用来存储
        Map<Integer, Integer> map = new HashMap<>();
        for(int[] center : points){
            map.clear();
            for(int[] side : points){
                int distance = (int) (Math.pow(side[0] - center[0],2) + Math.pow(side[1] - center[1], 2));
                map.merge(distance, 1, (oldVal, newVal) -> oldVal + newVal);
            }
            for(int i : map.values()){
                ans += i*(i-1);
            }
        }
        return ans;
    }


    // 直线上最多的点数
    // 这个解法不行，因为斜率是浮点数，会有精度损失，没法顺利加入map
    public int maxPoints(int[][] points) {
        // key:斜率 value:个数
        Map<Double, Integer> map = new HashMap<>();
        int res = 0, inf = 0;
        for(int[] start : points){
            map.clear();
            inf = 0;
            for(int[] end : points){
                if(end[0] == start[0]){
                    if(end[1] != start[1]) inf ++;
                }else {
                    Double k = (double) ((end[1] - start[1]) / (end[0] - start[0]));
                    map.merge(k, 1, Integer::sum);
                }
            }
            for(Integer num : map.values()){
                res = Math.max(num, res);
            }
            res = Math.max(inf, res);
        }
        return res;
    }

    //
    public int maxPoints_optimized(int[][] points) {
        int n = points.length;
        if( n <= 2){
            return n;
        }
        int ret = 0;
        for (int i = 0; i < n; i++) {
            // 如果一条直线经过了n/2个点，那么其他直线最多也就只能经过n/2个点，否则两条直线重合了
            // 只考虑i节点后面的点进行连线，如果ret已经大于n-i，那么不会有更多点的直线
            if(ret >= n - i || ret > n/2){
                break;
            }
            // 从i节点往后找最大节点的直线
            Map<SetKey, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int x = points[i][0] - points[j][0]; // x1-x0
                int y = points[i][1] - points[j][1]; // y1-y0
                SetKey key = new SetKey(x,y);
                map.merge(key, 1, Integer::sum);
            }
            // 找到map中最大的value即为节点最多的直线
            int maxn = 0;
            for (Map.Entry<SetKey, Integer> entry: map.entrySet()) {
                int num = entry.getValue();
                maxn = Math.max(maxn, num + 1);
            }
            ret = Math.max(ret,maxn);
        }
        return ret;
    }

    class SetKey{
        int[] position = new int[2];
        public SetKey(int x, int y) {
            if (x == 0) {
                y = 1;
            } else if (y == 0) {
                x = 1;
            } else {
                if (y < 0) {
                    x = -x;
                    y = -y;
                }
                int gcdXY = gcd(Math.abs(x), Math.abs(y));
                x /= gcdXY;
                y /= gcdXY;
            }
            position[0] = x;
            position[1] = y;
        }

        @Override
        public int hashCode(){
            return Arrays.hashCode(this.position);
        }
        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || this.getClass() != o.getClass()) return false;
            SetKey setKey = (SetKey) o;
            return Arrays.equals(this.position, setKey.position);
        }

        private int gcd (int a, int b){
            return b != 0 ? gcd(b, a%b) : a;
        }

    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(set.contains(nums[i])) return true;
            set.add(nums[i]);
            if(i >= k){
                set.remove(nums[i-k]);
            }
        }
        return false;
    }


    // 存在重复元素3
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        TreeSet<Long> ts = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            Long curr = (long) nums[i];
            Long l = ts.floor(curr);
            Long r = ts.ceiling(curr);
            if(l != null && curr - l <= valueDiff) return true;
            if(r != null && r - curr <= valueDiff) return true;
            ts.add(curr);
            if(i >= indexDiff) ts.remove((long)nums[i - indexDiff]);
        }
        return false;
    }


    long size;
    public boolean containsNearbyAlmostDuplicate_optimized(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        Map<Long,Long> map = new HashMap<>();
        size = valueDiff + 1L;
        for (int i = 0; i < n; i++) {
            // 拿到当前的数字
            long curr = (long)nums[i];
            long idx = getIdx(curr);
            // 目标桶已存在，桶不为空，则说明有 u-t~u+t 范围的数字存在
            if(map.containsKey(idx)) return true;
            // 检查相邻的桶
            long l = idx - 1, r = idx + 1;
            if(map.containsKey(l) && curr - map.get(l) <= valueDiff) return true;
            if(map.containsKey(r) && map.get(r) - curr <= valueDiff) return true;
            // 建立目标桶桶
            map.put(idx, curr);
            if(i >= indexDiff) {
                map.remove(getIdx((long)nums[i - indexDiff]));
            }
        }
        return false;
    }

    private long getIdx(Long curr) {
        return curr >= 0 ? curr / size : (curr + 1) / size - 1;
    }

    // 自己再写一遍
    public boolean containsNearbyAlmostDuplicate_byMyself(int[] nums, int indexDiff, int valueDiff) {
        long size = valueDiff + 1L;
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            long curr = (long)nums[i];
            long key = getBucket(curr, size);
            if(map.containsKey(key)) return true;
            // 检查相邻桶
            if(map.containsKey(key-1) && curr - map.get(key - 1) <= valueDiff) return true;
            if(map.containsKey(key+1) && map.get(key + 1) - curr <= valueDiff) return true;
            map.put(key, curr);
            if(i >= indexDiff){
                map.remove(getBucket(nums[i - indexDiff], size));
            }
        }
        return false;
    }

    private long getBucket(long curr, long size){
        return curr >= 0 ? curr / size : (curr + 1) / size - 1;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1000000000,1000000000,1000000000,1000000000};
        int target = -294967296;
        new SearchLearning().fourSum(nums, target);


/*        String s = "aaaabbbcdd";
        System.out.println(new SearchLearning().frequencySort(s));*/


/*        int[] nums1 = new int[]{4,9,5};
        int[] nums2 = new int[]{9,4,9,8,4};
        System.out.println(new SearchLearning().intersection(nums1, nums2));*/
    }

}

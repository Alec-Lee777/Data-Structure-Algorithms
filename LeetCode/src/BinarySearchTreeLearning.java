import com.sun.jdi.LongValue;
import com.sun.source.tree.Tree;

import java.time.temporal.ValueRange;
import java.util.*;
import java.util.LinkedList;

/**
 * ClassName: BinarySearchTreeLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/22 10:28
 * @Version 1.0
 */
public class BinarySearchTreeLearning {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 二叉搜索树迭代器
    class BSTIterator {
        Stack<TreeNode> stack;
        TreeNode curr = null;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            curr = root;
            if (curr == null) {
                curr = new TreeNode(-1);
                return;
            }
            while (curr.left != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr.left = new TreeNode(-1);
            stack.push(curr);
            curr = curr.left;
        }

        public int next() {
            // 如果有右孩子，则返回右孩子最左
            if (curr.right != null) {
                curr = curr.right;
                while (curr.left != null) {
                    stack.push(curr);
                    curr = curr.left;
                }
                return curr.val;
            } else {
                // 否则弹个栈
                if (stack.isEmpty()) return -1;
                curr = stack.pop();
                return curr.val;
            }
        }

        public boolean hasNext() {
            if (curr.right != null || !stack.isEmpty()) return true;
            return false;
        }
    }


    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode res = root;
        TreeNode pre = root;
        while (root != null) {
            pre = root;
            if (root.val > val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        if (pre.val > val) {
            pre.left = new TreeNode(val);
        } else {
            pre.right = new TreeNode(val);
        }
        return res;
    }


    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode p = root, pre = null;
        while (p != null && p.val != key) {
            pre = p;
            if (p.val > key) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        if (p == null) return root;
        byte dir = 0;
        if (pre != null && pre.left != null && pre.left.val == key) {
            dir = 0;
        } else if (pre != null) {
            dir = 1;
        }
        if (p.left == null && p.right == null) {
            if (pre == null) return null;
            if (dir == 0) pre.left = null;
            else pre.right = null;
        } else if (p.left == null) {
            if (pre == null) return p.right;
            if (dir == 0) pre.left = p.right;
            else pre.right = p.right;
        } else if (p.right == null) {
            if (pre == null) return p.left;
            if (dir == 0) pre.left = p.left;
            else pre.right = p.left;
        } else {
            // 用 pre 找中序后继
            pre = p.right;
            if (pre.left == null) {
                p.val = pre.val;
                p.right = pre.right;
                return root;
            }
            while (pre.left != null) {
                pre = pre.left;
            }
            p.val = pre.val;
            deleteNode(p.right, pre.val);
        }
        return root;
    }


    class KthLargest{
        PriorityQueue<Integer> pq;
        int k;

        public KthLargest(int k , int[] nums){
            this.k = k;
            pq = new PriorityQueue<>();
            for(int x : nums){
                add(x);
            }
        }

        public int add(int val) {
            pq.offer(val);
            if(pq.size() > k){
                pq.poll();
            }
            return pq.peek();
        }
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(p.val < root.val && q.val < root.val){
            return lowestCommonAncestor(root.left, p, q);
        }else if(p.val > root.val && q.val > root.val){
            return lowestCommonAncestor(root.right, p, q);
        }else{
            return root;
        }
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        Map<Long, Long> map = new HashMap<>();
        long valRange = valueDiff + 1L;
        for (int i = 0; i < nums.length; i++) {
            if(i > indexDiff){
                map.remove((long)nums[i - indexDiff - 1]/valRange);
            }
            long currKey;
            if(nums[i] >= 0) {
                currKey = nums[i] / valRange;
            }else{
                currKey = (nums[i] - valueDiff)/ valRange;
            }
            if(map.containsKey(currKey)) return true;
            if(map.containsKey(currKey - 1) && Math.abs(nums[i] - map.get(currKey - 1)) <= valueDiff) return true;
            if(map.containsKey(currKey + 1) && Math.abs(map.get(currKey + 1) - nums[i]) <= valueDiff) return true;
            map.put(currKey, (long)nums[i]);
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicate_BRTree(int[] nums, int k, int t) {
        if(nums.length <= 1){
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            long minVal = (long)nums[i] - t;
            long maxVal = (long)nums[i] + t;
            if(!set.isEmpty()){
                Long l = set.ceiling(minVal);
                if(l != null && l <= maxVal) return true;
            }
            set.add((long)nums[i]);
            if(set.size() > k){
                set.remove((long)nums[i - k]);
            }
        }
        return false;
    }


    // 平衡二叉树
    public boolean isBalanced(TreeNode root) {
        return balanceJudge(root) != -1;
    }

    private int balanceJudge(TreeNode root) {
        if(root == null) return 0;
        int lh = balanceJudge(root.left);
        int rh = balanceJudge(root.right);
        if(Math.abs(lh - rh) <= 1 && lh != -1 && rh != -1) return Math.max(lh, rh) + 1;
        else return -1;
    }


    public TreeNode sortedArrayToBST(int[] nums) {
        return BuildBST(nums, 0, nums.length - 1);
    }

    private TreeNode BuildBST(int[] nums, int left, int right) {
        if(left > right) return null;
        if(left == right) return new TreeNode(nums[left]);
        int mid = (left + right)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = BuildBST(nums, left, mid - 1);
        root.right = BuildBST(nums, mid + 1, right);
        return root;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,5,9,1,5,9};
        System.out.println(new BinarySearchTreeLearning().containsNearbyAlmostDuplicate(nums, 2, 3));
    }


}

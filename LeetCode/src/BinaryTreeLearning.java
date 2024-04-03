import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.LinkedList;

/**
 * ClassName: BinaryTreeLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/21 14:42
 * @Version 1.0
 */
public class BinaryTreeLearning {

    public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }

    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        TreeNode p = root;
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode curr = stack.peek();
            if((curr.left == null && curr.right == null) || (curr.right == null && p == curr.left) || p == curr.right){
                res.add(curr.val);
                stack.pop();
                p = curr;
            }else{
                if(curr.right != null) stack.push(curr.right);
                if(curr.left != null) stack.push(curr.left);
            }
        }
        return res;
    }

    public List<Integer> postorderTraversal_optimized(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack =  new Stack<>();
        if(root == null) return res;
        stack.push(root);
        while(!stack.isEmpty()){
            root = stack.pop();
            if(root != null){
                stack.push(root);
                stack.push(null);
                if(root.right != null) stack.push(root.right);
                if(root.left != null) stack.push(root.left);
            }else{
                res.add(stack.pop().val);
            }
        }
        return res;
    }


    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        TreeNode l = root.left, r = root.right;
        return symmetricLeaf(l, r);
    }

    private boolean symmetricLeaf(TreeNode l, TreeNode r){
        if(l == null && r == null) return true;
        if(l == null || r == null) return false;
        if(l.val != r.val) return false;
        return symmetricLeaf(l.left, r.right) && symmetricLeaf(l.right, r.left);
    }


    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null) return false;
        if(root.left == null && root.right == null && root.val == targetSum) return true;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


    Map<Integer,Integer> map = new HashMap<>();
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return InPostTreeBuilder(inorder, 0,inorder.length - 1,postorder,0,postorder.length - 1);
    }

    private TreeNode InPostTreeBuilder(int[] inorder,int inl, int inr, int[] postorder, int postl, int postr){
        if(inl == inr)
            return new TreeNode(inorder[inl], null, null);
        TreeNode root = new TreeNode();
        root.val = postorder[postr];
        int rootpos = map.get(root.val);
        // 左子树有(rootpos-inl)个结点
        if(rootpos-inl > 0)
            root.left = InPostTreeBuilder(inorder, inl, rootpos - 1, postorder, postl, postl + (rootpos - inl - 1));
        // 右子树有(inr-rootpos)个结点
        if(inr-rootpos > 0)
            root.right = InPostTreeBuilder(inorder, rootpos + 1, inr, postorder, postr - (inr - rootpos), postr - 1);

        return root;
    }


    public TreeNode buildTree_iteration(int[] preorder, int[] inorder) {
        // 必须写空集的退出条件，否则第一个节点入栈就会访问越界
        if(preorder == null || preorder.length == 0) return null;
        Stack<TreeNode> stack = new Stack<>();
        // 先序遍历的第一个节点肯定是根节点
        TreeNode root = new TreeNode(preorder[0]);
        TreeNode curr = root;
        // 将右子树还没处理的节点都入栈
        stack.push(root);
        int inorderIndex = 0;
        for(int i = 1; i < preorder.length; i ++){
            // 如果前序遍历到的位置还没到中序的最左下，则还可以继续向左下移动
            if(curr.val != inorder[inorderIndex]){
                curr.left = new TreeNode(preorder[i]);
                stack.push(curr);
                curr = curr.left;
            }else{
                // 如果当前位置和inorder[inorderIndex]相同，说明左下已经没节点了，需要处理右边或者向上退
                // 先inorderIndex++是因为curr就是这个节点了，现在要用栈里的和inorder后面的比较
                inorderIndex ++;
                // 将所有没有处理右子树但是也不需要处理右子树的结点退栈(表示已经处理完，因为实际也不用处理)
                while(!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]){
                    curr = stack.pop();
                    inorderIndex ++;
                }
                // 到达第一个需要处理右子树的结点，此时右子树一定是还未到达的第一个先序遍历元素
                curr.right = new TreeNode(preorder[i]);
                // curr 不用入栈直接指向右边，因为栈是给右子树没处理的结点用的，这时候的curr已经处理了右子树
                curr = curr.right;
            }
        }
        return root;
    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        if(root == null) return null;
        if(root.left == null && root.right == null){
            root.next = null;
            return root;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            Node pre = null, curr = null;
            for (int i = 0; i < size; i++) {
                curr = queue.poll();
                if(pre != null){
                    pre.next = curr;
                }
                pre = curr;
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
            pre.next = null;
        }
        return root;
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p || root == q) return root;
        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p , q);
        if(l != null && r != null) return root;
        if(l != null) return l;
        if(r != null) return r;
        return null;
    }


/*    public String serialize(TreeNode root) {
        if(root == null) return null;
        StringBuilder res = new StringBuilder();
        getInorder(root, res);
        res.deleteCharAt(res.length()-1);
        res.append(".");
        getPreorder(root, res);
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }

    private void getPreorder(TreeNode root, StringBuilder res) {
        if(root == null) return;
        res.append(root.val).append(",");
        getPreorder(root.left, res);
        getPreorder(root.right, res);
    }

    private void getInorder(TreeNode root, StringBuilder res){
        if(root == null) return;
        getInorder(root.left, res);
        res.append(root.val).append(",");
        getInorder(root.right, res);
    }

    public TreeNode deserialize(String data) {
        if(data == null || data.length() == 0) return null;
        String[] order = data.split("\\.");
        int[] inorder = Arrays.stream(order[0].split(",")).mapToInt(Integer::parseInt).toArray();
        int[] preorder = Arrays.stream(order[1].split(",")).mapToInt(Integer::parseInt).toArray();
        Map<Integer,Integer> map = new HashMap<>();
        // 错误原因：inorder[i] 有可能重复，所以先序中序无法还原
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return preOrderInorderGetTree(inorder, 0, inorder.length - 1, preorder, 0, preorder.length - 1, map);

    }

    private TreeNode preOrderInorderGetTree(int[] inorder, int inl, int inr, int[] preorder, int prel, int prer, Map<Integer,Integer> map) {
        if(inl > inr || prel > prer) return null;
        if(inl == inr) return new TreeNode(inorder[inl]);
        TreeNode root = new TreeNode(preorder[prel]);
        int rootPos = map.get(root.val);
        root.left = preOrderInorderGetTree(inorder, inl, rootPos - 1, preorder, prel + 1, prel + (rootPos - inl), map);
        root.right = preOrderInorderGetTree(inorder, rootPos + 1, inr , preorder, prel + (rootPos - inl) + 1, prer, map);
        return root;
    }*/

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        return rserialize(root, sb).toString();
    }

    private StringBuilder rserialize(TreeNode root, StringBuilder s) {
        if(root == null) {
            s.append("none,");
            return s;
        }
        s.append(root.val).append(",");
        s = rserialize(root.left, s);
        s = rserialize(root.right, s);
        return s;
    }


    // Decodes your encoded data to tree.
    int pos = 0;
    public TreeNode deserialize(String data) {
        String[] inorder = data.split(",");
        return rdeserialize(inorder);
    }

    private TreeNode rdeserialize(String[] inorder) {
        if(inorder[pos].equals("none") || pos >= inorder.length){
            pos ++;
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(inorder[pos++]));
        root.left = rdeserialize(inorder);
        root.right = rdeserialize(inorder);
        return root;
    }
/*
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializePre()
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {

    }*/


    public boolean isSymmetric1(TreeNode root) {
        TreeNode lTree = root.left, rTree = root.right;
        Stack<TreeNode> ls = new Stack<>();
        Stack<TreeNode> rs = new Stack<>();
        while(lTree != null || !ls.isEmpty() || rTree != null){
            if(lTree == null && rTree != null || lTree != null && rTree == null){
                return false;
            }else if(lTree != null && rTree != null){
                ls.add(lTree);
                lTree = lTree.left;
                rs.add(rTree);
                rTree = rTree.right;
            }else{
                lTree = ls.pop();
                rTree = rs.pop();
                if(lTree.val != rTree.val) return false;
                lTree = lTree.right;
                rTree = rTree.left;
            }
        }
        if(lTree == null && rTree == null)
            return true;
        return false;
    }


    public boolean isSymmetric2(TreeNode root) {
        if(root == null) return true;
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if(left == null && right == null) return true;
        if(left == null || right == null || left.val != right.val) return false;
        return isSymmetricHelper(left.left, right.right) && isSymmetricHelper(left.right, right.left);
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return getBST(nums, 0, nums.length - 1);
    }

    private TreeNode getBST(int[] nums, int left, int right) {
        if(left > right) return null;
        int mid = (left + right) / 2;
        TreeNode curr = new TreeNode(nums[mid]);
        curr.left = getBST(nums, left, mid - 1);
        curr.right = getBST(nums, mid + 1, right);
        return curr;
    }




    public static void main(String[] args) {
        TreeNode root = new TreeNode(4, new TreeNode(-7), new TreeNode(3));
        TreeNode curr = root.right;

/*        TreeNode root = new TreeNode(3, new TreeNode(2), new TreeNode(4));
        root.left.left = new TreeNode(3);*/


        String s = new BinaryTreeLearning().serialize(root);
        System.out.println(s);
        root = new BinaryTreeLearning().deserialize(s);
        System.out.println(s);

    }


}

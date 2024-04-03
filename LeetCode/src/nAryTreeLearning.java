import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * ClassName: nAryTreeLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/23 15:51
 * @Version 1.0
 */
public class nAryTreeLearning {

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public List<Integer> preorder(Node root) {
        Stack<Node> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        stack.push(root);
        Node curr;
        while(!stack.isEmpty()){
            curr = stack.pop();
            res.add(curr.val);
            for (int i = curr.children.size() - 1; i >= 0 ; i--) {
                stack.push(curr.children.get(i));
            }
        }
        return res;
    }


    public List<Integer> postorder(Node root) {
        Stack<Node> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        stack.push(root);
        Node curr;
        while(!stack.isEmpty()){
            curr = stack.pop();
            if(curr == null){
                curr = stack.pop();
                res.add(curr.val);
            }else{
                stack.push(curr);
                stack.push(null);
                for (int i = curr.children.size() - 1; i >= 0 ; i--) {
                    stack.push(curr.children.get(i));
                }
            }
        }
        return res;
    }
    public List<Integer> postorder_optimized(Node root) {
        List<Integer> res = new ArrayList<>();
        postorderHelper(root, res);
        return res;
    }

    private void postorderHelper(Node root, List<Integer> res) {
        if(root == null) return;
        for (Node node : root.children) {
            postorderHelper(node, res);
        }
        res.add(root.val);
    }


    public List<List<Integer>> levelOrder(Node root) {
        Queue<Node> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.add(root);
        Node curr;
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> currList = new ArrayList<>();
            res.add(currList);
            for (int i = 0; i < size; i++) {
                curr = queue.poll();
                currList.add(curr.val);
                for(Node node : curr.children){
                    queue.add(node);
                }
            }
        }
        return res;
    }


    public int maxDepth(Node root) {
        if(root == null) return 0;
        int max = 0;
        int childDepth;
        for(Node node : root.children){
            childDepth = maxDepth(node);
            if(childDepth > max){
                max = childDepth;
            }
        }
        return max + 1;
    }

}

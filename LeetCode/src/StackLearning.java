import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.LinkedList;

/**
 * ClassName: StackLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/15 15:51
 * @Version 1.0
 */
public class StackLearning {

    class MinStack {

        ArrayList<Integer> data;
        ArrayList<Integer> min;
        int size;

        public MinStack() {
            this.data = new ArrayList<>();
            this.min = new ArrayList<>();
            size = 0;
        }

        public void push(int val) {
            this.data.add(val);
            if (size == 0 || val < min.get(min.size() - 1)) {
                this.min.add(val);
            } else {
                this.min.add(min.get(min.size() - 1));
            }
            this.size++;
        }

        public void pop() {
            this.data.remove(size - 1);
            this.min.remove(min.size() - 1);
            this.size--;
        }

        public int top() {
            return data.get(size - 1);
        }

        public int getMin() {
            return min.get(min.size() - 1);
        }
    }


    static class Solution {

        public boolean isValid(String s) {
            char[] strs = s.toCharArray();
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < strs.length; i++) {
                if (strs[i] == '{' || strs[i] == '(' || strs[i] == '[') {
                    stack.push(strs[i]);
                } else if (strs[i] == '}') {
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                } else if (strs[i] == ')') {
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                } else if (strs[i] == ']') {
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                }
            }
            return stack.isEmpty();
        }


        public int[] dailyTemperatures(int[] temperatures) {
            int[] ans = new int[temperatures.length];
            // 用来存还没找到比自己温度高的日子的下标,因为用下标能找到当天温度，但是用温度在O(1)时间内找不到下标
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(0);
            for (int i = 1; i < temperatures.length; i++) {
                int curr = stack.peek();
                while (!stack.isEmpty() && temperatures[curr] < temperatures[i]) {
                    stack.pop();
                    ans[curr] = i - curr;
                    curr = stack.isEmpty() ? 0 : stack.peek();
                }
                stack.push(i);
            }

            return ans;
        }

        public int[] dailyTemperatures_optimized(int[] temperatures) {
            int[] res = new int[temperatures.length];
            for (int i = temperatures.length - 1; i >= 0; i--) {
                int j = i + 1;
                while (j < res.length) {
                    if (temperatures[j] > temperatures[i]) {
                        res[i] = j - i;
                        break;
                    } else if (res[j] == 0) {
                        break;
                    } else {
                        j += res[j];
                    }

                }
            }
            return res;

        }

        public int evalRPN(String[] tokens) {
            Stack<Integer> number = new Stack<>();
            for (String s : tokens) {
                if (!isOperator(s)) {
                    number.add(Integer.parseInt(s));
                } else {
                    Integer right = number.pop();
                    Integer left = number.pop();
                    if (s.equals("+")) {
                        number.add(left + right);
                    } else if (s.equals("-")) {
                        number.add((left - right));
                    } else if (s.equals("*")) {
                        number.add(left * right);
                    } else if (s.equals("/")) {
                        number.add(left / right);
                    }
                }
            }
            return number.pop();
        }

        private boolean isOperator(String s) {
            return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
        }



        class Node {
            public int val;
            public List<Node> neighbors;

            public Node() {
                val = 0;
                neighbors = new ArrayList<Node>();
            }

            public Node(int _val) {
                val = _val;
                neighbors = new ArrayList<Node>();
            }

            public Node(int _val, ArrayList<Node> _neighbors) {
                val = _val;
                neighbors = _neighbors;
            }
        }

        public Node cloneGraph(Node node) {
            if (node == null) return null;
            Node res = new Node(node.val);
            Map<Integer, Node> visited = new HashMap<>();
            Stack<Node> origin = new Stack<>();
            Stack<Node> result = new Stack<>();
            origin.add(node);
            result.add(res);
            visited.put(res.val, res);
            while (!origin.isEmpty()) {
                // 取出节点及其对应的copy
                Node originNode = origin.pop();
                Node resultNode = result.pop();
                // 遍历节点
                for (Node curr : originNode.neighbors) {
                    // 如果创建了新的节点，那么将当前节点和copy加入栈中，等待下次处理这个新节点
                    if (!visited.containsKey(curr.val)) {
                        Node newNode = new Node(curr.val);
                        visited.put(curr.val, newNode);
                        resultNode.neighbors.add(newNode);
                        origin.push(curr);
                        result.add(newNode);
                    } else {
                        resultNode.neighbors.add(visited.get(curr.val));
                    }
                }
            }

            return res;
        }

        public int findTargetSumWays(int[] nums, int target) {
            int res = 0, curr = 0;
            res = findByDFS(nums, target, curr, 0);

            return res;
        }

        int findByDFS(int[] nums, int target, int curr, int deep) {
            if (deep == nums.length && curr == target) return 1;
            if (deep == nums.length) return 0;
            return findByDFS(nums, target, curr + nums[deep], deep + 1) +
                    findByDFS(nums, target, curr - nums[deep], deep + 1);
        }

        public int findTargetSumWays_dp(int[] nums, int target) {
            int sum = 0;
            for (int i : nums) sum += i;
            int gap = sum - target;
            // gap = 2 * negSum
            if (gap < 0 || (gap & 1) != 0) return 0;
            int negSum = gap / 2;
            // dp[i][j] 前i个元素之和为j的个数
            int[][] dp = new int[nums.length + 1][negSum + 1];
            dp[0][0] = 1;
            for (int i = 1; i <= nums.length; i++) {
                // 第i个数字是nums[i-1]
                int currNum = nums[i - 1];
                for (int j = 0; j <= negSum; j++) {
                    dp[i][j] = currNum <= j ? dp[i - 1][j] + dp[i - 1][j - currNum] : dp[i - 1][j];
                }
            }

            return dp[nums.length][negSum];
        }

        public int findTargetSumWays_optimized(int[] nums, int target) {
            int sum = 0;
            for (int i : nums) sum += i;
            int gap = sum - target;
            if (gap < 0 || (gap & 1) != 0) return 0;
            int negSum = gap / 2;
            // dp[i] 表示总和为i的个数，每次迭代都会覆盖上一轮的值
            int[] dp = new int[negSum + 1];
            dp[0] = 1;
            for (int num : nums) {
                for (int i = negSum; i >= num; i--) {
                    dp[i] = i >= num ? dp[i] + dp[i - num] : dp[i];
                }
            }
            return dp[negSum];
        }


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

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if(root == null) return list;
            Stack<TreeNode> stack = new Stack<>();
            TreeNode curr = root;
            while(curr != null || !stack.isEmpty()){
                while(curr!=null){
                    stack.push(curr);
                    curr = curr.left;
                }
                if(!stack.isEmpty()){
                    curr = stack.pop();
                    list.add(curr.val);
                    curr = curr.right;
                }
            }
            return list;
        }


        class MyQueue {
            Stack<Integer> in;
            Stack<Integer> out;
            public MyQueue() {
                in = new Stack<>();
                out = new Stack<>();
            }

            public void push(int x) {
                in.push(x);
            }

            public int pop() {
                inToOut();;
                return out.pop();
            }

            public int peek() {
                inToOut();
                return out.peek();
            }

            public boolean empty() {
                if(in.isEmpty() && out.isEmpty()) return true;
                return false;
            }

            private void inToOut(){
                if (out.isEmpty()) {
                    while(!in.isEmpty()){
                        out.push(in.pop());
                    }
                }
            }
        }

        class MyStack {
            Queue<Integer> queue;

            public MyStack() {
                queue = new LinkedList<>();
            }

            public void push(int x) {
                int size = queue.size();
                queue.offer(x);
                while(size -- > 0){
                    queue.offer(queue.poll());
                }
            }

            public int pop() {
                return queue.poll();
            }

            public int top() {
                return queue.peek();
            }

            public boolean empty() {
                return queue.isEmpty();
            }
        }


        // 图像渲染
        public int[][] floodFill(int[][] image, int sr, int sc, int color) {
            if(image[sr][sc] == color) return image;
            int origin = image[sr][sc];
            image[sr][sc] = color;
            if(validDirection(image,sr,sc,1,origin)) floodFill(image, sr-1, sc, color);
            if(validDirection(image,sr,sc,2,origin)) floodFill(image, sr+1, sc, color);
            if(validDirection(image,sr,sc,3,origin)) floodFill(image, sr, sc-1, color);
            if(validDirection(image,sr,sc,4,origin)) floodFill(image, sr, sc+1, color);
            return image;
        }

        boolean validDirection(int[][] image, int x, int y, int dir,int color){
            // 上
            if(dir == 1 && x > 0 && image[x-1][y] == color) return true;
            // 下
            if(dir == 2 && x < image.length-1 && color == image[x+1][y]) return true;
            // 左
            if(dir == 3 && y > 0 && color == image[x][y-1]) return true;
            // 右
            if(dir == 4 && y < image[0].length-1 && color == image[x][y+1]) return true;
            return false;
        }

    }




    public static void main(String[] args) {

        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        System.out.println(Arrays.deepToString(new Solution().floodFill(image, 1, 1, 2)));

    }


        /*        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(new Solution().findTargetSumWays_optimized(nums, target));*/


/*
        String s = "()";
        System.out.println(new Solution().isValid(s));;
*/



}

import java.util.*;
import java.util.LinkedList;

/**
 * ClassName: QueueLearning
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/14 21:18
 * @Version 1.0
 */
public class QueueLearning {

    class MyCircularQueue {
        int front=0, rear=0;
        int[] data;
        int maxSize;

        public MyCircularQueue(int size){
            this.data = new int[size+1];
            this.maxSize = size+1;
        }

        public boolean enQueue(int value) {
            if((rear+1)%maxSize == front) return false;
            data[rear] = value;
            rear = (rear+1)%maxSize;
            return true;
        }

        public boolean deQueue() {
            if(rear == front) return false;
            front = (front+1)%maxSize;
            return true;
        }

        public int Front() {
            if(rear == front) return -1;
            return data[front];
        }

        public int Rear() {
            if(rear == front) return -1;
            return data[(rear-1+maxSize)%maxSize];
        }

        public boolean isEmpty() {
            if(rear == front) return true;
            return false;
        }

        public boolean isFull() {
            if((rear+1)%maxSize == front) return true;
            return false;
        }

    }


    static class Solution {
        public int numIslands(char[][] grid) {
            short[][] visited = new short[grid.length][grid[0].length];
            int sum = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (visited[i][j] == 0 && grid[i][j] == '1') {
                        DFSIsland(visited, grid, i, j);
                        sum++;
                    }
                }
            }
            return sum;
        }

        void DFSIsland(short[][] visited, char[][] grid, int x, int y) {
            visited[x][y] = 1;
            if (x + 1 < grid.length && visited[x + 1][y] == 0 && grid[x + 1][y] == '1') DFSIsland(visited, grid, x + 1, y);
            if (x - 1 >= 0 && visited[x -1][y] == 0 && grid[x - 1][y] == '1') DFSIsland(visited, grid, x - 1, y);
            if (y + 1 < grid[0].length && visited[x][y + 1] == 0 && grid[x][y + 1] == '1')
                DFSIsland(visited, grid, x, y + 1);
            if (y - 1 >= 0 && visited[x][y - 1] == 0 && grid[x][y - 1] == '1') DFSIsland(visited, grid, x, y - 1);
        }

        // 打开转盘锁
        public int openLock(String[] deadends, String target) {
            int targetNum = Integer.parseInt(target);
            // 用来查询是否可以向这个方向行动
            Set<Integer> dead = new HashSet<>();
            byte visited[] = new byte[10000];
            for(String s : deadends){
                dead.add(Integer.parseInt(s));
            }
            if(dead.contains(0)) return -1;

            Queue<Integer> q = new LinkedList<>();
            q.add(0);
            visited[0] = 1;
            int minDis = 0;
            int last = 0;
            while(!q.isEmpty()){
                int curr = q.poll();
                if(targetNum == curr) return minDis;
                // BFS
                if(validPosition(validNum(curr,+1000),dead,visited)) q.add(validNum(curr,+1000));
                if(validPosition(validNum(curr,-1000),dead,visited)) q.add(validNum(curr,-1000));
                if(validPosition(validNum(curr,+100),dead,visited)) q.add(validNum(curr,+100));
                if(validPosition(validNum(curr,-100),dead,visited)) q.add(validNum(curr,-100));
                if(validPosition(validNum(curr,+10),dead,visited)) q.add(validNum(curr,+10));
                if(validPosition(validNum(curr,-10),dead,visited)) q.add(validNum(curr,-10));
                if(validPosition(validNum(curr,+1),dead,visited)) q.add(validNum(curr,+1));
                if(validPosition(validNum(curr,-1),dead,visited)) q.add(validNum(curr,-1));

                if(last == curr){
                    minDis ++;
                    if(!q.isEmpty())
                        last = ((LinkedList<Integer>) q).getLast();
                }

            }
            return -1;

        }

        int validNum(int num, int addNum){
            if(addNum == 1000 && num/1000 == 9) return num-9000;
            else if(addNum == -1000 && num/1000 == 0) return num+9000;
            else if(addNum == 1 && num%10 == 9) return num-9;
            else if(addNum == -1 && num%10 == 0) return num+9;
            else if(addNum == 10 && num/10%10 == 9) return num-90;
            else if(addNum == -10 && num/10%10 == 0) return num+90;
            else if(addNum == 100 && num/100%10 == 9) return num-900;
            else if(addNum == -100 && num/100%10 == 0) return num+900;
            else return addNum+num;
        }

        Boolean validPosition(int num,Set<Integer> dead, byte visited[]){
            if(!dead.contains(num) && visited[num] == 0) {
                visited[num] = 1;
                return true;
            }
            return false;
        }


        // 时间复杂度就是一坨答辩
        public int numSquares(int n) {
            int[] res = new int[]{10000};
            DFSNumSquares(n,100,res,0);
            return res[0];
        }

        public void DFSNumSquares(double n, int square, int[] res, int currDepth){
            if(n == 0 && currDepth < res[0]) {
                res[0] = currDepth;
                return;
            }
            if(n <= 0 || square <= 0) return;
            for (int i = square; i > 0; i--) {
                if(i*i>n) continue;
                if(n>0 && n-Math.pow(i,2) >= 0)  DFSNumSquares(n-Math.pow(i,2), square, res, currDepth+1);
                DFSNumSquares(n,square-1,res,currDepth);
            }
        }


        //
        public int numSquares_optimized(int n) {
            int res = 0;
            // 如果当前处理的数字之前处理过了，那么如果有结果就已经反悔了，不需要再进行一次判断
            Set<Integer> visited = new HashSet<>();
            // 用队列记录当前要处理的数字，队列中加入的子树总会比父节点的值要大
            Queue<Integer> q = new LinkedList<>();
            q.offer(0);
            visited.add(0);
            while(!q.isEmpty()){
                int size = q.size();
                res++;
                while(size -- > 0){
                    int curr = q.poll();
                    for (int i = 0; i < n; i++) {
                        int tmp = curr+i*i;
                        if(tmp == n) return res;
                        else if(tmp > n) break;
                        else{
                            if(!visited.contains(tmp)) {
                                q.offer(tmp);
                                visited.add(tmp);
                            }
                        }
                    }
                }
            }
            return res;
        }

        public int numSquares_dp(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 0;
            for (int i = 1; i <= n; i++) {
                dp[i] = i;//最坏的情况都是由1的平方组成
                for (int j = 1; j * j <= i; j++) {
                    //动态规划公式
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }
            return dp[n];
        }

        // 拉格朗日四平方定理
        public int numSquares_lagrange(int n) {
            //一，先判断由1个平方数组成的
            //如果n是平方数，直接返回1即可，表示n由
            //1个平方数组成
            if (isSquare(n))
                return 1;
            //如果n是4的倍数，就除以4，因为4是2的平方，
            //如果n可以由m个完全平方数组成，那么4n也
            //可以由m个完全平方数组成
            while ((n & 3) == 0)
                n >>= 2;
            //二，在判断由4个平方数组成的
            //如果n是4的倍数，在上面代码的执行中就会一直除以4，
            //直到不是4的倍数为止，所以这里只需要判断n=(8b+7)
            //即可
            if ((n & 7) == 7)
                return 4;
            int sqrt_n = (int) (Math.sqrt(n));
            //三，接着判断由2个平方数组成的
            //下面判断是否能由2个平方数组成
            for (int i = 1; i <= sqrt_n; i++) {
                if (isSquare(n - i * i)) {
                    return 2;
                }
            }
            //四，剩下的只能由3个平方数组成了
            //如果上面都不成立，根据拉格朗日四平方和定理
            //只能由3个平方数组成了
            return 3;
        }

        boolean isSquare(int n){
            int sqrtN = (int)Math.sqrt(n);
            return sqrtN * sqrtN == n;
        }


    }

    public static void main(String[] args) {

        System.out.println(new Solution().numSquares_lagrange(12));


//        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
//        String target = "0202";
//        System.out.println(new Solution().openLock(deadends, target));


//        char[][] grid = new char[][]{{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
//        System.out.println(new Solution().numIslands(grid));
    }


}

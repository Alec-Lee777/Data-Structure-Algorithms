import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

/**
 * ClassName: T2023_12_3
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/29 12:32
 * @Version 1.0
 */

/*
 * 根类别：没有上级类别
 * 其他类别：有且仅有一个上级类别
 * 后代类别：
 *   if 没有后代则没有"后代类别"这一项
 *   else 后代类别为该类别的所有子孙类别
 * */

public class T2023_12_3 {

/*    public static void main(String[] args) throws IOException {
        // 获取输入，记录 wSelf[i] 和 wRoot[i]
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");

        int nodeNum = Integer.parseInt(str[0]);
        int testNum = Integer.parseInt(str[1]);

        long[] wSelf = new long[nodeNum + 1];
        int[] father = new int[nodeNum + 1];
        long[][] wRoot = new long[nodeNum + 1][2];
        HashMap<Integer, HashSet<Integer>> child = new HashMap<>();
        int wTotal = 0;
        // 获取权重输入
        str = br.readLine().split(" ");
        for (int i = 1; i <= nodeNum; i++) {
            wSelf[i] = Integer.parseInt(str[i-1]);
            wRoot[i][0] = wSelf[i];
            wTotal += wSelf[i];
        }
        // 获取父子关系，根节点权重
        str = br.readLine().split(" ");
        for (int i = nodeNum; i >= 2; i--) {
            father[i] = Integer.parseInt(str[i-2]);     // 获取输入
            wRoot[father[i]][0] += wRoot[i][0];               // 给父亲结点的结点总值加上当前结点的总值
            if(child.get(father[i]) == null){           // 如果父亲节点还没有记录过后孩子则创建
                child.put(father[i], new HashSet<>());
            }
            HashSet<Integer> currFather = child.get(father[i]);
            currFather.add(i);                          // 给父节点加上当前孩子
            if(child.containsKey(i)){                   // 如果孩子有孩子的话，则将其孩子也加上
                for(int node : child.get(i)){
                    currFather.add(node);
                }
            }
        }

        // 输出 testNum 次的查询路径
        long[] wDelta = new long[nodeNum + 1];
        int target, wDeltaMinIndex;
        long currTotal;
        for (int i = 0; i < testNum; i++) {
            str = br.readLine().split(" ");
            // 初始化工作
            byte[] visited = new byte[nodeNum + 1];
            String res = "";
            currTotal = wTotal;
            target = Integer.parseInt(str[0]);
            // 恢复临时 wRoot 的值
            for (int j = 1; j <= nodeNum; j++) {
                wRoot[j][1] = wRoot[j][0];
            }

            // 一直查找直至只剩一个节点
            while(currTotal > wSelf[target]){
                // 获取最小的 wDelta 节点下标
                wDeltaMinIndex = getWDeltaMinIndex(wRoot, visited, currTotal);
                res += wDeltaMinIndex + " ";
                visit(wDeltaMinIndex, visited, child);      // 标记当前子树及其所有根节点已经访问
                currTotal -= wRoot[wDeltaMinIndex][1];
                // 给父节点的总值都递归删掉当前结点的总值
                int fatherIndex = father[wDeltaMinIndex];
                while(fatherIndex != 0){
                    wRoot[fatherIndex][1] -= wRoot[wDeltaMinIndex][1];
                    fatherIndex = father[fatherIndex];
                }
            }
            res.trim();
            System.out.println(res);
        }

    }

    private static int getWDeltaMinIndex(long[][] wRoot,byte[] visited, long currTotal) {
        // 找到第一个未访问
        int wDeltaMinIndex = 0;
        for (int i = 1; i <= visited.length; i++) {
            if(visited[i] == 0){
                wDeltaMinIndex = i;
                break;
            }
        }
        long wDelta, wDeltaMin;
        wDeltaMin = Math.abs(2 * wRoot[wDeltaMinIndex][1] - currTotal);
        for (int i = wDeltaMinIndex + 1; i < visited.length ; i++) {
            wDelta = Math.abs(2 * wRoot[i][1] - currTotal);
            if(wDelta < wDeltaMin) {
                wDeltaMin = wDelta;
                wDeltaMinIndex = i;
            }
        }
        return wDeltaMinIndex;
    }

    private static void visit(int Index, byte[] visited, HashMap<Integer, HashSet<Integer>> child) {
        visited[Index] = 1;
        if(child.get(Index) != null) {
            for (Integer i : child.get(Index)) {
                if (visited[1] == 0)
                    visit(i, visited, child);
            }
        }
    }*/


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        int nodeNum = Integer.parseInt(in[0]);
        int testNum = Integer.parseInt(in[1]);
        long[] wRoot = new long[nodeNum + 1];
        long[] wight = new long[nodeNum + 1];
        int[] father = new int[nodeNum + 1];
        long wTotal = 0;
        in = br.readLine().split(" ");
        for (int i = 1; i <= nodeNum; i++) {
            wight[i] = Integer.parseInt(in[i - 1]);
            wTotal += wight[i];
            wRoot[i] = wight[i];
        }
        in = br.readLine().split(" ");
        for (int i = 2; i <= nodeNum; i++) {
            father[i] = Integer.parseInt(in[i - 2]);
        }

        //建立二叉树
        HashMap<Integer, HashSet<Integer>> myChild = new HashMap<>();
        for (int i = 2; i <= nodeNum; i++) {
            int currFather = father[i];
            while (currFather != 0) {
                wRoot[currFather] += wight[i];
                if (!myChild.containsKey(currFather)) {
                    myChild.put(currFather, new HashSet<Integer>());
                    myChild.get(currFather).add(currFather);
                }
                myChild.get(currFather).add(i);
                currFather = father[currFather];
            }
        }

        // 开始搜索
        for (int i = 0; i < testNum; i++) {
            in = br.readLine().split(" ");
            int target = Integer.parseInt(in[0]);
            long currTotal = wTotal;
            int[] visited = new int[nodeNum + 1];
            String res = "";
            while (currTotal > wight[target]) {
                int currNodeIndex = getCurrNode(visited, currTotal, wight, myChild);
                res += currNodeIndex + " ";
                HashSet<Integer> currSet = myChild.get(currNodeIndex);
                // 如果当前结点没有Child,并且自己就是target
                if (currSet == null && currNodeIndex == target) {
                    break; // 已经找到目标了，直接退出
                } else if (currSet == null) {
                    currTotal -= wight[currNodeIndex];
                    visited[currNodeIndex] = 1;
                } else if (!currSet.contains(target)) {
                    // 如果当前节点孩子中没有target，则要去掉当前结点的所有子节点
                    for (int index = 1; index <= nodeNum; index++) {
                        if (currSet.contains(index) && visited[index] == 0) {
                            visited[index] = 1;
                            currTotal -= wight[index];
                        }
                    }
                } else {
                    // 如果当前结点孩子中有target，去掉除了这个根节点之外的结点
                    for (int index = 1; index <= nodeNum; index++) {
                        if (!currSet.contains(index) && visited[index] == 0) {
                            visited[index] = 1;
                            currTotal -= wight[index];
                        }
                    }
                }
            }
            System.out.println(res.trim());
        }

    }

    public static int getCurrNode(int[] visited, long currTotal, long[] wight, HashMap<Integer, HashSet<Integer>> myChild) {
        int res = 0;
        long lowestAbs = 10000100;
        for (int i = 1; i < visited.length; i++) {
            if (visited[i] == 1) continue;
            long currRoot = 0;
            if (!myChild.containsKey(i)) {
                currRoot = wight[i];
            } else {
                for (int index : myChild.get(i)) {
                    if (visited[i] == 0) {
                        currRoot += wight[index];
                    }
                }
            }
            long currAbs = 2 * currRoot - currTotal;
            if (lowestAbs > currAbs) {
                res = i;
                lowestAbs = currAbs;
            }
        }

        return res;
    }

/*
接收 "类别数量NodeNum" 和 “测试数量 testNum"
建立一个 "子树总权重wRoot[i]", wRoot[i]表示i节点为根的子树的权重总值
建立一个 "总权重wTotal" ,初始为0

接收 "类别权重 wight[i + 1]" ,wight[i] 表示i节点的权重
	计入总权重( wTotal+=wight[i] )
	并且将子树总权重初始化为自身( wRoot[i] = wight[i] )
接收 "父亲类别 father[i + 1]"，father[1] = 0(根), father[i]表示i的父亲

建立二叉树：
	创建一个 HashMap<Integer, HashSet<Integer>> myChild 用来记录孩子， myChild.get(i)用于获得自己孩子的集合
	用myChild.get(i).contains(childIndex)判断节点i是否有孩子 childIndex

	递归为每个结点的祖辈添加自己为其孩子,直至不存在下一个祖辈(currFather != 0)
		令currFather = father[i]
		给父亲的子树总权重加上自己的权重 wRoot[currFather] += wight[i]
		如果myChild.containsKey(currFather)还不存在
			则先创建：myChild.put(currFather, new HashSet<Integer>)
			并且将自己加入集合：myChild.get(currFather).add(currFather)
		把自己添加给祖辈：myChild.get(currFather).add(i)
		向上继续寻找祖辈：currFather = father[currFather]

每次接受一个测试编号target，总共测试 testNum 次，输出测试过程遇到的节点编号

一次查找：
	建立visited[NodeNum + 1], visted[i] 表示当前结点已经被访问过了，不纳入查找范围
	建立 currTotal = wTotal, 记录当前查询还剩多少权重
	进行多次查找，直至 currTotal 和 wight[target] 相同的时候( while(currTotal > wight[target])说明已经找到最后一个节点了,退出循环
		查找首个未访问且权差绝对值最小的节点 currNodeIndex = getCurrNode(visited, wRoot,currTotal)
		打印 currNodeIndex
		如果当前子树包含目标节点 myChild.get(currNodeIndex).contains(target)
			更新 visited 列表，index不在 myChild.get(currNodeIndex) 集合中的所有visited[index]=0的结点都设置为1，并且将总权重减去wight[index]
		否则不含,应该排除自己这颗子树去找
			更新 visited 列表，index在 myChild.get(currNodeIndex) 集合中的所有visited[index]=0的结点都设置为1，并且将总权重减去wight[index]


*/


}





/*

记录每个结点自己的权重 wSelf[i]
记录每个结点为根的总权重 wRoot[i]
记录每个结点自己的父节点 father[i]
记录每个结点自己的孩子 child.get(father[i]).add(i), 还要向上递归添加
记录当前整棵树的总权重 wTotal
记录除自己以外的结点的权重
    1. 根节点：0
    2. 子节点：wTotol - wRoot[i]
求 wDelta 最小的结点：wDeltaMinIndex
    if(| wRoot[i] - (wTotal - wRoot[i]) | <
        | wRoot[wDeltaMinIndex] - (wTotal - wRoot[wDeltaMinIndex]) |)
        wDeltaMin = i
选中 i , 输出 i 的值
判断 i 是否为要查找的结点 target
    是则退出，判断下一个 target
    不是则将 wTotal 减去 wRoot[i]，重复上述过程

*/

import java.io.*;
import java.util.*;

/**
 * ClassName: T2023_3_3
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/9 14:14
 * @Version 1.0
 */
public class T2023_3_3 {

    int n;
    static Map<Integer, Map<Integer, List<Integer>>> r = new HashMap<>();

    public Set<Integer> check(String s){
        Set<Integer> ret = new HashSet<>();

        // 判断如果s的第一位是数字，则处理原子表达式
        if(Character.isDigit(s.charAt(0))){
            int len = s.length();
            char op = '0';
            // 找到操作符op
            for (int i = 0; i < len; i++) {
                if(!Character.isDigit(s.charAt(i))){
                    op = s.charAt(i);
                    break;
                }
            }
            // 从操作符断开字符串
            String[] str = s.split(op+"");
            // 获得操作符两边的 id 和 val
            int id = Integer.valueOf(str[0]), val = Integer.valueOf(str[1]);
            if(op == ':'){
                if(r.containsKey(id) && r.get(id).containsKey(val)){
                    ret.addAll(r.get(id).get(val));
                }
            }else{
                // 如果是 ~ 操作符
                if(r.containsKey(id)){
                    // 遍历当前 id 的所有权限信息
                    for(Map.Entry<Integer, List<Integer>> entry : r.get(id).entrySet()){
                        // 如果权限信息就是 ~ 对应的 val，则不应该加入结果集合
                        if(entry.getKey() == val) continue;
                        ret.addAll(entry.getValue());
                    }
                }
            }
        }
        // 如果首字符不是数字，说明此时需要递归处理非原子表达式
        else{
            char op = s.charAt(0);
            int bal = 1;
            int len = s.length();
            // 找到第一个原子表达式的最后一位
            int i = 2;
            for (; i < len; i++) {
                if(s.charAt(i) == '(') ++ bal;
                if(s.charAt(i) == ')') -- bal;
                if(bal==0) break;
            }
            // 递归处理原子表达式
            Set<Integer> set1 = check(s.substring(2,i));
            Set<Integer> set2 = check(s.substring(i+2, len-1));
            // 处理逻辑运算符
            if(op=='&'){
                for(int t : set1){
                    if(set2.contains(t)) ret.add(t);
                }
            }else{
                ret.addAll(set1);
                ret.addAll(set2);
            }
        }
        return ret;
    }

    void run() throws IOException {
        // 用 Scannker 不用 BufferedReader，因为字符串处理的内容太多了
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        // 读取所有用户信息
        for (int i = 0; i < n; i++) {
            int a = in.nextInt();
            int q = in.nextInt();

            while(q-- > 0){
                int x = in.nextInt(), y = in.nextInt();
                r.putIfAbsent(x, new HashMap<>());
                r.get(x).putIfAbsent(y, new ArrayList<>());
                r.get(x).get(y).add(a);
            }
        }

        int m = in.nextInt();
        while(m-- > 0){
            List<Integer> ans = new ArrayList<>(check(in.next()));
            if(ans.size() == 0) System.out.println();
            else{
                Collections.sort(ans);
                for(int id : ans) System.out.println(id + " ");
                System.out.println();
            }
        }

    }

}

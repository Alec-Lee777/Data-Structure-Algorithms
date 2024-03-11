import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ClassName: T2023_5_1
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/10 10:05
 * @Version 1.0
 */
public class T2023_5_1 {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int n;

    void run() throws IOException {
        String s = in.readLine();
        n = Integer.parseInt(s);

        // Map<char[][], Integer> m = new HashMap<>();
        //因为二维数组进行比较时，比较的是地址，所以很不好进行相等判断，所以改用字符串
        Map<String, Integer> m = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String log = "";
            // 将当前棋盘输入字符数组
            for (int j = 0; j < 8; j++) {
                s = in.readLine();
                // log.concat(s) 返回的是一个 String，但是并不会将其保存到 log 中，所以必须要写log=log.concat(s)
                log = log.concat(s);
            }
            // 更新 Map
            if(m.containsKey(log)){
                m.put(log, m.get(log)+1);
            }else{
                m.putIfAbsent(log, 1);
            }
            System.out.println(m.get(log));
        }

    }


}

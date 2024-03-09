import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ClassName: T20230302
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/8 17:39
 * @Version 1.0
 */
public class T20230302 {

    // n = n , m = resource, k = minDay
    int n,resource,minDay;
    // ti = needDay, ci = needResource
    int needDay, needResource;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));


    void run() throws IOException{
        String[] s = in.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        resource = Integer.parseInt(s[1]);
        minDay = Integer.parseInt(s[2]);
        // 用来存储 key (第key天) value(需要的资源)
        Map<Integer,Integer> hash = new HashMap<>();
        int maxDay = 0;
        for (int i = 0; i < n; i++) {
            s = in.readLine().split(" ");
            needDay = Integer.parseInt(s[0]);
            needResource = Integer.parseInt(s[1]);
            maxDay = Math.max(maxDay, needDay);
            hash.put(needDay, hash.getOrDefault(needDay, 0) + needResource);
        }
        int curr = hash.get(maxDay);
        while(maxDay > minDay && resource >= curr){
            if(!hash.containsKey(maxDay)){
                maxDay--;
                continue;
            }
            maxDay--;
            resource-=curr;
            // 下一次 maxDay 需要的资源应该是更大天数需要的资源与当前天数需要的资源之和
            hash.put(maxDay,hash.getOrDefault(maxDay,0)+curr);
            curr = hash.get(maxDay);

        }

        out.println(maxDay);
        out.flush();

    }


}

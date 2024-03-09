import javax.imageio.plugins.tiff.FaxTIFFTagSet;
import java.io.*;

/**
 * ClassName: T2023_3_2_method2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/9 13:46
 * @Version 1.0
 */
public class T2023_3_2_method2{
    int n, resource, minDay;
    static int  N = 100010;
    int[] needDay = new int[N];
    int[] needResource = new int[N];

    // 检查是否能够用现有资源达到 mid 天完工的条件
    boolean check(int mid){
        long sum = 0;
        for (int i = 0; i < n; i++) {
            if(needDay[i] > mid){
                sum += (needDay[i] - mid) * needResource[i];
            }
        }
        return resource >= sum;
    }

    void run() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String[]s = in.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        resource = Integer.parseInt(s[1]);
        minDay = Integer.parseInt(s[2]);
        int max = 0;
        for (int i = 0; i < n; i++) {
            s = in.readLine().split(" ");
            needDay[i] = Integer.parseInt(s[0]);
            needResource[i] = Integer.parseInt(s[1]);
            max = Math.max(max, needDay[i]);
        }

        // 二分法查找符合条件的最小值
        int l = minDay, r = max;
        while(l < r){
            int mid = l + r >> 1;
            if(check(mid)){
                r = mid;
            }
            else{
                l = mid + 1;
            }
        }
        System.out.println(l);
    }
}

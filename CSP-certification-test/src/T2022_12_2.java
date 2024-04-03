import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * ClassName: T2022_12_2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/30 8:07
 * @Version 1.0
 */
public class T2022_12_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 获取输入
        String[] in = br.readLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        in = br.readLine().split(" ");
        String[] in2 = br.readLine().split(" ");
        int[] previous = new int[m + 1];
        int[] courseTime = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            previous[i] = Integer.parseInt(in[i - 1]);
            courseTime[i] = Integer.parseInt(in2[i - 1]);
        }

        // 计算最早开始时间
        int[] earliest = new int[m + 1];
        earliest[0] = 1;
        int latestEnd = 0;
        String res = "";
        for (int i = 1; i <= m; i++) {
            earliest[i] = earliest[previous[i]] + courseTime[previous[i]];
            res += earliest[i] + " ";
            latestEnd = Math.max(earliest[i] + courseTime[i] - 1, latestEnd);
        }
        System.out.println(res.trim());
        res = "";
        // 计算最晚开始时间
        if(latestEnd <= n){
            int[] latestStart = new int[m + 1];
            latestStart[m] = n - courseTime[m] + 1;
            latestStart[0] = n + 1;
            res += latestStart[m];
            for (int i = m - 1; i >= 1; i--) {
                int relyMeIndex = 0;
                for (int j = m; j > i ; j--) {
                    if(previous[j] == i && latestStart[relyMeIndex] > latestStart[j]){       // 如果j号课程依赖i号课程,则i号课程需要考虑j的完成进度
                        relyMeIndex = j;
                    }
                }
                latestStart[i] = latestStart[relyMeIndex] - courseTime[i];
                res = latestStart[i] + " " + res;
            }
            System.out.println(res.trim());
        }

    }
}


/*
* 记录科目训练时间 courseTime[m + 1]，index为科目编号
* 记录科目依赖关系 previous[m + 1], previous[i] 表示编号为i的科目的前置科目的编号，previous[i]一定小于i
*
* 最早开始时间 earliest[m + 1], earliest[i] = earliest[previous[i]] + courseTime[previous[i]]
* 计算最早开始时间时顺便记录最晚结束时间 latestEnd = Math.max(earlist[i] + courseTime[i] - 1)
*
* 如果 latestEnd > n 则不用计算 latestStart
* 最晚开始时间 latestStart[m + 1], latestEnd - courseTime[m + 1] + 1
*
*
* */
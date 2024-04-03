import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * ClassName: T2023_3_2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/28 9:45
 * @Version 1.0
 */
public class T2023_3_2 {

    public static void main(String[] args) throws IOException {
        int n, resource, minDay, need = 0;
        Scanner in = new Scanner(new InputStreamReader(System.in));
        n = in.nextInt();
        resource = in.nextInt();
        minDay = in.nextInt();
        // [i][0]表示i号地所需天数，[i][1]表示i号地减少一天所需资源
        int[][] DaysAndResources = new int[n][2];
        for (int i = 0; i < n; i++) {
            DaysAndResources[i][0] = in.nextInt();
            DaysAndResources[i][1] = in.nextInt();
        }
        Arrays.sort(DaysAndResources, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr0, int[] arr1) {
                return arr1[0] - arr0[0];
            }
        });

        // i 为第一个比最大天数小的天数，maxDay为当前最大天数
        int i = 0, maxDay = DaysAndResources[0][0];
        for (; i < n && DaysAndResources[i][0] == DaysAndResources[0][0]; i++) {
            need += DaysAndResources[i][1];
        }
        while(resource >= need){
            if(maxDay <= minDay) break;
            // 用资源消去最大的一天
            resource -= need;
            // 最大天数减一
            maxDay--;
            // 如果和i所指的天数相同了，则要加入need中
            while(i < n && DaysAndResources[i][0] == maxDay){
                need += DaysAndResources[i][1];
                i++;
            }
        }
        System.out.println(maxDay);

    }



}

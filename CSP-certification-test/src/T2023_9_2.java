import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ClassName: T2023_9_2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/28 16:19
 * @Version 1.0
 */
public class T2023_9_2 {

    public static void main(String[] args) {

        // 获取输入
        Scanner scan = new Scanner(new InputStreamReader(System.in));
        int operNum = scan.nextInt();
        int queryNum = scan.nextInt();
        int operType;
        double[] Strech = new double[operNum + 1];
        double[] rotate = new double[operNum + 1];
        // 存下n个操作的dp,拉伸为前缀积,旋转为前缀和
        operType = scan.nextInt();
        if(operType == 1){
            Strech[1] = scan.nextDouble();
            rotate[1] = 0;
        }else{
            Strech[1] = 1;
            rotate[1] = scan.nextDouble();
        }

        for (int i = 2; i <=operNum; i++) {
            operType = scan.nextInt();
            if(operType == 1){
                Strech[i] = scan.nextDouble() * Strech[i-1];
                rotate[i] = rotate[i-1];
            }else{
                Strech[i] = Strech[i-1];
                rotate[i] = scan.nextDouble() + rotate[i-1];
            }
        }

        // 计算 m 个结果
        for (int i = 0; i < queryNum; i++) {
            // 获取起始位置以及首尾操作序号
            int start = scan.nextInt();
            int end = scan.nextInt();
            double x = scan.nextDouble();
            double y = scan.nextDouble();
            double theta = rotate[end] - (start == 1 ? 0 : rotate[start-1]);
            double k = Strech[end]/ (start == 1 ? 1 : Strech[start-1]);
            // 进行start~end的操作
            x *= k;
            y *= k;
            double temp = x;
            x = x * Math.cos(theta) - y * Math.sin(theta);
            y = temp * Math.sin(theta) + y * Math.cos(theta);
            System.out.println(x + " " + y);
        }

    }

}

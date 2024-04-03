import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ClassName: T2023_9_1
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/28 11:21
 * @Version 1.0
 */
public class T2023_9_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(new InputStreamReader(System.in));
        int operNum, originPosNum;

        operNum = scan.nextInt();
        originPosNum = scan.nextInt();
        int diffX = 0, diffY = 0;
        for (int i = 0; i < operNum; i++) {
            diffX += scan.nextInt();
            diffY += scan.nextInt();
        }
        int x = 0, y = 0;
        for (int i = 0; i < originPosNum; i++) {
            x = scan.nextInt() + diffX;
            y = scan.nextInt() + diffY;
            System.out.println(x + " " + y);
        }


    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName: T2023_12_1
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/28 20:39
 * @Version 1.0
 */
public class T2023_12_1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] first_line = br.readLine().split(" ");
        int n = Integer.parseInt(first_line[0]);
        int m = Integer.parseInt(first_line[1]);
        int[][] warehouse = new int[n + 1][m];
        for (int i = 1; i < n + 1; i++) {
            String[] curr = br.readLine().split(" ");
            for (int j = 0; j < m; j++) {
                warehouse[i][j] = Integer.parseInt(curr[j]);
            }
        }
        int flag = 1;
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= n ; j++) {
                flag = 1;
                for (int k = 0; k < m && flag == 1; k++) {
                    if(warehouse[i][k] >= warehouse[j][k]){
                        flag = 0;
                    }
                }
                if(flag == 1){
                    System.out.println(j);
                    break;
                }else if(j == n){
                    System.out.println(0);
                }
            }
        }
    }
}

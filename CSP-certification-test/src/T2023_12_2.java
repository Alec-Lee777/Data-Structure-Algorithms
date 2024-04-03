import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName: T2023_12_2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/29 11:02
 * @Version 1.0
 */
public class T2023_12_2 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int queryNum = Integer.parseInt(br.readLine());
        for (int i = 0; i < queryNum; i++) {
            String[] temp = br.readLine().split(" ");
            long n = Long.parseLong(temp[0]);
            int k = Integer.parseInt(temp[1]);
            long res = 1;
            for (int j = 2; j < n; j++) {
                int cnt = 0;
                while(n % j == 0){
                    cnt ++;
                    n /= j;
                }
                if(cnt >= k){
                    res *= Math.pow(j, cnt);
                }
            }
            System.out.println(res);
        }

    }

}

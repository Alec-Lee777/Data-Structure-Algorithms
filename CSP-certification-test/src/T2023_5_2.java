import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName: T2023_5_2
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/11 8:51
 * @Version 1.0
 */
public class T2023_5_2 {
    int n , d;
    int Q[][], K[][];
    int W[];
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // 获取矩阵输入
    void getMetrix(int[][] A) throws IOException {
        for (int i = 0; i < n; i++) {
            String[] s = in.readLine().split(" ");
            for (int j = 0; j < d; j++) {
                A[i][j] = Integer.parseInt(s[j]);
            }
        }
    }

    // 获取向量输入
    int[] getVector() throws IOException {
        int[] ret = new int[n];
        String[] s = in.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            ret[i] = Integer.parseInt(s[i]);
        }
        return ret;
    }

    // 矩阵乘法 ×
    int[][] matrixMultiple(int[][] A, int[][] B){
        int aLine,aCol,bLine,bCol;
        aLine = A.length;
        aCol = A[0].length;
        bLine = B.length;
        bCol = B[0].length;

        int[][] ret = new int[aLine][bCol];
        for (int i = 0; i < aLine; i++) {
            for (int j = 0; j < bCol; j++) {
                for (int k = 0; k < aCol; k++) {
                    ret[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return ret;
    }

    // 点乘
    int[][] pointMultiple(int[] Vector, int[][] Metric){
        int mLine = Metric.length;
        int mCol = Metric[0].length;
        for (int i = 0; i < mLine; i++) {
            for (int j = 0; j < mCol; j++) {
                Metric[i][j] = Metric[i][j] * Vector[i];
            }
        }

        return Metric;
    }

    int[][] getT(int[][] A){
        int aLine = A.length;
        int aCol = A[0].length;
        int[][] ret = new int[aCol][aLine];
        for (int i = 0; i < aLine; i++) {
            for (int j = 0; j < aCol; j++) {
                ret[j][i] = A[i][j];
            }
        }
        return ret;
    }

    void run() throws IOException {

        String[] s = in.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        d = Integer.parseInt(s[1]);

        // 获取 Q 矩阵和 K 矩阵
        Q = new int[n][d];
        K = new int[n][d];
        getMetrix(Q);
        getMetrix(K);
        // 计算 Q×KT
        Q = matrixMultiple(Q,getT(K));
        // K用不上了，节约空间，用K存V，不再重新开空间
        getMetrix(K);
        // 计算 Q×KT×V
        Q = matrixMultiple(Q,K);
        W = getVector();
        int[][] res = pointMultiple(W, Q);

        // int[][] res = matrixMultiple(pointMultiple(W,matrixMultiple(Q,getT(K))), V);


        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }
}

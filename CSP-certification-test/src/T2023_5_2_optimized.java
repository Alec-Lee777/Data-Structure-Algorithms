import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName: T2023_5_2_optimized
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/11 18:13
 * @Version 1.0
 */
public class T2023_5_2_optimized {
    int n, d;
    long[][] Q,K,V;
    long[] W;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // 获取矩阵式输入
    void getMetrix(long[][] A) throws IOException {
        for (int i = 0; i < n; i++) {
            String[] s = in.readLine().split(" ");
            for (int j = 0; j < d; j++) {
                A[i][j] = Integer.parseInt(s[j]);
            }
        }
    }
    // 获取向量输入
    long[] getVector() throws IOException {
        long[] ret = new long[n];
        String[] s = in.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            ret[i] = Integer.parseInt(s[i]);
        }
        return ret;
    }

    // 转置函数
    long[][] getT(long[][] A){
        long[][] kt = new long[d][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d; j++) {
                kt[j][i] = A[i][j];
            }
        }
        return kt;
    }

    // 计算矩阵 A×B 的第i行第j列元素
    long matrixMultiSingleElem(long[][] A,long[][] B,int i,int j){
        long res = 0L;
        for (int k = 0; k < d; k++) {
            res += A[i][k] * B[k][j];
        }
        return res;
    }

    // 获取矩阵乘法的单个元素
    long[] matrixMultiSingleLine(long[][] A, long[][] B, int line){
        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            res[i] = matrixMultiSingleElem(A,B,line, i);
        }
        return res;
    }

    // 获取结果矩阵的单个元素，避免了一次求出所有矩阵，节约空间
    long getResultSingleElement(long[] leftLine, long[][] rightMatrix,int j){
        long res = 0L;
        for (int i = 0; i < n; i++) {
            res += leftLine[i] * rightMatrix[i][j];
        }
        return res;
    }

    // 获取结果矩阵的一行
    long[] getResultSingleLine(long[] leftLine, long[][] rightMatrix, int line) throws IOException {
        long[] res = new long[d];
        for (int i = 0; i < d; i++) {
            res[i] = getResultSingleElement(leftLine, rightMatrix, i);
        }
        return res;
    }

    void run() throws IOException {
        String[] s = in.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        d = Integer.parseInt(s[1]);

        // 获取 Q 矩阵和 K 矩阵
        Q = new long[n][d];
        K = new long[n][d];
        V = new long[n][d];
        getMetrix(Q);
        getMetrix(K);
        getMetrix(V);
        W = getVector();
        K = getT(K);

        for (int i = 0; i < n; i++) {
            long[] QKT_line = matrixMultiSingleLine(Q,K,i);
            long[] res_line = getResultSingleLine(QKT_line,V,i);
            for (int j = 0; j < d; j++) {
                System.out.print(res_line[j] * W[i] + " ");
            }
            System.out.println();
        }

    }


}

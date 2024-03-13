import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: TwoDimensionalArray
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/13 10:45
 * @Version 1.0
 */
public class TwoDimensionalArray {

    static class Solution {

        public void rotate(int[][] matrix) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < i; j++) {
                    matrix[i][j] = matrix[i][j]+matrix[j][i];
                    matrix[j][i] = matrix[i][j]-matrix[j][i];
                    matrix[i][j] = matrix[i][j]-matrix[j][i];
                }
            }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length/2; j++) {
                    matrix[i][j] = matrix[i][matrix.length-j-1]+matrix[i][j];
                    matrix[i][matrix.length-j-1] = matrix[i][j]-matrix[i][matrix.length-j-1];
                    matrix[i][j] = matrix[i][j]-matrix[i][matrix.length-j-1];
                }
            }
        }

        public void exchange(int[][] matrix, int i1, int j1, int i2, int j2){
            matrix[i1][j1] = matrix[i1][j1] + matrix[i2][j2];
            matrix[i2][j2] = matrix[i1][j1] - matrix[i2][j2];
            matrix[i1][j1] = matrix[i1][j1] - matrix[i2][j2];
        }

        public void rotate_optimized(int[][] matrix) {
            if(matrix.length % 2 == 0){
                for (int i = 0; i < matrix.length/2; i++) {
                    for (int j = 0; j < matrix.length/2; j++) {
                        exchange(matrix,i,j,j,matrix.length-1-i);
                        exchange(matrix,i,j,matrix.length-1-i,matrix.length-1-j);
                        exchange(matrix,i,j,matrix.length-1-j,matrix.length-1-(matrix.length-1-i));
                    }
                }
            }else{
                for (int i = 0; i < matrix.length/2+1; i++) {
                    for (int j = 0; j < matrix.length/2; j++) {
                        exchange(matrix,i,j,j,matrix.length-1-i);
                        exchange(matrix,i,j,matrix.length-1-i,matrix.length-1-j);
                        exchange(matrix,i,j,matrix.length-1-j,matrix.length-1-(matrix.length-1-i));
                    }
                }
            }
        }

        public void setZeroes(int[][] matrix) {
            List<int[]> list = new ArrayList<>();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if(matrix[i][j] == 0){
                        list.add(new int[]{i,j});
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                // 清除list.get(i)[0]行
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[list.get(i)[0]][j] = 0;
                }
                // 清除list.get(i)[1]列
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j][list.get(i)[1]] = 0;
                }
            }
        }

        public void setZeroes_optimized(int[][] matrix){
            int rowLen = matrix.length;
            int colLen = matrix[0].length;

            int[] rowRecord = new int[rowLen];
            int[] colRecord = new int[colLen];

            for (int i = 0; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {
                    if(matrix[i][j] == 0){
                        rowRecord[i] = 1;
                        colRecord[j] = 1;
                    }
                }
            }

            for (int i = 0; i < rowLen; i++) {
                for (int j = 0; j < colLen; j++) {
                    if(rowRecord[i] == 1 || colRecord[j] == 1){
                        matrix[i][j] = 0;
                    }
                }
            }
        }


        // 在副对角线上：x+y=i
        // 向上时，初始位置要求x=i并且x<=n-1，向上移动过程中要求x>=0并且x>=i-(m-1)，因为x最多向右移动m次，而i是下标，m是列数，所以需要给m转换成下标形式(m-1)
        public int[] findDiagonalOrder(int[][] mat) {
            int n = mat.length;
            int m = mat[0].length;
            int[] result = new int[n*m];

            for (int i = 0,idx=0 ; i < n+m-1; i++) {
                if(i%2==0){
                    for(int x=Math.min(i,n-1); x >= Math.max(0,i-(m-1)); x--){
                        result[idx++] = mat[x][i-x];
                    }
                }else{
                    for (int y = Math.min(i,m-1); y >= Math.max(0,i-(n-1)) ; y--) {
                        result[idx++] = mat[i-y][y];
                    }
                }
            }
            return result;
        }

    }


    public static void main(String[] args) {

    }
}

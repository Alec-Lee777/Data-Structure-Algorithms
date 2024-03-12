import java.util.Scanner;

/**
 * ClassName: T2023_5_3
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/11 20:51
 * @Version 1.0
 */
public class T2023_5_3 {
    int n;
    String finalData = "";
    Scanner scan = new Scanner(System.in);

    int charToInt(char c){
        return Character.getNumericValue(c);
    }

    // 解析原始数据长度，并将光标移动到原始数据的下一位
    long getLengthOfFinalData(char[] c, int pos){
        // 记录当前字节的数据长度
        long len = 0;
        // 工作指针
        int curr = pos;
        int left,right;
        // 记录当前是十六进制数的第几位
        int parsePos = 0;
        do{
            pos += 2;
            left = charToInt(c[curr]);
            right = charToInt(c[curr + 1]);
            if(left >= 8){
                len += (long) (((left-8)*16+right) * Math.pow(128,parsePos));
            }
            else{
                len += (long) (((left)*16+right) * Math.pow(128,parsePos));            }
            parsePos ++;
            curr = pos;
        } while(left >= 8);

        return len;
    }

    int[] sixteenToBinary(long x){
        int pos = 0;
        int[] ret = new int[4];
        while(x != 0){
            if (x % 2 == 1) {
                ret[pos++] = '1';
            } else {
                ret[pos++] = '0';
            }
            x /= 2;
        }
        return ret;
    }

    int leftSixBinaryToInt(int[] left, int[]right){
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res += left[i] * Math.pow(2, 5-i);
        }
        res += right[0]*2 + right[1];
        return res;
    }


    // 获取当前数据长度，并将指针pos移动到数据的第一位
    int getLengthOfCurrentData(char[] data, int pos){
        long left = charToInt(data[pos]);
        long right = charToInt(data[pos+1]);
        int[] leftBinary = sixteenToBinary(left);
        int[] rightBinary = sixteenToBinary(right);
        if(rightBinary[2] == '0' && rightBinary[3] == '0'){
            // 00代表都是字面量
            int leftSix = leftSixBinaryToInt(leftBinary,rightBinary);
            if(leftSix < 60){
                // 小于60，(前六位+1)就是字节数
                pos += 2;
                return leftSix;
            }
            else{
                // 大于60，后面还需要字节来存储字面量字节数
                int tmp = leftSix-59;
                int dataLen = 0;
                pos += 2*(tmp+1);

                for (int i = 0; i < tmp*4; i++) {

                }
            }

        }
        if(rightBinary[2] == '0' && rightBinary[3] == '1'){
            // 01代表回溯引用，第一个字节前三位为偏移量o的高三位
        }
        if(rightBinary[2] == '1' && rightBinary[3] == '0'){
            // 01代表回溯引用，偏移量o占16位，以小端序存储于随后的两个字节中
        }

        return pos;
    }

    //
    void pushData(char[] data, int pos, int currLen){
        for (; pos < pos + currLen * 2; pos++) {
            finalData += data[pos];
        }
    }

    void run(){

        // 获取字节数
        String s = scan.nextLine();
        n = Integer.parseInt(s);

        // 获取后面的完整字符串
        s = scan.nextLine();
        while(scan.hasNextLine()){
            String temp = scan.nextLine();
            s = s.concat(temp);
        }

        // 将输入作为单个字符进行统一处理
        char[] data = s.toCharArray();
        int dataLen = data.length;
        int i = 0;

        // 获取当前数据原始长度，并移动光标i的位置到数据域
        long finalDataLen = getLengthOfFinalData(data, i);

        // 每次循环处理一个数据域
        while(i < dataLen){
            // 获取数据长度
            int currLen = getLengthOfCurrentData(data, i);
            // 将数据解析到最终中
            pushData(data, i, currLen);
        }
    }

}

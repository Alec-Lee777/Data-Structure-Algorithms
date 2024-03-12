import java.util.Scanner;

/**
 * ClassName: T2023_5_3_optimized
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/12 10:54
 * @Version 1.0
 */

// 参考代码:https://ernest.itakestwo.fun/2023/08/14/23/59/00/748/csp-202305-3-%e8%a7%a3%e5%8e%8b%e7%bc%a9/algorithms/ernest/
public class T2023_5_3_optimized {
    // 将输入字符串的 index~index+1 转化为二进制字符串
    public static String hexByte2binByte(String hexString, int index){
        // parseInt 可以解析16进制数为十进制，toBinaryString可以转为二进制字符串
        StringBuilder binByte = new StringBuilder(Integer.toBinaryString(Integer.parseInt(hexString.substring(index, index+2),16)));
        // 如果不足8位二进制，则在前面补0
        while(binByte.length() < 8){
            binByte.insert(0, "0");
        }
        return binByte.toString();
    }

    void run(){
        Scanner scanner = new Scanner(System.in);
        StringBuilder compressedData = new StringBuilder();
        int compressedDataLength = scanner.nextInt();
        while(compressedData.length() < compressedDataLength * 2){
            compressedData.append(scanner.next());
        }

        int o, l, i, length;
        int power = 0;
        int index = 0; // 当前处理到的压缩数据的索引
        int originalDataLength = 0;
        String binByte = null;
        StringBuilder originalData = new StringBuilder();

        // 引导区
        // index = 0 保证第一次会读取引导域的值，binByte.charAt(0)保证只读到最后一位
        for(; index < compressedData.length() && (index == 0 || binByte.charAt(0) != '0'); power += 1, index+=2){
            binByte = hexByte2binByte(compressedData.toString(), index);
            originalDataLength += Integer.parseInt(binByte.substring(1), 2) * Math.pow(128, power);
        }

        while (index < compressedData.length()){
            binByte = hexByte2binByte(compressedData.toString(), index);
            if(binByte.charAt(6)=='0'&&binByte.charAt(7)=='0'){
                length = Integer.parseInt(binByte.substring(0,6), 2); // 元素长度 (字节数量)
                if(length < 60){
                    originalData.append(compressedData.substring(index+2, index + 2 + (length+1)*2));
                    index = index + 2 + 2*(length+1);
                }else{
                    power = length - 59;
                    length = 0;
                    for (int j = 0; j < power; j++) {
                        length += Integer.parseInt(compressedData.substring(index + 2 + 2 * j,index + 4 + 2 * j), 16) * Math.pow(256, j);
                    }
                    // 字节中存储的是 l-1，所以应该补回1
                    length += 1;
                    index += 2*power + 2;
                    originalData.append(compressedData.substring(index, index + 2*length));
                    index += 2*length;
                }
            } else if (binByte.charAt(6) == '0'&& binByte.charAt(7) =='1') {
                // 解析偏移量
                o = Integer.parseInt(binByte.substring(0,3),2)*256 + Integer.parseInt(compressedData.substring(index + 2,index + 4),16);
                // 解析长度
                l = Integer.parseInt(binByte.substring(3,6),2) + 4;
                // 找到偏移位置
                i = originalData.length() - 2*o;
                for (int j = 0; j < 2*l; j++, i++) {
                    if(i == originalData.length()) i = originalData.length() - 2*o;
                    originalData.append(originalData.charAt(i));
                }
                index += 4;
            }else if (binByte.charAt(6) == '1' && binByte.charAt(7) == '0') {
                o = Integer.parseInt(compressedData.substring(index+2,index+4),16) +
                        Integer.parseInt(compressedData.substring(index+4,index+6),16)*256;
                l = Integer.parseInt(binByte.substring(0,6),2) + 1;
                i = originalData.length() - 2*o;
                for (int j = 0; j < 2*l; j++,i++) {
                    if(i == originalData.length()) i = originalData.length() - 2*o;
                    originalData.append(originalData.charAt(i));
                }
                index += 6;
            }
        }

        for (int j = 0; j < originalDataLength * 2; j += 16) {
            System.out.println(originalData.substring(j, Math.min(j + 16, originalDataLength * 2)));
        }

    }

}

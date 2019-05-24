package com.example.dongdemo;

/**
 * Created by M_genius on 2018/7/10.\
 * 描述:
 *      字节数组合并工具类
 */

public class ArrayMergeUtil {

    /**
     * 将多个字节数组合并为一个字节数组
     * @param values
     * @return
     */
    public static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }
}

package com.example.dongdemo;

public class DataHead {

    String str;
    int leng;
    short m_nType;
    String m_nIndex;

    public DataHead(String str, int leng, short m_nType, String m_nIndex) {
        this.str = str;
        this.leng = leng;
        this.m_nType = m_nType;
        this.m_nIndex = m_nIndex;
    }


    public static DataHead getInstance(byte[] bytes){

        String str;
        int leng;
        short m_nType;
        String m_nIndex;

        byte[] strs=new byte[11];
        str=toStr(bytes,4);
        System.arraycopy(bytes,4,strs,0,4);
        leng=toInt(strs);
        System.arraycopy(bytes,8,strs,0,2);
        m_nType=toShort(strs);
        System.arraycopy(bytes,10,strs,0,1);
        m_nIndex=toStr(strs,1);

        return new DataHead(str,leng,m_nType,m_nIndex);

    }

    /*
    byte 转String
     */
    public static String toStr(byte[] valArr,int maxLen){
        int index=0;
        while (index<valArr.length&&index<maxLen){
            if (valArr[index]==0){
                break;
            }
            index++;
        }

        byte[] type=new byte[index];
        System.arraycopy(valArr,0,type,0,index);
        return new String(type);
    }

    /*
    byte 转 int
     */
    public static int toInt(byte[] bytes){
        int n=0;
        for (int i=0;i<bytes.length&&i<4;i++){
            int left=i*8;
            n+=(bytes[i]<<left);
        }
        return n;
    }

    /*
    byte 转 short
     */
    public static short toShort(byte[] bytes){
        return (short)(bytes[0]&0xff|(bytes[1]<<8&0xff00));
    }

}

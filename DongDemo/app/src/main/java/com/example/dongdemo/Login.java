package com.example.dongdemo;

public class Login {

    byte[] buf=new byte[156];

    String head;
    int length;
    short m_nType;
    String				m_nIndex;     	 //请求索引，与请求数据包一致   1个字节  00
    String				m_No;            //暂时不用 					1个字节 00
    long				m_lKey;		 	 //一级标识  					4个字节 03 00 00 00
    short				m_cCodeType;	 //证券类型 					2个字节 00  00
    String				m_cCode;		 //证券代码                     6个字节 00 00 00 00 00 00
    short     			m_nSize;         //请求证券总数                 2个字节 00 00
    short		m_nOption;       //为了4字节对齐而添加的字段    2个字节  00 00
    String			m_szUser;	     //用户名     64个字节  67 75 65 73 74 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
    String			m_szPWD;

    public Login(String head, int length, short m_nType, String m_nIndex,String m_No, long m_lKey, short m_cCodeType, String m_cCode, short m_nSize, short m_nOption, String m_szUser, String m_szPWD) {

        this.head = head;
        this.length = length;
        this.m_nType = m_nType;
        this.m_nIndex = m_nIndex;
        this.m_No = m_No;
        this.m_lKey = m_lKey;
        this.m_cCodeType = m_cCodeType;
        this.m_cCode = m_cCode;
        this.m_nSize = m_nSize;
        this.m_nOption = m_nOption;
        this.m_szUser = m_szUser;
        this.m_szPWD = m_szPWD;

        byte[] temp=head.getBytes();
        System.arraycopy(temp,0,buf,0,temp.length);
        temp=NumberUtil.getByteArray(length);
        System.arraycopy(temp,0,buf,4,temp.length);
        temp=NumberUtil.getByteArray(m_nType);
        System.arraycopy(temp,0,buf,8,temp.length);
        temp=m_nIndex.getBytes();
        System.arraycopy(temp,0,buf,10,temp.length);
        temp=m_No.getBytes();
        System.arraycopy(temp,0,buf,11,temp.length);
        temp=NumberUtil.getByteArray(m_lKey);
        System.arraycopy(temp,0,buf,12,temp.length);
        temp=NumberUtil.getByteArray(m_cCodeType);
        System.arraycopy(temp,0,buf,16,temp.length);
        temp=m_cCode.getBytes();
        System.arraycopy(temp,0,buf,18,temp.length);
        temp=NumberUtil.getByteArray(m_nSize);
        System.arraycopy(temp,0,buf,24,temp.length);
        temp=NumberUtil.getByteArray(m_nOption);
        System.arraycopy(temp,0,buf,26,temp.length);
        temp=m_szUser.getBytes();
        System.arraycopy(temp,0,buf,28,temp.length);
        temp=m_szPWD.getBytes();
        System.arraycopy(temp,0,buf,92,temp.length);

    }

    public byte[] getBuf(){
        return buf;
    }

}

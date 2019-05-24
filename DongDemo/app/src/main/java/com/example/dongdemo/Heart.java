package com.example.dongdemo;

public class Heart {

    String head;
    int length;
    short m_nType;
    String m_nIndex;
    String m_cOperator;

    byte[] buf=new byte[12];

    public Heart(String head, int length, short m_nType, String m_nIndex, String m_cOperator) {
        this.head = head;
        this.length = length;
        this.m_nType = m_nType;
        this.m_nIndex = m_nIndex;
        this.m_cOperator = m_cOperator;

        byte[] temp=head.getBytes();
        System.arraycopy(temp,0,buf,0,temp.length);
        temp=NumberUtil.getByteArray(length);
        System.arraycopy(temp,0,buf,4,temp.length);
        temp=NumberUtil.getByteArray(m_nType);
        System.arraycopy(temp,0,buf,8,temp.length);
        temp=m_nIndex.getBytes();
        System.arraycopy(temp,0,buf,10,temp.length);
        temp=m_cOperator.getBytes();
        System.arraycopy(temp,0,buf,11,temp.length);

    }

    public byte[] getBuf(){
        return buf;
    }

}

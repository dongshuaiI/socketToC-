package com.example.dongdemo;

/**
 * Created by M_genius on 2018/7/10.\
 * 描述:
 */

public class Constant {

    // 心跳包的数据类型
    public static final int HEART_BEAT_PACKAGE_TYPE = 1;

    // 数据包的数据类型
    public static final int DATA_PACKAGE_TYPE = 0x30;

    // 心跳包的数据内容，4个字节，一个4个，共占 16 字节
    public static final int HEART_BEAT_PACKAGE_DATA = 0xaaaaaaaa;

    // 网络请求请求头
    public static final int NET_HEADER_DATA = 0x55AA55AA;

    // 服务器 IP 地址 "172.16.7.49"
//    public static final String SERVER_IP = "172.16.7.49";
    public static final String SERVER_IP = "192.168.9.45";
//    public static final String SERVER_IP = "192.168.9.10";
//    public static final String SERVER_IP = "192.168.253.1";
//    public static final String SERVER_IP = "192.168.1.72";

    // 服务器端口号
    public static final int SERVER_PORT = 7778;

    //================================= 以下 数据包的数据格式 ==============================================

    // 标识比较类型
    public static final int ENUM_TYPE = 1;

    // 标识请求唯一性，一共 64 个字节 C++ char类型，64个的数组
    public static final int REQUEST_KEY = 1;

    // 传递 20 个字节的 0，表示 ID
    public static final int REQUEST_ID = 0;

    // 优先级，传int 0
    public static final int REQUEST_PRIORITY = 0;

    // 阈值
    public static final int REQUEST_THRESHOLD = 0;

    // 最大结果数目
    public static final int REQUEST_TOP_X = 1;

    // 图片的数量
    public static final int REQUEST_PIC_NUM = 1;

    // 图片中有几张脸
    public static final int REQUEST_FACE_COUNT = 0;

    // 50 个 为0的结构体，每个结构体4个int，16个字节，共80个字节
    public static final int REQUEST_STRUCT_UNIT = 0;


    //int nPicLen[2];    8个字节   //现场图片数据长度
    //TAG_RECT stuRects[
    //COMP_MAX_FACES]; 50结构体，每个结构体有4个int


}

package com.example.dongdemo;


import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by M_genius on 2018/7/12.\
 * 描述:
 */

public class Client {


    private static final String TAG = "Client";

    private byte[] allBytes;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private byte[] bytes;

    /**
     * 处理服务端发回的对象，可实现该接口。
     */
    public interface ObjectAction {
        void doAction(Object obj, Client client);
    }

    public static final class DefaultObjectAction implements ObjectAction {
        public void doAction(Object obj, Client client) {
            System.out.println("处理：\t" + obj.toString());
        }
    }


    private String serverIp;
    private int port;
    private Socket socket;
    private boolean running = false; //连接状态

    private long lastSendTime; //最后一次发送数据的时间

    //用于保存接收消息对象类型及该类型消息处理的对象
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<>();

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    /**
     * 创建 socket 连接，开启收发心跳包的线程
     */
    public void start() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    return;
                }

                socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(serverIp, port);

                // 设置连接超时时间
                try {
                    socket.connect(socketAddress, 5000);
                    mInputStream = socket.getInputStream();
                    mOutputStream = socket.getOutputStream();

                    Log.d(TAG, "run: 连接成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: 连接失败");
                }

                Log.d(TAG, "start: 本地端口：" + socket.getLocalPort());

                lastSendTime = System.currentTimeMillis();
                running = true;
                new Thread(new KeepAliveRunnable(), "发送线程").start();  //保持长连接的线程，每隔2秒项服务器发一个一个保持连接的心跳消息
                new Thread(new ReceiveRunnable(), "接收线程").start();    //接受消息的线程，处理消息

            }
        }, "入口线程").start();

    }

    public void stop() {
        if (running) {
            running = false;
        }
    }

    /**
     * 添加接收对象的处理对象。
     *
     * @param cls    待处理的对象，其所属的类。
     * @param action 处理过程对象。
     */
    public void addActionMap(Class<Object> cls, ObjectAction action) {
        actionMapping.put(cls, action);
    }

    /**
     * 发送心跳包到服务端
     *
     * @throws IOException
     */
    public void sendHeartBeatPackage() throws IOException {

        mOutputStream = socket.getOutputStream();

//        allBytes = ArrayMergeUtil.byteMergerAll(
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.NET_HEADER_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(16)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_TYPE)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt())
//        );


        try {
            mOutputStream.write(allBytes);

//            Log.d(TAG, "sendHeartBeatPackage: " + (allBytes.length));
//            // 分别获取指定字节段的数据
//            for (int i = 0; i < allBytes.length; i++) {
//                if (i % 4 == 0) {
//                    Log.d(TAG, "sendHeartBeatPackage: 解析字节数组" + Integer.toHexString(NumberUtil.getInt(allBytes, i)));
//                }
//            }
//            Log.d(TAG, "sendHeartBeatPackage: 发送数据");

            mOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送心跳包的 Runnable
     */
    class KeepAliveRunnable implements Runnable {

        long checkDelay = 10;
        long keepAliveDelay = 3000;

        public void run() {

            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {

                        sendHeartBeatPackage();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {

                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    /**
     * 接收数据的 Runnable
     */
    class ReceiveRunnable implements Runnable {

        public void run() {

            while (socket != null && socket.isConnected() && mInputStream != null) {
                try {

                    // 读取流
                    byte[] data = new byte[0];

                    // 缓存字节组
                    byte[] buf = new byte[1024];

                    int len;
                    try {
                        while ((len = mInputStream.read(buf)) != -1) {

                            //收到数据包的第int，即8~11个字节构成的整形等于 1，则该包是一个心跳包
                            if (ByteBuffer.wrap(NumberUtil.getByteArray(NumberUtil.getInt(buf, 8))).order(ByteOrder.LITTLE_ENDIAN).getInt() == 1) {
                                // 第二个字节表示数据长度
                                Log.d(TAG, "run: " + ByteBuffer.wrap(NumberUtil.getByteArray(NumberUtil.getInt(buf, 4))).order(ByteOrder.LITTLE_ENDIAN).getInt());
                                Log.d(TAG, "run: 收到一个心跳包");
                            } else {
                                Log.d(TAG, "run: 收到一个数据包");

                            }

//                                byte[] temp = new byte[data.length + len];
//
//                                // 深度复制，副本不改变原数据的状态
//                                System.arraycopy(data, 0, temp, 0, data.length);
//                                System.arraycopy(buf, 0, temp, data.length, len);
//
//                                data = temp;
//                                Log.d(TAG, "run: 正在读取数据:" + data.length);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }

                    // 处理流，在此处处理接收的字节数组
                    if (data.length != 0) {
//                            System.out.println("321");
                        Log.d(TAG, "run: 接收数据");
                    } else {
                        Log.d(TAG, "run: 接收数据,数据异常");
                    }

//                        } else {
//                            Log.d(TAG, "run: 输入流不可用");
//                            Thread.sleep(10);
//                        }

                    Log.d(TAG, "run: 接收线程执行一次");

                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }

            }
        }
    }

}


package com.example.dongdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public  static TextView tv;
    public static TextView av;
    static Handler handler;
    private static final String TAG = "MainActivity";
    
//    String serverIp = "61.147.117.163";//61.147.117.163:8881   .. 192.168.200.154
    String serverIp = "106.14.37.254";//106.14.37.254:8881
    int port = 8881;
    private SocketChannel socket;
    private boolean running = false;
    private long lastSendTime;
    String str;

    private byte[] allBytes;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private byte[] bytes;


    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;
    // 接收服务器发送过来的消息
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        av = (TextView) findViewById(R.id.av);

        Button button1=findViewById(R.id.button1);
        Button button2=findViewById(R.id.button2);

        // 初始化线程池
        mThreadPool = Executors.newCachedThreadPool();


        // 实例化主线程,用于更新接收过来的消息
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        tv.setText("登录信息"+response);
                        break;
                }
            }
        };


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click1();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click2();
            }
        });

    }

    private void click2() {

        new Thread(new KeepAliveRunnable(), "发送线程").start();

        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {

//                try {
                    // 步骤1：创建输入流对象InputStream
//                   InputStream is = socket.getInputStream();
//
//                   byte[] re=new byte[40];
////                   is.read(re);
////                    Log.e(TAG, "接收: "+Arrays.toString(re) );
//
//                    // 步骤2：创建输入流读取器对象 并传入输入流对象
//                    // 该对象作用：获取服务器返回的数据
//                   InputStreamReader isr = new InputStreamReader(is);
//                   BufferedReader br = new BufferedReader(isr);
//
//                    // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
//                    response = br.readLine();
//                    Log.e(TAG, "接收 "+response );
//
//                    // 步骤4:通知主线程,将接收的消息显示到界面
//                    Message msg = Message.obtain();
//                    msg.what = 0;
//                    mMainHandler.sendMessage(msg);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

    }

    private ExecutorService mThreadPool;
    private void click1() {

//      mThreadPool.execute(new  KeepAliveRunnable());
//        new Thread(new KeepAliveRunnable(), "发送线程").start();

        new Thread(new sendHeart()).start();

    }

    public static final String SERVER_IP = "192.168.9.45";
//    public static final String SERVER_IP = "192.168.9.10";
//    public static final String SERVER_IP = "192.168.253.1";
//    public static final String SERVER_IP = "192.168.1.72";

    // 服务器端口号
    public static final int SERVER_PORT = 7778;
    public void btnClick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                if (running) {
//                    return;
//                }

                Log.e(TAG, "run: 开始" );


                // 设置连接超时时间
                try {

                    socket = SocketChannel.open();
                    InetSocketAddress socketAddress = new InetSocketAddress(serverIp, port);
                    boolean connect = socket.connect(socketAddress);
//                    mInputStream = socket.getInputStream();
//                    mOutputStream = socket.getOutputStream();
                    byte [] in=new byte[40];
//                    mInputStream.read(in);
//
//                    BufferedInputStream bis = new BufferedInputStream(mInputStream);
//                    BufferedReader br = new BufferedReader(new InputStreamReader(bis));

//                    InputStreamReader isr = new InputStreamReader(mInputStream);
//                    BufferedReader br = new BufferedReader(isr);
//                    String s = br.readLine();
//                    Log.e(TAG, "run: "+ s);//接收数据
//
//                BufferedReader   reader = new BufferedReader(new InputStreamReader(mInputStream,"GBK"));
//                char[] bt = new char[40];
//                do  {
//                    if ((reader.read(bt))!=-1){
////                        System.out.println(data);
//                        Log.e(TAG, "inputfirst0 "+Arrays.toString(bt) );
//                    }
//                }while (reader.ready());
//                    socket.shutdownInput();
//                    System.out.println("读取完毕");
//                    Log.e(TAG, "inputfirst "+Arrays.toString(in) );
//
//                    BufferedReader br=new BufferedReader(new InputStreamReader(mInputStream));
//                    String line=br.readLine();
//                    Log.e(TAG, "run: "+line );


                    Log.d(TAG, "run: 连接成功"+connect);

                    if (connect){
                        new Thread(new KeepAliveRunnable(), "发送线程").start();
//                        new Thread(new ReceiveRunnable(), "接收线程").start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: 连接失败");
                }

//                Log.d(TAG, "start: 本地端口：" + socket.getLocalPort());

                lastSendTime = System.currentTimeMillis();
                running = true;
//                new Thread(new sendHeart()).start();
//                new Thread(new KeepAliveRunnable(), "发送线程").start();  //保持长连接的线程，每隔2秒项服务器发一个一个保持连接的心跳消息
//                try {
//                    Thread.sleep(10000);
//                    new Thread(new ReceiveRunnable(), "接收线程").start();    //接受消息的线程，处理消息
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


            }
        }, "入口线程").start();
        
    }


    /**
     * 发送登录包的 Runnable
     */
    class KeepAliveRunnable implements Runnable {

        long checkDelay = 10;
        long keepAliveDelay = 3000;

        public void run() {

//            while (running) {
//                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {

                        sendLoginPackage();

                    } catch (IOException e) {
                        e.printStackTrace();
                        stop();
                    }
                    lastSendTime = System.currentTimeMillis();
//                } else {

//                    try {
//                        Thread.sleep(checkDelay);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        stop();
//                    }
//                }
            }
        }
//    }

    private void sendLoginPackage() throws IOException {

//        mOutputStream = socket.getOutputStream();

//        allBytes = ArrayMergeUtil.byteMergerAll(
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.NET_HEADER_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(16)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_TYPE)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt())
//        );
        Login login = new Login("ZJHR\0", 148,
                (short) 0x0102, "\0", "\0", 3L, (short) 0, "\0",
                (short) 0, (short) 0, "guest\0", "123456\0");
        allBytes=login.getBuf();

        String sj="ZJHR";
        byte[] bytes = sj.getBytes();
        Log.e(TAG, "ZJHR: " +Arrays.toString(bytes));

        byte[] a={90, 74, 72, 82, 94, 0, 0, 0, 2, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 103, 117, 101, 115, 116, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                49, 50, 51, 52, 53, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //[90, 74, 72, 82,
        // -108,0, 0, 0,
        // 32,1,
        // 0,
        // 0,
        // 3,0,0, 0,
        // 0, 0,
        // 0, 0, 0, 0, 0,0,
        // 0, 0,
        // 0, 0,
        // 103, 117, 101, 115, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 49, 50, 51, 52, 53, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

        String s = Arrays.toString(allBytes);
        Log.e(TAG, "sendLoginPackage: "+ s);
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        if (allBytes!=null&&allBytes.length>0){
            byteBuffer=ByteBuffer.wrap(allBytes);
            socket.write(byteBuffer);
            byteBuffer.flip();
        }

        ByteBuffer buf = ByteBuffer.allocate(11);
        int i = 0;
        if (socket != null)
            i = socket.read(buf);
        Log.e("TAG", "i000: "+i );
        buf.flip();
        if (i>0){
            byte[] array = buf.array();
            Log.e("TAG", "array: "+Arrays.toString(array) );

            DataHead instance = DataHead.getInstance(array);
            response="str:"+instance.str+"\t\tleng:"+instance.leng+"\t\tm_nType"
                    +instance.m_nType;//+"\t\tm_nIndex:"+instance.m_nIndex
//            response=Arrays.toString(array);
            Message msg = Message.obtain();
                    msg.what = 0;
                    mMainHandler.sendMessage(msg);

        }

//        try {
//            mOutputStream.write(allBytes);
//            Log.d(TAG, "sendHeartBeatPackage: " + (allBytes.length));
//            // 分别获取指定字节段的数据
//            for (int i = 0; i < allBytes.length; i++) {
//                if (i % 4 == 0) {
//                    Log.d(TAG, "sendHeartBeatPackage: 解析字节数组" + Integer.toHexString(NumberUtil.getInt(allBytes, i)));
//                }
//            }
//            Log.d(TAG, "sendHeartBeatPackage: 发送数据");
//            mOutputStream.flush();
//
//            byte[] receive=new byte[13];
//            socket.getInputStream().read(receive);
//            Log.e(TAG, "sendLoginPackage: "+Arrays.toString(receive) );
//
//            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line=br.readLine();
//            Log.e(TAG, "run: "+line );
//
//            new Thread(new sendHeart()).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    class sendHeart implements Runnable {

        @Override
        public void run() {
            try {
//                while (running){
//                    Thread.sleep(10000);
//                    mOutputStream = socket.getOutputStream();
//
//        allBytes = ArrayMergeUtil.byteMergerAll(
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.NET_HEADER_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(16)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_TYPE)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt()),
//                NumberUtil.getByteArray(ByteBuffer.wrap(NumberUtil.getByteArray(Constant.HEART_BEAT_PACKAGE_DATA)).order(ByteOrder.LITTLE_ENDIAN).getInt())
//        );
                    Heart login = new Heart("ZJHR\0", 4, (short) 0x0905, "\0", "\0");
                    allBytes=login.getBuf();
                    Log.e("TAG", "runheart: "+Arrays.toString(allBytes) );
                    byte[] d= {90};
                    ByteBuffer byteBuffer=ByteBuffer.allocate(12);
                    if (allBytes!=null&&allBytes.length>0){
                        byteBuffer=ByteBuffer.wrap(allBytes);
                        socket.write(byteBuffer);
                        byteBuffer.flip();
                    }


                    ByteBuffer buf = ByteBuffer.allocate(12);
                    int i = 0;
                    if (socket != null)
                        i = socket.read(buf);
                    Log.e("TAGHeart", "i000: "+i );
                    buf.flip();
                    if (i>0){
                        final byte[] array = buf.array();
                        Log.e("TAG", "array: "+Arrays.toString(array) );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                av.setText("心跳包返回:"+Arrays.toString(array));
                                Toast.makeText(MainActivity.this,"接收到心跳包",Toast.LENGTH_LONG).show();
                            }
                        });

                    }

//                    try {
//                        mOutputStream.write(allBytes);
//            Log.d(TAG, "sendHeartBeatPackage: " + (allBytes.length));
//            // 分别获取指定字节段的数据
//            for (int i = 0; i < allBytes.length; i++) {
//                if (i % 4 == 0) {
//                    Log.d(TAG, "sendHeartBeatPackage: 解析字节数组" + Integer.toHexString(NumberUtil.getInt(allBytes, i)));
//                }
//            }
//            Log.d(TAG, "sendHeartBeatPackage: 发送数据");
//                        byte[] heRe=new byte[1024];
//                        int read = socket.getInputStream().read(heRe);
//                        Log.e(TAG, "run: "+Arrays.toString(heRe) );
//
//                        mOutputStream.flush();



//                    BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String line=br.readLine();
//                    Log.e(TAG, "run: "+line );
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }



            } catch (Exception e) {
                e.printStackTrace();
                stop();
            }
        }
    }

    /**
     * 接收数据的 Runnable
     */
    class ReceiveRunnable implements Runnable {

        public void run() {

            while (socket != null && socket.isConnected() ) {
                try {


                    ByteBuffer buf = ByteBuffer.allocate(11);
                    int i = 0;
                    if (socket != null)
                        i = socket.read(buf);
//                    Log.e(TAG, "i: "+i );
                    buf.flip();
                    if (i>0){
                        final byte[] array = buf.array();
                        Log.e(TAG, "array: "+Arrays.toString(array) );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"接收字符了"+Arrays.toString(array),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    // 读取流
                    byte[] data = new byte[0];

                    // 缓存字节组
//                    byte[] buf = new byte[1024];

                    int len;
                    try {
//                        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        String line=br.readLine();
//                        Log.e(TAG, "run: "+line );


//                        InputStream in = socket.getInputStream();
//                        if(in.available()>0){
//                            ObjectInputStream ois = new ObjectInputStream(in);
//                            Object obj = ois.readObject();
//                            System.out.println("接收：\t"+obj);
//
//                        }
//                        br.close();
//
//                        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        String line=br.readLine();
//                        Log.e(TAG, "run: "+line );
//                        while ((len = socket.getInputStream().read(buf)) != -1) {
//
//                            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                            String line=br.readLine();
//                            Log.e(TAG, "run: "+line ); 192.168.1.120
//
//                            //收到数据包的第int，即8~11个字节构成的整形等于 1，则该包是一个心跳包
//                            if (ByteBuffer.wrap(NumberUtil.getByteArray(NumberUtil.getInt(buf, 8))).order(ByteOrder.LITTLE_ENDIAN).getInt() == 1) {
//                                // 第二个字节表示数据长度
//                                Log.d(TAG, "run: " + ByteBuffer.wrap(NumberUtil.getByteArray(NumberUtil.getInt(buf, 4))).order(ByteOrder.LITTLE_ENDIAN).getInt());
//                                Log.d(TAG, "run: 收到一个心跳包");
//                            } else {
//                                Log.d(TAG, "run: 收到一个数据包");
//
//                            }
////
////                                byte[] temp = new byte[data.length + len];
////
////                                // 深度复制，副本不改变原数据的状态
////                                System.arraycopy(data, 0, temp, 0, data.length);
////                                System.arraycopy(buf, 0, temp, data.length, len);
////
////                                data = temp;
////                                Log.d(TAG, "run: 正在读取数据:" + data.length);
//                        }
                    } catch (Exception e){
                        Log.d(TAG, "run: 接收数据,数据异常2");
                    }

                    // 处理流，在此处处理接收的字节数组
//                    if (data.length != 0) {
////                            System.out.println("321");
//                        Log.d(TAG, "run: 接收数据");
//                    } else {
//                        Log.d(TAG, "run: 接收数据,数据异常");
//                    }
//
//                        } else {
//                            Log.d(TAG, "run: 输入流不可用");
//                            Thread.sleep(10);
//                        }

//                    Log.d(TAG, "run: 接收线程执行一次");

                } catch (Exception e) {
                    e.printStackTrace();
                    stop();
                }

            }
        }
    }

    public void stop() {
        if (running) {
            running = false;
        }
    }

}

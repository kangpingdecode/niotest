package com.kangping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <p>
 * 功能： 客户端
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: NioClient
 * @date 2020/6/23
 */

public class NioClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put("hello, i am nio 我 client".getBytes());
        byteBuffer.flip();
        //socketChannel.write(byteBuffer);
        while(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer);
            socketChannel.write(byteBuffer);
        }

        byteBuffer.clear();
        int read = socketChannel.read(byteBuffer);
        if (read > 0) {
            System.out.println(new String(byteBuffer.array()));
        } else {
            System.out.println("没有接收到服务端的数据");
        }

    }
}

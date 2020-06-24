package com.kangping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * <p>
 * 功能： 服务端
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: NioService
 * @date 2020/6/23
 */

public class NioService {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // nio 默认也为阻塞，设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        while (true) {
            SocketChannel socketChannel= serverSocketChannel.accept();
            if (socketChannel != null) {
                // 读取数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                socketChannel.read(byteBuffer);
                System.out.println("接收到客户端发送来的数据："+new String(byteBuffer.array()));

                // 指针重置
                byteBuffer.flip();

                //回写数据
                socketChannel.write(byteBuffer);

            } else {
                System.out.println("暂时没有客户端连接");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }




    }
}

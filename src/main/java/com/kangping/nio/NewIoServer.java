package com.kangping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * 功能：
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: NewIoServer
 * @date 2020/6/23
 */

public class NewIoServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // nio 默认也为阻塞，设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        // 监听客户端发来的连接事件,(当客户端 socketChannel.connect(new InetSocketAddress("localhost", 8080))会触发)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 监听事件，没有事件，未阻塞状态
            selector.select();
            // 可能有多个事件
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 连接事件，(当客户端 socketChannel.connect(new InetSocketAddress("localhost", 8080))会触发)
                if ( selectionKey.isAcceptable()) {
                    handleAccept(selector,selectionKey);
                }
                // 读事件
                if (selectionKey.isReadable()) {
                    handleRead(selector, selectionKey);
                }
                // 处理完删除，防止重复处理
                iterator.remove();
            }

        }

    }

    private static void handleAccept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.write(ByteBuffer.wrap("接收到了客户端请求".getBytes()));
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private static void handleRead(Selector selector, SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}

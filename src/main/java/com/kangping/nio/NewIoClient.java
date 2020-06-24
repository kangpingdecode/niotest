package com.kangping.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
 * @ClassName: NewIOClient
 * @date 2020/6/23
 */

public class NewIoClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        // 打开选择器
        Selector selector = Selector.open();
        // 监听服务端接受的连接事件（当服务端serverSocketChannel.accept();的时候，会触发这个事件）
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        while (true) {
            // 监听事件，没有事件，未阻塞状态
            selector.select();
            // 可能有多个事件，客户端一般只有一个
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 连接事件（当服务端serverSocketChannel.accept();的时候，会触发这个事件）
                if (selectionKey.isConnectable()) {
                    handleConnect(selector, selectionKey);
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
    private static void handleConnect(Selector selector, SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        // 完成连接，后我们就可以向服务端发送数据
        socketChannel.write(ByteBuffer.wrap("我是客户端".getBytes()));
        // 同时注册读事件，这样服务端发送数据过来，selector可以监听到
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private static void handleRead(Selector selector, SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
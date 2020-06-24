package com.kangping.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * <p>
 * 功能：
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: BioNewServer
 * @date 2020/6/24
 */

public class BioNewServer {

    static ExecutorService executorService = newFixedThreadPool(20);

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        //监听8080端口
        try {
            serverSocket = new ServerSocket(8080);
            while (true){
                Socket socket = serverSocket.accept();
                //异步
                executorService.execute(new SocketThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.kangping.nio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * 功能：
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: BioServer
 * @date 2020/6/24
 */

public class BioServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            //监听8080端口
            serverSocket = new ServerSocket(8080);
            //阻塞等待客户端连接（连接阻塞）
            Socket socket = serverSocket.accept();
            //通过InputStream()接收数据
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("接收到客户端的消息 ： " + in.readLine());
            //写回去，通过OutputStream()发送数据

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //通过\n告诉接收端消息发送结束
            out.write("服务端回复的消息\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                out.close();
                in.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

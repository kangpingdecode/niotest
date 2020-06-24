package com.kangping.nio;

import java.io.*;
import java.net.Socket;

/**
 * <p>
 * 功能：
 * </p>
 *
 * @author kangping
 * Copyright Inc. All rights reserved
 * @version v1.0
 * @ClassName: BioClient
 * @date 2020/6/24
 */

public class BioClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            socket = new Socket("localhost", 8080);
            //发送消息到服务端
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Thread.sleep(100000);
            //通过\n告诉接收端消息发送结束
            out.write("我是客户端发送了一个消息\n");
            out.flush();
            //接收来自服务端的消息
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("来自服务端的消息：" + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

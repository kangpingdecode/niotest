package com.kangping.nio;

import java.io.*;
import java.net.Socket;

public class SocketThread implements Runnable {
    private Socket socket;
    public SocketThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
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
        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

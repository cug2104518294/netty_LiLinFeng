package com.phei.netty.pio;

import com.phei.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 伪异步  其实就是将单个线程的处理任务改成是使用线程池的方式来处理
 * 但是其底层还是BIO 同步处理方式  只不过是在优化线程池
 * */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }

        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(
                    50, 10000);// 创建IO任务线程池
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}

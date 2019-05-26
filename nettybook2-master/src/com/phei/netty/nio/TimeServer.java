
package com.phei.netty.nio;

import java.io.IOException;

/**
 * NIO
 * 缓冲区 bytebuffer  io是面向流操作的
 * 通道 channel 双向  全双工 更好的映射unix系统底层模型 同时支持读写操作  io是单向的 只能inputstream或者是outstream的子实例  因此只能是单向的
 * 主体是可以分为用于网络读写的SelectableChannel和用于文件操作的FileChannel  socketchannel和ServersocketChannel都是selectablechannel的子类
 * 多路复用器  提供选择已经就绪的任务的能力  selector会不断轮询注册在其上面的channel
 * 如果某个Channel上面发生读或者写事件  这个channel就处于就绪状态 会被selector轮询出来 然后通过
 * Selectionkey可以获得就绪的channel的集合 进行后续的io操作
 * 一个多路复用器selector同时轮询多个channel  由于jdk使用了epoll()代替传统的select实现 所以并没有
 * 最大句柄1024/2048的限制 这也就意味着只要一个线程selector的轮询就可以接入成千上万的客户端
 */

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
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}

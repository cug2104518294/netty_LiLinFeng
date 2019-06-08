package com.phei.netty.protocol.http.httpServer.httpTest01;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;

/***
 * http://127.0.0.1:8080/302  就可以
 * https://127.0.0.1:8080/302  需要在handler中添加ssl处理逻辑才可以是用https的加密请求
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    private String content = "hello world";

    private final static String LOC = "302";
    private final static String NOT_FOND = "404";
    private final static String BAD_REQUEST = "400";
    private final static String INTERNAL_SERVER_ERROR = "500";
    private static Map<String, HttpResponseStatus> mapStatus = new HashMap<>();

    static {
        mapStatus.put(LOC, HttpResponseStatus.FOUND);
        mapStatus.put(NOT_FOND, HttpResponseStatus.NOT_FOUND);
        mapStatus.put(BAD_REQUEST, HttpResponseStatus.BAD_REQUEST);
        mapStatus.put(INTERNAL_SERVER_ERROR, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            // boolean keepaLive = HttpUtil.isKeepAlive(request);
            boolean keepaLive = isKeepAlive(request);
            System.out.println("keepaLive: " + keepaLive);
            System.out.println("method:" + request.method());
            System.out.println("uri:" + request.uri());
            String uri = request.uri().replace("/", "").trim();
            System.out.println("trim_uri:" + uri);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            if (mapStatus.get(uri) != null) {
                httpResponse.setStatus(mapStatus.get(uri));
                httpResponse.content().writeBytes(mapStatus.get(uri).toString().getBytes());
            } else {
                httpResponse.content().writeBytes(content.getBytes());
            }
            //重定向处理
            if (httpResponse.status().equals(HttpResponseStatus.FOUND)) {
                httpResponse.headers().set(HttpHeaderNames.LOCATION, "https://www.baidu.com/");
            }
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());
            if (keepaLive) {
                httpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.writeAndFlush(httpResponse);
            } else {
                ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}

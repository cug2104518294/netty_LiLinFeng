package com.phei.netty.protocol.http.xml.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

/**
 * @description:
 * @author: lvzm.lv@dji.com
 * @create: 2019-05-20 11:57
 **/
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<FullHttpResponse> {
    public HttpJsonResponseDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) {
        System.out.println("开始解码...");
        out.add(new HttpJsonResponse(msg, decode0(ctx, msg.content())));
    }
}
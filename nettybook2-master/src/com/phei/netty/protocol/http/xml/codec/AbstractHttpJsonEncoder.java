package com.phei.netty.protocol.http.xml.codec;

import com.phei.netty.protocol.http.xml.Utils.FastJsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.Charset;

public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {
    final static Charset UTF_8 = Charset.forName("utf-8");

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) {
        String jsonStr = FastJsonUtils.convertObjectToJSON(body);
        ByteBuf encodeBuf = Unpooled.copiedBuffer(jsonStr, UTF_8);
        return encodeBuf;
    }

}
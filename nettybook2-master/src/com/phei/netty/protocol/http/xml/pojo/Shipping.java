package com.phei.netty.protocol.http.xml.pojo;

/**
 * 枚举类型如下：
 　　普通邮寄
 　　宅急送
 　　国际邮递
 　　国内快递
 　　国际快递
 */
public enum Shipping {
    STANDARD_MAIL, PRIORITY_MAIL, INTERNATIONAL_MAIL, DOMESTIC_EXPRESS, INTERNATIONAL_EXPRESS
}
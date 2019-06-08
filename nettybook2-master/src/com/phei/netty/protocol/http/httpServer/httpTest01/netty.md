# 概述

HTTP(超文本传输协议）是建立在TCP传输协议之上的应用层协议，由于其简单、灵活，其应用也非常之广泛。本文主要讲解如何用Netty 实现一个简单的http服务器。

# http 请求消息

**Http的请求由三部分组成：请求行、消息头、请求正文（body）。 **
请求行以一个方法开头，以空格分开，后面跟着请求URI和协议版本，格式为：Method（方法） Request-URI （统一资源标识符） HTTP-version（请求版本） CRLF（换行符）。

GET / HTTP/1.1
POST / HTTP/1.1

请求方法有多种，各种方法如下：

GET 请求获取Request-URI 所标识的资源。
POST 在Request-URI 所标识的资源后附加新的提交数据
HEAD 请求获取由Request-URI 所标识的资源相应消息报头
PUT 请求服务器存储一个资源，并用Request-URI作为其标识。
DELETE 请求服务器删除Request-URI 所标识的资源。
TRACE:请求服务器回收到的请求信息，主要用于测试或者诊断
CONNECT 保留将来使用
OPTIONS 请求查询服务器性能或者查询与资源相关的选项需求。
下面是一个http 请求消息（没有请求正文），请求行后面都是请求头。

GET / HTTP/1.1
Host: 127.0.0.1:8080
Connection: keep-alive
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.8


# http 响应消息

**HTTP响应消息也是由3部分组成，分别是：状态行、消息报头、响应正文。 **
状态行的格式为： HTTP-version Status-Code Reason-Phrase CRLF. 
具体如下

HTTP/1.1 200 OK
HTTP/1.1 404 Not Found

状态码由三位数字组成，第一个数字表示响应类别，有五种可能取值。

1xx：指示信息。表示请求已经接受，继续处理。
2xx ：成功。表示请求已经被成功接收，理解、接受
3xx ：重定向。要完成请求必须进行进一步的操作。
4xx：客户端错误，请求有语法错误，或者请求无法完成。
5xx：服务器错误。服务器未能处理。


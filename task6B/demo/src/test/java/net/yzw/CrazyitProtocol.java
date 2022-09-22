package net.yzw;

public interface CrazyitProtocol {
    //定义协议字符串的长度
    int PROTOCOL_LEN = 2;
    //下面是一些协议字符串，服务器端和客户端交换的信息都应该在前、后添加这种特殊字符串
    String MSG_ROUND = "#)";
    String USER_ROUND = "&)";//客户端发送username信息应该标识的前缀和后缀
    String NAME_REP = "-1";//标志名字重复
    String LOGIN_SUCCESS = "+1";//表明客户端用户登录成功，没有出现名字重复
    String PRIVATE_ROUND = "$%";//表明发送的是私聊消息
    String SPLIT_SIGN = "~";
    
}

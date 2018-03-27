package com.test.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//用户权限验证拦截器
public class CheckUserInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
        
//    输入拦截器链有如下几个阶段，这些阶段按照在拦截器链中的先后顺序排列。

//    阶段名称    阶段功能描述
//    RECEIVE Transport level processing(接收阶段，传输层处理)
//    (PRE/USER/POST)_STREAM  Stream level processing/transformations(流处理/转换阶段)
//    READ    This is where header reading typically occurs(SOAPHeader读取)
//    (PRE/USER/POST)_PROTOCOL    Protocol processing, such as JAX-WS SOAP handlers(协议处理阶段，例如JAX-WS的Handler处理)
//    UNMARSHAL   Unmarshalling of the request(SOAP请求解码阶段)
//    (PRE/USER/POST)_LOGICAL Processing of the umarshalled request(SOAP请求解码处理阶段)
//    PRE_INVOKE  Pre invocation actions(调用业务处理之前进入该阶段)
//    INVOKE  Invocation of the service(调用业务阶段)
//    POST_INVOKE Invocation of the outgoing chain if there is one(提交业务处理结果，并触发输入连接器)
//
//    输出拦截器链有如下几个阶段，这些阶段按照在拦截器链中的先后顺序排列。
//
//    阶段名称    阶段功能描述
//    SETUP   Any set up for the following phases(设置阶段)
//    (PRE/USER/POST)_LOGICAL Processing of objects about to marshalled
//    PREPARE_SEND    Opening of the connection(消息发送准备阶段，在该阶段创建Connection)
//    PRE_STREAM  流准备阶段
//    PRE_PROTOCOL    Misc protocol actions(协议准备阶段)
//    WRITE   Writing of the protocol message, such as the SOAP Envelope.(写消息阶段)
//    MARSHAL Marshalling of the objects
//    (USER/POST)_PROTOCOL    Processing of the protocol message
//    (USER/POST)_STREAM  Processing of the byte level message(字节处理阶段，在该阶段把消息转为字节)
//    SEND    消息发送
    
    //拦截时机
    public CheckUserInterceptor() {
        super(Phase.PRE_INVOKE);
    }
    
    /** 
     * 自定义拦截器需要实现handleMessage方法，该方法抛出Fault异常，可以自定义异常集成自Fault， 
     * 也可以new Fault(new Throwable()) 
     */ 
    public void handleMessage(SoapMessage soap) throws Fault {
        
        System.out.println("开始验证用户信息");
        List<Header> headers = soap.getHeaders();
        
        //检查headers是否存在
        if(headers==null || headers.size()<1) {
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
        }
        Header header = headers.get(0);  
        Element el = (Element)header.getObject();  
        NodeList users = el.getElementsByTagName("username");  
        NodeList passwords = el.getElementsByTagName("password");
        
        //检查是否有用户名和密码元素  
        if(users.getLength()<1){  
            throw new Fault(new IllegalArgumentException("找不到用户信息"));  
        }  
        String username = users.item(0).getTextContent().trim();  
          
        if(passwords.getLength()<1){  
            throw new Fault(new IllegalArgumentException("找不到密码信息"));  
        }  
        String password = passwords.item(0).getTextContent();  
          
        //检查用户名和密码是否正确  
        if(!"admin".equals(username) || !"admin".equals(password)){  
            throw new Fault(new IllegalArgumentException("用户名或密码不正确"));  
        }else{  
            System.out.println("用户名密码正确允许访问");  
        }  
        
    }

}

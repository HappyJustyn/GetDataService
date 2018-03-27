package com.test.service;

import javax.jws.WebService;

import com.test.util.Tools;

@WebService
public class GetDataImpl implements GetData {

    @Override
    public String getData() {
        System.out.println("服务端：接口连接成功");
        return "status:1"+"\n"+"msg:连接成功";
    }

    @Override
    public void transferXML(String name) {
        System.out.println("接口连接成功");
    }
    
    /**
     * 解析xml字符串
     * @param data 传递的xml字符串
     * @return 返回结果
     * @throws Exception 
     */
    @Override
    public String parseXML(String data) {
        try {
            Tools.parserXml(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "success";
    }
    
    

}

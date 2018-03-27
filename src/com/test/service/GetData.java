package com.test.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * 用于获取数据的接口
 */

@WebService
public interface GetData {
    
    @WebMethod
    public String getData();
    @WebMethod
    public void transferXML(String name);
    @WebMethod
    public String parseXML(String data);
    
}

package net.goutalk.fowit.net;

import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Domain;

public class Url {

    @DefaultDomain()
    public static String baseUrl = "http://www.51wit.cn";//https://api.youchengchefu.com/
   // @DefaultDomain()
    //public static String baseUrl = "https://api.youchengchefu.com/";//c/
    @Domain(name = "test")
    public static String update = "http://www.baidu.com/";

}
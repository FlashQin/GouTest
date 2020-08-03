package net.goutalk.fowit.net;



import android.content.Intent;

import com.blankj.utilcode.util.Utils;

import net.goutalk.fowit.Activity.LoginUserActivity;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.parse.SimpleParser;

/**
 * token 失效，自动刷新token，然后再次发送请求，用户无感知
 * User: ljx
 * Date: 2019-12-04
 * Time: 11:56
 */
public class TokenInterceptor implements Interceptor {

    //token刷新时间
    private static volatile long SESSION_KEY_REFRESH_TIME = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
       Response originalResponse = chain.proceed(request);
//        String code = originalResponse.header("token_code");
//        if (!"0".equals(code)) { //token 失效  1、这里根据自己的业务需求写判断条件
//          //  return handleTokenInvalid(chain, request);
//
//            Intent intent=new Intent();
//            intent.setClass(Utils.getApp(), LoginUserActivity.class);
//            Utils.getApp() .startActivity(intent);
//
//        }
        return originalResponse;
    }


}

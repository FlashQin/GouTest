package net.goutalk.fowit.net;

import com.blankj.utilcode.util.SPUtils;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.orhanobut.logger.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.platform.Platform;
import rxhttp.wrapper.param.DeleteRequest;
import rxhttp.wrapper.param.GetRequest;
import rxhttp.wrapper.param.PostRequest;
import rxhttp.wrapper.param.PutRequest;
import rxhttp.wrapper.param.RxHttp;


public class NetWorkUtils {

    private static OkHttpClient okHttpClient;

    public static void initNetWork() {
        //Cache cache = new Cache(Utils.getApp().getCacheDir(), 10 * 1024 * 1024);
        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
              // .cache(cache)
                .followRedirects(true)

                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(chain -> {
                    Request request = chain.request();

//                 if (request.url().toString().contains("app/mallGoods/list")){
//                        int maxStale = 10* 60; // 离线时缓存保存2天,单位:秒
//                        CacheControl tempCacheControl = new CacheControl.Builder()
//                                .onlyIfCached()
//                                .maxStale(maxStale, TimeUnit.SECONDS)
//                                .build();
//                        request = request.newBuilder()
//                                .cacheControl(tempCacheControl)
//                                .build();}
                    return chain.proceed(request);
                })
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
//                        .addHeader("Authorization", " Bearer " + UseLoginInfo.getAccess_token())
                        .build())
                .build();

        RxHttp.init(okHttpClient, true);
        RxHttp.setOnParamAssembly(param -> {
            //根据不同请求添加不同参数
            if (param instanceof GetRequest) {

            } else if (param instanceof PostRequest) {

            } else if (param instanceof PutRequest) {

            } else if (param instanceof DeleteRequest) {

            }
            String token = SPUtils.getInstance().getString("TOKEN", "");
            if (!token.equals("")) {
                return param.addHeader("Authorization", "Bearer "+token)
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
//                        .addHeader("Connection", "keep-alive")
                        .addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            } else {
                return param.addHeader("Content-Type", "application/json;charset=UTF-8")
//                        .addHeader("Connection", "keep-alive")
                        .addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            }
        });
    }

    //信任所有的服务器,返回true
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}

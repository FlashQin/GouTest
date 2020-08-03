package net.goutalk.fowit.net;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
       // int code=chain.request().body().s
        // try the request
//        Response originalResponse = chain.proceed(request);
//
//        /**通过如下的办法曲线取到请求完成的数据
//         *
//         * 原本想通过  originalResponse.body().string()
//         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
//         *
//         * 然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
//         */
//        ResponseBody responseBody = originalResponse.body();
//        BufferedSource source = responseBody.source();
//        source.request(Long.MAX_VALUE); // Buffer the entire body.
//        Buffer buffer = source.buffer();
//        Charset charset = UTF8;
//        MediaType contentType = responseBody.contentType();
//        if (contentType != null) {
//            charset = contentType.charset(UTF8);
//        }
//
//        String bodyString = buffer.clone().readString(charset);
//        BaseMsgBean baseMsgBean = (BaseMsgBean) new Gson().fromJson(bodyString, BaseMsgBean.class);
//        if (baseMsgBean.getStatus()==5){
//            Run.onUiAsync(() -> {
//                ACache.get(Utils.getApp()).remove("USER_BEAN");
//                SPUtils.getInstance().remove("TOKEN");
//            });
//            Intent intent = new Intent();
//            intent.setClass(Utils.getApp(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Utils.getApp() .startActivity(intent);
//        }


//        if (useLoginInfo==null||useLoginInfo.getData()==null){
//            Logger.i("当前尚未登录");
////            Utils.getApp().startActivity(new Intent(Utils.getApp(),LoginActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_CLEAR_TASK));
//            return chain.proceed(request);
//        }
//        if (!useLoginInfo.getData().getAccess_token().equals("")) {
//            Request.Builder builder = request.newBuilder();
//            builder.addHeader("Authorization", " Bearer "+ useLoginInfo.getData().getAccess_token());
//            request = builder.build();
//        }else{
//            ToastUtils.showShort("您尚未登录");
//            Utils.getApp().startActivity(new Intent(Utils.getApp(), LoginActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_CLEAR_TASK));
//        }
        return chain.proceed(request);
    }
}

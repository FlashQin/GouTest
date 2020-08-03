package net.goutalk.fowit.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bytedance.sdk.adnet.face.IHttpStack;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTCustomController;
import com.cmcm.cmgame.CmGameSdk;
import com.cmcm.cmgame.gamedata.CmGameAppInfo;
import com.coder.zzq.smartshow.core.SmartShow;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.zzhoujay.richtext.RichText;

import net.goutalk.fowit.net.NetWorkUtils;
import net.goutalk.fowit.utils.CmGameImageLoader;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.TTAdManagerHolder;

import java.util.Objects;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.plugins.RxJavaPlugins;


/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/22
 * @modified 20/02/22
 * @description com.haichuang.read.app
 */
public class app extends Application {

    public Context mContext;


    static {//使用static代码段可以防止内存泄漏

        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
//                layout.autoRefresh();
                layout.setEnableLoadMore(true);
                layout.setEnableFooterTranslationContent(true);
                layout.setEnableHeaderTranslationContent(true);
                layout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
                layout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
            }
        });

        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
                layout.setEnableHeaderTranslationContent(true);
                return new ClassicsHeader(context);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableFooterTranslationContent(true);
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mContext = this;
        //refWatcher = LeakCanary.install(this);
        RichText.initCacheDir(this);
        RichText.debugMode = true;
        //logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        //RxJava全局报错捕获
        RxJavaPlugins.setErrorHandler(throwable -> Log.e("err", Objects.requireNonNull(throwable.getMessage())));
        //UtilCode初始化
        Utils.init(this);
        //初始化RxHttp
        NetWorkUtils.initNetWork();

        //smartshow
        SmartShow.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "cdfad3017a", false);
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();
        initChuan();
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
              //  Toast.makeText(app.this, "初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
              //  Toast.makeText(app.this, "初始化失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initChuan(){
        TTAdManagerHolder.init(this);

        game();


    }
    public void game(){



        CmGameAppInfo cmGameAppInfo = new CmGameAppInfo();
        cmGameAppInfo.setAppId("gouzhuan");                             // GameSdkID，向我方申请
        cmGameAppInfo.setAppHost("https://gz-xyx-big-svc.beike.cn");   // 游戏host地址，向我方申请
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行


        // 【设置穿山甲广告id】不需要展示广告，则不设置广告ID
        CmGameAppInfo.TTInfo ttInfo = new CmGameAppInfo.TTInfo();
        // 游戏内广告场景
        ttInfo.setRewardVideoId(CommonUtils.mVerticalCodeId);   // 激励视频
        ttInfo.setFullVideoId(CommonUtils.mfullvideo);     // 全屏视频，插屏场景下展示
        ttInfo.setGameEndExpressFeedAdId(CommonUtils.mgameWindowvideo); // 游戏退出弹框，信息流广告，模板渲染
        // 游戏中心页面信息流广告
        ttInfo.setGameListExpressFeedId(CommonUtils.mgamelist); // 游戏列表，信息流广告，模板渲染
        cmGameAppInfo.setTtInfo(ttInfo);
        CmGameSdk.initCmGameSdk(this, cmGameAppInfo, new CmGameImageLoader());
    }

    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
    public static class sdf{
        private String mAppId;// 必选参数，设置应用的AppId
        private String mAppName;// 必选参数，设置应用名称
        private boolean mIsPaid = false;// 可选参数，设置是否为计费用户：true计费用户、false非计费用户。默认为false非计费用户。须征得用户同意才可传入该参数
        private String mKeywords;// 可选参数，设置用户画像的关键词列表 **不能超过为1000个字符**。须征得用户同意才可传入该参数
        private String mData;// 可选参数，设置额外的用户信息 **不能超过为1000个字符**
        private int mTitleBarTheme = TTAdConstant.TITLE_BAR_THEME_LIGHT;// 可选参数，设置落地页主题，默认为TTAdConstant#TITLE_BAR_THEME_LIGHT
        private boolean mAllowShowNotify = true;// 可选参数，设置是否允许SDK弹出通知：true允许、false禁止。默认为true允许
        private boolean mIsDebug = false;// 可选参数，是否打开debug调试信息输出：true打开、false关闭。默认false关闭
        private boolean mAllowShowPageWhenScreenLock = false;// 可选参数，设置是否允许落地页出现在锁屏上面：true允许、false禁止。默认为false禁止
        private int[] mDirectDownloadNetworkType;
        private boolean mIsUseTextureView = false;// 可选参数，设置是否使用texture播放视频：true使用、false不使用。默认为false不使用（使用的是surface）
        private boolean mIsSupportMultiProcess = false;// 可选参数，设置是否支持多进程：true支持、false不支持。默认为false不支持
        private IHttpStack mHttpStack;//可选参数，设置外部网络请求，默认为urlconnection
        private boolean mIsAsyncInit = false;//是否异步初始化sdk
        private TTCustomController mCustomController;//可选参数，可以设置隐私信息控制开关
    }
}

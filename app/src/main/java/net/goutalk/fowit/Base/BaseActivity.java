package net.goutalk.fowit.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.coder.zzq.smartshow.dialog.LoadingDialog;
import com.jaeger.library.StatusBarUtil;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxKeyboardTool;

import net.goutalk.fowit.Bean.ShareBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.BaseUiListener;
import net.goutalk.fowit.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText editText;
    private CompositeDisposable disposable;

    private boolean isFullScreen = false;
   public double baifen=50;
    private boolean isAllowScreenRoate = false;
    private LoadingDialog loadingDialog;
    private String[] name = {"朋友圈", "微信好友", "QQ", "QQ空间"};
    private int[] pic = {R.mipmap.wd_yqhy_pyq, R.mipmap.wd_yqhy_weixin, R.mipmap.wd_yqhy_qq, R.mipmap.wd_yqhy_qq};
    public List<ShareBean> listshare = new ArrayList<>();
    Tencent mTencent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigActivity();
        setContentView(this.getLayoutId());
        mTencent = Tencent.createInstance(CommonUtils.QQAPP_ID_WX, this.getApplicationContext());

        RxActivityTool.addActivity(this);
        ButterKnife.bind(this);
        SetStatusBar();
        disposable = new CompositeDisposable();

        initView();
        initDate();

    }
    public void sharetoQQ(Activity context, Bundle bundle) {
        mTencent.shareToQQ(context, bundle, new BaseUiListener());
    }

    public void sharetoQzONE(Activity context, Bundle bundle) {
        mTencent.shareToQzone(context, bundle, qZoneShareListener);
    }

    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            // Util.toastMessage(QZoneShareActivity.this, "onCancel:test ");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            //   Util.toastMessage(QZoneShareActivity.this, "onError: " + e.errorMessage, "e");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            //    Util.toastMessage(QZoneShareActivity.this, "onComplete: " + response.toString());
        }

    };


    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }
    public  byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    protected void SetStatusBar() {
        StatusBarUtil.setTranslucent(this, 0);
        StatusBarUtil.setLightMode(this);
    }
    public void setHtmlData(String content, WebView webView) {
        content=  content.replace("class=\"ql-align-center\"","  style=\"text-align:center\"");
        content=  content.replace("class=\"ql-align-right\""," style=\"text-align:right\"");
        content=getHtmlData(content);
                WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        //webSettings.setJavaScriptEnabled(true);//打开js和安卓通信
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

    }

    /**
     * 富文本适配
     */
    private String getHtmlData(String bodyHTML) {
        //windowWidth = windowWidth/2;
        String css = "<style> img{max-width:100%;width:auto !important;height:auto !important;min-height:10px;} p{margin-top:0 !important;margin-bottom:0 !important;}</style>";
        String html = "<html><header>" + css + "</header><body style='margin:0;padding:0'>" +bodyHTML + "</body></html>";
        return html;
    }
    public void invokingBD(Context context,String addr){

        //  com.baidu.BaiduMap这是高德地图的包名
        //调起百度地图客户端try {
        Intent intent = null;
        try {
            String uri = "intent://map/direction?origin=latlng:0,0|name:我的位置&destination=" + addr + "&mode=drivingion=" + "城市" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";

            intent = Intent.getIntent(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(isInstallByread("com.baidu.BaiduMap")){
            startActivity(intent); //启动调用
            Log.e("GasStation", "百度地图客户端已经安装") ;
        }else{
            Toast.makeText(context, "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
        }
    }
    public void invokingGD(Context context,String addr){

        //  com.autonavi.minimap这是高德地图的包名
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://navi?sourceApplication=应用名称&lat="+ "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+addr));

        if(isInstallByread("com.autonavi.minimap")){
            startActivity(intent);
            Log.e("GasStation", "高德地图客户端已经安装") ;
        }else{
            Toast.makeText(context, "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 调用腾讯地图
     *
     * @param context 上下文对象s
     */
    public  void invokeQQMap(Context context, String addr) {
        try {
            Uri uri = Uri.parse("qqmap://map/routeplan?type=drive" +
                    "&to=" + addr//终点的显示名称 必要参数
                    + "&tocoord="
                    + "&referer={你的应用名称}");
            Intent intent = new Intent();
            intent.setData(uri);

            context.startActivity(intent);
        } catch (Exception e) {
            //Logger.e(TAG, e.getMessage());
        }
    }

    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }



    private void ConfigActivity() {
        //是否全屏
        if (isFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //是否允许旋转屏幕
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void initDate() {
    }

    public void ShowLoading() {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog().middle();
        loadingDialog.showInActivity(this);
    }

    public void HideLoading() {
        if (loadingDialog == null) return;
        loadingDialog.dismiss();
    }

    public void setAllowFullScreen(boolean allowFullScreen) {
        this.isFullScreen = allowFullScreen;
    }

    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    public Context getContext() {
        return this;
    }

    public int getResouseColor(@ColorRes int colorId) {
        return getResources().getColor(colorId);
    }

    public Drawable getResouseDrawable(@DrawableRes int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    public void Goto(Class<?> clz) {
        startActivity(new Intent(this, clz), null);
    }

    public void Goto(Intent intent) {
        startActivity(intent);
    }

    public void Goto(Context context,Class<?> clz) {
        context.startActivity(new Intent(context, clz), null);
    }

    public void Goto(Class<?> clz, String key, String value) {
        startActivity(new Intent(this, clz).putExtra(key, value), null);
    }

    public void Goto(Class<?> clz, String key, Serializable value) {
        startActivity(new Intent(this, clz).putExtra(key, value), null);
    }

    public void Goto(Class<?> clz, String key, int value, String key1, Serializable value1) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, String value, String key1, String value1) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, String value, String key1, int value1) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, int value, String key1, int value1) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1), null);
    }

    public void Goto(Class<?> clz, String key, int value, String key1, int value1, String key2, String value2) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1).putExtra(key2, value2), null);
    }

    public void Goto(Class<?> clz, String key, int value, String key1, int value1, String key2, int value2) {
        startActivity(new Intent(this, clz).putExtra(key, value).putExtra(key1, value1).putExtra(key2, value2), null);
    }

    public void Goto(Class<?> clz, String key0, String value0, String key1, String value1, String key2, String value2) {
        startActivity(new Intent(this, clz).putExtra(key0, value0).putExtra(key1, value1).putExtra(key2, value2), null);
    }

    public void Goto(Class<?> clz, String key0, int value0, String key1, String value1, String key2, int value2) {
        startActivity(new Intent(this, clz).putExtra(key0, value0).putExtra(key1, value1).putExtra(key2, value2), null);
    }

    public void Goto(Class<?> clz, String key0, String value0, String key1, String value1, String key2, String value2, String key3, String value3) {
        startActivity(new Intent(this, clz).putExtra(key0, value0).putExtra(key1, value1).putExtra(key2, value2).putExtra(key3, value3), null);
    }

    public void Goto(Class<?> clz, String key0, String value0, String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4) {
        startActivity(new Intent(this, clz).putExtra(key0, value0).putExtra(key1, value1).putExtra(key2, value2).putExtra(key3, value3).putExtra(key4, value4), null);
    }

    public void Goto(Class<?> clz, String key, int value) {
        startActivity(new Intent(this, clz).putExtra(key, value), null);
    }

    public void Goto(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void Goto(Class<?> clz, int flag) {
        startActivity(new Intent(this, clz).setFlags(flag), null);
    }

    public void Goto(Class<?> clz, int flag,String key,String value) {
        startActivity(new Intent(this, clz).setFlags(flag).putExtra(key,value), null);
    }

    public void Goto(Class<?> clz, Bundle bundle, int flag) {
        Intent intent = new Intent();
        intent.setFlags(flag);
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public String getToken() {
        return SPUtils.getInstance().getString("token");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            RxActivityTool.finishActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    //使editText点击外部时候失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    RxKeyboardTool.hideKeyboard(this, v);
                    if (editText != null) {
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            editText = (EditText) v;
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    public CompositeDisposable getDisposable() {
        return disposable;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HideLoading();
    }
}

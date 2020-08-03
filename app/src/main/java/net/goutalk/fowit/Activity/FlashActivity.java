package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.MainThread;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.MainActivity;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class FlashActivity extends BaseActivity {


    @BindView(R.id.splash_container)
    FrameLayout splashContainer;
    private TTAdNative mTTAdNative;
    //是否强制跳转到主页面
    private boolean mForceGoMain=false;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;

    private boolean mIsExpress = false; //是否请求模板广告
    private DialogPlus dialog_spec;
    private CountDownTimer countDownTimer;
    @Override
    public int getLayoutId() {
        return R.layout.activity_display;
    }

    @Override
    public void initView() {
//        CheckPermission();
        StatusBarUtil.setTransparent(this);
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);

        if (SPUtils.getInstance().getBoolean("isFirstOpen", true)) {


            initSpecDialog();
            return;
        }else {
            getExtraInfo();
            //在合适的时机申请权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题
            //在开屏时候申请不太合适，因为该页面倒计时结束或者请求超时会跳转，在该页面申请权限，体验不好
            // TTAdManagerHolder.getInstance(this).requestPermissionIfNecessary(this);
            //加载开屏广告
            loadSplashAd();
        }

    }
    private void getExtraInfo() {
        Intent intent = getIntent();
        if(intent == null) {
            return;
        }

        mIsExpress = intent.getBooleanExtra("is_express", false);
    }

    //规格参数dialog
    private void initSpecDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.layout_flashwindow);
        dialog_spec = DialogPlus.newDialog(FlashActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        TextView txtfuwu = viewHolder.getInflatedView().findViewById(R.id.txt_fuwu);
        TextView txtyinsi = viewHolder.getInflatedView().findViewById(R.id.txtyinsi);

        TextView txtexit = viewHolder.getInflatedView().findViewById(R.id.txtexit);
        TextView txtok = viewHolder.getInflatedView().findViewById(R.id.txtok);

        txtfuwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Goto(ProtocolActivity.class);

            }
        });
        txtyinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto(YinsiActivity.class);
            }
        });

        txtexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
                finish();
                Goto(GuideActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
                SPUtils.getInstance().put("isFirstOpen", false);
            }
        });
        dialog_spec.show();
    }

    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            float expressViewWidth = UIUtils.getScreenWidthDp(this);
            float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(CommonUtils.startPageCode)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(CommonUtils.startPageCode)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, String.valueOf(message));
                ToastUtils.showShort(message);

              GotoStart();
            }

            @Override
            @MainThread
            public void onTimeout() {
                ToastUtils.showShort("开屏广告加载超时");
                GotoStart();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                if (ad == null) {
                    return;
                }

                //获取SplashView
                View view = ad.getSplashView();
                if (view != null && splashContainer != null && !FlashActivity.this.isFinishing()) {
                    splashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    splashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                }else {
                    GotoStart();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                      //  ToastUtils.showShort("开屏广告点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                        //  ToastUtils.showShort("开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                        //    ToastUtils.showShort("开屏广告跳过");
                        if (mForceGoMain==false) {
                            mForceGoMain=true;
                            GotoStart();
                        }

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        //  ToastUtils.showShort("开屏广告倒计时结束");
                        if (mForceGoMain==false) {
                            mForceGoMain=true;
                            GotoStart();
                        }
                    }
                });
                if(ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                //       ToastUtils.showShort("下载中...");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            //    ToastUtils.showShort("下载暂停...");

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            //     ToastUtils.showShort("下载失败...");

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            //      ToastUtils.showShort("下载完成...");

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            //      ToastUtils.showShort("安装完成...");

                        }
                    });
                }
            }
        }, AD_TIME_OUT);

    }
    private void GotoStart() {
        //游客模式直接跳转首页

        Goto(MainActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
        FlashActivity.this.finish();
        //查看是否有缓存的token     有：检查过期没    没有：跳转登录界面
//        if (SPUtils.getInstance().getString("TOKEN", "").equals("")) {
//            Goto(LoginActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
//            FlashActivity.this.finish();
//        } else {
//            Goto(MainActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
//            FlashActivity.this.finish();
//        }
    }

//    @OnClick(R.id.tv_timer)
//    public void onViewClicked() {
//        countDownTimer.onFinish();
//    }

    @Override
    public void onBackPressed() {
        CommonUtils.doubleClickExitApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //判断是否该跳转到主页面

    }
}

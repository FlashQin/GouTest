package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.SingBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DateUtils;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.qiujuer.genius.ui.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;



public class SingActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.txt_sing_nums)
    TextView txtSingNums;
    @BindView(R.id.btn_goto_buy)
    Button btnGotoBuy;
    @BindView(R.id.frrrrrrrrr)
    FrameLayout frrrrrrrrr;
    @BindView(R.id.express_container)
    FrameLayout expressContainer;
    private int CurrentPage = 1;
    int today = 0;
    private BaseQuickAdapter<String, BaseViewHolder> sorcdapter;

    private BaseQuickAdapter<String, BaseViewHolder> Adapter;
    private TTAdNative mTTAdNative;
    private TTAdNative mTTAdNativebanner;
    private TTNativeExpressAd mTTAd;
    private TTNativeExpressAd mTTAdbanner;
    private DialogPlus dialog_spec;
    FrameLayout mExpressContainer;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sing;
    }

    @Override
    public void initView() {

        tvTitle.setText("签到");
//        tvRightTitle.setText("积分记录");
//        tvRightTitle.setVisibility(View.VISIBLE);
        initAd();
        loadExpressAd(CommonUtils.mPicInfo);
        getSing();


    }
    private void loadBannerAd(String codeId) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(320, 150) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(320, 150)//这个参数设置即可，不影响个性化模板广告的size
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNativebanner.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //  TToast.show(NativeBannerActivity.this, "load error : " + code + ", " + message);
            }

            @Override

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads.get(0) == null) {
                    return;
                }
                mTTAdbanner = ads.get(0);
                mTTAdbanner.setSlideIntervalTime(30 * 1000);//设置轮播间隔 ms,不调用则不进行轮播展示
                bindbannerAdListener(mTTAdbanner);
                mTTAdbanner.render();//调用render开始渲染广告

            }
        });
    }

    //规格参数dialog
    private void initSpecDialog(String coin) {
        ViewHolder viewHolder = new ViewHolder(R.layout.layout_sing_window);
        dialog_spec = DialogPlus.newDialog(SingActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        ImageView img = viewHolder.getInflatedView().findViewById(R.id.img_close);
        mExpressContainer = viewHolder.getInflatedView().findViewById(R.id.banner_container);
        TextView txt = viewHolder.getInflatedView().findViewById(R.id.txt_num_coin);
        TextView txtok = viewHolder.getInflatedView().findViewById(R.id.txt_exit);
        txt.setText(coin.replace("金币", ""));

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
                finish();
            }
        });
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();

            }
        });

        loadBannerAd(CommonUtils.mQustionBanner);
        HideLoading();
        dialog_spec.show();


    }

    public void initAd() {
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        mTTAdNativebanner = TTAdManagerHolder.get().createAdNative(this);
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
    }

    public void getSing() {
        //签到
        RxHttp.postForm("/memberSign/member/read.json")

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        if (scoreBean.getCode() == 0) {
                            SingBean defalutAddressBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), SingBean.class);
                            txtSingNums.setText(defalutAddressBean.getData().getSignCount() + "");
                            if (scoreBean.getData() != null) {
                                if (defalutAddressBean.getData().getUpdateTime().substring(0, 10).equals(DateUtils.gettimenow().substring(0, 10))) {
                                    btnGotoBuy.setText("已签到");
                                    btnGotoBuy.setEnabled(false);

                                } else {
                                    btnGotoBuy.setText("签到");
                                    btnGotoBuy.setEnabled(true);
                                }
                            }
                        }
                    }
                });

    }

    public void Sing() {
        //签到
        RxHttp.postForm("/memberSign/save.do")

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        if (scoreBean.getCode() == 0) {

                            getSing();
                            initSpecDialog("");

                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @OnClick({R.id.img_back, R.id.btn_goto_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_goto_buy:
                Sing();
                break;
        }
    }

    private void loadExpressAd(String codeId) {
        expressContainer.removeAllViews();
        float expressViewWidth = 350;
        float expressViewHeight = 350;

        expressViewHeight = 0; //高度设置为0,则高度会自适应

        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //  TToast.show(NativeExpressActivity.this, "load error : " + code + ", " + message);
                expressContainer.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
                startTime = System.currentTimeMillis();
                mTTAd.render();
            }
        });
    }

    private long startTime = 0;

    private boolean mHasShowDownloadActive = false;
    //绑定广告行为
    private void bindbannerAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                //  TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                //TToast.show(mContext, "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                //Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                // TToast.show(mContext, msg+" code:"+code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                //返回view的宽高 单位 dp
                //  TToast.show(mContext, "渲染成功");
                //在渲染成功回调时展示广告，提升体验
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });
        //dislike设置
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        //可选，下载监听设置
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                // TToast.show(BannerExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                // if (!mHasShowDownloadActive) {
                //  mHasShowDownloadActive = true;
                //  TToast.show(BannerExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
                // }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                //  TToast.show(BannerExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                //  TToast.show(BannerExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
            }
        });
    }
    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                //    TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                //  TToast.show(mContext, "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
                // TToast.show(mContext, msg + " code:" + code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
                //返回view的宽高 单位 dp
                // TToast.show(mContext, "渲染成功");
                expressContainer.removeAllViews();
                expressContainer.addView(view);
            }
        });
        //dislike设置
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                //  TToast.show(NativeExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    //  TToast.show(NativeExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                // TToast.show(NativeExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                //   TToast.show(NativeExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                //  TToast.show(NativeExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                //   TToast.show(NativeExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置广告的不喜欢，注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
     *
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(this, words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                    // TToast.show(mContext, "点击 " + filterWord.getName());
                    //用户选择不喜欢原因后，移除广告展示
                    expressContainer.removeAllViews();
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(SingActivity.this, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                // TToast.show(mContext, "点击 " + value);
                //用户选择不喜欢原因后，移除广告展示
                expressContainer.removeAllViews();
            }

            @Override
            public void onCancel() {
                // TToast.show(mContext, "点击取消 ");
            }

            @Override
            public void onRefuse() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTAd != null) {
            mTTAd.destroy();
        }
    }
}

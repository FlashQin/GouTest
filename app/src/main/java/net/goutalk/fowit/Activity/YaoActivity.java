package net.goutalk.fowit.Activity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.rxjava.rxlife.RxLife;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.TTAdManagerHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class YaoActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.txt_copyyao)
    TextView txtCopyyao;
    @BindView(R.id.banner_container)
    FrameLayout mExpressContainer;
    @BindView(R.id.linwechat)
    LinearLayout linwechat;
    @BindView(R.id.linqq)
    LinearLayout linqq;
    @BindView(R.id.linwepeng)
    LinearLayout linwepeng;
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private Button mCreativeButton;
    private IWXAPI api;

    @Override
    public int getLayoutId() {
        return R.layout.layout_yao;
    }


    @Override
    public void initView() {

        api = WXAPIFactory.createWXAPI(this, CommonUtils.APP_ID_WX, false);
        //将应用的appid注册到微信
        api.registerApp(CommonUtils.APP_ID_WX);
        tvTitle.setText("邀请好友");
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        loadBannerAd(CommonUtils.mQustionBanner);


    }


    public void wechatshare(int poistion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WXWebpageObject textObject = new WXWebpageObject();
                textObject.webpageUrl = "https://www.baidu.com";
                WXMediaMessage msg = new WXMediaMessage(textObject);
                msg.title = "勾转！省钱多，赚钱快";
//                设置描述
                msg.description = "自从用了这个勾转app，每天都能省下一顿饭钱，好感动！好东西要让你知道。看视频，赚零花，新人专享现金折扣，可立提微信、支付宝，下载填写我的邀请码"+ SPUtils.getInstance().getString("code", "123")+"↓↓点击下载领取↓↓";
                Bitmap thumb = null;


//注意下面的这句压缩，120，150是长宽。

//一定要压缩，不然会分享失败

                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                发送的内容
                req.message = msg;
//                创建唯一的标识
                req.transaction = buildTransction("text");
//                设置场景（好友==>朋友圈）
                if (poistion == 0) {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                } else {
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                }
                api.sendReq(req);
            }
        }).start();

    }

    private String buildTransction(String str) {
        return (str == null) ? String.valueOf(System.currentTimeMillis()) : str + System.currentTimeMillis();
    }


    private void postCoin(String coin) {

        RxHttp.postForm("/coinIncome/save.do")
                .add("quantity", coin.replace("金币", ""))
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("得到" + coin + "金币");

                        } else {
                            ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTAd != null) {
            mTTAd.destroy();
        }
        if (mCreativeButton != null) {
            mCreativeButton = null;
        }
    }

    private void loadBannerAd(String codeId) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(450, 250) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(450, 250)//这个参数设置即可，不影响个性化模板广告的size
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //  TToast.show(NativeBannerActivity.this, "load error : " + code + ", " + message);
            }

            @Override

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads.get(0) == null) {
                    return;
                }
                mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(30 * 1000);//设置轮播间隔 ms,不调用则不进行轮播展示
                bindAdListener(mTTAd);
                mTTAd.render();//调用render开始渲染广告

            }
        });
    }

    //绑定广告行为
    private void bindAdListener(TTNativeExpressAd ad) {
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

    /**
     * 设置广告的不喜欢，开发者可自定义样式
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
                    mExpressContainer.removeAllViews();
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认个性化模板中默认dislike弹出样式
        ad.setDislikeCallback(YaoActivity.this, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                // TToast.show(mContext, "点击 " + value);
                //用户选择不喜欢原因后，移除广告展示
                mExpressContainer.removeAllViews();
            }

            @Override
            public void onCancel() {
                //TToast.show(mContext, "点击取消 ");
            }

            @Override
            public void onRefuse() {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.linwechat, R.id.linqq, R.id.linwepeng, R.id.linphone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linwechat:
                wechatshare(0);
                break;
            case R.id.linqq:
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "勾转！省钱多，赚钱快");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "自从用了这个勾转app，每天都能省下一顿饭钱，好感动！好东西要让你知道。看视频，赚零花，新人专享现金折扣，可立提微信、支付宝，下载填写我的邀请码"+ SPUtils.getInstance().getString("code", "123")+"↓↓点击下载领取↓↓");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "");
                // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ));
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "勾转");
                //   params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "");
                sharetoQQ(YaoActivity.this, params);
                break;
            case R.id.linwepeng:
                wechatshare(1);
                break;
            case R.id.linphone:
                Goto(PhoneListActivity.class);
                break;
        }
    }
}

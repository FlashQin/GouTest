package net.goutalk.fowit.Activity;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LuckBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.wigde.RotateListener;
import net.goutalk.fowit.wigde.WheelSurfView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class LuckyActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.frrrrrrrrr)
    FrameLayout frrrrrrrrr;
    @BindView(R.id.wheelSurfView1)
    WheelSurfView wheelSurfView;
    @BindView(R.id.recyclerview_luck)
    RecyclerView recyclerviewLuck;
    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mIsExpress = false; //是否请求模板广告
    private boolean mHasShowDownloadActive = false;
    private BaseQuickAdapter<LuckBean, BaseViewHolder> mAdapter;
    private DialogPlus dialog_spec;

    FrameLayout mExpressContainer;
    private TTAdNative mTTAdNativepic;
    private TTNativeExpressAd mTTAd;
    private Button mCreativeButton;
    @Override
    public int getLayoutId() {
        return R.layout.layout_luck;
    }


    @Override
    public void initView() {
        tvTitle.setText("幸运大转盘");
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());

        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNativepic = ttAdManager.createAdNative(getApplicationContext());
        getExtraInfo();
        initRec();
        start();
        initdata();
    }

    //规格参数dialog
    private void initSpecDialog(String coin) {
        ViewHolder viewHolder = new ViewHolder(R.layout.layout_zp_window);
        dialog_spec = DialogPlus.newDialog(LuckyActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        ImageView img = viewHolder.getInflatedView().findViewById(R.id.img_close);
        mExpressContainer= viewHolder.getInflatedView().findViewById(R.id.banner_container);
        TextView txt = viewHolder.getInflatedView().findViewById(R.id.txt_num_coin);
        TextView txtok = viewHolder.getInflatedView().findViewById(R.id.txt_exit);
        txt.setText(coin.replace("金币", ""));

        img.setOnClickListener(v -> dialog_spec.dismiss());
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoading();
                loadAd(CommonUtils.mVerticalCodeId, TTAdConstant.VERTICAL);
            }
        });

        loadBannerAd(CommonUtils.mQustionBanner);
        dialog_spec.show();

    }
    public void initdata() {

        List<LuckBean> list=new ArrayList<>();
        LuckBean luckBean=new LuckBean();
        luckBean.setPhone("187****6482");
        luckBean.setNums("90");
        list.add(luckBean);

        LuckBean luckBean1=new LuckBean();
        luckBean1.setPhone("186****3256");
        luckBean1.setNums("75");
        list.add(luckBean1);

        LuckBean luckBean2=new LuckBean();
        luckBean2.setPhone("187****1259");
        luckBean2.setNums("60");
        list.add(luckBean2);

        LuckBean luckBean3=new LuckBean();
        luckBean3.setPhone("132****4685");
        luckBean3.setNums("80");
        list.add(luckBean3);

        LuckBean luckBean4=new LuckBean();
        luckBean4.setPhone("158****8864");
        luckBean4.setNums("90");
        list.add(luckBean4);
        mAdapter.addData(list);
    }

    private void initRec() {
        //初始化RecycleView
        recyclerviewLuck.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerviewLuck.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewLuck.setAdapter(mAdapter = new BaseQuickAdapter<LuckBean, BaseViewHolder>(R.layout.item_lucky) {
            @Override
            protected void convert(BaseViewHolder helper, LuckBean item) {


                TextView txtphone = helper.getView(R.id.txt_phone);
                TextView txtcopin = helper.getView(R.id.txt_coin);
                txtphone.setText(item.getPhone());
                txtcopin.setText(item.getNums());
            }

        });
    }

    private void getExtraInfo() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        mIsExpress = intent.getBooleanExtra("is_express", false);
    }

    public void start() {
        //添加滚动监听
        wheelSurfView.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position, String des) {
                // Toast.makeText(LuckyActivity.this, "结束了 位置：" + position + "   描述：" + des, Toast.LENGTH_SHORT).show();
                initSpecDialog(des);
                if (des.contains("神")) {
                } else {
                    postCoin(des);

                }

            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {

            }

            @Override
            public void rotateBefore(ImageView goImg) {

                //模拟位置
                int position = new Random().nextInt(8) + 1;
                wheelSurfView.startRotate(position);

            }
        });
    }

    private void postCoin(String coin) {

        RxHttp.postForm("/coinIncome/save.do")
                .add("quantity", coin.replace("金币", ""))
                .add("depict","抽奖")
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

    private void loadAd(final String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500, 500)
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                    //该方法直接展示广告
//                    mttRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this);

                    //展示广告，并传入广告展示的场景
                    mttRewardVideoAd.showRewardVideoAd(LuckyActivity.this, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRewardVideoAd = null;
                } else {
                    //TToast.show(RewardVideoActivity.this, "请先加载广告");
                    loadAd(CommonUtils.mVerticalCodeId, TTAdConstant.VERTICAL);
                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoAdLoad");
                HideLoading();
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardVideoAd close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardVideoAd complete");
                        int coin = (int) (50 * Math.random() + 50);

                        postCoin(String.valueOf(coin));
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardVideoAd error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName;
                        Log.e(TAG, "Callback --> " + logString);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardVideoAd has onSkippedVideo");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
            }
        });
    }
    private void loadBannerAd(String codeId) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(450,150) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(450,150 )//这个参数设置即可，不影响个性化模板广告的size
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNativepic.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
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
                mTTAd.setSlideIntervalTime(30*1000);//设置轮播间隔 ms,不调用则不进行轮播展示
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
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
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
        ad.setDislikeCallback(LuckyActivity.this, new TTAdDislike.DislikeInteractionCallback() {
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


}

package net.goutalk.fowit.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import net.goutalk.fowit.Adapter.ViewPagerAdapter;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.BaseJDBean;
import net.goutalk.fowit.Bean.GoodsDetileBean;
import net.goutalk.fowit.Bean.GoodsLinkBean;
import net.goutalk.fowit.Bean.JDGoodsListBean;
import net.goutalk.fowit.Bean.JDJsonBean;
import net.goutalk.fowit.Bean.ShareBean;
import net.goutalk.fowit.Bean.UserInfoBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.fragment.FMBadyDetail;
import net.goutalk.fowit.fragment.FMCommonList;
import net.goutalk.fowit.fragment.FMProductCase;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DateUtils;
import net.goutalk.fowit.utils.GlideImageLoader;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.Urls;
import net.qiujuer.genius.ui.widget.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class JDGoodsInfoActivity extends BaseActivity {

    @BindView(R.id.shop_banner)
    Banner banner;
    @BindView(R.id.shop_tablayout)
    TabLayout shopTablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.shop_title_tv)
    TextView shopTitleTv;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;

    @BindView(R.id.shop_specif_tv)
    LinearLayout shopSpecifTv;

    @BindView(R.id.coupon_tv)
    TextView couponTv;
    @BindView(R.id.coupon_content_tv)
    TextView couponContentTv;
    @BindView(R.id.coupon_next_iv)
    ImageView couponNextIv;
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;
    @BindView(R.id.promotions_tv)
    TextView promotionsTv;
    @BindView(R.id.promotions_content_tv)
    TextView promotionsContentTv;
    @BindView(R.id.promotions_next_iv)
    ImageView promotionsNextIv;
    @BindView(R.id.promotions_rl)
    RelativeLayout promotionsRl;


    @BindView(R.id.al_shop_detail)
    AppBarLayout alShopDetail;
    @BindView(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout collToolbarLayout;
    @BindView(R.id.content_cl)
    ConstraintLayout contentCl;
    @BindView(R.id.tv_home_statusbar)
    TextView tvHomeStatusbar;
    @BindView(R.id.nsv_good)
    NestedScrollView nsvGood;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;


    @BindView(R.id.tv_take_name)
    TextView tvTakeName;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_mouth)
    TextView tvMouth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.rl_take)
    RelativeLayout rlTake;
    @BindView(R.id.iv_buy_one)
    ImageView ivBuyOne;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_mouth_1)
    View tvMouth1;
    @BindView(R.id.tv_mouth_2)
    View tvMouth2;
    @BindView(R.id.sdfsdf)
    TextView sdfsdf;
    @BindView(R.id.shop_name_you)
    TextView shopNameYou;
    @BindView(R.id.sdfcsdf)
    RelativeLayout sdfcsdf;

    @BindView(R.id.btn_goto_buy)
    Button btnGotoBuy;
    @BindView(R.id.shop_sale)
    TextView shopSale;
    @BindView(R.id.wwweweew)
    RelativeLayout wwweweew;

    @BindView(R.id.imgnext)
    ImageView imgnext;
    @BindView(R.id.cl_good)
    CoordinatorLayout clGood;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tvserch)
    TextView tvserch;
    @BindView(R.id.dsfsdf)
    LinearLayout dsfsdf;
    private List<Fragment> list;
    private int mAlpha;

    private int goodCount;
    private boolean isBottom;
    private boolean isCollect = false;
    private AlertDialog addDialog;
    String ID, goodsId;
    GoodsDetileBean goodsDetileBean;
    final ArrayList<String> imgs = new ArrayList<>();

    private BaseQuickAdapter<String, BaseViewHolder> mAdapterList;
    GoodsLinkBean goodsLinkBean;
    private DialogPlus dialog_spec;
    private BaseQuickAdapter<ShareBean, BaseViewHolder> specAdapter;
    private String[] name = {"朋友圈", "微信好友", "QQ", "QQ空间"};
    private int[] pic = {R.mipmap.wd_yqhy_pyq, R.mipmap.wd_yqhy_weixin, R.mipmap.wd_yqhy_qq, R.mipmap.wd_yqhy_qq};
    public List<ShareBean> listshare = new ArrayList<>();
    private IWXAPI api;

    private String menberid="";
    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    public void initView() {

        for (int i = 0; i < 4; i++) {
            ShareBean shareBean = new ShareBean();
            shareBean.setId(i);
            shareBean.setImg(pic[i]);
            shareBean.setName(name[i]);
            listshare.add(shareBean);
        }
        ID = getIntent().getStringExtra("id");
        goodsId = getIntent().getStringExtra("goodsid");
        //  tvTitle.setText("商品详情");
        initToolBar();
        initScrollView();

        CommonUtils.likeid = ID.equals("-1") ? goodsId : ID;
        SPUtils.getInstance().put("likeid", CommonUtils.likeid);
        api = WXAPIFactory.createWXAPI(JDGoodsInfoActivity.this, CommonUtils.APP_ID_WX, false);
        //将应用的appid注册到微信
        api.registerApp(CommonUtils.APP_ID_WX);

        initSpecDialog();
        // String ddssf=AlibcLogin.getInstance().getSession().toString();//获取用户淘宝信息
        // txtinfo.setText(AlibcLogin.getInstance().getSession().toString());

        // setStatusBar(tvHomeStatusbar);
        viewPager.setOffscreenPageLimit(3);



    }
    private void postInventCode(String code) {
        RxHttp.postForm("/login/baichuan/callback.do")
                .add("url", code)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            //ToastUtils.showShort("激活成功");

                        } else {
                           // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }

    public void logainTaoBao() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int result, String userId, String nick) {
                //Toast.makeText(TestActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                goTaoByItemUrl(goodsLinkBean.getData().getItemUrl());
            }

            @Override
            public void onFailure(int i, String s) {
                //Toast.makeText(TestActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean getIsLogainTao() {
        boolean islagin = false;
        String statu = AlibcLogin.getInstance().getSession().toString();
        if (statu.contains("topAccessToken=null")) {
            islagin = false;
        } else {
            postInventCode(statu);
            islagin = true;
        }
        return islagin;
    }

    public void goTaoByItemUrl(String url) {
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Auto);
        showParams.setClientType("taobao");
        showParams.setBackUrl("alisdk://");
        showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);

        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
        taokeParams.setPid("mm_27582576_1914750210_110673400159");

        Map<String, String> trackParams = new HashMap<>();
        // 通过百川内部的webview打开页面
        AlibcTrade.openByUrl(JDGoodsInfoActivity.this, "", url, null,
                new WebViewClient(), new WebChromeClient(),
                showParams, taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {
                        AlibcLogger.i("MainActivity", "request success");
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        AlibcLogger.e("MainActivity", "code=" + code + ", msg=" + msg);
                        if (code == -1) {
                            Toast.makeText(JDGoodsInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    public void initBanner() {


        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader(0, true));
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImages(imgs);
        banner.start();
    }


    private void initScrollView() {
        //监听AppBarLayout滑动状态
        alShopDetail.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

            if (appBarLayout.getTotalScrollRange() == -verticalOffset) {
                if (isBottom) {
                } else {
                    isBottom = true;
                }
            } else {
                isBottom = false;
            }
            int minHeight = 50;
            if (banner != null && banner.getMeasuredHeight() != 0) {
                int maxHeight = banner.getMeasuredHeight() / 2;
                if (maxHeight == 0) {
                    maxHeight = 255;
                }
                if (Math.abs(verticalOffset) <= minHeight) {
                    mAlpha = 0;
                } else if (Math.abs(verticalOffset) > maxHeight) {
                    mAlpha = 255;
                } else {
                    mAlpha = (Math.abs(verticalOffset) - minHeight) * 200 / (maxHeight - minHeight);
                }
                if (mAlpha <= 0) {
                    //  llHead.setBackgroundColor(Color.argb(0, 255, 255, 255));
                } else if (mAlpha >= 255) {
                    // llHead.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
                } else {
                    // llHead.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
                }
            }
        });
    }

    private void initTabLayout() {
        FMCommonList fmCommonList = new FMCommonList();
        FMProductCase fmProductCase = new FMProductCase();
        FMBadyDetail fmBadyDetail = new FMBadyDetail();
        list = new ArrayList<>();
        // list.add(fmBadyDetail);//宝贝详情
        list.add(fmProductCase);//产品案例
        list.add(fmCommonList);//商品评价
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (shopTablayout));
        shopTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (viewPager));
    }


    private void initToolBar() {

    }

//    @Override
//    protected void setStatusBar() {
//        StatusBarUtil.setTranslucentForImageView(MainActivity.this, 0, null);
//        StatusBarUtils.StatusBarLightMode(this, StatusBarUtils.StatusBarLightMode(this));
//    }

    private void getUserInfo() {
        RxHttp.postForm("/memberCenter/baseInfo.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean baseMsgBean) {
                        if (baseMsgBean.getCode() == 0) {
                            UserInfoBean     codeBean = JSONObject.parseObject(JSONObject.toJSONString(baseMsgBean), UserInfoBean.class);

                            menberid=codeBean.getData().getMemberId();

                            initUrl();
                        } else {
                            ToastUtils.showShort(baseMsgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }

    public void initUrl() {
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("goodsId", goodsId);
        paraMap.put("externalId", menberid);
        paraMap.put("specialId", menberid);
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urlgoodslinke + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            goodsLinkBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), GoodsLinkBean.class);


                        } else {
                            // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //  ToastUtils.showShort(e.toString());
                    }
                });

    }

    //规格参数dialog
    private void initSpecDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dialog_share_layout);
        dialog_spec = DialogPlus.newDialog(JDGoodsInfoActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        TextView txtcancle = viewHolder.getInflatedView().findViewById(R.id.txtcancle);
        txtcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
            }
        });
        RecyclerView mRec = viewHolder.getInflatedView().findViewById(R.id.rec_list);
        mRec.setLayoutManager(new GridLayoutManager(JDGoodsInfoActivity.this, 4));
        mRec.setAdapter(specAdapter = new BaseQuickAdapter<ShareBean, BaseViewHolder>(R.layout.itemshare) {
            @Override
            protected void convert(BaseViewHolder helper, ShareBean item) {
                helper.setText(R.id.tvname, item.getName());
                Glide.with(JDGoodsInfoActivity.this).load(item.getImg()).into((ImageView) helper.getView(R.id.imgpic));


            }
        });
        specAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommonUtils.share = 1;
                dialog_spec.dismiss();
                if (position == 0) {
                    wechatshare(0);
                }
                if (position == 1) {

                    wechatshare(1);
                }
                if (position == 2) {
                    final Bundle params = new Bundle();
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, goodsDetileBean.getData().getDtitle());
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, goodsDetileBean.getData().getTitle());
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, goodsLinkBean.getData().getItemUrl());
                    // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ));
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "勾转");
                    //   params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "");
                    sharetoQQ(JDGoodsInfoActivity.this, params);
                }
                if (position == 3) {
                    final Bundle miniProgramBundle = new Bundle();
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TITLE, goodsDetileBean.getData().getDtitle());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, goodsDetileBean.getData().getTitle());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, goodsLinkBean.getData().getItemUrl());
                    miniProgramBundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);

//                    ArrayList<String> imageUrls = new ArrayList<String>();
//                    imageUrls.add("https://share-commodity.youchengchefu.com/\"+ID+\"?from=singlemessage");
//                    miniProgramBundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                    //  params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH,"本地视频地址");


                    sharetoQzONE(JDGoodsInfoActivity.this, miniProgramBundle);
                }

            }
        });
        specAdapter.addData(listshare);
    }

    public void wechatshare(int poistion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WXWebpageObject textObject = new WXWebpageObject();
                textObject.webpageUrl = goodsLinkBean.getData().getItemUrl();
                WXMediaMessage msg = new WXMediaMessage(textObject);
                msg.title = goodsDetileBean.getData().getDtitle();
//                设置描述
                msg.description = goodsDetileBean.getData().getTitle();
                Bitmap thumb = null;
                try {
                    thumb = BitmapFactory.decodeStream(new URL(imgs.get(0)).openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

//注意下面的这句压缩，120，150是长宽。

//一定要压缩，不然会分享失败

                Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 120, 150, true);

//Bitmap回收

                thumb.recycle();

                msg.thumbData = bmpToByteArray(thumbBmp, true);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.img_back, R.id.tvserch, R.id.sdfcsdf, R.id.btn_goto_buy, R.id.imgnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tvserch:
                Goto(SerchGoodsActivity.class);
                break;
            case R.id.sdfcsdf:
                if (getIsLogainTao() == true) {

                    goTaoByItemUrl(goodsLinkBean.getData().getCouponClickUrl());

                } else {
                    logainTaoBao();
                }
                break;
            case R.id.btn_goto_buy:
                if (getIsLogainTao() == true) {

                    goTaoByItemUrl(goodsLinkBean.getData().getCouponClickUrl());

                } else {
                    logainTaoBao();
                }
                break;
            case R.id.imgnext:
                dialog_spec.show();

                break;
        }
    }
}
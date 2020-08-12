package net.goutalk.fowit.fragment;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.android.sdklibrary.admin.KDFBuilder;
import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.android.sdklibrary.presenter.util.Constant;
import com.android.sdklibrary.presenter.util.Params;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import net.goutalk.fowit.Activity.GoodsInfoActivity;
import net.goutalk.fowit.Activity.HotActivity;
import net.goutalk.fowit.Activity.LoveActivity;
import net.goutalk.fowit.Activity.NewGoodsActivity;
import net.goutalk.fowit.Activity.NineActivity;
import net.goutalk.fowit.Activity.RankActivity;
import net.goutalk.fowit.Activity.ZreoActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.BannerBean;
import net.goutalk.fowit.Bean.DongBean;
import net.goutalk.fowit.Bean.HomeTabBean;
import net.goutalk.fowit.Bean.RankGoodsBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.receve.KDFReceiver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.GlideImageLoader;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.SpacesItemDecoration;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.utils.Urls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

import butterknife.BindView;
import rxhttp.wrapper.param.RxHttp;

import static com.ali.auth.third.core.context.KernelContext.getApplicationContext;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview_home_te)
    RecyclerView recyclerviewHomeTe;

    @BindView(R.id.recyclerview_home_new)
    RecyclerView recyclerviewHomeNew;
    @BindView(R.id.recyclerview_home_list)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @BindView(R.id.rec_one)
    RecyclerView recOne;
    @BindView(R.id.rec_two)
    RecyclerView recTwo;

    private BaseQuickAdapter<HomeTabBean, BaseViewHolder> mAdapterTe;
    private BaseQuickAdapter<DongBean.DataBean.RoundsListBean, BaseViewHolder> mAdapterOne;
    private BaseQuickAdapter<DongBean.DataBean.GoodsListBean, BaseViewHolder> mAdapterRen;
    private BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder> mAdapterNew;
    private BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder> mAdapterList;
    private String[] name = {"0元购", "9.9包邮", "猜你喜欢", "新品推荐", "热门推荐", "每日半价", "折上折", "限时爆品", "信用卡返利"};
    private int[] pic = {R.drawable.sc_lygou, R.drawable.sc_jjbaoyou, R.drawable.sc_cnxh, R.drawable.sc_xptj, R.drawable.sc_rmtj, R.drawable.sc_mrbj, R.drawable.sc_zsz, R.drawable.sc_xsbp, R.drawable.sc_bkfx};
    String roundTime = "";

    String encrypt;
    private KDFReceiver mReceiver;
    //以下是广告加载
    private List<RankGoodsBean.DataBean> mListData;
    private TTAdNative mTTAdNative;
    String[] listcode = new String[]{CommonUtils.mGoodsListr1, CommonUtils.mGoodsListr2, CommonUtils.mGoodsListr3, CommonUtils.mGoodsListr4};

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
        // viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected void initView(View view) {

        //打开测试环境 打开测试环境 230 环境 或 210 环境
        Params.isDebug = true;      //如果上线的话请配置为false或者直接注释此代码
        Params.serverScene = "210"; //注意这个地方配置要放在初始化 init 之前


//        初始化
        KDFInterface.getInstance().init(getApplicationContext(), CommonUtils.kaduofenappkey);
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(getActivity());
        baifen = Double.parseDouble(SPUtils.getInstance().getString("baifen", "50"));

        initDong();
        initBanner();
        initHomeTab();
        initHomeNew();
        initHomeList();


        getActivity().registerReceiver(mReceiver = new KDFReceiver(), new IntentFilter(KDFBuilder.action));
    }

    private void loadBannerAd(FrameLayout mExpressContainer) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        int random = new Random().nextInt(4);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(listcode[random]) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(3) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(330, 150) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                // TToast.show(NativeExpressActivity.this, "load error : " + code + ", " + message);
                // mExpressContainer.removeAllViews();
                // ToastUtils.showShort("erro" + listcode[random]);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }


                ads.get(0).render();

                bindAdListener(ads.get(0), mExpressContainer);
            }
        });
    }

    //绑定广告行为
    private void bindAdListener(TTNativeExpressAd ad, FrameLayout mExpressContainer) {

        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                //  TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                //TToast.show(mContext, "广告展示");
                //  ToastUtils.showShort("广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                //Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                // TToast.show(mContext, msg+" code:"+code);
                //  ToastUtils.showShort(msg + " code:" + code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                //返回view的宽高 单位 dp
                //  TToast.show(mContext, "渲染成功");
                //在渲染成功回调时展示广告，提升体验
                // ToastUtils.showShort("succ");
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });
        //dislike设置
        // bindDislike(ad, false);
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void fetchData() {

    }

    public void initBanner() {

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader(10, true));
        banner.setBannerAnimation(Transformer.Default);

        getBanner();
    }

    private void getBanner() {
        RxHttp.get("/slideConfig/app/get.json?section=2")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        HideLoading();
                        if (codeBean.getCode() == 0) {
                            BannerBean bannerBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), BannerBean.class);
                            final ArrayList<String> imgs = new ArrayList<>();
                            for (int i = 0; i < bannerBean.getData().size(); i++) {
                                imgs.add(bannerBean.getData().get(i).getImgUrl());
                            }
                            banner.setImages(imgs);
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    String type = bannerBean.getData().get(position).getUrl();
                                }
                            });
                            banner.start();

                        } else {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort("erro");
                        HideLoading();
                    }
                });

    }

    private void initHomeTab() {

        List<HomeTabBean> listHomeTab = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HomeTabBean bean = new HomeTabBean();
            bean.setIcon(pic[i]);
            bean.setName(name[i]);
            listHomeTab.add(bean);
        }
        //初始化RecycleView
        recyclerviewHomeTe.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerviewHomeTe.addItemDecoration(new SpacesItemDecoration(15));

        recyclerviewHomeTe.setAdapter(mAdapterTe = new BaseQuickAdapter<HomeTabBean, BaseViewHolder>(R.layout.item_home_tab, listHomeTab) {
            @Override
            protected void convert(BaseViewHolder helper, HomeTabBean item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                TextView txtname = helper.itemView.findViewById(R.id.txtname);
                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getName());
                //EmptyView


            }


        });
        mAdapterTe.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    Goto(ZreoActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "1");
                }
                if (position == 1) {
                    Goto(NineActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 2) {
                    Goto(LoveActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 3) {
                    Goto(NewGoodsActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 4) {
                    Goto(HotActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 5) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "3");
                }
                if (position == 6) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "4");
                }
                if (position == 7) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "7");
                }
                if (position == 8) {

                    ShowLoading();
                    logain();
                }
            }
        });
    }

    private void logain() {
        RxHttp.postForm("/kaduofen/getLoginToken.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        HideLoading();
                        ToastUtils.showShort(codeBean.getCode() + "code");
                        if (codeBean.getCode() == 0) {

                            encrypt = codeBean.getData().toString();
                            KDFInterface.getInstance().login(getActivity(), encrypt, "12", "A",
                                    "true", "10", new OnComplete() {
                                        @Override
                                        public void onSuccess(org.json.JSONObject o) {
                                            //textViewUserId.setText("uid：" + Constant.sdkInstance.getUid());
                                            if (KDFInterface.getInstance().isLogin(getActivity(), Constant.sdkInstance.getThirdPartyUid())) {
                                                //  KDFInterface.getInstance().toCardStoreActivity(getActivity(), null);
                                                KDFInterface.getInstance().toCardListOrDetailActivity(getApplicationContext(), "01050000", "", null);

                                            }
                                        }

                                        @Override
                                        public void onError(String error) {
                                            KDFInterface.getInstance().toCardStoreNotLoginActivity(getActivity(), null);
                                        }
                                    });

                        } else {
                            KDFInterface.getInstance().toCardStoreNotLoginActivity(getActivity(), null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort("erro");
                        HideLoading();
                        KDFInterface.getInstance().toCardStoreNotLoginActivity(getActivity(), null);
                    }
                });

    }

    public void initDong() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recOne.setLayoutManager(ms);
        recOne.addItemDecoration(new SpacesItemDecoration(15));
        recOne.setAdapter(mAdapterOne = new BaseQuickAdapter<DongBean.DataBean.RoundsListBean, BaseViewHolder>(R.layout.item_dongone) {
            @SuppressLint("ResourceAsColor")
            @Override
            protected void convert(BaseViewHolder helper, DongBean.DataBean.RoundsListBean item) {


                TextView ttxpeice = helper.itemView.findViewById(R.id.txt);

                String time = item.getDdqTime();
                String content = "";
                if (item.getStatus() == 0) {
                    content = "\n已抢完";
                }
                if (item.getStatus() == 1) {
                    content = "\n已开抢";
                }
                if (item.getStatus() == 2) {
                    content = "\n待开始";
                }
                ttxpeice.setText(time.substring(11, 16) + "场" + content);
                if (item.getDdqTime().equals(roundTime)) {
                    ttxpeice.setBackgroundResource(R.drawable.dralbe_txt_dong_red);
                    ttxpeice.setTextColor(getResources().getColor(R.color.white));
                } else {
                    ttxpeice.setBackgroundResource(R.drawable.dralbe_txt_dong);
                    ttxpeice.setTextColor(getResources().getColor(R.color.list_divider));
                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        roundTime = item.getDdqTime();
                        mAdapterOne.notifyDataSetChanged();

                        getDongData();

                    }
                });
                //EmptyView

            }

        });

        //初始化RecycleView
        LinearLayoutManager mss = new LinearLayoutManager(getActivity());
        mss.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recTwo.setLayoutManager(mss);
        recTwo.addItemDecoration(new SpacesItemDecoration(15));
        recTwo.setAdapter(mAdapterRen = new BaseQuickAdapter<DongBean.DataBean.GoodsListBean, BaseViewHolder>(R.layout.item_home_ren) {
            @Override
            protected void convert(BaseViewHolder helper, DongBean.DataBean.GoodsListBean item) {

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
                TextView txtmenber = helper.itemView.findViewById(R.id.txtmenberprice);
                TextView txtprice = helper.itemView.findViewById(R.id.txtprice);
                TextView sale = helper.itemView.findViewById(R.id.txtsale);
//                imageView.setImageResource(item.getIcon());
                txtprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                txtprice.setText("￥" + item.getOriginalPrice() + "");
                txtmenber.setText("￥" + item.getActualPrice() + "");
                sale.setText("已抢" + item.getMonthSales());

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //     if (item.getActivityType()==3) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
//                        }else {
//                            ToastUtils.showShort("活动未开始或已结束");
//                        }
                    }
                });
                //EmptyView

            }

        });

        getDongData();

    }

    public void getDongData() {
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        if (!roundTime.equals("")) {
            paraMap.put("roundTime", roundTime);
        }
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urldong + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            DongBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), DongBean.class);

                            if (roundTime.equals("")) {
                                roundTime = nineToNineBean.getData().getDdqTime();
                                mAdapterOne.setNewData(nineToNineBean.getData().getRoundsList());
                            }
                            roundTime = nineToNineBean.getData().getDdqTime();
                            mAdapterRen.setNewData(nineToNineBean.getData().getGoodsList());
                            //  ToastUtils.showShort("得到" + coin + "金币");

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

    private void initHomeNew() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeNew.setLayoutManager(ms);
        recyclerviewHomeNew.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeNew.setAdapter(mAdapterNew = new BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder>(R.layout.item_home_xin) {
            @Override
            protected void convert(BaseViewHolder helper, RankGoodsBean.DataBean item) {

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
                TextView txtmenber = helper.itemView.findViewById(R.id.txtmenberprice);
                TextView txtprice = helper.itemView.findViewById(R.id.txtprice);
                TextView sale = helper.itemView.findViewById(R.id.txtsale);
//                imageView.setImageResource(item.getIcon());
                txtprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                txtprice.setText("￥" + item.getOriginalPrice() + "");
                txtmenber.setText("￥" + item.getActualPrice() + "");
                sale.setText("已售:" + item.getMonthSales());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });

        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", "1");
        paraMap.put("pageSize", "20");
        paraMap.put("rankType", "1");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urlranking + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            RankGoodsBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), RankGoodsBean.class);

                            mAdapterNew.setNewData(nineToNineBean.getData());
                            //  ToastUtils.showShort("得到" + coin + "金币");

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

    private void initHomeList() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder>(R.layout.item_rec_shop_layout) {
            @Override
            protected void convert(BaseViewHolder helper, RankGoodsBean.DataBean item) {
                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));
                if (item.getmData() == false) {

                    ImageView imageView = helper.itemView.findViewById(R.id.img);
                    TextView txtname = helper.itemView.findViewById(R.id.tv_name);
                    TextView txtjuan = helper.itemView.findViewById(R.id.tv_sell_count);
                    TextView txtshichang = helper.itemView.findViewById(R.id.tv_location);
                    TextView txtgou = helper.itemView.findViewById(R.id.tv_open_time);
                    TextView txtsale = helper.itemView.findViewById(R.id.sale);
                    TextView fan = helper.itemView.findViewById(R.id.txtfan);
                    double fana = (item.getCommissionRate() / 100) * (item.getOriginalPrice() - item.getCouponPrice()) * (baifen / 100);
                    fan.setText(roundByScale(fana, 1) + "元");
                    Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                    txtname.setText(item.getTitle());
                    txtjuan.setText(item.getCouponPrice() + "元");

                    txtshichang.setText("市场价￥ " + item.getOriginalPrice() + "");
                    txtgou.setText("勾转专享价￥" + roundByScale(item.getOriginalPrice() - item.getCouponPrice() - fana, 1) + "");
                } else {
                    LinearLayout linearLayout = helper.itemView.findViewById(R.id.lingoods);

                    FrameLayout mExpressContainer = helper.itemView.findViewById(R.id.banner_container);

                    mExpressContainer.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadBannerAd(mExpressContainer);
                        }
                    }).start();
                    //item.getmData().render();//调用render开始渲染广告


                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", "1");
        paraMap.put("pageSize", "50");
        paraMap.put("rankType", "2");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


        RxHttp.get(Urls.urlranking + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            RankGoodsBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), RankGoodsBean.class);
                            SPUtils.getInstance().put("likeid", String.valueOf(nineToNineBean.getData().get(0).getId()));


                            mListData = nineToNineBean.getData();
                            for (int i = 0; i < mListData.size(); i++) {
                                if (i != 0 && i % 10 == 0) {

                                    mListData.get(i).setmData(true);
                                }
                            }
                            mAdapterList.setNewData(mListData);
                            // mListData=nineToNineBean.getData();
                            //  ToastUtils.showShort("得到" + coin + "金币");

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }
}
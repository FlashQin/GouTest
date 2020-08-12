package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
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
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.RankGoodsBean;
import net.goutalk.fowit.Bean.ZoreBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.SpacesItemDecoration;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.utils.Urls;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

import static com.ali.auth.third.core.context.KernelContext.getApplicationContext;

public class RankActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.rec)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;
    int pagenum = 1, pageSize = 20;
    String type="1";
    private BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder> mAdapterList;
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private List<RankGoodsBean.DataBean> mListData;
    String[] listcode = new String[]{CommonUtils.mGoodsListr1, CommonUtils.mGoodsListr2, CommonUtils.mGoodsListr3, CommonUtils.mGoodsListr4};
    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    public void initView() {
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(RankActivity.this);
        tvTitle.setText(getIntent().getStringExtra("name"));
        type=getIntent().getStringExtra("type");
        initHomeList();
    }
    private void initHomeList() {
        smallLabel.setOnRefreshListener(this);
        smallLabel.setOnLoadMoreListener(this);
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(RankActivity.this);
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
                    //TextView txtgou = helper.itemView.findViewById(R.id.tv_open_time);
                    TextView txtsale = helper.itemView.findViewById(R.id.sale);


                    Glide.with(RankActivity.this).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                    txtname.setText(item.getTitle());
                    txtjuan.setText( item.getCouponPrice() + "元");
                    txtshichang.setText("市场价￥ " + item.getOriginalPrice() + "");
                   // txtgou.setText("勾转专享价￥" + item.getMonthSales() + "");
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
        getData();

    }
    public void getData(){
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", String.valueOf(pagenum));
        paraMap.put("pageSize", "20");
        paraMap.put("rankType", String.valueOf(new Random().nextInt(7)));
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

                            mListData=nineToNineBean.getData();
                            for (int i = 0; i < mListData.size(); i++) {
                                if (i != 0 && i % 10 == 0) {

                                    mListData.get(i).setmData(true);
                                }
                            }

                            if (pagenum == 1) {
                                mAdapterList.setNewData(mListData);
                            } else {
                                mAdapterList.addData(mListData);
                            }

                            if (nineToNineBean.getData().size() != 0) {
                                pagenum++;
                            } else {
                                smallLabel.setNoMoreData(true);
                            }
                            smallLabel.finishLoadMore();

                        } else {
                            smallLabel.finishLoadMore();
                            // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        smallLabel.finishRefresh();
                        smallLabel.finishLoadMore();
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
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pagenum = 1;
        getData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData();
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
}

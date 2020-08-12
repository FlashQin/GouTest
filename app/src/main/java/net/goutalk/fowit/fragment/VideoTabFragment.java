package net.goutalk.fowit.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Activity.VideoInfoActivity;
import net.goutalk.fowit.Bean.GoodsTypeListBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.Adapter.RecyclerViewAdapter;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.VideoListBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.TTAdManagerHolder;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rxhttp.wrapper.param.RxHttp;

import static com.ali.auth.third.core.context.KernelContext.getApplicationContext;

public class VideoTabFragment extends BaseFragment {
    private static String ARG_PARAM1 = "VideoTabFragment";
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smartLayout;
    private String current_type;
    @BindView(R.id.view_fit)
    View viewFit;
    @BindView(R.id.rec)
    RecyclerView recyclerView;
    private BaseQuickAdapter<VideoListBean.DataBean.ListBean, BaseViewHolder> mAdapter;
    RecyclerViewAdapter adapterVideoList;

    int pageNum = 1;
    private TTAdNative mTTAdNative;
    private List<VideoListBean.DataBean.ListBean> mListData;
    String[] listcode = new String[]{CommonUtils.mGoodsListr1, CommonUtils.mGoodsListr2, CommonUtils.mGoodsListr3, CommonUtils.mGoodsListr4};

    @Override
    protected void initView(View view) {
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(getActivity());
        //   initRec();
        initAdpter();

    }

    public void initAdpter() {
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getTabVideo(current_type);
            }
        });
        smartLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getTabVideo(current_type);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<VideoListBean.DataBean.ListBean, BaseViewHolder>(R.layout.item_videoview) {
            @Override
            protected void convert(BaseViewHolder helper, VideoListBean.DataBean.ListBean item) {
                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                FrameLayout mExpressContainer = helper.itemView.findViewById(R.id.banner_container);
                if (item.getmData() == false) {
                    JzvdStd jzvdStd = helper.itemView.findViewById(R.id.videoplayer);
                    ImageView imgshare = helper.itemView.findViewById(R.id.img_share);
                    jzvdStd.setUp(
                            item.getPlayUrl(),
                            item.getVideoName(), Jzvd.SCREEN_NORMAL);
                    Glide.with(getActivity()).load(item.getThumbnail()).into(jzvdStd.posterImageView);
                    if (jzvdStd != null && Jzvd.CURRENT_JZVD != null &&
                            jzvdStd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                        if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                            Jzvd.releaseAllVideos();
                        }
                    }

                    imgshare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Goto(VideoInfoActivity.class, "url", item.getPlayUrl(), "name", item.getVideoName());
                        }
                    });
                }else {
                    LinearLayout linearLayout = helper.itemView.findViewById(R.id.lingoods);
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
//                ImageView imageView=helper.itemView.findViewById(R.id.img_mainpic);
//                TextView txtname=helper.itemView.findViewById(R.id.txtname);
//                imageView.setImageResource(item.getIcon());
//                txtname.setText(item.getName());
                //EmptyView

            }

        });
    }



    public void getTabVideo(String id) {
        RxHttp.get("/video/findPage.json" + id + "&pageNum=" + pageNum + "&pageSize=" + 10)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        smartLayout.finishRefresh();
                        if (scoreBean.getCode() == 0) {
                            VideoListBean bookTypeBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), VideoListBean.class);

                            for (int i = 0; i < mListData.size(); i++) {
                                if (i != 0 && i % 15 == 0) {

                                    mListData.get(i).setmData(true);
                                }
                            }

                            if (pageNum == 1) {

                                mAdapter.setNewData(bookTypeBean.getData().getList());

                            } else {
                                if (bookTypeBean.getData().getList().size() != 0) {
                                    pageNum++;
                                    mAdapter.addData(bookTypeBean.getData().getList());
                                } else {
                                    smartLayout.setNoMoreData(true);
                                }
                            }
                            smartLayout.finishLoadMore();
                        }
                    }
                });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
        // viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_videotab;
    }

    @Override
    protected void fetchData() {
        if (getArguments() != null) {
            current_type = getArguments().getString(ARG_PARAM1);
            getTabVideo(current_type);
        }
    }

    public static VideoTabFragment newInstance(String TYPE) {
        VideoTabFragment fragment = new VideoTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadBannerAd(FrameLayout mExpressContainer) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        int random = new Random().nextInt(4);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(listcode[random]) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
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

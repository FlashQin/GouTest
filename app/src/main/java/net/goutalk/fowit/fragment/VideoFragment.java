package net.goutalk.fowit.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.flyco.tablayout.SlidingTabLayout;
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.VideoTabBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.utils.Urls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/28
 * @modified 20/02/28
 * @description com.haichuang.chefu.ui.fragments
 */
public class VideoFragment extends BaseFragment {

//Ad
//    @BindView(R.id.recyclerview_video)
//    LoadMoreListView recyclerView;
//    @BindView(R.id.smartLayout)
//    SmartRefreshLayout smartLayout;
//    RecyclerViewAdapter adapterVideoList;
//    @BindView(R.id.view_fit)
//    View viewFit;


    private static final int AD_POSITION = 3;
    private static final int LIST_ITEM_COUNT = 5;
    private static int mdislikeid = -1;
    @BindView(R.id.view_fit)
    View viewFit;
    @BindView(R.id.tl_9)
    SlidingTabLayout tl9;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<TTNativeExpressAd> mData;

    private TTAdNative mTTAdNative;
    private RadioGroup mRadioGroupManager;
    private RadioGroup mRadioGroupOri;
    private int mScrollOrientation = RecyclerView.VERTICAL;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private MyAdapter myAdapter;
    //
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
       // viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected void initView(View view) {

        getTab();

        // initRec();
        // initAd();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_videonew;
    }

    @Override
    protected void fetchData() {

    }


    public void getTab() {
        RxHttp.get("/video/category/index.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        if (scoreBean.getCode() == 0) {
                            VideoTabBean bookTypeBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), VideoTabBean.class);

                            for (int i = 0; i < bookTypeBean.getData().size(); i++) {
                                mTitles.add(bookTypeBean.getData().get(i).getTitle());
                                mFragments.add(VideoTabFragment.newInstance(bookTypeBean.getData().get(i).getUrl()));
                            }
                            View decorView = getActivity().getWindow().getDecorView();
                            /** indicator圆角色块 */
                            mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
                            vp.setAdapter(mAdapter);
                            tl9.setViewPager(vp);
                            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    Jzvd.releaseAllVideos();
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                        }
                    }
                });
    }

    private void initRec() {


//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        adapterVideoList = new RecyclerViewAdapter(getActivity());
//        recyclerView.setAdapter(adapterVideoList);
//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(View view) {
//
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(View view) {
//                Jzvd jzvd = view.findViewById(R.id.videoplayer);
//                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
//                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
//                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
//                        Jzvd.releaseAllVideos();
//                    }
//                }
//            }
//        });
//        loadListAd();
    }

    public void initAd() {
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        mTTAdNative = ttAdManager.createAdNative(getActivity().getApplicationContext());
        //申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(getActivity());

        mData = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), mData);
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setLoadMoreListener(new ILoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                loadListAd();
//            }
//        });
        loadListAd();
    }

    /**
     * 加载feed广告
     */
    private void loadListAd() {

        float expressViewWidth = 350;
        float expressViewHeight = 350;

        //step4:创建feed广告请求类型参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("945297102")
                .setSupportDeepLink(true)
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight) //期望模板广告view的size,单位dp
                .setAdCount(3) //请求广告数量为1到3条
                .build();
        //step5:请求广告，调用feed广告异步请求接口，加载到广告后，拿到广告素材自定义渲染
        mTTAdNative.loadNativeExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
//                if (recyclerView != null) {
//                    recyclerView.setLoadingFinish();
//                }
                // TToast.show(NativeExpressListActivity.this, message);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
//                if (recyclerView != null) {
//                    recyclerView.setLoadingFinish();
//                }

                if (ads == null || ads.isEmpty()) {
                    //  TToast.show(NativeExpressListActivity.this, "on FeedAdLoaded: ad is null!");
                    return;
                }

                for (int i = 0; i < LIST_ITEM_COUNT; i++) {
                    mData.add(null);
                }
                bindAdListener(ads);
            }
        });
    }

    private void bindAdListener(final List<TTNativeExpressAd> ads) {
        final int count = mData.size();
        for (TTNativeExpressAd ad : ads) {
            final TTNativeExpressAd adTmp = ad;
            int random = (int) (Math.random() * LIST_ITEM_COUNT) + count - LIST_ITEM_COUNT;
            mData.set(random, adTmp);
            myAdapter.notifyDataSetChanged();

            adTmp.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                @Override
                public void onAdClicked(View view, int type) {
                    mdislikeid = mData.indexOf(adTmp);
                    //TToast.show(NativeExpressListActivity.this, "广告被点击");
                }

                @Override
                public void onAdShow(View view, int type) {
                    //TToast.show(NativeExpressListActivity.this, "广告展示");
                }

                @Override
                public void onRenderFail(View view, String msg, int code) {
                    // TToast.show(NativeExpressListActivity.this, msg + " code:" + code);
                }

                @Override
                public void onRenderSuccess(View view, float width, float height) {
                    //返回view的宽高 单位 dp
                    //  TToast.show(NativeExpressListActivity.this, "渲染成功");
                    myAdapter.notifyDataSetChanged();
                }
            });
            ad.render();

        }

    }

    @SuppressWarnings("CanBeFinal")
    private static class MyAdapter extends BaseAdapter {

        private static final int ITEM_VIEW_TYPE_NORMAL = 0;
        private static final int ITEM_VIEW_TYPE_GROUP_PIC_AD = 1;
        private static final int ITEM_VIEW_TYPE_SMALL_PIC_AD = 2;
        private static final int ITEM_VIEW_TYPE_LARGE_PIC_AD = 3;
        private static final int ITEM_VIEW_TYPE_VIDEO = 4;
        private static final int ITEM_VIEW_TYPE_VERTICAL_IMG = 5;//竖版图片
        private static final int ITEM_VIEW_TYPE_VIDEO_VERTICAL = 6;//竖版视频

        private int mVideoCount = 0;


        private List<TTNativeExpressAd> mData;
        private Context mContext;

        private Map<AdViewHolder, TTAppDownloadListener> mTTAppDownloadListenerMap = new WeakHashMap<>();

        public MyAdapter(Context context, List<TTNativeExpressAd> data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size(); // for test
        }

        @Override
        public TTNativeExpressAd getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //信息流广告的样式，有大图、小图、组图和视频，通过ad.getImageMode()来判断
        @Override
        public int getItemViewType(int position) {
            TTNativeExpressAd ad = getItem(position);
            if (ad == null) {
                return ITEM_VIEW_TYPE_NORMAL;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
                return ITEM_VIEW_TYPE_SMALL_PIC_AD;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
                return ITEM_VIEW_TYPE_LARGE_PIC_AD;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
                return ITEM_VIEW_TYPE_GROUP_PIC_AD;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
                return ITEM_VIEW_TYPE_VIDEO;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VERTICAL_IMG) {
                return ITEM_VIEW_TYPE_VERTICAL_IMG;
            } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO_VERTICAL) {
                return ITEM_VIEW_TYPE_VIDEO_VERTICAL;
            } else {
                ToastUtils.showShort("图片展示样式错误");
                return ITEM_VIEW_TYPE_NORMAL;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 7;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TTNativeExpressAd ad = getItem(position);
            switch (getItemViewType(position)) {
                case ITEM_VIEW_TYPE_SMALL_PIC_AD:
                case ITEM_VIEW_TYPE_LARGE_PIC_AD:
                case ITEM_VIEW_TYPE_GROUP_PIC_AD:
                case ITEM_VIEW_TYPE_VERTICAL_IMG:
                case ITEM_VIEW_TYPE_VIDEO:
                case ITEM_VIEW_TYPE_VIDEO_VERTICAL:
                    return getVideoView(convertView, parent, ad);
                default:
                    return getNormalView(convertView, parent, position);
            }
        }

        //渲染视频广告，以视频广告为例，以下说明
        @SuppressWarnings("RedundantCast")
        private View getVideoView(View convertView, ViewGroup parent, @NonNull final TTNativeExpressAd ad) {
            final AdViewHolder adViewHolder;
            try {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_native_express, parent, false);
                    adViewHolder = new AdViewHolder();
                    adViewHolder.videoView = (FrameLayout) convertView.findViewById(R.id.iv_listitem_express);
                    adViewHolder.videoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mdislikeid = mData.indexOf(ad);
                        }
                    });
                    convertView.setTag(adViewHolder);
                } else {
                    adViewHolder = (AdViewHolder) convertView.getTag();
                }

                //绑定广告数据、设置交互回调
                bindData(convertView, adViewHolder, ad);
                if (adViewHolder.videoView != null) {
                    //获取视频播放view,该view SDK内部渲染，在媒体平台可配置视频是否自动播放等设置。
                    View video = ad.getExpressAdView();
                    if (video != null) {
                        if (video.getParent() == null) {
                            adViewHolder.videoView.removeAllViews();
                            adViewHolder.videoView.addView(video);
//                            ad.render();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }

        /**
         * 非广告list
         *
         * @param convertView
         * @param parent
         * @param position
         * @return
         */
        @SuppressWarnings("RedundantCast")
        @SuppressLint("SetTextI18n")
        private View getNormalView(View convertView, ViewGroup parent, int position) {
            NormalViewHolder normalViewHolder;
            if (convertView == null) {
                normalViewHolder = new NormalViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_videoview, parent, false);
                normalViewHolder.idle = (JzvdStd) convertView.findViewById(R.id.videoplayer);
                convertView.setTag(normalViewHolder);
            } else {
                normalViewHolder = (NormalViewHolder) convertView.getTag();
            }
            //  normalViewHolder.idle.setText("ListView item " + position);
            normalViewHolder.idle.setUp(
                    Urls.videoUrls[(int) (Math.random() * Urls.videoUrls.length)][(int) (Math.random() * Urls.videoUrls.length)],
                    Urls.videoTitles[(int) (Math.random() * Urls.videoTitles.length)][(int) (Math.random() * Urls.videoTitles.length)], Jzvd.SCREEN_NORMAL);
            Glide.with(normalViewHolder.idle.getContext()).load(Urls.videoPosters[(int) (Math.random() * Urls.videoPosters.length)][(int) (Math.random() * Urls.videoPosters.length)]).into(normalViewHolder.idle.posterImageView);
            if (normalViewHolder.idle != null && Jzvd.CURRENT_JZVD != null &&
                    normalViewHolder.idle.jzDataSource.containsTheUrl("http://jzvd.nathen.cn/6ea7357bc3fa4658b29b7933ba575008/fbbba953374248eb913cb1408dc61d85-5287d2089db37e62345123a1be272f8b.mp4")) {
                if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                    Jzvd.releaseAllVideos();
                }
            }
            return convertView;
        }

        /**
         * 设置广告的不喜欢，注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
         *
         * @param ad
         * @param customStyle 是否自定义样式，true:样式自定义
         */
        private void bindDislike(final TTNativeExpressAd ad, boolean customStyle) {
            if (customStyle) {
                //使用自定义样式
                List<FilterWord> words = ad.getFilterWords();
                if (words == null || words.isEmpty()) {
                    return;
                }


                final DislikeDialog dislikeDialog = new DislikeDialog(mContext, words);
                dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                    @Override
                    public void onItemClick(FilterWord filterWord) {
                        //屏蔽广告
                        // ToastUtils.showShort("点击 " + filterWord.getName());
                        //用户选择不喜欢原因后，移除广告展示
                        mData.remove(mdislikeid);
                        notifyDataSetChanged();
                    }
                });
                ad.setDislikeDialog(dislikeDialog);
                return;
            }
            //使用默认模板中默认dislike弹出样式
            ad.setDislikeCallback((Activity) mContext, new TTAdDislike.DislikeInteractionCallback() {
                @Override
                public void onSelected(int position, String value) {
                    //    ToastUtils.showShort( "点击 " + value);
                    //用户选择不喜欢原因后，移除广告展示
                    mData.remove(mdislikeid);
                    notifyDataSetChanged();
                }

                @Override
                public void onCancel() {
                    ToastUtils.showShort("点击取消 ");
                }

                @Override
                public void onRefuse() {

                }
            });
        }

        private void bindData(View convertView, final AdViewHolder adViewHolder, TTNativeExpressAd ad) {
            //设置dislike弹窗，这里展示自定义的dialog
            bindDislike(ad, false);
            switch (ad.getInteractionType()) {
                case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
                    bindDownloadListener(adViewHolder, ad);
                    break;
            }
        }


        private void bindDownloadListener(final AdViewHolder adViewHolder, TTNativeExpressAd ad) {
            TTAppDownloadListener downloadListener = new TTAppDownloadListener() {
                private boolean mHasShowDownloadActive = false;

                @Override
                public void onIdle() {
                    if (!isValid()) {
                        return;
                    }
                    // ToastUtils.showShort("点击广告开始下载");
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                    if (!isValid()) {
                        return;
                    }
                    if (!mHasShowDownloadActive) {
                        mHasShowDownloadActive = true;
                        //  ToastUtils.showShort(appName + " 下载中，点击暂停", Toast.LENGTH_LONG);
                    }
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                    if (!isValid()) {
                        return;
                    }
                    //  ToastUtils.showShort( appName + " 下载暂停", Toast.LENGTH_LONG);

                }

                @Override
                public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                    if (!isValid()) {
                        return;
                    }
                    //   ToastUtils.showShort( appName + " 下载失败，重新下载", Toast.LENGTH_LONG);
                }

                @Override
                public void onInstalled(String fileName, String appName) {
                    if (!isValid()) {
                        return;
                    }
                    //   ToastUtils.showShort(appName + " 安装完成，点击打开", Toast.LENGTH_LONG);
                }

                @Override
                public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                    if (!isValid()) {
                        return;
                    }
                    //   ToastUtils.showShort( appName + " 下载成功，点击安装", Toast.LENGTH_LONG);

                }

                @SuppressWarnings("BooleanMethodIsAlwaysInverted")
                private boolean isValid() {
                    return mTTAppDownloadListenerMap.get(adViewHolder) == this;
                }
            };
            //一个ViewHolder对应一个downloadListener, isValid判断当前ViewHolder绑定的listener是不是自己
            ad.setDownloadListener(downloadListener); // 注册下载监听器
            mTTAppDownloadListenerMap.put(adViewHolder, downloadListener);
        }


        private static class AdViewHolder {
            FrameLayout videoView;
        }

        private static class NormalViewHolder {
            JzvdStd idle;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mData != null) {
            for (TTNativeExpressAd ad : mData) {
                if (ad != null) {
                    ad.destroy();
                }
            }
        }
        mHandler.removeCallbacksAndMessages(null);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragments.get(position);
        }
    }
}
package net.goutalk.fowit.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Activity.VideoInfoActivity;
import net.goutalk.fowit.R;
import net.goutalk.fowit.Adapter.RecyclerViewAdapter;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.VideoListBean;
import net.goutalk.fowit.net.BaseObserver;

import java.util.Objects;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rxhttp.wrapper.param.RxHttp;

public class VideoTabFragment extends BaseFragment {
    private static String ARG_PARAM1 = "TYPE_FRAGMENT";
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

    @Override
    protected void initView(View view) {

        //   initRec();
        initAdpter();

    }

    public void initAdpter() {
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum=1;
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

                        Goto(VideoInfoActivity.class,"url",item.getPlayUrl(),"name",item.getVideoName());
                    }
                });
//                ImageView imageView=helper.itemView.findViewById(R.id.img_mainpic);
//                TextView txtname=helper.itemView.findViewById(R.id.txtname);
//                imageView.setImageResource(item.getIcon());
//                txtname.setText(item.getName());
                //EmptyView

            }

        });
    }

    private void initRec() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVideoList = new RecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(adapterVideoList);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
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






}

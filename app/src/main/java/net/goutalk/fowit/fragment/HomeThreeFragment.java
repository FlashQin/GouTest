package net.goutalk.fowit.fragment;

import android.graphics.Color;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.youth.banner.Banner;

import net.goutalk.fowit.Adapter.OneAdapter;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.GlideImageLoader;
import net.goutalk.fowit.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author geyifeng
 * @date 2017/5/12
 */
public class HomeThreeFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private OneAdapter mOneAdapter;
    private List<String> mItemList = new ArrayList<>();
    private List<String> mImages = new ArrayList<>();
    private int bannerHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one_home;
    }

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initView(View view) {
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();

        refreshLayout.setEnableLoadmore(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);
        mOneAdapter = new OneAdapter();
        mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRv.setAdapter(mOneAdapter);
        addHeaderView();
        mOneAdapter.setPreLoadNumber(1);
        mOneAdapter.setNewData(mItemList);
    }

    private void addHeaderView() {
        final ArrayList<String> mImages = new ArrayList<>();
        mImages.add(Urls.videoPosterList[0]);
        mImages.add(Urls.videoPosterList[2]);
        mImages.add(Urls.videoPosterList[1]);
        if (mImages != null && mImages.size() > 0) {
            View headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, (ViewGroup) mRv.getParent(), false);
            Banner banner = headView.findViewById(R.id.banner);
            banner.setImages(mImages)
                    .setImageLoader(new GlideImageLoader())
                    .setDelayTime(5000)
                    .start();
            mOneAdapter.addHeaderView(headView);
            ViewGroup.LayoutParams bannerParams = banner.getLayoutParams();
            ViewGroup.LayoutParams titleBarParams = mToolbar.getLayoutParams();
            bannerHeight = bannerParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(getActivity());
        }
    }
    @Override
    protected void initData() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (totalDy <= bannerHeight) {
                    float alpha = (float) totalDy / bannerHeight;
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(getActivity(), R.color.colorPrimary), alpha));
                } else {
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(getActivity(), R.color.colorPrimary), 1));
                }
            }
        });
        mOneAdapter.setOnLoadMoreListener(() -> new Handler().postDelayed(() -> {
            mOneAdapter.addData(addData());
            if (mItemList.size() == 100) {
                mOneAdapter.loadMoreEnd();
            } else {
                mOneAdapter.loadMoreComplete();
            }
        }, 2000), mRv);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> {
                    mItemList.clear();
                    mItemList.addAll(newData());
                    mOneAdapter.setNewData(mItemList);
                    refreshLayout.finishRefreshing();
                    if (mToolbar != null) {
                        mToolbar.setVisibility(View.VISIBLE);
                    }
                }, 2000);
            }

            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
                if (mToolbar != null) {
                    mToolbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                if (mToolbar != null) {
                    if (Math.abs(fraction - 1.0f) > 0) {
                        mToolbar.setVisibility(View.VISIBLE);
                    } else {
                        mToolbar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private List<String> addData() {
        List<String> data = new ArrayList<>();
        for (int i = mItemList.size() + 1; i <= mItemList.size() + 20; i++) {
            data.add("item" + i);
        }
        return data;
    }

    private List<String> newData() {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("item" + i);
        }
        return data;
    }
}

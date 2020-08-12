package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;
import net.goutalk.fowit.fragment.ServiceOrderFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ServiceOrderActivity extends BaseActivity {

    @BindView(R.id.xTabLayout)
    XTabLayout xTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right_title)
    TextView tv_right_title;
    @BindView(R.id.layout_search)
    FrameLayout layoutSearch;

    private List<BaseFragment> fragments;
    private List<String> titles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_xtab_viewpager;
    }

    @Override
    public void initView() {
        layoutSearch.setVisibility(View.GONE);
        //init_title
        tv_title.setText("订单列表");
        xTabLayout.setVisibility(View.VISIBLE);
        //订单状态(1:代付款,2:待发货或待服务,3:待收货,4:待评价或已服务,5:交易成功,6:申请退款,7:申请换货,8:已退款,9:已取消,10:已关闭
        titles = Arrays.asList("全部", "已结算", "已付款","已失效");
        fragments = Arrays.asList(
                ServiceOrderFragment.newInstance(0),
                ServiceOrderFragment.newInstance(3),
                ServiceOrderFragment.newInstance(4),
                ServiceOrderFragment.newInstance(5));

        viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        viewpager.setOffscreenPageLimit(5);
        xTabLayout.setupWithViewPager(viewpager);
        for (int i = 0; i < fragments.size(); i++) {
            Objects.requireNonNull(xTabLayout.getTabAt(i)).setText(titles.get(i));
        }
    }

    @OnClick({R.id.img_back, R.id.tv_right_title})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_back) {
            onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
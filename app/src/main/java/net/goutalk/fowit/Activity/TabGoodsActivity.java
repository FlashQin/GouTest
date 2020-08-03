package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;
import net.goutalk.fowit.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabGoodsActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.view_fit)
    View viewFit;
    @BindView(R.id.tl_9)
    SlidingTabLayout tl9;
    @BindView(R.id.vp)
    ViewPager vp;

    //
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    String[] name={"家具","家具","家具","家具","家具","家具","家具","家具","家具","家具","家具"};
    @Override
    public int getLayoutId() {
        return R.layout.activity_tabgoods;
    }

    @Override
    public void initView() {


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

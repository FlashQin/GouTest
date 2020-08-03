package net.goutalk.fowit.Activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ScreenUtils;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;
import net.goutalk.fowit.fragment.GuideFragment1;
import net.goutalk.fowit.fragment.GuideFragment2;
import net.goutalk.fowit.fragment.GuideFragment3;
import net.goutalk.fowit.fragment.GuideFragment5;
import com.jaeger.library.StatusBarUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;


public class GuideActivity extends BaseActivity {

    //0  16/9   1 18/9
    public static int TYPE_SCREEN = 0;

    @BindView(R.id.circleIndicator)
    CircleIndicator circleIndicator;
    @BindView(R.id.bannerViewPager)
    ViewPager bannerViewPager;

    private List<BaseFragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);

        //判断长宽比
        int screenWidth = ScreenUtils.getScreenWidth();
        int screenHeight = ScreenUtils.getScreenHeight();
        double ra = (double) screenHeight / screenWidth;
        if (ra > 1.8) TYPE_SCREEN = 1;

        //这个是三个引导页
        //GuideFragment3按钮跳转
        fragments = Arrays.asList(new GuideFragment1(), new GuideFragment2(), new GuideFragment3(), new GuideFragment5());
        bannerViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        circleIndicator.setViewPager(bannerViewPager);
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == fragments.size() - 1) {
                    circleIndicator.setVisibility(View.GONE);
                } else {
                    circleIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //零时写在这里便于调试
//        Goto(LoginActivity.class);
    }
}

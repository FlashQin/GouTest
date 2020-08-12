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

import net.goutalk.fowit.Activity.SerchGoodsActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.GoodsTypeBean;
import net.goutalk.fowit.Bean.VideoTabBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.TTAdManagerHolder;
import net.goutalk.fowit.utils.Urls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/28
 * @modified 20/02/28
 * @description com.haichuang.chefu.ui.fragments
 */
public class NewHomeFragment extends BaseFragment {

    @BindView(R.id.view_fit)
    View viewFit;
    @BindView(R.id.tl_9)
    SlidingTabLayout tl9;
    @BindView(R.id.vp)
    ViewPager vp;

    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    String[] titles=new String[]{"美食","居家日用","女装","美妆","数码家电","鞋品","男装","内衣","母婴","家装家纺","文娱车品","配饰","箱包","户外运动"};
    String[] cids=new String[]{"6","4","1","3","8","5","9","10","2","14","7","12","11","13"};
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
        return R.layout.fragment_goodsnew;
    }

    @Override
    protected void fetchData() {

    }


    public void getTab() {
//        TreeMap<String, String> paraMap = new TreeMap<>();
//        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
//        paraMap.put("version", "v1.2.3");
//        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
//        String p = null;
//        try {
//            p = ApiTest.buildQuery(paraMap, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        RxHttp.get(Urls.urltype + p)
//                .asObject(BaseMsgBean.class)
//                .as(RxLife.asOnMain(this))
//                .subscribe(new BaseObserver<BaseMsgBean>() {
//                    @Override
//                    public void onNext(BaseMsgBean scoreBean) {
//
//                        if (scoreBean.getCode() == 0) {
                          //  GoodsTypeBean bookTypeBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), GoodsTypeBean.class);

                            mTitles.add("优选");
                            mFragments.add(new HomeFragment());
                            for (int i = 0; i < titles.length; i++) {
                                mTitles.add(titles[i]);
                                mFragments.add(GoodsTabFragment.newInstance(cids[i]));
                            }
                            View decorView = getActivity().getWindow().getDecorView();
                            /** indicator圆角色块 */
                            mAdapter = new MyPagerAdapter(getChildFragmentManager());
                            vp.setAdapter(mAdapter);
                            tl9.setViewPager(vp);
                            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                   // Jzvd.releaseAllVideos();
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                       // }
//                    }
//                });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

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
    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        Goto(SerchGoodsActivity.class);
    }
}
package net.goutalk.fowit.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.androidkun.xtablayout.XTabLayout;
import com.jaeger.library.StatusBarUtil;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.fragment.GetMoneyFragment;
import net.goutalk.fowit.fragment.PostMoneyFragment;
import net.goutalk.fowit.wigde.CustomViewPager;
import net.qiujuer.genius.ui.widget.Button;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountInfoActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.layout_edit)
    LinearLayout layoutEdit;
    @BindView(R.id.layout_header_new)
    FrameLayout layoutHeaderNew;
    @BindView(R.id.xTabLayout)
    XTabLayout xTabLayout;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.img_all_select)
    CheckBox imgAllSelect;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.btn_cancel_collection)
    Button btnCancelCollection;
    @BindView(R.id.layout_bottom)
    ConstraintLayout layoutBottom;
    private List<BaseFragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.layout_account_info;
    }


    @Override
    protected void SetStatusBar() {
        super.SetStatusBar();
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void initView() {
        tvTitle.setText("账户明细");
        initTabLayout();
    }

    private void initTabLayout() {
        if (fragments == null)
            fragments = Arrays.asList(new GetMoneyFragment(), new PostMoneyFragment());

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
        });
        xTabLayout.setupWithViewPager(viewpager);
        Objects.requireNonNull(xTabLayout.getTabAt(0)).setText("收入明细");
        Objects.requireNonNull(xTabLayout.getTabAt(1)).setText("指出明细");
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
}

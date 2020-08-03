package net.goutalk.fowit.fragment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.MainActivity;
import net.goutalk.fowit.R;
import com.jaeger.library.StatusBarUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static androidx.constraintlayout.motion.widget.KeyPosition.TYPE_SCREEN;


/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/27
 * @modified 20/02/27
 * @description com.haichuang.chefu.ui.fragments
 */
public class GuideFragment5 extends BaseFragment {

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide_last;
    }

    @Override
    protected void initView(View view) {
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        if (TYPE_SCREEN == 0) {
            Glide.with(Objects.requireNonNull(getContext())).load(R.mipmap.yingdaoye_4).into(img);
        } else {
            Glide.with(Objects.requireNonNull(getContext())).load(R.mipmap.yingdaoye_4).into(img);
        }
    }

    @Override
    protected void fetchData() {

    }

    @OnClick(R.id.btn_goto_main)
    public void onViewClicked() {
        //游客模式直接跳转首页
        Goto(MainActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
//        if (!SPUtils.getInstance().getBoolean("isLogin", false)) {
//            Goto(LoginActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
//            Objects.requireNonNull(getActivity()).finish();
//        } else {
//            Goto(MainActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
//            Objects.requireNonNull(getActivity()).finish();
//        }
    }
}

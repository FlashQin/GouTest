package net.goutalk.fowit.fragment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseFragment;

import java.util.Objects;

import butterknife.BindView;

import static androidx.constraintlayout.motion.widget.KeyPosition.TYPE_SCREEN;


/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/27
 * @modified 20/02/27
 * @description com.haichuang.chefu.ui.fragments
 */
public class GuideFragment1 extends BaseFragment {

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView(View view) {
        if (TYPE_SCREEN == 0) {
            Glide.with(Objects.requireNonNull(getContext())).load(R.mipmap.yingdaoye_1).into(img);
        } else {
            Glide.with(Objects.requireNonNull(getContext())).load(R.mipmap.yingdaoye_1).into(img);
        }
    }

    @Override
    protected void fetchData() {

    }
}

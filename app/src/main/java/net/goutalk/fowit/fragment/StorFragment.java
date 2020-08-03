package net.goutalk.fowit.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseFragment;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/28
 * @modified 20/02/28
 * @description com.haichuang.chefu.ui.fragments
 */
public class StorFragment extends BaseFragment {


    @BindView(R.id.layout_web)
    LinearLayout layoutWeb;

    private Object mAgentWeb;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
         //viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected void initView(View view) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layoutWeb, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://cms.51wit.cn/");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stor;
    }

    @Override
    protected void fetchData() {

    }


}

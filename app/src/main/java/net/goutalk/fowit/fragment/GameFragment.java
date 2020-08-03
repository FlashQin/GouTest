package net.goutalk.fowit.fragment;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cmcm.cmgame.GameView;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.utils.SpacesItemDecoration;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;

public class GameFragment extends BaseFragment {


    @BindView(R.id.tv_location)
    ImageView tvLocation;
    @BindView(R.id.tv_search)
    LinearLayout tvSearch;
    @BindView(R.id.img_msg)
    ImageView imgMsg;
    @BindView(R.id.linnn)
    LinearLayout linnn;
    @BindView(R.id.recyclerview_home)
    RecyclerView rec_base;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @BindView(R.id.gameView)
    GameView gameView;
    @BindView(R.id.sdfdsf)
    LinearLayout sdfdsf;

    @BindView(R.id.img_bac)
    ImageView imgBac;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
     //   viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected void initView(View view) {

        // 目前只支持anrdoid 5.0或以上
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(getActivity(), "不支持低版本，仅支持android 5.0或以上版本!", Toast.LENGTH_LONG).show();
        }

        // 游戏入口样式二，把默认游戏中心view添加进媒体指定界面，如下2行为核心代码

        gameView.inflate(getActivity());
        //initRec();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void fetchData() {

    }


    private void initRec() {
        //初始化RecycleView
        rec_base.setLayoutManager(new LinearLayoutManager(getContext()));
        rec_base.addItemDecoration(new SpacesItemDecoration(15));
        rec_base.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home, Arrays.asList("0", "1", "2", "3", "4", "5")) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

                //子RecycleView
                BaseQuickAdapter<String, BaseViewHolder> madapter;
                RecyclerView rec_sub = helper.getView(R.id.recyclerview_home_item);
                rec_sub.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rec_sub.addItemDecoration(new SpacesItemDecoration(15));
                rec_sub.setAdapter(madapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_item, Arrays.asList("0", "1", "2", "3")) {
                    @Override
                    protected void convert(BaseViewHolder helper, String item) {

                    }
                });

                //EmptyView
//        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rec_emp_shop_car_layout, null);
//        inflate.findViewById(R.id.btn_GG).setOnClickListener(v -> Goto(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        mAdapter.setEmptyView(inflate);
            }

        });
    }

}

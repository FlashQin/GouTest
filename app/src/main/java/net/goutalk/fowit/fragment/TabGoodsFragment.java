package net.goutalk.fowit.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;

import butterknife.BindView;

public class TabGoodsFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.view_fit)
    View viewFit;
    @BindView(R.id.rec)
    RecyclerView rec;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;

    @Override
    protected void initView(View view) {

        smallLabel.setOnLoadMoreListener(this);
        smallLabel.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goodstab;
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}

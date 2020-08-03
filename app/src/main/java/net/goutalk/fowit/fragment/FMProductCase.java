package net.goutalk.fowit.fragment;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.SpacesItemDecoration;

import butterknife.BindView;


public class FMProductCase extends BaseFragment {


    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.webView)
    WebView webView;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapterList;

    @Override
    protected void initView(View view) {

        initpic();

    }

    private void initpic() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        rvDetail.setLayoutManager(ms);
        rvDetail.addItemDecoration(new SpacesItemDecoration(15));
        rvDetail.setAdapter(mAdapterList = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_pic) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img);

                Glide.with(getActivity()).load(CommonUtils.checkImgUrl(item)).into(imageView);

            }

        });
        mAdapterList.setNewData(CommonUtils.imgsgoodspic);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_body_detail;
    }

    @Override
    protected void fetchData() {

    }


}

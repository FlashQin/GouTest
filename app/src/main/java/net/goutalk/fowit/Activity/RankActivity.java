package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.RankGoodsBean;
import net.goutalk.fowit.Bean.ZoreBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.SpacesItemDecoration;
import net.goutalk.fowit.utils.Urls;

import java.io.IOException;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class RankActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.rec)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;
    int pagenum = 1, pageSize = 20;
    String type="1";
    private BaseQuickAdapter<ZoreBean.DataBean.ListBean, BaseViewHolder> mAdapterList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    public void initView() {

        tvTitle.setText(getIntent().getStringExtra("name"));
        type=getIntent().getStringExtra("type");
        initHomeList();
    }
    private void initHomeList() {
        smallLabel.setOnRefreshListener(this);
        smallLabel.setOnLoadMoreListener(this);
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(RankActivity.this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<ZoreBean.DataBean.ListBean, BaseViewHolder>(R.layout.item_zreo_layout) {
            @Override
            protected void convert(BaseViewHolder helper, ZoreBean.DataBean.ListBean item) {


                ImageView imageView=helper.itemView.findViewById(R.id.img);
                TextView txtname=helper.itemView.findViewById(R.id.tv_name);
                TextView txtjuan=helper.itemView.findViewById(R.id.tv_sell_count);
                TextView txtshichang=helper.itemView.findViewById(R.id.tv_location);
               // TextView txtgou=helper.itemView.findViewById(R.id.tv_open_time);

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                Glide.with(RankActivity.this).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getTitle());
                txtjuan.setText("卷后价 "+item.getActualPrice() + "");
                txtshichang.setText("天猫商城价￥ "+item.getOriginalPrice() + "");
             //   txtgou.setText("勾转专享价￥"+item.getMonthSales() + "");

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });
        getData();

    }
    public void getData(){
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", String.valueOf(pagenum));
        paraMap.put("pageSize", "20");
        paraMap.put("priceLowerLimit", "2");
        paraMap.put("priceUpperLimit", "10");
        paraMap.put("commissionRateLowerLimit", "90");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urlzroe + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            ZoreBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), ZoreBean.class);

                            if (pagenum == 1) {
                                mAdapterList.setNewData(nineToNineBean.getData().getList());
                            } else {
                                mAdapterList.addData(nineToNineBean.getData().getList());
                            }

                            if (nineToNineBean.getData().getList().size() != 0) {
                                pagenum++;
                            } else {
                                smallLabel.setNoMoreData(true);
                            }
                            smallLabel.finishLoadMore();

                        } else {
                            smallLabel.finishLoadMore();
                            // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        smallLabel.finishRefresh();
                        smallLabel.finishLoadMore();
                    }
                });
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
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pagenum = 1;
        getData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData();
    }
}

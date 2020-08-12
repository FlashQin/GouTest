package net.goutalk.fowit.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
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

import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LoginBean;
import net.goutalk.fowit.Bean.OderListBean;
import net.goutalk.fowit.MainActivity;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ACache;
import net.goutalk.fowit.utils.SpacesItemDecoration;

import java.util.Objects;

import butterknife.BindView;
import rxhttp.wrapper.param.RxHttp;

import static net.goutalk.fowit.utils.CommonUtils.checkImgUrl;


public class ServiceOrderFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {

    private static final String ARG_PARAM1 = "TYPE_FRAGMENT";

    @BindView(R.id.rec)
    RecyclerView rec_base;

    int pageNum = 1;
    @BindView(R.id.smallLabel_ad)
    SmartRefreshLayout smallLabelAd;
    private int current_type;
    private BaseQuickAdapter<OderListBean.DataBean.ListBean, BaseViewHolder> mAdapter;
    LoginBean user_bean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragemnt_only_recyclewview_layout;
    }

    public static ServiceOrderFragment newInstance(int TYPE) {
        ServiceOrderFragment fragment = new ServiceOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        user_bean = GsonUtils.fromJson(ACache.get(Utils.getApp()).getAsString("USER_BEAN"), LoginBean.class);

        initRec();
        smallLabelAd.setOnLoadMoreListener(this);
        smallLabelAd.setOnRefreshListener(this);


    }

    private void getNetData() {
        //不同的Fragment去加载不同的数据
        //订单状态(1:代付款,2:待发货或待服务,3:待收货,4:待评价或已服务,5:交易成功,6:申请退款,7:申请换货,8:已退款,9:已取消,10:已关闭
        RxHttp.postForm("/ecOrder/findPage.json?orderStatus=" + current_type + "&pageNum=" + pageNum + "&pageSize=20")
//                .add("pageNum", pageNum)
//                .add("pageSize", 10)
//                .add("status", current_type == 0 ? "" : current_type)
//                .add("type", "")         //类型(分类管理中的类型id)
//                .add("goodsType", 1)    //订单类型(1:商城订单,2:服务订单,3:广告位订单
//                .add("dealType", "")     //商品类型(1:原价,2:会员价,3:团购,4:秒杀)
//                .add("trade", "1")    //交易类型(1:人民    币交易,2:积分交易单
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean baseMsgBean) {

                        smallLabelAd.finishRefresh();
                        smallLabelAd.finishLoadMore();
                        if (baseMsgBean.getCode() == 0) {
                            OderListBean bean = JSONObject.parseObject(JSONObject.toJSONString(baseMsgBean), OderListBean.class);


                            if (pageNum == 1) {
                                mAdapter.setNewData(bean.getData().getList());
                            } else {
                                mAdapter.addData(bean.getData().getList());
                            }
                            if (bean.getData().getList().size() >20) {
                                pageNum++;
                            } else {
                                smallLabelAd.setNoMoreData(true);
                            }


                        }
                    }
                });
    }

    private void initRec() {
        //初始化RecycleView
        rec_base.setLayoutManager(new LinearLayoutManager(getContext()));
        rec_base.addItemDecoration(new SpacesItemDecoration(15));
        rec_base.setAdapter(mAdapter = new BaseQuickAdapter<OderListBean.DataBean.ListBean, BaseViewHolder>(R.layout.item_oder_list3) {
            @Override
            protected void convert(BaseViewHolder helper, OderListBean.DataBean.ListBean item1) {
                try {
                    RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                    ImageView imgpic = helper.itemView.findViewById(R.id.img_pic);
                    Glide.with(getActivity()).load("https:" + item1.getItemImg()).apply(options).into(imgpic);
                    TextView txtnum = helper.itemView.findViewById(R.id.txtnums);
                    TextView stasu = helper.itemView.findViewById(R.id.txtstatus);
                    TextView title = helper.itemView.findViewById(R.id.txttitle);
                    TextView time = helper.itemView.findViewById(R.id.txttime);
                    TextView price = helper.itemView.findViewById(R.id.txtprice);
                    TextView fanli = helper.itemView.findViewById(R.id.txtfanli);

                    txtnum.setText("订单号:" + item1.getOrderNo());
                    title.setText(item1.getItemTitle());
                    time.setText("创建时间:" + item1.getPaidTime());
                    price.setText("实付款￥" + item1.getTotalPrice());
                    fanli.setText("预估收入￥" + item1.getShareMemberFee());
                    if (item1.getOrderStatus() == 3) {
                        stasu.setText("已结算");
                    }
                    if (item1.getOrderStatus() == 4) {
                        stasu.setText("已付款");
                    }
                    if (item1.getOrderStatus() == 51) {
                        stasu.setText("已失效");
                    }


                    // tvname.setText(item1.getItems().get(0).getMallGoods().getShop().getName());
                    //   TextView tccount = helper.itemView.findViewById(R.id.tv_count_all);
                    // tccount.setText("共" + item1.getItems().size() + "件商品" + "   合计:" + "￥" + item1.getPrice());
                } catch (NullPointerException e) {

                }
                //整个item的点击事件
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            }
        });
        //EmptyView
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rec_emp_ping_layout, null);
        inflate.findViewById(R.id.btn_GG).setOnClickListener(v -> Goto(MainActivity.class));
        mAdapter.setEmptyView(inflate);


    }

    @Override
    protected void fetchData() {
        if (getArguments() != null) {
            current_type = getArguments().getInt(ARG_PARAM1);
            getNetData();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        getNetData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getNetData();
    }
}

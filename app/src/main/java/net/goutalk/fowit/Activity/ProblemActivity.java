package net.goutalk.fowit.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.PromBean;
import net.goutalk.fowit.net.BaseObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class ProblemActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;
    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;
    private BaseQuickAdapter<PromBean.DataBean.HotListBean, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<PromBean.DataBean.CategoryListBean, BaseViewHolder> mAdapter1;

    @Override
    public int getLayoutId() {
        return R.layout.layout_problem;
    }


    @Override
    protected void SetStatusBar() {
        super.SetStatusBar();
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void initView() {
        tvTitle.setText("常见问题");
        initRec();
        getdata();
    }

    private void initRec() {
        //初始化RecycleView
        recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerview1.addItemDecoration(new SpacesItemDecoration(15));
        recyclerview1.setAdapter(mAdapter = new BaseQuickAdapter<PromBean.DataBean.HotListBean, BaseViewHolder>(R.layout.item_promble1) {
            @Override
            protected void convert(BaseViewHolder helper, PromBean.DataBean.HotListBean item) {
                TextView txttitle = helper.getView(R.id.txtname);
                txttitle.setText(item.getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(ProInfoActivity.class,"text",item.getTitle());
                    }
                });
                //子RecycleView
                //EmptyView
  //        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rec_emp_shop_car_layout, null);
//        inflate.findViewById(R.id.btn_GG).setOnClickListener(v -> Goto(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        mAdapter.setEmptyView(inflate);
            }

        });

        //初始化RecycleView
        recyclerview2.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerview2.addItemDecoration(new SpacesItemDecoration(15));
        recyclerview2.setAdapter(mAdapter1 = new BaseQuickAdapter<PromBean.DataBean.CategoryListBean, BaseViewHolder>(R.layout.item_promble1) {
            @Override
            protected void convert(BaseViewHolder helper, PromBean.DataBean.CategoryListBean item) {
                TextView txttitle = helper.getView(R.id.txtname);

                txttitle.setText(item.getTitle());

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(ProInfoActivity.class,"text",item.getTitle());
                    }
                });
                //子RecycleView


                //EmptyView
//        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rec_emp_shop_car_layout, null);
//        inflate.findViewById(R.id.btn_GG).setOnClickListener(v -> Goto(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        mAdapter.setEmptyView(inflate);
            }

        });
    }

    public void getdata() {
        //签到
        RxHttp.postForm("/systemHelp/category/index.json")

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        if (scoreBean.getCode() == 0) {
                            PromBean defalutAddressBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), PromBean.class);
                            mAdapter.addData(defalutAddressBean.getData().getHotList());
                            mAdapter1.addData(defalutAddressBean.getData().getCategoryList());
                        }
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
}

package net.goutalk.fowit.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.GetMoneyBean;
import net.goutalk.fowit.net.BaseObserver;

import butterknife.BindView;
import rxhttp.wrapper.param.RxHttp;

public class GetMoneyFragment extends BaseFragment {
    @BindView(R.id.rec)
    SwipeRecyclerView mRec;
    private BaseQuickAdapter<GetMoneyBean.DataBean.ListBean, BaseViewHolder> mAdapter;

    @Override
    protected void initView(View view) {

        initAdapter();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_getmoney;
    }

    @Override
    protected void fetchData() {

    }

    private void initAdapter() {


        mRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRec.setAdapter(mAdapter = new BaseQuickAdapter<GetMoneyBean.DataBean.ListBean, BaseViewHolder>(R.layout.item_getmoney) {
            @Override
            protected void convert(BaseViewHolder helper, GetMoneyBean.DataBean.ListBean item) {
                TextView txttime = helper.getView(R.id.txt_time);
                TextView txtcontent = helper.getView(R.id.txt_content);
                TextView txtnums = helper.getView(R.id.txt_nums);
                txttime.setText(item.getCreateTime());
                txtcontent.setText(item.getDepict()+"");
                txtnums.setText(item.getQuantity()+"金币");


            }
        });

    }

    public void getData() {
        RxHttp.postForm("/coinIncome/findPage.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean simpleMsgBean) {
                        //Goto(ConfirmOrderActivity.class);
                        if (simpleMsgBean.getCode() == 0) {//
                            GetMoneyBean defalutAddressBean = JSONObject.parseObject(JSONObject.toJSONString(simpleMsgBean), GetMoneyBean.class);

                            if (defalutAddressBean.getData().getList() != null) {

                                mAdapter.addData(defalutAddressBean.getData().getList());
                            }

                        } else {
                            //  ToastUtils.showShort("收藏失败");
                        }
                    }
                });

    }
}

package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.NameBean;
import net.goutalk.fowit.Bean.UserInfoBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Bean.MoneyCheckBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class GetMoneyActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.txt_money_coin)
    TextView txtMoneyCoin;
    @BindView(R.id.txt_money_true)
    TextView txtMoneyTrue;
    @BindView(R.id.txt_money_today)
    TextView txtMoneyToday;
    @BindView(R.id.txt_money_all)
    TextView txtMoneyAll;
    @BindView(R.id.txt_wechat)
    TextView txtWechat;
    @BindView(R.id.txt_airplay)
    TextView txtAirplay;
    @BindView(R.id.txt_phonemoney)
    TextView txtPhonemoney;
    @BindView(R.id.img_wechat)
    ImageView imgWechat;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.rel_change)
    LinearLayout relChange;
    @BindView(R.id.recyclerview_money)
    RecyclerView recyclerviewMoney;
    @BindView(R.id.txt_tixian)
    TextView txtTixian;
    String [] strmoney={"10","20","30","40","50","100"};
    private BaseQuickAdapter< MoneyCheckBean, BaseViewHolder> mAdapter;
    private String allCoin="0";
    private String todayCoin="0";
    NameBean codeBean;
    int type=1;
    String money="10";
    @Override
    public int getLayoutId() {
        return R.layout.layout_getmoney;
    }

    @Override
    public void initView() {
        tvTitle.setText("兑换提现");
        allCoin=getIntent().getStringExtra("allCoin");
        todayCoin=getIntent().getStringExtra("todayCoin");
        txtMoneyCoin.setText(allCoin);
        txtMoneyToday.setText(todayCoin);

        txtMoneyTrue.setText("约"+     roundByScale(Double.parseDouble(allCoin)/1000,2)+"元");
        txtMoneyAll.setText("约"+ roundByScale(Double.parseDouble(todayCoin)/1000,2)+"元");
        initRec();
        getUserInfo();

    }
    private void getUserInfo() {
        RxHttp.postForm("/memberCenter/baseInfo.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean baseMsgBean) {
                        if (baseMsgBean.getCode() == 0) {
                            codeBean = JSONObject.parseObject(JSONObject.toJSONString(baseMsgBean), NameBean.class);

                            if (codeBean.getData().getWechatNo()!=null){
                                txtName.setText(codeBean.getData().getWechatNo());
                            }else {
                                txtName.setText("请绑定微信号");
                            }
                        } else {
                            ToastUtils.showShort(baseMsgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }
    private void initRec() {

        List<MoneyCheckBean> list=new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            MoneyCheckBean bean=new MoneyCheckBean();
            if (i==0) {
                bean.setIsselect(true);
            }else {
                bean.setIsselect(false);
            }
            bean.setMoney(strmoney[i]);
            list.add(bean);
        }
        //初始化RecycleView
        recyclerviewMoney.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerviewMoney.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewMoney.setAdapter(mAdapter = new BaseQuickAdapter< MoneyCheckBean, BaseViewHolder>(R.layout.item_money,list) {
            @Override
            protected void convert(BaseViewHolder helper, MoneyCheckBean item) {

                //子RecycleView
                TextView textView=helper.itemView.findViewById(R.id.txtred);
                textView.setText(item.getMoney());
                if (item.isIsselect()==false){
                    textView.setBackgroundResource(R.drawable.drawable_money_wechat);
                }else {
                    textView.setBackgroundResource(R.mipmap.money_wechat);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        money=item.getMoney();
                        if (item.isIsselect()==false){
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getMoney().equals(item.getMoney())){
                                    list.get(i).setIsselect(true);
                                }else {
                                    list.get(i).setIsselect(false);
                                }
                            }
                            mAdapter.notifyDataSetChanged();

                        }
                    }
                });

            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        finish();
        // CommonUtils.doubleClickExitApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.img_back, R.id.txt_wechat, R.id.txt_airplay, R.id.txt_phonemoney, R.id.rel_change, R.id.txt_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_wechat:
                txtWechat.setBackgroundResource(R.mipmap.txfs);
                txtAirplay.setBackgroundResource(R.drawable.drawable_money_wechat);
                txtPhonemoney.setBackgroundResource(R.drawable.drawable_money_wechat);
                if (codeBean.getData().getWechatNo()!=null){
                    txtName.setText(codeBean.getData().getWechatNo());
                }else {
                    txtName.setText("请绑定微信号");
                }
                imgWechat.setBackgroundResource(R.mipmap.wd_dhtx_weixin);
                type=1;
                break;
            case R.id.txt_airplay:
                txtAirplay.setBackgroundResource(R.mipmap.txfs);
                txtPhonemoney.setBackgroundResource(R.drawable.drawable_money_wechat);
                txtWechat.setBackgroundResource(R.drawable.drawable_money_wechat);
                if (codeBean.getData().getAlipayNo()!=null){
                    txtName.setText(codeBean.getData().getWechatNo());
                }else {
                    txtName.setText("请绑定支付宝");
                }
                imgWechat.setBackgroundResource(R.drawable.ic_ali);
                type=2;
                break;
            case R.id.txt_phonemoney:
                txtPhonemoney.setBackgroundResource(R.mipmap.money_wechat);
                txtWechat.setBackgroundResource(R.drawable.drawable_money_wechat);
                txtAirplay.setBackgroundResource(R.drawable.drawable_money_wechat);
                break;
            case R.id.rel_change:
                if (type==1){
                    Goto(WechatActivity.class);
                }else {
                    Goto(ArilActivity.class);
                }
                break;
            case R.id.txt_tixian:
                tixian();
                break;
        }
    }

    private void tixian() {
        RxHttp.get("/coinExchange/save.do")
                .add("withdrawType",type)
                .add("quantity",new Double(money).intValue()*10000)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("提现成功");
                            finish();

                        } else {
                            //  ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }
}

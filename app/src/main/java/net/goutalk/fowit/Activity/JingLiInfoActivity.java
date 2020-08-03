package net.goutalk.fowit.Activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class JingLiInfoActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.txt_logain)
    TextView txtLogain;

    @BindView(R.id.txtminge)
    TextView txtminge;
    @BindView(R.id.numberbar3)
    NumberProgressBar numberbar3;
    @BindView(R.id.etname)
    EditText etname;
    @BindView(R.id.etphone)
    EditText etphone;
    @BindView(R.id.etcity)
    TextView etcity;
    @BindView(R.id.etaddr)
    EditText etaddr;
    @BindView(R.id.txtallmoney)
    TextView txtallmoney;
    @BindView(R.id.txtpastmoney)
    TextView txtpastmoney;
    JDCityPicker cityPicker;
    public JDCityConfig.ShowType mWheelType = JDCityConfig.ShowType.PRO_CITY_DIS;

    private JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
    @Override
    public int getLayoutId() {
        return R.layout.activity_jingliiinfo;
    }

    @Override
    public void initView() {

        tvTitle.setText("收货信息");
        txtpastmoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
        postwechat();
        initCity();
    }
    public void initCity() {

        cityPicker = new JDCityPicker();
        //初始化数据
        cityPicker.init(JingLiInfoActivity.this);
        //设置JD选择器样式位只显示省份和城市两级
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                String proData = null;
                if (province != null) {
                    proData = "name:  " + province.getName() + "   id:  " + province.getId();
                }

                String cituData = null;
                if (city != null) {
                    cituData = "name:  " + city.getName() + "   id:  " + city.getId();
                }


                String districtData = null;
                if (district != null) {
                    districtData = "name:  " + district.getName() + "   id:  " + district.getId();
                }


                if (mWheelType == JDCityConfig.ShowType.PRO_CITY_DIS) {
//                    resultV.setText("城市选择结果：\n" + proData + "\n"
//                            + cituData + "\n"
//                            + districtData);
                    etcity.setText(province.getName()+ city.getName()+district.getName());
                } else {
//                    resultV.setText("城市选择结果：\n" + proData + "\n"
//                            + cituData + "\n"
                    // );
                }
            }

            @Override
            public void onCancel() {
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private void postwechat() {
        RxHttp.get("/cardOrder/getPayFee.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            txtallmoney.setText(codeBean.getData().toString()+"元");


                        } else {
                            ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }
    @OnClick({R.id.img_back, R.id.txt_logain, R.id.dizhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_logain:
                if (etname.getText().toString().trim().length()==0){
                    ToastUtils.showShort("请完善相关信息");
                    return;
                }
                if (etphone.getText().toString().trim().length()==0){
                    ToastUtils.showShort("请完善相关信息");
                    return;
                }
                if (etcity.getText().toString().trim().length()==0){
                    ToastUtils.showShort("请完善相关信息");
                    return;
                }
                if (etaddr.getText().toString().trim().length()==0){
                    ToastUtils.showShort("请完善相关信息");
                    return;
                }
                Goto(JingLiPayActivity.class,"name",etname.getText().toString().trim(),"phone",etphone.getText().toString().trim(),"addr",etcity.getText().toString().trim()+etaddr.getText().toString().trim());
                break;
            case R.id.dizhi:
                cityPicker.showCityPicker();

                break;
        }
    }
}

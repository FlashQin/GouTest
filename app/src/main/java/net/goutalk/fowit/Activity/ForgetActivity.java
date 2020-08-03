package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class ForgetActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_sms_phone)
    EditText etSmsPhone;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_passagain)
    EditText edtPassagain;
    @BindView(R.id.store_checkBox)
    CheckBox storeCheckBox;
    @BindView(R.id.txt_service)
    TextView txtService;
    @BindView(R.id.txt_privde)
    TextView txtPrivde;
    @BindView(R.id.txt_resiger)
    TextView txtResiger;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms_code)
    TextView tvGetSmsCode;

    @Override
    public int getLayoutId() {
        return R.layout.layout_resiger;
    }

    @Override
    public void initView() {


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


    @OnClick({R.id.img_back, R.id.txt_service, R.id.txt_privde,R.id.tv_get_sms_code, R.id.txt_resiger})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_service:
                break;
            case R.id.txt_privde:
                break;
            case R.id.tv_get_sms_code:
                if (etSmsPhone.length() != 11) {
                    ToastUtils.showShort("请先输入11位手机号码");
                    break;
                }

                RxHttp.get("/sms/validate.do?")
                        .add("mobile", etSmsPhone.getText().toString().trim())
                        .add("genre", 3)//登录  type=1用户, 2商家,, type=2时name必传
                        .add("token", "")

                        .asObject(BaseMsgBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseMsgBean>() {
                            @Override
                            public void onNext(BaseMsgBean codeBean) {
                                if (codeBean.getCode() == 0) {
                                    ToastUtils.showShort("短信验证码获取成功");
                                    new CommonTimer(60 * 1000 + 200, 1000, tvGetSmsCode).start();
                                } else {
                                    ToastUtils.showShort(codeBean.getMsg());
                                }
                            }
                        });


                break;
            case R.id.txt_resiger:
                break;
        }
    }
}

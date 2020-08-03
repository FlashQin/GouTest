package net.goutalk.fowit.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LoginBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ACache;
import net.goutalk.fowit.utils.CommonTimer;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import rxhttp.wrapper.param.RxHttp;

/**
 * wx qq 登陆的时候需要绑定手机号码
 */
public class BindPhoneActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_sms_phone)
    EditText etSmsPhone;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_get_sms_code)
    TextView tvGetSmsCode;
    @BindView(R.id.btn_agree_and_login)
    Button btnAgreeAndLogin;

    String openid,token;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        etSmsPhone.addTextChangedListener(this);
        etSmsCode.addTextChangedListener(this);
        openid=getIntent().getStringExtra("openid");
        token=getIntent().getStringExtra("token");
    }

    @OnClick({R.id.icon_back, R.id.tv_get_sms_code, R.id.btn_agree_and_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                onBackPressed();
                break;
            case R.id.tv_get_sms_code:
                if (etSmsPhone.length() != 11) {
                    ToastUtils.showShort("请先输入11位手机号码");
                    break;
                }

                RxHttp.get("/sms/validate.do?")
                        .add("mobile", etSmsPhone.getText().toString().trim())
                        .add("genre", 3)//登录  type=1用户, 2商家,, type=2时name必传
                        .add("token",token )

                        .asObject(BaseMsgBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseMsgBean>() {
                            @Override
                            public void onNext(BaseMsgBean codeBean) {
                                if (codeBean.getCode()==0) {
                                    ToastUtils.showShort("短信验证码获取成功");
                                    new CommonTimer(60 * 1000 + 200, 1000, tvGetSmsCode).start();
                                } else {
                                    ToastUtils.showShort(codeBean.getMsg());
                                }
                            }
                        });



                break;
            case R.id.btn_agree_and_login:
                RxHttp.postForm("/login/weChat/updateMobile.do?openid="+openid+"&mobile="+etSmsPhone.getText().toString().trim())
                        .asObject(BaseMsgBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseMsgBean>() {
                            @Override
                            public void onNext(BaseMsgBean codeBean) {
                                if (codeBean.getCode()==0) {
                                    LoginBean loginBean = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(codeBean), LoginBean.class);

                                    ToastUtils.showShort("登录成功");
                                    SPUtils.getInstance().put("TOKEN", loginBean.getData().getEasyfowitToken());
                                    ACache.get(Utils.getApp()).put("USER_BEAN", GsonUtils.toJson(loginBean));
                                    finish();
                                } else {
                                    ToastUtils.showShort(codeBean.getMsg());
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }
                        });
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //验证码登陆，电话号码为11为且验证码等于6位
        if (etSmsPhone.length() == 11 && etSmsCode.length() == 6) {
            btnAgreeAndLogin.setEnabled(true);
        } else {
            btnAgreeAndLogin.setEnabled(false);
        }
    }

}

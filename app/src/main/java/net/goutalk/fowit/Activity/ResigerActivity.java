package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ResigerActivity extends BaseActivity {


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


    @OnClick({R.id.img_back, R.id.txt_service,R.id.tv_get_sms_code, R.id.txt_privde, R.id.txt_resiger})
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
                        .add("token","" )

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
            case R.id.txt_resiger:

                if (isMobile(etSmsPhone.getText().toString().trim()) == false) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                if (edtPass.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort("请输入密码");
                    return;

                }
                if (edtPassagain.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort("请再次输入密码");
                    return;

                }
                if (!edtPassagain.getText().toString().trim().equals(edtPass.getText().toString().trim())) {
                    ToastUtils.showShort("密码不一致");
                    return;

                }

                RxHttp.postForm("/register.do")
                        .add("mobileNo", etSmsPhone.getText().toString().trim())
                        .add("password", edtPass.getText().toString().trim())//登录  type=1用户, 2商家,, type=2时name必传
                        .asObject(BaseMsgBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseMsgBean>() {
                            @Override
                            public void onNext(BaseMsgBean codeBean) {
                                if (codeBean.getCode() == 0) {
                                    ToastUtils.showShort("注册成功");
                                    finish();
                                } else {
                                    ToastUtils.showShort(codeBean.getMsg());
                                }
                            }
                        });
                break;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
}

package net.goutalk.fowit.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import rxhttp.wrapper.param.RxHttp;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class SettingPsdActivity extends BaseActivity implements TextWatcher {

    //新用户设置密码
    public static final int TYPE_2 = 0x02;
    //找回密码的第二步
    public static final int TYPE_1 = 0x01;

    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_again)
    EditText etPsdAgain;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private int type;
    private int CODE;
    private String PHONE;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_psd;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("TYPE", TYPE_1);
        CODE = getIntent().getIntExtra("CODE", 0);
        PHONE = getIntent().getStringExtra("PHONE");
        if (type == TYPE_1) {
            tvSubTitle.setText("设置密码");
            tvPass.setVisibility(View.GONE);
        } else {
            tvSubTitle.setText("请设置密码");
            tvPass.setVisibility(View.VISIBLE);
        }
        etPsd.addTextChangedListener(this);
        etPsdAgain.addTextChangedListener(this);
    }


    @OnClick({R.id.icon_back, R.id.tv_pass, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                onBackPressed();
                break;
            case R.id.tv_pass:
                if (type == 0x03) {
                    finish();
                }else {

                    finish();
                }

                break;
            case R.id.btn_submit:
                if (!etPsd.getText().toString().trim().equals(etPsdAgain.getText().toString().trim())) {
                    ToastUtils.showShort("两次输入的密码不一致");
                    etPsdAgain.setText("");
                    return;
                }
                if (etPsd.getText().toString().trim().length()<6){
                    ToastUtils.showShort("密码不能小于6位");
                    return;
                }

                    RxHttp.postForm("/resetPassword.do")
                            .add("mobile", etPsd.getText().toString().trim())
                            .add("password", etPsd.getText().toString().trim())
                            .add("password", etPsd.getText().toString().trim())
                            .asObject(BaseMsgBean.class)
                            .as(RxLife.asOnMain(this))
                            .subscribe(new BaseObserver<BaseMsgBean>() {
                                @Override
                                public void onNext(BaseMsgBean msgBean) {

                                    if (msgBean.getCode()==0) {
                                      //  Goto(MainActivity.class, FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK);
                                        finish();
                                        ToastUtils.showShort("修改成功");
                                    }else {
                                       // ToastUtils.showShort("密码设置失败");
                                    }
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
//        //判断密码位数>=8
//        if (!etPsd.hasFocus() && etPsd.getText().toString().length() <= 8) {
//            etPsd.requestFocus();
//            etPsdAgain.setText("");
//            ToastUtils.showShort("请输入大于等于8位的密码");
//            return;
//        }
//        //判断密码位数<=8
//        if (etPsd.getText().toString().length() >= 16) {
//            etPsdAgain.requestFocus();
//            ToastUtils.showShort("密码最大16位");
//        }
//        //第二个密码框数据长度不能大于第一个
//        if (etPsdAgain.getText().toString().length() > etPsd.getText().toString().length()) {
//            etPsdAgain.removeTextChangedListener(this);
//            etPsdAgain.setText(s.toString().substring(0, etPsd.length()));
//            etPsdAgain.addTextChangedListener(this);
//            etPsdAgain.setSelection(etPsdAgain.getText().toString().length());
//        }
//        //判断第二次输入的密码是否同第一次的
//        if (etPsdAgain.getText().toString().length() == etPsd.getText().toString().length() && etPsdAgain.hasFocus()) {
//            if (!etPsdAgain.getText().toString().equals(etPsd.getText().toString())) {
//                ToastUtils.showShort("两次输入的密码不一致");
//            } else {
//                btn_submit.setEnabled(true);
//                return;
//            }
//        }
        if (!etPsd.getText().toString().trim().equals("") && !etPsdAgain.getText().toString().trim().equals("")) {
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setEnabled(false);
        }
    }
}

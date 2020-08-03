package net.goutalk.fowit.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class EmailActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.edt_wechat)
    EditText edtWechat;
    @BindView(R.id.txt_exit)
    TextView txtExit;
    @BindView(R.id.fra_exit)
    FrameLayout fraExit;


    @Override
    public int getLayoutId() {
        return R.layout.layout_email;
    }


    @Override
    public void initView() {
        tvTitle.setText("绑定邮箱");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back,R.id.txt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_exit:

                if (edtWechat.getText().toString().trim().length()>0){

                    postwechat(edtWechat.getText().toString().trim());
                }else {
                    ToastUtils.showShort("请填写邮箱");
                }


                break;

        }
    }

    private void postwechat(String wechat) {
        RxHttp.postForm("/member/updateEmail.do")
                .add("email",wechat)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("绑定成功");
                            finish();

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
}

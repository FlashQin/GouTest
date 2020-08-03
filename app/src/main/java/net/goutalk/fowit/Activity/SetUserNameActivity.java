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

public class SetUserNameActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.txt_exit)
    TextView txtExit;
    @BindView(R.id.fra_exit)
    FrameLayout fraExit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_user_name;
    }

    @Override
    public void initView() {
        tvTitle.setText("用户昵称");
    }


    @OnClick({R.id.img_back, R.id.fra_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.fra_exit:
                String nickName = et.getText().toString().trim();
                if (nickName.length() == 0) {
                    ToastUtils.showShort("请先输入昵称");
                    return;
                }

                RxHttp.postForm("member/updateNickName.do")
                        .add("nickName", nickName)
                        .asObject(BaseMsgBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<BaseMsgBean>() {
                            @Override
                            public void onNext(BaseMsgBean msgBean) {

                                ToastUtils.showShort(msgBean.getMsg());
                                SetUserNameActivity.this.finish();
                            }
                        });
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

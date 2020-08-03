package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;


    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @Override
    public void initView() {

        //从登陆界面来/设置 ---  用户协议
        tv_title.setText("用户协议");


    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JingLiIntrduceActivity extends BaseActivity {
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_jingliintrduce;
    }

    @Override
    public void initView() {

        tvTitle.setText("全球购锦鲤卡办理");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.txt_logain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_logain:
                Goto(JingLiInfoActivity.class);
                break;
        }
    }
}

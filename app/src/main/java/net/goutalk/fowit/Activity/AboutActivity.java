package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.R;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.txtcode)
    TextView txtcode;
    @BindView(R.id.tv_clear_cache)
    TextView tvClearCache;
    @BindView(R.id.tv_cache_number)
    TextView tvCacheNumber;
    @BindView(R.id.tv_clear_protocol)
    TextView tvClearProtocol;
    @BindView(R.id.tv_ys_protocol)
    TextView tvYsProtocol;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.btn_exit_login)
    Button btnExitLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {

        tvTitle.setText("设置");

        txtcode.setText("版本号 " + getVersionName(AboutActivity.this));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.tv_clear_cache, R.id.tv_cache_number, R.id.tv_clear_protocol, R.id.tv_ys_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_clear_cache:
                break;
            case R.id.tv_cache_number:
                break;
            case R.id.tv_clear_protocol:
                break;
            case R.id.tv_ys_protocol:
                break;
        }
    }
}

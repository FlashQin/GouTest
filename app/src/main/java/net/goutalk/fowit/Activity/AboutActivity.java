package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.CommonUtils;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.goutalk.fowit.utils.CommonUtils.getDiskCachePath;


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
        try {

            long sds=FileUtils.getLength(getDiskCachePath()) / 1000;
            double safdsf=(double)sds;

            if (safdsf>1000){

                tvCacheNumber.setText(String.format("%sMB", roundByScale(safdsf/1000,2)));
            }else {
                tvCacheNumber.setText(String.format("%sKB", FileUtils.getLength(getDiskCachePath()) / 1000));
            }

        } catch (Exception e) {
            tvCacheNumber.setText(String.format("%sKB", 0));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.tv_clear_cache, R.id.tv_ys_protocol, R.id.tv_yonghu, R.id.tv_yinsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_clear_cache:
                ToastUtils.showShort("缓存清理中");
                tvTitle.postDelayed(() -> {
                    ToastUtils.showShort("缓存已清除");
                    FileUtils.delete(getDiskCachePath());
                    tvCacheNumber.setText("0KB");
                }, 1000);
                break;
            case R.id.tv_ys_protocol:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShort("未检测到应用市场");
                    e.printStackTrace();
                }
                break;
            case R.id.tv_yonghu:
                Goto(ProtocolActivity.class);
                break;
            case R.id.tv_yinsi:
                Goto(YinsiActivity.class);
                break;
        }
    }
}

package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.R;
import net.goutalk.fowit.wigde.JzvdStdAutoCompleteAfterFullscreen;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

import static cn.jzvd.Jzvd.backPress;

public class VideoInfoActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.videoplayer)
    JzvdStdAutoCompleteAfterFullscreen jzvdStd;
    @BindView(R.id.img_head_video)
    ImageView imgHeadVideo;

    String name = "", videoUrl = "";
    @BindView(R.id.txt_name)
    TextView txtName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_videoinfo;
    }

    @Override
    public void initView() {
        tvTitle.setText("视频详情");
        name = getIntent().getStringExtra("name");
        videoUrl = getIntent().getStringExtra("url");
        txtName.setText(name);
        jzvdStd.setUp(
                videoUrl,
               "");
        jzvdStd.startVideo();
     //   Glide.with(getActivity()).load(item.getThumbnail()).into(jzvdStd.posterImageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_head_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_head_video:
                break;
        }
    }
}

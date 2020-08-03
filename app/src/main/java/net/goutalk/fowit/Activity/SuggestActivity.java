package net.goutalk.fowit.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.rxjava.rxlife.RxLife;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.net.BaseObserver;
import net.qiujuer.genius.ui.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

import static net.goutalk.fowit.utils.CommonUtils.checkImgUrl;
import static net.goutalk.fowit.utils.CommonUtils.uriToFile;


public class SuggestActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;

    @BindView(R.id.tv_select_reason)
    TextView tvSelectReason;
    @BindView(R.id.et_suggest)
    EditText etSuggest;
    @BindView(R.id.rec_img)
    RecyclerView recImg;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.img_checkone)
    ImageView imgCheckone;
    @BindView(R.id.img_checktwo)
    ImageView imgChecktwo;
    @BindView(R.id.img_checkthree)
    ImageView imgCheckthree;
    private DialogPlus dialogPlus;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
   // private UpImgHelper upImgHelper;
    public static final int CHOOSE_PHOTO_REQUEST = 0x09;
    private int TYPE = 1;
    private String phone = "";
    private int current_select_pay_type = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_suggest;
    }


    @Override
    protected void SetStatusBar() {
        super.SetStatusBar();
      //  StatusBarUtil.setColor(SuggestActivity.this, Color.WHITE);
    }

    @Override
    public void initView() {
        tvTitle.setText("意见反馈");
        phone = getIntent().getStringExtra("Phone");

        recImg.setLayoutManager(new GridLayoutManager(this, 3));
        recImg.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_only_img_80_layout) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (item.equals("")) {
//                    Glide.with(getContext()).load(R.drawable.ic_add_img)
//                            .into((ImageView) Objects.requireNonNull(helper.itemView));
                } else {
                    Glide.with(getContext()).load(checkImgUrl(item))
                            .into((ImageView) Objects.requireNonNull(helper.itemView));
                }
            }
        });
        recImg.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, SizeUtils.dp2px(8));
            }
        });
      //   upImgHelper = new UpImgHelper(this, mAdapter, this::choosePic);
    }
    private void postdata() {
        RxHttp.postForm("/helpSuggest/save.do")
                .add("content",etSuggest.getText().toString().trim())
                .add("imageList","")
                .add("mobile",phone)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("提交成功");
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

    private void choosePic() {
        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .as(RxLife.asOnMain(this))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        //申请的权限全部允许
                        Matisse.from(SuggestActivity.this)
                                .choose(MimeType.ofImage()) // 选择 mime 的类型
                                .countable(true)
                                .maxSelectable(3) // 图片选择的最多数量
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f) // 缩略图的比例
                                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                                .forResult(CHOOSE_PHOTO_REQUEST); // 设置作为标记的请求码
                    } else {
                        //只要有一个权限被拒绝，就会执行
                        ToastUtils.showShort("无文件访问权限");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_PHOTO_REQUEST) {
                ArrayList<File> files = new ArrayList<>();
                List<Uri> mSelected = Matisse.obtainResult(Objects.requireNonNull(data));
                for (Uri uri : mSelected) {
                    files.add(uriToFile(this, uri));
                }
                // upImgHelper.RequestUpload(files);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.tv_select_reason, R.id.btn_commit,R.id.img_checkone, R.id.img_checktwo, R.id.img_checkthree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_select_reason:
                break;
            case R.id.btn_commit:
                if (etSuggest.getText().toString().trim().length()>0){
                    postdata();
                }else {
                    ToastUtils.showShort("请填写相关内容");
                }
                break;
            case R.id.img_checkone:
                current_select_pay_type=0;
                imgCheckone.setImageResource(R.mipmap.checkyes);
                imgChecktwo.setImageResource(R.mipmap.checkno);
                imgCheckthree.setImageResource(R.mipmap.checkno);
                break;
            case R.id.img_checktwo:
                current_select_pay_type=1;
                imgCheckone.setImageResource(R.mipmap.checkno);
                imgChecktwo.setImageResource(R.mipmap.checkyes);
                imgCheckthree.setImageResource(R.mipmap.checkno);
                break;
            case R.id.img_checkthree:
                current_select_pay_type=2;
                imgCheckone.setImageResource(R.mipmap.checkno);
                imgChecktwo.setImageResource(R.mipmap.checkno);
                imgCheckthree.setImageResource(R.mipmap.checkyes);
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

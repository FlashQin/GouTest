package net.goutalk.fowit.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.dialog.EnsureDialog;
import com.rxjava.rxlife.RxLife;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.NameBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ACache;
import net.goutalk.fowit.utils.GlideEngine;
import net.qiujuer.genius.kit.handler.Run;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rxhttp.wrapper.param.RxHttp;


public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_head_info)
    CircleImageView imgHeadInfo;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.layout_user_name)
    FrameLayout layoutUserName;
    @BindView(R.id.tv_user_sex)
    TextView tvUserSex;
    @BindView(R.id.layout_user_sex)
    FrameLayout layoutUserSex;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.layout_user_phone)
    FrameLayout layoutUserPhone;
    @BindView(R.id.tv_user_pass)
    TextView tvUserPass;
    @BindView(R.id.layout_user_pass)
    FrameLayout layoutUserPass;
    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;
    @BindView(R.id.layout_user_email)
    FrameLayout layoutUserEmail;
    @BindView(R.id.txt_exit)
    TextView txtExit;
    @BindView(R.id.fra_exit)
    FrameLayout fraExit;
    public static final int CHOOSE_PHOTO_REQUEST = 0x09;
    @BindView(R.id.tv_user_wechat)
    TextView tvUserWechat;
    @BindView(R.id.layout_user_wechat)
    FrameLayout layoutUserWechat;
    @BindView(R.id.tv_user_alip)
    TextView tvUserAlip;
    @BindView(R.id.layout_user_alip)
    FrameLayout layoutUserAlip;

    @Override
    public int getLayoutId() {
        return R.layout.layout_userinfo;
    }


    @Override
    protected void SetStatusBar() {
        super.SetStatusBar();
        //  StatusBarUtil.setColor(SuggestActivity.this, Color.WHITE);
    }

    @Override
    public void initView() {


    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
        RxHttp.postForm("/memberCenter/baseInfo.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            NameBean nameBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), NameBean.class);

                            tvUserName.setText(nameBean.getData().getNickName()+"");
                            tvUserSex.setText(nameBean.getData().getSex()+"");
                            tvUserPhone.setText(nameBean.getData().getMobileNo()+"");
                            tvUserAlip.setText(nameBean.getData().getAlipayNo()+"");
                            tvUserWechat.setText(nameBean.getData().getWechatNo()+"");
                            tvUserPass.setText(nameBean.getData().getPassword()+"");
                            tvUserEmail.setText(nameBean.getData().getEmail()+"");

                        } else {
                          //  ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }
    public void exit() {
        ToastUtils.showShort("已退出");
        Run.onUiAsync(() -> {
            ACache.get(Utils.getApp()).remove("USER_BEAN");
            SPUtils.getInstance().remove("TOKEN");
        });

        Goto(LoginUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_head_info, R.id.layout_user_name, R.id.layout_user_sex, R.id.layout_user_phone, R.id.layout_user_pass, R.id.layout_user_email, R.id.fra_exit,R.id.layout_user_wechat, R.id.layout_user_alip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_head_info:
                choosePic();
                break;
            case R.id.layout_user_name:
                Goto(SetUserNameActivity.class);
                break;
            case R.id.layout_user_sex:
                selectSex();
                break;
            case R.id.layout_user_phone:
                break;
            case R.id.layout_user_pass:
                break;
            case R.id.layout_user_email:
                Goto(EmailActivity.class);
                break;
            case R.id.fra_exit:
                exit();
                break;
            case R.id.layout_user_wechat:
                Goto(WechatActivity.class);
                break;
            case R.id.layout_user_alip:
                Goto(ArilActivity.class);

                break;
        }
    }
    private void selectSex() {

        new EnsureDialog()
                .message("请选择性别")
                .messageStyle(Color.BLACK, 18, false)
                .confirmBtn("男", Color.BLACK, (smartDialog, i, o) -> {
                    tvUserSex.setText("男");
                    smartDialog.dismiss();
                    //data.setSex(1);
                    postSex(1);
                })
                .cancelBtn("女", Color.BLACK, (smartDialog, i, o) -> {
                    tvUserSex.setText("女");
                    smartDialog.dismiss();
                 //   data.setSex(2);
                    postSex(0);
                })
                .showInActivity(this);
    }
    public void postSex(int sex){

        RxHttp.postForm("member/update/sex.do")
                .add("sex",sex)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean msgBean) {



                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_PHOTO_REQUEST) {
//                LPhotoPickerActivity.getSelectedPhotos(data);
                List<Uri> mSelected = Matisse.obtainResult(Objects.requireNonNull(data));
                if (mSelected.size() != 0) {
                    Glide.with(imgHeadInfo).load(mSelected.get(0)).into(imgHeadInfo);
//                    up_load_cover.setVisibility(View.GONE);
//                    upLoadHeaderImg(uriToFile(this, mSelected.get(0)));
                }
            }
        }
    }


    private void choosePic() {

        new RxPermissions(UserInfoActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .as(RxLife.asOnMain(UserInfoActivity.this))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        //申请的权限全部允许
                        Matisse.from(UserInfoActivity.this)
                                .choose(MimeType.ofImage()) // 选择 mime 的类型
                                .countable(true)
                                .maxSelectable(1) // 图片选择的最多数量
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


}

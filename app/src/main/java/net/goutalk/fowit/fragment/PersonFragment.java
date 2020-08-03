package net.goutalk.fowit.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import net.goutalk.fowit.Activity.AboutActivity;
import net.goutalk.fowit.Activity.JingLiIntrduceActivity;
import net.goutalk.fowit.R;
import net.goutalk.fowit.Activity.AccountInfoActivity;
import net.goutalk.fowit.Activity.AnsenwActivity;
import net.goutalk.fowit.Activity.GetMoneyActivity;
import net.goutalk.fowit.Activity.LockerActivity;
import net.goutalk.fowit.Activity.LoginUserActivity;
import net.goutalk.fowit.Activity.LuckyActivity;
import net.goutalk.fowit.Activity.ProblemActivity;
import net.goutalk.fowit.Activity.SingActivity;
import net.goutalk.fowit.Activity.SuggestActivity;
import net.goutalk.fowit.Activity.UserInfoActivity;
import net.goutalk.fowit.Activity.YaoActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.UserInfoBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.TTAdManagerHolder;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class PersonFragment extends BaseFragment {


    @BindView(R.id.tv_userhead)
    ImageView tvUserhead;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.tv_search)
    LinearLayout tvSearch;
    @BindView(R.id.img_msg)
    ImageView imgMsg;
    @BindView(R.id.lin_edit)
    LinearLayout linEdit;
    @BindView(R.id.txt_all_coin)
    TextView txtAllCoin;
    @BindView(R.id.txt_today_coin)
    TextView txtTodayCoin;
    @BindView(R.id.txt_person_coin)
    TextView txtPersonCoin;
    @BindView(R.id.linnn)
    LinearLayout linnn;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @BindView(R.id.rel_action)
    RelativeLayout relAction;
    @BindView(R.id.rel_invent)
    RelativeLayout relInvent;
    @BindView(R.id.rel_account_info)
    RelativeLayout relAccountInfo;
    @BindView(R.id.rel_getmoney)
    RelativeLayout relGetmoney;
    @BindView(R.id.rel_usulypromber)
    RelativeLayout relUsulypromber;
    @BindView(R.id.rel_idea)
    RelativeLayout relIdea;
    @BindView(R.id.lin_luck)
    LinearLayout linLuck;
    @BindView(R.id.lin_study)
    LinearLayout linStudy;
    @BindView(R.id.lin_video)
    LinearLayout linVideo;
    @BindView(R.id.lin_sencrro)
    LinearLayout linSencrro;
    @BindView(R.id.lin_edit_onme)
    LinearLayout linEditOnme;
    @BindView(R.id.rel_setting)
    RelativeLayout relSetting;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private DialogPlus dialog_spec;
    private String allCoin = "0";
    private String todayCoin = "0";
    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mIsExpress = false; //是否请求模板广告
    private boolean mHasShowDownloadActive = false;
    UserInfoBean codeBean;
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
        // viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();

    }

    @Override
    protected void initView(View view) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(getActivity());
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(getActivity());
        getExtraInfo();
    }

    private void getExtraInfo() {
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return;
        }

        mIsExpress = intent.getBooleanExtra("is_express", false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onResume() {
        super.onResume();


        if (!SPUtils.getInstance().getString("TOKEN", "").equals("")) {
            getUserInfo();

        } else {
            txtName.setText("游客");
            txtCode.setText("");
            Glide.with(getActivity())
                    .load(R.mipmap.shouye_touxiang)
                    .into(tvUserhead);
        }
    }

    private void postInventCode(String code) {
        RxHttp.postForm("/memberCenter/saveActiveCode.do")
                .add("activeCode", code)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("激活成功");

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

    private void getUserInfo() {
        RxHttp.postForm("/memberCenter/baseInfo.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean baseMsgBean) {
                        if (baseMsgBean.getCode() == 0) {
                             codeBean = JSONObject.parseObject(JSONObject.toJSONString(baseMsgBean), UserInfoBean.class);
                            txtAllCoin.setText(codeBean.getData().getCoinQuantity());
                            txtTodayCoin.setText(codeBean.getData().getCurrentDateCoinQuantity());
                            txtPersonCoin.setText(codeBean.getData().getInviteQuantity());
                            allCoin = codeBean.getData().getCoinQuantity();
                            todayCoin = codeBean.getData().getCurrentDateCoinQuantity();
                            SPUtils.getInstance().put("code", codeBean.getData().getInviteCode());

                            loadBaseInfo(codeBean);

                        } else {
                            ToastUtils.showShort(baseMsgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showShort(e.toString());
                    }
                });

    }

    private void loadBaseInfo(UserInfoBean bean) {


        try {


            if (bean.getData().getHeadUrl() != null) {

                //   我自己写的游客模式
                Glide.with(getActivity())
                        .load(bean.getData().getHeadUrl())
                        .into(tvUserhead);
                //加载背景
            }
            txtName.setText(bean.getData().getMobileNo());
            txtCode.setText("邀请码:" + bean.getData().getInviteCode());
            // Glide.with(getContext()).load(R.drawable.bg_person_bottom).into(layout_bg_2);
            // layout_bg_2.setVisibility(View.VISIBLE);
            //imgVip.setVisibility(View.GONE);


        } catch (NullPointerException e) {
            e.printStackTrace();
            //  ToastUtils.showShort("身份信息失效，请重新登陆");
            txtName.setText("游客");
            txtCode.setText("");
            Glide.with(getActivity())
                    .load(R.mipmap.shouye_touxiang)
                    .into(tvUserhead);


        }

    }

    @OnClick({R.id.linnn, R.id.tv_userhead, R.id.txt_name, R.id.txt_code, R.id.tv_search, R.id.img_msg, R.id.lin_edit, R.id.rel_action, R.id.rel_invent,
            R.id.rel_account_info, R.id.rel_getmoney, R.id.rel_usulypromber, R.id.rel_idea, R.id.lin_luck, R.id.lin_study,
            R.id.lin_video, R.id.lin_sencrro, R.id.lin_edit_onme, R.id.rel_setting, R.id.imgjingli})
    public void onViewClicked(View view) {
        if (SPUtils.getInstance().getString("TOKEN", "").equals("")) {
            Goto(LoginUserActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.linnn:
                Goto(GetMoneyActivity.class, "allCoin", allCoin, "todayCoin", todayCoin);
                break;
            case R.id.tv_userhead:
                Goto(UserInfoActivity.class);
                break;
            case R.id.txt_name:
                Goto(UserInfoActivity.class);
                break;
            case R.id.txt_code:
                Goto(UserInfoActivity.class);
                break;
            case R.id.tv_search:
                break;
            case R.id.img_msg:
                break;
            case R.id.lin_edit:
                Goto(SingActivity.class);
                break;
            case R.id.rel_action:
                Goto(YaoActivity.class);
                break;
            case R.id.rel_invent:
                initSpecDialog();
                break;
            case R.id.rel_account_info:
                Goto(AccountInfoActivity.class);
                break;
            case R.id.rel_getmoney:
                Goto(GetMoneyActivity.class, "allCoin", allCoin, "todayCoin", todayCoin);
                break;
            case R.id.rel_usulypromber:
                Goto(ProblemActivity.class);
                break;
            case R.id.rel_idea:
                Goto(SuggestActivity.class,"Phone",codeBean.getData().getMobileNo());
                break;
            case R.id.lin_luck:
                Goto(LuckyActivity.class);
                break;
            case R.id.lin_study:
                Goto(AnsenwActivity.class);
                break;
            case R.id.lin_video:
                loadAd("945308169", TTAdConstant.VERTICAL);
                break;
            case R.id.lin_sencrro:
                Goto(LockerActivity.class);
                break;
            case R.id.lin_edit_onme:
                Goto(UserInfoActivity.class);
                break;
            case R.id.rel_setting:
                Goto(AboutActivity.class);
                break;
            case R.id.imgjingli:
                Goto(JingLiIntrduceActivity.class);
                break;
        }
    }

    //规格参数dialog
    private void initSpecDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dialog_invent_layout);
        dialog_spec = DialogPlus.newDialog(getActivity())
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        ImageView img = viewHolder.getInflatedView().findViewById(R.id.img_close);
        TextView txt = viewHolder.getInflatedView().findViewById(R.id.txt_logain);
        EditText editText = viewHolder.getInflatedView().findViewById(R.id.et_search);
        img.setOnClickListener(v -> dialog_spec.dismiss());
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() > 0) {
                    postInventCode(editText.getText().toString().trim());
                } else {
                    ToastUtils.showShort("邀请码不能为空");
                }
            }
        });
        dialog_spec.show();

    }

    private void loadAd(final String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500, 500)
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)  //奖励的数量
                    .setUserID("user123")//用户id,必传参数
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                    //该方法直接展示广告
//                    mttRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this);

                    //展示广告，并传入广告展示的场景
                    mttRewardVideoAd.showRewardVideoAd(getActivity(), TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRewardVideoAd = null;
                } else {
                    //TToast.show(RewardVideoActivity.this, "请先加载广告");
                    loadAd(CommonUtils.mVerticalCodeId, TTAdConstant.VERTICAL);
                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoAdLoad");

                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardVideoAd close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardVideoAd complete");
                        int coin = (int) (50 * Math.random() + 50);

                        postCoin(String.valueOf(coin));
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardVideoAd error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName;
                        Log.e(TAG, "Callback --> " + logString);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardVideoAd has onSkippedVideo");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);

                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
            }
        });
    }

    private void postCoin(String coin) {

        RxHttp.postForm("/coinIncome/save.do")
                .add("quantity", coin.replace("金币", ""))
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                            ToastUtils.showShort("得到" + coin + "金币");

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

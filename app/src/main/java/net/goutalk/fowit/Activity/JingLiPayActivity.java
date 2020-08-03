package net.goutalk.fowit.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.PayResult;
import net.goutalk.fowit.Bean.WXPAYbean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class JingLiPayActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.img_select_alipay)
    ImageView imgSelectAlipay;
    @BindView(R.id.layout_alipay)
    FrameLayout layoutAlipay;
    @BindView(R.id.img_select_wx)
    ImageView imgSelectWx;
    @BindView(R.id.layout_wx)
    FrameLayout layoutWx;
    @BindView(R.id.txtallmoney)
    TextView txtallmoney;
    @BindView(R.id.txt_logain)
    TextView txtLogain;
    IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, CommonUtils.APP_ID_WX, true);

    @BindView(R.id.txtname)
    TextView txtname;
    @BindView(R.id.txtphone)
    TextView txtphone;
    @BindView(R.id.txtaddr)
    TextView txtaddr;
    private int SelectedPosition = 0;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    String name, phone, addr,money="20";

    @Override
    public int getLayoutId() {
        return R.layout.activity_jinglipay;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showShort("支付成功");


                        finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //  showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        ToastUtils.showShort(payResult.getResult());
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //   showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
//                    } else {
//                        // 其他状态值则为授权失败
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
//                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void initView() {
        mWxApi.registerApp(CommonUtils.APP_ID_WX);
        tvTitle.setText("确认订单");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        addr = getIntent().getStringExtra("addr");
        txtname.setText(name);
        txtphone.setText(phone);

        txtaddr.setText(addr);

        postwechat();

    }
    private void postwechat() {
        RxHttp.get("/cardOrder/getPayFee.json")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            txtallmoney.setText(codeBean.getData().toString()+"元");


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.layout_alipay, R.id.layout_wx, R.id.txt_logain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.layout_alipay:
                SelectedPosition = 0;
                imgSelectAlipay.setImageDrawable(getResouseDrawable(R.drawable.pay_select));
                imgSelectWx.setImageDrawable(getResouseDrawable(R.drawable.pay_unselect));
                break;
            case R.id.layout_wx:
                SelectedPosition = 1;
                imgSelectAlipay.setImageDrawable(getResouseDrawable(R.drawable.pay_unselect));
                imgSelectWx.setImageDrawable(getResouseDrawable(R.drawable.pay_select));
                break;
            case R.id.txt_logain:

                switch (SelectedPosition) {
                    //alipay
                    case 0:

                        RxHttp.postForm("cardOrder/alipay/save.do?" + "payFee=" + money + "&consigneeName=" + name + "&consigneeMobile=" +phone+ "&consigneeAddress=" +addr + "&payPlatform=" + 2)
                                .asObject(BaseMsgBean.class)
                                .as(RxLife.asOnMain(this))
                                .subscribe(new BaseObserver<BaseMsgBean>() {
                                    @Override
                                    public void onNext(BaseMsgBean msgBean) {
                                        if (msgBean.getCode() == 0) {
                                            final Runnable payRunnable = new Runnable() {

                                                @Override
                                                public void run() {
                                                    PayTask alipay = new PayTask(JingLiPayActivity.this);
                                                    Map<String, String> result = alipay.payV2(msgBean.getData().toString(), true);
                                                    Log.i("msp", result.toString());

                                                    Message msg = new Message();
                                                    msg.what = SDK_PAY_FLAG;
                                                    msg.obj = result;
                                                    mHandler.sendMessage(msg);
                                                }
                                            };

                                            // 必须异步调用
                                            Thread payThread = new Thread(payRunnable);
                                            payThread.start();
                                        }
                                    }
                                });
                        break;
                    //wxpay
                    case 1:
                        RxHttp.postForm("cardOrder/save.do?" + "payFee=" + money + "&consigneeName=" +  name + "&consigneeMobile=" +phone+ "&consigneeAddress=" +addr  + "&payPlatform=" + 1)
                                .asObject(BaseMsgBean.class)
                                .as(RxLife.asOnMain(this))
                                .subscribe(new BaseObserver<BaseMsgBean>() {
                                    @Override
                                    public void onNext(BaseMsgBean msgBean) {
                                        if (msgBean.getCode() == 0) {
                                            WXPAYbean wxpaYbean = JSONObject.parseObject(JSONObject.toJSONString(msgBean), WXPAYbean.class);

                                            //  wxOder=msgBean.getData().getOut_trade_no();
                                            goToWX(wxpaYbean);

                                        } else {
                                            ToastUtils.showShort("生成订单失败");
                                        }
                                    }

                                });
                        break;
                }

                break;
        }
    }

    /**
     * 前往微信页面支付
     */
    public void goToWX(WXPAYbean bean) {
        // 可以在当前页面注册,或者前面APP 注册

        if (mWxApi != null) {
            PayReq req = new PayReq();
            req.appId = CommonUtils.APP_ID_WX;// 微信开放平台审核通过的应用APPID
            req.partnerId = bean.getData().getPartnerid();// 微信支付分配的商户号
            req.prepayId = bean.getData().getPrepayid(); // 预支付订单号，app服务器调用“统一下单”接口获取
            req.nonceStr = bean.getData().getNoncestr();// 随机字符串，不长于32位咱生成
            req.timeStamp = bean.getData().getTimestamp();// 时间戳
            req.packageValue = bean.getData().getPackageX();// 固定值Sign=WXPay，服务器返回固定值
            req.sign = bean.getData().getSign();// 签名

            mWxApi.sendReq(req);
        }
    }


}

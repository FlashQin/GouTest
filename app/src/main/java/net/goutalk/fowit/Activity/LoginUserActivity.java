package net.goutalk.fowit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.rxjava.rxlife.RxLife;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LoginBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ACache;
import net.goutalk.fowit.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rxhttp.wrapper.param.RxHttp;

public class LoginUserActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_sms_phone)
    EditText etSmsPhone;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.txt_logain)
    TextView txtLogain;
    @BindView(R.id.txt_resiger)
    TextView txtResiger;
    @BindView(R.id.txt_forgot)
    TextView txtForgot;
    @BindView(R.id.img_wechat)
    ImageView imgWechat;
    private IWXAPI api;

    @Override
    public int getLayoutId() {
        return R.layout.layout_logain;
    }

    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(this, CommonUtils.APP_ID_WX, false);
        //将应用的appid注册到微信
        api.registerApp(CommonUtils.APP_ID_WX);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        finish();
        // CommonUtils.doubleClickExitApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.txt_logain, R.id.txt_resiger, R.id.txt_forgot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_logain:

                if (etSmsPhone.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if (edtPass.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort("请输入密码");
                    return;

                }

                RxHttp.postForm("/login.do")
                        .add("loginName", etSmsPhone.getText().toString().trim())
                        .add("password", edtPass.getText().toString().trim())//登录  type=1用户, 2商家,, type=2时name必传
                        .asObject(LoginBean.class)
                        .as(RxLife.asOnMain(this))
                        .subscribe(new BaseObserver<LoginBean>() {
                            @Override
                            public void onNext(LoginBean codeBean) {
                                if (codeBean.getCode() == 0) {
                                    ToastUtils.showShort("登录成功");
                                    SPUtils.getInstance().put("TOKEN", codeBean.getData().getEasyfowitToken());
                                    ACache.get(Utils.getApp()).put("USER_BEAN", GsonUtils.toJson(codeBean));
                                    finish();
                                } else {
                                    ToastUtils.showShort(codeBean.getMsg());
                                }
                            }
                        });
                break;
            case R.id.txt_resiger:
                Goto(ResigerActivity.class);
                break;
            case R.id.txt_forgot:
                Goto(ResigerActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SPUtils.getInstance().getString("TOKEN", "").equals("")) {
            finish();
            return;
        }
        if (!CommonUtils.code.equals("")) {
            // showDialog();
            //  getAccessToken(DataSave.code);
            getAccessToken(CommonUtils.code);
            CommonUtils.code = "";
        }
    }

    private void getAccessToken(String code) {
        //createProgressDialog();
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(CommonUtils.APP_ID_WX)
                .append("&secret=")
                .append(CommonUtils.WEI_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.d("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fan12", "onFailure: ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.d("fan12", "onResponse: " + responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WXLoagin(access, openId);
                //  getUserInfo(access, openId);
            }
        });


    }

    public void WXLoagin(String id, String toek) {
        RxHttp.postForm("/login/weChat.do?openid=" + toek + "&accessToken=" + id)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        ToastUtils.showShort(toek + "&accessToken=" + id + codeBean.getCode());
                        if (codeBean.getCode() == 0) {
                            LoginBean loginBean = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(codeBean), LoginBean.class);
                            ToastUtils.showShort("登录成功");
                            SPUtils.getInstance().put("TOKEN", loginBean.getData().getEasyfowitToken());
                            ACache.get(Utils.getApp()).put("USER_BEAN", GsonUtils.toJson(loginBean));
                            finish();


                        } else {
                            if (codeBean.getCode() == 2) {

                                Goto(BindPhoneActivity.class, "openid", toek,"token",codeBean.getData().toString());

                            }
                            ToastUtils.showShort(codeBean.getMsg());
                        }
                    }
                });
    }

    @OnClick(R.id.img_wechat)
    public void onViewClicked() {
        if (api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";//
            req.state = "wechat_sdk_微信登录";
            api.sendReq(req);
        } else {
            ToastUtils.showShort("没有安装微信");
        }
    }
}

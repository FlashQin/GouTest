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

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LoginBean;
import net.goutalk.fowit.R;
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

public class JiHuoActivity extends BaseActivity {




    @Override
    public int getLayoutId() {
        return R.layout.layout_jihuo;
    }

    @Override
    public void initView() {



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


}

package net.goutalk.fowit.wxapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.goutalk.fowit.utils.CommonUtils;


/**
 *
 *
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private String unionid;
    private String openid;
    private ProgressBar progressBar;
    private WXPayEntryActivity mContext;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mContext.getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, CommonUtils.APP_ID_WX, false);
        iwxapi.handleIntent(getIntent(), this);


    }


    @Override
    public void onReq(BaseReq baseReq) {

    }


    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {

            //支付成功  TODO
        CommonUtils.WXCODE=baseResp.errCode;
        switch (baseResp.errCode) {

            case 0:
                Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                finish();

                break;
            case -1://用户拒绝授权
                Toast.makeText(this,"配置错误",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case -2://用户取消
                Toast.makeText(this,"用户取消支付",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:

                break;
        }
    }




}


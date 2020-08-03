package net.goutalk.fowit.wxapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.goutalk.fowit.utils.CommonUtils;


/**

 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private String unionid;
    private String openid;
    private ProgressBar progressBar;
    private WXEntryActivity mContext;
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
//
//    private void createProgressDialog() {
//        mContext = this;
//        mProgressDialog = new ProgressDialog(mContext);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setTitle("提示");
//        mProgressDialog.setMessage("登录中，请稍后");
//        mProgressDialog.show();
//    }

    @Override
    public void onReq(BaseReq baseReq) {

    }


    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {

            case BaseResp.ErrCode.ERR_OK:
                finish();
                String code = ((SendAuth.Resp) baseResp).code;
                CommonUtils.code=code;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                Toast.makeText(this,"用户拒绝授权",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                Toast.makeText(this,"用户取消",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                finish();
                Toast.makeText(this,"QITA",Toast.LENGTH_SHORT).show();
                break;
        }
    }




}


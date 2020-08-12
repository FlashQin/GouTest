package net.goutalk.fowit.receve;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.sdklibrary.presenter.util.MyActivityManager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


import net.goutalk.fowit.Activity.GoodsInfoActivity;
import net.goutalk.fowit.Activity.LoginUserActivity;
import net.goutalk.fowit.Bean.ShareBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.BaseUiListener;
import net.goutalk.fowit.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static net.goutalk.fowit.utils.Util.bmpToByteArray;

/**
 *
 */
public class KDFReceiver extends BroadcastReceiver {
    private DialogPlus dialog_spec;
    private BaseQuickAdapter<ShareBean, BaseViewHolder> specAdapter;
    private String[] name = {"朋友圈", "微信好友", "QQ", "QQ空间"};
    private int[] pic = {R.mipmap.wd_yqhy_pyq, R.mipmap.wd_yqhy_weixin, R.mipmap.wd_yqhy_qq, R.mipmap.wd_yqhy_qq};
    public List<ShareBean> listshare = new ArrayList<>();
    private IWXAPI api;
    Context mconten;
    Activity activity;
    Tencent mTencent;

    @Override
    public void onReceive(Context context, Intent intent) {

        listshare.clear();
        for (int i = 0; i < 4; i++) {
            ShareBean shareBean = new ShareBean();
            shareBean.setId(i);
            shareBean.setImg(pic[i]);
            shareBean.setName(name[i]);
            listshare.add(shareBean);
        }
        mconten=context;

        String data = intent.getStringExtra("data");
      //  Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = null;
        try {
            if (!TextUtils.isEmpty(data)) {
                jsonObject = new JSONObject(data);
                String action = jsonObject.getString("action");
                if ("share".equals(action)) {
                    String param = jsonObject.getString("param");
                    if (MyActivityManager.getActivityManager().currentActivity()!=null){
                        //此处为获取sdk页面栈顶的activity 如果分享弹窗必须依附activity的话可以用此activity
                        Log.i("KDFinfoasshare", MyActivityManager.getActivityManager().currentActivity().getClass().getName());
                        activity= MyActivityManager.getActivityManager().currentActivity();
                        mTencent = Tencent.createInstance(CommonUtils.QQAPP_ID_WX, activity.getApplicationContext());
                        initSpecDialog();

                    }
                } else if ("login".equals(action)) { //跳转到登录
                    context.startActivity(new Intent(context, LoginUserActivity.class));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //规格参数dialog
    private void initSpecDialog() {
        ViewHolder viewHolder = new ViewHolder(R.layout.dialog_share_layout);
        dialog_spec = DialogPlus.newDialog(activity)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        TextView txtcancle = viewHolder.getInflatedView().findViewById(R.id.txtcancle);
        txtcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
            }
        });
        RecyclerView mRec = viewHolder.getInflatedView().findViewById(R.id.rec_list);
        mRec.setLayoutManager(new GridLayoutManager(activity, 4));
        mRec.setAdapter(specAdapter = new BaseQuickAdapter<ShareBean, BaseViewHolder>(R.layout.itemshare) {
            @Override
            protected void convert(BaseViewHolder helper, ShareBean item) {
                helper.setText(R.id.tvname, item.getName());
                Glide.with(activity).load(item.getImg()).into((ImageView) helper.getView(R.id.imgpic));


            }
        });
        specAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommonUtils.share = 1;
                dialog_spec.dismiss();
                if (position == 0) {
                    wechatshare(0);
                }
                if (position == 1) {

                    wechatshare(1);
                }
                if (position == 2) {
                    final Bundle params = new Bundle();
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    params.putString(QQShare.SHARE_TO_QQ_TITLE,"" );
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "");
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "");
                    // params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ));
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "勾转");
                    //   params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "");
                    sharetoQQ(activity, params);
                }
                if (position == 3) {
                    final Bundle miniProgramBundle = new Bundle();
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TITLE, "");
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "");
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"");
                    miniProgramBundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);

//                    ArrayList<String> imageUrls = new ArrayList<String>();
//                    imageUrls.add("https://share-commodity.youchengchefu.com/\"+ID+\"?from=singlemessage");
//                    miniProgramBundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                    //  params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH,"本地视频地址");


                    sharetoQzONE(activity, miniProgramBundle);
                }

            }
        });
        specAdapter.addData(listshare);
        dialog_spec.show();
    }

    public void wechatshare(int poistion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WXWebpageObject textObject = new WXWebpageObject();
                textObject.webpageUrl = "";
                WXMediaMessage msg = new WXMediaMessage(textObject);
                msg.title = "sd";
//                设置描述
                msg.description = "";
                Bitmap thumb = null;
//                try {
//                    thumb = BitmapFactory.decodeStream(new URL(imgs.get(0)).openStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//注意下面的这句压缩，120，150是长宽。

//一定要压缩，不然会分享失败

           //     Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 120, 150, true);

//Bitmap回收

               // thumb.recycle();

              //  msg.thumbData = bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                发送的内容
                req.message = msg;
//                创建唯一的标识
                req.transaction = buildTransction("text");
//                设置场景（好友==>朋友圈）
                if (poistion == 0) {
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
                } else {
                    req.scene = SendMessageToWX.Req.WXSceneSession;
                }
                api.sendReq(req);
            }
        }).start();
    }

    private String buildTransction(String str) {
        return (str == null) ? String.valueOf(System.currentTimeMillis()) : str + System.currentTimeMillis();
    }
    public void sharetoQQ(Activity context, Bundle bundle) {
        mTencent.shareToQQ(context, bundle, new BaseUiListener());
    }

    public void sharetoQzONE(Activity context, Bundle bundle) {
        mTencent.shareToQzone(context, bundle, qZoneShareListener);
    }

    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            // Util.toastMessage(QZoneShareActivity.this, "onCancel:test ");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            //   Util.toastMessage(QZoneShareActivity.this, "onError: " + e.errorMessage, "e");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            //    Util.toastMessage(QZoneShareActivity.this, "onComplete: " + response.toString());
        }

    };

}

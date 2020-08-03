package net.goutalk.fowit.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.R;
import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.AnswBean;
import net.goutalk.fowit.Bean.QustionBean;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.DislikeDialog;
import net.goutalk.fowit.utils.TTAdManagerHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/03/04
 * @modified 20/03/04
 * @description com.haichuang.chefu.ui.activitys
 */
public class QustionActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.recyclerview)
    RecyclerView rec_base;

    @BindView(R.id.banner_container)
    FrameLayout mExpressContainer;

    private DialogPlus dialog_spec;
    private BaseQuickAdapter<QustionBean.DataBean, BaseViewHolder> Adapter;
    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private Button mCreativeButton;
    BaseQuickAdapter<AnswBean, BaseViewHolder> madapter;
    String coin="0";
    @Override
    public int getLayoutId() {
        return R.layout.layout_qustion;
    }

    @Override
    public void initView() {

        tvTitle.setText("答题中");
//        tvRightTitle.setText("积分记录");
//        tvRightTitle.setVisibility(View.VISIBLE);
        //step2:创建TTAdNative对象，createAdNative(Context context) banner广告context需要传入Activity对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
        initRec();
        getData();
        loadBannerAd(CommonUtils.mQustionBanner);

    }

    //规格参数dialog
    private void initSpecDialog(String coin) {
        ViewHolder viewHolder = new ViewHolder(R.layout.layout_dt_window);
        dialog_spec = DialogPlus.newDialog(QustionActivity.this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setOverlayBackgroundResource(R.color.dialog_overlay_bg)
                .create();
        ImageView img = viewHolder.getInflatedView().findViewById(R.id.img_close);
        mExpressContainer = viewHolder.getInflatedView().findViewById(R.id.banner_container);
        TextView txt = viewHolder.getInflatedView().findViewById(R.id.txt_num_coin);
        TextView txtok = viewHolder.getInflatedView().findViewById(R.id.txt_exit);
        txt.setText(coin.replace("金币", ""));

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
                finish();
            }
        });
        txtok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spec.dismiss();
                getData();
            }
        });

        loadBannerAd(CommonUtils.mQustionBanner);
        HideLoading();
        dialog_spec.show();


    }
    private void postCoin(String coin) {

        RxHttp.postForm("/coinIncome/save.do")
                .add("quantity", coin.replace("金币", ""))
                .add("depict","抽奖")
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {

                          //  ToastUtils.showShort("得到" + coin + "金币");

                        } else {
                           // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                      //  ToastUtils.showShort(e.toString());
                    }
                });

    }
    private void initRec() {
        //初始化RecycleView
        rec_base.setLayoutManager(new LinearLayoutManager(getContext()));
       // rec_base.addItemDecoration(new SpacesItemDecoration(15));
        rec_base.setAdapter(Adapter = new BaseQuickAdapter<QustionBean.DataBean, BaseViewHolder>(R.layout.item_qustion) {
            @Override
            protected void convert(BaseViewHolder helper, QustionBean.DataBean item) {
                TextView txttitle = helper.getView(R.id.txtred);
                txttitle.setText(item.getQuestionName());

                List<AnswBean> liqu = new ArrayList<>();
                if (item.getChoiceA() != null) {
                    AnswBean bean = new AnswBean();
                    bean.setContent(item.getChoiceA());
                    bean.setType(1);
                    liqu.add(bean);
                }
                if (item.getChoiceB() != null) {
                    AnswBean bean = new AnswBean();
                    bean.setContent(item.getChoiceB());
                    bean.setType(1);
                    liqu.add(bean);
                }
                if (item.getChoiceC() != null) {
                    AnswBean bean = new AnswBean();
                    bean.setContent(item.getChoiceC());
                    bean.setType(1);
                    liqu.add(bean);
                }
                if (item.getChoiceD() != null) {
                    AnswBean bean = new AnswBean();
                    bean.setContent(item.getChoiceD());
                    bean.setType(1);
                    liqu.add(bean);
                }
                //子RecycleView


                RecyclerView rec_sub = helper.getView(R.id.recyclerview);
                rec_sub.setLayoutManager(new LinearLayoutManager(getContext()));
              //  rec_sub.addItemDecoration(new SpacesItemDecoration(15));
                rec_sub.setAdapter(madapter = new BaseQuickAdapter<AnswBean, BaseViewHolder>(R.layout.item_qustion_little, liqu) {
                    @Override
                    protected void convert(BaseViewHolder helper, AnswBean item1) {
                        RelativeLayout relbac = helper.getView(R.id.rel_bac);
                        ImageView imgright = helper.getView(R.id.img_right);
                        TextView txttitle = helper.getView(R.id.txtred);
                        txttitle.setText(item1.getContent());
                        if (item1.getType() == 1) {
                            relbac.setBackgroundResource(R.drawable.drawable_qust_item);
                            imgright.setVisibility(View.GONE);
                        }
                        if (item1.getType() == 2) {
                            relbac.setBackgroundResource(R.drawable.drawable_qust_rightitem);
                            imgright.setBackgroundResource(R.mipmap.zhenque);
                            imgright.setVisibility(View.VISIBLE);
                            txttitle.setTextColor(Color.WHITE);
                        }
                        if (item1.getType() == 3) {
                            relbac.setBackgroundResource(R.drawable.drawable_qust_rongitem);
                            imgright.setBackgroundResource(R.mipmap.cuowu);
                            imgright.setVisibility(View.VISIBLE);
                            txttitle.setTextColor(Color.WHITE);
                        }
                        helper.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item1.getContent().contains(item.getAnswer())) {//答对
                                    coin= String.valueOf(item.getScore());
                                    for (int i = 0; i < liqu.size(); i++) {
                                        if (liqu.get(i).getContent().equals(item.getAnswer())) {
                                            liqu.get(i).setType(2);
                                        }
                                    }

                                } else {//答错

                                    coin="0";
                                    for (int i = 0; i < liqu.size(); i++) {
                                        if (liqu.get(i).getContent().equals(item1.getContent())) {
                                            liqu.get(i).setType(3);
                                        }
                                        if (liqu.get(i).getContent().equals(item.getAnswer())) {
                                            liqu.get(i).setType(2);
                                        }
                                    }
                                }
                                madapter.notifyDataSetChanged();
                                ShowLoading();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        initSpecDialog(coin);
                                        postCoin(coin);
                                    }
                                }, 1000);

                            }
                        });

                    }
                });

                //EmptyView
//        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.rec_emp_shop_car_layout, null);
//        inflate.findViewById(R.id.btn_GG).setOnClickListener(v -> Goto(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        mAdapter.setEmptyView(inflate);
            }

        });
    }

    public void getData() {
        //签到
        RxHttp.get("/examQuestion/findRandom.json?n=" + 1)
                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean scoreBean) {

                        if (scoreBean.getCode() == 0) {
                            QustionBean codeBean = JSONObject.parseObject(JSONObject.toJSONString(scoreBean), QustionBean.class);


                            Adapter.setNewData(codeBean.getData());


                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTAd != null) {
            mTTAd.destroy();
        }
        if (mCreativeButton != null) {
            mCreativeButton = null;
        }
    }

    private void loadBannerAd(String codeId) {
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(320, 150) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(320, 150)//这个参数设置即可，不影响个性化模板广告的size
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //  TToast.show(NativeBannerActivity.this, "load error : " + code + ", " + message);
            }

            @Override

            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads.get(0) == null) {
                    return;
                }
                mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(30 * 1000);//设置轮播间隔 ms,不调用则不进行轮播展示
                bindAdListener(mTTAd);
                mTTAd.render();//调用render开始渲染广告

            }
        });
    }

    //绑定广告行为
    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                //  TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                //TToast.show(mContext, "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                //Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                // TToast.show(mContext, msg+" code:"+code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                //返回view的宽高 单位 dp
                //  TToast.show(mContext, "渲染成功");
                //在渲染成功回调时展示广告，提升体验
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });
        //dislike设置
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        //可选，下载监听设置
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                // TToast.show(BannerExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                // if (!mHasShowDownloadActive) {
                //  mHasShowDownloadActive = true;
                //  TToast.show(BannerExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
                // }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                //  TToast.show(BannerExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                //  TToast.show(BannerExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                // TToast.show(BannerExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 设置广告的不喜欢，开发者可自定义样式
     *
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(this, words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                    // TToast.show(mContext, "点击 " + filterWord.getName());
                    //用户选择不喜欢原因后，移除广告展示
                    mExpressContainer.removeAllViews();
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认个性化模板中默认dislike弹出样式
        ad.setDislikeCallback(QustionActivity.this, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                // TToast.show(mContext, "点击 " + value);
                //用户选择不喜欢原因后，移除广告展示
                mExpressContainer.removeAllViews();
            }

            @Override
            public void onCancel() {
                //TToast.show(mContext, "点击取消 ");
            }

            @Override
            public void onRefuse() {

            }
        });
    }

}

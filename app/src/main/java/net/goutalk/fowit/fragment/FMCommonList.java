package net.goutalk.fowit.fragment;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;

import net.goutalk.fowit.Activity.GoodsInfoActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.LikeBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.SpacesItemDecoration;
import net.goutalk.fowit.utils.Urls;

import java.io.IOException;
import java.util.TreeMap;

import butterknife.BindView;
import rxhttp.wrapper.param.RxHttp;


public class FMCommonList extends BaseFragment {
    @BindView(R.id.rv_detail)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.webView)
    WebView webView;
    private BaseQuickAdapter<LikeBean.DataBean, BaseViewHolder> mAdapterList;


    @Override
    protected void initView(View view) {

        initHomeList();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_body_tuil;
    }

    @Override
    protected void fetchData() {

    }
    private void initHomeList() {

        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<LikeBean.DataBean, BaseViewHolder>(R.layout.item_rec_shop_layout) {
            @Override
            protected void convert(BaseViewHolder helper, LikeBean.DataBean item) {


                ImageView imageView=helper.itemView.findViewById(R.id.img);
                TextView txtname=helper.itemView.findViewById(R.id.tv_name);
                TextView txtjuan=helper.itemView.findViewById(R.id.tv_sell_count);
                TextView txtshichang=helper.itemView.findViewById(R.id.tv_location);
                TextView txtgou=helper.itemView.findViewById(R.id.tv_open_time);
                TextView txtsale=helper.itemView.findViewById(R.id.sale);

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getTitle());
                txtjuan.setText("卷后价 "+item.getActualPrice() + "");
                txtshichang.setText("市场价￥ "+item.getOriginalPrice() + "");
                txtgou.setText("勾转专享价￥"+item.getMonthSales() + "");

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });
        getData();

    }
    public void getData(){

        String likeid= SPUtils.getInstance().getString("likeid", "");
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("id", likeid);
        paraMap.put("size", "40");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urllove + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            LikeBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), LikeBean.class);

                            mAdapterList.setNewData(nineToNineBean.getData());

                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

}

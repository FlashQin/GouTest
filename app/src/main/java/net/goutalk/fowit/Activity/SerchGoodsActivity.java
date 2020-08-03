package net.goutalk.fowit.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.SerchBean;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

public class SerchGoodsActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.layout_top_search)
    LinearLayout layoutTopSearch;
    @BindView(R.id.rec_his)
    RecyclerView recyclerviewHomeList;
    int pagenum = 1, pageSize = 20;
    @BindView(R.id.smallLabel)
    SmartRefreshLayout smallLabel;
    private BaseQuickAdapter<SerchBean.DataBean.ListBean, BaseViewHolder> mAdapterList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_other;
    }

    @Override
    public void initView() {
        SearchClick();
        initHomeList();
    }

    private void initHomeList() {
        smallLabel.setOnLoadMoreListener(this);
        smallLabel.setOnRefreshListener(this);
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(SerchGoodsActivity.this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<SerchBean.DataBean.ListBean, BaseViewHolder>(R.layout.item_rec_shop_layout) {
            @Override
            protected void convert(BaseViewHolder helper, SerchBean.DataBean.ListBean item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img);
                TextView txtname = helper.itemView.findViewById(R.id.tv_name);
                TextView txtjuan = helper.itemView.findViewById(R.id.tv_sell_count);
                TextView txtshichang = helper.itemView.findViewById(R.id.tv_location);
                TextView txtgou = helper.itemView.findViewById(R.id.tv_open_time);
                TextView txtsale = helper.itemView.findViewById(R.id.sale);

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                Glide.with(SerchGoodsActivity.this).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getTitle());
                txtjuan.setText("卷后价 " + item.getActualPrice() + "");
                txtshichang.setText("市场价￥ " + item.getOriginalPrice() + "");
                txtgou.setText("勾转专享价￥" + item.getMonthSales() + "");

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });

    }

    public void getNine() {
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.3.0");
        paraMap.put("pageId", String.valueOf(pagenum));
        paraMap.put("keyWords", etSearch.getText().toString().trim());
        paraMap.put("type", "0");
        paraMap.put("pageSize", String.valueOf(pageSize));
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = Urls.urlSerch + p;
        RxHttp.get(url)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        smallLabel.finishRefresh();
                        if (codeBean.getCode() == 0) {
                            SerchBean serchBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), SerchBean.class);

                            if (pagenum == 1) {
                                mAdapterList.setNewData(serchBean.getData().getList());
                            } else {
                                mAdapterList.addData(serchBean.getData().getList());
                            }

                            if (serchBean.getData().getList().size() != 0) {
                                pagenum++;
                            } else {
                                smallLabel.setNoMoreData(true);
                            }
                            smallLabel.finishLoadMore();

                        } else {
                            smallLabel.finishLoadMore();
                            // ToastUtils.showShort(codeBean.getMsg());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        smallLabel.finishRefresh();
                        smallLabel.finishLoadMore();
                    }
                });
    }

    private void SearchClick() {
        enterListener(etSearch, text -> {
            String trim_his = etSearch.getText().toString().trim();
            // etSearch.setText("");
            getNine();

        });
    }

    //回车监听工具
    public static void enterListener(EditText editText, CommonUtils.onChangeText onChangeText) {
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editText.getText().length() == 0) return false;
                String trim_his = editText.getText().toString().trim();
                onChangeText.onChangeText(trim_his);
               // editText.setText("");
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getNine();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pagenum=1;
        getNine();
    }
}

package net.goutalk.fowit.fragment;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import net.goutalk.fowit.Activity.GoodsInfoActivity;
import net.goutalk.fowit.Activity.HotActivity;
import net.goutalk.fowit.Activity.LoveActivity;
import net.goutalk.fowit.Activity.NewGoodsActivity;
import net.goutalk.fowit.Activity.NineActivity;
import net.goutalk.fowit.Activity.RankActivity;
import net.goutalk.fowit.Activity.SerchGoodsActivity;
import net.goutalk.fowit.Base.BaseFragment;
import net.goutalk.fowit.Base.BaseMsgBean;
import net.goutalk.fowit.Bean.DongBean;
import net.goutalk.fowit.Bean.HomeTabBean;
import net.goutalk.fowit.Bean.RankGoodsBean;
import net.goutalk.fowit.R;
import net.goutalk.fowit.net.BaseObserver;
import net.goutalk.fowit.utils.ApiTest;
import net.goutalk.fowit.utils.CommonUtils;
import net.goutalk.fowit.utils.GlideImageLoader;
import net.goutalk.fowit.utils.SignMD5Util;
import net.goutalk.fowit.utils.SpacesItemDecoration;
import net.goutalk.fowit.utils.Urls;
import net.goutalk.fowit.wigde.XCollLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


public class HomeFragment extends BaseFragment {



    @BindView(R.id.tv_search)
    LinearLayout tvSearch;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview_home_te)
    RecyclerView recyclerviewHomeTe;

    @BindView(R.id.recyclerview_home_new)
    RecyclerView recyclerviewHomeNew;
    @BindView(R.id.recyclerview_home_list)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @BindView(R.id.rec_one)
    RecyclerView recOne;
    @BindView(R.id.rec_two)
    RecyclerView recTwo;

    private BaseQuickAdapter<HomeTabBean, BaseViewHolder> mAdapterTe;
    private BaseQuickAdapter<DongBean.DataBean.RoundsListBean, BaseViewHolder> mAdapterOne;
    private BaseQuickAdapter<DongBean.DataBean.GoodsListBean, BaseViewHolder> mAdapterRen;
    private BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder> mAdapterNew;
    private BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder> mAdapterList;
    private String[] name = {"0元购", "9.9包邮", "猜你喜欢", "新品推荐", "热门推荐", "每日半价", "折上折", "限时爆品"};
    private int[] pic = {R.drawable.sc_lygou, R.drawable.sc_jjbaoyou, R.drawable.sc_cnxh, R.drawable.sc_xptj, R.drawable.sc_rmtj, R.drawable.sc_mrbj, R.drawable.sc_zsz, R.drawable.sc_xsbp};
    String roundTime = "";

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
        // viewFit.getLayoutParams().height = BarUtils.getStatusBarHeight();
    }

    @Override
    protected void initView(View view) {


        initDong();
        initBanner();
        initHomeTab();
        initHomeNew();
        initHomeList();


    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void fetchData() {

    }

    public void initBanner() {
        final ArrayList<String> imgs = new ArrayList<>();
        imgs.add(Urls.videoPosterList[0]);
        imgs.add(Urls.videoPosterList[2]);
        imgs.add(Urls.videoPosterList[1]);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader(10, true));
        banner.setBannerAnimation(Transformer.Default);
        banner.setImages(imgs);
        banner.start();
    }

    private void initHomeTab() {
        List<HomeTabBean> listHomeTab = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            HomeTabBean bean = new HomeTabBean();
            bean.setIcon(pic[i]);
            bean.setName(name[i]);
            listHomeTab.add(bean);
        }
        //初始化RecycleView
        recyclerviewHomeTe.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerviewHomeTe.addItemDecoration(new SpacesItemDecoration(15));

        recyclerviewHomeTe.setAdapter(mAdapterTe = new BaseQuickAdapter<HomeTabBean, BaseViewHolder>(R.layout.item_home_tab, listHomeTab) {
            @Override
            protected void convert(BaseViewHolder helper, HomeTabBean item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                TextView txtname = helper.itemView.findViewById(R.id.txtname);
                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getName());
                //EmptyView


            }


        });
        mAdapterTe.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "1");
                }
                if (position == 1) {
                    Goto(NineActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 2) {
                    Goto(LoveActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 3) {
                    Goto(NewGoodsActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 4) {
                    Goto(HotActivity.class, "name", mAdapterTe.getData().get(position).getName());
                }
                if (position == 5) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "3");
                }
                if (position == 6) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "4");
                }
                if (position == 7) {
                    Goto(RankActivity.class, "name", mAdapterTe.getData().get(position).getName(), "type", "7");
                }

            }
        });
    }

    public void initDong() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recOne.setLayoutManager(ms);
        recOne.addItemDecoration(new SpacesItemDecoration(15));
        recOne.setAdapter(mAdapterOne = new BaseQuickAdapter<DongBean.DataBean.RoundsListBean, BaseViewHolder>(R.layout.item_dongone) {
            @SuppressLint("ResourceAsColor")
            @Override
            protected void convert(BaseViewHolder helper, DongBean.DataBean.RoundsListBean item) {


                TextView ttxpeice = helper.itemView.findViewById(R.id.txt);

                String time = item.getDdqTime();
                String content = "";
                if (item.getStatus() == 0) {
                    content = "\n已抢完";
                }
                if (item.getStatus() == 1) {
                    content = "\n已开抢";
                }
                if (item.getStatus() == 2) {
                    content = "\n待开始";
                }
                ttxpeice.setText(time.substring(11, 16) + "场" + content);
                if (item.getDdqTime().equals(roundTime)) {
                    ttxpeice.setBackgroundResource(R.drawable.dralbe_txt_dong_red);
                    ttxpeice.setTextColor(getResources().getColor(R.color.white));
                } else {
                    ttxpeice.setBackgroundResource(R.drawable.dralbe_txt_dong);
                    ttxpeice.setTextColor(getResources().getColor(R.color.list_divider));
                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        roundTime = item.getDdqTime();
                        mAdapterOne.notifyDataSetChanged();

                        getDongData();

                    }
                });
                //EmptyView

            }

        });

        //初始化RecycleView
        LinearLayoutManager mss = new LinearLayoutManager(getActivity());
        mss.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recTwo.setLayoutManager(mss);
        recTwo.addItemDecoration(new SpacesItemDecoration(15));
        recTwo.setAdapter(mAdapterRen = new BaseQuickAdapter<DongBean.DataBean.GoodsListBean, BaseViewHolder>(R.layout.item_home_ren) {
            @Override
            protected void convert(BaseViewHolder helper, DongBean.DataBean.GoodsListBean item) {

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
                TextView txtmenber = helper.itemView.findViewById(R.id.txtmenberprice);
                TextView txtprice = helper.itemView.findViewById(R.id.txtprice);
                TextView sale = helper.itemView.findViewById(R.id.txtsale);
//                imageView.setImageResource(item.getIcon());
                txtprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                txtprice.setText("￥" + item.getOriginalPrice() + "");
                txtmenber.setText("￥" + item.getActualPrice() + "");
                sale.setText("已抢" + item.getMonthSales());

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //     if (item.getActivityType()==3) {
                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
//                        }else {
//                            ToastUtils.showShort("活动未开始或已结束");
//                        }
                    }
                });
                //EmptyView

            }

        });

        getDongData();

    }

    public void getDongData() {
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        if (!roundTime.equals("")) {
            paraMap.put("roundTime", roundTime);
        }
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urldong + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            DongBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), DongBean.class);

                            if (roundTime.equals("")) {
                                roundTime = nineToNineBean.getData().getDdqTime();
                                mAdapterOne.setNewData(nineToNineBean.getData().getRoundsList());
                            }
                            roundTime = nineToNineBean.getData().getDdqTime();
                            mAdapterRen.setNewData(nineToNineBean.getData().getGoodsList());
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

    private void initHomeNew() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeNew.setLayoutManager(ms);
        recyclerviewHomeNew.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeNew.setAdapter(mAdapterNew = new BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder>(R.layout.item_home_ren) {
            @Override
            protected void convert(BaseViewHolder helper, RankGoodsBean.DataBean item) {

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                ImageView imageView = helper.itemView.findViewById(R.id.img_mainpic);
                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
                TextView txtmenber = helper.itemView.findViewById(R.id.txtmenberprice);
                TextView txtprice = helper.itemView.findViewById(R.id.txtprice);
                TextView sale = helper.itemView.findViewById(R.id.txtsale);
//                imageView.setImageResource(item.getIcon());
                txtprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                txtprice.setText("￥" + item.getOriginalPrice() + "");
                txtmenber.setText("￥" + item.getActualPrice() + "");
                sale.setText("已抢" + item.getMonthSales());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Goto(GoodsInfoActivity.class, "id", String.valueOf(item.getId()), "goodsid", String.valueOf(item.getGoodsId()));
                    }
                });
            }

        });

        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", "1");
        paraMap.put("pageSize", "20");
        paraMap.put("rankType", "1");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urlranking + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            RankGoodsBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), RankGoodsBean.class);

                            mAdapterNew.setNewData(nineToNineBean.getData());
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

    private void initHomeList() {
        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<RankGoodsBean.DataBean, BaseViewHolder>(R.layout.item_rec_shop_layout) {
            @Override
            protected void convert(BaseViewHolder helper, RankGoodsBean.DataBean item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img);
                TextView txtname = helper.itemView.findViewById(R.id.tv_name);
                TextView txtjuan = helper.itemView.findViewById(R.id.tv_sell_count);
                TextView txtshichang = helper.itemView.findViewById(R.id.tv_location);
                TextView txtgou = helper.itemView.findViewById(R.id.tv_open_time);
                TextView txtsale = helper.itemView.findViewById(R.id.sale);

                RequestOptions options = new RequestOptions().transform(new RoundedCorners(15));

                Glide.with(getActivity()).load(item.getMainPic()).apply(options).into(imageView);
//                imageView.setImageResource(item.getIcon());
                txtname.setText(item.getTitle());
                txtjuan.setText("领券减 " + item.getCouponPrice() + "");
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
        TreeMap<String, String> paraMap = new TreeMap<>();
        paraMap.put("appKey", CommonUtils.TAOAPPKEY);
        paraMap.put("version", "v1.2.3");
        paraMap.put("pageId", "1");
        paraMap.put("pageSize", "20");
        paraMap.put("rankType", "2");
        paraMap.put("sign", SignMD5Util.getSignStr(paraMap, CommonUtils.TAOSERCT));
        String p = null;
        try {
            p = ApiTest.buildQuery(paraMap, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        RxHttp.get(Urls.urlranking + p)

                .asObject(BaseMsgBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(new BaseObserver<BaseMsgBean>() {
                    @Override
                    public void onNext(BaseMsgBean codeBean) {
                        if (codeBean.getCode() == 0) {
                            RankGoodsBean nineToNineBean = JSONObject.parseObject(JSONObject.toJSONString(codeBean), RankGoodsBean.class);
                            SPUtils.getInstance().put("likeid", String.valueOf(nineToNineBean.getData().get(0).getId()));

                            mAdapterList.setNewData(nineToNineBean.getData());
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

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        Goto(SerchGoodsActivity.class);
    }
}
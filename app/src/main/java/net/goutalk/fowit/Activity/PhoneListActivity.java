package net.goutalk.fowit.Activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rxjava.rxlife.RxLife;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.goutalk.fowit.Base.BaseActivity;
import net.goutalk.fowit.Bean.PhoneDto;
import net.goutalk.fowit.R;
import net.goutalk.fowit.utils.PhoneUtil;
import net.goutalk.fowit.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneListActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_title)
    TextView tvRightTitle;
    @BindView(R.id.title_img)
    ImageView titleImg;
    @BindView(R.id.choose)
    TextView choose;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.rec)
    RecyclerView recyclerviewHomeList;
    @BindView(R.id.imgchoose)
    ImageView imgchoose;
    @BindView(R.id.choosenums)
    TextView choosenums;
    @BindView(R.id.txtall)
    TextView txtall;
    @BindView(R.id.sdfsdf)
    LinearLayout sdfsdf;
    private BaseQuickAdapter<PhoneDto, BaseViewHolder> mAdapterList;
    List<PhoneDto> lidata = new ArrayList<>();

    int nums=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_phonelist;
    }

    @Override
    public void initView() {

        tvTitle.setText("短信推广");
        initHomeList();
    }

    private void initHomeList() {

        //初始化RecycleView
        LinearLayoutManager ms = new LinearLayoutManager(PhoneListActivity.this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);// 设置 recyclerview 布局方式为
        recyclerviewHomeList.setLayoutManager(ms);
        recyclerviewHomeList.addItemDecoration(new SpacesItemDecoration(15));
        recyclerviewHomeList.setAdapter(mAdapterList = new BaseQuickAdapter<PhoneDto, BaseViewHolder>(R.layout.item_phone) {
            @Override
            protected void convert(BaseViewHolder helper, PhoneDto item) {


                ImageView imageView = helper.itemView.findViewById(R.id.img_select_wx);
                TextView txtname = helper.itemView.findViewById(R.id.name);
                TextView phone = helper.itemView.findViewById(R.id.phone);
                txtname.setText(item.getName());
                phone.setText(item.getTelPhone());

                if (item.isChoose() == true) {

                    imageView.setImageDrawable(getResouseDrawable(R.drawable.yuanzhong));

                } else {
                    imageView.setImageDrawable(getResouseDrawable(R.drawable.yuanbai));

                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int i = 0; i < lidata.size(); i++) {
                            if (lidata.get(i).getTelPhone().equals(item.getTelPhone())) {
                                if (item.isChoose() == true) {
                                    lidata.get(i).setChoose(false);
                                    nums--;
                                } else {
                                    lidata.get(i).setChoose(true);
                                    nums++;
                                }
                            }
                        }
                        choosenums.setText(nums+"");
                        mAdapterList.notifyDataSetChanged();

                    }
                });
            }

        });
        new RxPermissions(PhoneListActivity.this)
                .request(Manifest.permission.READ_CONTACTS)
                .as(RxLife.asOnMain(PhoneListActivity.this))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        //申请的权限全部允许
                        lidata = new PhoneUtil(this).getPhone();
                        txtall.setText("/"+lidata.size()+"");
                        mAdapterList.setNewData(lidata);
                    } else {
                        //只要有一个权限被拒绝，就会执行
                        ToastUtils.showShort("无文件访问权限");
                    }
                });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.linall, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.linall:
                if (choose.getText().toString().equals("全选")) {
                    for (int i = 0; i < lidata.size(); i++) {

                        if (lidata.get(i).isChoose()==false){
                            nums++;
                        }
                        lidata.get(i).setChoose(true);

                    }
                    imgchoose.setImageDrawable(getResouseDrawable(R.drawable.yuanzhong));

                    mAdapterList.notifyDataSetChanged();
                    choose.setText("取消");
                } else {
                    for (int i = 0; i < lidata.size(); i++) {

                        if (lidata.get(i).isChoose()==true){
                            nums--;
                        }
                        lidata.get(i).setChoose(false);
                    }
                    mAdapterList.notifyDataSetChanged();
                    choose.setText("全选");
                    //lidataChoose.clear();
                    imgchoose.setImageDrawable(getResouseDrawable(R.drawable.yuanbai));
                }
                choosenums.setText(nums+"");
                break;
            case R.id.send:
                new RxPermissions(PhoneListActivity.this)
                        .request(Manifest.permission.SEND_SMS)
                        .as(RxLife.asOnMain(PhoneListActivity.this))
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                //申请的权限全部允许
                                sendMessge();
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                ToastUtils.showShort("无文件访问权限");
                            }
                        });

                break;
        }
    }

    public void sendMessge() {

        String message="Hi! 我在勾转零元购，在勾转购物，玩游戏，看视频就能赚零花钱哦！点击领取：http://openbox.mobilem.360.cn/index/d/sid/4535355 输入我的邀请码："+ SPUtils.getInstance().getString("code", "123")+" 还能获得最多66元大红包，1元就能提现，快来领取吧！(24小时过期)。";
        for (int i = 0; i < lidata.size(); i++) {
            if (lidata.get(i).isChoose() == true) {
                SmsManager sManager = SmsManager.getDefault();
                // 创建一个PendingIntent对象
                // 拆分短信内容（手机短信长度限制）
                List<String> phoneList = sManager.divideMessage(message);
                if(message.length() > 70){
                    //拆分短信
                    //发送短信
                    sManager.sendMultipartTextMessage(lidata.get(i).getTelPhone().replace(" ",""), null, (ArrayList<String>) phoneList, null, null);
                }else {
                    //不超过70字时使用sendTextMessage发送
                    sManager.sendTextMessage(lidata.get(i).getTelPhone().replace(" ",""), null, message, null, null);
                }
                // 发送短信

                //sManager.sendTextMessage("15881143606", null, cont, null, null);
            }
        }
        ToastUtils.showShort("已发送");
        finish();

        // 提示短信群发完成
    }
}

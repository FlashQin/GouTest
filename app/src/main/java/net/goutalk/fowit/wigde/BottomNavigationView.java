package net.goutalk.fowit.wigde;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import net.goutalk.fowit.R;

import java.util.Objects;



/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/25
 * @modified 20/02/25
 * @description com.haichuang.read.ui.widgets
 */
public class BottomNavigationView extends FrameLayout {

    private Context mContext;
    private XTabLayout xTabLayout;
    private TextView tv_main_1;
    //private TextView tv_main_2;
    private TextView tv_main_3;
    private TextView tv_main_4;
    private TextView tvgame;
    private ImageView img_main_1;
   // private ImageView img_main_2;
    private ImageView img_main_3;
    private ImageView img_main_4;
    private ImageView imggame;
    public BottomNavigationView(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.btn_navi_rec_layout, null);
        this.addView(inflate);
        xTabLayout = inflate.findViewById(R.id.tabs);
        tv_main_1 = findViewById(R.id.tv_main_1);
       // tv_main_2 = findViewById(R.id.tv_main_2);
        tv_main_3 = findViewById(R.id.tv_main_3);
        tv_main_4 = findViewById(R.id.tv_main_4);
        tvgame = findViewById(R.id.tv_game_1);
        img_main_1 = findViewById(R.id.img_main_1);
        //img_main_2 = findViewById(R.id.img_main_2);
        img_main_3 = findViewById(R.id.img_main_3);
        img_main_4 = findViewById(R.id.img_main_4);
        imggame = findViewById(R.id.img_game_1);

//        Objects.requireNonNull(xTabLayout.getTabAt(1)).select();

        inflate.findViewById(R.id.layout_main_1).setOnClickListener(v -> changeStatus(1));
        inflate.findViewById(R.id.layout_game_1).setOnClickListener(v -> changeStatus(2));
       // inflate.findViewById(R.id.layout_main_2).setOnClickListener(v -> changeStatus(3));
        inflate.findViewById(R.id.layout_main_3).setOnClickListener(v -> changeStatus(3));
        inflate.findViewById(R.id.layout_main_4).setOnClickListener(v -> changeStatus(4));
    }

    public void setupWithViewPager(ViewPager viewPager){
        if (xTabLayout==null) try {
            throw new Exception("xTabLayout is null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        xTabLayout.setupWithViewPager(viewPager);
    }

    private void changeStatus(int type) {
        Objects.requireNonNull(xTabLayout.getTabAt(--type)).select();
        switch (++type){
            case 1:
                tv_main_1.setTextColor(getResources().getColor(R.color.login_main_color));
                img_main_1.setImageResource(R.drawable.tb_icon_gw);

                tvgame.setTextColor(getResources().getColor(R.color.color_ababab));
                imggame.setImageResource(R.drawable.tb_icon_yx_1);

//                tv_main_2.setTextColor(getResources().getColor(R.color.color_ababab));
//                img_main_2.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_3.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_3.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_4.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_4.setImageResource(R.drawable.tb_icon_wd_1);
                break;
            case 2:
                tv_main_1.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_1.setImageResource(R.drawable.tb_icon_gw_1);

                tvgame.setTextColor(getResources().getColor(R.color.login_main_color));
                imggame.setImageResource(R.drawable.tb_icon_yx);

//                tv_main_2.setTextColor(getResources().getColor(R.color.color_ababab));
//                img_main_2.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_3.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_3.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_4.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_4.setImageResource(R.drawable.tb_icon_wd_1);
                break;

            case 3:
                tv_main_1.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_1.setImageResource(R.drawable.tb_icon_gw_1);

                tvgame.setTextColor(getResources().getColor(R.color.color_ababab));
                imggame.setImageResource(R.drawable.tb_icon_yx_1);

//                tv_main_2.setTextColor(getResources().getColor(R.color.color_ababab));
//                img_main_2.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_3.setTextColor(getResources().getColor(R.color.login_main_color));
                img_main_3.setImageResource(R.drawable.tb_icon_sp);

                tv_main_4.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_4.setImageResource(R.drawable.tb_icon_wd_1);
                break;
            case 4:
                tv_main_1.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_1.setImageResource(R.drawable.tb_icon_gw_1);

                tvgame.setTextColor(getResources().getColor(R.color.color_ababab));
                imggame.setImageResource(R.drawable.tb_icon_yx_1);

//                tv_main_2.setTextColor(getResources().getColor(R.color.color_ababab));
//                img_main_2.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_3.setTextColor(getResources().getColor(R.color.color_ababab));
                img_main_3.setImageResource(R.drawable.tb_icon_sp_1);

                tv_main_4.setTextColor(getResources().getColor(R.color.login_main_color));
                img_main_4.setImageResource(R.drawable.tb_icon_wd);
                break;
        }
    }

    public void setCurrentItem(int position) {
        changeStatus(position);
    }

}

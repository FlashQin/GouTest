package net.goutalk.fowit.Adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.goutalk.fowit.R;

import static net.goutalk.fowit.utils.CommonUtils.checkImgUrl;


public class MinePicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public deltlistern deltlistern;

    public void setDeltlistern(deltlistern deltlistern) {
        this.deltlistern = deltlistern;
    }

    public MinePicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        LoadImage(helper, item);
    }

    private void LoadImage(BaseViewHolder helper, String item) {
        ImageView img = helper.itemView.findViewById(R.id.img);
        ImageView imgclose = helper.itemView.findViewById(R.id.imgclose);
        if (item.equals("")) {
            Glide.with(helper.itemView.getContext()).load(R.drawable.ic_add_img)
                    .into(img);
            imgclose.setVisibility(View.GONE);
        } else {
            Glide.with(helper.itemView.getContext()).load(checkImgUrl(item))
                    .into(img);
            imgclose.setVisibility(View.VISIBLE);
        }
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deltlistern != null) {
                    deltlistern.delt(item);
                }
            }
        });
    }

    public interface deltlistern {
        void delt(String url);
    }
}
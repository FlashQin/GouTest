package net.goutalk.fowit.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/02/28
 * @modified 20/02/28
 * @description com.haichuang.chefu.utils
 */
public class GlideImageLoader extends ImageLoader {

    private int Round_dp = 10;
    private boolean isCheckUrl = false;

    public GlideImageLoader(int round_dp, boolean isCheckUrl) {
        Round_dp = round_dp;
        this.isCheckUrl = isCheckUrl;
    }

    public GlideImageLoader(int round_dp) {
        Round_dp = round_dp;
    }

    public GlideImageLoader() {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (!isCheckUrl) {
            Glide.with(context)
                    .load(path)
                    .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context, Round_dp)))
                    .into(imageView);
        } else {
            try {
                Glide.with(context)
                        .load(CommonUtils.checkImgUrl(String.valueOf(path)))
                        .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(context, Round_dp)))
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

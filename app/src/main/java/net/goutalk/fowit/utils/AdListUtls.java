package net.goutalk.fowit.utils;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public class AdListUtls {

    private static final int AD_POSITION = 3;
    private static final int LIST_ITEM_COUNT = 30;
    private List<TTFeedAd> mData;
    private TTAdNative mTTAdNative;
    public List<TTFeedAd> getAdList(String codeid, SmartRefreshLayout smartRefreshLayout){
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeid)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(20)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishLoadMore();
                }
                if (ads == null || ads.isEmpty()) {
                    return;
                }

                for (int i = 0; i < LIST_ITEM_COUNT; i++) {
                    mData.add(null);
                }

                int count = mData.size();
                for (TTFeedAd ad : ads) {
                    int random = (int) (Math.random() * LIST_ITEM_COUNT) + count - LIST_ITEM_COUNT;
                    mData.set(random, ad);
                }



            }
        });
        return mData;
    }

}

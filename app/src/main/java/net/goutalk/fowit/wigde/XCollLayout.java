package net.goutalk.fowit.wigde;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class XCollLayout extends CollapsingToolbarLayout {

    public OnScrimsListener mListener; // 渐变监听

    public void setmListener(OnScrimsListener mListener) {
        this.mListener = mListener;
    }

    Boolean isCurrentScrimsShown = false; // 当前渐变状态

    public XCollLayout(Context context) {
        super(context);
    }

    public XCollLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public XCollLayout(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }


    @Override
    public void setScrimsShown(boolean shown, boolean animate) {
        super.setScrimsShown(shown, animate);
        if (isCurrentScrimsShown != shown) {
            isCurrentScrimsShown = shown;
            mListener.onScrimsStateChange(shown);
        }
    }

    public interface OnScrimsListener {

        /**
         * 渐变状态变化
         */
        void onScrimsStateChange(Boolean shown);
    }

}

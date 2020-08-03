package net.goutalk.fowit.wigde;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.dynamicanimation.animation.SpringAnimation;

import com.blankj.utilcode.util.SizeUtils;

public class SpringScrollView extends NestedScrollView {

    private float startDragY;
    private SpringAnimation springAnim;
    private ScrollViewListener scrollViewListener = null;
    private View view = null;

    public SpringScrollView(Context context) {
        this(context, null);
    }

    public SpringScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        springAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0);
        //刚度 默认1200 值越大回弹的速度越快
        springAnim.getSpring().setStiffness(1000.0f);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        springAnim.getSpring().setDampingRatio(2f);
    }

    //传view过来 我们的UI要做一个滑动渐变的背景，你们可以看着办
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() <= 0) {
                    //顶部下拉
                    if (startDragY == 0) {
                        startDragY = e.getRawY();
                    }
                    if (e.getRawY() - startDragY >= 0) {
                        setTranslationY((e.getRawY() - startDragY) / 3);
                        if (view != null) {
                            float alpha = (e.getRawY() - startDragY) / 3 / SizeUtils.dp2px(150);
                            if (alpha < 0.6) {
                                view.setAlpha(alpha);
                            } else {
                                view.setAlpha((float) 0.6);
                            }
                        }
                        return true;
                    } else {
                        startDragY = 0;
                        springAnim.cancel();
                        setTranslationY(0);
                    }

                } else if ((getScrollY() + getHeight()) >= getChildAt(0).getMeasuredHeight()) {
                    //底部上拉
                    if (startDragY == 0) {
                        startDragY = e.getRawY();
                    }
                    if (e.getRawY() - startDragY <= 0) {
                        setTranslationY((e.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        startDragY = 0;
                        springAnim.cancel();
                        setTranslationY(0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getTranslationY() != 0) {
                    springAnim.start();
                }
                startDragY = 0;
                break;
        }
        return super.onTouchEvent(e);
    }

    //定义一个滑动接口，监听滑动距离
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(SpringScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
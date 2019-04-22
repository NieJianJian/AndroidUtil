package njj.utils.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import njj.utils.ViewPagerScroller;

/**
 * Created by jian on 2016/10/24.
 */
public class DefViewPager extends ViewPager {

    private DefViewpagerListener mListener; // 点击事件监听
    private boolean mNoScroll = false; // true表示不滑动，false表示允许滑动
    private boolean mSmoothScroll = true; // 是否需要平稳滑动效果，true表示需要

    public DefViewPager(Context context) {
        this(context, null);
    }

    public DefViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否允许手动滑动
     *
     * @param noScroll true表示不滑动，false表示允许滑动
     */
    public void setNoScroll(boolean noScroll) {
        this.mNoScroll = noScroll;
    }

    /**
     * 设置是否需要平滑的滚动到新页面
     *
     * @param smoothScroll true表示平滑滚动，false表示立即切换，不需要滚动时间。
     */
    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    /**
     * 设置滚动切换的速度，单位ms。
     *
     * @param duration 切换时间
     */
    public void setScrollInterval(int duration) {
        ViewPagerScroller pagerScroller = new ViewPagerScroller(getContext());
        pagerScroller.setScrollDuration(duration);
        pagerScroller.initViewPagerScroll(this);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, mSmoothScroll); // false表示不需要时间滚动，去除滚动效果
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return !mNoScroll && super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !mNoScroll && super.onTouchEvent(ev);
    }

    /**************************************** start *******************************************/
    /**
     * 只是用于一般的轮播图设置，比如，每5秒切换一次轮播图，如果遇到点击事件，就取消自动轮播，
     * 松手后，继续轮播，此设置只提供两个回调方法，具体相关逻辑，自己根据需求实现。
     * 如不需要，不需要调用setListener()方法即可。
     * 如有特殊需求，子类重写dispatchTouchEvent()方法即可
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mListener != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onClear();
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onStart();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mListener.onStart();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setListener(DefViewpagerListener listener) {
        this.mListener = listener;
    }

    public interface DefViewpagerListener {
        void onClear();

        void onStart();
    }
    /**************************************** end *******************************************/
}

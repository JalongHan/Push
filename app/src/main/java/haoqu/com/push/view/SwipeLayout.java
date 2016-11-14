package haoqu.com.push.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 侧滑弹出的界面
 * Created by apple on 16/11/14.
 */

public class SwipeLayout extends FrameLayout {
    private final ViewDragHelper mDragHelper;
    private View mBackView;
    private View mFrontView;
    private int mRange;
    private int mWidth;
    private int mHeight;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //第一步,初始化ViewDragHelper

        ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            /**
             * 重写这个方法.向左滑动
             * @param child
             * @param left
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }
        };
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }


    /**
     * 传递触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //交给viewDragHelper判断是否去拦截事件
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        }catch (Exception e){
                            e.printStackTrace();
        }
        //返回true,这里表示去拦截事件
        return true;
    }

    /**
     * 当xml填充完毕的时候
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        /**
         * 后view
         */
        mBackView = getChildAt(0);


        /**
         * 前view
         */
        mFrontView = getChildAt(1);
    }

    /**
     * 在这里获取宽和高
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 高度
         */
        mHeight = mFrontView.getMeasuredHeight();
        /**
         * 宽度
         */
        mWidth = mFrontView.getMeasuredWidth();
        /**
         * 移动距离
         */
        mRange = mBackView.getMeasuredWidth();



    }

    /**
     * 摆放两个view位置
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        layoutContent(false);
    }

    private void layoutContent(boolean isOpen) {
        //摆放前view

    }
}

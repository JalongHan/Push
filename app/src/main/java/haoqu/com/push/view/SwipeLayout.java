package haoqu.com.push.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 侧滑弹出的界面
 * Created by apple on 16/11/14.
 */

public class SwipeLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;
    private View mBackView;
    private View mFrontView;
    private int mRange;
    private int mWidth;
    private int mHeight;
    private final String TAG = "SwipeLayout";

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
            //确定哪个view可划动
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

                //在这里处理旋转的逻辑拖拽的前view
                if (child == mFrontView) {
                    if (left > 0) {
                        return 0;
                    } else if (left < -mRange) {
                        return -mRange;
                    }

                }//拖拽的后view
                else if (child == mBackView) {
                    if (left > mWidth) {
                        return mWidth;
                    } else if (left < mWidth - mRange) {
                        return mWidth - mRange;
                    }

                }
                return left;
            }

            /**
             * 当view位置改变的时候
             * @param changedView 改变的view
             * @param left
             * @param top
             * @param dx x轴偏移量
             * @param dy
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //传递事件，如果是拖拽的前view
                if (changedView == mFrontView) {
                    //Offset this view's horizontal location by the specified amount of pixels.
                    //就是说我的前view向左滑了dx，那么我的后view也是左滑dx，右滑同理。
                    mBackView.offsetLeftAndRight(dx);
                } else if (changedView == mBackView) {
                    //拖拽的是后view的话，前view的处理方式一样。
                    mFrontView.offsetLeftAndRight(dx);
                }
                //兼容老版本
                invalidate();

//                dispatchSwipeEvent();


            }

            /**
             * 拖拽view释放的时候  实现以下滑动的距离和速度【判断是否打开和关闭
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (xvel == 0 && mFrontView.getLeft() < -mRange / 2.0f) {
                    open();
                } else if (xvel < 0) {
                    open();
                } else {
                    close();
                }
            }


            //关闭
            private void close() {
                close(true);
            }

            //实现以下平滑的关闭和打开
            private void close(boolean isSmooth) {
                int finalLeft = 0;
                if (isSmooth) {
                    //开始动画 如果返回true表示没有完成动画
                    if (mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)) {
                        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);

                    }
                } else {
                    layoutContent(false);
                }
            }

            //打开
            private void open() {
                open(true);
            }

            private void open(boolean isSmooth) {
                int finalLeft = -mRange;
                if (isSmooth) {
                    //开始动画
                    if (mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)) {
                        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
                    }
                } else {
                    layoutContent(true);
                }

            }


        };
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }


    /**
     * 持续动画
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //这个是固定
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
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
        } catch (Exception e) {
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
        mBackView = getChildAt(1);
        Log.i(TAG, "onFinishInflate:mBackView "+mBackView.getId());


        /**
         * 前view
         */
        mFrontView = getChildAt(0);
        Log.i(TAG, "onFinishInflate:mFrontView "+mFrontView.getId());
    }

    /**
     * 在这里获取宽和高
     *
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
        Log.i(TAG, "onSizeChanged: "+mHeight);
        /**
         * 宽度
         */
        mWidth = mFrontView.getMeasuredWidth();
        Log.i(TAG, "onSizeChanged: "+mWidth);
        /**
         * 移动距离
         */
        mRange = mBackView.getMeasuredWidth();
        Log.i(TAG, "onSizeChanged: "+mRange);

    }

    /**
     * 摆放两个view位置
     *
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
        Rect frontRect = computeFrontViewRect(isOpen);
        mFrontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
        //摆放后view
        Rect backRect = computeBackViewRect(frontRect);
        mBackView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);
        //前置前view
        bringChildToFront(mBackView);

    }

    /**
     * 可以把前view相当于一个矩形
     *
     * @param frontRect
     * @return
     */
    private Rect computeBackViewRect(Rect frontRect) {
        int left = frontRect.right;
        return new Rect(left, 0, left + mRange, 0 + mHeight);
    }

    private Rect computeFrontViewRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = -mRange;
        }
        return new Rect(left, 0, left + mWidth, 0 + mHeight);
    }


    /**
     * 默认状态是关闭
     * 在这里加上一些回调，以方便外部使用的时候可以回调
     */

    /**
     * 定义三种状态
     */
    public enum Status {
        Close, Open, Draging;
    }

    private Status status = Status.Close;
    private OnSwipeLayoutListener swipeLayoutListener;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OnSwipeLayoutListener getSwipeLayoutListener() {
        return swipeLayoutListener;
    }

    public void setSwipeLayoutListener(OnSwipeLayoutListener swipeLayoutListener) {
        this.swipeLayoutListener = swipeLayoutListener;
    }

    /**
     * 定义回调接口 这个在我们
     */
    public interface OnSwipeLayoutListener {
        /**
         * 关闭
         *
         * @param mSwipeLayout
         */
        void onClose(SwipeLayout mSwipeLayout);

        /**
         * 打开
         *
         * @param mSwipeLayout
         */
        void onOpen(SwipeLayout mSwipeLayout);

        /**
         * 绘制
         *
         * @param mSwipeLayout
         */
        void onDraging(SwipeLayout mSwipeLayout);

        /**
         * 要去关闭
         */
        void onStartClose(SwipeLayout mSwipeLayout);

        /**
         * 要去开启
         */
        void onStartOpen(SwipeLayout mSwipeLayout);
    }

    /**
     * 像 是判断状态的方法
     */
    private void dispatchSwipeEvent() {
        //判断是否为空
        if (swipeLayoutListener != null) {
            swipeLayoutListener.onDraging(this);
        }

        //记录上一次的状态
        Status preStatus = status;
        //更新当前状态
        status = updateStatus();
        if (preStatus != status && swipeLayoutListener != null) {
            if (status == Status.Close) {
                swipeLayoutListener.onClose(this);
            } else if (status == Status.Open) {
                swipeLayoutListener.onOpen(this);
            } else if (status == Status.Draging) {
                swipeLayoutListener.onDraging(this);
            }
        }
    }

    /**
     * 更新状态
     *
     * @return
     */
    private Status updateStatus() {
        //得到view的左边位置
        int left = mFrontView.getLeft();
        if (left == 0) {
            //如果位置是0,就是关闭状态
            return Status.Close;
        } else if (left == -mRange) {
            //如果左侧边距是后view的宽度负值,状态为开
            return Status.Open;
        }
        return Status.Draging;
    }


}

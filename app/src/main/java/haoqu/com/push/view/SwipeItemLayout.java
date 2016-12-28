package haoqu.com.push.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by apple on 16/12/26.
 */

public class SwipeItemLayout extends FrameLayout {
    private final ViewDragHelper dragHelper;
    private View menu;
    private View content;
    private boolean isOpen;
    private int currentState;
    private final String TAG = "SwipeItemLayout";

    public SwipeItemLayout(Context context) {
        this(context, null);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dragHelper = ViewDragHelper.create(this, rightCallback);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menu = getChildAt(0);
        content = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }


    private ViewDragHelper.Callback rightCallback = new ViewDragHelper.Callback() {


        //触摸到view的时候就会回调这个方法.return true表示抓取这个view

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return content == child;
        }

        //左右滑动的
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left > 0 ? 0 : left < -menu.getWidth() ? -menu.getWidth() : left;
        }

        //拖拽view释放的时候  实现以下滑动的距离和速度【判断是否打开和关闭
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //x轴移动速度大于菜单一半,或者已经移动到菜单的一半之后,展开菜单.
            if (isOpen) {
                Log.i(TAG, "onViewReleased: "+"执行了"+isOpen);
                if (xvel > menu.getWidth() || -content.getLeft() < menu.getWidth() / 2) {

                    close();
                } else {
                    open();
                }
            } else {
                Log.i(TAG, "onViewReleased: "+"执行了"+isOpen);
                if (-xvel > menu.getWidth() || -content.getLeft() > menu.getWidth() / 2) {
                    open();
                }else {
                    close();
                }

            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            currentState = state;
        }
    };


    public void close() {
        dragHelper.smoothSlideViewTo(content, 0, 0);
        isOpen = false;
        Log.i(TAG, "close: "+isOpen);
        invalidate();
    }


    public void open() {
        dragHelper.smoothSlideViewTo(content, -menu.getWidth(), 0);
        isOpen = true;
        Log.i(TAG, "open: "+isOpen);
        invalidate();
    }




    public boolean isOpen() {
        return isOpen;
    }

    public int getState(){
     return currentState;
    }


    private Rect outRect = new Rect();

    public Rect getMenuRect() {
        menu.getHitRect(outRect);
        return outRect;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        content.setOnClickListener(l);
    }
}

package haoqu.com.push.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import haoqu.com.push.status.SwipeLayoutStatus;

/**
 * Created by apple on 16/12/26.
 */

public class SwpieRecyclerView extends RecyclerView {


    private boolean isChildHandle;
    private float startX;
    private float startY;
    private float distanceX;
    private float distanceY;
    private View touchView;
    private int touchSlop;

    public SwpieRecyclerView(Context context) {
        this(context, null);
    }

    public SwpieRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwpieRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isChildHandle = false;
                //记录下手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                distanceX = 0;
                distanceY = 0;
                //获取按下的那个view
                int position = pointToPosition((int) startX, (int) startY);
                touchView = getChildAt(position);

                if (hasChildOpen()) {
                    //如果触摸的不是打开的那个View,关闭所有View,并且拦截所有事件.
                    if (touchView != null && touchView instanceof SwipeLayout) {
                        isChildHandle = true;//将事件交给child
                    } else {
                        closeAllSwipeItem();
                        return false;
                    }
                }

                break;
            //禁用多点触控
            case MotionEvent.ACTION_POINTER_DOWN:
                return false;


        }

        return super.dispatchTouchEvent(ev);
    }

    //处理侧划和菜单冲突


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //如果竖向滑动,拦截,否则不拦截.
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                //获取当前手指位置
                float endY = e.getY();
                float endX = e.getX();
                //Math.abs取绝对值
                distanceX = Math.abs(endX - startX);

                //如果child已经持有事件,那么 不拦截它的事件,直接return false;
                if (isChildHandle) {
                    return false;
                }

                //如果x轴位移大于Y轴位移,那么将事件交给child处理
                if (distanceX > touchSlop && distanceX > distanceY) {
                    isChildHandle = true;
                    return false;
                }

                break;

            case MotionEvent.ACTION_UP:
                //state != 1 没有滑动过,关闭打开的菜单
                if(touchView != null && touchView instanceof SwipeLayout){
                    SwipeLayout swipeLayout = (SwipeLayout) this.touchView;
                    if(swipeLayout.getStatus().equals(SwipeLayoutStatus.Open)){
                        if(distanceX<touchSlop && distanceY <touchSlop){
                            swipeLayout.close(true);
                        }




                    }



                }

        }


        return super.onInterceptTouchEvent(e);
    }

    /**
     * 关闭所有打开的item
     */
    private void closeAllSwipeItem() {
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child instanceof SwipeLayout) {
                ((SwipeLayout) child).close(true);
            }
        }

    }

    /**
     * 看这个子view是否是打开状态
     *
     * @return
     */
    private boolean hasChildOpen() {
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child instanceof SwipeLayout) {
                if (((SwipeLayout) child).getStatus().equals(SwipeLayoutStatus.Open)) {
                    return true;
                }
            }
        }


        return false;
    }

    /**
     * 当前手指位置的position(屏幕上显示的第一个Item为0)
     *
     * @param startX
     * @param startY
     * @return
     */

    private Rect touchFrame;

    private int pointToPosition(int x, int y) {
        Rect frame = touchFrame;
        if (frame == null) {
            touchFrame = new Rect();
            frame = touchFrame;
        }
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }

            }
        }


        return -1;
    }
}

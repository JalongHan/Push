package haoqu.com.push.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by apple on 16/12/26.
 */

public class SwipeItemLayout extends FrameLayout{
    private final ViewDragHelper dragHelper;
    private View menu;
    private View content;
    private boolean isOpen;

    public SwipeItemLayout(Context context) {
        this(context,null);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dragHelper = ViewDragHelper.create(this,rightCallback);
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
            return left>0 ? 0 : left < -menu.getWidth() ? - menu.getWidth():left;
        }
        //拖拽view释放的时候  实现以下滑动的距离和速度【判断是否打开和关闭
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //x轴移动速度大于菜单一半,或者已经移动到菜单的一半之后,展开菜单.
            if(isOpen){

            }
        }
    };





}

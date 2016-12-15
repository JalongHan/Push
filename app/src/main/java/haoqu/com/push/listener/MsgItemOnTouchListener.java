package haoqu.com.push.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * Touch接口
 * Created by apple on 16/12/15.
 */

public interface MsgItemOnTouchListener {
    void ItemOnTouch(View v,int position,MotionEvent event);
}

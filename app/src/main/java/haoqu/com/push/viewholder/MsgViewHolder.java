package haoqu.com.push.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import haoqu.com.push.R;
import haoqu.com.push.listener.MsgItemClickListener;
import haoqu.com.push.listener.MsgItemOnTouchListener;
import haoqu.com.push.view.SwipeLayout;

/**
 * 消息列表的viewholder
 * Created by apple on 16/12/13.
 */

public class MsgViewHolder extends ViewHolder implements View.OnClickListener ,View.OnTouchListener{

    private final String TAG = "MsgViewHolder";
    private final ImageView mHeadIcon;
    private final TextView mTitle;
    private final TextView mContentTime;
    private final ImageView mPoint;
    private final TextView mText;
    private final RelativeLayout mContent;
    private final Button mMarkedAsRead;
    private final Button mDeleteMsg;
    private final LinearLayout mSwipeView;
    private final SwipeLayout mSwipeLayout;


    private float DownX;
    private float DownY;
    private float moveX;
    private float moveY;
    private long currentMS;

    private MsgItemClickListener mMsgItemClickListener;
    private MsgItemOnTouchListener mMsgItemOnTouchListener;

    public MsgViewHolder(View itemView, MsgItemClickListener msgItemClickListener) {
        super(itemView);
        mHeadIcon = (ImageView) itemView.findViewById(R.id.headIcon);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mContentTime = (TextView) itemView.findViewById(R.id.Content_time);
        mPoint = (ImageView) itemView.findViewById(R.id.point);
        mText = (TextView) itemView.findViewById(R.id.text);
        mContent = (RelativeLayout) itemView.findViewById(R.id.Content);
        mMarkedAsRead = (Button) itemView.findViewById(R.id.markedAsRead);
        mDeleteMsg = (Button) itemView.findViewById(R.id.deleteMsg);
        mSwipeView = (LinearLayout) itemView.findViewById(R.id.swipeView);
        mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.SwipeLayout);
        this.mMsgItemClickListener = msgItemClickListener;

        mDeleteMsg.setOnClickListener(this);
        mMarkedAsRead.setOnClickListener(this);
        itemView.setOnClickListener(this);
//        mSwipeLayout.setOnTouchListener(this);
//        mSwipeLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i(TAG, "onTouch: itemView");
//
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(TAG, "onTouchEvent: down");
//                        DownX = event.getX();//float DownX
//                        DownY = event.getY();//float DownY
//                        moveX = 0;
//                        moveY = 0;
//                        currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
//
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(TAG, "onTouchEvent: move");
//                        moveX += Math.abs(event.getX() - DownX);//X轴距离
//                        moveY += Math.abs(event.getY() - DownY);//y轴距离
//                        DownX = event.getX();
//                        DownY = event.getY();
////                return false;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(TAG, "onTouchEvent: up");
//                        long moveTime = System.currentTimeMillis() - currentMS;//移动时间
//                        //判断是否继续传递信号
//                        if (moveTime < 200 && (moveX < 20 || moveY < 20)) {
//                            Log.i(TAG, "onTouchEvent: return false");
//
////                            return true; //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
//
//                        }
//
//                        break;
//                }
//
//                return false;
//            }
//        });




    }

    public RelativeLayout getmContent() {
        return mContent;
    }

    public TextView getmContentTime() {
        return mContentTime;
    }

    public Button getmDeleteMsg() {
        return mDeleteMsg;
    }

    public ImageView getmHeadIcon() {
        return mHeadIcon;
    }

    public Button getmMarkedAsRead() {
        return mMarkedAsRead;
    }

    public ImageView getmPoint() {
        return mPoint;
    }

    public LinearLayout getmSwipeView() {
        return mSwipeView;
    }

    public TextView getmText() {
        return mText;
    }

    public TextView getmTitle() {
        return mTitle;
    }


    @Override
    public void onClick(View v) {
        if (null != mMsgItemClickListener) {
            mMsgItemClickListener.onItemClick(v, getPosition());
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(null != mMsgItemOnTouchListener){
            mMsgItemOnTouchListener.ItemOnTouch(v,getPosition(),event);
        }
        return false;
    }
}

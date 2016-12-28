package haoqu.com.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import haoqu.com.push.JSONModel.MsgBean;
import haoqu.com.push.R;
import haoqu.com.push.viewholder.MsgViewHolder;

/**
 * 展示消息页的adapter
 * Created by apple on 16/11/11.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MsgBean> mMsgBean;
    private String TAG = "MessageAdapter";

//    private MsgItemClickListener mMsgItemClickListener;
//    private MsgItemOnTouchListener mMsgItemOnTouchListener;





    public MessageAdapter(List<MsgBean> mMsgBean, Context context) {
        this.mMsgBean = mMsgBean;
        this.context = context;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);


        return new MsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String text = mMsgBean.get(position).getContent();
        Log.i(TAG, "onBindViewHolder: "+text);
        ((MsgViewHolder) holder).getmText().setText(text);

    }

    @Override
    public int getItemCount() {
        return mMsgBean.size();
    }

    /**
     * 设置item的监听
     * @param listener
     */
//    public void setOnItemClickListener(MsgItemClickListener listener){
//        this.mMsgItemClickListener = listener;
//    }

//    public void setItemOnTouchListener(MsgItemOnTouchListener listener){
//        this.mMsgItemOnTouchListener = listener;
//    }

}

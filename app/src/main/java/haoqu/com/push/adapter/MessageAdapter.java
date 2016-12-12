package haoqu.com.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import haoqu.com.push.R;

/**
 * 展示消息页的adapter
 * Created by apple on 16/11/11.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> actionMessageBean;
    private String TAG = "MessageAdapter";
    private MyItemClickListener mItemClickListener;


    /**
     * 接口暴露给外面调用点击事件和item点击事件
     */
    public interface MyItemClickListener {
//        void click(View v);
        void OnItemClick(View v, int position);
    }



    public MessageAdapter(List<String> actionMessageBean, Context context) {
        this.actionMessageBean = actionMessageBean;
        this.context = context;
    }



    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{


        private TextView tvMsg;
        private ImageView ivPoint;

        public viewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            tvMsg = (TextView) itemView.findViewById(R.id.text);
            ivPoint = (ImageView) itemView.findViewById(R.id.point);
            mItemClickListener = myItemClickListener;
            itemView.setOnClickListener(this);


        }


        public ImageView getIvDot() {
            return ivPoint;
        }

        public TextView getTvMsg() {
            return tvMsg;
        }

        /**
         * 点击监听
         * @param v
         */
        @Override
        public void onClick(View v) {
            mItemClickListener.OnItemClick(v,getPosition());
        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);


        return new viewHolder(view,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String text = actionMessageBean.get(position);
        Log.i(TAG, "onBindViewHolder: "+text);
        ((viewHolder) holder).getTvMsg().setText(text);

    }

    @Override
    public int getItemCount() {
        return actionMessageBean.size();
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }




}

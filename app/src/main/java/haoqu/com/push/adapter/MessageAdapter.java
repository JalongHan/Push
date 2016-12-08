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

    public MessageAdapter(List<String> actionMessageBean, Context context) {
        this.actionMessageBean = actionMessageBean;
        this.context = context;
    }


    public class viewHolder extends RecyclerView.ViewHolder {


        private TextView tvMsg;
        private ImageView ivDot;

        public viewHolder(View itemView) {
            super(itemView);
            tvMsg = (TextView) itemView.findViewById(R.id.text);
            ivDot = (ImageView) itemView.findViewById(R.id.dot);

        }


        public ImageView getIvDot() {
            return ivDot;
        }

        public TextView getTvMsg() {
            return tvMsg;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);


        return new viewHolder(view);
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
}

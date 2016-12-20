package haoqu.com.push.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import haoqu.com.push.Consts;
import haoqu.com.push.JSONModel.MsgBean;
import haoqu.com.push.R;
import haoqu.com.push.adapter.MessageAdapter;
import haoqu.com.push.listener.MsgItemClickListener;
import haoqu.com.push.listener.MsgItemOnTouchListener;
import haoqu.com.push.status.SwipeLayoutStatus;
import haoqu.com.push.view.SwipeLayout;
import haoqu.com.push.viewholder.MsgViewHolder;

public class MainActivity extends AppCompatActivity implements MsgItemClickListener, MsgItemOnTouchListener {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private FloatingActionButton fab;
    //消息广播
    private MsgReceiver mMsgReceiver;


    private List<MsgBean> mMsgList;
    private RecyclerView mReceyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter mMessageAdapter;

    private float DownX;
    private float DownY;
    private float moveX;
    private float moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //先从数据库取一下数据.
        mMsgList = SQLite.select().from(MsgBean.class).queryList();
        initViews();
        setListeners();

        initReceiver();


    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter(Consts.EXTRA_ALERT);
        mMsgReceiver = new MsgReceiver();
        registerReceiver(mMsgReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMsgReceiver);
    }

    /**
     * 设置监听
     */
    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                VolleyGetData();
                //开启服务去后台,一直获取数据.
//                startService(new Intent(MainActivity.this, HeartBeatService.class));

                startActivity(new Intent(MainActivity.this, ContentActivity.class));


            }


        });

        mMessageAdapter.setOnItemClickListener(this);
        mMessageAdapter.setItemOnTouchListener(this);

    }

    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.more);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mReceyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        mReceyclerView.setLayoutManager(linearLayoutManager);
        mMessageAdapter = new MessageAdapter(mMsgList, this);
        mReceyclerView.setAdapter(mMessageAdapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG, "onItemClick: " + position);
    }

    @Override
    public void ItemOnTouch(View v, int position, MotionEvent event) {





        Log.i(TAG, "onTouch: itemView");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: down");
                DownX = event.getX();//float DownX
                DownY = event.getY();//float DownY
                moveX = 0;
                moveY = 0;
//                currentMS = System.currentTimeMillis();//long currentMS     获取系统时间


                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: move");
                moveX += Math.abs(event.getX() - DownX);//X轴距离
                moveY += Math.abs(event.getY() - DownY);//y轴距离
                DownX = event.getX();
                DownY = event.getY();
//                return false;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: up");
//                long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                //判断是否继续传递信号
                if (moveX < 20 && moveY < 20) {
                    Log.i(TAG, "onTouchEvent: return false");

                    MsgViewHolder msgViewHolder = (MsgViewHolder) mReceyclerView.getChildViewHolder(mReceyclerView.getChildAt(position));

                    SwipeLayout swipeLayout = msgViewHolder.getmSwipeLayout();

                    Log.i(TAG, "ItemOnTouch: " + swipeLayout.getStatus().toString());


                    if (SwipeLayoutStatus.Open.equals(swipeLayout.getStatus())) {
                        swipeLayout.close(true);
                    } else {
                        startActivity(new Intent(MainActivity.this, ContentActivity.class));
                    }


//

                }

                break;
        }


    }


    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String alert = intent.getStringExtra(Consts.KEY_MESSAGE);
            MsgBean msgBean = new MsgBean();
            msgBean.setContent(alert);
            mMsgList.add(0, msgBean);
            mMessageAdapter.notifyDataSetChanged();
            saveMsg(msgBean);
            Log.i(TAG, "onReceive: " + alert);
        }

    }

    /**
     * 保存到数据库
     *
     * @param msgBean
     */
    private void saveMsg(MsgBean msgBean) {

        msgBean.save();

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

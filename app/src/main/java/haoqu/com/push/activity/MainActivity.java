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
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import haoqu.com.push.Consts;
import haoqu.com.push.JSONModel.ActionMessageBean;
import haoqu.com.push.R;
import haoqu.com.push.adapter.MessageAdapter;

public class MainActivity extends AppCompatActivity implements MessageAdapter.CallBack{

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private FloatingActionButton fab;
    //消息广播
    private MsgReceiver mMsgReceiver;

    private List<ActionMessageBean> mListActionMsg;

    private List<String> mStringList;
    private RecyclerView mReceyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStringList = new ArrayList<>();
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

                startActivity(new Intent(MainActivity.this,ContentActivity.class));


            }


        });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.more);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mReceyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        mReceyclerView.setLayoutManager(linearLayoutManager);
        mMessageAdapter = new MessageAdapter(mStringList,this);
        mReceyclerView.setAdapter(mMessageAdapter);


    }

    @Override
    public void click(View v) {

    }


    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String alert = intent.getStringExtra(Consts.KEY_MESSAGE);

            mStringList.add(0,alert);
            mMessageAdapter.notifyDataSetChanged();

            Log.i(TAG, "onReceive: " + alert);
        }
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

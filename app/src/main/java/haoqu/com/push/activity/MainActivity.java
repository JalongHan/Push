package haoqu.com.push.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import haoqu.com.push.Consts;
import haoqu.com.push.R;
import haoqu.com.push.service.HeartBeatService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private FloatingActionButton fab;
    //消息广播
    private MsgReceiver mMsgReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        setListeners();

        initReceiver();


    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter(Consts.Msg);
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
                startService(new Intent(MainActivity.this, HeartBeatService.class));


            }


        });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(Consts.Msg);
            Log.i(TAG, "onReceive: " + result);
        }
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

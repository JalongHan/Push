package haoqu.com.push.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.lang.reflect.Field;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import haoqu.com.push.Consts;
import haoqu.com.push.JSONModel.MsgBean;
import haoqu.com.push.JSONModel.MsgBean_Table;
import haoqu.com.push.R;
import haoqu.com.push.adapter.MessageAdapter;
import haoqu.com.push.listener.MsgItemClickListener;
import haoqu.com.push.viewholder.MsgViewHolder;

public class MainActivity extends AppCompatActivity implements MsgItemClickListener {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;

    //消息广播
    private MsgReceiver mMsgReceiver;


    private List<MsgBean> mMsgList;
    private RecyclerView mReceyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter mMessageAdapter;
    private ImageButton mContentSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //先从数据库取一下数据.
        mMsgList = SQLite.select().from(MsgBean.class).orderBy(MsgBean_Table.id, false).queryList();
        Log.i(TAG, "onCreate: " + mMsgList.size());


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

        mMessageAdapter.setOnItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Navigation");
            }
        });
        mContentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ContentSearch");
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.more);

        mContentSearch = (ImageButton) findViewById(R.id.Content_search);
        //通过反射拿到mNavButtonView的高度,赋给右边的搜索按钮
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Class tb = toolbar.getClass();
                    Field navButton = tb.getDeclaredField("mNavButtonView");
                    navButton.setAccessible(true);

                    ImageButton a = (ImageButton) navButton.get(toolbar);
                    Log.i(TAG, "run: " + a.getMeasuredHeight());

                    mContentSearch.setMinimumHeight(a.getMeasuredHeight());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        });

        //recyclerview先显示一个空白的
        mReceyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReceyclerView.setLayoutManager(linearLayoutManager);
        mMessageAdapter = new MessageAdapter(mMsgList, this);
        mReceyclerView.setAdapter(mMessageAdapter);


    }

    @Override
    public void onItemClick(View view, final int position) {
        Log.i(TAG, "onItemClick: " + position);
        switch (view.getId()) {
            case R.id.Content:
                startActivity(new Intent(MainActivity.this, ContentActivity.class));
                break;
            //点击删除时
            case R.id.deleteMsg:

//                mMsgList.remove(position);
                mMsgList.get(position).delete();
                mMsgList.remove(position);
                mMessageAdapter.notifyItemRemoved(position);

                break;
            //点击标为已读时
            case R.id.markedAsRead:
                MsgBean mMsg = mMsgList.get(position);
                mMsg.setMark(false);
//                mMessageAdapter.notifyDataSetChanged();
//                mMessageAdapter.notifyItemChanged(position);

                MsgViewHolder msgViewHolder = (MsgViewHolder) mReceyclerView.getChildViewHolder((View) view.getParent().getParent());
                msgViewHolder.getmPoint().setVisibility(View.GONE);

//
                mMsg.save();
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
//            mMessageAdapter.notifyDataSetChanged();
            mMessageAdapter.notifyItemRangeInserted(0, 1);
//            mReceyclerView.smoothScrollToPosition(0);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

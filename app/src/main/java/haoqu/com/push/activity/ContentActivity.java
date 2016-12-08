package haoqu.com.push.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import haoqu.com.push.R;

public class ContentActivity extends AppCompatActivity {

    private ImageView mMainIvPersonstore;
    private TextView mMainTvPersonstore;
    private RelativeLayout mMainRlPersonstore;
    private ImageView mMainIvFriends;
    private TextView mMainTvFriends;
    private RelativeLayout mMainRlFriends;
    private ImageView mMainIvStore;
    private TextView mMainTvStore;
    private RelativeLayout mMainRlStore;
    private LinearLayout mBottomButton;
    private ImageView mContentImgTitle;
    private TextView mContentTitle;
    private TextView mContentWriter;
    private TextView mContentTime;
    private TextView mContentContent;
    private RelativeLayout mActivityContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
    }

    private void initView() {
        mMainIvPersonstore = (ImageView) findViewById(R.id.main_iv_personstore);
        mMainTvPersonstore = (TextView) findViewById(R.id.main_tv_personstore);
        mMainRlPersonstore = (RelativeLayout) findViewById(R.id.main_rl_personstore);
        mMainIvFriends = (ImageView) findViewById(R.id.main_iv_friends);
        mMainTvFriends = (TextView) findViewById(R.id.main_tv_friends);
        mMainRlFriends = (RelativeLayout) findViewById(R.id.main_rl_friends);
        mMainIvStore = (ImageView) findViewById(R.id.main_iv_store);
        mMainTvStore = (TextView) findViewById(R.id.main_tv_store);
        mMainRlStore = (RelativeLayout) findViewById(R.id.main_rl_store);
        mBottomButton = (LinearLayout) findViewById(R.id.bottom_button);
        mContentImgTitle = (ImageView) findViewById(R.id.Content_imgTitle);
        mContentTitle = (TextView) findViewById(R.id.Content_Title);
        mContentWriter = (TextView) findViewById(R.id.Content_writer);
        mContentTime = (TextView) findViewById(R.id.Content_time);
        mContentContent = (TextView) findViewById(R.id.Content_content);
        //使textview可滑动
        mContentContent.setMovementMethod(new ScrollingMovementMethod());
        mActivityContent = (RelativeLayout) findViewById(R.id.activity_content);
    }
}

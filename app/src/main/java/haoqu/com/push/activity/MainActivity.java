package haoqu.com.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;

import haoqu.com.push.Consts;
import haoqu.com.push.R;
import haoqu.com.push.service.HeartBeatService;
import haoqu.com.push.tools.VolleyListenerInterface;
import haoqu.com.push.tools.VolleyRequestTool;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_dialog_email);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
     * 获取数据
     */
    private void VolleyGetData() {
        VolleyRequestTool.RequestPost(
                MainActivity.this,
                Consts.baseUrl + Consts.appIndex + Consts.weid,
                Consts.appIndex,
                null,
                new VolleyListenerInterface(MainActivity.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(String result) {
                        Log.i(TAG, "onMySuccess: ");
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        Log.i(TAG, "onMyError: ");
                    }
                });
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

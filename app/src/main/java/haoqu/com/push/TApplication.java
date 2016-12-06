package haoqu.com.push;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by apple on 16/9/14.
 */
public class TApplication extends Application {
    //vooley的requestQueue
    private static RequestQueue HttpQueue;
    //uuid
    public static String UUID;
    /*授权令牌*/
    public static String token = "";
    /*uid*/
    public static String uid = "";
    /*头像网址*/
    public static String avatar = "";
    //用户的昵称
    public static String nickname = "";
    //用户的手机号
    public static String mobile = "";
    //*tapplication全局用来发广播
    public static TApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化jPush
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush


         /* Volley配置 */
        // 建立Volley的Http请求队列
        HttpQueue = Volley.newRequestQueue(getApplicationContext());
        //设备的uuid
        UUID = getMyUUID();

        instance = this;

    }


    public static RequestQueue getHttpQueue() {
        return HttpQueue;
    }


    /**
     * 获得UUID唯一设备识别码
     *
     * @return
     */
    private String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;
    }

    /**
     * 获得sharedpreferences保存的uid token avatar nickname mobile
     */
    private void getUidToken(){
        SharedPreferences sp = getSharedPreferences("login",
                Context.MODE_PRIVATE);
			/*如果有存过就取出值来直接登陆,取不到值说明没登陆过，先检查网络*/
        Log.i("boolean", String.valueOf(sp.contains("phonekey")));
        if (sp.contains("phonekey")) {
            TApplication.uid = sp.getString("uid", "");
            TApplication.token = sp.getString("token", "");
            TApplication.avatar = sp.getString("avatar","");
            TApplication.nickname = sp.getString("nickname","");
            TApplication.mobile = sp.getString("mobile","");
        }
    }
}

package haoqu.com.push.datebase;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 推送消息数据库类
 * Created by apple on 16/12/17.
 */
@Database(name = MsgDateBase.Name,version = MsgDateBase.Version)
public class MsgDateBase {
    static final String Name = "MsgDateBase";
    static final int Version = 1;
}

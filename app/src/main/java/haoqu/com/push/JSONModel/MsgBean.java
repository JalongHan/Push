package haoqu.com.push.JSONModel;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import haoqu.com.push.datebase.MsgDateBase;

/**
 * Created by apple on 16/12/17.
 */
@Table(database = MsgDateBase.class)
public class MsgBean extends BaseModel {

    @PrimaryKey(autoincrement = true)
    Long id;
    @Column
    String content;
    //是否未读,当值为空时,默认给值1代表true,表示未读状态
    @Column(defaultValue = "1")
    Boolean mark;


    public MsgBean() {
    }

    public MsgBean(String content, Boolean mark) {
        this.content = content;
        this.mark = mark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }
}

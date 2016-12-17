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
    @Column
    Boolean mark;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }
}

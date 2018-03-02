package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class Inform_User implements Serializable {
    private Integer inform_user_id;

    private Integer inform_id;

    private String detail;

    private String if_read;

    private Integer class_id;

    private Integer user_id;

    private Date create_date_time;

    public Integer getInform_user_id() {
        return inform_user_id;
    }

    public void setInform_user_id(Integer inform_user_id) {
        this.inform_user_id = inform_user_id;
    }

    public Integer getInform_id() {
        return inform_id;
    }

    public void setInform_id(Integer inform_id) {
        this.inform_id = inform_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIf_read() {
        return if_read;
    }

    public void setIf_read(String if_read) {
        this.if_read = if_read;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_date_time() {
        return create_date_time;
    }

    public void setCreate_date_time(Date create_date_time) {
        this.create_date_time = create_date_time;
    }

    public Date getModify_date_time() {
        return modify_date_time;
    }

    public void setModify_date_time(Date modify_date_time) {
        this.modify_date_time = modify_date_time;
    }

    private Date modify_date_time;
}

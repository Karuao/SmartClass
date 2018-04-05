package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by asus on 2018/4/5.
 */

public class ClassUserExp implements Serializable {
    private Integer class_user_exp_id;

    private Integer class_id;

    private Integer user_id;

    private Integer exp;

    private String detail;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getClass_user_exp_id() {
        return class_user_exp_id;
    }

    public void setClass_user_exp_id(Integer class_user_exp_id) {
        this.class_user_exp_id = class_user_exp_id;
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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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
}
package team.qdu.model;

/**
 * Created by asus on 2018/3/7.
 */

import java.io.Serializable;
import java.util.Date;

public class ClassUser implements Serializable {
    private Integer class_user_id;

    private Integer class_id;

    private Integer user_id;

    private String title;

    private String if_in_class;

    private String if_new_material;

    private String if_new_homework;

    private String if_new_class_thing;

    private Integer unread_information_num;

    private Integer exp;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getClass_user_id() {
        return class_user_id;
    }

    public void setClass_user_id(Integer class_user_id) {
        this.class_user_id = class_user_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIf_in_class() {
        return if_in_class;
    }

    public void setIf_in_class(String if_in_class) {
        this.if_in_class = if_in_class == null ? null : if_in_class.trim();
    }

    public String getIf_new_material() {
        return if_new_material;
    }

    public void setIf_new_material(String if_new_material) {
        this.if_new_material = if_new_material;
    }

    public String getIf_new_homework() {
        return if_new_homework;
    }

    public void setIf_new_homework(String if_new_homework) {
        this.if_new_homework = if_new_homework;
    }

    public String getIf_new_class_thing() {
        return if_new_class_thing;
    }

    public void setIf_new_class_thing(String if_new_class_thing) {
        this.if_new_class_thing = if_new_class_thing;
    }

    public Integer getUnread_information_num() {
        return unread_information_num;
    }

    public void setUnread_information_num(Integer unread_information_num) {
        this.unread_information_num = unread_information_num;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
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


package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class HomeworkAnswer implements Serializable {
    private Integer homework_answer_id;

    private Integer homework_id;

    private Integer user_id;

    private String url;

    private String if_submit;

    private String remark_url;

    private Integer exp;

    private Integer class_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getHomework_answer_id() {
        return homework_answer_id;
    }

    public void setHomework_answer_id(Integer homework_answer_id) {
        this.homework_answer_id = homework_answer_id;
    }

    public Integer getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(Integer homework_id) {
        this.homework_id = homework_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIf_submit() {
        return if_submit;
    }

    public void setIf_submit(String if_submit) {
        this.if_submit = if_submit == null ? null : if_submit.trim();
    }

    public String getRemark_url() {
        return remark_url;
    }

    public void setRemark_url(String remark_url) {
        this.remark_url = remark_url == null ? null : remark_url.trim();
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
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
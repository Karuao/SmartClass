package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class Homework implements Serializable {
    private Integer homework_id;

    private String name;

    private String group;

    private String url;

    private Integer submit_num;

    private String if_repository;

    private String homework_status;

    private Date deadline;

    private Byte exp;

    private Integer class_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(Integer homework_id) {
        this.homework_id = homework_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSubmit_num() {
        return submit_num;
    }

    public void setSubmit_num(Integer submit_num) {
        this.submit_num = submit_num;
    }

    public String getIf_repository() {
        return if_repository;
    }

    public void setIf_repository(String if_repository) {
        this.if_repository = if_repository == null ? null : if_repository.trim();
    }

    public String getHomework_status() {
        return homework_status;
    }

    public void setHomework_status(String homework_status) {
        this.homework_status = homework_status == null ? null : homework_status.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Byte getExp() {
        return exp;
    }

    public void setExp(Byte exp) {
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
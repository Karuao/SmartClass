package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class Inform implements Serializable {
    private Integer inform_id;

    private String detail;

    private Integer read_num;

    private Integer class_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

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
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getRead_num() {
        return read_num;
    }

    public void setRead_num(Integer read_num) {
        this.read_num = read_num;
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
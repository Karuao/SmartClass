package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by asus on 2018/3/16.
 */

public class Attendance implements Serializable {
    private Integer attendance_id;

    private String if_open;

    private Integer attendance_stu_count;

    private Integer stu_num;

    private Integer class_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(Integer attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getIf_open() {
        return if_open;
    }

    public void setIf_open(String if_open) {
        this.if_open = if_open == null ? null : if_open.trim();
    }

    public Integer getAttendance_stu_count() {
        return attendance_stu_count;
    }

    public void setAttendance_stu_count(Integer attendance_stu_count) {
        this.attendance_stu_count = attendance_stu_count;
    }

    public Integer getStu_num() {
        return stu_num;
    }

    public void setStu_num(Integer stu_num) {
        this.stu_num = stu_num;
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

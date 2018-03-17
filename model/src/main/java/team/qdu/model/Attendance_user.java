package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by asus on 2018/3/16.
 */

public class Attendance_user  implements Serializable {
    private Integer attendance_user_id;

    private String attendance_status;

    private Integer user_id;

    private Integer attendance_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getAttendance_user_id() {
        return attendance_user_id;
    }

    public void setAttendance_user_id(Integer attendance_user_id) {
        this.attendance_user_id = attendance_user_id;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status == null ? null : attendance_status.trim();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(Integer attendance_id) {
        this.attendance_id = attendance_id;
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

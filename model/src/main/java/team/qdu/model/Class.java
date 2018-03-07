package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class Class implements Serializable {
    private Integer class_id;

    private String name;

    private String avatar;

    private String course;

    private String university;

    private String department;

    private String detail;

    private String exam_shedule;

    private Short population;

    private String if_allow_to_join;

    private Integer user_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course == null ? null : course.trim();
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university == null ? null : university.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getExam_shedule() {
        return exam_shedule;
    }

    public void setExam_shedule(String exam_shedule) {
        this.exam_shedule = exam_shedule == null ? null : exam_shedule.trim();
    }

    public Short getPopulation() {
        return population;
    }

    public void setPopulation(Short population) {
        this.population = population;
    }

    public String getIf_allow_to_join() {
        return if_allow_to_join;
    }

    public void setIf_allow_to_join(String if_allow_to_join) {
        this.if_allow_to_join = if_allow_to_join == null ? null : if_allow_to_join.trim();
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
}
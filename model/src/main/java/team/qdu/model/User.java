package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer user_id;

    private String account;

    private String password;

    private String avatar;

    private String name;

    private String gender;

    private String status_message;

    private Date birthday;

    private Short university;

    private Short department;

    private String if_show_closed_classes;

    private Date create_date_time;

    private Date modify_date_time;

    private String security_question;

    private String security_answer;

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    private static final long serialVersionUID = 1L;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message == null ? null : status_message.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Short getUniversity() {
        return university;
    }

    public void setUniversity(Short university) {
        this.university = university;
    }

    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

    public String getIf_show_closed_classes() {
        return if_show_closed_classes;
    }

    public void setIf_show_closed_classes(String if_show_closed_classes) {
        this.if_show_closed_classes = if_show_closed_classes == null ? null : if_show_closed_classes.trim();
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
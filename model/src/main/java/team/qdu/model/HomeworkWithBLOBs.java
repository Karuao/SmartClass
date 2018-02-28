package team.qdu.model;

import java.io.Serializable;

public class HomeworkWithBLOBs extends Homework implements Serializable {
    private String detail;

    private static final long serialVersionUID = 1L;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}
package team.qdu.model;

import java.io.Serializable;

public class HomeworkAnswerWithBLOBs extends HomeworkAnswer implements Serializable {
    private String detail;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
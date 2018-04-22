package team.qdu.model;

import java.io.Serializable;
import java.util.Date;

public class Material_User implements Serializable {
    private Integer material_user_id;

    private Integer material_id;

    private String name;

    private String url;

    private String if_download;

    private String if_browse;

    private Integer class_id;

    private Integer user_id;

    private Date create_date_time;

    private Date modify_date_time;

    private static final long serialVersionUID = 1L;

    public Integer getMaterial_user_id() {
        return material_user_id;
    }

    public void setMaterial_user_id(Integer material_user_id) {
        this.material_user_id = material_user_id;
    }

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIf_download() {
        return if_download;
    }

    public void setIf_download(String if_download) {
        this.if_download = if_download == null ? null : if_download.trim();
    }
    public String getIf_browse() {
        return if_browse;
    }

    public void setIf_browse(String if_browse) {
        this.if_browse = if_browse;
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
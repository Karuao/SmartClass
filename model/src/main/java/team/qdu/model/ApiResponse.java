package team.qdu.model;

/**
 * Created by Rock on 2017/9/3.
 */

public class ApiResponse<T> {

    private String event;
    private String msg;
    private T obj;
    private T objList;
//    private int currentPage;
//    private int pageSize;
//    private int maxCount;
//    private int maxPage;

    public ApiResponse(){}

    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObjList() {
        return objList;
    }

    public void setObjList(T objList) {
        this.objList = objList;
    }

    public boolean isSuccess() {
        return event.equals("0");
    }
}

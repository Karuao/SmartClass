package team.qdu.api;

import team.qdu.model.ApiResponse;

/**
 * Created by Rock on 2017/9/3.
 */

public interface Api {

    //发送验证码
    public final static String SEND_SMS_CODE = "service.sendSmsCode4Register";
    //注册
    public final static String REGISTER = "customer.registerByPhone";
    //登录
    public final static String LOGIN = "customer.loginByApp";
    //拳列表
    public final static String LIST_COUPON = "issue.listNewCoupon";


    public ApiResponse<Void> loginByApp(String loginName, String password);
}

package com.twosmall.constellation.entity.result;

import java.util.HashMap;
import java.util.Map;

public class AbstractRestConstants {

    public final static Integer RESPONSE_CODE_SUCCESS = 1;                                //接口访问成功
    public final static Integer RESPONSE_CODE_FAILED = 0;                                //接口访问失败

    public final static Integer ERROR_CODE_SYSTEM_ERROR = 10001;                        //系统异常错误

    public final static Integer ERROR_CODE_PARAM_ERROR = 10003;                            //参数验证错误

    public final static Integer ERROR_CODE_TOKEN_LOGOUT = 10004;                        //登录失效,请重新登陆
    public final static Integer ERROR_CODE_TOKEN_EXPIRE = 10006;                        //token过期
    public final static Integer ERROR_CODE_TOKEN_FAIL = 10007;                            //token验证失败
    public final static Integer ERROR_CODE_NOT_LOGIN = 10008;                            //未登录
    public final static Integer ERROR_CODE_REPEAT_COMMIT = 10009;                            //重复提交

    public final static Integer ERROR_CODE_IMPORT_ERROR = 10010;                            //导入错误



    public static Map<Integer, String> commonErrorCodeMap = new HashMap<Integer, String>();

    static {
        initCommonErrorCodeMap(commonErrorCodeMap);
    }

    public static void initCommonErrorCodeMap(Map<Integer, String> commonErrorCodeMap) {
        commonErrorCodeMap.put(ERROR_CODE_SYSTEM_ERROR, "系统异常错误");
        commonErrorCodeMap.put(ERROR_CODE_PARAM_ERROR, "参数验证错误");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_LOGOUT, "登录失效,请重新登录");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_EXPIRE, "登录过期,请重新登录");
        commonErrorCodeMap.put(ERROR_CODE_TOKEN_FAIL, "身份验证失败");
        commonErrorCodeMap.put(ERROR_CODE_NOT_LOGIN, "请先登录");
        commonErrorCodeMap.put(ERROR_CODE_REPEAT_COMMIT, "重复提交!");
    }

    public static String getMessage(Integer errorCode) {
        return commonErrorCodeMap.get(errorCode);
    }
}

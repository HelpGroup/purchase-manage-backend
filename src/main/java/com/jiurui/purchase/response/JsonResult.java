package com.jiurui.purchase.response;

import java.io.Serializable;
import java.util.List;

public class JsonResult implements Serializable {

    /**
     * 成功
     */
    public static final int SUCCESS = 1;

    /**
     * 失败
     */
    public static final int FAIL = 0;

    /**
     * 参数错误
     */
    public static final int PARAMETERERROR = 400;


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    protected int status;

    /**
     * 消息
     */
    protected String message;

    /**
     * 构造函数
     */
    public JsonResult() {

    }


    /**
     * 构造函数
     *
     * @param status 状态
     */
    public JsonResult(int status) {
        this.status = status;
    }

    /**
     * 构造函数
     *
     * @param status 状态
     */
    public JsonResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static JsonResult ParameterError() {
        return new JsonResult(JsonResult.PARAMETERERROR);
    }

    public static JsonResult Success() {
        return new JsonResult(JsonResult.SUCCESS);
    }

    public static JsonResult Fail() {
        return new JsonResult(JsonResult.FAIL);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

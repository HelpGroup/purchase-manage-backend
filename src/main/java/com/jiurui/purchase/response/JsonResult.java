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
     * 服务端异常
     */
    public static final int ERROR = 500;

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
     * 结果值
     */
    protected String result;


    /**
     * 结果值集合
     */
    protected List resultSet;


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

    public static JsonResult Error() {
        return new JsonResult(JsonResult.ERROR);
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List getResultSet() {
        return resultSet;
    }

    public void setResultSet(List resultSet) {
        this.resultSet = resultSet;
    }
}

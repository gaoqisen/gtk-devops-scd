package com.gtk.webapi.utils;

/**
 * @ClassName JsonResult
 * @Author gaoqisen
 * @Date 2019-06-20
 * @Version 1.0
 */
public class JsonResult<T> {
    public static final int SUCCESS=200;
    public static final int ERROR=300;
    private int code;
    private String msg="";
    private T data;
    public JsonResult() {
        code=SUCCESS;
    }
    //为了方便，重载n个构造器

    public JsonResult(int code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public JsonResult(int code, String data){
        if(code==1){
            this.code=code;
            this.msg=data;
            return;
        }
        this.code=code;
        this.data=(T) data;
    }
    public JsonResult(String error){
        this(ERROR,error,null);
    }
    public JsonResult(T data){
        this(SUCCESS,"成功",data);
    }

    public JsonResult(int code){
        String msg = "失败";
        if(code == 0) {
            msg = "成功";
        }
        new JsonResult(code,msg,null);
    }
    public JsonResult(Throwable e){
        this(ERROR,e.getMessage(),null);
    }
    public JsonResult(Exception e, String string) {
        this.code=1;
        this.msg=string;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getERROR() {
        return ERROR;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "JsonResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }

}
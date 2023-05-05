package com.dawson.client1.common.response;

/**
 * 自定义异常
 */
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(RRExceptionEnum enumerate) {
        super(enumerate.getMsg());
        this.msg = enumerate.getMsg();
        this.code = enumerate.getCode();
    }

    public RRException(RRExceptionEnum enumerate, String msg) {
        super(msg);
        msg = enumerate.getMsg() + ":" + msg;
        this.msg = msg;
        this.code = enumerate.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toJsonString() {
        return "{\"msg\":\"" + msg + "\",\"code\":" + code + "}";
    }
}

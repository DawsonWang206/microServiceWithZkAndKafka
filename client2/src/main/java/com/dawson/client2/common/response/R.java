package com.dawson.client2.common.response;

import java.util.HashMap;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R ok() {
        return new R();
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(RRExceptionEnum enumerate) {
        R r = new R();
        r.put("code", enumerate.getCode());
        r.put("msg", enumerate.getMsg());
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

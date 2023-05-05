package com.dawson.client2.common.response;

public enum RRExceptionEnum {
    PATH_NOT_FOUND("路径不存在，请检查路径是否正确", 404),

    SYSTEM_ERROR("系统异常，请联系管理员", 500),
    NULL_POINTER("空指针异常，请检查传入参数或联系管理员", 500),
    FILE_UPLOAD_ERROR("上传文件异常", 500),
    JSON_ERROR("json解析异常", 500),

    TOKEN_EXPIRE("token已失效", 501),
    ACCOUNT_NOT_EXIST("该账号不存在或已删除", 501),
    USER_NOT_FOUND("未找到用户信息，请尝试重新授权或提交", 501),

    WARN_SQL_INJECT("对不起，您输入的数据中含有非法单词，请确认后重新输入", 502),
    INVALID_PARAMETER("参数非法", 502),
    WRONG_QR_MSG("参数非法:错误的二维码信息", 502),
    REQUEST_METHOD_NOT_SUPPORTED("请求方式不支持", 502),
    PARAMETER_TOKEN_NOT_FOUND("参数非法,请求参数token不存在", 502),
    PARAMETER_OPEN_ID_NOT_FOUND("参数非法,请求参数openId不存在", 502),
    EXCLE_INVALID_PARAMETER("导入文件中存在错误数据，数据所在行数:", 502),

    WRONG_PASSWORD("账号或密码错误", 503),
    SAME_PASSWORD("新密码不能和原密码相同", 503),
    WRONG_OLD_PASSWORD("原密码错误", 503),
    NEED_MORE_PERMISSION("权限不足", 503),

    DATA_NOT_EXISTS("数据不存在或已删除", 504),
    USER_NOT_EXISTS("用户信息不存在或已删除", 504),

    SYSTEM_BUSY("系统繁忙，请稍后再试", 505),
    DATA_ALREADY_EXISTS("数据库已存在该记录", 505),
    PHONE_ALREADY_EXISTS("对不起，该手机号已经提交过信息了", 505),
    OPEN_ID_ALREADY_EXISTS("对不起，该微信号已经提交过信息了", 505),
    PHOTO_ALREADY_EXISTS("对不起，您已经提交过照片了", 505),
    ALREADY_REGISTER("对不起，您已经签过到了", 505),
    USERNAME_EXISTED("用户名已存在，请更改后再提交", 501),
    UPLOAD_WRONGDISTRICT("地区信息有误，请修改后上传", 501),
    MODELCODE_EXISTS("车型编码已存在，请修改后提交", 501),

    POSTCONTENT_EMPTY("提交内容不能全为空", 500),
    PROJECT_NOTEXIST("项目不存在", 500),
    PROJECT_STARTTIMECOUDNOTMODIFY("不能修改开始时间", 500),
    SQL_NOTSAVE("信息保存失败", 500);


    private String msg;
    private int code;

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

    RRExceptionEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}

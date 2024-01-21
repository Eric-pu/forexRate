package org.exchange_rate.enums;

public enum RespStateEnum {

    SUCCESS("0000", "成功"),
    ERROR("E001", "日期區間不符");

    String code;

    String msg;

    private RespStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

package com.codem.hello.constant;

import cn.com.codem.commons.lang.constant.EnumValue;

public enum PublishStatusEnum implements EnumValue<Short> {
    WAITING((short) 0, "等待中"),
    PROCESS((short) 1, "发布中"),
    SUCCESS((short) 2, "成功"),
    FAILURE((short) 3, "失败"),
    UNKNOWN((short) 4, "未知");

    private Short value;
    private String desc;

    PublishStatusEnum(short value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }

    public Short value() {
        return value;
    }

    public boolean isSame(Short value) {
        return this.value.equals(value);
    }

}

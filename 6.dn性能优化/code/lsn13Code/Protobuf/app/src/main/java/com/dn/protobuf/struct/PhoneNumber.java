package com.dn.protobuf.struct;
/**
 *
 * @author Damon
 * @Date 2019/05/29 00:07:48
 *
 */
public class PhoneNumber {
    enum PhoneType {
        MOBILE,
        HOME,
        WORK;
    }

    private String number;
    private PhoneType type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
}

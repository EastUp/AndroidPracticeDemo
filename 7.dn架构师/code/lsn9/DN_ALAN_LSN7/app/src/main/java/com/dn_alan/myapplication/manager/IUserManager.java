package com.dn_alan.myapplication.manager;

import com.dn_alan.myapplication.Friend;
import com.dn_alan.myapplication.annotion.ClassId;
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  客户端中的类
 *  @author: jamin
 *  @date: 2021/1/19 9:57
 * |---------------------------------------------------------------------------------------------------------------|
 */
@ClassId("com.dn_alan.myapplication.manager.DnUserManager")
public interface IUserManager {
    public Friend getFriend();
    public void setFriend(Friend friend);
}

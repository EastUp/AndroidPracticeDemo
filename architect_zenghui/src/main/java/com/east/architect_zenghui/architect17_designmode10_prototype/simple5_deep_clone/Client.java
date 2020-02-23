package com.east.architect_zenghui.architect17_designmode10_prototype.simple5_deep_clone;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  测试客户端
 *  @author: East
 *  @date: 2020-02-23 16:49
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Client {
    public static void main(String[] args){
        User user = new User();
        user.setAge(18);
        user.setName("Darren");

        User.Address address = new User.Address("湖南长沙", "长沙");

        user.setAddress(address);

        // 深拷贝
        try {
            // 拷贝对象
            User copyUser =  user.clone();

            // System.out.print("姓名："+user.userName+" 地址："+user.userAddress.addressName+"\n");
            // System.out.print("姓名："+copyUser.userName+" 地址："+copyUser.userAddress.addressName);
            // 把拷贝的地址做修改
            copyUser.getAddress().addressName = "深圳珠海";
            copyUser.setName("GG");
            // 现象是 姓名是各自的 copy: GG 原来的是 Darren
            // 把copy的地址改了一下 ，就都变成了 深圳珠海，按道理应该是 copy:深圳珠海 原来的 湖南长沙
            // 浅拷贝，就是类的类对象实例，是没有被拷贝的，他们还是公用一份
            System.out.print("姓名："+user.getName()+" 地址："+user.getAddress().addressName+"\n");
            System.out.print("姓名："+copyUser.getName()+" 地址："+copyUser.getAddress().addressName);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}

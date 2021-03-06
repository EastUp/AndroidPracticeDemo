package com.east.architect_zenghui.architect_20_designmode13.simple2;

/**
 * Created by hcDarren on 2017/10/29.
 */

public class Client {
    public static void main(String[] args){
        // 看似很复杂  三个角色：命令角色，命令的接收（执行者），命令的发送者
        TetrisMachine machine = new TetrisMachine();

        LeftCommand leftCommand = new LeftCommand(machine);
        RightCommand rightCommand = new RightCommand(machine);

        Buttons buttons = new Buttons();
        buttons.setLeftCommand(leftCommand);
        buttons.setRightCommand(rightCommand);

        buttons.toRight();
        buttons.toLeft();
        buttons.toRight();

        // 简单版
        // TetrisMachine machine = new TetrisMachine();
        machine.toLeft();
        machine.toRight();
    }
}

package com.east.architect_zenghui.architect_20_designmode13.simple2;

/**
 * Created by hcDarren on 2017/10/29.
 */

public class RightCommand implements Command{
    private TetrisMachine machine;

    public RightCommand(TetrisMachine machine){
        this.machine = machine;
    }


    @Override
    public void execute() {
        machine.toRight();
    }
}

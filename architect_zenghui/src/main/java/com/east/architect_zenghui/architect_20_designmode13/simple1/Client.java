package com.east.architect_zenghui.architect_20_designmode13.simple1;

/**
 * Created by hcDarren on 2017/10/29.
 */

public class Client {
    public static void main(String[] args){
        for(int i=0;i<1000;i++){
            Ticket ticket = TicketFactory.getTicket("深圳","长沙");
            
        }
    }
}

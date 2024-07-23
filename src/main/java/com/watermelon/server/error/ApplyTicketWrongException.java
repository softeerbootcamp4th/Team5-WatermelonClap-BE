package com.watermelon.server.error;



public class ApplyTicketWrongException extends Exception{
    public ApplyTicketWrongException(){
        super("유효하지 않은 티켓");
    }
}

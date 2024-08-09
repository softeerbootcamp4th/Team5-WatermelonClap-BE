package com.watermelon.server.event.order.error;

public class WrongPhoneNumberFormatException extends Exception{
    public WrongPhoneNumberFormatException() {
        super("Invalid phone number");
    }
}

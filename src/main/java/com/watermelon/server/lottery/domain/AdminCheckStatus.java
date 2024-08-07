package com.watermelon.server.lottery.domain;

public enum AdminCheckStatus {
    DONE, READY;

    public static AdminCheckStatus getStatus(boolean isCheckedByAdmin){
        return isCheckedByAdmin ? READY : DONE;
    }

}

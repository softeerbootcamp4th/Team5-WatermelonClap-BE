package com.watermelon.server.randomevent.domain;

public enum AdminCheckStatus {
    DONE, READY;

    public static AdminCheckStatus getStatus(boolean isCheckedByAdmin){
        return isCheckedByAdmin ? READY : DONE;
    }

}

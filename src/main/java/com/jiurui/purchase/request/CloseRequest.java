package com.jiurui.purchase.request;

import javax.validation.constraints.NotNull;

/**
 * Created by mark on 15/9/21.
 */
public class CloseRequest {
    @NotNull
    private boolean lock;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}

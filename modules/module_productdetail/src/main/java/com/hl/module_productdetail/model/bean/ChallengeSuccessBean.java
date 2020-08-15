package com.hl.module_productdetail.model.bean;

import com.hl.anotation.NotProguard;

@NotProguard
public class ChallengeSuccessBean {

    /**
     * uTaskId : 10
     * taskId : 3
     * overdue_time : 2020-06-21 17:35:09
     */

    private int uTaskId;
    private int taskId;
    private String overdue_time;

    public int getUTaskId() {
        return uTaskId;
    }

    public void setUTaskId(int uTaskId) {
        this.uTaskId = uTaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getOverdue_time() {
        return overdue_time;
    }

    public void setOverdue_time(String overdue_time) {
        this.overdue_time = overdue_time;
    }
}

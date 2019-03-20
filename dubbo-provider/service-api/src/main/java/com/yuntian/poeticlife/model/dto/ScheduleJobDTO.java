package com.yuntian.poeticlife.model.dto;

import java.io.Serializable;

public class ScheduleJobDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = 8850670194065278842L;

    private Long jobId;


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
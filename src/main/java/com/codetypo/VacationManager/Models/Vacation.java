package com.codetypo.VacationManager.Models;

import java.sql.Date;

public class Vacation {

    private int id;
    private Date beginDate;
    private Date endDate;
    private boolean approved;

    public Vacation(int id, Date beginDate, Date endDate, boolean approved) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.approved = approved;
    }

    public Vacation(Date beginDate, Date endDate, boolean approved) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.approved = approved;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }



}

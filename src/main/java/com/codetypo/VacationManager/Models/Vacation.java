package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
public class Vacation {

    private int id;
    private Date beginDate;
    private Date endDate;
    private boolean approved;

    public Vacation(Date beginDate, Date endDate, boolean approved) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.approved = approved;
    }
}

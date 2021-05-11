package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * This class represents the vacation, that was taken by the employee.
 */
@Setter
@Getter
@AllArgsConstructor
public class Vacation {

    /**
     * This private field represents id of vacation.
     */
    private int id;

    /**
     * This private field represents begin vacation date.
     */
    private Date beginDate;

    /**
     * This private field represents end vacation date.
     */
    private Date endDate;

    /**
     * This private field represents whether or not the given vacation has been approved.
     */
    private boolean approved;

    /**
     * This is all arguments constructor.
     *
     * @param beginDate represents begin vacation date.
     * @param endDate   represents end vacation date.
     * @param approved  represents whether or not the given vacation has been approved.
     */
    public Vacation(Date beginDate, Date endDate, boolean approved) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.approved = approved;
    }
}

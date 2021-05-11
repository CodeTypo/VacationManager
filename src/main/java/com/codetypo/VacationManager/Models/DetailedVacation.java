package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * This class combines information about the employee and about the vacation, that
 * was taken by the employee.
 */
@Setter
@Getter
@AllArgsConstructor
public class DetailedVacation {

    /**
     * This private field represents id of vacation detail.
     */
    private int id;

    /**
     * This private field represents id of employee.
     */
    private int employeeId;

    /**
     * This private field represents first name of employee.
     */
    private String employeeFirstName;

    /**
     * This private field represents last name of employee.
     */
    private String employeeLastName;

    /**
     * This private field represents email of employee.
     */
    private String email;

    /**
     * This private field represents id of vacation.
     */
    private int vacationId;

    /**
     * This private field represents begin date of vacation.
     */
    private Date beginDate;

    /**
     * This private field represents end date of vacation.
     */
    private Date endDate;

    /**
     * This private field represents whether or not the given vacation has been approved.
     */
    private boolean approved;
}

package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
public class DetailedVacation {

    private int id;
    private int employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String email;
    private int vacationId;
    private Date beginDate;
    private Date endDate;
    private boolean approved;
}

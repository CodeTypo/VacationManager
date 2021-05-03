package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Details {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int vacationDaysLeft;




    public Details(String firstName, String lastName, String email, int vacationDaysLeft) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.vacationDaysLeft = vacationDaysLeft;

    }

}

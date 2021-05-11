package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents details about the user.
 */
@Setter
@Getter
@AllArgsConstructor
public class Details {

    /**
     * This private field represents id of details.
     */
    private int id;

    /**
     * This private field represents first name of user.
     */
    private String firstName;

    /**
     * This private field represents last name of user.
     */
    private String lastName;

    /**
     * This private field represents email of user.
     */
    private String email;

    /**
     * This private field represents value of vacation days.
     */
    private int vacationDaysLeft;

    /**
     * This is all arguments constructor.
     *
     * @param firstName        represents first name of user.
     * @param lastName         represents last name of user.
     * @param email            represents email of user.
     * @param vacationDaysLeft represents value of vacation days, that user has.
     */
    public Details(String firstName, String lastName, String email, int vacationDaysLeft) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.vacationDaysLeft = vacationDaysLeft;
    }

}

package com.codetypo.VacationManager.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the employee.
 */
@Setter
@Getter
@AllArgsConstructor
public class Employee {

    /**
     * This private field represents id of employee.
     */
    private int id;

    /**
     * This private field represents login of employee.
     */
    private String login;

    /**
     * This private field represents password of employee.
     */
    private String password;

    /**
     * This private field tells you if the user is admin.
     */
    private boolean isAdmin;

    /**
     * This is a getter of isAdmin.
     *
     * @return value of isAdmin.
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * This is a setter of isAdmin.
     *
     * @param admin sets value for isAdmin.
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

package com.app.borgapplication.database.impl;

public class JoinEmployeeInformation {
    //employee_id, first_name, last_name, availability, dayNumber
    private String first_name;
    private String last_name;
    private String dayNumber;
    private String availability;
    private String email;
    private String training_status;
    private int employee_id;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public String getTraining_status() {
        return training_status;
    }

    public void setTraining_status(String training_status) {
        this.training_status = training_status;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String days_of_week) {
        this.dayNumber = days_of_week;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

}

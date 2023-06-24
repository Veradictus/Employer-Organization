package com.app.borgapplication.database.impl;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee_roster")
public class EmployeeTable {

    @ColumnInfo(name = "employee_id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name= "first_name")
    private String firstName;

    @ColumnInfo(name= "last_name")
    private String lastName;

    @ColumnInfo(name= "email")
    private String email;

    public EmployeeTable(@NonNull String firstName, @NonNull String lastName, @NonNull String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Ignore
    public EmployeeTable(int id,@NonNull String firstName, @NonNull String lastName, @NonNull String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }
}
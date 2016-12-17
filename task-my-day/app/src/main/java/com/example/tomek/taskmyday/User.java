package com.example.tomek.taskmyday;

import java.io.Serializable;

/**
 * Created by tomek on 08.02.16.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 100L;
    private int userId;
    private String userName;
    private int Group;
    private boolean Local;
    private String JoinDate;
    private String BirthDate;
    private String NameDay;
    private String ImportantDate;
    private String Description;
    private String Other;


    public User (int id, String name, int group, boolean local, String joinDate, String birthDate, String nameDay, String importantDate, String description, String other) {
        this.userId = id;
        this.userName = name;
        this.Group = group;
        this.Local = local;
        this.JoinDate = joinDate;
        this.BirthDate = birthDate;
        this.NameDay = nameDay;
        this.ImportantDate = importantDate;
        this.Description = description;
        this.Other = other;
    }

    public User() {

    }

    public void setId(int id) {
        this.userId = id;
    }

    public int getId() {
        return this.userId;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getName() {
        return this.userName;
    }

    public void setGroup(int group) {
        this.Group = group;
    }

    public int getGroup() {
        return this.Group;
    }

    public void setLocal(Boolean local) {
        this.Local = local;
    }

    public boolean getLocal() {
        return this.Local;
    }

    public void setJoinDate(String date) {
        // this.JoinDate = TO DO;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setBirthDate(String date) {
        // this.BirthDate = TO DO;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setNameDay(String date) {
        // this.NameDay = TO DO;
    }

    public String getNameDay() {
        return this.NameDay;
    }

    public void setImportantDate(String date) {
        // this.ImportantDate = TO DO;
    }

    public String getImportantDate() {
        return this.ImportantDate;
    }
    public void setDescription(String description) {
        this.Description = description;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setOther(String date) {
        // this.Other = TO DO;
    }

    public String getOther() {
        return this.Other;
    }

    @Override
    public String toString() {
        return "User:" +
                "\nid: " + userId +
                "\nname" + userName +
                "\ngroup: " + Group +
                "\nlocal: " + Local +
                "\njoinDate: " + JoinDate +
                "\nbirthDate: " + BirthDate +
                "\nnameDay: " + NameDay +
                "\nimportantDate: " + ImportantDate +
                "\nother: " + Other;
    }

}
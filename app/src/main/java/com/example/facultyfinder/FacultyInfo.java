package com.example.facultyfinder;

import java.util.ArrayList;

public class FacultyInfo {
    String facultyname;
    String facultyphoneno;
    Boolean present;
    ArrayList<String> monday = new ArrayList<String>();
    ArrayList<String> tuesday = new ArrayList<String>();
    ArrayList<String> wednesday = new ArrayList<String>();
    ArrayList<String> thursday = new ArrayList<String>();
    ArrayList<String> friday = new ArrayList<String>();
    ArrayList<String> saturday = new ArrayList<String>();

    public  FacultyInfo()
    {

    }

    public FacultyInfo(String facultyname, String facultyphoneno,Boolean present, ArrayList<String> monday, ArrayList<String> tuesday, ArrayList<String> wednesday, ArrayList<String> thursday, ArrayList<String> friday, ArrayList<String> saturday) {
        this.facultyname = facultyname;
        this.facultyphoneno = facultyphoneno;
        this.present=present;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    //Getters
    public Boolean getPresent()
    {
        return present;
    }
    public String getFacultyname() {
        return facultyname;
    }

    public String getFacultyphoneno() {
        return facultyphoneno;
    }

    public ArrayList<String> getMonday() {
        return monday;
    }

    public ArrayList<String> getTuesday() {
        return tuesday;
    }

    public ArrayList<String> getWednesday() {
        return wednesday;
    }

    public ArrayList<String> getThursday() {
        return thursday;
    }

    public ArrayList<String> getFriday() {
        return friday;
    }

    public ArrayList<String> getSaturday() {
        return saturday;
    }

    //Setters
    public void setPresent(Boolean present)
    {
        this.present=present;
    }
    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public void setFacultyphoneno(String facultyphoneno) {
        this.facultyphoneno = facultyphoneno;
    }

    public void setMonday(ArrayList<String> monday) {
        this.monday = monday;
    }

    public void setTuesday(ArrayList<String> tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(ArrayList<String> wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(ArrayList<String> thursday) {
        this.thursday = thursday;
    }

    public void setFriday(ArrayList<String> friday) {
        this.friday = friday;
    }

    public void setSaturday(ArrayList<String> saturday) {
        this.saturday = saturday;
    }
}

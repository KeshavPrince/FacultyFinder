package com.example.facultyfinder;



public class ProfileInfo {

    public String username;
    public String phonenumber;
    public String organisationname;
    public Integer nooflectures;
    public Integer duration;
    public Integer startingtime;

    public ProfileInfo()
    {

    }
    public ProfileInfo(String username, String phonenumber, String organisationname, Integer nooflectures, Integer duration, Integer startingtime) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.organisationname = organisationname;
        this.nooflectures = nooflectures;
        this.duration = duration;
        this.startingtime = startingtime;
    }
 //getters
    public String getUsername() {
        return username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getOrganisationname() {
        return organisationname;
    }

    public Integer getNooflectures() {
        return nooflectures;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getStartingtime() {
        return startingtime;
    }
   //setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setOrganisationname(String organisationname) {
        this.organisationname = organisationname;
    }

    public void setNooflectures(Integer nooflectures) {
        this.nooflectures = nooflectures;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStartingtime(Integer startingtime) {
        this.startingtime = startingtime;
    }
}

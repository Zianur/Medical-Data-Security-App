package com.medicalsecurityapp;

import com.google.firebase.database.Exclude;

public class MedicalRecordModule {

    String userfullname, id, date, issues, doctor, testreports, age, medicine;

    String recordkey;

    public MedicalRecordModule() {

    }

    @Exclude
    public String getRecordkey() {
        return recordkey;
    }

    @Exclude
    public void setRecordkey(String recordkey) {
        this.recordkey = recordkey;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTestreports() {
        return testreports;
    }

    public void setTestreports(String testreports) {
        this.testreports = testreports;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

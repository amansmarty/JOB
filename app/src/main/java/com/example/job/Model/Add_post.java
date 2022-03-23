package com.example.job.Model;

public class Add_post {
    String Job_Title;
    String job_description;
    String skill;
    String experience;
    String qualification;
    String job;
    String salary;
    String Date;

    public Add_post(String job_Title, String job_description, String skill, String experience, String qualification, String job, String salary,String Date) {
        Job_Title = job_Title;
        this.job_description = job_description;
        this.skill = skill;
        this.experience = experience;
        this.qualification = qualification;
        this.job = job;
        this.salary = salary;
        this.Date=Date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Add_post() {
    }

    public String getJob_Title() {
        return Job_Title;
    }

    public void setJob_Title(String job_Title) {
        Job_Title = job_Title;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}

package com.example.fitness;

public class UserHelperClass2 {
    String name,username,email,phoneNo,password,Height,Weight,Age,Gender,Active,Goal;

    public UserHelperClass2() {
    }

    public UserHelperClass2(String name,String username,String email,String phoneNo,String password,String Height, String Weight, String Age,String Gender,String Active,String Goal) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.Height = Height;
        this.Weight = Weight;
        this.Age = Age;
        this.Gender=Gender;
        this.Active=Active;
        this.Goal=Goal;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getHeight() {
        return Height;
    }
    public void setHeight(String Height) {
        this.Height = Height;
    }
    public String getWeight() {
        return Weight;
    }
    public void setWeight(String Weight) {
        this.Weight = Weight;
    }
    public String getAge() {
        return Age;
    }
    public void setAge(String Age) {
        this.Age = Age;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String Gender) {
        this.Gender = Gender;
    }
    public String getActive() {
        return Active;
    }
    public void setActive(String Active) {
        this.Active = Active;
    }
    public String getGoal() {
        return Goal;
    }
    public void setGoal(String Goal) {
        this.Goal = Goal;
    }

}

package com.example.yuniavia.Client.ClientHandlerClasses;

import java.io.Serializable;

public class Account implements Serializable {
    private String first_name;
    private String second_name;
    private String phone_number;
    private String password;
    private String role;
    private String role_in_string;

    public Account(String first_name, String second_name, String phone_number,
                  String password, String role) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.phone_number = phone_number;
        this.password = password;


        this.role=role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", role_in_string='" + role_in_string + '\'' +
                '}';
    }

    public Account() {

    }

    public String getStringRole(int role) {
        String string_role="";
        switch (role){
            case 5:string_role="Администратор";
            break;
            case 1:string_role="Спортсмен";
            break;
            case 2:string_role="Тренер";
            break;
        }
        return string_role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getSecond_name() {
        return second_name;
    }
    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }



    public String getRole_in_string() {
        return role_in_string;
    }

    public void setRole_in_string(String role_in_string) {
        this.role_in_string = role_in_string;
    }
}


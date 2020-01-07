/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slmn.patient_management.user_structures;

import com.google.gson.internal.LinkedTreeMap;

/**
 *
 * @author Jill
 */

public class Patient extends User {
    private int age;
    private String sex; 
    // "sex" is the medically correct term for "gender assigned at birth"
    
    public Patient (String givenName, String surname, String address, String password, int age, String sex) {
        super("P", givenName, surname, address, password);
        
        this.age = age;
        this.sex = sex;
    }

    public Patient(LinkedTreeMap object) {
        super(object);
        this.age = (int) object.get("age");
        this.sex = (String) object.get("sex");
    }
}
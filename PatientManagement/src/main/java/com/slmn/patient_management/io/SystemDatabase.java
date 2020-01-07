package com.slmn.patient_management.io;

import com.slmn.patient_management.user_structures.*;
import com.slmn.patient_management.drug_structures.*;
import com.slmn.patient_management.io.*;

import java.util.ArrayList;

public class SystemDatabase {
    public ArrayList<Administrator> admins;
    public ArrayList<Secretary> secretaries;
    public ArrayList<Doctor> doctors;
    public ArrayList<Patient> patients;

    public SystemDatabase() {
        // Load all data into the system
        /*
        Shouldn't need to load the data at any other point other than start, as long as it gets written back timely
        * */
        this.admins = this.loadUsers("admins.json");
        this.secretaries = this.loadUsers("secretaries.json");
        this.doctors = this.loadUsers("doctors.json");
        this.patients = this.loadUsers("patients.json");
    }

    public void write() {
        /*
        * Write everything back to their files.
        * Won't be large enough to cause any problems. Also avoids problems of part-saving data with dependencies
        * */

    }

    private ArrayList loadUsers(String filename) {
        JSONArrayDecoder decoder = new JSONArrayDecoder(filename, new UserDecoder());
        return decoder.decode();
    }
}
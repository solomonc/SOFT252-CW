package com.slmn.patient_management.gui.controllers;

import com.google.gson.internal.LinkedTreeMap;
import com.slmn.patient_management.io.SystemDatabase;
import com.slmn.patient_management.notifications.NotificationHandler;
import com.slmn.patient_management.user_structures.Administrator;
import com.slmn.patient_management.user_structures.Patient;
import com.slmn.patient_management.user_structures.User;
import com.slmn.patient_management.user_structures.requests.AccountCreationRequest;
import com.slmn.patient_management.user_structures.requests.AccountRequest;

import javax.swing.*;

public class AccountCreatorController extends Controller {

    public User createUser(String className, String givenName, String surname, String address, String password) {
        String code = className.substring(0,1);
        User user = User.createAppropriateUser(code, givenName, surname, address, password);
        System.out.println(String.format("Created user: %s", user.describe()));

        // Using polymorphism to push the user to the correct collection
        SystemDatabase.connect().pushUser(user);
        SystemDatabase.connect().writeAll();

        this.showMessage(String.format("[ID: %s] %s has been created.", user.getID(), user.getFullName()), String.format("New %s created", className), JOptionPane.PLAIN_MESSAGE);
        return user;
    }

    public void requestPatient(String givenName, String surname, String address, String password, String ageInput, String sex) {
        int age = Integer.parseInt(ageInput.trim());
        Patient requestedPatient = new Patient(givenName, surname, address, password, age, sex);
        System.out.println(String.format("Patient requested: %s", requestedPatient.describe()));
        SystemDatabase.connect().accountRequests.add(new AccountCreationRequest(requestedPatient));
        SystemDatabase.connect().writeAll();
        this.showMessage("Your account has been submitted for approval.\nA secretary will approve your account soon.", "Account submitted", JOptionPane.INFORMATION_MESSAGE);

        NotificationHandler.notifySecretaries(String.format("A new patient account (ID %s) is ready to be approved.", requestedPatient.getID()));
    }

    public void approveRequest(AccountRequest request) {
        this.showMessage(String.format("%s's %s request has been approved.", request.getPatient().getFullName(), request.getType().toLowerCase()), "Request approved", JOptionPane.INFORMATION_MESSAGE);
        request.approve();
    }
    public void declineRequest(AccountRequest request) {
        this.showMessage(String.format("%s's %s request has been declined.", request.getPatient().getFullName(), request.getType().toLowerCase()), "Request approved", JOptionPane.INFORMATION_MESSAGE);
        request.decline();
    }


}

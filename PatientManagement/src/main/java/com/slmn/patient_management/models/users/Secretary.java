/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slmn.patient_management.models.users;

import com.google.gson.internal.LinkedTreeMap;
import com.slmn.patient_management.io.SystemDatabase;
import com.slmn.patient_management.models.notifications.Notification;

/**
 * @author Jill
 */
public class Secretary extends User {
    public Secretary(String givenName, String surname, String address, String password) {
        super("S", givenName, surname, address, password);
    }

    public Secretary(LinkedTreeMap object) {
        super(object);
    }

    @Override
    public void destroyDependencies() {
        for (Notification notification : SystemDatabase.connect().specificUserNotifications) {
            if (notification.isApplicableToUser(this)) notification.dismiss();
        }
    }
}

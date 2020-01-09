package com.slmn.patient_management.user_structures.requests;

import com.slmn.patient_management.io.SystemDatabase;
import com.slmn.patient_management.user_structures.Patient;

public class AccountDeletionRequest implements AccountRequest {
    private Patient user = null;
    public String type = "Deletion";



    public AccountDeletionRequest(Patient user) {
        this.user = user;
    }

    @Override
    public void approve() {
        this.delete(user);
        this.completeRequest();
    }

    @Override
    public void decline() {
        this.completeRequest();
        // ignore
    }

    private void delete(Patient user) {
        // actually remove the user
        SystemDatabase.connect().patients.remove(user);
    }

    @Override
    public void completeRequest() {
        SystemDatabase.connect().accountRequests.remove(this);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Patient getPatient() {
        return this.user;
    }
}

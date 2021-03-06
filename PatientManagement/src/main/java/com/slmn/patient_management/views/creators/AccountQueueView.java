package com.slmn.patient_management.views.creators;

import com.slmn.patient_management.controllers.AccountController;
import com.slmn.patient_management.io.SystemDatabase;
import com.slmn.patient_management.models.users.requests.AccountRequest;
import com.slmn.patient_management.views.main_menu.SecretaryMainMenu;
import com.slmn.patient_management.views.structures.SubFrame;
import com.slmn.patient_management.views.structures.SwitchableFrame;
import com.slmn.patient_management.views.structures.ViewWithFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccountQueueView extends ViewWithFrame {
    private JPanel mainPanel;
    private JComboBox cbxQueue;
    private JButton btnApprove;
    private JButton btnDecline;

    private AccountRequest request;

    public AccountQueueView() {
        updateBox();
        AccountController controller = new AccountController();

        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.approveRequest(request);
                updateBox();
            }
        });
        btnDecline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.declineRequest(request);
                updateBox();
            }
        });


        cbxQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRequest();
            }
        });
    }

    @Override
    public SwitchableFrame getFrame() {
        return new SubFrame("Account Queue", this.mainPanel, new SecretaryMainMenu());
    }

    private void updateRequest() {
        if (cbxQueue.getSelectedIndex() == -1) {
            request = null;
            btnDecline.setEnabled(false);
            btnApprove.setEnabled(false);
        } else {
            request = SystemDatabase.connect().accountRequests.get(cbxQueue.getSelectedIndex());
            btnDecline.setEnabled(true);
            btnApprove.setEnabled(true);
        }
    }

    public void updateBox() {
        cbxQueue.removeAllItems();
        ArrayList<AccountRequest> requests = SystemDatabase.connect().accountRequests;
        for (AccountRequest request : requests) {
            cbxQueue.addItem(String.format("[%s] ID %s %s", request.getType(), request.getPatient().getID(), request.getPatient().getFullName()));
        }
        updateRequest();
    }
}

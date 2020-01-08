package com.slmn.patient_management.gui.views.creators;

import com.slmn.patient_management.gui.controllers.AccountCreatorController;
import com.slmn.patient_management.gui.structures.SubFrame;
import com.slmn.patient_management.gui.structures.SwitchableFrame;
import com.slmn.patient_management.gui.structures.ViewWithFrame;
import com.slmn.patient_management.gui.views.main_menu.AdminMainMenuView;
import com.slmn.patient_management.user_structures.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountCreatorView extends ViewWithFrame {

    private JPanel mainPanel;
    private JLabel lblDescTitle;
    private JRadioButton rbtnAdmin;
    private JRadioButton rbtnDoctor;
    private JRadioButton rbtnSecretary;
    private JTextField txtGivenName;
    private JTextField txtSurname;
    private JTextField txtAddress;
    private JTextField txtPassword;
    private JButton btnSubmit;
    private ButtonGroup btngpAccountType;

    private static SwitchableFrame frame;

    @Override
    public SwitchableFrame getFrame() {
        if (frame == null) frame = new SubFrame("Account Creator", this.mainPanel, new AdminMainMenuView());
        return frame;
    }

    public AccountCreatorView() {
        AccountCreatorController controller = new AccountCreatorController();
        rbtnAdmin.addActionListener(e -> this.userType = Administrator.class);
        rbtnDoctor.addActionListener(e -> this.userType = Doctor.class);
        rbtnSecretary.addActionListener(e -> this.userType = Secretary.class);
        rbtnSecretary.addActionListener(this.radioButtonListener());
        rbtnDoctor.addActionListener(this.radioButtonListener());
        rbtnAdmin.addActionListener(this.radioButtonListener());

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String code, String givenName, String surname, String address, String password
                User user = controller.createUser(userType.getSimpleName(), txtGivenName.getText(), txtSurname.getText(), txtAddress.getText(), txtPassword.getText());
                close();
            }
        });
    }
    private void close() {
        this.getFrame().dispose();
        // cleans it for next time
        frame = null;
    }

    private Class userType = null;


    private ActionListener radioButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSubmit.setEnabled(true);
            }
        };
    }
}
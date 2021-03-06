package com.slmn.patient_management.views.main_menu;

import com.slmn.patient_management.controllers.AccountController;
import com.slmn.patient_management.controllers.BasicRoutingController;
import com.slmn.patient_management.core.Main;
import com.slmn.patient_management.models.users.Patient;
import com.slmn.patient_management.views.structures.ClosableFrame;
import com.slmn.patient_management.views.structures.SwitchableFrame;
import com.slmn.patient_management.views.structures.ViewWithFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientMainMenu extends ViewWithFrame {

    private JPanel mainPanel;
    private JButton btnPrescriptions;
    private JButton btnRequestAccountDeletion;
    private JButton btnViewRecords;
    private JButton btnRateDoctors;
    private JButton btnRequestAppointment;
    private JButton btnLogout;
    private JButton btnViewAppointments;

    public PatientMainMenu() {
        BasicRoutingController controller = new BasicRoutingController();
        btnPrescriptions.addActionListener(e -> controller.routeToPrescriptionViewer((Patient) Main.authenticatedUser, this));
        btnViewRecords.addActionListener(e -> controller.routeToRecordsSelfView());
        btnRateDoctors.addActionListener(e -> controller.routeToDoctorRatingPatient());

        btnRequestAccountDeletion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?\nThis cannot be undone.", "Confirm account deletion request", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    AccountController ac = new AccountController();
                    ac.requestDeletion((Patient) Main.authenticatedUser);
                    close();
                }
            }
        });
        btnRequestAppointment.addActionListener(e -> controller.routeToPatientAppointmentRequester());
        btnLogout.addActionListener(e -> Main.logout());
        btnViewAppointments.addActionListener(e -> controller.routeToAppointmentView((Patient) Main.authenticatedUser));
    }

    private void close() {this.getFrame().dispose();}

    @Override
    public SwitchableFrame getFrame() {
        return new ClosableFrame("Main Menu", this.mainPanel);
    }
}

package com.slmn.patient_management.views;

import com.slmn.patient_management.controllers.AppointmentController;
import com.slmn.patient_management.core.Main;
import com.slmn.patient_management.io.SystemDatabase;
import com.slmn.patient_management.models.appointments.Appointment;
import com.slmn.patient_management.models.patient_services.DoctorReport;
import com.slmn.patient_management.models.users.Doctor;
import com.slmn.patient_management.models.users.User;
import com.slmn.patient_management.views.structures.SubFrame;
import com.slmn.patient_management.views.structures.SwitchableFrame;
import com.slmn.patient_management.views.structures.ViewWithFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserAppointmentView extends ViewWithFrame {
    private JPanel mainPanel;
    private JTable tblAppointments;
    private JButton btnNotes;
    private JPanel pnlDoctorControls;
    private JButton btnPrescription;

    private ViewWithFrame returnView;
    private User user;
    private AppointmentController controller = new AppointmentController();

    private Appointment selectedInput = null;

    private boolean isDoctor() {
        return user.isDoctor();
    }

    public UserAppointmentView(ViewWithFrame returnView, User targetUser) {
        this.returnView = returnView;
        this.user = targetUser;

        pnlDoctorControls.setVisible(isDoctor());
        updateTable();


        btnPrescription.addActionListener(z -> {
            System.out.println("Prescription clicked");

            if (selectedInput == null) return;
            controller.routeAppointmentPrescription(selectedInput);
        });
        btnNotes.addActionListener(z -> {

            if (selectedInput == null) return;
            controller.routeAppointmentNotes(selectedInput);
        });
    }


    private ArrayList<Appointment> getAppointments() {
        ArrayList<Appointment> output = new ArrayList<>();
        for (Appointment appointment: SystemDatabase.connect().appointments) {

            if (user.isDoctor() && appointment.getDoctor().equals(user)) output.add(appointment);
            if (user.isPatient() && appointment.getPatient().equals(user)) output.add(appointment);
            
        }
        return output;
    }

    private void updateTable() {
        DefaultTableModel model = new DefaultTableModel() {

            private String[] titles = new String[]{(isDoctor() ? "Patient" : "Doctor"), "Date", "Time"};

            @Override
            public boolean isCellEditable(int row, int column) { return false; }

            @Override
            public String getColumnName(int column) {
                return this.titles[column];
            }
        };




        /*
         * Doctor/Patient | Date | Time
         * */
        model.setColumnCount(3);
        model.setRowCount(getAppointments().size());

        for (int i = 0; i < getAppointments().size(); i++) {
            Appointment appointment = getAppointments().get(i);
            if (isDoctor()) {
                // show patient
                model.setValueAt(String.format("ID %s: %s", appointment.getPatient().getID(), appointment.getPatient().getFullName()), i, 0);
            } else {
                // show doctor
                model.setValueAt(String.format("ID %s: Dr %s", appointment.getDoctor().getID(), appointment.getDoctor().getSurname()), i, 0);
            }
            model.setValueAt(appointment.getDate(), i, 1);
            model.setValueAt(appointment.getTimeSlot(), i, 2);
        }



        tblAppointments.setModel(model);
        tblAppointments.getColumnModel().getColumn(0).setPreferredWidth(200);

        if (isDoctor()) {
            // No need to add this event listener when the patients can't use them
            tblAppointments.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) { // final event in chain
                    selectedInput = getAppointments().get(tblAppointments.getSelectedRow());
                    btnNotes.setEnabled(selectedInput != null);
                    btnPrescription.setEnabled(selectedInput != null);
                }
            });
        }
    }

    @Override
    public SwitchableFrame getFrame() {
        System.out.println("Framing Appt viewer");
        return new SubFrame("Appointment Viewer", this.mainPanel, this.returnView);
    }
}

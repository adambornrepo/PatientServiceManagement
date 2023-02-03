package dataaccess.concretes;

import core.enums.Departments;
import core.exceptions.IllegalDataProcessException;
import dataaccess.abstracts.DataProcessing;
import entities.concretes.Appointment;
import entities.concretes.Doctor;
import entities.concretes.Patient;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

public class AppointmentDataAccess implements DataProcessing {
    LinkedList<Doctor> doctorList;
    LinkedList<Patient> patientList;
    HashMap<String, HashMap<String, TreeSet<Appointment>>> allDepartments;

    public AppointmentDataAccess(LinkedList<Doctor> doctorList, LinkedList<Patient> patientList, HashMap<String, HashMap<String, TreeSet<Appointment>>> allDepartments) {
        this.doctorList = doctorList;
        this.patientList = patientList;
        this.allDepartments = allDepartments;
    }

    @Override
    public void read() {
        throw new IllegalDataProcessException("\t\t Data reading cannot be done from this section");
    }

    @Override
    public void write() {
        throw new IllegalDataProcessException("\t\t Empty write operation is not accepted");
    }

    public void makeDepartment(Departments department) {
        try {
            File file = new File(GATEWAY + "\\appointmentqueues\\" + department);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeDoctorFile(Departments department, String doctorId) {
        try {
            File file = new File(GATEWAY + "\\appointmentqueues\\" + department + "\\" + doctorId);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void takeAppQueueInfo(String department, Doctor doctor, TreeSet<Appointment> queue) {

        try {
            File file = new File(GATEWAY + "\\appointmentqueues\\" + department + "\\" + doctor.getHospitalId());
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String appointment = null;
                while ((appointment = br.readLine()) != null) {
                    String[] param = appointment.split(" ");
                    Patient found = patientList.get(patientList.indexOf(new Patient(Long.parseLong(param[0]))));
                    Appointment addQueue = new Appointment(found, department, doctor, Boolean.parseBoolean(param[2]), param[3]);
                    addQueue.setDay(param[4] + " " + param[5]);
                    queue.add(addQueue);
                }
                br.close();
                fr.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void addQueue(String department, String doctorId, TreeSet<Appointment> queue) {

        try {
            File file = new File(GATEWAY + "\\appointmentqueues\\" + department + "\\" + doctorId);
            FileWriter fr = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fr);
            for (Appointment appointment : queue) {
                String info = appointment.getPatient().getIdNum() + " " + appointment.getDepartment() + " " + appointment.isUrgent() + " " + appointment.getSymptom() + " " + appointment.getDay();
                br.write(info + "\n");
                br.flush();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void writeDailyLog(String department, String doctorId, Appointment appointment) {
        try {
            File file = new File(GATEWAY + "\\annuallog\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            String log = appointment.getDay() + " " + appointment.getPatient() + " " + department + " " + doctorId + " " +
                    appointment.isUrgent() + " " + appointment.getSymptom();
            br.write(log);
            br.newLine();
            br.flush();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Appointment serializable
    public void write(Appointment appointment) {
        try {
            File file = new File(GATEWAY + "\\medicalhistories\\" + appointment.getPatient().getIdNum());
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(appointment.toString());
            br.newLine();
            br.flush();
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}

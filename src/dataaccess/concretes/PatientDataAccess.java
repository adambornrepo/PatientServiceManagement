package dataaccess.concretes;

import core.abstracts.Colorable;
import dataaccess.abstracts.DataProcessing;
import entities.concretes.Patient;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class PatientDataAccess implements DataProcessing, Colorable {
    LinkedList<Patient> patientList;

    public PatientDataAccess(LinkedList<Patient> patientList) {
        this.patientList = patientList;
    }

    //Appointment serializable
    @Override
    public void read() {
        try {
            File file = new File(GATEWAY + "\\personaldata\\PatientData");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String patients = null;
            while ((patients = br.readLine()) != null) {
                String[] param = patients.split(" ");
                Patient patient = new Patient(Long.parseLong(param[0]), param[1], param[2], LocalDate.parse(param[3]), Integer.parseInt(param[4]), param[5]);
                patientList.add(patient);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void write() {
        try {
            File file = new File(GATEWAY + "\\personaldata\\PatientData");
            FileWriter fr = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fr);
            for (Patient patient : patientList) {
                br.write(patient.toString() + "\n");
                br.flush();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createMedicalHistory(Long patientId) {
        try {
            File file = new File(GATEWAY + "\\medicalhistories\\" + patientId);
            if (!file.exists()) file.createNewFile();
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
    }

    public void printMedicalHistory(Long patientId) {
        try {
            File file = new File(GATEWAY + "\\medicalhistories\\" + patientId);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String medicalHistory = null;
            boolean modifier = true;

            while ((medicalHistory = br.readLine()) != null) {
                String[] columns = medicalHistory.split(" ");
                String color = modifier ? WHITE : YELLOW;
                System.out.printf("\t\t|" + color + " %-11s %-9s %-12s %-25s %-7s %-49s|\n", columns[0], columns[1], columns[2], columns[3], columns[4], columns[5] + RESET);
                modifier ^= true;
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}

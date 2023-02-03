package dataaccess.concretes;

import dataaccess.abstracts.DataProcessing;
import entities.concretes.Doctor;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DoctorDataAccess implements DataProcessing {
    LinkedList<Doctor> doctorList;

    public DoctorDataAccess(LinkedList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    //Appointment serializable
    @Override
    public void read() {
        try {
            File file = new File(GATEWAY + "\\personaldata\\DoctorData");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String doctors = null;
            while ((doctors = br.readLine()) != null) {
                String[] param = doctors.split(" ");
                Doctor doctor = new Doctor(param[0], Long.parseLong(param[1]), param[2], param[3],
                        LocalDate.parse(param[4]), Integer.parseInt(param[5]), param[6]);
                doctor.setPhoneNumber(param[7]);
                doctor.setAddress(param[8]);
                doctor.setActive(Boolean.valueOf(param[9]));
                doctorList.add(doctor);
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
            File file = new File(GATEWAY + "\\personaldata\\DoctorData");
            FileWriter fr = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fr);
            for (Doctor doctor : doctorList) {
                br.write(doctor.toString() + "\n");
                br.flush();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}

package dataaccess.concretes;

import application.concretes.Runner;
import core.abstracts.Colorable;
import core.enums.Departments;
import entities.concretes.Appointment;
import entities.concretes.Doctor;
import entities.concretes.Patient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;


public class AppointmentQueue implements Colorable {
    LinkedList<Doctor> doctorList;
    LinkedList<Patient> patientList;
    protected HashMap<String, HashMap<String, TreeSet<Appointment>>> allDepartments;

    AppointmentDataAccess ada;

    public AppointmentQueue(LinkedList<Doctor> doctorList, LinkedList<Patient> patientList) {
        this.doctorList = doctorList;
        this.patientList = patientList;
        this.allDepartments = new HashMap<>();

        // FIXME: 3.02.2023 Every access means new service data
        for (int i = 0; i < Departments.size(); i++) {
            HashMap<String, TreeSet<Appointment>> department = new HashMap<>();
            for (Doctor doctor : doctorList) {
                if (doctor.getSpecialty().equalsIgnoreCase(Departments.of(i + 1).toString()) && doctor.isActive()) {
                    TreeSet<Appointment> queue = new TreeSet<>();
                    department.put(doctor.getHospitalId(), queue);
                }
            }
            allDepartments.put(Departments.of(i + 1).toString(), department);
        }
        this.ada = new AppointmentDataAccess(doctorList, patientList, allDepartments);

        startCheck();


    }


    public void addQueue(String department, String doctorId, Appointment appointment) {
        allDepartments.get(department).get(doctorId).add(appointment);
        ada.writeDailyLog(department, doctorId, appointment);
        printQueue(department, doctorId);
    }

    public void printQueue(String department, String doctorId) {
        Doctor found = doctorList.get(doctorList.indexOf(new Doctor(doctorId)));
        String header = found.getHospitalId() + " " + "Dr." + found.getName() + " " + found.getSurname();

        System.out.print("\t\t+" + "-".repeat(9) + " " + header + " " + "-".repeat(61 - header.length()) + "+\n");
        allDepartments.get(department).get(doctorId).
                forEach(t -> System.out.printf("\t\t| %-20s| %-12s| %-7s" + RESET + "| %-18s| %-7s " + RESET + "|\n",
                        t.getDay(),
                        "*".repeat(8) + t.getPatient().getIdNum() % 1000,
                        t.getPatient().getGender().equalsIgnoreCase("MALE") ? BLUE_BACK + BLACK_B + " MALE " : PURPLE_BACK + BLACK_B + "FEMALE",
                        t.getPatient().getName() + " " + t.getPatient().getSurname().charAt(0) + ".",
                        t.isUrgent() ? RED_BACK + "URGENT" : YELLOW_BACK + "COMMON"));
        System.out.println("\t\t+" + "-".repeat(72) + "+\n");

    }

    public void printDepartmentQueue(String department) {
        if (allDepartments.get(department).keySet().size() == 0) {
            System.out.println("\t\tACTIVE DOCTOR NOT FOUND IN THIS DEPARTMENT");
        }
        allDepartments.get(department).keySet().forEach(t -> printQueue(department, t));
    }

    public void addPatientMedicalHistory(Appointment appointment) {
        ada.write(appointment);
    }

    private boolean startCheck() {
        boolean sameDate = ada.startCheck();
        return sameDate;
    }


}

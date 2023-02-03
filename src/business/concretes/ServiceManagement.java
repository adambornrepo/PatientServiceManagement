package business.concretes;

import business.abstracts.InfoService;
import core.abstracts.Colorable;
import core.enums.Departments;
import core.utilities.NumberInput;
import dataaccess.concretes.DoctorDataAccess;
import dataaccess.concretes.PatientDataAccess;
import dataaccess.concretes.AppointmentQueue;
import entities.concretes.Appointment;
import entities.concretes.Doctor;
import entities.concretes.Patient;

import java.util.Scanner;


public class ServiceManagement implements InfoService, Colorable {
    NumberInput numberInput = new NumberInput();
    Scanner inp = new Scanner(System.in);
    DoctorInfoManagement dim;
    PatientInfoManagement pim;
    DoctorDataAccess dda;
    PatientDataAccess pda;
    AppointmentQueue aQueue;

    public ServiceManagement() {
        dim = new DoctorInfoManagement();
        pim = new PatientInfoManagement();
        this.dda = new DoctorDataAccess(dim.doctorList);
        this.pda = new PatientDataAccess(pim.patientList);
        this.aQueue = new AppointmentQueue(dim.doctorList, pim.patientList);
    }

    @Override
    public void processMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(GREEN_B + "\t\t" + "|\t< 1 > NEW APPOINTMENT" + " ".repeat(8) + "|" + GREEN_B + "\t\t" + "|\t< 2 > CHECK APPOINTMENTS" + " ".repeat(5) + "|");
            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + RESET);

            System.out.println(WHITE_B + "\t\t" + "+" + "-".repeat(72) + "+");
            System.out.println(WHITE_B + "\t\t|" + " ".repeat(27) + "< 0 > SAVE & EXIT" + " ".repeat(28) + "|");
            System.out.println(WHITE_B + "\t\t" + "+" + "-".repeat(72) + "+" + RESET);

            System.out.print("\t\tSELECT : ");
            choice = numberInput.scanInt();

            switch (choice) {
                case 0:
                    System.out.println("\n\t\tRedirected to the Start Menu\n");
                    break;
                case 1:
                    add(numberInput.scanIdNum());
                    break;
                case 2:
                    Departments.toList();
                    System.out.print("\t\tSELECT : ");
                    String department = null;
                    try {
                        department = Departments.of(numberInput.scanInt()).toString();
                    } catch (Exception e) {
                        System.out.println("\t\t❌ " + e.getMessage());
                    }
                    if (department != null) listDepartments(department);
                    break;
                default:
                    System.out.println("\t\tInvalid Selection");
                    break;
            }
        }
    }

    @Override
    public void add(Long id) {

        Patient search = new Patient(id);
        if (pim.patientList.contains(search)) {
            search = pim.patientList.get(pim.patientList.indexOf(search));
            System.out.println("\t\t" + "+" + "-".repeat(9) + " PATIENT INFO " + "-".repeat(49) + "+");
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "ID Number", search.getIdNum());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Name", search.getName());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Surname", search.getSurname());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Age", search.getAge());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Gender", search.getGender());
            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");

        } else {
            System.out.println("\t\tPatient information not found\n\t\tNew patient registration :");
            pim.add(id);
            pda.write();
            search = pim.patientList.get(pim.patientList.indexOf(search));
        }

        String department = null;
        if (search.getAge() < 18) {
            department = Departments.of(3).toString();
        } else {

            Departments.toList();
            int departmentNo = -1;
            boolean inValidSelection = true;
            do {
                try {
                    System.out.printf("\t\t%-10s : ", "Department");
                    departmentNo = numberInput.scanInt();

                    if (departmentNo == 3) {
                        System.out.println("\t\t🔞 Adults cannot select Child Diseases. Try Again");
                        continue;
                    }

                    department = Departments.of(departmentNo).toString();


                    inValidSelection = search.getGender().equalsIgnoreCase("MALE") && departmentNo == 7;
                    if (inValidSelection) {
                        System.out.println("\t\t🤰 Men cannot select Gynecology. Try Again");
                    }
                } catch (Exception e) {
                    System.out.println("\t\t❌ " + e.getMessage());
                }
            } while (department == null || (search.getAge() > 17 && departmentNo == 3) || inValidSelection);

        }

        int activeDoctor = filterActive(department);

        if (activeDoctor != 0) {

            boolean notExist = true;
            Doctor found;
            do {

                System.out.print("\t\tHospital ID :");
                String selectedDoctor = inp.nextLine().trim().toUpperCase();
                found = new Doctor(selectedDoctor);
                if (dim.doctorList.contains(found)) {
                    found = dim.doctorList.get(dim.doctorList.indexOf(found));
                    notExist = false;
                } else {
                    System.out.println("\t\tNo doctor found with this ID number");
                }
            } while (notExist);

            int select;
            do {
                System.out.printf("\t\t%-10s<1> URGENT <2> COMMON : ", "Condition");
                select = numberInput.scanInt();
            } while (select != 1 && select != 2);
            boolean isUrgent = select == 1 ? true : false;

            System.out.print("\t\tWhat are the symptoms of the disease?\n\t\tDescribe : ");
            String symptoms = inp.nextLine().trim().toUpperCase().replace(" ", "_");
            symptoms = symptoms.length() == 0 ? "NO_INFORMATION" : symptoms;

            Appointment appointment = new Appointment(search, department, found, isUrgent, symptoms);

            aQueue.addQueue(department, found.getHospitalId(), appointment);
            aQueue.addPatientMedicalHistory(appointment);


        } else {
            System.out.println("\t\tWe are sorry that we cannot provide service");
        }

    }

    public int filterActive(String department) {
        int activeDoctor = 0;
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | %-10s |\n", "HOSPITAL ID", "NAME", "SURNAME", "SPECIALTY", "AGE", "STATUS");
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        for (Doctor doctor : dim.doctorList) {
            if (doctor.getSpecialty().equalsIgnoreCase(department) && doctor.isActive()) {
                System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | " + "%-15s" + RESET + " |\n",
                        doctor.getHospitalId(), doctor.getName(), doctor.getSurname(), doctor.getSpecialty(),
                        doctor.getAge(), doctor.isActive() ? GREEN_BACK + "ACTIVE" : YELLOW_BACK + "PASSIVE");
                activeDoctor++;
            }
        }
        if (activeDoctor == 0) {
            System.out.println("\t\t|" + " ".repeat(36) + "ACTIVE DOCTOR NOT FOUND IN THIS DEPARTMENT" + " ".repeat(37) + "|");
        }
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        return activeDoctor;
    }


    public void listDepartments(String department) {
        aQueue.printDepartmentQueue(department);
    }

}

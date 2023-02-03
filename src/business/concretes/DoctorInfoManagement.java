package business.concretes;

import business.abstracts.InfoService;
import core.abstracts.Colorable;
import core.enums.Departments;
import core.utils.IdMaker;
import core.utils.NumberInput;
import dataaccess.concretes.DoctorDataAccess;
import entities.concretes.Doctor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DoctorInfoManagement implements InfoService, Colorable {

    protected LinkedList<Doctor> doctorList = new LinkedList<>();
    NumberInput numberInput = new NumberInput();
    Scanner inp = new Scanner(System.in);
    DoctorDataAccess dda;
    IdMaker idMaker = new IdMaker();

    public DoctorInfoManagement() {
        dda = new DoctorDataAccess(doctorList);
        dda.read();

    }

    @Override
    public void processMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(GREEN_B + "\t\t" + "|\t< 1 > LIST ALL DOCTORS" + " ".repeat(7) + "|" + BLUE_B + "\t\t" + "|\t< 2 > UPDATE STATUS" + " ".repeat(10) + "|");
            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(GREEN_B + "\t\t" + "|\t< 3 > FILTER BY 'SPECIALTY'" + " ".repeat(2) + "|" + BLUE_B + "\t\t" + "|\t< 4 > NEW DOCTOR INFO" + " ".repeat(8) + "|");
            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+" + RESET);

            System.out.println(YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+" + RED_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(YELLOW_B + "\t\t" + "|\t< 5 > DETAILED DOCTOR INFO" + " ".repeat(3) + "|" + RED_B + "\t\t" + "|\t< 6 > DELETE DOCTOR INFO" + " ".repeat(5) + "|");
            System.out.println(YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+" + RED_B + "\t\t" + "+" + "-".repeat(32) + "+" + RESET);

            System.out.println(WHITE_B + "\t\t" + "+" + "-".repeat(72) + "+");
            System.out.println(WHITE_B + "\t\t|" + " ".repeat(27) + "< 0 > SAVE & EXIT" + " ".repeat(28) + "|");
            System.out.println(WHITE_B + "\t\t" + "+" + "-".repeat(72) + "+" + RESET);


            System.out.print("\t\tSELECT : ");
            choice = numberInput.scanInt();

            switch (choice) {
                case 0:
                    dda.write();
                    System.out.println("\n\t\tRedirected to the Start Menu\n");
                    break;
                case 1:
                    listAll();
                    break;
                case 2:
                    System.out.print("\t\tHospital ID : ");
                    String updateHosId = inp.nextLine().trim().toUpperCase();
                    update(updateHosId);
                    break;
                case 3:
                    Departments.toList();
                    System.out.print("\t\tSpecialty : ");
                    String department = null;
                    try {
                        department = Departments.of(numberInput.scanInt()).toString();
                    } catch (Exception e) {
                        System.out.println("\t\t❌ " + e.getMessage());
                    }
                    if (department != null) filter(department);
                    break;
                case 4:
                    add(numberInput.scanIdNum());
                    break;
                case 5:
                    System.out.print("\t\tHospital ID : ");
                    String doctorId = inp.nextLine().trim().toUpperCase();
                    getInfo(doctorId);
                    break;
                case 6:
                    delete();
                    break;
                default:
                    System.out.println("\t\tInvalid Selection");
                    break;
            }
        }
    }

    public void listAll() {
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | %-10s |\n", "HOSPITAL ID", "NAME", "SURNAME", "SPECIALTY", "AGE", "STATUS");
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        for (Doctor doctor : this.doctorList) {
            System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | " + "%-15s" + RESET + " |\n",
                    doctor.getHospitalId(), doctor.getName(), doctor.getSurname(), doctor.getSpecialty(), doctor.getAge(), doctor.isActive() ? GREEN_BACK + "ACTIVE" : YELLOW_BACK + "PASSIVE");
        }
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
    }


    public void getInfo(String doctorId) {
        if (doctorList.contains(new Doctor(doctorId))) {

            Doctor doctor = doctorList.get(doctorList.indexOf(new Doctor(doctorId)));

            System.out.println("\t\t" + "+" + "-".repeat(9) + " DOCTOR  INFO " + "-".repeat(49) + "+");
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Hospital ID", doctor.getHospitalId());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "ID Number", doctor.getIdNum());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Name", doctor.getName());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Surname", doctor.getSurname());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Specialty", doctor.getSpecialty());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Birthdate", doctor.getBirthdate().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Age", doctor.getAge());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Active", doctor.isActive());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Phone Number", doctor.getPhoneNumber());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Address", doctor.getAddress());
            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");
        } else {
            System.out.println("\t\tDoctor information not found");
        }

    }

    public void filter(String filter) {
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | %-10s |\n", "HOSPITAL ID", "NAME", "SURNAME", "SPECIALTY", "AGE", "STATUS");
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        for (Doctor doctor : this.doctorList) {
            if (doctor.getSpecialty().equalsIgnoreCase(filter)) {
                System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | " + "%-15s" + RESET + " |\n",
                        doctor.getHospitalId(), doctor.getName(), doctor.getSurname(), doctor.getSpecialty(), doctor.getAge(), doctor.isActive() ? GREEN_BACK + "ACTIVE" : YELLOW_BACK + "PASSIVE");
            }
        }
        System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
    }

    public void update(String doctorId) {

        if (doctorList.contains(new Doctor(doctorId))) {

            Doctor found = doctorList.get(doctorList.indexOf(new Doctor(doctorId)));

            System.out.println("\t\t" + "+" + "-".repeat(9) + " DOCTOR  INFO " + "-".repeat(49) + "+");
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Hospital ID", found.getHospitalId());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Name", found.getName());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Surname", found.getSurname());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Active", found.isActive());
            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");

            System.out.println("\t\tIs the doctor " + (found.isActive() == true ? "passive" : "active") + " now? " + "< 1 > Yes < 0 > No");
            System.out.print("\t\tSELECT : ");
            int selection = numberInput.scanInt();
            if (selection == 1) {
                boolean status = found.isActive();
                status ^= true;
                found.setActive(status);
                System.out.println("\t\tStatus updated to " + (found.isActive() == true ? "\"ACTIVE\"" : "\"PASSIVE\""));
            } else {
                System.out.println("\t\tStatus not updated");
            }

            System.out.println("\t\tWant to update the doctor's phone number and address information? " + "< 1 > Yes < 0 > No");
            System.out.print("\t\tSELECT : ");
            selection = numberInput.scanInt();
            if (selection == 1) {
                System.out.println("\t\t" + "+" + "-".repeat(72) + "+");
                System.out.printf("\t\t|\t%-13s: %-54s|\n", "Phone Number", found.getPhoneNumber());
                System.out.printf("\t\t|\t%-13s: %-54s|\n", "Address", found.getAddress());
                System.out.println("\t\t" + "+" + "-".repeat(72) + "+");

                System.out.print("\t\tPhone Number : ");
                long phoneNumber = numberInput.scanLong();
                //To be set by country
                found.setPhoneNumber("+1-" + phoneNumber);
                System.out.print("\t\tAddress : ");
                String address = inp.nextLine().trim().toUpperCase().replace(" ", "_");
                found.setAddress(address);
                System.out.println("\t\tAddress and phone number information updated");
            } else {
                System.out.println("\t\tAddress and phone number information not updated");
            }

        } else {
            System.out.println("\t\tDoctor information not found");
        }
    }

    @Override
    public void add(Long id) {

        Doctor search = new Doctor(id);

        if (doctorList.contains(search)) {

            Doctor doctor = doctorList.get(doctorList.indexOf(search));

            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
            System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | %-10s |\n", "HOSPITAL ID", "NAME", "SURNAME", "SPECIALTY", "AGE", "STATUS");
            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");


            System.out.printf("\t\t|\t%-13s | %-20s | %-20s | %-25s | %-8s | " + "%-15s" + RESET + " |\n",
                    doctor.getHospitalId(), doctor.getName(), doctor.getSurname(), doctor.getSpecialty(), doctor.getAge(), doctor.isActive() ? GREEN_BACK + "ACTIVE" : YELLOW_BACK + "PASSIVE");
            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");

            System.out.println("\t\tThere is already a doctor on this ID number.\n\t\tYou cannot add doctor with the same information");

        } else {

            System.out.printf("\t\t%-10s : ", "Name");
            String name = inp.nextLine().trim().replaceAll(" ", "_").toUpperCase();

            System.out.printf("\t\t%-10s : ", "Surname");
            String surname = inp.nextLine().trim().replaceAll(" ", "_").toUpperCase();

            LocalDate birthdate = numberInput.scanDate();
            int age = numberInput.getAge(birthdate);

            Departments.toList();
            String specialty = null;
            do {
                try {
                    System.out.printf("\t\t%-10s : ", "Specialty");
                    specialty = Departments.of(numberInput.scanInt()).toString();
                } catch (Exception e) {
                    System.out.println("\t\t❌ " + e.getMessage());
                }
            } while (specialty == null);

            String peek = doctorList.peekLast() == null ? null : doctorList.peekLast().getHospitalId();
            String hospitalId = idMaker.make(specialty, id) + "-" + (peek == null ? 1 : Integer.parseInt(peek.substring(peek.indexOf("-") + 1)) + 1);

            Doctor doctor = new Doctor(hospitalId, id, name, surname, birthdate, age, specialty);

            this.doctorList.add(doctor);
            System.out.println("\t\tDoctor information has been added");
        }

    }

    public void delete() {
        boolean isExist = true;
        System.out.print("\t\tHospital ID : ");
        String doctorId = inp.nextLine().trim().toUpperCase();

        if (doctorList.contains(new Doctor(doctorId))) {
            Doctor doctor = doctorList.get(doctorList.indexOf(new Doctor(doctorId)));
            this.doctorList.remove(doctor);
            System.out.println("\t\tDoctor information was deleted");
        } else {
            System.out.println("\t\tDoctor information not found");
        }
    }

}
package business.concretes;

import business.abstracts.InfoService;
import core.abstracts.Colorable;
import core.utils.NumberInput;
import dataaccess.concretes.PatientDataAccess;
import entities.concretes.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class PatientInfoManagement implements InfoService, Colorable {
    protected LinkedList<Patient> patientList = new LinkedList<>();
    NumberInput numberInput = new NumberInput();
    Scanner inp = new Scanner(System.in);
    PatientDataAccess pda;

    public PatientInfoManagement() {
        pda = new PatientDataAccess(patientList);
        pda.read();
    }

    @Override
    public void processMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println();

            System.out.println(BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+" + YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(BLUE_B + "\t\t" + "|\t\t< 1 > ADD PATIENT" + " ".repeat(8) + "|" + YELLOW_B + "\t\t" + "|\t\t< 2 > PATIENT INFO" + " ".repeat(7) + "|");
            System.out.println(BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+" + YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+");

            System.out.println(YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+" + WHITE_B + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(YELLOW_B + "\t\t" + "|\t\t< 3 > MEDICAL HISTORY" + " ".repeat(4) + "|" + WHITE_B + "\t\t" + "|\t\t< 0 > SAVE & EXIT" + " ".repeat(8) + "|");
            System.out.println(YELLOW_B + "\t\t" + "+" + "-".repeat(32) + "+" + WHITE_B + "\t\t" + "+" + "-".repeat(32) + "+" + RESET);

            System.out.print("\t\tSELECT : ");
            choice = numberInput.scanInt();

            switch (choice) {
                case 0:
                    pda.write();
                    System.out.println("\n\t\tRedirected to the Start Menu\n");
                    break;
                case 1:
                    add(numberInput.scanIdNum());
                    break;
                case 2:
                    getInfo(numberInput.scanIdNum());
                    break;
                case 3:
                    getMedicalHistory(numberInput.scanIdNum());
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
        if (patientList.contains(search)) {
            Patient patient = patientList.get(patientList.indexOf(search));

            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
            System.out.printf("\t\t|\t%-20s | %-25s | %-25s | %-19s | %-10s |\n", "ID NUMBER", "NAME", "SURNAME", "AGE", "GENDER");
            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");

            System.out.printf("\t\t|\t%-20s | %-25s | %-25s | %-19s | %-10s |\n",
                    patient.getIdNum(), patient.getName(), patient.getSurname(), patient.getAge(), patient.getGender());
            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");

            System.out.println("\t\tThere is already a patient on this ID number.\n\t\tYou cannot add patient with the same information");

        } else {
            System.out.printf("\t\t%-10s : ", "Name");
            String name = inp.nextLine().replaceAll(" ", "_").toUpperCase();

            System.out.printf("\t\t%-10s : ", "Surname");
            String surname = inp.nextLine().replaceAll(" ", "_").toUpperCase();

            LocalDate birthdate = numberInput.scanDate();

            int age = numberInput.getAge(birthdate);

            int select;
            do {
                System.out.printf("\t\t%-10s<1> Male <2> Female : ", "Gender");
                select = numberInput.scanInt();
            } while (select != 1 && select != 2);
            String gender = select == 1 ? "MALE" : "FEMALE";

            Patient patient = new Patient(id, name, surname, birthdate, age, gender);
            this.patientList.add(patient);
            pda.createMedicalHistory(patient.getIdNum());
            System.out.println("\t\tPatient information has been added");
        }

    }


    public void getInfo(Long id) {

        Patient search = new Patient(id);
        if (patientList.contains(search)) {
            Patient patient = patientList.get(patientList.indexOf(search));
            System.out.println("\t\t" + "+" + "-".repeat(9) + " PATIENT INFO " + "-".repeat(49) + "+");
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "ID Number", patient.getIdNum());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Name", patient.getName());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Surname", patient.getSurname());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Birthdate", patient.getBirthdate().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Age", patient.getAge());
            System.out.printf("\t\t|\t%-13s: %-54s|\n", "Gender", patient.getGender());
            System.out.println("\t\t" + "+" + "-".repeat(72) + "+");
        } else {
            System.out.println("\t\tPatient information not found");
        }

    }

    public void getMedicalHistory(Long id) {

        Patient search = new Patient(id);
        if (patientList.contains(search)) {
            Patient found = patientList.get(patientList.indexOf(search));

            String patientInfoHeader = "  ID: " + found.getIdNum() + "  " + found.getName() + " " + found.getSurname() + "  AGE: " + found.getAge() + "  " + found.getGender() + "  ";
            System.out.println("\t\t" + " ".repeat(10) + "+" + "-".repeat(patientInfoHeader.length()) + "+");
            System.out.print("\t\t+" + "-".repeat(9) + "|" + RED_B + patientInfoHeader + RESET + "|" + "-".repeat(104 - patientInfoHeader.length()) + "+\n");
            pda.printMedicalHistory(id);

            System.out.println("\t\t" + "+" + "-".repeat(115) + "+");
        } else {
            System.out.println("\t\tPatient information not found");
        }

    }


}

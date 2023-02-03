package application.concretes;

import business.abstracts.InfoService;
import business.concretes.DoctorInfoManagement;
import business.concretes.PatientInfoManagement;
import business.concretes.ServiceManagement;
import core.abstracts.Colorable;
import core.utilities.NumberInput;

public class Runner implements Colorable {


    public static void main(String[] args) {
        start();
    }

    public static void start() {
        NumberInput intInput = new NumberInput();

        int select = -1;
        while (select != 0) {

            System.out.println(BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+");
            System.out.println(BLUE_B + "\t\t" + "|\t\t< 1 > DOCTORS" + " ".repeat(12) + "|" + "\t\t" + "|\t\t< 2 > PATIENTS" + " ".repeat(11) + "|" + WHITE_B + "\t\t🟩 : USABLE");
            System.out.println(BLUE_B + "\t\t" + "+" + "-".repeat(32) + "+" + "\t\t" + "+" + "-".repeat(32) + "+" + WHITE_B + "\t\t🟨 : PERSONAL DATA");

            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + WHITE_B + "\t\t" + "+" + "-".repeat(32) + "+" + "\t\t🟦 : AUTHORIZED");
            System.out.println(GREEN_B + "\t\t" + "|\t\t< 3 > SERVICES" + " ".repeat(11) + "|" + WHITE_B + "\t\t" + "|\t\t" + RED_B + "< 0 > EXIT" + WHITE_B + " ".repeat(15) + "|" + "\t\t🟥 : RISKY");
            System.out.println(GREEN_B + "\t\t" + "+" + "-".repeat(32) + "+" + WHITE_B + "\t\t" + "+" + "-".repeat(32) + "+" + RESET);

            System.out.print("\t\tSELECT : ");
            String arrow = " ".repeat(17) + "|\n" + " ".repeat(17) + "↓";
            select = intInput.scanInt();
            InfoService pis;
            switch (select) {
                case 1:
                    System.out.print(arrow);
                    pis = new DoctorInfoManagement();
                    pis.processMenu();
                    break;
                case 2:
                    System.out.print(arrow);
                    pis = new PatientInfoManagement();
                    pis.processMenu();
                    break;
                case 3:
                    System.out.print(arrow);
                    pis = new ServiceManagement();
                    pis.processMenu();
                    break;
                case 0:
                    System.out.println(WHITE_B + "\n\t\t🏥 HEALTHY DAYS" + RESET);
                    break;
                default:
                    System.out.println("\t\tInvalid Selection. Try Again");
                    break;
            }
        }
    }
}


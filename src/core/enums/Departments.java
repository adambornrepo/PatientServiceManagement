package core.enums;

import core.abstracts.Colorable;
import core.exceptions.DepartmentIndexOutOfBoundsException;

public enum Departments implements Colorable {
    ALLERGY_DISEASES,
    DERMATOLOGY,
    CHILD_DISEASES,
    GENERAL_SURGERY,
    EYE_DISEASES,
    INTERNAL_MEDICINE,
    GYNECOLOGY,
    CARDIOLOGY,
    EAR_NOSE_THROAT,
    NEUROLOGY,
    ORTHOPEDICS,
    UROLOGY;

    private static final Departments[] ENUMS = Departments.values();

    public static Departments of(int department) {
        if (department < 1 || department > ENUMS.length) {
            throw new DepartmentIndexOutOfBoundsException("Invalid value for Department: " + department);
        }
        return ENUMS[department - 1];
    }

    public static void toList() {
        System.out.print(WHITE_B + "\t\t+" + "-".repeat(115) + "+\n\t\t");
        for (int i = 0; i < ENUMS.length; i++) {
            if (i == 6) {
                System.out.printf("|" + YELLOW_B + " < %2d >" + PURPLE_B + " %-20s" + WHITE_B, (i + 1), ENUMS[i]);
            } else if (i == 2) {
                System.out.printf("|" + YELLOW_B + " < %2d >" + BLUE_B + " %-20s" + WHITE_B, (i + 1), ENUMS[i]);
            } else {
                System.out.printf("|" + YELLOW_B + " < %2d >" + WHITE_B + " %-20s" + WHITE_B, (i + 1), ENUMS[i]);
            }
            if ((i + 1) % 4 == 0) System.out.print("|\n\t\t");
        }
        System.out.println("+" + "-".repeat(115) + "+" + RESET);
    }

    public static int size() {
        return ENUMS.length;
    }

}

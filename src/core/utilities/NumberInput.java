package core.utilities;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class NumberInput {

    public int scanInt() {
        int result = Integer.MIN_VALUE;
        Scanner scanner = new Scanner(System.in);
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("\t\tThat's not a number!\n\t\tTry Again : ");
                scanner.next();
            }
            result = scanner.nextInt();
        } while (result == Integer.MIN_VALUE);
        return result;
    }

    public long scanLong() {
        long result = Long.MIN_VALUE;
        Scanner scanner = new Scanner(System.in);
        do {
            while (!scanner.hasNextLong()) {
                System.out.print("\t\tThat's not a number!\n\t\tTry Again : ");
                scanner.next();
            }
            result = scanner.nextLong();
        } while (result == Long.MIN_VALUE);
        return result;
    }

    public long scanIdNum() {
        NumberInput numberInput = new NumberInput();
        boolean isValid;
        long idNum = 0;
        do {
            System.out.print("\t\tID Number: ");
            idNum = numberInput.scanLong();
            long clone = idNum;
            int digit = (int) Math.log10(clone) + 1;
            int lastDigit = (int) (clone % 10);
            int controlDigit = (int) ((clone / 10) % 10);
            int firstDigit = (int) (clone / 10000000000L);
            int odd = 0;
            int even = 0;
            boolean sw = false;
            clone /= 100;
            while (clone > 0) {
                boolean evenDigit = ((int) Math.log10(clone) + 1) % 2 == 0;
                if (evenDigit) even += clone % 10;
                else odd += clone % 10;
                clone /= 10;
            }
            // FIXME: 3.02.2023 This part should be set according to country's id standards
            boolean control1 = ((odd * 7) + (even * 9)) % 10 == controlDigit;
            boolean control2 = ((odd * 7) - even) % 10 == controlDigit;
            boolean control3 = (odd + even + controlDigit) % 10 == lastDigit;
            boolean control4 = (odd * 8) % 10 == lastDigit;
            boolean control5 = firstDigit != 0;
            isValid = control1 && control2 && control3 && control4 && control5;
            if (!isValid) System.out.println("\t\tInvalid ID Number. Try Again");
        } while (!isValid);
        return idNum;
    }

    public LocalDate scanDate() {
        Scanner scanner = new Scanner(System.in);
        String[] param;
        LocalDate birthday;
        boolean year;
        do {
            boolean matches;
            do {
                System.out.print("\t\tBirthday (DD MM YYYY): ");
                String s = scanner.nextLine().trim();
                matches = s.matches("^[0-9]{1,2}\\s+[0-9]{1,2}\\s+[0-9]{4}$");
                param = s.split("\\s+");

                if (!matches) {
                    System.out.println("\t\tInvalid Birthday. Try Again");
                }

            } while (!matches);

            year = Integer.parseInt(param[2]) < LocalDate.now().getYear() - 120 || Integer.parseInt(param[2]) > LocalDate.now().getYear();

            try {
                if (year) throw new Exception("Invalid value for year (valid values 120 years ago from today - now)");
                birthday = LocalDate.of(Integer.parseInt(param[2]), Integer.parseInt(param[1]), Integer.parseInt(param[0]));
            } catch (Exception e) {
                System.out.println("\t\t📅 ERROR:" + e.getMessage() + " Try Again");
                return scanDate();
            }
        } while (year);
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd_MM_yyyy");
//        String result = birthday.format(dateFormat);

        return birthday;
    }

    public int getAge(LocalDate birthday) {
        int age = (int) ChronoUnit.YEARS.between(birthday, LocalDate.now());
        return age;
    }

}


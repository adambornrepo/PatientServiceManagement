package dataaccess.abstracts;

import core.exceptions.IllegalDataProcessException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class StartProcessing implements DataProcessing{

    // FIXME: 3.02.2023 This section is not currently in use
    protected boolean startCheck() {
        boolean sameDay = true;
        try {
            File file = new File(GATEWAY + "\\annuallog\\" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            if (file.exists()) {
                sameDay = true;
            } else {
                sameDay = false;
                FileWriter fr = new FileWriter(file);
                BufferedWriter br = new BufferedWriter(fr);
                String logHeader = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " DATE LOGS = " +
                        "DATE::HOUR::PATIENT_ID::NAME::SURNAME::BIRTHDATE::GENDER::DEPARTMENT::DOCTOR_H_ID::URGENCY::SYMPTOMS";
                br.write(logHeader);
                br.newLine();
                br.flush();
                br.close();
                fr.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sameDay;
    }

    @Override
    public void read() {
        throw new IllegalDataProcessException("\t\t Data reading cannot be done from this section");
    }

    @Override
    public void write() {
        throw new IllegalDataProcessException("\t\t Empty write operation is not accepted");
    }
}

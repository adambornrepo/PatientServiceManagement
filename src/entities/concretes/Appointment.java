package entities.concretes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Comparable<Appointment>, Serializable {
    private String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    private Doctor doctor;
    private Patient patient;
    private String department;
    private boolean isUrgent;
    private String symptom;

    public Appointment(Patient patient, String department, Doctor doctor, boolean isUrgent, String symptom) {
        this.patient = patient;
        this.department = department;
        this.doctor = doctor;
        this.isUrgent = isUrgent;
        this.symptom = symptom;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        this.isUrgent = urgent;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return this.day + " " + this.patient.getIdNum() + " " + this.department + " " + (this.isUrgent == true ? "URGENT" : "COMMON") + " " + this.symptom;
    }

    @Override
    public int compareTo(Appointment o) {
        if (Boolean.compare(this.isUrgent, o.isUrgent) != 0) {
            return Boolean.compare(o.isUrgent, this.isUrgent);
        } else {
            return this.day.compareTo(o.day);
        }
    }

}

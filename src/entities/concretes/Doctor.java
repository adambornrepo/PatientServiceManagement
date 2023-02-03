package entities.concretes;

import entities.abstracts.PerInfo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Doctor extends PerInfo implements Serializable {
    private String hospitalId;
    private String specialty;
    private boolean isActive;
    private String phoneNumber = "NULL";
    private String address = "NULL";

    public Doctor(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Doctor(long idNum) {
        super(idNum);
    }

    public Doctor(String hospitalId, long idNum, String name, String surname, LocalDate birthday, int age, String specialty) {
        super(idNum, name, surname, birthday, age);
        this.specialty = specialty;
        this.hospitalId = hospitalId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor doctor)) return false;
        return Objects.equals(getHospitalId(), doctor.getHospitalId()) || this.getIdNum() == doctor.getIdNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHospitalId(), getSpecialty(), isActive());
    }

    @Override
    public String toString() {
        return this.getHospitalId() + " " + super.getIdNum() + " " + super.getName() + " " +
                super.getSurname() + " " + super.getBirthdate().toString() + " " + super.getAge() + " " +
                this.specialty + " " + this.phoneNumber + " " + this.address + " " + this.isActive;
    }
}

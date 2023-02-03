package entities.concretes;

import entities.abstracts.PerInfo;

import java.io.Serializable;
import java.time.LocalDate;


public class Patient extends PerInfo implements Comparable<Patient>, Serializable {
    private String gender;

    public Patient(long idNum) {
        super(idNum);
    }

    public Patient(long idNum, String name, String surname, LocalDate birthday, int age, String gender) {
        super(idNum, name, surname, birthday, age);
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {//It's not about sex change
        this.gender = gender;//Just had to use it somewhere in my code
    }

    @Override
    public String toString() {
        return super.getIdNum() + " " + super.getName() + " " + super.getSurname() + " " + super.getBirthdate() + " " + super.getAge() + " " + this.getGender();
    }

    @Override
    public int compareTo(Patient o) {
        return this.getAge() - o.getAge();
    }
}

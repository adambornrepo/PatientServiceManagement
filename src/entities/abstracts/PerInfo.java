package entities.abstracts;

import java.time.LocalDate;

public abstract class PerInfo {
    private long idNum;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private int age;

    public PerInfo() {
    }

    public PerInfo(long idNum) {
        this.idNum = idNum;
    }

    public PerInfo(long idNum, String name, String surname, LocalDate birthdate, int age) {
        this.idNum = idNum;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.age = age;
    }

    public long getIdNum() {
        return idNum;
    }

    public void setIdNum(long idNum) {
        this.idNum = idNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerInfo that)) return false;
        return getIdNum() == that.getIdNum();
    }

}

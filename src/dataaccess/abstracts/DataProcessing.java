package dataaccess.abstracts;

public interface DataProcessing {
    String GATEWAY = "D:\\javaEgitim\\PatientServiceManagement\\src\\dataaccess\\data";
    // FIXME: 31.01.2023 Give me the right absolute path of "data" package

    void read();

    void write();
}

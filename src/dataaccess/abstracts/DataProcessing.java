package dataaccess.abstracts;

public interface DataProcessing {
    String GATEWAY = "src/dataaccess/data";

    void read();

    void write();
}

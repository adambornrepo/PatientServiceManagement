package core.utilities;

public class IdMaker {
    public String make(String specialty, long idNum) {
        return specialty.substring(0, 3).toUpperCase() + idNum % 100;
    }
}

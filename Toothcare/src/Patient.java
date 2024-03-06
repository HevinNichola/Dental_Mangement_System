import java.time.LocalDate;

public interface Patient {
    String getFirstName();
    String getLastName();
    LocalDate getDateOfBirth();
    String getNic();
    String getMobileNumber();
    String getAddress();
    public String getFullName();
}




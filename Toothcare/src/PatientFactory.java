import java.time.LocalDate;

public interface PatientFactory {
    Patient createPatient(String firstName, String lastName, LocalDate dateOfBirth, String nic,
                          String mobileNumber, String address);
}

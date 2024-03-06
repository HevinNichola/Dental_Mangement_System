import java.time.LocalDate;

public class PatientFactoryImpl implements PatientFactory {
    @Override
    public Patient createPatient(String firstName, String lastName, LocalDate dateOfBirth, String nic,
                                 String mobileNumber, String address) {
        return new PatientImpl(firstName, lastName, dateOfBirth, nic, mobileNumber, address);
    }
}


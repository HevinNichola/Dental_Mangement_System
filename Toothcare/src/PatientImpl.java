import java.time.LocalDate;

public class PatientImpl implements Patient {
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String nic;
    private final String mobileNumber;
    private final String address;

    public PatientImpl(String firstName, String lastName, LocalDate dateOfBirth, String nic,
                   String mobileNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nic = nic;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getNic() {
        return nic;
    }

    @Override
    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String getAddress() {
        return address;
    }
    
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}

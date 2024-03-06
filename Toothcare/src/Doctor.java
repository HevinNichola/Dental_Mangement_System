import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String firstName;
    private String lastName;
    private String specialization;
    private int honsCompletionYear;
    
    private static final List<Doctor> defaultDoctors;
    
    static{
        defaultDoctors = new ArrayList<>();
        defaultDoctors.add(new Doctor("John","Doe","Dentist", 2000));
        defaultDoctors.add(new Doctor("Jane","Smith","Orthodontist", 2005));
    }
    
    public Doctor(String firstName, String lastName, String specialization, int honsCompletionYear){
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.honsCompletionYear = honsCompletionYear;
    }
    
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
    
    public boolean isAvailableOnDay(DayOfWeek day){
        switch(day){
            case MONDAY:
            case WEDNESDAY:
                return getFullName().equals("Joe Doe");
                
            case SATURDAY:
            case SUNDAY:
                return getFullName().equals("Jane Smith");
                
            default:
                return false;
        }
    }
    
    public static List<Doctor> getDefaultDoctors(){
        return new ArrayList<>(defaultDoctors);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * @param specialization the specialization to set
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * @return the honsCompletionYear
     */
    public int getHonsCompletionYear() {
        return honsCompletionYear;
    }

    /**
     * @param honsCompletionYear the honsCompletionYear to set
     */
    public void setHonsCompletionYear(int honsCompletionYear) {
        this.honsCompletionYear = honsCompletionYear;
    }
}

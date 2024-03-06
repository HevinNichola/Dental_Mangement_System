import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private static int nextId = 1;
    private int appointmentId;
    private Patient patient;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String doctorName;



    public Appointment(Patient patient, LocalDate appointmentDate, LocalTime appointmentTime, String doctorName) {
        this.appointmentId = nextId++;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getAppointmentDate() {
        return this.appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(LocalTime newTime) {
        this.appointmentTime = newTime;
    }

    public String getDoctorName() {
        return doctorName;
    }
    
    public void setAppointmentDate(LocalDate newDate) {
        this.appointmentDate = newDate;
    }
}

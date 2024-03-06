import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private Map<DayOfWeek, List<LocalTime>> scheduleMap;
    private Map<LocalDate, List<LocalTime>> bookedSlots;

    public Schedule() {
        this.scheduleMap = new HashMap<>();
        this.bookedSlots = new HashMap<>();
        initializeSchedule();
    }

    private void initializeSchedule() {
        addAvailableTimes(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(21, 0));
        addAvailableTimes(DayOfWeek.WEDNESDAY, LocalTime.of(18, 0), LocalTime.of(21, 0));
        addAvailableTimes(DayOfWeek.SATURDAY, LocalTime.of(15, 0), LocalTime.of(22, 0));
        addAvailableTimes(DayOfWeek.SUNDAY, LocalTime.of(15, 0), LocalTime.of(22, 0));
    }

    public void addAvailableTimes(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        List<LocalTime> times = scheduleMap.computeIfAbsent(day, k -> new ArrayList<>());
        LocalTime currentTime = startTime;

        while (currentTime.plusMinutes(60).isBefore(endTime) || currentTime.plusMinutes(60).equals(endTime)) {
            times.add(currentTime);
            currentTime = currentTime.plusMinutes(60);
        }
    }


    public List<LocalTime> getAvailableTimes(DayOfWeek day) {
        return scheduleMap.getOrDefault(day, Collections.emptyList());
    }

    public Map<DayOfWeek, List<LocalTime>> getFullSchedule() {
        return Collections.unmodifiableMap(scheduleMap);
    }

    public List<LocalTime> getAvailableAppointmentTimes(DayOfWeek selectedDay) {
        return getAvailableTimes(selectedDay);
    }

    public List<LocalTime> getBookedSlots(LocalDate date) {
        return bookedSlots.getOrDefault(date, Collections.emptyList());
    }

    public void bookAppointment(LocalDate date, LocalTime time) {
        bookedSlots.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
    }

    public void displayAvailableAppointmentSlots(DayOfWeek selectedDay, List<LocalTime> bookedSlots) {
        List<LocalTime> availableTimes = getAvailableAppointmentTimes(selectedDay);

        System.out.println();
        System.out.println("Available appointment slots for " + selectedDay + ":");
        for (int i = 0; i < availableTimes.size(); i++) {
            LocalTime time = availableTimes.get(i);
            String status = bookedSlots.contains(time) ? "Booked" : "Available";
            System.out.println((i + 1) + ". " + time + " - " + status);
        }
    }
    
    public void cancelAppointment(LocalDate appointmentDate, LocalTime appointmentTime) {
        List<LocalTime> bookedSlots = getBookedSlots(appointmentDate);
        bookedSlots.remove(appointmentTime);
    }

    List<LocalTime> getAvailableAppointmentTimes(LocalDate selectedDay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

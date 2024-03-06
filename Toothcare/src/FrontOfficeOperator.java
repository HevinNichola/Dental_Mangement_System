import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FrontOfficeOperator {

    private List<Appointment> appointments;

    private Schedule schedule;

    private Set<Integer> printedInvoices;



    public FrontOfficeOperator() {
        schedule = new Schedule();
        appointments = new ArrayList<>();
        printedInvoices = new HashSet<>();

    }

    //Appointment Menu
    void appointmentCategory(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Appointment Category");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointment");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Search Appointment");
            System.out.print("Choose an option (or press 0 and enter to go back): ");

            int appointmentChoice = scanner.nextInt();
            scanner.nextLine();

            if (appointmentChoice == 0) {
                System.out.println();
                return;
            }

            switch (appointmentChoice) {
                case 1:
                    makeAppointment(scanner);
                    break;

                case 2:
                    viewAppointments(scanner);
                    break;

                case 3:
                    updateAppointment(scanner);
                    break;

                case 4:
                    deleteAppointment(scanner);
                    break;

                case 5:
                    searchAppointment(scanner);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    //Creates Appointment
    private void makeAppointment(Scanner scanner) {
        System.out.println();
        System.out.println("Availability Note:");
        System.out.println("  - Dr. John Doe: Monday and Wednesday");
        System.out.println("  - Dr.  Jane Smith: Saturday and Sunday");
        System.out.print("-----------------------------------------------");

        while (true) {
            System.out.println();
            System.out.println("Select the day");
            //retrieves the available days from the class
            displayAppointmentDays();
            System.out.print("Enter your choice (Type 0 to go to the previous menu): ");
            int dayChoice = scanner.nextInt();
            scanner.nextLine();

            if (dayChoice == 0) {
                return;
            }

            List<LocalDate> availableDates = getAvailableAppointmentDates();

            if (dayChoice > 0 && dayChoice <= availableDates.size()) {
                LocalDate selectedDate = availableDates.get(dayChoice - 1);
                DayOfWeek selectedDay = selectedDate.getDayOfWeek();

                displayAvailableAppointmentSlots(selectedDay, schedule.getBookedSlots(selectedDate));

                System.out.print("Enter the selected time (Type 0 to go to the previous menu): ");
                int timeChoice = scanner.nextInt();
                scanner.nextLine();

                if (timeChoice == 0) {
                    break;
                }

                List<LocalTime> availableTimes = schedule.getAvailableAppointmentTimes(selectedDay);

                if (timeChoice > 0 && timeChoice <= availableTimes.size()) {
                    LocalTime selectedTime = availableTimes.get(timeChoice - 1);

                    Patient selectedPatient = patientDetails(scanner);

                    System.out.println();
                    System.out.println("Patient Details");
                    System.out.println("Name: " + selectedPatient.getFullName());
                    System.out.println("Date of Birth: " + selectedPatient.getDateOfBirth());
                    System.out.println("NIC: " + selectedPatient.getNic());
                    System.out.println("Mobile Number: " + selectedPatient.getMobileNumber());
                    System.out.println("Address: " + selectedPatient.getAddress());

                    String doctorName = determineDoctorName(selectedDate);
                    boolean paymentSuccessful = Payment.processPayment(scanner);

                    if (paymentSuccessful) {
                        if (!schedule.getBookedSlots(selectedDate).contains(selectedTime)) {
                            schedule.bookAppointment(selectedDate, selectedTime);

                            Appointment appointment = new Appointment(selectedPatient, selectedDate, selectedTime, doctorName);
                            appointments.add(appointment);

                            // Display confirmation details
                            System.out.println("Appointment successfully scheduled!");
                            System.out.println();
                            System.out.println("Appointment Details");
                            System.out.println("Appointment ID: " + appointment.getAppointmentId());
                            System.out.println("Patient Name: " + selectedPatient.getFullName());
                            System.out.println("Appointment Date: " + selectedDate);
                            System.out.println("Appointment Time: " + selectedTime);
                            System.out.println("Doctor's Name: " + doctorName);
                        } else {
                            System.out.println("Error: The selected time is already booked.");
                        }
                    } else {
                        System.out.println("Payment unsuccessful. Appointment not scheduled.");
                    }
                } else {
                    System.out.println("Invalid time choice. Please enter a valid time choice.");
                }
            }
        }
    }

    private Patient patientDetails(Scanner scanner) {
        System.out.println();
        System.out.println("Enter Patient Details:");


        String firstName;
        do {
            System.out.print("First Name: ");
            firstName = scanner.nextLine();
        } while (firstName.trim().isEmpty());


        String lastName;
        do {
            System.out.print("Last Name: ");
            lastName = scanner.nextLine();
        } while (lastName.trim().isEmpty());


        LocalDate dateOfBirth = null;
        boolean validDateOfBirth = false;
        while (!validDateOfBirth) {
            try {
                System.out.print("Date of Birth (yyyy-MM-dd): ");
                String dobInput = scanner.nextLine();
                dateOfBirth = LocalDate.parse(dobInput);
                validDateOfBirth = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date in the format yyyy-MM-dd.");
            }
        }

        String nic;
        do {
            System.out.print("NIC: ");
            nic = scanner.nextLine();
        } while (nic.trim().isEmpty());



        String mobileNumber;
        do {
            System.out.print("Mobile Number: ");
            mobileNumber = scanner.nextLine();
        } while (!mobileNumber.matches("\\d{10}"));

        // Validating Address
        String address;
        do {
            System.out.print("Address: ");
            address = scanner.nextLine();
        } while (address.trim().isEmpty());

        return new PatientImpl(firstName, lastName, dateOfBirth,nic,mobileNumber, address);
    }

    private void updateAppointment(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Edit Appointment List");

            if (appointments.isEmpty()) {
                System.out.println("No appointments available");
                return;
            } else {
                for (int i = 0; i < appointments.size(); i++) {
                    Appointment appointment = appointments.get(i);
                    System.out.println("Appointment ID " + appointment.getAppointmentId() +
                            ": Patient Name: " + appointment.getPatient().getFullName() +
                            ", Date of Birth: " + appointment.getPatient().getDateOfBirth() +
                            ", NIC: " + appointment.getPatient().getNic()  +
                            ", Mobile Number: " + appointment.getPatient().getMobileNumber() +
                            ", Address: " + appointment.getPatient().getAddress() +
                            ", Appointment Date: " + appointment.getAppointmentDate() +
                            ", Appointment Time: " + appointment.getAppointmentTime() +
                            ", Doctor's Name: " + appointment.getDoctorName());
                }
            }

            System.out.println();
            System.out.println("Enter the Appointment ID or Mobile Number to edit details (Type 0 to go to previous Menu): ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                break;
            } else {
                Appointment selectedAppointment = findAppointmentById(input);

                if (selectedAppointment != null) {
                    // Allow the user to update appointment details
                    System.out.print("Do you want to update appointment details? (1 for Yes, 2 for No): ");
                    int updateChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (updateChoice == 1) {
                        System.out.println("1. Update Appointment Day");
                        System.out.println("2. Update Appointment Time");
                        System.out.println("3. Update Patient Details");
                        System.out.println("4. Go Back");
                        System.out.print("Choose an option: ");
                        int option = scanner.nextInt();
                        scanner.nextLine();

                        switch (option) {
                            case 1:
                                updateAppointmentDay(selectedAppointment, scanner);
                                break;
                            case 2:
                                updateAppointmentTime(selectedAppointment, scanner);
                                break;
                            case 3:
                                updatePatientDetails(selectedAppointment, scanner);
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Invalid option. Please choose a valid option.");
                        }
                    }

                    System.out.println("Details updated successfully.");
                } else {
                    System.out.println("Invalid input. Please enter a valid Appointment ID or Mobile Number.");
                }
            }
        }
    }

    private void updateAppointmentDay(Appointment appointment, Scanner scanner) {
        System.out.println("Update Appointment");

        System.out.println("Select the reschedule date");
        //Prints all the available dates
        List<LocalDate> availableDates = getAvailableAppointmentDates();

        for (int i = 0; i < availableDates.size(); i++) {
            LocalDate currentDate = availableDates.get(i);
            DayOfWeek day = currentDate.getDayOfWeek();
            System.out.println((i + 1) + " - " + day + " - " + "["+currentDate+"]");
        }

        System.out.print("Enter the new appointment day (or press 0 to cancel): ");
        int dayChoice = scanner.nextInt();
        scanner.nextLine();

        if (dayChoice == 0) {
            System.out.println("Returning to previous Menu.");
            return;
        }

        if (dayChoice > 0 && dayChoice <= availableDates.size()) {
            LocalDate newDate = availableDates.get(dayChoice - 1);

            DayOfWeek newDay = newDate.getDayOfWeek();
            System.out.println("New appointment date: " + newDay + " - " + newDate);

            appointment.setAppointmentDate(newDate);

            System.out.println("Appointment day updated successfully!");
        } else {
            System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private void updateAppointmentTime(Appointment appointment, Scanner scanner) {
        DayOfWeek appointmentDay = appointment.getAppointmentDate().getDayOfWeek();
        List<LocalTime> bookedSlots = schedule.getBookedSlots(appointment.getAppointmentDate());

        // Display available time slots for the selected day
        displayAvailableAppointmentSlots(appointmentDay, bookedSlots);

        // Prompt the user to select a new time
        System.out.print("Enter the new time slot (Type 0 to go to previous Menu): ");
        int timeChoice = scanner.nextInt();
        scanner.nextLine();

        if (timeChoice == 0) {
            return;  // User chose to go back
        }

        List<LocalTime> availableTimes = schedule.getAvailableAppointmentTimes(appointmentDay);

        if (timeChoice > 0 && timeChoice <= availableTimes.size()) {
            LocalTime newTime = availableTimes.get(timeChoice - 1);

            // Check if the new time slot is available
            if (!bookedSlots.contains(newTime)) {
                // Update the appointment time
                appointment.setAppointmentTime(newTime);
                System.out.println("Appointment time updated successfully!");
            } else {
                System.out.println("Error: The selected time is already booked.");
            }
        } else {
            System.out.println("Invalid time choice. Please enter a valid time choice.");
        }
    }

    private void updatePatientDetails(Appointment appointment, Scanner scanner) {
        System.out.println("Updating Patient Details for Appointment ID: " + appointment.getAppointmentId());

        // Display current patient details
        displayPatientDetails(appointment.getPatient());

        // Ask user if they want to update patient details
        System.out.print("Do you want to update patient details? (1 for Yes, 2 for No): ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine();

        if (updateChoice == 1) {
            // Capture updated patient details
            Patient updatedPatient = patientDetails(scanner);

            // Display updated patient details
            System.out.println("Updated Patient Details:");
            displayPatientDetails(updatedPatient);

            // Ask for confirmation
            System.out.print("Confirm update? (1 for Yes, 2 for No): ");
            int confirmationChoice = scanner.nextInt();
            scanner.nextLine();

            if (confirmationChoice == 1) {
                // Update patient details in the appointment
                appointment.setPatient(updatedPatient);
                System.out.println("Patient details updated successfully!");
            } else {
                System.out.println("Update cancelled. Returning to the main menu.");
            }
        } else {
            System.out.println("No changes made. Returning to the main menu.");
        }
    }
    private void displayPatientDetails(Patient patient) {
        System.out.println("Patient Details");
        System.out.println("Name: " + patient.getFullName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("NIC: " + patient.getNic());
        System.out.println("Mobile Number: " + patient.getMobileNumber());
        System.out.println("Address: " + patient.getAddress());
    }

    private Appointment findAppointmentById(String input) {
        for (Appointment appointment : appointments) {
            if (String.valueOf(appointment.getAppointmentId()).equals(input)) {
                return appointment;
            }
        }
        return null;
    }

    private void deleteAppointment(Scanner scanner) {
        System.out.println("Delete Appointment");

        if (appointments.isEmpty()) {
            System.out.println("No Appointments are booked");
            return;
        }

        // display available appointments
        displayAppointmentList();

        // insert appointment ID
        System.out.print("Enter the Appointment ID to remove (or press 0 to go back): ");
        String input = scanner.nextLine();

        if ("0".equals(input)) {
            return;
        }

        // Find the appointment by ID
        Appointment appointmentToDelete = findAppointmentById(input);

        if (appointmentToDelete != null) {
            // Display details of the appointment
            System.out.println("Appointment Details:");
            displayAppointmentDetails(appointmentToDelete);

            // Confirm deletion
            System.out.print("Are you sure to delete the appointment? (1 for Yes, 2 for No): ");
            int confirmationChoice = scanner.nextInt();
            scanner.nextLine();

            if (confirmationChoice == 1) {
                // Delete appointment
                appointments.remove(appointmentToDelete);
                schedule.cancelAppointment(appointmentToDelete.getAppointmentDate(), appointmentToDelete.getAppointmentTime());
                System.out.println("Appointment deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Invalid Appointment ID");
        }
    }

    private void displayAppointmentList() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments are booked.");
        } else {
            System.out.println("Booked Appointments");
            for (Appointment appointment : appointments) {
                System.out.println("Appointment ID: " + appointment.getAppointmentId() +
                        ", Patient Name: " + appointment.getPatient().getFullName() +
                        ", Appointment Date: " + appointment.getAppointmentDate() +
                        ", Appointment Time: " + appointment.getAppointmentTime() +
                        ", Doctor's Name: " + appointment.getDoctorName());
            }
        }
    }

    private void displayAppointmentDetails(Appointment appointment) {
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Patient Name: " + appointment.getPatient().getFullName());
        System.out.println("Appointment Date: " + appointment.getAppointmentDate());
        System.out.println("Appointment Time: " + appointment.getAppointmentTime());
        System.out.println("Doctor's Name: " + appointment.getDoctorName());
    }

    private String getInput(String prompt){
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    // View Appointment
    private void viewAppointments(Scanner scanner) {
        System.out.println();
        System.out.println("Select the appointment day to view appointments");
        displayAppointmentDays();
        System.out.print("Enter your choice (Type 0 to go to previous Menu): ");
        int dayChoice = scanner.nextInt();
        scanner.nextLine();
        if (dayChoice == 0) {
            return;
        }
        List<LocalDate> availableDates = getAvailableAppointmentDates();

        if (dayChoice > 0 && dayChoice <= availableDates.size()) {
            LocalDate selectedDate = availableDates.get(dayChoice - 1);

            List<Appointment> appointmentsOnDate = getAppointmentsByDate(selectedDate);

            if (!appointmentsOnDate.isEmpty()) {
                System.out.println();
                System.out.println("Appointments on " + selectedDate + ":");

                for (Appointment appointment : appointmentsOnDate) {
                    System.out.println("Appointment ID: " + appointment.getAppointmentId());
                    System.out.println("Patient Name: " + appointment.getPatient().getFullName());
                    System.out.println("Appointment Time: " + appointment.getAppointmentTime());
                    System.out.println("Doctor's Name: " + appointment.getDoctorName());
                    System.out.println("--------------------");
                }
            } else {
                System.out.println("No appointments scheduled on " + selectedDate);
            }
        } else {
            System.out.println("Invalid day choice. Please enter a valid day choice.");
        }
    }
    private void displayAppointmentDays() {
        List<LocalDate> availableDates = getAvailableAppointmentDates();
        for (int i = 0; i < availableDates.size(); i++) {
            LocalDate currentDate = availableDates.get(i);
            DayOfWeek day = currentDate.getDayOfWeek();
            System.out.println((i + 1) + " - " + day + " - " + "["+currentDate+"]");
        }
    }

    private List<LocalDate> getAvailableAppointmentDates() {
        LocalDate today = LocalDate.now();
        List<LocalDate> availableDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = today.plusDays(i);
            DayOfWeek day = currentDate.getDayOfWeek();
            if (day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                availableDates.add(currentDate);
            }
        }
        return availableDates;
    }

    private List<Appointment> getAppointmentsByDate(LocalDate selectedDate) {
        List<Appointment> appointmentsOnDate = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDate().equals(selectedDate)) {
                appointmentsOnDate.add(appointment);
            }
        }

        return appointmentsOnDate;
    }

    private void displayAvailableAppointmentSlots(DayOfWeek selectedDay, List<LocalTime> bookedSlots) {
        System.out.println();
        System.out.println("Available time slots for " + selectedDay + ":");

        List<LocalTime> availableTimes = schedule.getAvailableAppointmentTimes(selectedDay);

        for (int i = 0; i < availableTimes.size(); i++) {
            LocalTime timeSlot = availableTimes.get(i);
            String status = bookedSlots.contains(timeSlot) ? "Booked" : "Available";
            System.out.println((i + 1) + ". " + timeSlot + " (" + status + ")");
        }
    }
    private String determineDoctorName(LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
            case WEDNESDAY:
                return "Dr. John Doe";
            case SATURDAY:
            case SUNDAY:
                return "Dr. Jane Smith";
            default:
                // Handle other cases or throw an exception if needed
                throw new IllegalArgumentException("Invalid appointment date");
        }
    }

    private void searchAppointment(Scanner scanner){
        System.out.println();
        System.out.println("Search for an Appointment");
        System.out.println("1 - Search by Patient Name");
        System.out.println("2 - Search by Appointment ID");
        System.out.print("Select an option (Type 0 to go to previous Menu): ");

        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        if(searchChoice == 0){
            return;
        }

        switch(searchChoice){
            case 1:
                searchByPatientName(scanner);
                break;

            case 2:
                searchByAppointmentId(scanner);
                break;

            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private Appointment binarySearchByAppointmentId(int targetId) {
        appointments.sort(Comparator.comparingInt(Appointment::getAppointmentId));
        int low = 0;
        int high = appointments.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Appointment midAppointment = appointments.get(mid);

            if (midAppointment.getAppointmentId() == targetId) {
                return midAppointment;
            } else if (midAppointment.getAppointmentId() < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return null; // Appointment not found
    }


    private void searchByAppointmentId(Scanner scanner) {
        System.out.print("Enter the Appointment ID to search: ");
        int targetId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        Appointment foundAppointment = binarySearchByAppointmentId(targetId);

        if (foundAppointment != null) {
            displayAppointmentDetails(foundAppointment);

        } else {
            System.out.println("Appointment not found.");
        }
    }

    private void displaySearchResults(List<Appointment> appointments) {
        if (!appointments.isEmpty()) {
            System.out.println("Search Results:");

            for (Appointment appointment : appointments) {
                displayAppointmentDetails(appointment);
            }
        } else {
            System.out.println("No matching appointments found.");
        }
    }

    private void searchByPatientName(Scanner scanner) {
        System.out.print("Enter Part of Patient Name: ");
        String partialName = scanner.nextLine().toLowerCase();

        List<Appointment> matchingAppointments = new ArrayList<>();

        //Search by patient name or part of name
        for (Appointment appointment : appointments) {
            String fullName = appointment.getPatient().getFullName().toLowerCase();
            if (fullName.contains(partialName)) {
                matchingAppointments.add(appointment);
            }
        }

        displaySearchResults(matchingAppointments);
    }
    private double calculateTreatmentCost(Scanner scanner) {
        double totalCost = 0.0;

        System.out.println("Treatment Selection");

        //Goes through a for loop letting user pick what treatments to be added
        for (Treatment.TreatmentType treatmentType : Treatment.TreatmentType.values()) {
            System.out.print("Whether " + treatmentType.name() +  " Have been Taken? (1 for Yes, 2 for No, 0 to finish): ");
            String treatmentInput = scanner.nextLine();

            if ("0".equals(treatmentInput)) {
                break;
            }
            if ("1".equals(treatmentInput)) {
                double treatmentCost = Treatment.getPrice(treatmentType);

                //Prints treatment name
                System.out.println("Selected Treatment: " + treatmentType.name());

                //Prints treatment price
                System.out.println("Treatment Cost: Rs." + treatmentCost);

                //calculates total cost
                totalCost += treatmentCost;
            } else if (!"2".equals(treatmentInput)) {
                //Handle invalid inputs
                System.out.println("Invalid input. Please enter 1 for Yes, 2 for No, or 0 to finish.");
            }
            System.out.println("Running Total: Rs." + totalCost);
            System.out.println();
        }
        return totalCost;
    }

    private boolean processPayment(Scanner scanner, double totalCost) {
        System.out.println("Payment Options:");
        System.out.println("1. Cash Payment");
        System.out.println("2. Card Payment");

        int paymentOption = 0;
        boolean paymentSuccessful = false;

        while (true) {
            System.out.print("Choose a payment option (1 for Cash, 2 for Card): ");
            String paymentInput = scanner.nextLine();

            try {
                paymentOption = Integer.parseInt(paymentInput);
                if (paymentOption == 1 || paymentOption == 2) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 1 for Cash or 2 for Card.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        switch (paymentOption) {
            case 1:
                // Process cash payment
                paymentSuccessful = processCashPayment(scanner, totalCost);
                break;
            case 2:
                // Process card payment
                paymentSuccessful = processCardPayment(scanner, totalCost);
                break;
        }

        return paymentSuccessful;
    }

    private boolean processCashPayment(Scanner scanner, double totalCost) {
        System.out.println();
        System.out.print("Enter the amount in cash: Rs. ");
        double cashAmount = 0;

        while (true) {
            try {
                cashAmount = Double.parseDouble(scanner.nextLine());
                if (cashAmount >= totalCost) {
                    break;
                } else {
                    System.out.println("Insufficient cash. Please enter an amount equal to or greater than the total cost.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Calculate remaining balance
        double remainingBalance = cashAmount - totalCost;
        if (remainingBalance > 0) {
            System.out.println("Cash payment of Rs. " + totalCost + " received.");
            System.out.println("Remaining balance: Rs. " + remainingBalance);
        } else {
            System.out.println("Cash payment of Rs. " + totalCost + " received. No remaining balance.");
        }

        return true;
    }

    private boolean processCardPayment(Scanner scanner, double totalCost) {
        System.out.println("Card payment of Rs. " + totalCost + " processed successfully.");
        return true;
    }


    private void printInvoiceConfirmation(Scanner scanner, int appointmentId) {
        System.out.println();
        System.out.print("Do you want to print the invoice? (1 for Yes, 2 for No): ");
        String printInput = scanner.nextLine();

        if ("1".equals(printInput)) {
            if (!isInvoicePrinted(appointmentId)) {
                displayInvoiceDetails(scanner, appointmentId);
                markInvoiceAsPrinted(appointmentId);
                System.out.println();

                // Ask the user whether to calculate the total of the bill for another appointment ID
                System.out.print("Calculate for another appointment? (1 for Yes, 2 for No): ");
                String calculateAnotherInput = scanner.nextLine();

                if ("1".equals(calculateAnotherInput)) {
                    generateInvoice(scanner);
                } else {
                    System.out.println("Returning to the billing category menu.");
                }
            } else {
                System.out.println("Error: Invoice already printed for this appointment ID.");
            }
        } else {
            System.out.println("Invoice not printed.");
        }
    }

    private boolean isInvoicePrinted(int appointmentId) {

        return printedInvoices.contains(appointmentId);
    }

    private void markInvoiceAsPrinted(int appointmentId) {

        printedInvoices.add(appointmentId);
    }

    void generateInvoice(Scanner scanner) {
        System.out.println();
        System.out.print("Enter Appointment ID or Mobile Number (or press 0 to go back): ");
        String appointmentIdentifier = scanner.nextLine();

        if ("0".equals(appointmentIdentifier)) {
            return;
        }

        int appointmentId = Integer.parseInt(appointmentIdentifier);


        // Check if the invoice has already been printed for the given appointment ID
        if (isInvoicePrinted(appointmentId)) {
            System.out.println("Error: Invoice already printed for this appointment ID.");
            return;
        }

        Appointment appointment = findAppointmentByIdentifier(appointmentIdentifier);

        if (appointment != null) {
            System.out.println("Appointment Details:");
            System.out.println("Appointment ID: " + appointment.getAppointmentId());

            Patient patient = appointment.getPatient();

            System.out.println("Patient Name: " + patient.getFullName());
            System.out.println("Appointment Date: " + appointment.getAppointmentDate());
            System.out.println("Appointment Time: " + appointment.getAppointmentTime());
            System.out.println("Doctor's Name: " + appointment.getDoctorName());


            // Calculate and display treatment cost
            double totalCost = calculateTreatmentCost(scanner);

            // Display payment option and process payment
            if (processPayment(scanner, totalCost)) {
                // Confirm and print invoice
                printInvoiceConfirmation(scanner, appointment.getAppointmentId());
            } else {
                System.out.println("Payment unsuccessful. Invoice will not be printed.");
                return;
            }


            // Prompt user to go back after printing invoice
            System.out.print("Press 0 and enter to go back: ");
            String goBackInput = scanner.nextLine();
            if ("0".equals(goBackInput)) {
                return;
            }
        } else {
            System.out.println("Appointment not found. Please enter a valid Appointment ID or Mobile Number.");
        }
    }

    private Appointment findAppointmentByIdentifier(String appointmentIdentifier) {
        String trimmedIdentifier = appointmentIdentifier.trim();

        for (Appointment appointment : appointments) {
            String storedId = String.valueOf(appointment.getAppointmentId()).trim();
            String storedMobileNumber = appointment.getPatient().getMobileNumber().trim();

            if (trimmedIdentifier.equals(storedId) || trimmedIdentifier.equals(storedMobileNumber)) {
                return appointment;
            }
        }
        return null;
    }

    private void displayInvoiceDetails(Scanner scanner, int appointmentId) {
        // Fetch appointment details
        Appointment appointment = findAppointmentById(appointmentId);

        if (appointment != null) {
            // Display invoice format with all the details
            System.out.println("\n************ Tooth Care Dental System ************");
            System.out.println("Invoice Details:");
            System.out.println("Appointment ID: " + appointment.getAppointmentId());

            Patient patient = appointment.getPatient();
            System.out.println("Patient Name: " + patient.getFullName());
            System.out.println("Appointment Date: " + appointment.getAppointmentDate());
            System.out.println("Appointment Time: " + appointment.getAppointmentTime());
            System.out.println("Doctor's Name: " + appointment.getDoctorName());
            System.out.println("\n************ Thank you For your Payment ************");

        } else {
            System.out.println("Error: Appointment details not found for ID " + appointmentId);
        }
    }

    private Appointment findAppointmentById(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment != null && appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

}

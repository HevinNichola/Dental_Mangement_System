import java.util.Random;
import java.util.Scanner;

public class Payment {
    private static final double APPOINTMENT_REGISTRATION_FEE = 1000.00;
    public static boolean processPayment(Scanner scanner) {
        System.out.println();
        System.out.println("Appointment Registration Fee: $" + APPOINTMENT_REGISTRATION_FEE);
        System.out.println("Select payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Credit/Debit Card");
        System.out.print("Enter your choice: ");
        
        int paymentChoice;
        try {
            paymentChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return false;
        }

        switch (paymentChoice) {
            case 1:
                return processCashPayment(scanner);

            case 2:
                return processCardPayment(scanner);

            default:
                System.out.println("Invalid payment choice. Payment unsuccessful.");
                return false;
        }
    }

    private static boolean processCardPayment(Scanner scanner) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter expiration date (MM/YYYY): ");
        String expirationDate = scanner.nextLine();
        System.out.print("Enter CVV: ");
        char[] cvv = scanner.nextLine().toCharArray();

        if (isValidCardDetails(cardNumber, expirationDate, cvv)) {
            boolean paymentApproval = simulatePaymentApproval();

            if (paymentApproval) {
                System.out.println("Payment approved!");
                return true;
            } else {
                System.out.println("Payment declined. Please try again or use another payment method.");
            }
        } else {
            System.out.println("Invalid card details. Please check and try again.");
        }
        return false;
    }

    private static boolean isValidCardDetails(String cardNumber, String expirationDate, char[] cvv) {
        // Implement more thorough validation as needed
        return cardNumber != null && cardNumber.matches("\\d{16}")
                && expirationDate.matches("(0[1-9]|1[0-2])/20\\d{2}")
                && cvv != null && cvv.length == 3;
    }

    private static boolean processCashPayment(Scanner scanner) {
        System.out.println();
        System.out.println("Appointment registration fee is Rs. " + APPOINTMENT_REGISTRATION_FEE);
        System.out.print("Enter the amount paid: Rs. ");

        double amountPaid;
        try {
            amountPaid = scanner.nextDouble();
            scanner.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return false;
        }

        if (amountPaid < APPOINTMENT_REGISTRATION_FEE) {
            System.out.println("Insufficient amount. Please pay the full amount.");
            return false;
        } else {
            double balance = amountPaid - APPOINTMENT_REGISTRATION_FEE;
            System.out.println("Amount paid: $" + amountPaid);
            System.out.println("Payment successful!");
            System.out.println("Balance: $" + balance);
            System.out.println();
            return true;
        }
    }

    private static boolean simulatePaymentApproval() {
        return new Random().nextDouble() < 0.8;
    }
}

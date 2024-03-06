import java.util.EnumSet;

public class Invoice {
    private static int invoiceCounter = 1;

    private int invoiceId;
    private EnumSet<Treatment.TreatmentType> treatments;
    private double totalCost;

    public Invoice(int appointmentId, EnumSet<Treatment.TreatmentType> treatments) {
        this.invoiceId = invoiceCounter++;
        this.treatments = treatments;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public EnumSet<Treatment.TreatmentType> getTreatments() {
        return treatments;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void calculateTotalCost() {
        this.totalCost = calculateTreatmentCost(treatments);
    }

    public void displayFinalBill() {
        System.out.println("\nFinal Bill (Invoice ID: " + invoiceId + "):");
        System.out.println("Treatments Utilized:");

        for (Treatment.TreatmentType treatmentType : treatments) {
            System.out.println("- " + treatmentType + ": $" + Treatment.getPrice(treatmentType));
        }

        System.out.println("\nTotal Cost: $" + totalCost);
    }

    private static double calculateTreatmentCost(EnumSet<Treatment.TreatmentType> treatments) {
        double totalCost = 0.0;

        for (Treatment.TreatmentType treatmentType : treatments) {
            totalCost += Treatment.getPrice(treatmentType);
        }

        return totalCost;
    }
}

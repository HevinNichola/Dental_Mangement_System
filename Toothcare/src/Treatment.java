public class Treatment {
    public enum TreatmentType{
        CLEANING,
        WHITENING,
        FILLING,
        NERVE_FILLING,
        ROOT_CANAL_THERAPY
    }
    
    private static final java.util.Map<TreatmentType, Double> TREATMENT_PRICES;
    
    static{
        TREATMENT_PRICES =  new java.util.EnumMap<>(TreatmentType.class);
        TREATMENT_PRICES.put(TreatmentType.CLEANING, 500.0);
        TREATMENT_PRICES.put(TreatmentType.WHITENING, 1000.0);
        TREATMENT_PRICES.put(TreatmentType.FILLING, 1500.0);
        TREATMENT_PRICES.put(TreatmentType.NERVE_FILLING, 2000.0);
        TREATMENT_PRICES.put(TreatmentType.ROOT_CANAL_THERAPY, 2500.0);
    }
    
    public static double getPrice(TreatmentType treatmentType){
        return TREATMENT_PRICES.getOrDefault(treatmentType, 0.0);
    }


}

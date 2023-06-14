import java.util.Arrays;

public enum Nominal {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100),
    FIFTY(50);

    private final int nominalValue;

    Nominal(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    public int getNominalValue() {
        return nominalValue;
    }

    public static Nominal getNominal(Integer nominalValue){
        return Arrays.stream(Nominal.values()).filter(it-> it.getNominalValue() == nominalValue).findFirst().get();
    }
}

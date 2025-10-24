package client.asta_lsi2.models;

public enum Programme {
    I1("I1"),
    I2("I2"),
    I3("I3");
    
    private final String label;
    
    Programme(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}

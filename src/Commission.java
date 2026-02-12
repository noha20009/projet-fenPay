public class Commission {
    private  int id;
    private double tauxCommission =2;
    private double montant;

    public Commission(int id,  double montant) {
        this.id = id;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTauxCommission() {
        return tauxCommission;
    }

    public void setTauxCommission(double tauxCommission) {
        this.tauxCommission = tauxCommission;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public  double calculerCommission(){
        double montantCommission= getMontant()*tauxCommission/100;
        return montantCommission;
    }
}

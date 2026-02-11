import java.time.LocalDateTime;

public class Statistique {
   private double totalPaiement;
   private double totalCommission;
   private int nombrePaiement;
   private int facturePaye;
   private int factureNonPaye;

    public Statistique(double totalPaiement, double totalCommission, int nombrePaiement, int facturePaye, int factureNonPaye) {
        this.totalPaiement = totalPaiement;
        this.totalCommission = totalCommission;
        this.nombrePaiement = nombrePaiement;
        this.facturePaye = facturePaye;
        this.factureNonPaye = factureNonPaye;
    }
}

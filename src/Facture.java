import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Facture {
        private int id;
        private LocalDate date;
        private double montant;
        private boolean status;
        private Client client;
        private Prestataire prestataire;

        public Facture(int id, LocalDate date,double montant, boolean status, Client client, Prestataire prestataire) {
            this.id = id;
            this.date = date;
            this.montant = montant;
            this.status = status;
            this.client = client;
            this.prestataire = prestataire;
        }

        public int getId() {
            return id;
        }

        public double getMontant() {
            return montant;
        }

    public LocalDate getDate() {
        return date;
    }

    public Client getClient() {
        return client;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public boolean isStatus() {
            return status;
        }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}






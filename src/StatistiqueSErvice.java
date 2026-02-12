public class StatistiqueSErvice {

    private Statistique statistique;

    public StatistiqueSErvice() {
        this.statistique = new Statistique();
    }

    public void ajouterPaiement(double montant, double commission, boolean factureEstPaye) {

        statistique.calculerTotalPaiements(montant);
        statistique.calculerTotalCommissions(commission);

        if (factureEstPaye) {
            statistique.setFacturePaye(statistique.getFacturePaye() + 1);
        } else {
            statistique.setFactureNonPaye(statistique.getFactureNonPaye() + 1);
        }
    }

    public void afficherRapport() {
        statistique.genererRapport();
    }

    public Statistique getStatistique() {
        return statistique;
    }
}


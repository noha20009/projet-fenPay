import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatistiqueService {

    private Statistique statistique;

    public StatistiqueService() {
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

    // save dans base donnee!!!!
    public void saveStatistique() {

        String sql = "INSERT INTO Statistique (totalPaiements, totalCommissions, nombrePaiements, nombreFacturePayee, nombreFactureNonPayee, dateGeneration) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, statistique.getTotalPaiements());
            stmt.setDouble(2, statistique.getTotalCommissions());
            stmt.setInt(3, statistique.getNombrePaiements());
            stmt.setInt(4, statistique.getFacturePaye());
            stmt.setInt(5, statistique.getFactureNonPaye());
            stmt.setDate(6, java.sql.Date.valueOf(statistique.getDateGeneration()));

            stmt.executeUpdate();
            System.out.println("Statistique enregistrée en base avec succès !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statistique getStatistique() {
        return statistique;
    }
}

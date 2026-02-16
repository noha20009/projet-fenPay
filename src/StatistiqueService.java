import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class StatistiqueService {

    private Statistique statistique;

    public StatistiqueService() {
        this.statistique = new Statistique();
    }


public void ajouterPaiement(double montant, double tauxCommission, boolean paye) {

    String sqlPaiement = "INSERT INTO Paiement (montant, date, idFacture) VALUES (?, ?, ?)";
    String sqlCommission = "INSERT INTO Commission (idPaiement, tauxCommission, montant) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection()) {

        conn.setAutoCommit(false);

        // insert Paiement
        PreparedStatement stmtPaiement = conn.prepareStatement(sqlPaiement, Statement.RETURN_GENERATED_KEYS);
        stmtPaiement.setDouble(1, montant);
        stmtPaiement.setDate(2, Date.valueOf(LocalDate.now()));
        stmtPaiement.setInt(3, 1);

        stmtPaiement.executeUpdate();

        ResultSet rs = stmtPaiement.getGeneratedKeys();
        int idPaiement = 0;
        if (rs.next()) {
            idPaiement = rs.getInt(1);
        }


        double montantCommission = montant * 2 / 100;


        PreparedStatement stmtCommission = conn.prepareStatement(sqlCommission);
        stmtCommission.setInt(1, idPaiement);
        stmtCommission.setDouble(2, 2);
        stmtCommission.setDouble(3, montantCommission);

        stmtCommission.executeUpdate();

        conn.commit();

        System.out.println("Paiement ajouté avec commission automatique !");
        System.out.println("Commission calculée: " + montantCommission);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void afficherRapport() {
        statistique.genererRapport();
    }


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

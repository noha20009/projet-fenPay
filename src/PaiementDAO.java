import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaiementDAO{


    public void insertPaymentWithCommission(double montantPaiement) {
        try (Connection con = DBConnection.getConnection()) {


            String sqlPaiement = "INSERT INTO Paiement(montant) VALUES (?)";
            PreparedStatement ps1 = con.prepareStatement(sqlPaiement, PreparedStatement.RETURN_GENERATED_KEYS);
            ps1.setDouble(1, montantPaiement);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            rs.next();
            int paymentId = rs.getInt(1);


            Commission commission = new Commission(paymentId, montantPaiement);
            double montantCommission = commission.calculerCommission();


            String sqlCommission = "INSERT INTO Commission(idPaiement, tauxCommission, montant) VALUES (?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(sqlCommission);
            ps2.setInt(1, commission.getIdPaiement());
            ps2.setDouble(2, commission.getTauxCommission());
            ps2.setDouble(3, montantCommission);
            ps2.executeUpdate();


            System.out.println("✅ Payment and Commission inserted successfully!");
            System.out.println("Payment ID: " + paymentId + ", Amount: " + montantPaiement);
            System.out.println("Commission Amount (2%): " + montantCommission);

            // Close resources
            ps1.close();
            ps2.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting payment or commission!");
        }
    }
}

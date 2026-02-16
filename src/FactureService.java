import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


    public class FactureService {

        //  Créer facture
        public Facture creerFacture(Facture facture) {

            String sql = "INSERT INTO facture (date, montant, status, idClient, idPrestataire) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDate(1, Date.valueOf(facture.getDate()));
                stmt.setDouble(2, facture.getMontant());
                stmt.setBoolean(3, facture.isStatus());
                stmt.setInt(4, facture.getClient().getIdClient());
                stmt.setInt(5, facture.getPrestataire().getIdPrestat());

                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    facture.setId(keys.getInt(1));
                }

                System.out.println("Facture enregistrée avec succès !");
                return facture;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        //  Lister toutes les factures
        public List<Facture> lister() {

            List<Facture> factures = new ArrayList<>();
            String sql = "SELECT * FROM facture";

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {

                    Facture f = new Facture(
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getDouble("montant"),
                            rs.getBoolean("status"),
                            null,   // charger client si besoin
                            null    // charger prestataire si besoin
                    );

                    factures.add(f);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return factures;
        }

        //  Modifier facture (seulement si non payée)
        public boolean modifierFacture(int id, double nouveauMontant, boolean nouveauStatus) {

            String sql = "UPDATE facture SET montant = ?, status = ? WHERE id = ? AND status = false";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDouble(1, nouveauMontant);
                stmt.setBoolean(2, nouveauStatus);
                stmt.setInt(3, id);

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Facture modifiée avec succès !");
                    return true;
                } else {
                    System.out.println("Modification impossible !");
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        //  Rechercher par ID
        public Facture findById(int id) {

            String sql = "SELECT * FROM facture WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Facture(
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getDouble("montant"),
                            rs.getBoolean("status"),
                            null,
                            null
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }




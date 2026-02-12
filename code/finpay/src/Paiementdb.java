import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Paiementdb {
   private int  id;
   private double montant;
   private String date;

    public static Connection createConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/finepay?useSSL=false",
                "root",
                "123456789"
        );
    }


  public static void paimentDBservice(Scanner scanner){
      System.out.println("entrer votre choix \n 1:enregistrer Payment  \n 2:mettre AJour Pyment \n 3:listerPyment");
      int choix;
      do{
           choix = scanner.nextInt();
          scanner.nextLine();

         if(choix==1){

             Paiementdb.enregistrerPayment(scanner);
         }
         else if(choix==2){
             Paiementdb.mettreAJourPyment(scanner);
         }
         else if(choix==3){
             Paiementdb.listerPyment();
         }
         }while (choix!=0);




  }


    public static void enregistrerPayment(Scanner scanner) {
        try {
            System.out.print("Facture ID: ");
            int idFacture = Integer.parseInt(scanner.nextLine());

            try (Connection con = createConnection()) {
                con.setAutoCommit(false);

                // montant facture
                double montantFacture;
                String sqlFacture = "SELECT montant FROM facture WHERE id = ?";
                PreparedStatement psFacture = con.prepareStatement(sqlFacture);
                psFacture.setInt(1, idFacture);
                ResultSet rsFacture = psFacture.executeQuery();

                if (!rsFacture.next()) {
                    System.out.println("Facture introuvable.");
                    return;
                }

                montantFacture = rsFacture.getDouble("montant");

                // Total payé
                String sqlTotal = "SELECT COALESCE(SUM(montant),0) AS total FROM paiement WHERE idFacture = ?";
                PreparedStatement psTotal = con.prepareStatement(sqlTotal);
                psTotal.setInt(1, idFacture);
                ResultSet rsTotal = psTotal.executeQuery();
                rsTotal.next();
                double totalPaye = rsTotal.getDouble("total");

                double restant = montantFacture - totalPaye;

                // ✅ AFFICHAGE AVANT DEMANDE
                System.out.println("Montant facture : " + montantFacture);
                System.out.println("Total déjà payé : " + totalPaye);
                System.out.println("Montant restant : " + restant);

                if (restant <= 0) {
                    System.out.println("Cette facture est déjà payée.");
                    return;
                }

                // 3) Demande paiement APRÈS affichage
                System.out.print("Montant à payer: ");
                double montantPaye = Double.parseDouble(scanner.nextLine());

                if (montantPaye > restant || montantPaye <= 0) {
                    System.out.println("Paiement refusé.");
                    return;
                }

                System.out.print("Date (YYYY-MM-DD): ");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                // 4) Insert paiement
                String sqlInsert = "INSERT INTO paiement (montant, date, idFacture) VALUES (?, ?, ?)";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                psInsert.setDouble(1, montantPaye);
                psInsert.setDate(2, Date.valueOf(date));
                psInsert.setInt(3, idFacture);
                psInsert.executeUpdate();

                con.commit();
                System.out.println("Paiement enregistré ");
            }

        } catch (Exception e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }



    public static void mettreAJourPyment(Scanner scanner) {
        try {
            System.out.print("Paiement ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nouveau montant: ");
            double montant = Double.parseDouble(scanner.nextLine());

            System.out.print("Nouvelle date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            String sql = "UPDATE paiement SET montant = ?, date = ? WHERE id = ?";

            try (Connection con = createConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setDouble(1, montant);
                ps.setDate(2, Date.valueOf(date));
                ps.setInt(3, id);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Paiement modifié");
                } else {
                    System.out.println("ID introuvable");
                }

            }

        } catch (Exception e) {
            System.out.println(" Erreur (vérifie les valeurs)");
            e.printStackTrace();
        }
    }


    public static void listerPyment() {
        String sql = "SELECT id, idFacture, montant, date FROM paiement";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("=== Liste des paiements ===");

            boolean empty = true;
            while (rs.next()) {
                empty = false;
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | Facture: " + rs.getInt("idFacture") +
                                " | Montant: " + rs.getDouble("montant") +
                                " | Date: " + rs.getDate("date")
                );
            }

            if (empty) System.out.println("(aucun paiement)");

        } catch (Exception e) {
            System.out.println("Erreur SQL");
            e.printStackTrace();
        }
    }
}

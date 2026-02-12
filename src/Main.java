import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static FactureService factureService = new FactureService();
    static StatistiqueService statistiqueService = new StatistiqueService();

    public static void main(String[] args) {

        String choix;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1: Facture");
            System.out.println("2: Statistique");
            System.out.println("0: Quitter");

            choix = scanner.nextLine();

            if (choix.equals("1")) {
                menuFacture();
            }
            else if (choix.equals("2")) {
                menuStatistique();
            }

        } while (!choix.equals("0"));

        System.out.println("Programme terminé !");
    }

    // =========================
    // MENU FACTURE
    // =========================
    public static void menuFacture() {

        String choix;

        do {
            System.out.println("\n===== MENU FACTURE =====");
            System.out.println("1: Ajouter facture");
            System.out.println("2: Modifier facture");
            System.out.println("3: Lister factures");
            System.out.println("4: Rechercher facture");
            System.out.println("0: Retour");

            choix = scanner.nextLine();

            if (choix.equals("1")) {

                System.out.println("Montant:");
                double montant = Double.parseDouble(scanner.nextLine());

                System.out.println("ID Client:");
                int idClient = Integer.parseInt(scanner.nextLine());

                System.out.println("ID Prestataire:");
                int idPrestataire = Integer.parseInt(scanner.nextLine());

                Client client = new Client(idClient, "", "", "");
                Prestataire prestataire = new Prestataire(idPrestataire, "", "");

                Facture facture = new Facture(
                        0,
                        LocalDate.now(),
                        montant,
                        false,
                        client,
                        prestataire
                );

                factureService.creerFacture(facture);
            }

            else if (choix.equals("2")) {

                System.out.println("ID facture:");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.println("Nouveau montant:");
                double montant = Double.parseDouble(scanner.nextLine());

                System.out.println("Nouveau status (true/false):");
                boolean status = Boolean.parseBoolean(scanner.nextLine());

                factureService.modifierFacture(id, montant, status);
            }

            else if (choix.equals("3")) {

                List<Facture> factures = factureService.lister();

                for (Facture f : factures) {
                    System.out.println("----------------------");
                    System.out.println("ID: " + f.getId());
                    System.out.println("Date: " + f.getDate());
                    System.out.println("Montant: " + f.getMontant());
                    System.out.println("Status: " + f.isStatus());
                }
            }

            else if (choix.equals("4")) {

                System.out.println("ID facture:");
                int id = Integer.parseInt(scanner.nextLine());

                Facture f = factureService.findById(id);

                if (f != null) {
                    System.out.println("Facture trouvée:");
                    System.out.println("Montant: " + f.getMontant());
                } else {
                    System.out.println("Facture non trouvée");
                }
            }

        } while (!choix.equals("0"));
    }

    // =========================
    // MENU STATISTIQUE
    // =========================
    public static void menuStatistique() {

        String choix;

        do {
            System.out.println("\n===== MENU STATISTIQUE =====");
            System.out.println("1: Ajouter paiement");
            System.out.println("2: Afficher rapport");
            System.out.println("3: Sauvegarder statistique en base");
            System.out.println("0: Retour");

            choix = scanner.nextLine();

            if (choix.equals("1")) {

                System.out.println("Montant paiement:");
                double montant = Double.parseDouble(scanner.nextLine());

                System.out.println("Commission:");
                double commission = Double.parseDouble(scanner.nextLine());

                System.out.println("Facture payée (true/false):");
                boolean paye = Boolean.parseBoolean(scanner.nextLine());

                statistiqueService.ajouterPaiement(montant, commission, paye);
            }

            else if (choix.equals("2")) {
                statistiqueService.afficherRapport();
            }

            else if (choix.equals("3")) {
                statistiqueService.saveStatistique();
            }

        } while (!choix.equals("0"));
    }
}
